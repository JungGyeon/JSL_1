<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="model.BoardDTO"%>
<%@ page import="java.util.List"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<%
request.setAttribute("activePage", "board");
%>

<!DOCTYPE html>
<html lang="ja">

<head>

<meta charset="UTF-8">

<meta name="viewport"
    content="width=device-width, initial-scale=1.0">

<title>掲示板 — ANIVERSE</title>

<link
    href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/css/bootstrap.min.css"
    rel="stylesheet">

<link
    href="https://fonts.googleapis.com/css2?family=Zen+Kaku+Gothic+New:wght@400;500;700;900&family=Noto+Sans+JP:wght@400;500;700&family=JetBrains+Mono:wght@400;600&display=swap"
    rel="stylesheet">

<link
    href="${pageContext.request.contextPath}/css/style.css"
    rel="stylesheet">

<style>

.board-section {
    min-height: 520px;
}

.board-table {
    width: 100%;
    table-layout: fixed;
    color: var(--text);
    margin-bottom: 0;
    border-collapse: collapse;
}

.board-table thead {
    height: 44px;
}

.board-table thead th {
    background: var(--surface-2);
    color: var(--text-muted);
    border-color: var(--border);
    font-weight: 500;
    font-size: 15px;
    height: 44px;
    padding: 10px 8px;
    text-align: center;
    vertical-align: middle;
    white-space: nowrap;
}

.board-table tbody tr {
    height: 42px;
}

.board-table tbody td {
    background: var(--surface);
    color: var(--text);
    border-color: var(--border);
    height: 42px;
    padding: 8px;
    font-size: 15px;
    line-height: 1.4;
    vertical-align: middle;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}

.board-table tbody tr:hover td {
    background: var(--surface-2);
}

