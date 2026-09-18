package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import util.DBManager;

public class BoardReactionDAO {

	// リアクションを保存・変更・取消
	public int saveReaction(int boardId, String userId, String reactionType) {

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		int result = 0;

		try {

			conn = DBManager.getInstance();

			// 現在のリアクションを確認
			String checkSql = "SELECT REACTION_TYPE "
					+ "FROM BOARD_REACTION "
					+ "WHERE BOARD_ID = ? "
					+ "AND USER_ID = ?";

			pstm = conn.prepareStatement(checkSql);

			pstm.setInt(1, boardId);
			pstm.setString(2, userId);

			rs = pstm.executeQuery();

			String currentReaction = null;

			if (rs.next()) {
				currentReaction = rs.getString("REACTION_TYPE");
			}

			rs.close();
			pstm.close();


			// 같은 리액션을 다시 누른 경우 → 취소
			if (reactionType.equals(currentReaction)) {

				String deleteSql = "DELETE FROM BOARD_REACTION "
						+ "WHERE BOARD_ID = ? "
						+ "AND USER_ID = ?";

				pstm = conn.prepareStatement(deleteSql);

				pstm.setInt(1, boardId);
				pstm.setString(2, userId);

				result = pstm.executeUpdate();

			}

			// 기존 리액션이 없거나 다른 리액션을 누른 경우
			else {

				String mergeSql = "MERGE INTO BOARD_REACTION br "
						+ "USING (SELECT ? AS BOARD_ID, ? AS USER_ID, ? AS REACTION_TYPE FROM DUAL) temp "
						+ "ON (br.BOARD_ID = temp.BOARD_ID AND br.USER_ID = temp.USER_ID) "
						+ "WHEN MATCHED THEN "
						+ "UPDATE SET br.REACTION_TYPE = temp.REACTION_TYPE "
						+ "WHEN NOT MATCHED THEN "
						+ "INSERT (REACTION_ID, BOARD_ID, USER_ID, REACTION_TYPE) "
						+ "VALUES (BOARD_REACTION_SEQ.NEXTVAL, temp.BOARD_ID, temp.USER_ID, temp.REACTION_TYPE)";

				pstm = conn.prepareStatement(mergeSql);

				pstm.setInt(1, boardId);
				pstm.setString(2, userId);
				pstm.setString(3, reactionType);

				result = pstm.executeUpdate();

			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			try {
				if (rs != null) rs.close();
			} catch (Exception e) {
			}

			try {
				if (pstm != null) pstm.close();
			} catch (Exception e) {
			}

		}

		return result;
	}


	// いいねの件数を取得
	public int getLikeCount(int boardId) {

		return getReactionCount(boardId, "LIKE");

	}


	// 微妙の件数を取得
	public int getMaybeCount(int boardId) {

		return getReactionCount(boardId, "MAYBE");

	}


	// よくないの件数を取得
	public int getDislikeCount(int boardId) {

		return getReactionCount(boardId, "DISLIKE");

	}


	// リアクションの件数を取得
	private int getReactionCount(int boardId, String reactionType) {

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		String sql = "SELECT COUNT(*) "
				+ "FROM BOARD_REACTION "
				+ "WHERE BOARD_ID = ? "
				+ "AND REACTION_TYPE = ?";

		int count = 0;

		try {

			conn = DBManager.getInstance();

			pstm = conn.prepareStatement(sql);

			pstm.setInt(1, boardId);
			pstm.setString(2, reactionType);

			rs = pstm.executeQuery();

			if (rs.next()) {
				count = rs.getInt(1);
			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			try {
				if (rs != null) rs.close();
			} catch (Exception e) {
			}

			try {
				if (pstm != null) pstm.close();
			} catch (Exception e) {
			}

		}

		return count;
	}

}