package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.BoardDAO;
import model.BoardDTO;

public class BoardUpdateService implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 게시글 번호 가져오기
		String boardIdStr = request.getParameter("boardId");
		int boardId = Integer.parseInt(boardIdStr);

		// 로그인한 사용자 ID 가져오기
		HttpSession session = request.getSession(false);

		if (session == null) {
			response.sendRedirect(request.getContextPath() + "/member/loginForm.do");
			return;
		}

		String userid = (String) session.getAttribute("userid");

		if (userid == null) {
			response.sendRedirect(request.getContextPath() + "/member/loginForm.do");
			return;
		}

		BoardDAO dao = new BoardDAO();

		// GET → 수정 화면
		if ("GET".equals(request.getMethod())) {

			BoardDTO dto = dao.getBoardDetail(boardId);

			// 게시글이 없는 경우
			if (dto == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}

			// 작성자 확인
			if (!userid.equals(dto.getUserId())) {
				request.setAttribute("message", "作成者本人のみ記事を編集できます。");
				request.getRequestDispatcher("/views/board/error.jsp").forward(request, response);
				return;
			}

			request.setAttribute("board", dto);
		}

		// POST → 수정 처리
		else if ("POST".equals(request.getMethod())) {

			// 먼저 기존 게시글을 가져온다.
			BoardDTO board = dao.getBoardDetail(boardId);

			// 게시글이 없는 경우
			if (board == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}

			// 작성자 확인
			if (!userid.equals(board.getUserId())) {
				request.setAttribute("message", "作成者本人のみ記事を編集できます。");
				request.getRequestDispatcher("/views/board/error.jsp").forward(request, response);
				return;
			}

			String title = request.getParameter("title");
			String content = request.getParameter("content");

			BoardDTO dto = new BoardDTO();

			dto.setBoardId(boardId);
			dto.setTitle(title);
			dto.setContent(content);

			dao.updateBoard(dto);
		}
	}
}