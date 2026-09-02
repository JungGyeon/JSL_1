package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UserDAO;
import model.UserDTO;

/**
 * USER-001~004 : 회원가입 처리.
 * 입력값 검증 -> 이메일 인증 완료 여부 확인 -> 아이디 중복 확인(서버측 최종 확인) -> USER 테이블 저장 -> 자동 로그인
 *
 * 성공/실패 여부는 request attribute "joinSuccess" 로, 실패 사유는 "errorMsg" 로 전달한다.
 */
public class MemberJoinService implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");

		String userId = request.getParameter("userId");
		String userPw = request.getParameter("userPw");
		String userPwCheck = request.getParameter("userPwCheck");
		String nickname = request.getParameter("nickname");
		String email = request.getParameter("email");

		// USER-004 : 입력값 검증
		if (isEmpty(userId) || isEmpty(userPw) || isEmpty(userPwCheck) || isEmpty(nickname) || isEmpty(email)) {
			fail(request, "모든 항목을 입력해 주세요.");
			return;
		}

		if (!userPw.equals(userPwCheck)) {
			fail(request, "비밀번호가 일치하지 않습니다.");
			return;
		}

		// 이메일 인증(데모용 인증번호) 완료 여부 확인
		HttpSession session = request.getSession();
		Boolean emailVerified = (Boolean) session.getAttribute("emailVerified");
		String verifiedTarget = (String) session.getAttribute("emailVerifyTarget");

		if (emailVerified == null || !emailVerified || verifiedTarget == null || !verifiedTarget.equals(email)) {
			fail(request, "이메일 인증을 완료해 주세요.");
			return;
		}

		UserDAO dao = new UserDAO();

		// USER-003 : 아이디 중복 확인 (버튼 클릭으로 한 번 확인했더라도, 최종 제출 시 서버에서 다시 확인)
		if (dao.isDuplicateId(userId)) {
			fail(request, "이미 사용 중인 아이디입니다.");
			return;
		}

		// USER-003(닉네임) : 닉네임 중복 확인 (버튼 클릭으로 한 번 확인했더라도, 최종 제출 시 서버에서 다시 확인)
		if (dao.isDuplicateNickname(nickname)) {
			fail(request, "이미 사용 중인 닉네임입니다.");
			return;
		}

		UserDTO dto = new UserDTO();
		dto.setUserId(userId);
		dto.setPassword(userPw);
		dto.setNickname(nickname);
		dto.setEmail(email);

		// USER-002 : 회원정보 저장 (UserDAO 내부에서 BCrypt로 비밀번호를 해시하여 저장)
		dao.insertUser(dto);

		// 인증 관련 세션 값은 더 이상 필요 없으므로 정리
		session.removeAttribute("emailVerifyCode");
		session.removeAttribute("emailVerifyTarget");
		session.removeAttribute("emailVerifyTime");
		session.removeAttribute("emailVerified");

		// AUTH-002 : 가입 완료 즉시 로그인 상태로 전환 (Session에 로그인 사용자 정보 저장)
		session.setAttribute("userid", userId);
		session.setAttribute("nickname", nickname);

		request.setAttribute("joinSuccess", true);
	}

	private void fail(HttpServletRequest request, String message) {
		request.setAttribute("joinSuccess", false);
		request.setAttribute("errorMsg", message);
	}

	private boolean isEmpty(String s) {
		return s == null || s.trim().isEmpty();
	}
}