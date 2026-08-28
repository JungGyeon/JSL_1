<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.List"%>
<%@ page import="model.AnimeDTO"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>マイページ — ANIVERSE</title>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css2?family=Zen+Kaku+Gothic+New:wght@400;500;700;900&family=Noto+Sans+JP:wght@400;500;700&family=JetBrains+Mono:wght@400;600&display=swap"
	rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
</head>
<body>

	<nav class="navbar navbar-expand-md navbar-custom py-3">
		<div class="container">
			<a class="navbar-brand brand" href="../index.html">ANI<span>VERSE</span></a>
			<div class="d-flex align-items-center gap-2 order-md-3">
				<button class="spec-toggle" id="specToggleBtn"
					onclick="toggleSpec()" title="画面に要件ID(例: MY-001)を表示します">⌘
					仕様書オーバーレイ</button>
				<span id="navAuthArea"></span>
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
					<li class="nav-item"><a
						class="nav-link nav-link-custom active"
						href="${pageContext.request.contextPath}/favorite/list.do">マイページ</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<section class="section container req-anchor">
		<span class="req-id">MY-001 · MY-002 · FAV-003</span>

		<div class="d-flex align-items-center gap-3 mb-4">
			<div class="avatar-ring">
				<div class="inner">${sessionScope.userid.substring(0,1)}</div>
			</div>
			<div>
				<h4 class="mb-0">${sessionScope.userid}</h4>
				<div class="text-muted small">お気に入り${list.size()}件</div>
			</div>
		</div>

		<h5 class="mb-3">マイお気に入りリスト</h5>

		<c:choose>
			<c:when test="${empty list}">
				<div class="empty-state">
					まだお気に入り作品がありません。<br> <a class="btn btn-accent mt-3"
						href="../list/list.html">アニメを見る →</a>
				</div>
			</c:when>
			<c:otherwise>
				<div class="row g-3">
					<c:forEach var="anime" items="${list}">
						<div class="col-6 col-md-4 col-lg-3">
							<div style="position: relative;">
								<a class="anime-card"
									href="${pageContext.request.contextPath}/detail/detail.do?animeId=${anime.animeId}">

									<div class="poster"
										style="background-image:url('${pageContext.request.contextPath}${anime.thumbnail}'); background-size:cover; background-position:center;">
										<span class="score-badge">★ ${anime.score}</span>
										<div class="poster-title">${anime.title}</div>
									</div>

									<div class="meta">${anime.type} · ${anime.year}年 · 全${anime.episodes}話</div>
								</a> <a class="card-fav-btn" title="お気に入り解除"
									onclick="return confirm('お気に入りから削除しますか？');"
									href="${pageContext.request.contextPath}/favorite/delete.do?userId=${sessionScope.userid}&amp;animeId=${anime.animeId}">♥</a>
							</div>
						</div>
					</c:forEach>
				</div>
			</c:otherwise>
		</c:choose>
	</section>

	<!-- footer -->
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
