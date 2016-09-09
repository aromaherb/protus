<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% 
	String ipValue = "192.168.10.124";    // request.getRemoteAddr();
	String msg = ipValue+"는 접근권한이없는 아이피입니다 !!!";
	String login_ok = "N";
	
	System.out.print("ipValue "+ipValue);
	
/*
	if(login_ok == "N"){
		out.println(" <script language='javascript'>\n");
		out.println("	alert('" + msg + "'); \n");
		out.println("	window.history.back(); \n");
		out.println(" </script> \n");		
	}
*/
%>

<script type="text/javaScript" src="./js/jquery.js"/></script>
<script  type="text/javaScript" >
$(document).ready(function(){
	$('#adminLoginForm').submit();
});
</script>
<form id="adminLoginForm" name="adminLoginForm" method="post" action="../auth/login.do">
	<input type="hidden" id="adminLoginYn" name="adminLoginYn" value="Y">
</form>
