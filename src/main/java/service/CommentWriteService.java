package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.CommentDAO;
import model.CommentDTO;

public class CommentWriteService implements Command {

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");

        // 로그인한 사용자 정보 가져오기
        HttpSession session = request.getSession();

        String userId = (String) session.getAttribute("userid");
        String nickname = (String) session.getAttribute("nickname");

        // 댓글 정보 가져오기
        String boardIdStr = request.getParameter("boardId");
        String content = request.getParameter("content");

        int boardId = Integer.parseInt(boardIdStr);

        // DTO 생성
        CommentDTO dto = new CommentDTO();

        dto.setBoardId(boardId);
        dto.setUserId(userId);
        dto.setNickname(nickname);
        dto.setContent(content);

        // DAO를 통해 DB에 저장
        CommentDAO dao = new CommentDAO();

        dao.insertComment(dto);

        System.out.println("댓글 등록 완료");
    }
}
