package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.BoardDAO;
import model.BoardDTO;
import model.CommentDAO;

public class BoardDeleteService implements Command {

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 게시글 번호 가져오기
        String boardIdStr = request.getParameter("boardId");

        int boardId = Integer.parseInt(boardIdStr);

        // 로그인한 사용자 ID 가져오기
        HttpSession session = request.getSession(false);

        if (session == null) {

            response.sendRedirect(
                request.getContextPath() + "/member/loginForm.do"
            );

            return;
        }

        String userid = (String) session.getAttribute("userid");

        if (userid == null) {

            response.sendRedirect(
                request.getContextPath() + "/member/loginForm.do"
            );

            return;
        }

        BoardDAO dao = new BoardDAO();

        // 기존 게시글 가져오기
        BoardDTO board = dao.getBoardDetail(boardId);

        // 게시글이 없는 경우
        if (board == null) {

            response.sendError(HttpServletResponse.SC_NOT_FOUND);

            return;
        }

        // 작성자 확인
        if (!userid.equals(board.getUserId())) {

            request.setAttribute(
                "message",
                "作成者本人のみ記事を削除できます。"
            );

            request.getRequestDispatcher(
                "/views/board/error.jsp"
            ).forward(request, response);

            return;
        }

        // 댓글 DAO 생성
        CommentDAO commentDao = new CommentDAO();

        // 게시글에 달린 댓글 먼저 삭제
        commentDao.deleteCommentsByBoardId(boardId);

        // 게시글 삭제
        int result = dao.deleteBoard(boardId);

        System.out.println("게시글 삭제 결과 = " + result);
    }
}