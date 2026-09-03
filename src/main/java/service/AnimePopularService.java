package service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.AnimeDAO;
import model.AnimeDTO;

public class AnimePopularService implements Command {

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        AnimeDAO dao = new AnimeDAO();

        List<AnimeDTO> list = dao.getPopularAnimeList();

        request.setAttribute("popularList", list);
    }
}