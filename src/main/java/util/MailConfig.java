package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 이메일 인증 발송용 SMTP 계정 정보(mail.properties)를 읽어오는 유틸.
 * mail.properties가 없거나 값이 비어 있으면 get()이 null을 반환하고,
 * MailSender는 이 경우 콘솔 로그 모드로 자동 전환한다.
 */
public class MailConfig {

	private static final Properties props = new Properties();

	static {
		try (InputStream in = MailConfig.class.getClassLoader().getResourceAsStream("mail.properties")) {
			if (in != null) {
				props.load(in);
			} else {
				System.out.println("[MailConfig] mail.properties 파일을 찾을 수 없습니다. "
						+ "mail.properties.example을 복사해서 src/main/java 바로 아래에 mail.properties로 만들어주세요. "
						+ "(만들기 전까지는 인증번호가 콘솔 로그로만 출력됩니다)");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String get(String key) {
		String value = props.getProperty(key);
		return (value == null || value.trim().isEmpty()) ? null : value;
	}
}
