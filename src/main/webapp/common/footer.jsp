<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<footer class="footer text-center req-anchor">
	<span class="req-id"
		style="position: static; display: inline-block; margin-bottom: .5rem;">SYS-002</span>
	<div>ANIVERSE — アニメおすすめサイト プロジェクト · JSP + Oracle + Bootstrap</div>
</footer>

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
<script>
	var themeBtn = document.getElementById("themeToggleBtn");
	function syncThemeIcon() {
		if (!themeBtn)
			return;
		themeBtn.textContent = document.body.classList.contains("light-mode") ? "☀️"
				: "🌙";
	}
	function toggleTheme() {
		document.body.classList.toggle("light-mode");
		var isLight = document.body.classList.contains("light-mode");
		localStorage.setItem("aniverseTheme", isLight ? "light" : "dark");
		syncThemeIcon();
	}
	syncThemeIcon();

	function toggleSpec() {
		document.body.classList.toggle("spec-mode");
		var btn = document.getElementById("specToggleBtn");
		if (btn)
			btn.classList.toggle("on");
	}
</script>
