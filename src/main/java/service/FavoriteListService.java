package service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.AnimeDTO;
import model.FavoriteDAO;

public class FavoriteListService implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		String userId = request.getParameter("userId");
		
		FavoriteDAO dao = new FavoriteDAO();
		
		List<AnimeDTO> list = dao.getFavoriteList(userId);
		request.setAttribute("list", list);
		
	}

}
