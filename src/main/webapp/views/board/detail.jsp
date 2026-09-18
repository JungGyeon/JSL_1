<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ja">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>${board.title} — ANIVERSE</title>

    <link
        href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/css/bootstrap.min.css"
        rel="stylesheet">

    <link
        href="https://fonts.googleapis.com/css2?family=Zen+Kaku+Gothic+New:wght@400;500;700;900&family=Noto+Sans+JP:wght@400;500;700&family=JetBrains+Mono:wght@400;600&display=swap"
        rel="stylesheet">

    <link href="${pageContext.request.contextPath}/css/style.css"
        rel="stylesheet">

    <style>
        .board-detail {
            max-width: 900px;
            margin: 0 auto;
        }

        .detail-header {
            padding-bottom: 20px;
            border-bottom: 1px solid var(--border);
        }

        .detail-title {
            color: var(--text);
            font-size: 28px;
            font-weight: 700;
            line-height: 1.4;
            margin-bottom: 15px;
            word-break: break-word;
        }

        .detail-info {
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
            color: var(--text-muted);
            font-size: 14px;
        }

        .detail-info-item {
            display: flex;
            align-items: center;
            gap: 5px;
        }

        .detail-content {
            color: var(--text);
            font-size: 16px;
            line-height: 1.8;
            min-height: 300px;
            padding: 30px 5px;
            white-space: pre-wrap;
            word-break: break-word;
        }

        /* 리액션 영역 */
        .reaction-area {
            display: flex;
            justify-content: center;
            gap: 10px;
            padding: 25px 0;
            border-top: 1px solid var(--border);
            border-bottom: 1px solid var(--border);
        }

        .reaction-area form {
            margin: 0;
        }

        .reaction-btn {
            min-width: 110px;
            padding: 10px 18px;
            border: 1px solid var(--border);
            border-radius: 6px;
            font-weight: 600;
            transition: 0.2s;
            cursor: pointer;
        }

        .reaction-btn:hover {
            transform: translateY(-2px);
        }

        .reaction-count {
            margin-left: 5px;
            font-weight: 700;
        }

        /* 좋아요 */
        .like-btn {
            background-color: #e7f5ff;
            border-color: #4dabf7;
            color: #1971c2;
        }

        .like-btn:hover {
            background-color: #4dabf7;
            color: #ffffff;
        }

        /* 애매해요 */
        .maybe-btn {
            background-color: #fff9db;
            border-color: #ffc107;
            color: #b58105;
        }

        .maybe-btn:hover {
            background-color: #ffc107;
            color: #212529;
        }

        /* 싫어요 */
        .dislike-btn {
            background-color: #fff5f5;
            border-color: #ff8787;
            color: #c92a2a;
        }

        .dislike-btn:hover {
            background-color: #ff8787;
            color: #ffffff;
        }

        .comment-area {
            color: var(--text);
        }

        .detail-buttons {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 25px;
        }

        .detail-buttons-right {
            display: flex;
            gap: 8px;
        }

        @media (max-width: 576px) {
            .detail-title {
                font-size: 22px;
            }

            .detail-info {
                gap: 8px;
                font-size: 13px;
            }

            .detail-content {
                padding: 20px 0;
            }

            .reaction-area {
                flex-wrap: wrap;
            }

            .reaction-btn {
                min-width: 90px;
                padding: 9px 12px;
            }

            .detail-buttons {
                flex-direction: column;
                align-items: stretch;
                gap: 10px;
            }

            .detail-buttons-right {
                justify-content: flex-end;
            }
        }
    </style>
</head>

