package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * SOCIAL-001 : 구글/라인 OAuth 설정값(Client ID·Secret 등)을 oauth.properties 파일에서 읽어온다.
 *
 * oauth.properties는 실제 비밀 값(Client Secret)이 들어있어서 .gitignore로 제외되어 있다.
 * 각자 로컬에서 oauth.properties.example을 복사해 oauth.properties로 만든 뒤,
 * 자기가 발급받은 값으로 채워 넣어야 한다. (src/main/java 바로 아래에 두면 빌드 시 build/classes로 복사된다)
 */
public class OAuthConfig {

	private static final Properties props = new Properties();

	static {
		try (InputStream in = OAuthConfig.class.getClassLoader().getResourceAsStream("oauth.properties")) {
			if (in != null) {
				props.load(in);
			} else {
				System.out.println("[OAuthConfig] oauth.properties 파일을 찾을 수 없습니다. "
						+ "oauth.properties.example을 복사해서 src/main/java 바로 아래에 oauth.properties로 만들어주세요.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String get(String key) {
		return props.getProperty(key);
	}
}
