package service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 사용자가 입력한 인증번호가 세션에 저장된 값과 일치하는지 확인한다.
 * 응답: "ok"(일치) / "mismatch"(불일치) / "expired"(유효시간 초과) / "fail"(요청 이력 없음)
 */
public class MemberEmailVerifyService implements Command {

	private static final long CODE_VALID_MILLIS = 5 * 60 * 1000L; // 5분

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");

		String email = request.getParameter("email");
		String inputCode = request.getParameter("code");

		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession(false);

		if (session == null) {
			out.print("fail");
			return;
		}

		String savedCode = (String) session.getAttribute("emailVerifyCode");
		String savedTarget = (String) session.getAttribute("emailVerifyTarget");
		Long savedTime = (Long) session.getAttribute("emailVerifyTime");

		if (savedCode == null || savedTarget == null || savedTime == null) {
			out.print("fail");
			return;
		}

		if (System.currentTimeMillis() - savedTime > CODE_VALID_MILLIS) {
			session.setAttribute("emailVerified", false);
			out.print("expired");
			return;
		}

		if (!savedTarget.equals(email) || !savedCode.equals(inputCode)) {
			out.print("mismatch");
			return;
		}

		session.setAttribute("emailVerified", true);
		out.print("ok");
	}
}
