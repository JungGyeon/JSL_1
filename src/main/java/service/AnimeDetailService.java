package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AnimeDAO;
import model.AnimeDTO;
import model.FavoriteDAO;

public class AnimeDetailService implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int animeId = Integer.parseInt(request.getParameter("animeId")) ;
		AnimeDAO dao = new AnimeDAO();
		AnimeDTO dto = dao.getAnimeDetail(animeId);
		request.setAttribute("anime", dto);
		
		HttpSession session = request.getSession(false);
		String userId = (session != null) ? (String) session.getAttribute("userid") : null;
		boolean isFavorite = false;
		if (userId != null) {
			isFavorite = new FavoriteDAO().isFavorite(userId, animeId);
		}
		request.setAttribute("isFavorite", isFavorite);

	}

}
