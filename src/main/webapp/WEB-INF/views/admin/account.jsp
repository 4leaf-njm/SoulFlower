<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="container">
	<div class="content">
		<h2 class="acc_title">계정 승인</h2>
		<table class="acc_tbl" id="tblApprove">
			<tr>
				<th>등록일</th>
				<th>성명</th>
				<th>비고</th>
			</tr>
			<c:choose>
			<c:when test="${empty adminList }">
			<tr>
				<td colspan="3">가입 신청한 사용자가 없습니다.</td>
			</tr>
			</c:when>
			<c:otherwise>
			<c:forEach var="admin" items="${adminWaitList }">
			<tr>
				<td><fmt:formatDate value="${admin.adminRegdate }" pattern="yyyy-MM-dd"/></td>
				<td>${admin.adminName } (${admin.adminId })</td>
				<td>
					<a href="javascript:approve('${admin.adminId }', 'Y')" class="btn">승인</a>
					<a href="javascript:approve('${admin.adminId }', 'N')" class="btn">거부</a>
				</td>
			</tr>
			</c:forEach>
			</c:otherwise>
			</c:choose>
		</table>
		<h2 class="acc_title">권한 부여</h2>
		<table class="acc_tbl">
			<tr>
				<th>성명</th>
				<c:forEach var="role" items="${roleList }">
					<th>${role.roleText }</th>
				</c:forEach>
				<th>비고</th>
			</tr>
			<c:forEach var="admin" items="${adminList }" >
			<c:if test="${admin.adminRootyn ne 'Y'}">
			<tr>
				<td>${admin.adminName } (${admin.adminId })</td>
				<c:forEach var="role" items="${roleList }">
					<td><input type="checkbox" name="roleList${admin.adminId }" value="${role.roleNo }"></td>
				</c:forEach>
				<td><a href="javascript:authority('${admin.adminId }')" class="btn">적용</a></td>
			</tr>
			</c:if>
			</c:forEach>
		</table>
	</div>
	<script>
		$(document).ready(function(){
			var authMap = '${authMap}';
			authMap = authMap.replace('{', '').replace('}', '');
			authMap = authMap.split(', ');
			$.each(authMap, function(index, value) {
				var key = value.split('=')[0];
				var val = value.split('=')[1].split('/');
				$.each(val, function(i, v) {
					$('input:checkbox[name=roleList' + key + '][value=' + v + ']').prop('checked', true);
				});
			});
		});
		function approve(adminId, useyn) {
			var result;
			if(useyn == 'Y')
				result = confirm('승인 하시겠습니까 ?');
			else
				result = confirm("승인 거부하시겠습니까 ?");
			if(result) {
				$.ajax({
					url: 'approveAdmin.do',
					type: 'post',
					data: {'adminId': adminId, 'useyn': useyn},
					dataType: 'json',
					success: function(data) {
						if(data.msg != '') {
							alert(data.msg);
							location.reload();
						}
					},
					error: function() {
						alert('error');
					}
				});
			}
		}
		
		function authority(adminId) {
			var result = confirm('권한을 적용하시겠습니까 ?');
			if(result) {
				var roleList = '';
				var role = $('input:checkbox[name=roleList' + adminId + ']:checked');
				$.each(role, function(index, value) {
					roleList += role.length-1 == index ? value.value : value.value + ', ';
				});
				 $.ajax({
					url: 'authorityAdmin.do',
					type: 'post',
					data: {'adminId': adminId, 'roles': roleList},
					dataType: 'json',
					success: function(data) {
						if(data.msg != '') {
							alert(data.msg);
							location.reload();
						}
					},
					error: function(a, b, c) {
						alert('error' + a + ' ' + b + ' ' + c);
					}
				});
			}
		}
	</script>
</div>