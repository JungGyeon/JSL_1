package model;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;

public class AnimeDAO {

// 애니메이션 전체 목록 조회
	public List<AnimeDTO> getAnimeList() {

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		String sql = "SELECT * " + "FROM ANIME " + "ORDER BY ANIME_ID";

		List<AnimeDTO> list = new ArrayList<AnimeDTO>();

		try {

			conn = DBManager.getInstance();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();

			while (rs.next()) {

				AnimeDTO dto = new AnimeDTO();

				dto.setAnimeId(rs.getInt("ANIME_ID"));
				dto.setTitle(rs.getString("TITLE"));
				dto.setType(rs.getString("TYPE"));
				dto.setEpisodes(rs.getInt("EPISODES"));
				dto.setStatus(rs.getString("STATUS"));
				dto.setSeason(rs.getString("SEASON"));
				dto.setYear(rs.getInt("YEAR"));
				dto.setPicture(rs.getString("PICTURE"));
				dto.setThumbnail(rs.getString("THUMBNAIL"));
				dto.setScore(rs.getDouble("SCORE"));
				dto.setDurationValue(rs.getInt("DURATION_VALUE"));
				dto.setDurationUnit(rs.getString("DURATION_UNIT"));

				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			DBManager.close(rs, pstm, conn);
		}
		return list;

	}

	// 애니메이션 검색
	public List<AnimeDTO> searchAnime(String title, String year, String sort) {
		if (title == null) {
			title = "";
		}
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		List<AnimeDTO> list = new ArrayList<AnimeDTO>();
		AnimeDTO dto = null;

		String sql = "SELECT * FROM ANIME WHERE TITLE LIKE ?";

		// 방송년도 선택
		if (year != null && !year.equals("")) {
			sql += " AND YEAR = ?";
		}

		// 정렬
		if ("score".equals(sort)) {
			sql += " ORDER BY SCORE DESC";
		} else if ("year".equals(sort)) {
			sql += " ORDER BY YEAR DESC";
		} else if ("title".equals(sort)) {
			sql += " ORDER BY TITLE ASC";
		} else {
			sql += " ORDER BY ANIME_ID";
		}

		try {

			conn = DBManager.getInstance();
			pstm = conn.prepareStatement(sql);

			int index = 1;

			pstm.setString(index++, "%" + title + "%");

			if (year != null && !year.equals("")) {
				pstm.setInt(index++, Integer.parseInt(year));
			}

			rs = pstm.executeQuery();

			while (rs.next()) {

				dto = new AnimeDTO();

				dto.setAnimeId(rs.getInt("ANIME_ID"));
				dto.setTitle(rs.getString("TITLE"));
				dto.setType(rs.getString("TYPE"));
				dto.setEpisodes(rs.getInt("EPISODES"));
				dto.setStatus(rs.getString("STATUS"));
				dto.setSeason(rs.getString("SEASON"));
				dto.setYear(rs.getInt("YEAR"));
				dto.setPicture(rs.getString("PICTURE"));
				dto.setThumbnail(rs.getString("THUMBNAIL"));
				dto.setScore(rs.getDouble("SCORE"));
				dto.setDurationValue(rs.getInt("DURATION_VALUE"));
				dto.setDurationUnit(rs.getString("DURATION_UNIT"));

				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public AnimeDTO getAnimeDetail(int animeId) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		AnimeDTO dto = null;

		String sql = "select * from ANIME where anime_id = ?";

		try {

			conn = DBManager.getInstance();
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, animeId);
			rs = pstm.executeQuery();

			while (rs.next()) {
				dto = new AnimeDTO();
				dto.setAnimeId(rs.getInt("ANIME_ID"));
				dto.setTitle(rs.getString("TITLE"));
				dto.setType(rs.getString("TYPE"));
				dto.setEpisodes(rs.getInt("EPISODES"));
				dto.setStatus(rs.getString("STATUS"));
				dto.setSeason(rs.getString("SEASON"));
				dto.setYear(rs.getInt("YEAR"));
				dto.setPicture(rs.getString("PICTURE"));
				dto.setThumbnail(rs.getString("THUMBNAIL"));
				dto.setScore(rs.getDouble("SCORE"));
				dto.setDurationValue(rs.getInt("DURATION_VALUE"));
				dto.setDurationUnit(rs.getString("DURATION_UNIT"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;

	}
	
	public List<AnimeDTO> getPopularAnimeList() {

	    Connection conn = null;
	    PreparedStatement pstm = null;
	    ResultSet rs = null;

	    List<AnimeDTO> list = new ArrayList<AnimeDTO>();

	    String sql =
	            "SELECT A.*, COUNT(F.USER_ID) AS FAVORITE_COUNT "
	          + "FROM ANIME A "
	          + "JOIN FAVORITE F "
	          + "ON A.ANIME_ID = F.ANIME_ID "
	          + "GROUP BY "
	          + "A.ANIME_ID, A.TITLE, A.TYPE, A.EPISODES, "
	          + "A.STATUS, A.SEASON, A.YEAR, A.PICTURE, "
	          + "A.THUMBNAIL, A.SCORE, A.DURATION_VALUE, A.DURATION_UNIT "
	          + "ORDER BY FAVORITE_COUNT DESC, A.SCORE DESC, A.ANIME_ID";

	    try {

	        conn = DBManager.getInstance();
	        pstm = conn.prepareStatement(sql);
	        rs = pstm.executeQuery();

	        while (rs.next()) {

	            AnimeDTO dto = new AnimeDTO();

	            dto.setAnimeId(rs.getInt("ANIME_ID"));
	            dto.setTitle(rs.getString("TITLE"));
	            dto.setType(rs.getString("TYPE"));
	            dto.setEpisodes(rs.getInt("EPISODES"));
	            dto.setStatus(rs.getString("STATUS"));
	            dto.setSeason(rs.getString("SEASON"));
	            dto.setYear(rs.getInt("YEAR"));
	            dto.setPicture(rs.getString("PICTURE"));
	            dto.setThumbnail(rs.getString("THUMBNAIL"));
	            dto.setScore(rs.getDouble("SCORE"));
	            dto.setDurationValue(rs.getInt("DURATION_VALUE"));
	            dto.setDurationUnit(rs.getString("DURATION_UNIT"));

	            // 찜 개수
	            dto.setFavoriteCount(rs.getInt("FAVORITE_COUNT"));

	            list.add(dto);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();

	    } finally {
	        DBManager.close(rs, pstm, conn);
	    }

	    return list;
	}




}