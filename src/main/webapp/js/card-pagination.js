/* =========================================================
   애니 카드 그리드(3줄 단위) 클라이언트 페이지네이션
   - list.jsp / search.jsp / recommend.jsp / mypage.jsp 에서 공용으로 사용
   - 서버에서는 전체 목록을 그대로 내려주고, 화면에서는 반응형 열 수(2/3/4열)를
     계산해 "3줄 분량"만 보여주고 나머지는 숨긴 뒤 페이지 버튼으로 전환한다.
   - 마크업 조건: <div id="xxxGrid" class="row g-3 anime-card-grid">...</div>
                 바로 뒤에 <div id="xxxGridPagination" class="card-pagination"></div>
   ========================================================= */
(function () {
	"use strict";

	var ROWS_PER_PAGE = 3;
	var PAGE_WINDOW_SIZE = 10; // 한 번에 보여줄 페이지 번호 개수

	// 마크업의 반응형 그리드 클래스(col-6 col-md-4 col-lg-3)와 동일한 기준으로
	// 열 개수를 계산한다. 예전에는 카드 요소의 offsetTop을 비교해서 열 수를
	// 추정했는데, 페이지 전환으로 대부분의 카드가 display:none 상태가 되면
	// 숨겨진 요소들의 offsetTop이 전부 0으로 잡혀 열 수가 잘못 계산되고,
	// 그 결과 페이지마다 카드 개수가 들쭉날쭉해지는 문제가 있었다.
	// 뷰포트 너비로 직접 계산하면 카드의 표시 상태와 무관하게 항상 정확하다.
	function getColumnCount() {
		var w = document.documentElement.clientWidth || window.innerWidth;
		if (w >= 992) return 4; // col-lg-3
		if (w >= 768) return 3; // col-md-4
		return 2;               // col-6
	}

	function render(grid, container) {
		var items = Array.prototype.slice.call(grid.children);

		if (!items.length) {
			container.innerHTML = "";
			return;
		}

		var columns = getColumnCount();
		var itemsPerPage = columns * ROWS_PER_PAGE;
		var totalPages = Math.max(1, Math.ceil(items.length / itemsPerPage));

		var current = parseInt(grid.getAttribute("data-page"), 10) || 1;
		if (current > totalPages) current = totalPages;
		if (current < 1) current = 1;
		grid.setAttribute("data-page", String(current));

		items.forEach(function (el, idx) {
			var page = Math.floor(idx / itemsPerPage) + 1;
			el.style.display = (page === current) ? "" : "none";
		});

		renderControls(grid, container, current, totalPages);
	}

	function renderControls(grid, container, current, totalPages) {
		if (totalPages <= 1) {
			container.innerHTML = "";
			return;
		}

		// 페이지가 많아지면 전부 나열하지 않고, 현재 페이지가 속한 10개 단위
		// 구간(1~10, 11~20, ...)만 보여준다.
		var windowStart = Math.floor((current - 1) / PAGE_WINDOW_SIZE) * PAGE_WINDOW_SIZE + 1;
		var windowEnd = Math.min(windowStart + PAGE_WINDOW_SIZE - 1, totalPages);

		var atFirstBlock = windowStart === 1;
		var atLastBlock = windowEnd === totalPages;

		var html = '<ul class="pagination">';

		// 처음으로
		html += '<li class="page-item' + (atFirstBlock ? " disabled" : "") + '">'
			+ '<a href="#" class="page-link" data-page="1">&laquo;</a></li>';

		// 이전 구간(10개 단위)
		html += '<li class="page-item' + (atFirstBlock ? " disabled" : "") + '">'
			+ '<a href="#" class="page-link" data-page="' + (windowStart - 1) + '">&lsaquo;</a></li>';

		for (var p = windowStart; p <= windowEnd; p++) {
			html += '<li class="page-item' + (p === current ? " active" : "") + '">'
				+ '<a href="#" class="page-link" data-page="' + p + '">' + p + "</a></li>";
		}

		// 다음 구간(10개 단위)
		html += '<li class="page-item' + (atLastBlock ? " disabled" : "") + '">'
			+ '<a href="#" class="page-link" data-page="' + (windowEnd + 1) + '">&rsaquo;</a></li>';

		// 마지막으로
		html += '<li class="page-item' + (atLastBlock ? " disabled" : "") + '">'
			+ '<a href="#" class="page-link" data-page="' + totalPages + '">&raquo;</a></li>';

		html += "</ul>";
		container.innerHTML = html;

		var links = container.querySelectorAll(".page-link");
		for (var i = 0; i < links.length; i++) {
			links[i].addEventListener("click", function (e) {
				e.preventDefault();
				var target = parseInt(this.getAttribute("data-page"), 10);
				if (!target || target < 1 || target > totalPages || target === current) {
					return;
				}
				grid.setAttribute("data-page", String(target));
				render(grid, container);
				grid.scrollIntoView({ behavior: "smooth", block: "start" });
			});
		}
	}

	function collectGrids() {
		var grids = document.querySelectorAll(".anime-card-grid[id]");
		var pairs = [];

		grids.forEach(function (grid) {
			var container = document.getElementById(grid.id + "Pagination");
			if (container) {
				pairs.push({ grid: grid, container: container });
			}
		});

		return pairs;
	}

	function init() {
		var pairs = collectGrids();
		if (!pairs.length) return;

		pairs.forEach(function (pair) {
			pair.grid.setAttribute("data-page", "1");
			render(pair.grid, pair.container);
		});

		var resizeTimer = null;
		window.addEventListener("resize", function () {
			clearTimeout(resizeTimer);
			resizeTimer = setTimeout(function () {
				// 반응형 breakpoint가 바뀌면 열 수가 달라지므로 1페이지로 재계산
				pairs.forEach(function (pair) {
					pair.grid.setAttribute("data-page", "1");
					render(pair.grid, pair.container);
				});
			}, 200);
		});
	}

	if (document.readyState === "loading") {
		document.addEventListener("DOMContentLoaded", init);
	} else {
		init();
	}
})();
