package util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 이메일 인증번호 발송을 담당하는 유틸리티 클래스.
 *
 * 발신 계정(구글 Gmail 또는 네이버 메일) 정보는 mail.properties에서 읽어온다. (MailConfig 참고)
 * mail.properties가 아직 없거나 값이 비어 있으면, 개발 중에도 막히지 않도록 콘솔 로그 모드로 자동 대체한다.
 *
 * WEB-INF/lib에 javax.mail 관련 jar(javax.mail-1.6.2.jar, activation-1.1.1.jar)가 필요하다.
 */
public class MailSender {

	public static void sendVerificationCode(String toEmail, String code) {

		String host = MailConfig.get("mail.smtp.host");
		String port = MailConfig.get("mail.smtp.port");
		String user = MailConfig.get("mail.smtp.user");
		String password = MailConfig.get("mail.smtp.password");

		if (host == null || port == null || user == null || password == null) {
			// mail.properties가 없거나 값이 비어 있으면 콘솔 로그로 대체한다.
			System.out.println("[MailSender] mail.properties가 설정되지 않아 콘솔 로그로 대체합니다. "
					+ toEmail + " 로 발송할 인증번호: " + code);
			return;
		}

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);

		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			// 메일 제목/본문이 깨지지 않도록 UTF-8을 명시한다.
			message.setSubject("[ANIVERSE] メール認証番号のご案内", "UTF-8");
			message.setText("リクエストされた認証番号は [" + code + "] です。5分以内に入力してください。", "UTF-8");

			Transport.send(message);

			System.out.println("[MailSender] " + toEmail + " 로 인증 메일을 발송했습니다.");

		} catch (MessagingException e) {
			e.printStackTrace();
			// 발송 실패 시에도 개발/테스트가 막히지 않도록 콘솔 로그로 대체한다.
			System.out.println("[MailSender] 메일 발송 실패. 콘솔 로그로 대체합니다. "
					+ toEmail + " 로 발송할 인증번호: " + code);
		}
	}
}
