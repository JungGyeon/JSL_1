package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.MailSender;

/**
 * 회원가입 시 이메일 인증번호를 생성해서 세션에 저장하고, MailSender로 실제 발송(구글/네이버 SMTP)한다.
 * mail.properties가 아직 설정되지 않았으면 MailSender가 자동으로 콘솔 로그로 대체한다.
 * 응답: "ok"(정상 발송 시도) / "empty"(이메일 미입력)
 *
 * 보안을 위해 인증번호는 응답에 실어 보내지 않는다(더 이상 "ok:코드" 형태가 아니다).
 * 실제로 코드를 받으려면 메일함을 확인해야 한다.
 */
public class MemberEmailCodeService implements Command {

	private static final SecureRandom RANDOM = new SecureRandom();

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");

		String email = request.getParameter("email");

		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();

		if (email == null || email.trim().isEmpty()) {
			out.print("empty");
			return;
		}

		String code = generateCode();

		HttpSession session = request.getSession();
		session.setAttribute("emailVerifyCode", code);
		session.setAttribute("emailVerifyTarget", email);
		session.setAttribute("emailVerifyTime", System.currentTimeMillis());
		session.setAttribute("emailVerified", false);

		MailSender.sendVerificationCode(email, code);

		out.print("ok");
	}

	private String generateCode() {
		int num = RANDOM.nextInt(1_000_000);
		return String.format("%06d", num);
	}
}
