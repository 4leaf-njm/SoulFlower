<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="container">
	<div class="content">
		<div class="history">
			<ul>
				<c:choose>
				<c:when test="${empty historyList }">
					<li>* 작업 내역이 없습니다.</li>
				</c:when>
				<c:otherwise>
					<c:forEach var="history" items="${historyList }">
						<li>[ <fmt:formatDate value="${history.historyDate}" pattern="yyyy-MM-dd HH:mm:ss"/> ]&nbsp;&nbsp;&nbsp;${history.historyText}</li>
					</c:forEach>
				</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</div>
</div>