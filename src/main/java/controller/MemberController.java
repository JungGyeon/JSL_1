package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.MemberEmailCodeService;
import service.MemberEmailVerifyService;
import service.MemberIdCheckService;
import service.MemberInfoUpdateService;
import service.MemberJoinService;
import service.MemberLoginService;
import service.MemberLogoutService;
import service.MemberNicknameCheckService;

/**
 * 회원(USER) 관련 기능을 담당하는 컨트롤러.
 * 담당 기능: 회원가입(USER), 로그인/로그아웃/Session(AUTH), 마이페이지 정보수정(MY)
 */
@WebServlet("/member/*")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MemberController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doAction(request, response);
	}

	protected void doAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");

		String action = request.getPathInfo();
		String contextPath = request.getContextPath();

		System.out.println("action: " + action);

		String page = null;

		switch (action) {

		case "/loginForm.do": {
			// 로그인 화면 표시 전용 (입력값 검증 없이 그냥 login.jsp로 이동)
			page = "/login/login.jsp";
			break;
		}

		case "/joinForm.do": {
			// 회원가입 화면 표시 전용 (입력값 검증 없이 그냥 join.jsp로 이동)
			page = "/join/join.jsp";
			break;
		}

		case "/login.do": {
			new MemberLoginService().doCommand(request, response);
			if (Boolean.TRUE.equals(request.getAttribute("loginSuccess"))) {
				// 로그인 성공 시 마이페이지가 아니라 메인 화면으로 이동한다.
				response.sendRedirect(contextPath + "/main.do");
				return;
			}
			page = "/login/login.jsp";
			break;
		}

		case "/logout.do": {
			new MemberLogoutService().doCommand(request, response);
			response.sendRedirect(contextPath + "/main.do");
			return;
		}

		case "/join.do": {
			new MemberJoinService().doCommand(request, response);
			if (Boolean.TRUE.equals(request.getAttribute("joinSuccess"))) {
				// 회원가입 성공 시 마이페이지가 아니라 메인 화면으로 이동한다.
				response.sendRedirect(contextPath + "/index.jsp");
				return;
			}
			page = "/join/join.jsp";
			break;
		}

		case "/idcheck.do": {
			new MemberIdCheckService().doCommand(request, response);
			return;
		}

		case "/nickcheck.do": {
			new MemberNicknameCheckService().doCommand(request, response);
			return;
		}

		case "/emailcode.do": {
			new MemberEmailCodeService().doCommand(request, response);
			return;
		}

		case "/emailverify.do": {
			new MemberEmailVerifyService().doCommand(request, response);
			return;
		}

		case "/update.do": {
			new MemberInfoUpdateService().doCommand(request, response);
			HttpSession session = request.getSession(false);
			String userId = (session != null) ? (String) session.getAttribute("userid") : null;
			if (userId != null) {
				response.sendRedirect(contextPath + "/favorite/list.do?userId=" + userId);
				return;
			}
			page = "/login/login.jsp";
			break;
		}
		}

		if (page != null) {
			request.getRequestDispatcher(page).forward(request, response);
		}
	}
}
