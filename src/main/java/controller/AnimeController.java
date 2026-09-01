package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.AnimeDetailService;
import service.AnimeGetListService;
import service.AnimeSearchService;

@WebServlet("/anime/*")
public class AnimeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AnimeController() {
		super();
		// TODO Auto-generated constructor stub
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

		System.out.println("action: " + action);

		String page = null;

		switch (action) {
		case "/list.do":
			new AnimeGetListService().doCommand(request, response);
			page = "/list/list.jsp";
			break;

		case "/search.do":
			new AnimeSearchService().doCommand(request, response);
			page = "/search/search.jsp";
			break;

		case "/detail.do":
			new AnimeDetailService().doCommand(request, response);
			page = "/detail/detail.jsp";
			break;

		}

		if (page != null) {
			request.getRequestDispatcher(page).forward(request, response);
		}

	}

}
