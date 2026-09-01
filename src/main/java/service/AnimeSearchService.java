package service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.AnimeDAO;
import model.AnimeDTO;

public class AnimeSearchService implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		String title = request.getParameter("title");

		AnimeDAO dao = new AnimeDAO();
		List<AnimeDTO> list = dao.searchAnime(title);

		request.setAttribute("list", list); // 검색 결과용
		request.setAttribute("title", title); // 검색 이름 표시 
	}

}
