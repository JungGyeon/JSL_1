package service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AnimeDAO;
import model.AnimeDTO;
import model.FavoriteDAO;

public class AnimeSearchService implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		String title = request.getParameter("title");
		String year = request.getParameter("year");
		String sort = request.getParameter("sort");
		

		AnimeDAO dao = new AnimeDAO();
		List<AnimeDTO> list = dao.searchAnime(title,year,sort);

		request.setAttribute("list", list); // 검색 결과용
		request.setAttribute("title", title); // 검색 이름 표시 
		request.setAttribute("year", year);
		request.setAttribute("sort", sort);
		
		HttpSession session = request.getSession(false);
		String userId = (session != null) ? (String) session.getAttribute("userid") : null;
		Set<Integer> favIds = (userId != null)
				? new FavoriteDAO().getFavoriteAnimeIds(userId)
				: new HashSet<Integer>();
		request.setAttribute("favIds", favIds);
		
	}

}
