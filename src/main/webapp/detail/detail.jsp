<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html lang="ja">

<head>

<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>作品詳細 — ANIVERSE</title>

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

	<!-- ===== NAV ===== -->

	<nav class="navbar navbar-expand-md navbar-custom py-3">

		<div class="container">


			<!-- 로고 -->

			<a class="navbar-brand brand"
				href="${pageContext.request.contextPath}/index.jsp"> ANI<span>VERSE</span>

			</a>


			<!-- 오른쪽 버튼 -->

			<div class="d-flex align-items-center gap-2 order-md-3">

				<button class="spec-toggle" id="specToggleBtn" type="button">

					⌘ 仕様書オーバーレイ</button>


				<c:choose>
					<c:when test="${not empty sessionScope.userid}">
						<span class="text-muted small me-2 d-none d-sm-inline">${sessionScope.nickname}さん</span>
						<a class="btn btn-outline-soft btn-sm"
							href="${pageContext.request.contextPath}/member/logout.do"> ログアウト

						</a>
					</c:when>
					<c:otherwise>
						<a class="btn btn-outline-soft btn-sm"
							href="${pageContext.request.contextPath}/member/loginForm.do"> ログイン

						</a>
					</c:otherwise>
				</c:choose>


				<button class="navbar-toggler border-0" type="button"
					data-bs-toggle="collapse" data-bs-target="#navMain"
					style="filter: invert(1);">

					<span class="navbar-toggler-icon"></span>

				</button>

			</div>


			<!-- 메뉴 -->

			<div class="collapse navbar-collapse order-md-2" id="navMain">

				<ul class="navbar-nav mx-md-3 gap-1 mt-3 mt-md-0">


					<li class="nav-item"><a class="nav-link nav-link-custom"
						href="${pageContext.request.contextPath}/index.jsp"> ホーム </a></li>


					<li class="nav-item"><a
						class="nav-link nav-link-custom active"
						href="${pageContext.request.contextPath}/anime/list.do"> アニメ一覧

					</a></li>


					<li class="nav-item"><a class="nav-link nav-link-custom"
						href="${pageContext.request.contextPath}/recommend/recommend.jsp">

							おすすめ </a></li>


					<li class="nav-item"><a class="nav-link nav-link-custom"
						href="${pageContext.request.contextPath}/mypage/mypage.jsp">

							マイページ </a></li>


				</ul>

			</div>

		</div>

	</nav>

	<!-- ===== 상세 정보 ===== -->

	<section class="section container">

		<!-- 애니가 존재하는 경우 -->

		<c:if test="${not empty anime}">


			<!-- 요구사항 ID -->

			<div class="req-anchor mb-3">

				<span class="req-id"> ANI-007 · FAV-001 · FAV-002 · FAV-004 </span>

			</div>



			<div class="row g-4">


				<!-- ===== 포스터 ===== -->

				<div class="col-md-4">

					<div style="max-width: 280px;">


						<c:choose>

							<c:when test="${not empty anime.thumbnail}">
								<!-- thumbnail이 존재하면 thumbnail 사용 -->
								<img src="${anime.thumbnail}" class="img-fluid"
									alt="${anime.title}">

							</c:when>

							<c:when test="${not empty anime.picture}">
								<!-- thumbnail이 없고 picture가 있으면 picture 사용 -->
								<img src="${anime.picture}" class="img-fluid"
									alt="${anime.title}">

							</c:when>

							<c:otherwise>
								<!-- 둘 다 없으면 -->
								<div class="p-5 text-center">No Image</div>

							</c:otherwise>

						</c:choose>


					</div>

				</div>



				<!-- ===== 애니 정보 ===== -->

				<div class="col-md-8">


					<!-- 제목 -->

					<h2 class="mb-2">${anime.title}</h2>


					<!-- 기본 정보 -->

					<div class="text-muted mb-3">${anime.type}·${anime.season}·
						${anime.year}年 · 全${anime.episodes}話 ·
						1話${anime.durationValue}${anime.durationUnit} · ${anime.status}</div>


					<!-- 평점 -->

					<div class="mb-3">

						<span class="mono"
							style="color: var(--accent-2); font-size: .9rem;"> ★
							${anime.score} </span> <span class="text-muted small ms-2"> /
							10.0 </span>

					</div>


					<!-- 찜 버튼 -->

					<div class="mb-4">

						<a class="btn btn-outline-soft"
							href="${pageContext.request.contextPath}/favorite/add.do?animeId=${anime.animeId}">

							♡ お気に入りに追加 </a>

					</div>


					<div class="small text-muted mt-2">ログイン後にお気に入り登録できます。</div>


				</div>

			</div>


		</c:if>



		<!-- ===== 애니가 존재하지 않는 경우 ===== -->

		<c:if test="${empty anime}">

			<div class="empty-state">アニメが見つかりません。</div>

		</c:if>

	</section>

	<!-- ===== FOOTER ===== -->

	<footer class="footer text-center req-anchor">
		<span class="req-id"
			style="position: static; display: inline-block; margin-bottom: .5rem;">

			SYS-002 </span>


		<div>ANIVERSE — アニメおすすめサイト プロジェクト · JSP + Oracle + Bootstrap</div>

	</footer>

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/js/bootstrap.bundle.min.js">
		
	</script>

</body>

</html>
