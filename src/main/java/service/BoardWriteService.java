package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.BoardDAO;
import model.BoardDTO;

public class BoardWriteService implements Command {

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");

        // 로그인한 사용자 정보 가져오기
        HttpSession session = request.getSession();

        String userId = (String) session.getAttribute("userid");
        String nickname = (String) session.getAttribute("nickname");

        // 작성한 게시글 정보 가져오기
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        // DTO 생성
        BoardDTO dto = new BoardDTO();

        dto.setUserId(userId);
        dto.setNickname(nickname);
        dto.setTitle(title);
        dto.setContent(content);

        // DAO를 통해 DB에 저장
        BoardDAO dao = new BoardDAO();

        int result = dao.insertBoard(dto);

        System.out.println("게시글 등록 결과: " + result);
    }
}