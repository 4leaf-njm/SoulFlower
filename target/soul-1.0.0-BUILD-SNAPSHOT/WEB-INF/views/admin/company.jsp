<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="container">
	<div class="content">
		<div class="align-center">
			<table class="tbl_sub">
				<tr>
					<th>번호</th>
					<th>상조회사명</th>
					<th></th>
				</tr>
				<c:choose>
				<c:when test="${empty companyList }">
					<tr>
						<td colspan="3">상조회사를 추가해주세요.</td>
					</tr>
				</c:when>
				<c:otherwise>
				<c:forEach var="company" items="${companyList }" varStatus="status">
					<tr>
						<td>${status.count}</td>
						<td>${company.companyName}</td>
						<td>
							<a href="javascript:remove('${company.companyNo}', '${company.companyName }', '${menu_code }')"><i class="fas fa-minus"></i></a>
						</td>
					</tr>
				</c:forEach>
				</c:otherwise>
				</c:choose>
			</table>
			
			<div class="add_area">
				<form method="post" name="frm_add">
					<div class="row">
						<label for="companyName">상조회사명</label>
						<input type="text" name="companyName" id="companyName" />
					</div>
					<a href="javascript:insert()" class="btn_add">추가하기</a>
				</form>
			</div>
		</div>
	</div>

	<script>
		if('${msg}' != '') {
			alert('${msg}');	
		}
		function insert() {
			var frm = document.frm_add;
			if(frm.companyName.value == '') {
				alert('상조회사명을 입력해주세요.');
				frm.companyName.focus();
				return;
			}
			frm.submit();
		}	
		
		function remove(no, name, menu_code) {
			var result = confirm('[' + name + '] 을(를) 삭제하시겠습니까 ?');
			if(result) 
				location.href = 'removeCompany.do?no=' + no + '&menu_code=' + menu_code;
		}
	</script>
</div>