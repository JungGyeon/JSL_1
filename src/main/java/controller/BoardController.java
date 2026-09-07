package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.BoardDeleteService;
import service.BoardDetailService;
import service.BoardListService;
import service.BoardUpdateService;
import service.BoardWriteService;

@WebServlet("/board/*")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BoardController() {
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
			new BoardListService().doCommand(request, response);
			page = "/board/board.jsp";
			break;

		case "/write.do":
			new BoardWriteService().doCommand(request, response);
			page = "/board/write.jsp";
			break;

		case "/detail.do":
			new BoardDetailService().doCommand(request, response);
			page = "/board/detail.jsp";
			break;

		case "/update.do":
			new BoardUpdateService().doCommand(request, response);
			page = "/board/update.jsp";
			break;

		case "/delete.do":
			new BoardDeleteService().doCommand(request, response);
			page = "/board/delete.jsp";
			break;
		}

		if (page != null) {
			request.getRequestDispatcher(page).forward(request, response);
		}

	}

}
