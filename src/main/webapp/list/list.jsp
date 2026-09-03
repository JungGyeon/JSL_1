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

<link href="${pageContext.request.contextPath}/css/style.css"
	rel="stylesheet">
</head>

<body>

	<nav class="navbar navbar-expand-md navbar-custom py-3">

		<div class="container">

			<a class="navbar-brand brand"
				href="${pageContext.request.contextPath}/main.do"> ANI<span>VERSE</span>
			</a>

			<div class="collapse navbar-collapse">

				<ul class="navbar-nav mx-md-3 gap-1">

					<li class="nav-item"><a class="nav-link nav-link-custom"
						href="${pageContext.request.contextPath}/main.do"> ホーム </a></li>

					<li class="nav-item"><a
						class="nav-link nav-link-custom active"
						href="${pageContext.request.contextPath}/anime/list.do"> アニメ一覧
					</a></li>

					<li class="nav-item"><a class="nav-link nav-link-custom"
						href="${pageContext.request.contextPath}/recommend/recommend.html">
							おすすめ </a></li>

				</ul>

			</div>

		</div>

	</nav>


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
				<!-- command -->



				<!-- タイトル検索 -->

				<div class="col-md-4">

					<label class="form-label-custom"> タイトル検索 </label> <input
						type="text" class="form-control form-control-custom" name="title"
						value="<%=title != null ? title : ""%>" placeholder="タイトルを入力">

				</div>


				<!-- 放送年 -->

				<div class="col-md-3">

					<label class="form-label-custom"> 放送年 </label> <select
						class="form-select form-control-custom" name="year">

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

					<label class="form-label-custom"> 並び替え </label> <select
						class="form-select form-control-custom" name="sort">

						<option value="score" <%="score".equals(sort) ? "selected" : ""%>>評価順</option>

						<option value="year" <%="year".equals(sort) ? "selected" : ""%>>
							新着順</option>

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

			<span class="text-muted small"> 全${list.size()}件 </span>

		</div>


		<!-- 애니메이션 목록 -->

		<div class="row g-3">

			<c:forEach var="anime" items="${list}">

				<div class="col-12 col-sm-6 col-md-4 col-lg-3">

					<div class="card h-100">

						<c:choose>

							<c:when test="${not empty anime.thumbnail}">

								<img src="${anime.thumbnail}" class="card-img-top"
									alt="${anime.title}">

							</c:when>

							<c:otherwise>

								<div class="p-5 text-center">No Image</div>

							</c:otherwise>

						</c:choose>


						<div class="card-body">

							<h5 class="card-title">${anime.title}</h5>

							<p class="card-text">

								${anime.type} <br> ${anime.year} <br>

								${anime.episodes}話 <br> ★ ${anime.score}

							</p>


							<a
								href="${pageContext.request.contextPath}/anime/detail.do?animeId=${anime.animeId}"
								class="btn btn-accent btn-sm"> 詳細を見る </a>

						</div>

					</div>

				</div>

			</c:forEach>


			<!-- 데이터가 없을 경우 -->

			<c:if test="${empty list}">

				<div class="col-12">

					<div class="empty-state">アニメが見つかりません。</div>

				</div>

			</c:if>

		</div>

	</section>


	<footer class="footer text-center">

		<div>ANIVERSE — アニメおすすめサイト プロジェクト</div>

	</footer>

</body>
</html>