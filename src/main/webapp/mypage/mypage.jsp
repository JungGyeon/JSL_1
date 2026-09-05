<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.List"%>
<%@ page import="model.AnimeDTO"%>
<%@ page import="model.UserDTO"%>
<%@ page import="model.UserDAO"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
// MY-001 : 회원정보 조회. 로그인 사용자(userid)를 기준으로 USER 테이블에서 최신 프로필을 조회한다.
String loginId = (String) session.getAttribute("userid");
UserDTO loginUser = (loginId != null) ? new UserDAO().getUserById(loginId) : null;
request.setAttribute("loginUser", loginUser);

request.setAttribute("activePage", "mypage");
%>

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
<link href="${pageContext.request.contextPath}/css/style.css"
	rel="stylesheet">
</head>
<body>

	<%@ include file="/common/header.jsp"%>

	<section class="section container req-anchor">
		<span class="req-id">MY-001 · MY-002 · FAV-003</span>

		<div class="d-flex align-items-center gap-3 mb-3">
			<div class="avatar-ring">
				<div class="inner">${sessionScope.userid.substring(0,1)}</div>
			</div>
			<div>
				<h4 class="mb-0">${loginUser.nickname}</h4>
				<div class="text-muted small">ID: ${sessionScope.userid} ·
					お気に入り${list.size()}件</div>
			</div>
			<button class="btn btn-outline-soft btn-sm ms-auto" type="button"
				onclick="toggleEditForm()">会員情報修正</button>
		</div>

		<!-- MY-001 : 회원정보 조회 -->
		<div class="surface p-3 p-md-4 mb-4">
			<div class="row g-2">
				<div class="col-md-6">
					<span class="text-muted small">ニックネーム</span>
					<div>${loginUser.nickname}</div>
				</div>
				<div class="col-md-6">
					<span class="text-muted small">メールアドレス</span>
					<div>${loginUser.email}</div>
				</div>
			</div>

			<!-- MY-002 : 회원정보 수정 (선택 기능) -->
			<form id="editForm" novalidate style="display: none;"
				class="row g-2 mt-3"
				action="${pageContext.request.contextPath}/member/update.do"
				method="post" onsubmit="return validateEditForm()">
				<div class="col-md-6">
					<label class="form-label-custom">ニックネーム</label> <input type="text"
						class="form-control form-control-custom" name="nickname"
						id="editNickname" value="${loginUser.nickname}" required>
				</div>
				<div class="col-md-6">
					<label class="form-label-custom">メールアドレス</label> <input
						type="email" class="form-control form-control-custom" name="email"
						id="editEmail" value="${loginUser.email}" required>
				</div>
				<div class="col-12">
					<button class="btn btn-accent btn-sm mt-2" type="submit">保存する</button>
				</div>
			</form>
		</div>

		<h5 class="mb-3">マイお気に入りリスト</h5>

		<c:choose>
			<c:when test="${empty list}">
				<div class="empty-state">
					まだお気に入り作品がありません。<br> <a class="btn btn-accent mt-3"
						href="${pageContext.request.contextPath}/anime/list.do">アニメを見る
						→</a>
				</div>
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

									<div class="meta">${anime.type}· ${anime.year}年 ·
										全${anime.episodes}話</div>
								</a> <a class="card-fav-btn active" title="お気に入り解除"
									onclick="return confirm('お気に入りから削除しますか？');"
									href="${pageContext.request.contextPath}/favorite/delete.do?userId=${sessionScope.userid}&amp;animeId=${anime.animeId}">♥</a>
							</div>
						</div>
					</c:forEach>
				</div>
			</c:otherwise>
		</c:choose>
	</section>

	<%@ include file="/common/footer.jsp"%>
	<script>
		// MY-002 : 회원정보 수정 폼 표시/숨김 토글
		function toggleEditForm() {
			const form = document.getElementById("editForm");
			form.style.display = (form.style.display === "none") ? "block"
					: "none";
		}

		// ブラウザ標準の入力チェックポップアップは言語がブラウザ設定に依存するため、
		// novalidateで無効化し、ここで日本語のメッセージを出す。
		function validateEditForm() {
			const nickname = document.getElementById("editNickname").value
					.trim();
			const email = document.getElementById("editEmail").value.trim();

			if (!nickname || !email) {
				alert("ニックネームとメールアドレスを入力してください。");
				return false;
			}
			return true;
		}
	</script>
</body>
</html>