<body>

    <%@ include file="/common/header.jsp"%>

    <!-- 게시판 제목 -->
    <section class="sub-hero">
        <div class="container">
            <h2 class="mb-0">掲示板</h2>
        </div>
    </section>

    <!-- 게시글 상세 -->
    <section class="section container req-anchor">

        <span class="req-id">BOARD-003</span>

        <div class="board-detail">

            <div class="surface p-4">

                <!-- 게시글 제목 및 정보 -->
                <div class="detail-header">

                    <h1 class="detail-title">
                        ${board.title}
                    </h1>

                    <div class="detail-info">

                        <div class="detail-info-item">
                            <span>作成者</span>
                            <strong>${board.nickname}</strong>
                        </div>

                        <div class="detail-info-item">
                            <span>作成日</span>
                            <span>${board.regDate}</span>
                        </div>

                        <div class="detail-info-item">
                            <span>閲覧数</span>
                            <span>${board.viewCount}</span>
                        </div>

                    </div>
                </div>

                <!-- 게시글 내용 -->
                <div class="detail-content">${board.content}</div>

                <!-- 리액션 버튼 -->
                <div class="reaction-area">

                    <!-- 좋아요 -->
                    <form action="${pageContext.request.contextPath}/board/reaction.do"
                          method="post">

                        <input type="hidden"
                               name="boardId"
                               value="${board.boardId}">

                        <input type="hidden"
                               name="reaction"
                               value="LIKE">

                        <button type="submit"
                                class="reaction-btn like-btn">
                            👍 いいね
                            <span class="reaction-count">${likeCount}</span>
                        </button>

                    </form>

                    <!-- 애매해요 -->
                    <form action="${pageContext.request.contextPath}/board/reaction.do"
                          method="post">

                        <input type="hidden"
                               name="boardId"
                               value="${board.boardId}">

                        <input type="hidden"
                               name="reaction"
                               value="MAYBE">

                        <button type="submit"
                                class="reaction-btn maybe-btn">
                            🤔 微妙
                            <span class="reaction-count">${maybeCount}</span>
                        </button>

                    </form>

                    <!-- 싫어요 -->
                    <form action="${pageContext.request.contextPath}/board/reaction.do"
                          method="post">

                        <input type="hidden"
                               name="boardId"
                               value="${board.boardId}">

                        <input type="hidden"
                               name="reaction"
                               value="DISLIKE">

                        <button type="submit"
                                class="reaction-btn dislike-btn">
                            👎 よくない
                            <span class="reaction-count">${dislikeCount}</span>
                        </button>

                    </form>

                </div>

                <!-- 댓글 -->
                <div class="comment-area mt-4">

                    <h4 class="mb-3">コメント</h4>

                    <!-- 댓글 작성 -->
                    <c:choose>

                        <c:when test="${not empty sessionScope.userid}">

                            <form action="${pageContext.request.contextPath}/board/commentWrite.do"
                                  method="post">

                                <input type="hidden"
                                       name="boardId"
                                       value="${board.boardId}">

                                <div class="mb-2">
                                    <textarea name="content"
                                              class="form-control"
                                              rows="3"
                                              placeholder="コメントを入力してください。"
                                              required></textarea>
                                </div>

                                <div class="text-end">
                                    <button type="submit"
                                            class="btn btn-primary">
                                        コメントする
                                    </button>
                                </div>

                            </form>

                        </c:when>

                        <c:otherwise>

                            <div class="text-center text-muted py-3">
                                コメントを作成するにはログインが必要です。
                                <a href="${pageContext.request.contextPath}/member/loginForm.do">
                                    ログイン
                                </a>
                            </div>

                        </c:otherwise>

                    </c:choose>

                    <!-- 댓글 목록 -->
                    <div class="mt-4">

                        <c:forEach var="comment" items="${commentList}">

                            <div class="border-top py-3">

                                <!-- 댓글 작성자 및 날짜 -->
                                <div class="d-flex justify-content-between">

                                    <strong>${comment.nickname}</strong>

                                    <small class="text-muted">
                                        ${comment.regDate}
                                    </small>

                                </div>

                                <!-- 댓글 내용 -->
                                <div class="mt-2">
                                    ${comment.content}
                                </div>

                                <!-- 댓글 작성자 본인에게만 표시 -->
                                <c:if test="${sessionScope.userid eq comment.userId}">

                                    <div class="mt-2 text-end">

                                        <!-- 댓글 수정 -->
                                        <button type="button"
                                                class="btn btn-sm btn-primary"
                                                onclick="showCommentEdit('${comment.commentId}')">
                                            編集
                                        </button>

                                        <!-- 댓글 삭제 -->
                                        <a href="${pageContext.request.contextPath}/board/commentDelete.do?commentId=${comment.commentId}&boardId=${comment.boardId}"
                                           class="btn btn-sm btn-danger"
                                           onclick="return confirm('コメントを削除しますか？');">
                                            削除
                                        </a>

                                    </div>

                                    <!-- 댓글 수정 폼 -->
                                    <div id="commentEdit-${comment.commentId}"
                                         class="mt-3"
                                         style="display: none;">

                                        <form action="${pageContext.request.contextPath}/board/commentUpdate.do"
                                              method="post">

                                            <input type="hidden"
                                                   name="commentId"
                                                   value="${comment.commentId}">

                                            <input type="hidden"
                                                   name="boardId"
                                                   value="${comment.boardId}">

                                            <textarea name="content"
                                                      class="form-control"
                                                      rows="3"
                                                      required>${comment.content}</textarea>

                                            <div class="mt-2 text-end">

                                                <button type="submit"
                                                        class="btn btn-sm btn-success">
                                                    保存
                                                </button>

                                                <button type="button"
                                                        class="btn btn-sm btn-secondary"
                                                        onclick="hideCommentEdit('${comment.commentId}')">
                                                    キャンセル
                                                </button>

                                            </div>

                                        </form>

                                    </div>

                                </c:if>

                            </div>

                        </c:forEach>

                        <!-- 댓글이 없는 경우 -->
                        <c:if test="${empty commentList}">

                            <div class="text-center text-muted py-4">
                                コメントはありません。
                            </div>

                        </c:if>

                    </div>

                </div>

                <!-- 하단 버튼 -->
                <div class="detail-buttons">

                    <!-- 목록 -->
                    <a href="${pageContext.request.contextPath}/board/list.do"
                       class="btn btn-outline-secondary">
                        一覧へ
                    </a>

                    <!-- 작성자 본인에게만 수정・삭제 표시 -->
                    <c:if test="${not empty sessionScope.userid
                                 and sessionScope.userid eq board.userId}">

                        <div class="detail-buttons-right">

                            <!-- 수정 -->
                            <a href="${pageContext.request.contextPath}/board/update.do?boardId=${board.boardId}"
                               class="btn btn-primary">
                                修正する
                            </a>

                            <!-- 삭제 -->
                            <form action="${pageContext.request.contextPath}/board/delete.do"
                                  method="post"
                                  style="display: inline;">

                                <input type="hidden"
                                       name="boardId"
                                       value="${board.boardId}">

                                <button type="submit"
                                        class="btn btn-danger"
                                        onclick="return confirm('この記事を削除しますか？');">
                                    削除する
                                </button>

                            </form>

                        </div>

                    </c:if>

                </div>

            </div>

        </div>

    </section>

    <%@ include file="/common/footer.jsp"%>

    <script>
        // 댓글 수정 화면 표시
        function showCommentEdit(commentId) {
            document.getElementById("commentEdit-" + commentId)
                .style.display = "block";
        }

        // 댓글 수정 화면 숨김
        function hideCommentEdit(commentId) {
            document.getElementById("commentEdit-" + commentId)
                .style.display = "none";
        }
    </script>

</body>
</html>