package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;

public class BoardDAO { // 게시글 목록

	public List<BoardDTO> getBoardList() {

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM BOARD ORDER BY BOARD_ID DESC";

		List<BoardDTO> list = new ArrayList<BoardDTO>();

		try {

			conn = DBManager.getInstance();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();

			while (rs.next()) {

				BoardDTO dto = new BoardDTO();

				dto.setBoardId(rs.getInt("BOARD_ID"));
				dto.setUserId(rs.getString("USER_ID"));
				dto.setNickname(rs.getString("NICKNAME"));
				dto.setTitle(rs.getString("TITLE"));
				dto.setContent(rs.getString("CONTENT"));
				dto.setRegDate(rs.getDate("REG_DATE"));
				dto.setUpdateDate(rs.getDate("UPDATE_DATE"));
				dto.setViewCount(rs.getInt("VIEW_COUNT"));

				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}