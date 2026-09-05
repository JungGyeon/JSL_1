package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import util.DBManager;

public class FavoriteDAO {

	public void addFavorite(FavoriteDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "INSERT INTO FAVORITE (USER_ID, ANIME_ID) VALUES (?, ?)";

		try {

			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getUserId());
			pstmt.setInt(2, dto.getAnimeId());

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(pstmt, conn);
		}
	}

	public void deleteFavorite(String userId, int animeId) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "DELETE FROM FAVORITE WHERE USER_ID = ? AND ANIME_ID = ?";

		try {

			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);
			pstmt.setInt(2, animeId);

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(pstmt, conn);
		}
	}

	public List<AnimeDTO> getFavoriteList(String userId) {

		List<AnimeDTO> list = new ArrayList<AnimeDTO>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT A.* "
				+ "FROM FAVORITE F "
				+ "JOIN ANIME A "
				+ "ON F.ANIME_ID = A.ANIME_ID "
				+ "WHERE F.USER_ID = ?";

		try {

			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				AnimeDTO dto = new AnimeDTO();

				dto.setAnimeId(rs.getInt("anime_id"));
				dto.setTitle(rs.getString("title"));
				dto.setType(rs.getString("type"));
				dto.setEpisodes(rs.getInt("episodes"));
				dto.setStatus(rs.getString("status"));
				dto.setSeason(rs.getString("season"));
				dto.setYear(rs.getInt("year"));
				dto.setPicture(rs.getString("picture"));
				dto.setThumbnail(rs.getString("thumbnail"));
				dto.setScore(rs.getDouble("score"));
				dto.setDurationValue(rs.getInt("duration_value"));
				dto.setDurationUnit(rs.getString("duration_unit"));

				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(rs, pstmt, conn);
		}

		return list;
	}
	
	public boolean isFavorite(String userId, int animeId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT 1 FROM FAVORITE WHERE USER_ID = ? AND ANIME_ID = ?";

		boolean result = false;

		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);
			pstmt.setInt(2, animeId);

			rs = pstmt.executeQuery();
			result = rs.next();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(rs, pstmt, conn);
		}

		return result;
	}

	public Set<Integer> getFavoriteAnimeIds(String userId) {
		Set<Integer> ids = new HashSet<Integer>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT ANIME_ID FROM FAVORITE WHERE USER_ID = ?";

		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ids.add(rs.getInt("anime_id"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(rs, pstmt, conn);
		}

		return ids;
	}
	
}
