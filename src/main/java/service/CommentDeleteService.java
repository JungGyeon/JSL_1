package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.CommentDAO;
import model.CommentDTO;

public class CommentDeleteService implements Command {

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 로그인 여부 확인
        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect(
                    request.getContextPath() + "/member/loginForm.do");
            return;
        }

        // 로그인한 사용자 ID
        String userid = (String) session.getAttribute("userid");

        if (userid == null) {
            response.sendRedirect(
                    request.getContextPath() + "/member/loginForm.do");
            return;
        }

        // 삭제할 댓글 번호 가져오기
        String commentIdStr = request.getParameter("commentId");

        int commentId = Integer.parseInt(commentIdStr);

        // 댓글 조회
        CommentDAO dao = new CommentDAO();

        CommentDTO comment = dao.getComment(commentId);

        // 댓글이 존재하지 않는 경우
        if (comment == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 댓글 작성자와 현재 로그인한 사용자 비교
        if (!userid.equals(comment.getUserId())) {

            // 본인 댓글이 아니면 삭제 불가능
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

     // 본인 댓글이면 삭제
        int result = dao.deleteComment(commentId);

        System.out.println("댓글 삭제 결과 = " + result);
    }
}