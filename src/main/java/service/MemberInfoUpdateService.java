package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UserDAO;
import model.UserDTO;

/**
 * MY-002 : 로그인한 사용자의 닉네임/이메일을 수정한다. (선택 기능)
 * 성공/실패 여부는 request attribute "updateSuccess" 로, 실패 사유는 "errorMsg" 로 전달한다.
 */
public class MemberInfoUpdateService implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");

		HttpSession session = request.getSession(false);
		String userId = (session != null) ? (String) session.getAttribute("userid") : null;

		if (userId == null) {
			request.setAttribute("updateSuccess", false);
			request.setAttribute("errorMsg", "로그인이 필요합니다.");
			return;
		}

		String nickname = request.getParameter("nickname");
		String email = request.getParameter("email");

		if (isEmpty(nickname) || isEmpty(email)) {
			request.setAttribute("updateSuccess", false);
			request.setAttribute("errorMsg", "닉네임과 이메일을 입력해 주세요.");
			return;
		}

		UserDTO dto = new UserDTO();
		dto.setUserId(userId);
		dto.setNickname(nickname);
		dto.setEmail(email);

		UserDAO dao = new UserDAO();
		dao.updateUser(dto);

		// 화면에 곧바로 반영되도록 세션의 닉네임도 함께 갱신한다.
		session.setAttribute("nickname", nickname);

		request.setAttribute("updateSuccess", true);
	}

	private boolean isEmpty(String s) {
		return s == null || s.trim().isEmpty();
	}
}
