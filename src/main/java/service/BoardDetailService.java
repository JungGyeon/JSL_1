package service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BoardDAO;
import model.BoardDTO;
import model.BoardReactionDAO;
import model.CommentDAO;
import model.CommentDTO;

public class BoardDetailService implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");

		// URL에서 게시글 번호 가져오기
		String boardIdStr = request.getParameter("boardId");
		int boardId = Integer.parseInt(boardIdStr);

		// BoardDAO 생성
		BoardDAO dao = new BoardDAO();

		// 조회수 증가
		dao.increaseViewCount(boardId);

		// 게시글 하나 가져오기
		BoardDTO dto = dao.getBoardDetail(boardId);

		// JSP에서 사용할 수 있도록 저장
		System.out.println("① board 조회 완료");
		System.out.println("board = " + dto);

		request.setAttribute("board", dto);


		// =========================
		// 댓글 조회
		// =========================

		CommentDAO commentDao = new CommentDAO();

		System.out.println("② CommentDAO 생성 완료");

		List<CommentDTO> commentList = commentDao.getCommentList(boardId);

		System.out.println("③ 댓글 조회 완료");
		System.out.println("commentList = " + commentList);

		request.setAttribute("commentList", commentList);


		// =========================
		// リアクション数を取得
		// =========================

		BoardReactionDAO reactionDao = new BoardReactionDAO();

		int likeCount = reactionDao.getLikeCount(boardId);
		int maybeCount = reactionDao.getMaybeCount(boardId);
		int dislikeCount = reactionDao.getDislikeCount(boardId);

		// JSPで使用できるように保存
		request.setAttribute("likeCount", likeCount);
		request.setAttribute("maybeCount", maybeCount);
		request.setAttribute("dislikeCount", dislikeCount);

		System.out.println("④ リアクション数の取得完了");
		System.out.println("いいね = " + likeCount);
		System.out.println("微妙 = " + maybeCount);
		System.out.println("よくない = " + dislikeCount);

		System.out.println("⑤ BoardDetailService終了");
	}

}