package model;

import java.sql.Timestamp;

/**
 * USER 테이블(회원 정보)에 대응하는 DTO.
 * 컬럼: USER_ID(PK), PASSWORD, NICKNAME, EMAIL, REG_DATE
 */
public class UserDTO {

	private String userId;
	private String password;
	private String nickname;
	private String email;
	private Timestamp regDate;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getRegDate() {
		return regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}
}
