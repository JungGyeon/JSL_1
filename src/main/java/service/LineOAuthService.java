package service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.UserDTO;
import util.OAuthConfig;

/**
 * SOCIAL-001(LINE) : 라인 로그인.
 *
 * 흐름은 구글과 동일(인가 -> code -> access_token -> 프로필)하지만,
 * LINE의 /v2/profile API는 이메일을 주지 않는다. 그래서 scope에 "email"을 넣어 발급받은
 * id_token(JWT)의 payload를 직접 디코딩해서 이메일 클레임을 꺼내 쓴다.
 * (LINE Developers 콘솔에서 채널에 이메일 권한이 아직 승인되지 않았거나, 사용자가 이메일 제공에
 *  동의하지 않으면 이메일을 못 받아올 수 있는데, 그 경우 USER_TBL.EMAIL이 NOT NULL이라 임시 이메일로 대체한다.)
 */
public class LineOAuthService {

	private static final String AUTH_URL = "https://access.line.me/oauth2/v2.1/authorize";
	private static final String TOKEN_URL = "https://api.line.me/oauth2/v2.1/token";
	private static final String PROFILE_URL = "https://api.line.me/v2/profile";

	public String buildAuthUrl(String state) {

		String channelId = OAuthConfig.get("line.channel.id");
		String redirectUri = OAuthConfig.get("line.redirect.uri");

		StringBuilder url = new StringBuilder(AUTH_URL);
		url.append("?response_type=code");
		url.append("&client_id=").append(encode(channelId));
		url.append("&redirect_uri=").append(encode(redirectUri));
		url.append("&state=").append(encode(state));
		url.append("&scope=").append(encode("profile openid email"));

		return url.toString();
	}

	public UserDTO handleCallback(String code) throws Exception {

		String channelId = OAuthConfig.get("line.channel.id");
		String channelSecret = OAuthConfig.get("line.channel.secret");
		String redirectUri = OAuthConfig.get("line.redirect.uri");

		StringBuilder body = new StringBuilder();
		body.append("grant_type=authorization_code");
		body.append("&code=").append(encode(code));
		body.append("&redirect_uri=").append(encode(redirectUri));
		body.append("&client_id=").append(encode(channelId));
		body.append("&client_secret=").append(encode(channelSecret));

		String tokenResponse = post(TOKEN_URL, body.toString());
		JsonObject tokenJson = JsonParser.parseString(tokenResponse).getAsJsonObject();

		String accessToken = tokenJson.get("access_token").getAsString();
		String idToken = tokenJson.has("id_token") ? tokenJson.get("id_token").getAsString() : null;

		String profileResponse = get(PROFILE_URL, accessToken);
		JsonObject profile = JsonParser.parseString(profileResponse).getAsJsonObject();

		String userId = profile.get("userId").getAsString();
		String displayName = profile.has("displayName") ? profile.get("displayName").getAsString()
				: ("line_" + userId);

		String email = (idToken != null) ? extractEmailFromIdToken(idToken) : null;
		if (email == null) {
			// 이메일 권한이 없거나 사용자가 동의하지 않은 경우: NOT NULL 제약 때문에 임시 이메일로 대체
			email = userId + "@line.local";
		}

		UserDTO dto = new UserDTO();
		dto.setUserId("line_" + userId);
		dto.setNickname(displayName);
		dto.setEmail(email);
		dto.setProvider("LINE");
		dto.setProviderId(userId);

		return dto;
	}

	/** id_token(JWT)의 payload 부분만 디코딩해서 email 클레임을 꺼낸다. (서명 검증은 하지 않음 - LINE 토큰 엔드포인트에서 HTTPS로 직접 받은 값이라 표시 용도로만 사용) */
	private String extractEmailFromIdToken(String idToken) {
		try {
			String[] parts = idToken.split("\\.");
			if (parts.length < 2) {
				return null;
			}
			String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
			JsonObject payload = JsonParser.parseString(payloadJson).getAsJsonObject();
			return payload.has("email") ? payload.get("email").getAsString() : null;
		} catch (Exception e) {
			return null;
		}
	}

	private String post(String urlStr, String body) throws Exception {

		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
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
			throw new RuntimeException("라인 OAuth 요청 실패 (HTTP " + status + "): " + sb);
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
