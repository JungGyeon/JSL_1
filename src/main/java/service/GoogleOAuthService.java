package service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.UserDTO;
import util.OAuthConfig;

/**
 * SOCIAL-001(GOOGLE) : 구글 OAuth2 로그인/가입.
 *
 * 1) buildAuthUrl()로 만든 주소로 사용자를 보내면 구글 로그인 화면이 뜬다.
 * 2) 사용자가 로그인/동의를 마치면 구글이 redirect_uri(=/oauth/google/callback)로
 *    "code"라는 값을 붙여서 되돌려준다.
 * 3) handleCallback(code)에서 그 code를 access_token으로 교환하고,
 *    access_token으로 구글 사용자 정보(userinfo)를 조회해서 UserDTO로 만들어 돌려준다.
 */
public class GoogleOAuthService {

	private static final String AUTH_URL = "https://accounts.google.com/o/oauth2/v2/auth";
	private static final String TOKEN_URL = "https://oauth2.googleapis.com/token";
	private static final String USERINFO_URL = "https://www.googleapis.com/oauth2/v3/userinfo";

	public String buildAuthUrl(String state) {

		String clientId = OAuthConfig.get("google.client.id");
		String redirectUri = OAuthConfig.get("google.redirect.uri");

		StringBuilder url = new StringBuilder(AUTH_URL);
		url.append("?client_id=").append(encode(clientId));
		url.append("&redirect_uri=").append(encode(redirectUri));
		url.append("&response_type=code");
		url.append("&scope=").append(encode("openid email profile"));
		url.append("&state=").append(encode(state));
		url.append("&prompt=select_account");

		return url.toString();
	}

	public UserDTO handleCallback(String code) throws Exception {

		String clientId = OAuthConfig.get("google.client.id");
		String clientSecret = OAuthConfig.get("google.client.secret");
		String redirectUri = OAuthConfig.get("google.redirect.uri");

		// 1) code -> access_token 교환
		StringBuilder body = new StringBuilder();
		body.append("code=").append(encode(code));
		body.append("&client_id=").append(encode(clientId));
		body.append("&client_secret=").append(encode(clientSecret));
		body.append("&redirect_uri=").append(encode(redirectUri));
		body.append("&grant_type=authorization_code");

		String tokenResponse = post(TOKEN_URL, body.toString());
		JsonObject tokenJson = JsonParser.parseString(tokenResponse).getAsJsonObject();
		String accessToken = tokenJson.get("access_token").getAsString();

		// 2) access_token으로 사용자 프로필 조회
		String profileResponse = get(USERINFO_URL, accessToken);
		JsonObject profile = JsonParser.parseString(profileResponse).getAsJsonObject();

		String sub = profile.get("sub").getAsString();
		String email = profile.has("email") ? profile.get("email").getAsString() : (sub + "@google.local");
		String name = profile.has("name") ? profile.get("name").getAsString() : ("google_" + sub);

		UserDTO dto = new UserDTO();
		dto.setUserId("google_" + sub);
		dto.setNickname(name);
		dto.setEmail(email);
		dto.setProvider("GOOGLE");
		dto.setProviderId(sub);

		return dto;
	}

	private String post(String urlStr, String body) throws Exception {

		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Accept", "application/json");
		conn.setDoOutput(true);

		try (OutputStream os = conn.getOutputStream()) {
			os.write(body.getBytes(StandardCharsets.UTF_8));
		}

		return readBody(conn);
	}

	private String get(String urlStr, String accessToken) throws Exception {

		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Authorization", "Bearer " + accessToken);

		return readBody(conn);
	}

	private String readBody(HttpURLConnection conn) throws Exception {

		int status = conn.getResponseCode();
		boolean ok = status >= 200 && status < 300;

		InputStreamReader isr = new InputStreamReader(
				ok ? conn.getInputStream() : conn.getErrorStream(), StandardCharsets.UTF_8);

		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(isr)) {
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		}

		if (!ok) {
			throw new RuntimeException("구글 OAuth 요청 실패 (HTTP " + status + "): " + sb);
		}

		return sb.toString();
	}

	private String encode(String value) {
		try {
			return URLEncoder.encode(value == null ? "" : value, "UTF-8");
		} catch (Exception e) {
			return "";
		}
	}
}
