<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- header (S) -->
<div id="header">
	<div class="head_lt">
		<h1 class="logo">Soul Flower</h1>
	</div>
	<div class="head_rt">
		<p class="welcome"><span>${sessionScope.loginUser.adminName }</span>님 오늘도 좋은 하루 되세요.</p>
		<p class="logout"><a href="${pageContext.request.contextPath}/admin/logout.do">LOGOUT</a>
	</div>
	<div class="gnb swiper-container">
		<div class="swiper-wrapper">
			<div class="swiper-slide<c:out value="${menu_code eq '01' ? ' on' : '' }" />"><a href="${pageContext.request.contextPath }/m/admin/main.do?menu_code=01">매출 현황</a></div>
			<div class="swiper-slide<c:out value="${menu_code eq '02' ? ' on' : '' }" />"><a href="${pageContext.request.contextPath }/m/admin/real_sales.do?menu_code=02">실매출 현황</a></div>
			<div class="swiper-slide<c:out value="${menu_code eq '03' ? ' on' : '' }" />"><a href="${pageContext.request.contextPath }/m/admin/register.do?menu_code=03">영업 등록</a></div>
			<div class="swiper-slide<c:out value="${menu_code eq '04' ? ' on' : '' }" />"><a href="${pageContext.request.contextPath }/m/admin/area.do?menu_code=04">지역 관리</a></div>
			<div class="swiper-slide<c:out value="${menu_code eq '05' ? ' on' : '' }" />"><a href="${pageContext.request.contextPath }/m/admin/company.do?menu_code=05">상조회사 관리</a></div>
			<div class="swiper-slide<c:out value="${menu_code eq '06' ? ' on' : '' }" />"><a href="${pageContext.request.contextPath }/m/admin/item.do?menu_code=06">품목 관리</a></div>
			<div class="swiper-slide<c:out value="${menu_code eq '07' ? ' on' : '' }" />"><a href="${pageContext.request.contextPath }/m/admin/account.do?menu_code=07">계정 관리</a></div>
			<div class="swiper-slide<c:out value="${menu_code eq '08' ? ' on' : '' }" />"><a href="${pageContext.request.contextPath }/m/admin/work_history.do?menu_code=08">작업내역 보기</a></div>
		</div>
	</div>
</div>
<!-- header (E) -->