package model;

import java.sql.Timestamp;

/**
 * USER 테이블(회원 정보)에 대응하는 DTO.
 * 컬럼: USER_ID(PK), PASSWORD, NICKNAME, EMAIL, PROVIDER, PROVIDER_ID, REG_DATE
 *
 * SOCIAL-001 : 구글/라인 소셜 로그인 지원을 위해 PROVIDER(가입 경로), PROVIDER_ID(소셜 서비스가 발급한 고유 ID)를 추가했다.
 * 일반 아이디/비밀번호로 가입한 회원은 PROVIDER = "LOCAL", PROVIDER_ID = null 이다.
 */
public class UserDTO {

	private String userId;
	private String password;
	private String nickname;
	private String email;
	private String provider = "LOCAL";
	private String providerId;
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

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public Timestamp getRegDate() {
		return regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}
}
