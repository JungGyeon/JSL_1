<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    String activePage = (String) request.getAttribute("activePage");

    if (activePage == null) {
        activePage = "";
    }
%>

<script>
	// 깜빡이 방지
	if (localStorage.getItem("aniverseTheme") === "light") {
		document.body.classList.add("light-mode");
	}
</script>

<nav class="navbar navbar-expand-md navbar-custom py-3">
	<div class="container">

		<a class="navbar-brand brand"
			href="${pageContext.request.contextPath}/main.do">ANI<span>VERSE</span></a>

		<div class="d-flex align-items-center gap-2 order-md-3">

			<button class="spec-toggle" id="specToggleBtn" type="button"
				onclick="toggleSpec()" title="画面に要件ID(例: ANI-001)を表示します">⌘
				仕様書オーバーレイ</button>

			<c:choose>
				<c:when test="${not empty sessionScope.userid}">
					<span class="text-muted small me-2 d-none d-sm-inline">${sessionScope.nickname}さん</span>
					<a class="btn btn-outline-soft btn-sm"
						href="${pageContext.request.contextPath}/member/logout.do">ログアウト</a>
				</c:when>
				<c:otherwise>
					<a class="btn btn-outline-soft btn-sm"
						href="${pageContext.request.contextPath}/member/loginForm.do">ログイン</a>
				</c:otherwise>
			</c:choose>

			<button class="btn btn-outline-soft btn-sm" type="button"
				id="themeToggleBtn" onclick="toggleTheme()"
				title="ダークモード/ライトモード切り替え">🌙</button>

			<button class="navbar-toggler border-0" type="button"
				data-bs-toggle="collapse" data-bs-target="#navMain"
				style="filter: invert(1);">
				<span class="navbar-toggler-icon"></span>
			</button>
		</div>

		<div class="collapse navbar-collapse order-md-2" id="navMain">
			<ul class="navbar-nav mx-md-3 gap-1 mt-3 mt-md-0">
				<li class="nav-item"><a
					class="nav-link nav-link-custom <%="home".equals(activePage) ? "active" : ""%>"
					href="${pageContext.request.contextPath}/main.do">ホーム</a></li>
				<li class="nav-item"><a
					class="nav-link nav-link-custom <%="list".equals(activePage) ? "active" : ""%>"
					href="${pageContext.request.contextPath}/anime/list.do">アニメ一覧</a></li>
				<li class="nav-item"><a
					class="nav-link nav-link-custom <%="recommend".equals(activePage) ? "active" : ""%>"
					href="${pageContext.request.contextPath}/recommend/list.do">おすすめ</a></li>
				<li class="nav-item"><a
					class="nav-link nav-link-custom <%="mypage".equals(activePage) ? "active" : ""%>"
					href="${pageContext.request.contextPath}/favorite/list.do?userId=${sessionScope.userid}">マイページ</a></li>
			</ul>
		</div>

	</div>
</nav>
