<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ja">

<head>

<meta charset="UTF-8">

<meta name="viewport"
    content="width=device-width, initial-scale=1.0">

<title>投稿する — ANIVERSE</title>

<!-- Bootstrap -->
<link
    href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/css/bootstrap.min.css"
    rel="stylesheet">

<!-- Font -->
<link
    href="https://fonts.googleapis.com/css2?family=Zen+Kaku+Gothic+New:wght@400;500;700;900&family=Noto+Sans+JP:wght@400;500;700&family=JetBrains+Mono:wght@400;600&display=swap"
    rel="stylesheet">

<!-- 공통 CSS -->
<link
    href="${pageContext.request.contextPath}/css/style.css"
    rel="stylesheet">


<style>

.board-write {
    max-width: 900px;
    margin: 0 auto;
}

.write-label {
    color: var(--text);
    font-weight: 700;
    margin-bottom: 8px;
}

.write-input {
    background: var(--surface);
    color: var(--text);
    border: 1px solid var(--border);
    border-radius: 6px;
    padding: 12px 14px;
}

.write-input:focus {
    background: var(--surface);
    color: var(--text);
    border-color: var(--accent-2);
    box-shadow: 0 0 0 0.2rem rgba(128, 90, 213, 0.15);
}

.write-input::placeholder {
    color: var(--text-muted);
}

textarea.write-input {
    min-height: 350px;
    resize: vertical;
}

.write-buttons {
    display: flex;
    justify-content: flex-end;
    gap: 10px;
    margin-top: 25px;
}

</style>

</head>


<body>

    <%@ include file="/common/header.jsp"%>


    <!-- 페이지 제목 -->
    <section class="sub-hero">

        <div class="container">

            <h2 class="mb-0">
                投稿する
            </h2>

        </div>

    </section>


    <!-- 게시글 작성 -->
    <section class="section container req-anchor">

        <span class="req-id">
            BOARD-002
        </span>


        <div class="board-write">

            <div class="surface p-4">

                <form
                    action="${pageContext.request.contextPath}/board/write.do"
                    method="post">


                    <!-- 제목 -->
                    <div class="mb-4">

                        <label
                            for="title"
                            class="write-label">

                            タイトル

                        </label>

                        <input
                            type="text"
                            id="title"
                            name="title"
                            class="form-control write-input"
                            placeholder="タイトルを入力してください。"
                            maxlength="200"
                            required>

                    </div>


                    <!-- 내용 -->
                    <div class="mb-3">

                        <label
                            for="content"
                            class="write-label">

                            内容

                        </label>

                        <textarea
                            id="content"
                            name="content"
                            class="form-control write-input"
                            placeholder="内容を入力してください。"
                            maxlength="4000"
                            required></textarea>

                    </div>


                    <!-- 버튼 -->
                    <div class="write-buttons">

                        <a
                            href="${pageContext.request.contextPath}/board/list.do"
                            class="btn btn-outline-secondary">

                            キャンセル

                        </a>


                        <button
                            type="submit"
                            class="btn btn-accent">

                            投稿する

                        </button>

                    </div>


                </form>

            </div>

        </div>

    </section>


    <%@ include file="/common/footer.jsp"%>


</body>

</html>