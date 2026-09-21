<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ja">

<head>
<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>エラー — ANIVERSE</title>

<link
    href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/css/bootstrap.min.css"
    rel="stylesheet">

<link
    href="https://fonts.googleapis.com/css2?family=Zen+Kaku+Gothic+New:wght@400;500;700;900&family=Noto+Sans+JP:wght@400;500;700&family=JetBrains+Mono:wght@400;600&display=swap"
    rel="stylesheet">

<link href="${pageContext.request.contextPath}/css/style.css"
    rel="stylesheet">

<style>

    .error-container {
        max-width: 520px;
        margin: 0 auto;
    }

    .error-icon {
        font-size: 48px;
        margin-bottom: 16px;
    }

    .error-title {
        font-weight: 700;
        margin-bottom: 12px;
    }

    .error-message {
        color: var(--text-muted);
        margin-bottom: 28px;
    }

</style>

</head>

<body>

<%@ include file="/common/header.jsp"%>

<section class="section container">

    <div class="error-container surface p-4 p-md-5 text-center">

        <div class="error-icon">
            ⚠️
        </div>

        <h2 class="error-title">
            操作できません
        </h2>

        <p class="error-message">
            ${message}
        </p>

        <button
            type="button"
            class="btn btn-accent"
            onclick="history.back()">
            前のページに戻る
        </button>

    </div>

</section>

<%@ include file="/common/footer.jsp"%>

</body>

</html>
