package service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UserDAO;

/**
 * USER-003 : 회원가입 화면에서 아이디 중복 여부를 비동기(AJAX)로 확인한다.
 * 응답: "dup"(이미 사용 중) / "ok"(사용 가능) / "empty"(아이디 미입력)
 */
public class MemberIdCheckService implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");

		String userId = request.getParameter("userId");

		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();

		if (userId == null || userId.trim().isEmpty()) {
			out.print("empty");
			return;
		}

		UserDAO dao = new UserDAO();
		boolean duplicate = dao.isDuplicateId(userId);

		out.print(duplicate ? "dup" : "ok");
	}
}
