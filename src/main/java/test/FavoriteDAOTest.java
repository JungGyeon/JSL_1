package test;

import java.util.List;

import model.AnimeDTO;
import model.FavoriteDAO;
import model.FavoriteDTO;

public class FavoriteDAOTest {

	public static void main(String[] args) {

	    FavoriteDAO dao = new FavoriteDAO();

	    List<AnimeDTO> list = dao.getFavoriteList("user01");

	    for (AnimeDTO anime : list) {

	        System.out.println("애니 ID : " + anime.getAnimeId());
	        System.out.println("제목 : " + anime.getTitle());
	        System.out.println("타입 : " + anime.getType());
	        System.out.println("점수 : " + anime.getScore());
	        
	        
	        // pr test ing,,,
	    }
	}
}