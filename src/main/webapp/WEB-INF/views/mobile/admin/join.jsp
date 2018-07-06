<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="login_wrap">
	<div class="login">
		<h1 class="title">소울플라워 관리자</h1>
		<form method="post" name="frm_join">
			<input type="text" name="adminName" placeholder="이름" value="${admin.adminName }" />
			<input type="text" name="adminId" placeholder="아이디" value="${admin.adminId }" />
			<input type="password" name="adminPwd" placeholder="비밀번호" value="${admin.adminPwd }" />
			<button type="button" class="btn_join" onclick="join();">회원가입</button>
		</form>
	</div>
	<script>
		if('${msg}' != '') {
			alert('${msg}');	
		}
		
		function join() {
			var frm = document.frm_join;
			if(frm.adminName.value == '') {
				alert('이름을 입력해주세요.');
				frm.adminName.focus();
				return false;
			} else if(frm.adminId.value == '') {
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