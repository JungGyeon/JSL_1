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
%>

<!DOCTYPE html>
<html lang="ja">

<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>アニメ検索 — ANIVERSE</title>

<!-- Bootstrap -->
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Google Fonts -->
<link
	href="https://fonts.googleapis.com/css2?family=Zen+Kaku+Gothic+New:wght@400;500;700;900&family=Noto+Sans+JP:wght@400;500;700&family=JetBrains+Mono:wght@400;600&display=swap"
	rel="stylesheet">

<!-- CSS -->
<link href="../css/style.css" rel="stylesheet">

</head>

<body>

	<!-- =========================================================
     NAV
========================================================= -->

	<nav class="navbar navbar-expand-md navbar-custom py-3">

		<div class="container">

			<a class="navbar-brand brand"
				href="${pageContext.request.contextPath}/main.do"> ANI<span>VERSE</span>
			</a>

			<div class="d-flex align-items-center gap-2 order-md-3">

				<button class="spec-toggle" id="specToggleBtn"
					title="画面に要件ID(例: ANI-001)を表示します">⌘ 仕様書オーバーレイ</button>

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

				<button class="navbar-toggler border-0" type="button"
					data-bs-toggle="collapse" data-bs-target="#navMain"
					style="filter: invert(1);">

					<span class="navbar-toggler-icon"></span>

				</button>

			</div>

			<div class="collapse navbar-collapse order-md-2" id="navMain">

				<ul class="navbar-nav mx-md-3 gap-1 mt-3 mt-md-0">

					<li class="nav-item"><a class="nav-link nav-link-custom"
						href="${pageContext.request.contextPath}/main.do"> ホーム </a></li>

					<li class="nav-item"><a
						class="${pageContext.request.contextPath}/anime/list.do"
						href="search.jsp"> アニメ一覧 </a></li>

					<li class="nav-item"><a class="nav-link nav-link-custom"
						href="${pageContext.request.contextPath}/recommend/recommend.html">
							おすすめ </a></li>

					<li class="nav-item"><a class="nav-link nav-link-custom"
						href="../mypage/mypage.jsp"> マイページ </a></li>

				</ul>

			</div>

		</div>

	</nav>


	<!-- =========================================================
     SUB HERO
========================================================= -->

	<section class="sub-hero">

		<div class="container">

			<h2 class="mb-0">アニメ検索</h2>

		</div>

	</section>


	<!-- =========================================================
     SEARCH
========================================================= -->

	<section class="section container req-anchor">

		<span class="req-id"> ANI-002~006 · SYS-003 </span>


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


		<!-- =====================================================
         검색 결과
    ====================================================== -->

		<div class="d-flex justify-content-between align-items-center mb-3">
			<span class="text-muted small"> 검색結果：<%=list != null ? list.size() : 0%>件
			</span>
		</div>



		<!-- =====================================================
         CARD
    ====================================================== -->

		<div class="row g-3">

			<%
			if (list != null && !list.isEmpty()) {

				for (AnimeDTO anime : list) {
			%>


			<div class="col-12 col-sm-6 col-md-4 col-lg-3">

				<div class="card h-100">


					<!-- 이미지 -->

					<%
					if (anime.getPicture() != null && !anime.getPicture().equals("")) {
					%>

					<img src="<%=anime.getPicture()%>" class="card-img-top"
						alt="<%=anime.getTitle()%>"
						style="height: 300px; object-fit: cover;">

					<%
					} else {
					%>

					<div class="d-flex align-items-center justify-content-center"
						style="height: 300px; background: #eee;">No Image</div>

					<%
					}
					%>


					<!-- 카드 내용 -->

					<div class="card-body d-flex flex-column">


						<!-- 제목 -->

						<h5 class="card-title">

							<%=anime.getTitle()%>

						</h5>


						<!-- 연도 -->

						<p class="card-text text-muted mb-1">

							<%=anime.getYear()%>年

						</p>


						<!-- 점수 -->

						<p class="card-text mb-3">

							⭐
							<%=anime.getScore()%>

						</p>


						<!-- 상세 페이지 -->

						<a href="${pageContext.request.contextPath}/anime/detail.do?animeId=${anime.animeId}"
							class="btn btn-accent mt-auto"> 詳細を見る </a>


					</div>

				</div>

			</div>


			<%
			}

			} else {
			%>


			<!-- 검색 결과 없음 -->

			<div class="col-12">

				<div class="empty-state text-center p-5">検索結果がありません。</div>

			</div>


			<%
			}
			%>

		</div>


	</section>


	<!-- =========================================================
     FOOTER
========================================================= -->

	<footer class="footer text-center req-anchor">

		<span class="req-id"
			style="position: static; display: inline-block; margin-bottom: .5rem;">

			SYS-002 </span>

		<div>ANIVERSE — アニメおすすめサイト プロジェクト · JSP + Oracle + Bootstrap</div>

	</footer>


	<!-- Bootstrap JS -->

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/js/bootstrap.bundle.min.js">
		
	</script>


</body>

</html>
