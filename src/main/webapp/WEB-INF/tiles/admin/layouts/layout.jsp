<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<jsp:useBean id="today" class="java.util.Date" />
<fmt:formatDate value='${today}' pattern='yyyyMMddHHmmddss' var="ver"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="x-ua-compatible" content="ie=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<title>소울플라워 관리자</title>
<link href="${pageContext.request.contextPath }/resources/css/reset.css?ver=${ver}" rel="stylesheet">
<link href="${pageContext.request.contextPath }/resources/css/commons.css?ver=${ver}" rel="stylesheet">
<link href="${pageContext.request.contextPath }/resources/css/admin.css?ver=${ver}" rel="stylesheet">
<link href="${pageContext.request.contextPath }/resources/plugin/scrollbar/jquery.basic.css" rel="stylesheet">
<link href="${pageContext.request.contextPath }/resources/plugin/scrollbar/jquery.scrollbar.css" rel="stylesheet">
<link href="${pageContext.request.contextPath }/resources/plugin/calendar/calendar.css" rel="stylesheet">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="${pageContext.request.contextPath }/resources/plugin/scrollbar/jquery.scrollbar.min.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/default.js?ver=${ver}"></script>
<script src="${pageContext.request.contextPath }/resources/plugin/calendar/calendar.js"></script>
</head>
<body>
	<!-- wrapper (S) -->
	<div id="wrapper" class="scrollbar-dynamic">
		<tiles:insertAttribute name="header" />
		<tiles:insertAttribute name="content" />
		<tiles:insertAttribute name="footer" />
	</div>
	<!-- wrapper (E) -->
</body>
</html>