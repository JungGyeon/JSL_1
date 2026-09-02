package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.mindrot.jbcrypt.BCrypt;

import util.DBManager;

/**
 * 회원 정보 테이블(USER_TBL) 관련 DAO.
 *
 * 참고: USER는 Oracle의 예약어(의사 컬럼)라서 그냥 테이블명으로 쓰면 ORA-00903 오류가 날 수 있어
 * 팀에서 테이블명을 USER_TBL로 정했다. (sql/schema.sql 참고)
 */
public class UserDAO {

	private static final String TABLE = "USER_TBL";

	// USER-003 : 아이디 중복 확인
	public boolean isDuplicateId(String userId) {

		boolean duplicate = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT USER_ID FROM " + TABLE + " WHERE USER_ID = ?";

		try {

			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);

			rs = pstmt.executeQuery();

			duplicate = rs.next();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(rs, pstmt, conn);
		}

		return duplicate;
	}

	// USER-003(닉네임) : 닉네임 중복 확인
	public boolean isDuplicateNickname(String nickname) {

		boolean duplicate = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT NICKNAME FROM " + TABLE + " WHERE NICKNAME = ?";

		try {

			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, nickname);

			rs = pstmt.executeQuery();

			duplicate = rs.next();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(rs, pstmt, conn);
		}

		return duplicate;
	}

	// USER-002 : 회원가입 처리 (비밀번호는 BCrypt로 해시하여 저장한다)
	public void insertUser(UserDTO dto) {

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "INSERT INTO " + TABLE
				+ " (USER_ID, PASSWORD, NICKNAME, EMAIL, REG_DATE) VALUES (?, ?, ?, ?, SYSDATE)";

		try {

			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);

			String hashedPw = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());

			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, hashedPw);
			pstmt.setString(3, dto.getNickname());
			pstmt.setString(4, dto.getEmail());

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(pstmt, conn);
		}
	}

	// AUTH-001 : 로그인 인증. 아이디로 조회한 뒤 BCrypt로 비밀번호를 대조한다.
	public UserDTO login(String userId, String password) {

		UserDTO dto = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT USER_ID, PASSWORD, NICKNAME, EMAIL, REG_DATE FROM " + TABLE + " WHERE USER_ID = ?";

		try {

			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);

			rs = pstmt.executeQuery();

			if (rs.next()) {

				String savedPassword = rs.getString("password");

				if (matchesPassword(password, savedPassword)) {
					dto = new UserDTO();
					dto.setUserId(rs.getString("user_id"));
					dto.setNickname(rs.getString("nickname"));
					dto.setEmail(rs.getString("email"));
					dto.setRegDate(rs.getTimestamp("reg_date"));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(rs, pstmt, conn);
		}

		return dto;
	}

	/**
	 * 입력한 비밀번호와 저장된 비밀번호가 일치하는지 확인한다.
	 *
	 * join.jsp로 새로 가입한 회원은 BCrypt 해시로 저장되어 있어 BCrypt.checkpw로 비교하면 되지만,
	 * DB에 SQL로 직접 넣은 더미 데이터(예: user01 / 1234)는 평문 그대로 저장되어 있어
	 * BCrypt.checkpw에 넣으면 "Invalid salt" 오류가 난다.
	 * 그래서 저장된 값이 BCrypt 형식이 아니면(평문이면) 평문 비교로 대체한다.
	 */
	private boolean matchesPassword(String inputPassword, String savedPassword) {

		if (savedPassword != null && savedPassword.startsWith("$2")) {
			try {
				return BCrypt.checkpw(inputPassword, savedPassword);
			} catch (IllegalArgumentException e) {
				return false;
			}
		}

		// BCrypt 해시 형식이 아니면(평문 더미 데이터) 평문으로 비교한다.
		return inputPassword != null && inputPassword.equals(savedPassword);
	}

	// MY-001 : 회원정보 조회 (비밀번호 제외)
	public UserDTO getUserById(String userId) {

		UserDTO dto = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT USER_ID, NICKNAME, EMAIL, REG_DATE FROM " + TABLE + " WHERE USER_ID = ?";

		try {

			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new UserDTO();
				dto.setUserId(rs.getString("user_id"));
				dto.setNickname(rs.getString("nickname"));
				dto.setEmail(rs.getString("email"));
				dto.setRegDate(rs.getTimestamp("reg_date"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(rs, pstmt, conn);
		}

		return dto;
	}

	// MY-002 : 회원정보 수정 (닉네임, 이메일만 수정 가능하도록 한다)
	public void updateUser(UserDTO dto) {

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "UPDATE " + TABLE + " SET NICKNAME = ?, EMAIL = ? WHERE USER_ID = ?";

		try {

			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getNickname());
			pstmt.setString(2, dto.getEmail());
			pstmt.setString(3, dto.getUserId());

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(pstmt, conn);
		}
	}
}