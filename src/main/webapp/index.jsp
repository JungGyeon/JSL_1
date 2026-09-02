<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ANIVERSE — アニメおすすめサイト</title>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css2?family=Zen+Kaku+Gothic+New:wght@400;500;700;900&family=Noto+Sans+JP:wght@400;500;700&family=JetBrains+Mono:wght@400;600&display=swap"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/style.css"
	rel="stylesheet">
</head>
<body>

	<nav class="navbar navbar-expand-md navbar-custom py-3">
		<div class="container">

			<a class="navbar-brand brand"
				href="${pageContext.request.contextPath}/main.do"> ANI<span>VERSE</span>
			</a>

			<div class="d-flex align-items-center gap-2 order-md-3">
				<a class="btn btn-outline-soft btn-sm"
					href="${pageContext.request.contextPath}/login/login.html">
					ログイン </a>
				<button class="navbar-toggler border-0" type="button"
					data-bs-toggle="collapse" data-bs-target="#navMain"
					style="filter: invert(1);">
					<span class="navbar-toggler-icon"></span>
				</button>
			</div>

			<div class="collapse navbar-collapse order-md-2" id="navMain">
				<ul class="navbar-nav mx-md-3 gap-1 mt-3 mt-md-0">
					<li class="nav-item"><a
						class="nav-link nav-link-custom active"
						href="${pageContext.request.contextPath}/main.do"> ホーム </a></li>
					<li class="nav-item"><a class="nav-link nav-link-custom"
						href="${pageContext.request.contextPath}/anime/list.do"> アニメ一覧
					</a></li>
					<li class="nav-item"><a class="nav-link nav-link-custom"
						href="${pageContext.request.contextPath}/recommend/recommend.html">
							おすすめ </a></li>
					<li class="nav-item"><a class="nav-link nav-link-custom"
						href="${pageContext.request.contextPath}/favorite/list.do">
							マイページ </a></li>
				</ul>
			</div>

		</div>
	</nav>

	<section class="hero">
		<div class="container text-center">
			<h1 class="display">タグで見つける、あなただけのアニメ</h1>
			<p class="lead mb-4">気になるタイトルを検索して、お気に入りに追加してみましょう。</p>

			<form class="mx-auto d-flex search-pill" style="max-width: 560px;"
				action="${pageContext.request.contextPath}/anime/search.do"
				method="get">
				<input type="text" name="title"
					placeholder="タイトルで検索(例:魔法、ロボット、学園もの...)">
				<button class="btn btn-accent" type="submit">検索</button>
			</form>
		</div>
	</section>

	<section class="section container">
		<div class="d-flex justify-content-between align-items-end mb-3">
			<h3>今、人気の作品</h3>
			<a class="text-muted small"
				href="${pageContext.request.contextPath}/anime/list.do">すべて見る →</a>
		</div>
		<div class="row g-3" id="homePopularGrid"></div>
	</section>

	<section class="section container">
		<div
			class="surface p-4 p-md-5 d-flex flex-column flex-md-row align-items-center justify-content-between gap-3">
			<div>
				<h4 class="mb-1">お気に入りが増えるほど、おすすめが正確になります</h4>
				<p class="text-muted mb-0">ログインして気に入ったアニメをお気に入りに追加しましょう。</p>
			</div>
			<a class="btn btn-accent flex-shrink-0"
				href="${pageContext.request.contextPath}/recommend/recommend.html">
				おすすめを見る </a>
		</div>
	</section>

	<footer class="footer text-center">
		<div>ANIVERSE — アニメおすすめサイト プロジェクト · JSP + Oracle + Bootstrap</div>
		<div class="mono mt-1" style="font-size: .7rem;">この画面のトップ人気作品はダミーデータ(anime-data.js)で表示しています。</div>
	</footer>

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/anime-data.js"></script>
	<script>
		function onFavClick(id) {
			location.href = "${pageContext.request.contextPath}/login/login.html";
		}
		var popular = [...ANIME].sort(function(a, b) {
			return b.score - a.score;
		}).slice(0, 8);
		document.getElementById("homePopularGrid").innerHTML = popular.map(
				function(a) {
					return cardHTML(a, "detail/detail.html", false);
				}).join("");
	</script>
</body>
</html>
