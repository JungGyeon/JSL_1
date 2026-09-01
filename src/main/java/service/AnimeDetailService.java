package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.AnimeDAO;
import model.AnimeDTO;

public class AnimeDetailService implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int animeId = Integer.parseInt(request.getParameter("animeId")) ;
		AnimeDAO dao = new AnimeDAO();
		AnimeDTO dto = dao.getAnimeDetail(animeId);
		request.setAttribute("anime", dto);

	}

}
