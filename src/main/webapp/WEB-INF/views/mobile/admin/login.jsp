<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="login_wrap">
	<div class="login">
		<h1 class="title">소울플라워 관리자</h1>
		<form method="post" name="frm_login">
			<input type="text" name="adminId" placeholder="아이디" value="${admin.adminId }" />
			<input type="password" name="adminPwd" placeholder="비밀번호" value="${admin.adminPwd }" />
			<button type="button" class="btn_login" onclick="login();">로그인</button>
		</form>
		<p class="go_join">
			<i class="fas fa-caret-right"></i>아이디가 없으신가요?
			<a href="${pageContext.request.contextPath }/m/admin/join.do" class="btn_gojoin">회원가입 하기</a>
		</p>
	</div>
	<script>
		if('${msg}' != '') {
			alert('${msg}');	
		}
		
		function login() {
			var frm = document.frm_login;
			if(frm.adminId.value == '') {
				alert('아이디를 입력해주세요.');
				frm.adminId.focus();
				return false;
			} else if (frm.adminPwd.value == '') {
				alert('비밀번호를 입력해주세요.');
				frm.adminPwd.focus();
				return false;
			}
			frm.submit();
		}
	</script>
</div>