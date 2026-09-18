package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.BoardDeleteService;
import service.BoardDetailService;
import service.BoardListService;
import service.BoardReactionService;
import service.BoardUpdateService;
import service.BoardWriteService;
import service.CommentDeleteService;
import service.CommentUpdateService;
import service.CommentWriteService;

@WebServlet("/board/*")
public class BoardController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public BoardController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doAction(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doAction(request, response);
    }

    protected void doAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getPathInfo();

        System.out.println("action: " + action);

        if (action == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String page = null;

        switch (action) {

        // =====================================================
        // 게시글 목록
        // =====================================================
        case "/list.do":

            new BoardListService().doCommand(request, response);

            page = "/views/board/board.jsp";

            break;

        // =====================================================
        // 게시글 작성
        // =====================================================
        case "/write.do": {

            HttpSession session = request.getSession(false);

            // 로그인 여부 확인
            if (session == null || session.getAttribute("userid") == null) {

                response.sendRedirect(
                        request.getContextPath() + "/member/loginForm.do");

                return;
            }

            // GET 요청: 작성 화면 표시
            if ("GET".equalsIgnoreCase(request.getMethod())) {

                page = "/views/board/write.jsp";

            // POST 요청: 게시글 등록
            } else if ("POST".equalsIgnoreCase(request.getMethod())) {

                new BoardWriteService().doCommand(request, response);

                response.sendRedirect(
                        request.getContextPath() + "/board/list.do");

                return;
            }

            break;
        }

        // =====================================================
        // 댓글 작성
        // =====================================================
        case "/commentWrite.do": {

            HttpSession session = request.getSession(false);

            // 로그인 여부 확인
            if (session == null || session.getAttribute("userid") == null) {

                response.sendRedirect(
                        request.getContextPath() + "/member/loginForm.do");

                return;
            }

            if ("POST".equalsIgnoreCase(request.getMethod())) {

                new CommentWriteService().doCommand(request, response);

                if (response.isCommitted()) {
                    return;
                }

                response.sendRedirect(
                        request.getContextPath()
                                + "/board/detail.do?boardId="
                                + request.getParameter("boardId"));

                return;
            }

            break;
        }

        // =====================================================
        // 댓글 삭제
        // =====================================================
        case "/commentDelete.do": {

            HttpSession session = request.getSession(false);

            // 로그인 여부 확인
            if (session == null || session.getAttribute("userid") == null) {

                response.sendRedirect(
                        request.getContextPath() + "/member/loginForm.do");

                return;
            }

            new CommentDeleteService().doCommand(request, response);

            if (response.isCommitted()) {
                return;
            }

            response.sendRedirect(
                    request.getContextPath()
                            + "/board/detail.do?boardId="
                            + request.getParameter("boardId"));

            return;
        }

        // =====================================================
        // 댓글 수정
        // =====================================================
        case "/commentUpdate.do": {

            HttpSession session = request.getSession(false);

            // 로그인 여부 확인
            if (session == null || session.getAttribute("userid") == null) {

                response.sendRedirect(
                        request.getContextPath() + "/member/loginForm.do");

                return;
            }

            new CommentUpdateService().doCommand(request, response);

            if (response.isCommitted()) {
                return;
            }

            response.sendRedirect(
                    request.getContextPath()
                            + "/board/detail.do?boardId="
                            + request.getParameter("boardId"));

            return;
        }

        // =====================================================
        // 게시글 삭제
        // =====================================================
        case "/delete.do": {

            HttpSession session = request.getSession(false);

            // 로그인 여부 확인
            if (session == null || session.getAttribute("userid") == null) {

                response.sendRedirect(
                        request.getContextPath() + "/member/loginForm.do");

                return;
            }

            new BoardDeleteService().doCommand(request, response);

            // 작성자가 아닌 경우 Service에서 이미 응답 처리
            if (response.isCommitted()) {
                return;
            }

            // 삭제 완료 후 목록으로 이동
            response.sendRedirect(
                    request.getContextPath() + "/board/list.do");

            return;
        }

        // =====================================================
        // 게시글 수정
        // =====================================================
        case "/update.do": {

            HttpSession session = request.getSession(false);

            // 로그인 여부 확인
            if (session == null || session.getAttribute("userid") == null) {

                response.sendRedirect(
                        request.getContextPath() + "/member/loginForm.do");

                return;
            }

            // GET 요청: 수정 화면 표시
            if ("GET".equalsIgnoreCase(request.getMethod())) {

                new BoardUpdateService().doCommand(request, response);

                if (response.isCommitted()) {
                    return;
                }

                page = "/views/board/update.jsp";

            // POST 요청: 게시글 수정 처리
            } else if ("POST".equalsIgnoreCase(request.getMethod())) {

                new BoardUpdateService().doCommand(request, response);

                if (response.isCommitted()) {
                    return;
                }

                response.sendRedirect(
                        request.getContextPath()
                                + "/board/detail.do?boardId="
                                + request.getParameter("boardId"));

                return;
            }

            break;
        }

        // =====================================================
        // 게시글 상세보기
        // =====================================================
        case "/detail.do":

            System.out.println("A. detail case 시작");

            new BoardDetailService().doCommand(request, response);

            if (response.isCommitted()) {
                return;
            }

            System.out.println("B. BoardDetailService 실행 완료");

            page = "/views/board/detail.jsp";

            System.out.println("C. JSP 이동 전");

            break;

        // =====================================================
        // 좋아요 / 애매해요 / 싫어요
        // =====================================================
        case "/reaction.do": {

            HttpSession session = request.getSession(false);

            // 로그인 여부 확인
            if (session == null || session.getAttribute("userid") == null) {

                response.sendRedirect(
                        request.getContextPath() + "/member/loginForm.do");

                return;
            }

            if ("POST".equalsIgnoreCase(request.getMethod())) {

                new BoardReactionService().doCommand(request, response);

                return;
            }

            break;
        }

        default:

            response.sendError(HttpServletResponse.SC_NOT_FOUND);

            return;
        }

        // =====================================================
        // JSP 화면으로 이동
        // =====================================================
        if (page != null) {

            System.out.println("D. forward 시작");
            System.out.println("page = " + page);

            request.getRequestDispatcher(page).forward(request, response);

            System.out.println("E. forward 완료");
        }
    }
}