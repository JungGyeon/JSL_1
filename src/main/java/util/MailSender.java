package util;

/**
 * 이메일 인증번호 발송을 담당하는 유틸리티 클래스.
 *
 * [데모 모드] (현재 상태)
 * 실제 메일 서버 계정 없이도 동작을 확인할 수 있도록, 인증번호를 실제로 발송하지 않고
 * 서버 콘솔에 로그로만 출력한다. 화면(join.jsp의 JS)에서도 발급된 인증번호를 함께
 * 응답받아 알림창으로 보여주므로, 실제 메일함을 열지 않아도 바로 테스트할 수 있다.
 *
 * [실제 SMTP 발송으로 전환하는 방법]
 * 1) WEB-INF/lib 에 jakarta.mail(또는 javax.mail) jar를 추가한다.
 * 2) 아래 sendVerificationCode() 안의 "데모 모드" 블록을 지우고,
 *    주석 처리된 SMTP 발송 코드의 주석을 해제한다.
 * 3) SMTP_HOST / SMTP_PORT / SMTP_USER / SMTP_PASSWORD 값을
 *    팀에서 사용할 발신 계정 정보로 채운다.
 *    (Gmail 사용 시 Google 계정에서 "앱 비밀번호"를 발급받아 SMTP_PASSWORD에 사용할 것 —
 *     일반 로그인 비밀번호는 사용할 수 없다.)
 * 4) service.MemberEmailCodeService 에서 응답 문자열에 인증번호를 그대로 실어 보내는
 *    부분("ok:" + code)을 "ok"로만 바꾸면, 인증번호가 더 이상 화면(JS)에 노출되지 않는다.
 */
public class MailSender {

	// ↓ 실제 SMTP 연동 시 사용할 값들 (데모 모드에서는 사용하지 않음)
	private static final String SMTP_HOST = "smtp.gmail.com";
	private static final String SMTP_PORT = "587";
	private static final String SMTP_USER = "team-account@gmail.com";
	private static final String SMTP_PASSWORD = "앱 비밀번호를 입력하세요";

	public static void sendVerificationCode(String toEmail, String code) {

		// ===== 데모 모드: 실제 발송 대신 서버 콘솔에 로그로 출력 =====
		System.out.println("[MailSender] (DEMO) " + toEmail + " 로 발송할 인증번호: " + code);

		/* ===== 실제 SMTP 발송으로 전환할 때는 아래 주석을 해제하고, 위 데모 로그 줄은 지운다 =====

		java.util.Properties props = new java.util.Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", SMTP_HOST);
		props.put("mail.smtp.port", SMTP_PORT);

		javax.mail.Session session = javax.mail.Session.getInstance(props, new javax.mail.Authenticator() {
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(SMTP_USER, SMTP_PASSWORD);
			}
		});

		try {
			javax.mail.Message message = new javax.mail.internet.MimeMessage(session);
			message.setFrom(new javax.mail.internet.InternetAddress(SMTP_USER));
			message.setRecipients(javax.mail.Message.RecipientType.TO,
					javax.mail.internet.InternetAddress.parse(toEmail));
			message.setSubject("[ANIVERSE] 이메일 인증번호 안내");
			message.setText("요청하신 인증번호는 [" + code + "] 입니다. 5분 이내에 입력해 주세요.");
			javax.mail.Transport.send(message);
		} catch (javax.mail.MessagingException e) {
			e.printStackTrace();
		}

		===== 여기까지 ===== */
	}
}
