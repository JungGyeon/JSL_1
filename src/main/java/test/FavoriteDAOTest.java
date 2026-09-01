package test;

import java.util.List;

import model.AnimeDAO;
import model.AnimeDTO;
import model.FavoriteDAO;

public class FavoriteDAOTest {

	public static void main(String[] args) {
		
		/*
		 * FavoriteDAO dao = new FavoriteDAO();
		 * 
		 * List<AnimeDTO> list = dao.getFavoriteList("user01");
		 * 
		 * for (AnimeDTO anime : list) {
		 * 
		 * System.out.println("애니 ID : " + anime.getAnimeId());
		 * System.out.println("제목 : " + anime.getTitle()); System.out.println("타입 : " +
		 * anime.getType()); System.out.println("점수 : " + anime.getScore());
		 * 
		 * }
		 */
		

		// 1. DAO 객체 생성
		AnimeDAO dao = new AnimeDAO();

		// 2. 메서드 호출
		List<AnimeDTO> list = dao.getAnimeList();

		// 3. 결과 검증
		if (list == null || list.isEmpty()) {
			System.out.println("❌ 데이터가 없습니다. DB 연결 상태나 ANIME 테이블의 데이터 존재 여부를 확인하세요.");
		} else {
			System.out.println("✅ 조회 성공! 총 " + list.size() + "개의 애니메이션을 가져왔습니다.\n");

			// 상위 3개 데이터만 출력해보기
			for (int i = 0; i < Math.min(list.size(), 3); i++) {
				AnimeDTO anime = list.get(i);
				System.out.println(
						"ID: " + anime.getAnimeId() + " | 제목: " + anime.getTitle() + " | 평점: " + anime.getScore());
			}
		}
	}
}