<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ログイン — ANIVERSE</title>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css2?family=Zen+Kaku+Gothic+New:wght@400;500;700;900&family=Noto+Sans+JP:wght@400;500;700&family=JetBrains+Mono:wght@400;600&display=swap"
	rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
</head>
<body>

	<!-- ===== NAV ===== -->
	<nav class="navbar navbar-expand-md navbar-custom py-3">
		<div class="container">
			<a class="navbar-brand brand" href="../index.html">ANI<span>VERSE</span></a>
			<div class="d-flex align-items-center gap-2 order-md-3">
				<button class="spec-toggle" id="specToggleBtn"
					onclick="toggleSpec()" title="画面に要件ID(例: AUTH-001)を表示します">⌘
					仕様書オーバーレイ</button>
				<button class="navbar-toggler border-0" type="button"
					data-bs-toggle="collapse" data-bs-target="#navMain"
					style="filter: invert(1);">
					<span class="navbar-toggler-icon"></span>
				</button>
			</div>
			<div class="collapse navbar-collapse order-md-2" id="navMain">
				<ul class="navbar-nav mx-md-3 gap-1 mt-3 mt-md-0">
					<li class="nav-item"><a class="nav-link nav-link-custom"
						href="../index.html">ホーム</a></li>
					<li class="nav-item"><a class="nav-link nav-link-custom"
						href="../list/list.html">アニメ一覧</a></li>
					<li class="nav-item"><a class="nav-link nav-link-custom"
						href="../recommend/recommend.html">おすすめ</a></li>
					<li class="nav-item"><a class="nav-link nav-link-custom"
						href="../mypage/mypage.html">マイページ</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<section class="section container req-anchor" style="max-width: 420px;">
		<span class="req-id">AUTH-001 · AUTH-002 · AUTH-003</span>
		<div class="surface p-4 p-md-5">
			<h3 class="text-center mb-1">ログイン</h3>
			<p class="text-muted text-center mb-4 small">ANIVERSEであなただけのお気に入りリストを管理しましょう</p>

			<c:if test="${not empty errorMsg}">
				<div class="alert alert-danger py-2 small"
					style="background: #3a1a22; border: 1px solid #6b2532; color: #ffb4c0;">${errorMsg}</div>
			</c:if>

			<!-- AUTH-001~003 : member/login.do 로 로그인 요청을 보낸다 -->
			<form action="${pageContext.request.contextPath}/member/login.do"
				method="post">
				<div class="mb-3">
					<label class="form-label-custom">ID</label> <input type="text"
						class="form-control form-control-custom" name="userId"
						value="${param.userId}" placeholder="IDを入力" required>
				</div>
				<div class="mb-3">
					<label class="form-label-custom">パスワード</label> <input
						type="password" class="form-control form-control-custom"
						name="userPw" placeholder="パスワードを入力" required>
				</div>
				<button class="btn btn-accent w-100 mt-2" type="submit">ログイン</button>
			</form>

			<p class="text-center text-muted small mt-3 mb-0">
				アカウントをお持ちでないですか? <a href="../member/join.jsp"
					style="color: var(--accent-2);">会員登録</a>
			</p>
		</div>
	</section>

	<footer class="footer text-center req-anchor">
		<span class="req-id"
			style="position: static; display: inline-block; margin-bottom: .5rem;">SYS-002</span>
		<div>ANIVERSE — アニメおすすめサイト プロジェクト · JSP + Oracle + Bootstrap</div>
	</footer>

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
	<script src="../js/anime-data.js"></script>
</body>
</html>
