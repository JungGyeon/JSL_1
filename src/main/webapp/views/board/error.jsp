<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ja">

<head>
<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>エラー</title>

<link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
    rel="stylesheet">

<style>

    body {
        background-color: #f8f9fa;
    }

    .error-container {
        max-width: 600px;
        margin: 120px auto;
    }

    .error-card {
        background-color: #ffffff;
        border: 1px solid #dee2e6;
        border-radius: 12px;
        padding: 50px 40px;
        text-align: center;
        box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
    }

    .error-icon {
        font-size: 55px;
        margin-bottom: 20px;
    }

    .error-title {
        font-weight: bold;
        margin-bottom: 15px;
    }

    .error-message {
        color: #6c757d;
        margin-bottom: 30px;
    }

</style>

</head>

<body>

<div class="container">

    <div class="error-container">

        <div class="error-card">

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
                class="btn btn-primary"
                onclick="history.back()">
                前のページに戻る
            </button>

        </div>

    </div>

</div>

</body>

</html>
