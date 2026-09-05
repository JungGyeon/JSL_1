<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.AnimeDTO"%>
<%@ page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
List<AnimeDTO> recommendList = (List<AnimeDTO>) request.getAttribute("recommendList");

request.setAttribute("activePage", "recommend");

String backUrl = request.getContextPath() + "/recommend/list.do";
String encodedBack = java.net.URLEncoder.encode(backUrl, "UTF-8");
%>
<!DOCTYPE html>
<html lang="ja">


<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>おすすめ — ANIVERSE</title>

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

	<section class="sub-hero">
		<div class="container">
			<h2 class="mb-0">おすすめ</h2>
		</div>
	</section>

	<section class="section container req-anchor">
		<span class="req-id">REC-001 · REC-002 · REC-003</span>

		<c:choose>

			<c:when test="${not hasFavorites}">
				<div class="empty-state">
					まだお気に入りに追加した作品がありません。<br>
					<span class="small">お気に入りのタグをもとにおすすめするので、まず気になる作品をお気に入りに追加してみましょう。</span><br>
					<a class="btn btn-accent mt-3"
						href="${pageContext.request.contextPath}/anime/list.do">アニメを見る →</a>
				</div>
			</c:when>

			<c:when test="${empty recommendList}">
				<div class="empty-state">
					条件に合うおすすめ作品が見つかりませんでした。<br>
					<span class="small">お気に入りをもっと追加すると、おすすめの精度が上がります。</span>
				</div>
			</c:when>

			<c:otherwise>
				<p class="text-muted mb-3">お気に入りのタグと近い作品を、一致するタグの数が多い順に紹介します。</p>

				<div class="row g-3">
					<c:forEach var="anime" items="${recommendList}">
						<div class="col-6 col-md-4 col-lg-3">
							<div style="position: relative;">
								<a class="anime-card"
									href="${pageContext.request.contextPath}/anime/detail.do?animeId=${anime.animeId}">
									<div class="poster"
										style="background-image:url('${anime.thumbnail}'); background-size:cover; background-position:center;">
										<span class="mono"
											style="position: absolute; top: .5rem; left: .5rem; background: rgba(20,17,31,.75); color: var(--accent-2); font-size: .72rem; font-weight: 600; padding: .15rem .5rem; border-radius: 6px;">
											タグ一致 ${anime.matchCount}
										</span>
										<span class="score-badge">★ ${anime.score}</span>
										<div class="poster-title">${anime.title}</div>
									</div>
									<div class="meta">${anime.type} · ${anime.year}年 ·
										${anime.episodes}話</div>
								</a>
								
							</div>
						</div>
					</c:forEach>
				</div>
			</c:otherwise>

		</c:choose>

	</section>

	<%@ include file="/common/footer.jsp"%>
</body>
</html>
