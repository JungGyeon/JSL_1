package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;

public class RecommendDAO {
	
	public List<AnimeDTO> getRecommendList(String userId) {
		List<AnimeDTO> list = new ArrayList<AnimeDTO>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT\n"
				+ "    A.ANIME_ID,\n"
				+ "    A.TITLE,\n"
				+ "    A.SCORE,\n"
				+ "    A.THUMBNAIL,\n"
				+ "    A.TYPE,\n"
				+ "    A.YEAR,\n"
				+ "    A.EPISODES,\n"
				+ "    COUNT(*) AS MATCH_COUNT\n"
				+ "FROM ANIME A\n"
				+ "JOIN ANIME_TAG AT\n"
				+ "    ON A.ANIME_ID = AT.ANIME_ID\n"
				+ "JOIN TAG T\n"
				+ "    ON AT.TAG_ID = T.TAG_ID\n"
				+ "WHERE T.TAG_ID IN (\n"
				+ "    SELECT AT2.TAG_ID\n"
				+ "    FROM FAVORITE F\n"
				+ "    JOIN ANIME_TAG AT2\n"
				+ "        ON F.ANIME_ID = AT2.ANIME_ID\n"
				+ "    WHERE F.USER_ID = ?\n"
				+ ")\n"
				+ "AND NOT EXISTS (\n"
				+ "    SELECT 1\n"
				+ "    FROM FAVORITE FV\n"
				+ "    WHERE FV.USER_ID = ?\n"
				+ "      AND FV.ANIME_ID = A.ANIME_ID\n"
				+ ")\n"
				+ "GROUP BY A.ANIME_ID, A.TITLE, A.SCORE, A.THUMBNAIL, A.TYPE, A.YEAR, A.EPISODES\n"
				+ "ORDER BY MATCH_COUNT DESC, A.SCORE DESC\n";

		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);
			pstmt.setString(2, userId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				AnimeDTO dto = new AnimeDTO();

				dto.setAnimeId(rs.getInt("anime_id"));
				dto.setTitle(rs.getString("title"));
				dto.setScore(rs.getDouble("score"));
				dto.setThumbnail(rs.getString("thumbnail"));
				dto.setType(rs.getString("type"));
				dto.setYear(rs.getInt("year"));
				dto.setEpisodes(rs.getInt("episodes"));
				dto.setMatchCount(rs.getInt("match_count"));

				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(rs, pstmt, conn);
		}

		return list;
	}
}
