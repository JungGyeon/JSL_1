package service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UserDAO;

/**
 * USER-003(닉네임) : 회원가입 화면에서 닉네임 중복 여부를 비동기(AJAX)로 확인한다.
 * 응답: "dup"(이미 사용 중) / "ok"(사용 가능) / "empty"(닉네임 미입력)
 */
public class MemberNicknameCheckService implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");

		String nickname = request.getParameter("nickname");

		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();

		if (nickname == null || nickname.trim().isEmpty()) {
			out.print("empty");
			return;
		}

		UserDAO dao = new UserDAO();
		boolean duplicate = dao.isDuplicateNickname(nickname);

		out.print(duplicate ? "dup" : "ok");
	}
}