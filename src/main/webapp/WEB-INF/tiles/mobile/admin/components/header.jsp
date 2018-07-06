<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- header (S) -->
<div id="header">
	모바일 헤더
	<%-- <div class="head_rt">
		<p class="welcome"><span>${sessionScope.loginUser.adminName }</span>님 오늘도 좋은 하루 되세요.</p>
		<p class="logout"><a href="${pageContext.request.contextPath}/admin/logout.do">LOGOUT</a>
	</div>
	<div class="menu">
		<h1 class="logo">Soul Flower</h1>
		<div class="gnb">
			<ul>
				<li <c:out value="${menu_code eq '01' ? 'class=on' : '' }" />><a href="${pageContext.request.contextPath }/admin/main.do?menu_code=01">매출 현황</a></li>
				<li <c:out value="${menu_code eq '02' ? 'class=on' : '' }" />><a href="${pageContext.request.contextPath }/admin/register.do?menu_code=02">영업 등록</a></li>
				<li <c:out value="${menu_code eq '03' ? 'class=on' : '' }" />><a href="${pageContext.request.contextPath }/admin/area.do?menu_code=03">지역 관리</a></li>
				<li <c:out value="${menu_code eq '04' ? 'class=on' : '' }" />><a href="${pageContext.request.contextPath }/admin/company.do?menu_code=04">상조회사 관리</a></li>
				<li <c:out value="${menu_code eq '05' ? 'class=on' : '' }" />><a href="${pageContext.request.contextPath }/admin/item.do?menu_code=05">품목 관리</a></li>
				<li <c:out value="${menu_code eq '06' ? 'class=on' : '' }" />><a href="${pageContext.request.contextPath }/admin/account.do?menu_code=06">계정 관리</a></li>
				<li <c:out value="${menu_code eq '07' ? 'class=on' : '' }" />><a href="${pageContext.request.contextPath }/admin/work_history.do?menu_code=07">작업내역 보기</a></li>
			</ul>
		</div>
	</div> --%>
</div>
<!-- header (E) -->