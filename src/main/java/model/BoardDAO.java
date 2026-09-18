package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;

public class BoardDAO {

    // 게시글 목록 조회
    public List<BoardDTO> getBoardList(String sort) {

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        String orderBy = getOrderBy(sort);

        String sql = "SELECT b.*, "
                + "(SELECT COUNT(*) "
                + "FROM BOARD_REACTION r "
                + "WHERE r.BOARD_ID = b.BOARD_ID "
                + "AND r.REACTION_TYPE = 'LIKE') AS LIKE_COUNT, "
                + "(SELECT COUNT(*) "
                + "FROM BOARD_REACTION r "
                + "WHERE r.BOARD_ID = b.BOARD_ID "
                + "AND r.REACTION_TYPE = 'DISLIKE') AS DISLIKE_COUNT "
                + "FROM BOARD b "
                + "ORDER BY " + orderBy;

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

                dto.setLikeCount(rs.getInt("LIKE_COUNT"));
                dto.setDislikeCount(rs.getInt("DISLIKE_COUNT"));

                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            DBManager.close(rs, pstm, conn);
        }

        return list;
    }


    // 게시글 검색
    public List<BoardDTO> searchBoard(String searchType, String keyword, String sort) {

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        String column = "TITLE";

        if ("content".equals(searchType)) {
            column = "CONTENT";
        } else if ("writer".equals(searchType)) {
            column = "NICKNAME";
        }

        String orderBy = getOrderBy(sort);

        String sql = "SELECT b.*, "
                + "(SELECT COUNT(*) "
                + "FROM BOARD_REACTION r "
                + "WHERE r.BOARD_ID = b.BOARD_ID "
                + "AND r.REACTION_TYPE = 'LIKE') AS LIKE_COUNT, "
                + "(SELECT COUNT(*) "
                + "FROM BOARD_REACTION r "
                + "WHERE r.BOARD_ID = b.BOARD_ID "
                + "AND r.REACTION_TYPE = 'DISLIKE') AS DISLIKE_COUNT "
                + "FROM BOARD b "
                + "WHERE " + column + " LIKE ? "
                + "ORDER BY " + orderBy;

        List<BoardDTO> list = new ArrayList<BoardDTO>();

        try {

            conn = DBManager.getInstance();
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, "%" + keyword + "%");

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

                dto.setLikeCount(rs.getInt("LIKE_COUNT"));
                dto.setDislikeCount(rs.getInt("DISLIKE_COUNT"));

                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            DBManager.close(rs, pstm, conn);
        }

        return list;
    }


    // 게시글 목록 페이징
    public List<BoardDTO> getBoardListPaging(String sort, int startRow, int pageSize) {

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        String orderBy = getOrderBy(sort);

        String sql =
                "SELECT * "
              + "FROM ("
              + "    SELECT row_.*, ROWNUM rn "
              + "    FROM ("
              + "        SELECT b.*, "
              + "        (SELECT COUNT(*) "
              + "         FROM BOARD_REACTION r "
              + "         WHERE r.BOARD_ID = b.BOARD_ID "
              + "         AND r.REACTION_TYPE = 'LIKE') AS LIKE_COUNT, "
              + "        (SELECT COUNT(*) "
              + "         FROM BOARD_REACTION r "
              + "         WHERE r.BOARD_ID = b.BOARD_ID "
              + "         AND r.REACTION_TYPE = 'DISLIKE') AS DISLIKE_COUNT "
              + "        FROM BOARD b "
              + "        ORDER BY " + orderBy
              + "    ) row_ "
              + "    WHERE ROWNUM <= ? "
              + ") "
              + "WHERE rn > ?";

        List<BoardDTO> list = new ArrayList<BoardDTO>();

        try {

            conn = DBManager.getInstance();

            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, startRow + pageSize);
            pstm.setInt(2, startRow);

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
                dto.setLikeCount(rs.getInt("LIKE_COUNT"));
                dto.setDislikeCount(rs.getInt("DISLIKE_COUNT"));

                list.add(dto);
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            DBManager.close(rs, pstm, conn);

        }

        return list;
    }


    // 검색 + 페이징
    public List<BoardDTO> searchBoardPaging(
            String searchType,
            String keyword,
            String sort,
            int startRow,
            int pageSize) {

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        String column = "TITLE";

        if ("content".equals(searchType)) {
            column = "CONTENT";
        } else if ("writer".equals(searchType)) {
            column = "NICKNAME";
        }

        String orderBy = getOrderBy(sort);

        String sql =
                "SELECT * "
              + "FROM ("
              + "    SELECT row_.*, ROWNUM rn "
              + "    FROM ("
              + "        SELECT b.*, "
              + "        (SELECT COUNT(*) "
              + "         FROM BOARD_REACTION r "
              + "         WHERE r.BOARD_ID = b.BOARD_ID "
              + "         AND r.REACTION_TYPE = 'LIKE') AS LIKE_COUNT, "
              + "        (SELECT COUNT(*) "
              + "         FROM BOARD_REACTION r "
              + "         WHERE r.BOARD_ID = b.BOARD_ID "
              + "         AND r.REACTION_TYPE = 'DISLIKE') AS DISLIKE_COUNT "
              + "        FROM BOARD b "
              + "        WHERE " + column + " LIKE ? "
              + "        ORDER BY " + orderBy
              + "    ) row_ "
              + "    WHERE ROWNUM <= ? "
              + ") "
              + "WHERE rn > ?";

        List<BoardDTO> list = new ArrayList<BoardDTO>();

        try {

            conn = DBManager.getInstance();

            pstm = conn.prepareStatement(sql);

            pstm.setString(1, "%" + keyword + "%");
            pstm.setInt(2, startRow + pageSize);
            pstm.setInt(3, startRow);

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
                dto.setLikeCount(rs.getInt("LIKE_COUNT"));
                dto.setDislikeCount(rs.getInt("DISLIKE_COUNT"));

                list.add(dto);
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            DBManager.close(rs, pstm, conn);

        }

        return list;
    }


    // 전체 게시글 수
    public int getBoardCount() {

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        String sql = "SELECT COUNT(*) FROM BOARD";

        int count = 0;

        try {

            conn = DBManager.getInstance();
            pstm = conn.prepareStatement(sql);

            rs = pstm.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            DBManager.close(rs, pstm, conn);
        }

        return count;
    }


    // 검색 결과 게시글 수
    public int getSearchBoardCount(
            String searchType,
            String keyword) {

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        String column = "TITLE";

        if ("content".equals(searchType)) {
            column = "CONTENT";
        } else if ("writer".equals(searchType)) {
            column = "NICKNAME";
        }

        String sql = "SELECT COUNT(*) "
                + "FROM BOARD "
                + "WHERE " + column + " LIKE ?";

        int count = 0;

        try {

            conn = DBManager.getInstance();
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, "%" + keyword + "%");

            rs = pstm.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            DBManager.close(rs, pstm, conn);
        }

        return count;
    }


    // 정렬 조건
    private String getOrderBy(String sort) {

        // 추천순
        if ("recommend".equals(sort)) {

            return "LIKE_COUNT DESC, BOARD_ID DESC";

        // 조회수순
        } else if ("views".equals(sort)) {

            return "VIEW_COUNT DESC, BOARD_ID DESC";

        // 최신순
        } else {

            return "BOARD_ID DESC";
        }
    }


    // 게시글 작성
    public int insertBoard(BoardDTO dto) {

        Connection conn = null;
        PreparedStatement pstm = null;

        String sql = "INSERT INTO BOARD "
                + "(BOARD_ID, USER_ID, NICKNAME, TITLE, CONTENT) "
                + "VALUES (BOARD_SEQ.NEXTVAL, ?, ?, ?, ?)";

        int result = 0;

        try {

            conn = DBManager.getInstance();
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, dto.getUserId());
            pstm.setString(2, dto.getNickname());
            pstm.setString(3, dto.getTitle());
            pstm.setString(4, dto.getContent());

            result = pstm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            DBManager.close(pstm, conn);
        }

        return result;
    }


    // 게시글 상세보기
    public BoardDTO getBoardDetail(int boardId) {

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM BOARD WHERE BOARD_ID = ?";

        BoardDTO dto = null;

        try {

            conn = DBManager.getInstance();
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, boardId);

            rs = pstm.executeQuery();

            if (rs.next()) {

                dto = new BoardDTO();

                dto.setBoardId(rs.getInt("BOARD_ID"));
                dto.setUserId(rs.getString("USER_ID"));
                dto.setNickname(rs.getString("NICKNAME"));
                dto.setTitle(rs.getString("TITLE"));
                dto.setContent(rs.getString("CONTENT"));
                dto.setRegDate(rs.getDate("REG_DATE"));
                dto.setUpdateDate(rs.getDate("UPDATE_DATE"));
                dto.setViewCount(rs.getInt("VIEW_COUNT"));
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            DBManager.close(rs, pstm, conn);
        }

        return dto;
    }


    // 조회수 증가
    public int increaseViewCount(int boardId) {

        Connection conn = null;
        PreparedStatement pstm = null;

        String sql = "UPDATE BOARD "
                + "SET VIEW_COUNT = VIEW_COUNT + 1 "
                + "WHERE BOARD_ID = ?";

        int result = 0;

        try {

            conn = DBManager.getInstance();
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, boardId);

            result = pstm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    // 게시글 수정
    public int updateBoard(BoardDTO dto) {

        Connection conn = null;
        PreparedStatement pstm = null;

        String sql = "UPDATE BOARD "
                + "SET TITLE = ?, "
                + "CONTENT = ?, "
                + "UPDATE_DATE = SYSDATE "
                + "WHERE BOARD_ID = ?";

        int result = 0;

        try {

            conn = DBManager.getInstance();
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, dto.getTitle());
            pstm.setString(2, dto.getContent());
            pstm.setInt(3, dto.getBoardId());

            result = pstm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    // 게시글 삭제
    public int deleteBoard(int boardId) {

        Connection conn = null;
        PreparedStatement pstm = null;

        String sql = "DELETE FROM BOARD WHERE BOARD_ID = ?";

        int result = 0;

        try {

            conn = DBManager.getInstance();
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, boardId);

            result = pstm.executeUpdate();

            System.out.println("게시글 삭제 결과 = " + result);

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            DBManager.close(pstm, conn);
        }

        return result;
    }

}