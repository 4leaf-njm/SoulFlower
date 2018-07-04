<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script>
/* 메인 페이지로 이동 */
var mobileInfo = new Array('Android', 'iPhone', 'iPod', 'BlackBerry', 'Windows CE', 'SAMSUNG', 'LG', 'MOT', 'SonyEricsson');
var result = false;
for (var info in mobileInfo){		
	if (navigator.userAgent.match(mobileInfo[info]) != null){	
		result = true;
		break;
	}
}
if(result) {
   	location.href="${pageContext.request.contextPath }/admin/login.do";
} else {
	location.href="${pageContext.request.contextPath }/admin/login.do";
}
</script>
