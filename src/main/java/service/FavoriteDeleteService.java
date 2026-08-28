package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.FavoriteDAO;

public class FavoriteDeleteService implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		String userId = request.getParameter("userId");
		int animeId = Integer.parseInt(request.getParameter("animeId"));
		
		FavoriteDAO dao = new FavoriteDAO();
		
		dao.deleteFavorite(userId, animeId);
		
	}

}
