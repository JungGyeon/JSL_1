<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    request.setAttribute("activePage", "home");
%>

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
	
	<%@ include file="/common/header.jsp"%>

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

		<c:choose>
			<c:when test="${empty popularList}">
				<div class="empty-state">まだお気に入りに追加された作品がありません。</div>
			</c:when>
			<c:otherwise>
				<div class="row g-3">
					<c:forEach var="anime" items="${popularList}" varStatus="status"
						end="7">
						<div class="col-6 col-md-4 col-lg-3">
							<a class="anime-card"
								href="${pageContext.request.contextPath}/anime/detail.do?animeId=${anime.animeId}">
								<div class="poster"
									style="background-image:url('${anime.thumbnail}'); background-size:cover; background-position:center;">
									<span class="mono"
										style="position: absolute; top: .5rem; left: .5rem; background: rgba(20,17,31,.75); color: var(--accent-2); font-size: .72rem; font-weight: 600; padding: .15rem .5rem; border-radius: 6px;">${status.index + 1}位</span>
									<span class="score-badge">★ ${anime.score}</span>
									<div class="poster-title">${anime.title}</div>
								</div>
								<div class="meta">${anime.type} · ${anime.year}年 ·
									❤️ ${anime.favoriteCount}</div>
							</a>
						</div>
					</c:forEach>
				</div>
			</c:otherwise>
		</c:choose>
	</section>

	<div class="container text-center mono"
		style="font-size: .7rem; color: var(--text-muted); margin-top: -1rem; margin-bottom: 1.5rem;">お気に入り数をもとに人気作品を表示しています。</div>

	<%@ include file="/common/footer.jsp"%>
</body>
</html>
