package service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.AnimeDAO;
import model.AnimeDTO;

public class AnimeGetListService implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		AnimeDAO dao = new AnimeDAO();

		List<AnimeDTO> list = dao.getAnimeList();
		request.setAttribute("list", list);

	}

}
