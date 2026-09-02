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
 * 회원가입 시 이메일 인증번호를 생성해서 세션에 저장하고 발송(데모 모드에서는 콘솔 로그)한다.
 * 응답: "ok:인증번호" (데모 모드 - 화면에서 바로 확인 가능) / "empty"(이메일 미입력)
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

		// 데모 모드: 실제 메일 서버가 없으므로 발급된 인증번호를 응답에 함께 실어 화면에서 바로 보여준다.
		// 실제 SMTP 연동 후에는 보안을 위해 이 줄을 out.print("ok"); 로 바꿔서 인증번호를 노출하지 않는다.
		out.print("ok:" + code);
	}

	private String generateCode() {
		int num = RANDOM.nextInt(1_000_000);
		return String.format("%06d", num);
	}
}
