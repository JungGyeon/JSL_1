<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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

	<script>
		if (localStorage.getItem("aniverseTheme") === "light") {
			document.body.classList.add("light-mode");
		}
	</script>

	<nav class="navbar navbar-expand-md navbar-custom py-3">
		<div class="container">

			<a class="navbar-brand brand"
				href="${pageContext.request.contextPath}/main.do"> ANI<span>VERSE</span>
			</a>

			<div class="d-flex align-items-center gap-2 order-md-3">
				<c:choose>
					<c:when test="${not empty sessionScope.userid}">
						<span class="text-muted small me-2 d-none d-sm-inline">${sessionScope.nickname}さん</span>
						<a class="btn btn-outline-soft btn-sm"
							href="${pageContext.request.contextPath}/member/logout.do">
							ログアウト </a>
					</c:when>
					<c:otherwise>
						<a class="btn btn-outline-soft btn-sm"
							href="${pageContext.request.contextPath}/member/loginForm.do">
							ログイン </a>
					</c:otherwise>
				</c:choose>
				<button class="btn btn-outline-soft btn-sm" type="button"
					id="themeToggleBtn" onclick="toggleTheme()"
					title="ダークモード/ライトモード切り替え">🌙</button>
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
						href="${pageContext.request.contextPath}/favorite/list.do?userId=${sessionScope.userid}">
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
		<div class="row g-3">

			<c:if test="${empty popularList}">검색된 리스트가 없습니다</c:if>



			<c:forEach var="anime" items="${popularList}" varStatus="status"
				end="7">

				<div class="col-6 col-md-4 col-lg-3">

					<div class="surface h-100 p-3">

						<!-- 순위 -->
						<div class="mono text-muted small mb-2">${status.index + 1}位
						</div>

						<!-- 썸네일 -->
						<c:if test="${not empty anime.thumbnail}">
							<img src="${anime.thumbnail}" alt="${anime.title}"
								class="img-fluid rounded mb-3">
						</c:if>

						<!-- 제목 -->
						<h5>${anime.title}</h5>

						<!-- 정보 -->
						<div class="text-muted small mb-3">⭐ ${anime.score} &nbsp;
							❤️ ${anime.favoriteCount}</div>

						<!-- 상세 페이지 -->
						<a class="btn btn-outline-soft btn-sm"
							href="${pageContext.request.contextPath}/anime/detail.do?animeId=${anime.animeId}">
							詳細を見る </a>

					</div>

				</div>

			</c:forEach>

		</div>
	</section>

	<footer class="footer text-center">
		<div>ANIVERSE — アニメおすすめサイト プロジェクト · JSP + Oracle + Bootstrap</div>
		<div class="mono mt-1" style="font-size: .7rem;">お気に入り数をもとに人気作品を表示しています。</div>
	</footer>


</body>
<script>
	var themeBtn = document.getElementById("themeToggleBtn");
	function syncThemeIcon() {
		themeBtn.textContent = document.body.classList.contains("light-mode") ? "☀️"
				: "🌙";
	}
	function toggleTheme() {
		document.body.classList.toggle("light-mode");
		var isLight = document.body.classList.contains("light-mode");
		localStorage.setItem("aniverseTheme", isLight ? "light" : "dark");
		syncThemeIcon();
	}
</script>
</html>
