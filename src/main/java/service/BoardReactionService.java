package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.BoardReactionDAO;

public class BoardReactionService implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ログイン情報を取得
		HttpSession session = request.getSession(false);

		if (session == null) {
			response.sendRedirect(
					request.getContextPath() + "/member/loginForm.do");
			return;
		}

		String userId = (String) session.getAttribute("userid");

		if (userId == null) {
			response.sendRedirect(
					request.getContextPath() + "/member/loginForm.do");
			return;
		}

		// 게시글 번호 가져오기
		String boardIdStr = request.getParameter("boardId");

		// リアクションの種類を取得
		String reaction = request.getParameter("reaction");

		if (boardIdStr == null || reaction == null) {
			return;
		}

		int boardId = Integer.parseInt(boardIdStr);

		// リアクションの種類を確認
		if (!"LIKE".equals(reaction)
				&& !"MAYBE".equals(reaction)
				&& !"DISLIKE".equals(reaction)) {
			return;
		}

		BoardReactionDAO dao = new BoardReactionDAO();

		// リアクションを保存
		dao.saveReaction(boardId, userId, reaction);

		// リアクション後、詳細ページへ戻る
		response.sendRedirect(
				request.getContextPath()
				+ "/board/detail.do?boardId=" + boardId);
	}
}