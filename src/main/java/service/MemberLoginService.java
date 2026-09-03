package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UserDAO;
import model.UserDTO;

/**
 * AUTH-001~003 : 아이디/비밀번호로 로그인 인증 후 Session에 로그인 정보를 저장한다.
 * 성공/실패 여부는 request attribute "loginSuccess" 로, 실패 사유는 "errorMsg" 로 전달한다.
 */
public class MemberLoginService implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");

		String userId = request.getParameter("userId");
		String userPw = request.getParameter("userPw");

		if (isEmpty(userId) || isEmpty(userPw)) {
			fail(request, "IDとパスワードを入力してください。");
			return;
		}

		UserDAO dao = new UserDAO();
		UserDTO dto = dao.login(userId, userPw);

		// AUTH-003 : 로그인 실패
		if (dto == null) {
			fail(request, "IDまたはパスワードが一致しません。");
			return;
		}

		// AUTH-002 : Session에 로그인 사용자 정보 저장
		HttpSession session = request.getSession();
		session.setAttribute("userid", dto.getUserId());
		session.setAttribute("nickname", dto.getNickname());

		request.setAttribute("loginSuccess", true);
	}

	private void fail(HttpServletRequest request, String message) {
		request.setAttribute("loginSuccess", false);
		request.setAttribute("errorMsg", message);
	}

	private boolean isEmpty(String s) {
		return s == null || s.trim().isEmpty();
	}
}