.board-table .title-cell {
    text-align: left;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.board-table .author-cell {
    text-align: center;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.board-table .date-cell {
    text-align: center;
    white-space: nowrap;
    color: var(--text-muted);
}

.board-title {
    display: block;
    width: 100%;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    color: var(--text);
    text-decoration: none;
}

.board-title:hover {
    color: var(--accent-2);
}

.board-search {
    display: flex;
    align-items: center;
    gap: 5px;
    margin: 0;
}

.board-search select,
.board-search input {
    width: 100px;
    height: 36px;
    padding: 4px 8px;
    font-size: 14px;
    border-radius: 4px;
    background: var(--surface-2);
    color: var(--text);
    border: 1px solid var(--border);
}

.board-search input {
    width: 220px;
    padding: 4px 10px;
}

.board-search select:focus,
.board-search input:focus {
    background: var(--surface-2);
    color: var(--text);
    border-color: var(--accent-2-deep);
    box-shadow: 0 0 0 .2rem rgba(110, 231, 216, .12);
    outline: none;
}

.board-search input::placeholder {
    color: var(--text-muted);
}

.board-search button {
    width: 55px;
    height: 36px;
    padding: 0;
    font-size: 14px;
    border-radius: 4px;
}

.board-write-btn {
    height: 36px;
    padding: 0 14px;
    font-size: 14px;
    display: flex;
    align-items: center;
    justify-content: center;
    white-space: nowrap;
    border-radius: 4px;
}

.board-pagination {
    margin-top: 24px;
    display: flex;
    justify-content: center;
}

.board-pagination .pagination {
    margin-bottom: 0;
}

.board-pagination .page-link {
    background: var(--surface);
    color: var(--text);
    border-color: var(--border);
    min-width: 35px;
    height: 40px;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 0 10px;
}

.board-pagination .page-link:hover {
    background: var(--surface-2);
    color: var(--accent-2);
}

.board-pagination .page-item.active .page-link {
    background: var(--accent-2);
    border-color: var(--accent-2);
    color: #fff;
}

@media (max-width: 768px) {

    .board-section {
        min-height: 500px;
    }

    .board-search input {
        width: 160px;
    }

    .board-search select {
        width: 90px;
    }

    .board-search button {
        width: 50px;
    }

    .board-table thead th,
    .board-table tbody td {
        font-size: 13px;
        padding: 6px 4px;
    }

    .board-table tbody tr {
        height: 42px;
    }

    .board-table .author-cell {
        white-space: normal;
        word-break: keep-all;
    }

    .board-pagination {
        margin-top: 20px;
    }

}

</style>

</head>

<body>

<%@ include file="/common/header.jsp"%>

<section class="sub-hero">

    <div class="container">

        <h2 class="mb-0">掲示板</h2>

    </div>

</section>

<section class="section container board-section">

    <div class="d-flex justify-content-between align-items-center mb-3">

        <div>

            <span class="text-muted small">

                全

                <c:out value="${empty totalCount ? 0 : totalCount}" />

                件

            </span>

        </div>

        <div class="d-flex align-items-center gap-2">

            <form
                action="${pageContext.request.contextPath}/board/list.do"
                method="get"
                class="board-search">

                <select name="searchType" class="form-select">

                    <option value="title"
                        ${searchType == 'title' ? 'selected' : ''}>
                        タイトル
                    </option>

                    <option value="content"
                        ${searchType == 'content' ? 'selected' : ''}>
                        内容
                    </option>

                    <option value="writer"
                        ${searchType == 'writer' ? 'selected' : ''}>
                        作成者
                    </option>

                </select>

                <input
                    type="text"
                    name="keyword"
                    class="form-control"
                    placeholder="キーワードを入力"
                    value="<c:out value='${keyword}' />">

                <select name="sort" class="form-select">

                    <option value="latest"
                        ${sort == 'latest' ? 'selected' : ''}>
                        最新順
                    </option>

                    <option value="recommend"
                        ${sort == 'recommend' ? 'selected' : ''}>
                        おすすめ順
                    </option>

                    <option value="views"
                        ${sort == 'views' ? 'selected' : ''}>
                        閲覧数順
                    </option>

                </select>

                <button
                    type="submit"
                    class="btn btn-outline-soft">
                    検索
                </button>

            </form>

            <a
                class="btn btn-accent board-write-btn"
                href="${pageContext.request.contextPath}/board/write.do">
                投稿する
            </a>

        </div>

    </div>

    <c:choose>

        <c:when test="${empty boardList}">

            <div class="empty-state">

                投稿された記事がありません。

            </div>

        </c:when>

        <c:otherwise>

            <div class="surface p-0 overflow-hidden">

                <div class="table-responsive">

                    <table class="table board-table align-middle mb-0">

                        <colgroup>

                            <col style="width: 6%;">

                            <col style="width: 57%;">

                            <col style="width: 10%;">

                            <col style="width: 6%;">

                            <col style="width: 7%;">

                            <col style="width: 6%;">

                            <col style="width: 8%;">

                        </colgroup>

                        <thead>

                            <tr>

                                <th>番号</th>

                                <th>タイトル</th>

                                <th>作成者</th>

                                <th>いいね</th>

                                <th>わるいね</th>

                                <th>閲覧</th>

                                <th>作成日</th>

                            </tr>

                        </thead>

                        <tbody>

                            <c:forEach
                                var="board"
                                items="${boardList}">

                                <tr>

                                    <td class="text-center">

                                        <c:out value="${board.boardId}" />

                                    </td>

                                    <td class="title-cell">

                                        <a
                                            class="board-title"
                                            href="${pageContext.request.contextPath}/board/detail.do?boardId=${board.boardId}"
                                            title="<c:out value='${board.title}' />">

                                            <c:out value="${board.title}" />

                                        </a>

                                    </td>

                                    <td class="author-cell">

                                        <c:out value="${board.nickname}" />

                                    </td>

                                    <td class="text-center">

                                        <c:out value="${board.likeCount}" />

                                    </td>

                                    <td class="text-center">

                                        <c:out value="${board.dislikeCount}" />

                                    </td>

                                    <td class="text-center">

                                        <c:out value="${board.viewCount}" />

                                    </td>

                                    <td class="date-cell">

                                        <c:out value="${board.regDate}" />

                                    </td>

                                </tr>

                            </c:forEach>

                        </tbody>

                    </table>

                </div>

            </div>

            <c:if test="${totalPage > 1}">

                <div class="board-pagination">

                    <ul class="pagination">

                        <c:if test="${currentPage > 1}">

                            <li class="page-item">

                                <a
                                    class="page-link"
                                    href="${pageContext.request.contextPath}/board/list.do?page=${currentPage - 1}&searchType=${searchType}&keyword=${keyword}&sort=${sort}">

                                    &laquo;

                                </a>

                            </li>

                        </c:if>

                        <c:forEach
                            var="pageNum"
                            begin="1"
                            end="${totalPage}">

                            <li
                                class="page-item ${pageNum == currentPage ? 'active' : ''}">

                                <a
                                    class="page-link"
                                    href="${pageContext.request.contextPath}/board/list.do?page=${pageNum}&searchType=${searchType}&keyword=${keyword}&sort=${sort}">

                                    ${pageNum}

                                </a>

                            </li>

                        </c:forEach>

                        <c:if test="${currentPage < totalPage}">

                            <li class="page-item">

                                <a
                                    class="page-link"
                                    href="${pageContext.request.contextPath}/board/list.do?page=${currentPage + 1}&searchType=${searchType}&keyword=${keyword}&sort=${sort}">

                                    &raquo;

                                </a>

                            </li>

                        </c:if>

                    </ul>

                </div>

            </c:if>

        </c:otherwise>

    </c:choose>

</section>

<%@ include file="/common/footer.jsp"%>

</body>

</html>