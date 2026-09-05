<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    request.setAttribute("activePage", "list");

    // 찜/찜 해제 후 마이페이지로 튀지 않고 이 상세 화면으로 그대로 돌아오기 위한 복귀 주소
    String backUrl = request.getContextPath() + "/anime/detail.do?animeId=" + request.getParameter("animeId");
    request.setAttribute("encodedBack", java.net.URLEncoder.encode(backUrl, "UTF-8"));
%>

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

	<%@ include file="/common/header.jsp"%>

	<section class="section container">

		<c:if test="${not empty anime}">
			<div class="req-anchor mb-3">
				<span class="req-id">ANI-007 · FAV-001 · FAV-002 · FAV-004</span>
			</div>
			<div class="row g-4">
				<div class="col-md-4">
					<div style="max-width: 280px;">
						<c:choose>
							<c:when test="${not empty anime.thumbnail}">
								<div class="poster"
									style="background-image:url('${anime.thumbnail}'); background-size:cover; background-position:center;">
									<span class="score-badge">★ ${anime.score}</span>
								</div>
							</c:when>

							<c:when test="${not empty anime.picture}">
								<div class="poster"
									style="background-image:url('${anime.picture}'); background-size:cover; background-position:center;">
									<span class="score-badge">★ ${anime.score}</span>
								</div>
							</c:when>

							<c:otherwise>
								<div class="empty-state">No Image</div>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<!-- ===== 애니 정보 ===== -->

				<div class="col-md-8">
					<h2 class="mb-2">${anime.title}</h2>
					<div class="text-muted mb-3">${anime.type}·${anime.season}·
						${anime.year}年 · 全${anime.episodes}話 ·
						1話${anime.durationValue}${anime.durationUnit} · ${anime.status}</div>
					<div class="mb-3">
						<span class="mono"
							style="color: var(--accent-2); font-size: .9rem;">★
							${anime.score}</span> <span class="text-muted small ms-2">/ 10.0</span>
					</div>
					<div class="mb-4">
						<c:choose>
							<c:when test="${not empty sessionScope.userid}">
								<c:choose>
									<c:when test="${isFavorite}">
										<a class="btn btn-accent" title="お気に入り解除"
											onclick="return confirm('お気に入りから削除しますか？');"
											href="${pageContext.request.contextPath}/favorite/delete.do?userId=${sessionScope.userid}&amp;animeId=${anime.animeId}&amp;back=${encodedBack}">
											♥ お気に入り解除 </a>
									</c:when>
									<c:otherwise>
										<a class="btn btn-outline-soft"
											href="${pageContext.request.contextPath}/favorite/add.do?animeId=${anime.animeId}&amp;userId=${sessionScope.userid}">
											♡ お気に入りに追加 </a>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<a class="btn btn-outline-soft"
									href="${pageContext.request.contextPath}/member/loginForm.do">
									♡ お気に入りに追加</a>
								<div class="small text-muted mt-2">ログイン後にお気に入り登録できます。</div>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</c:if>
		<c:if test="${empty anime}">

			<div class="empty-state">アニメが見つかりません。</div>

		</c:if>

	</section>

	<!-- ===== FOOTER ===== -->

	<%@ include file="/common/footer.jsp"%>

</body>

</html>
