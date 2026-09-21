package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;

public class CommentDAO {

    // 댓글 작성
    public void insertComment(CommentDTO dto) {

        Connection conn = null;
        PreparedStatement pstm = null;

        String sql = "INSERT INTO BOARD_COMMENT "
                   + "(COMMENT_ID, BOARD_ID, USER_ID, NICKNAME, CONTENT, REG_DATE) "
                   + "VALUES (BOARD_COMMENT_SEQ.NEXTVAL, ?, ?, ?, ?, SYSDATE)";

        try {
            conn = DBManager.getInstance();
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, dto.getBoardId());
            pstm.setString(2, dto.getUserId());
            pstm.setString(3, dto.getNickname());
            pstm.setString(4, dto.getContent());

            pstm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            DBManager.close(null, pstm, conn);
        }
    }


    // 특정 게시글의 댓글 목록
    public List<CommentDTO> getCommentList(int boardId) {

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM BOARD_COMMENT "
                   + "WHERE BOARD_ID = ? "
                   + "ORDER BY COMMENT_ID ASC";

        List<CommentDTO> list = new ArrayList<CommentDTO>();

        try {
            conn = DBManager.getInstance();
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, boardId);

            rs = pstm.executeQuery();

            while (rs.next()) {

                CommentDTO dto = new CommentDTO();

                dto.setCommentId(rs.getInt("COMMENT_ID"));
                dto.setBoardId(rs.getInt("BOARD_ID"));
                dto.setUserId(rs.getString("USER_ID"));
                dto.setNickname(rs.getString("NICKNAME"));
                dto.setContent(rs.getString("CONTENT"));
                dto.setRegDate(rs.getDate("REG_DATE"));

                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            DBManager.close(rs, pstm, conn);
        }

        return list;
    }
    
 // 댓글 하나 조회
    public CommentDTO getComment(int commentId) {

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM BOARD_COMMENT "
                   + "WHERE COMMENT_ID = ?";

        CommentDTO dto = null;

        try {

            conn = DBManager.getInstance();
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, commentId);

            rs = pstm.executeQuery();

            if (rs.next()) {

                dto = new CommentDTO();

                dto.setCommentId(rs.getInt("COMMENT_ID"));
                dto.setBoardId(rs.getInt("BOARD_ID"));
                dto.setUserId(rs.getString("USER_ID"));
                dto.setNickname(rs.getString("NICKNAME"));
                dto.setContent(rs.getString("CONTENT"));
                dto.setRegDate(rs.getDate("REG_DATE"));
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            DBManager.close(rs, pstm, conn);
        }

        return dto;
    }
    
 // 댓글 수정
    public int updateComment(CommentDTO dto) {

        Connection conn = null;
        PreparedStatement pstm = null;

        String sql = "UPDATE BOARD_COMMENT "
                   + "SET CONTENT = ? "
                   + "WHERE COMMENT_ID = ?";

        int result = 0;

        try {

            conn = DBManager.getInstance();
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, dto.getContent());
            pstm.setInt(2, dto.getCommentId());

            result = pstm.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            DBManager.close(null, pstm, conn);
        }

        return result;
    }
    
 // 댓글 삭제
    public int deleteComment(int commentId) {

        Connection conn = null;
        PreparedStatement pstm = null;

        String sql = "DELETE FROM BOARD_COMMENT "
                   + "WHERE COMMENT_ID = ?";

        int result = 0;

        try {

            conn = DBManager.getInstance();
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, commentId);

            result = pstm.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            DBManager.close(null, pstm, conn);
        }

        return result;
    }
    
    public int deleteCommentsByBoardId(int boardId) {

        Connection conn = null;
        PreparedStatement pstm = null;

        String sql = "DELETE FROM BOARD_COMMENT WHERE BOARD_ID = ?";

        int result = 0;

        try {

            conn = DBManager.getInstance();

            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, boardId);

            result = pstm.executeUpdate();

            System.out.println("게시글의 댓글 삭제 결과 = " + result);

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            DBManager.close(null, pstm, conn);
        }

        return result;
    }
}