package controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UserDAO;
import model.UserDTO;
import service.GoogleOAuthService;
import service.LineOAuthService;

/**
 * SOCIAL-001 : 구글/라인 소셜 로그인·회원가입.
 *
 * find-or-create 방식이라 "가입"과 "로그인"이 버튼 하나로 처리된다.
 *  - USER_ID를 "google_"+구글 고유ID / "line_"+라인 고유ID 로 만들어서 기존 USER_TBL의 PK 체계와 충돌 없이 저장한다.
 *  - 이미 있는 회원이면 그대로 로그인, 처음 온 사람이면 자동으로 회원가입한 뒤 로그인한다.
 */
@WebServlet("/oauth/*")
public class OAuthController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getPathInfo();
		String contextPath = request.getContextPath();
		HttpSession session = request.getSession();

		try {

			if (action == null) {
				response.sendRedirect(contextPath + "/login/login.jsp");
				return;
			}

			switch (action) {

			case "/google": {
				String state = issueState(session);
				response.sendRedirect(new GoogleOAuthService().buildAuthUrl(state));
				return;
			}

			case "/google/callback": {
				if (!checkState(request, session)) {
					response.sendRedirect(contextPath + "/login/login.jsp?errorMsg=" + encode("不正なリクエストです。もう一度お試しください。"));
					return;
				}
				String code = request.getParameter("code");
				UserDTO dto = new GoogleOAuthService().handleCallback(code);
				loginOrJoin(dto, session);
				// 로그인/가입 성공 시 마이페이지가 아니라 메인 화면으로 이동한다.
				response.sendRedirect(contextPath + "/main.do");
				return;
			}

			case "/line": {
				String state = issueState(session);
				response.sendRedirect(new LineOAuthService().buildAuthUrl(state));
				return;
			}

			case "/line/callback": {
				if (!checkState(request, session)) {
					response.sendRedirect(contextPath + "/login/login.jsp?errorMsg=" + encode("不正なリクエストです。もう一度お試しください。"));
					return;
				}
				String code = request.getParameter("code");
				UserDTO dto = new LineOAuthService().handleCallback(code);
				loginOrJoin(dto, session);
				// 로그인/가입 성공 시 마이페이지가 아니라 메인 화면으로 이동한다.
				response.sendRedirect(contextPath + "/main.do");
				return;
			}

			default:
				response.sendRedirect(contextPath + "/login/login.jsp");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(contextPath + "/login/login.jsp?errorMsg=" + encode("ソーシャルログイン中にエラーが発生しました。"));
		}
	}

	/** CSRF 방지용 state 값을 세션에 저장해두고, 콜백에서 그대로 돌아왔는지 검사한다. */
	private String issueState(HttpSession session) {
		String state = UUID.randomUUID().toString();
		session.setAttribute("oauthState", state);
		return state;
	}

	private boolean checkState(HttpServletRequest request, HttpSession session) {
		String expected = (String) session.getAttribute("oauthState");
		String actual = request.getParameter("state");
		session.removeAttribute("oauthState");
		return expected != null && expected.equals(actual);
	}

	/** USER_ID가 이미 있으면 로그인 세션만 만들고, 없으면 새로 저장(자동 회원가입)한다. */
	private void loginOrJoin(UserDTO dto, HttpSession session) {

		UserDAO dao = new UserDAO();

		if (dao.isDuplicateId(dto.getUserId())) {
			UserDTO saved = dao.getUserById(dto.getUserId());
			session.setAttribute("userid", saved.getUserId());
			session.setAttribute("nickname", saved.getNickname());
		} else {
			dao.insertSocialUser(dto);
			session.setAttribute("userid", dto.getUserId());
			session.setAttribute("nickname", dto.getNickname());
		}
	}

	private String encode(String value) {
		try {
			return URLEncoder.encode(value, "UTF-8");
		} catch (Exception e) {
			return "";
		}
	}
}
