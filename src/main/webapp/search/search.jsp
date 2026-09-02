<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List"%>
<%@ page import="model.AnimeDTO"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ja">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>アニメ一覧 — ANIVERSE</title>

<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/css/bootstrap.min.css"
      rel="stylesheet">

<link href="${pageContext.request.contextPath}/css/style.css"
      rel="stylesheet">
</head>

<%
List<AnimeDTO> list = (List<AnimeDTO>)request.getAttribute("list");
String title = (String)request.getAttribute("title");

if(title == null){
    title = "";
}
%>	

<body>

<nav class="navbar navbar-expand-md navbar-custom py-3">

    <div class="container">

        <a class="navbar-brand brand"
           href="${pageContext.request.contextPath}/index.html">
            ANI<span>VERSE</span>
        </a>

        <div class="collapse navbar-collapse">

            <ul class="navbar-nav mx-md-3 gap-1">

                <li class="nav-item">
                    <a class="nav-link nav-link-custom"
                       href="${pageContext.request.contextPath}/index.html">
                        ホーム
                    </a>
                </li>

                <li class="nav-item">
                    <a class="nav-link nav-link-custom active"
                       href="${pageContext.request.contextPath}/anime/list.do">
                        アニメ一覧
                    </a>
                </li>

                <li class="nav-item">
                    <a class="nav-link nav-link-custom"
                       href="${pageContext.request.contextPath}/recommend/recommend.html">
                        おすすめ
                    </a>
                </li>

            </ul>

        </div>

    </div>

</nav>



</body>
<footer class="footer text-center">

    <div>
        ANIVERSE — アニメおすすめサイト プロジェクト
    </div>

</footer>

</html>


