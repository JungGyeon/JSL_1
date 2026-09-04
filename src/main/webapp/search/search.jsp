<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="java.util.List"%>
<%@ page import="model.AnimeDTO"%>

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

%>

<!DOCTYPE html>
<html lang="ja">

<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>アニメ検索 — ANIVERSE</title>

<link
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css2?family=Zen+Kaku+Gothic+New:wght@400;500;700;900&family=Noto+Sans+JP:wght@400;500;700&family=JetBrains+Mono:wght@400;600&display=swap"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">

</head>

<body>

	<%@ include file="/common/header.jsp"%>

	<section class="sub-hero">
		<div class="container">
			<h2 class="mb-0">アニメ検索</h2>
		</div>
	</section>
	<section class="section container req-anchor">

		<span class="req-id">ANI-002~006 · SYS-003</span>

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

		<div class="d-flex justify-content-between align-items-center mb-3">
			<span class="text-muted small">検索結果：<%=list != null ? list.size() : 0%>件</span>
		</div>

		<%
		if (list != null && !list.isEmpty()) {
		%>
		<div class="row g-3">
			<%
			for (AnimeDTO anime : list) {
			%>
			<div class="col-6 col-md-4 col-lg-3">
				<a class="anime-card"
					href="<%=request.getContextPath()%>/anime/detail.do?animeId=<%=anime.getAnimeId()%>">
					<div class="poster"
						style="background-image:url('<%=anime.getThumbnail() != null ? anime.getThumbnail() : ""%>'); background-size:cover; background-position:center;">
						<span class="score-badge">★ <%=anime.getScore()%></span>
						<div class="poster-title"><%=anime.getTitle()%></div>
					</div>
					<div class="meta"><%=anime.getType()%> · <%=anime.getYear()%>年
						· <%=anime.getEpisodes()%>話</div>
				</a>
			</div>
			<%
			}
			%>
		</div>
		<%
		} else {
		%>
		<div class="empty-state">検索結果がありません。</div>
		<%
		}
		%>

	</section>

	<%@ include file="/common/footer.jsp"%>
</body>

</html>
