package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.CommentDAO;
import model.CommentDTO;

public class CommentUpdateService implements Command {

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 로그인 확인
        HttpSession session = request.getSession(false);

        if (session == null) {

            response.sendRedirect(
                request.getContextPath() + "/member/loginForm.do"
            );

            return;
        }

        // 로그인한 사용자 ID
        String userid = (String) session.getAttribute("userid");

        if (userid == null) {

            response.sendRedirect(
                request.getContextPath() + "/member/loginForm.do"
            );

            return;
        }

        // 댓글 번호 가져오기
        String commentIdStr = request.getParameter("commentId");

        int commentId = Integer.parseInt(commentIdStr);

        // 수정할 내용 가져오기
        String content = request.getParameter("content");

        CommentDAO dao = new CommentDAO();

        // 기존 댓글 가져오기
        CommentDTO comment = dao.getComment(commentId);

        // 댓글이 없는 경우
        if (comment == null) {

            response.sendError(HttpServletResponse.SC_NOT_FOUND);

            return;
        }

        // 댓글 작성자 확인
        if (!userid.equals(comment.getUserId())) {

            response.sendError(HttpServletResponse.SC_FORBIDDEN);

            return;
        }

        // 수정할 내용 설정
        comment.setContent(content);

        // 댓글 수정
        int result = dao.updateComment(comment);

        System.out.println("댓글 수정 결과 = " + result);
    }
}