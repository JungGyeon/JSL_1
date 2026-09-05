package service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.AnimeDTO;
import model.FavoriteDAO;
import model.RecommendDAO;

public class RecommendService implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		String userId = (String) request.getSession().getAttribute("userid");

		Set<Integer> favIds = new FavoriteDAO().getFavoriteAnimeIds(userId);
		boolean hasFavorites = !favIds.isEmpty();
		request.setAttribute("hasFavorites", hasFavorites);
		request.setAttribute("favIds", favIds);

		List<AnimeDTO> recommendList = new RecommendDAO().getRecommendList(userId);
		request.setAttribute("recommendList", recommendList);
	}

}
