<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="kr.co.raon.commons.CommonProperty" %>
<%@ page import="kr.co.raon.commons.util.*" %>
<%
CommonProperty cf;
String loginProcURL = "";
cf = new CommonProperty();
loginProcURL = StringUtil.null2Str(cf.getProp("loginProcURL"));
%>
<script  type="text/javaScript" >
$(document).ready(function(){
	/*
	var adminLogin = "${adminLoginYn}";
	
	if(adminLogin != "Y"){
		alert("잘못된 경로로 접근하였습니다.");
		window.history.back();
	}
	*/
	
	$(".btnLogin").click(function(){
		
		var result = verifyForm();
		if(result==false) return;
		 
		//var passwd3=b64($("#logpw").val());
		//$("#logpw").val(passwd3);
		//alert($("#logpw").val()); 
		
		$('#formEdit').attr("action","<%=loginProcURL%>");
		$('#formEdit').submit();
	});
	
	
	$('#formEdit').submit(function(){
		
		var result = verifyForm();
		if(result==false) return false;
		$('#formEdit').attr("action","<%=loginProcURL%>");
		return true;
	});
	
	$('#logemail').focus();
	
	$("#site_type").change(function(){
        $("#selType").val($(this).val());
	});
	
});
function verifyForm() {
	theForm = document.formEdit;
	
	if(Valid.isEmpty(theForm.logemail.value)) {
		alert("이메일을 입력하세요.");
		theForm.logemail.focus();
		return false;
	}else {
		// 정규식 - 이메일 유효성 검사
        var regEmail = /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;

		if(!regEmail.test(theForm.logemail.value)) {

            alert('이메일 주소가 유효하지 않습니다');

            theForm.logemail.focus();

            return false;

        }
	}
	if(Valid.isEmpty(theForm.logpw.value)) {
		alert("비밀번호를 입력하세요.");
		theForm.logpw.focus();
		return false;
	}
	return true;
}
</script>
<div id="warpper">
	<div style="width:600px; margin:250px auto; padding-top:100px;position:relative; background:url(/images/admin/top_bg03.gif) no-repeat -30px 90px;height:300px;">
	<p style="position:absolute; top:40px; left:15px; font-size:35px; font-weight:bold; color:#035385;"><img src="/images/admin/admin_page.gif" alt="관리자 로그인" /></p>
	&nbsp;&nbsp;&nbsp;&nbsp;
		<p style="position:absolute; top: 110px; left:20px;">라온 로그인</p>
	<div class="login" style="margin-right:-5px;">	
		<form id="formEdit" name="formEdit" method="post">
			<input type="hidden" id="selType" name="selType" value="KOR" />
			 <fieldset>
			 <legend>로그인</legend>
				<p class="id"><input type="text" id="logemail" name="logemail" onkeypress="javascript:if(event.keyCode==13) $('#logemail').focus();" /></p>
				<p class="pw"><input type="password" id="logpw" name="logpw" onkeypress="javascript:if(event.keyCode==13) $('#formEdit').submit();" /></p>
			 <p class="btnLogin">
			 <a href="#"><img src="/images/img/btn_login.gif" alt="로그인" /></a>
			 </p>
			 </fieldset>
		</form>
		<div class="memberInfo" style=" margin-left:-190px; margin-top:15px;border:0;"> <a href="#" class="find"></a></div>
	</div>
	</div>
</div>
