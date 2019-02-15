<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="container">
	<div class="content">
		<div class="align-center">
			<div class="add_area">
				<form method="post" name="frm_add">
					<div class="row">
						<label for="areaName">지역명</label>
						<input type="text" name="areaName" id="areaName" />
					</div>
					<a href="javascript:insert()" class="btn_add">추가하기</a>
				</form>
			</div>
			<table class="tbl_sub">
				<tr>
					<th>번호</th>
					<th>지역명</th>
					<th></th>
				</tr>
				<c:choose>
				<c:when test="${empty areaList }">
					<tr>
						<td colspan="3">지역을 추가해주세요.</td>
					</tr>
				</c:when>
				<c:otherwise>
				<c:forEach var="area" items="${areaList }" varStatus="status">
					<tr>
						<td>${status.count}</td>
						<td>${area.areaName}</td>
						<td>
							<a href="javascript:remove('${area.areaNo}', '${area.areaName }', '${menu_code }')"><i class="fas fa-minus"></i></a>
						</td>
					</tr>
				</c:forEach>
				</c:otherwise>
				</c:choose>
			</table>
		</div>
	</div>

	<script>
		if('${msg}' != '') {
			alert('${msg}');	
		}
		function insert() {
			var frm = document.frm_add;
			if(frm.areaName.value == '') {
				alert('지역명을 입력해주세요.');
				frm.areaName.focus();
				return;
			}
			frm.submit();
		}	
		
		function remove(no, name, menu_code) {
			var result = confirm('[' + name + '] 을(를) 삭제하시겠습니까 ?');
			if(result) 
				location.href = 'removeArea.do?no=' + no + '&menu_code=' + menu_code;
		}
	</script>
</div>