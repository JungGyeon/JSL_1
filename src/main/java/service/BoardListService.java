package service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BoardDAO;
import model.BoardDTO;

public class BoardListService implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BoardDAO dao = new BoardDAO();

		// 검색 조건
		String searchType = request.getParameter("searchType");

		// 검색 키워드
		String keyword = request.getParameter("keyword");

		// 정렬 조건
		String sort = request.getParameter("sort");

		// 페이지 번호
		String pageParam = request.getParameter("page");

		// 검색 조건이 없으면 제목 검색
		if (searchType == null || searchType.trim().isEmpty()) {
			searchType = "title";
		}

		// 정렬 조건이 없으면 최신순
		if (sort == null || sort.trim().isEmpty()) {
			sort = "latest";
		}

		// 현재 페이지
		int page = 1;

		try {

			if (pageParam != null && !pageParam.trim().isEmpty()) {
				page = Integer.parseInt(pageParam);
			}

		} catch (NumberFormatException e) {

			page = 1;
		}

		// 잘못된 페이지 번호 방지
		if (page < 1) {
			page = 1;
		}

		// 한 페이지에 보여줄 게시글 수
		int pageSize = 10;

		// OFFSET 계산
		int startRow = (page - 1) * pageSize;

		List<BoardDTO> list;

		int totalCount;

		// 검색 키워드가 없는 경우
		if (keyword == null || keyword.trim().isEmpty()) {

			list = dao.getBoardListPaging(sort, startRow, pageSize);

			totalCount = dao.getBoardCount();

		} else {

			// 검색 + 정렬 + 페이징
			list = dao.searchBoardPaging(searchType, keyword, sort, startRow, pageSize);

			totalCount = dao.getSearchBoardCount(searchType, keyword);
		}

		request.setAttribute("boardList", list);

		request.setAttribute("currentPage", page);

		request.setAttribute("pageSize", pageSize);

		request.setAttribute("totalCount", totalCount);

		int totalPage = (totalCount + pageSize - 1) / pageSize;
		request.setAttribute("totalPage", totalPage);

		request.setAttribute("searchType", searchType);

		request.setAttribute("keyword", keyword);

		request.setAttribute("sort", sort);
	}
}