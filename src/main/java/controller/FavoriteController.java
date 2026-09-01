package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import service.FavoriteAddService;
import service.FavoriteDeleteService;
import service.FavoriteListService;

@WebServlet("/favorite/*")
public class FavoriteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FavoriteController() {
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

		System.out.println("action: " + action);

		String page = null;

		switch(action) {
		case "/add.do":
			new FavoriteAddService().doCommand(request, response);
			break;
		case "/delete.do":
			new FavoriteDeleteService().doCommand(request, response);
			break;
		case "/list.do":
		    new FavoriteListService().doCommand(request, response);
		    page = "/mypage/mypage.jsp";
		    break;
		}
		
		if(page != null) {
		    request.getRequestDispatcher(page).forward(request, response);
		}
	}
}
