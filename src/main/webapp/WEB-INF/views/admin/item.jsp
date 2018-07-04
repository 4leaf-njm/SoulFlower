<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="container">
	<div class="content">
		<div class="align-center">
			<table class="tbl_sub">
				<tr>
					<th width="60">번호</th>
					<th width="170">품목명</th>
					<th width="70">단위</th>
					<th></th>
				</tr>
				<c:choose>
				<c:when test="${empty itemList }">
					<tr>
						<td colspan="4">품목을 추가해주세요.</td>
					</tr>
				</c:when>
				<c:otherwise>
				<c:forEach var="item" items="${itemList }" varStatus="status">
					<tr>
						<td>${status.count}</td>
						<td>${item.itemName}</td>
						<td>${item.itemUnit}</td>
						<td>
							<a href="javascript:remove('${item.itemNo}', '${item.itemName }', '${menu_code }')"><i class="fas fa-minus"></i></a>
						</td>
					</tr>
				</c:forEach>
				</c:otherwise>
				</c:choose>
			</table>
			
			<div class="add_area">
				<form method="post" name="frm_add">
					<div class="row">
						<label for="itemName">품목명</label>
						<input type="text" name="itemName" id="itemName" />
					</div>
					<div class="row">
						<label for="itemUnit">단위</label>
						<input type="text" name="itemUnit" id="itemUnit" />
					</div>
					<a href="javascript:insert()" class="btn_add">추가하기</a>
				</form>
			</div>
		</div>
	</div>

	<script>
		function insert() {
			var frm = document.frm_add;
			if(frm.itemName.value == '') {
				alert('품목명 입력해주세요.');
				frm.itemName.focus();
				return;
			} else if (frm.itemUnit.value == '') {
				alert('단위를 입력해주세요.');
				frm.itemUnit.focus();
				return;
			}
			frm.submit();
		}	
		
		function remove(no, name, menu_code) {
			var result = confirm('[' + name + '] 을(를) 삭제하시겠습니까 ?');
			if(result) 
				location.href = 'removeItem.do?no=' + no + '&menu_code=' + menu_code;
		}
	</script>
</div>