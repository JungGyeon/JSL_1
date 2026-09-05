<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.AnimeDTO"%>
<%@ page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String title = (String) request.getAttribute("title");
String year = (String) request.getAttribute("year");
String sort = (String) request.getAttribute("sort");

if (title == null) {
	title = "";
}

if (year == null) {
	year = "";
}

if (sort == null) {
	sort = "score";
}

List<AnimeDTO> list = (List<AnimeDTO>) request.getAttribute("list");

request.setAttribute("activePage", "list");

// 찜/찜 해제 후 상세페이지로 넘어가지 않고 이 목록 화면으로 그대로 돌아오기 위한 복귀 주소
String backUrl = request.getContextPath() + "/anime/list.do"
		+ (request.getQueryString() != null ? "?" + request.getQueryString() : "");
String encodedBack = java.net.URLEncoder.encode(backUrl, "UTF-8");
%>
<!DOCTYPE html>
<html lang="ja">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>アニメ一覧 — ANIVERSE</title>

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
			<h2 class="mb-0">アニメ一覧</h2>
		</div>
	</section>

	<section class="section container">

		<!-- 검색 영역 -->
		<div class="surface p-3 p-md-4 mb-4">
			<form action="${pageContext.request.contextPath}/anime/search.do"
				method="get" class="row g-2 align-items-end">

				<!-- タイトル検索 -->
				<div class="col-md-4">
					<label class="form-label-custom">タイトル検索</label>
					<input type="text" class="form-control form-control-custom" name="title"
						value="<%=title != null ? title : ""%>" placeholder="タイトルを入力">
				</div>

				<!-- 放送年 -->
				<div class="col-md-3">
					<label class="form-label-custom">放送年</label>
					<select class="form-select form-control-custom" name="year">
						<option value="">すべて</option>
						<%
						String selectedYear = (String) request.getAttribute("year");

						if (selectedYear == null) {
							selectedYear = "";
						}

						for (int y = 2025; y >= 1980; y--) {
						%>
						<option value="<%=y%>"
							<%=String.valueOf(y).equals(selectedYear) ? "selected" : ""%>>
							<%=y%>年
						</option>
						<%
						}
						%>
					</select>
				</div>

				<!-- 並び替え -->
				<div class="col-md-3">
					<label class="form-label-custom">並び替え</label>
					<select class="form-select form-control-custom" name="sort">
						<option value="score" <%="score".equals(sort) ? "selected" : ""%>>評価順</option>
						<option value="year" <%="year".equals(sort) ? "selected" : ""%>>新着順</option>
						<option value="title" <%="title".equals(sort) ? "selected" : ""%>>タイトル順</option>
					</select>
				</div>

				<!-- 検索 -->
				<div class="col-md-2">
					<button class="btn btn-accent w-100" type="submit">検索</button>
				</div>
			</form>
		</div>

		<!-- 결과 개수 -->
		<div class="d-flex justify-content-between align-items-center mb-3">
			<span class="text-muted small">全${list.size()}件</span>
		</div>

		<!-- 애니메이션 목록 -->
		<c:choose>
			<c:when test="${empty list}">
				<div class="empty-state">アニメが見つかりません。</div>
			</c:when>
			<c:otherwise>
				<div class="row g-3">
					<c:forEach var="anime" items="${list}">
						<div class="col-6 col-md-4 col-lg-3">
							<div style="position: relative;">
								<a class="anime-card"
									href="${pageContext.request.contextPath}/anime/detail.do?animeId=${anime.animeId}">
									<div class="poster"
										style="background-image:url('${anime.thumbnail}'); background-size:cover; background-position:center;">
										<span class="score-badge">★ ${anime.score}</span>
										<div class="poster-title">${anime.title}</div>
									</div>
									<div class="meta">${anime.type} · ${anime.year}年 ·
										${anime.episodes}話</div>
								</a>

								<!-- FAV-001/004 : 목록에서 바로 찜하기/찜 해제 -->
								<c:choose>
									<c:when test="${not empty sessionScope.userid}">
										<c:choose>
											<c:when test="${favIds.contains(anime.animeId)}">
												<a class="card-fav-btn active" title="お気に入り解除"
													onclick="return confirm('お気に入りから削除しますか？');"
													href="${pageContext.request.contextPath}/favorite/delete.do?userId=${sessionScope.userid}&amp;animeId=${anime.animeId}&amp;back=<%=encodedBack%>">♥</a>
											</c:when>
											<c:otherwise>
												<a class="card-fav-btn" title="お気に入りに追加"
													href="${pageContext.request.contextPath}/favorite/add.do?animeId=${anime.animeId}&amp;userId=${sessionScope.userid}&amp;back=<%=encodedBack%>">♡</a>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<a class="card-fav-btn" title="ログインしてお気に入りに追加"
											href="${pageContext.request.contextPath}/member/loginForm.do">♡</a>
									</c:otherwise>
								</c:choose>
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
