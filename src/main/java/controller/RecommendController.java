package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.RecommendService;

@WebServlet("/recommend/list.do")
public class RecommendController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public RecommendController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Object userId = (session != null) ? session.getAttribute("userid") : null;

        // 로그인하지 않은 경우
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/member/loginForm.do");
            return;
        }

        // 추천 데이터 가져오기
        new RecommendService().doCommand(request, response);

        // 현재 메뉴 표시
        request.setAttribute("activePage", "recommend");

        // JSP로 이동
        request.getRequestDispatcher("/recommend/recommend.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		doGet(request, response);
	}
}