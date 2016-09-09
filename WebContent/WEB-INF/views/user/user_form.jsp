<%@page import="kr.co.protus.controllers.base.UserController"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
/***************************************************************************************************
 * 파일명 : user_form.jsp
 * 작성자 : 장재현 (jang1945@raonsolution.co.kr)
 * 작성일 : 2016-7-11
 * 내  용 : ( TB_USER )의 입력 및 수정 form출력/저장/취소/입력 값 검증
 ***************************************************************************************************/
%>

<script type="text/javaScript">
<!--
$(function() {

	/**
	 * ( TB_USER ) 저장
	 */
	$("#btn_save").click(function() {
//	    if( verifyForm() == false) return;
	    
	    $.ajax({
	    	url		: "<c:url value='/user/user_save.do'/>",
	        type	: "POST",
	        data	: $("#formEdit").serialize(),
	        dataType: "text",
	        success : function(data) {
	        	data = data.replace(/[<][^>]*[>]/gi, '');
	            var jData = JSON.parse(data);
	            if(jData.result_cd == "200") {
	            	alert("저장되었습니다.");
	        		$("form[name=formList]").submit();
	            }
	            else {
	                alert(jData.result_msg);
	            }
	        },
	        error : function(xhr,status,error) {
	            alert(error);
	        }
	    });
	});
	
	/**
	 * ( TB_USER ) 취소
	 */
	$("#btn_cancel").click(function(e) {
	    history.back(-1);
	    e.preventDefault();
	});
	
	/**
     * ( TB_USER ) 목록
     */
  	$("#btn_list").click(function() {
		$("#formEdit").attr("action", "/user/user_list.do");
		$("#formEdit").submit();
	});
}); 

/**
 * ( TB_USER ) 입력 값 검증
 */
/* function verifyForm() {	
	if(Valid.isEmpty($("input[name=user_seq]").val())) {
		alert(" 는 필수입력 항목입니다.");
		return false;
	}
	return true;
} */



//-->
</script>

<!-- 목록 이동 폼 -->
<form id="formList" name="formList" class="inline" method="post" action="user_list.do">
    <input type="hidden" name="page" value="${pageInfo.page}">
    <input type="hidden" name="search_word" size="20" value="${pageInfo.search_word}">
</form>
	<div class="content">
 			<ul class="tab">
				<li class="on">사용자관리</li>
			</ul>
			<div class="visual">
				 <h2>
				 	protus<br>
				 	사용자관리
				 </h2>
			</div> <!--위에 비주얼추가될 -->
		 	<div class="tab_con">
 			<form id="formEdit" class="inline" name="formEdit" method="post">
				<input type="hidden" name="method" value="${pageInfo.method}">
 				<div class="board_write">
 					<table>
	 					<tr>
	 						<td><label class="input_lab" for="email">이메일(ID) </label></td>
	 						<td><input type="text" name="email" size="100" maxlength="120" value="${entity.EMAIL}" placeholder="이메일을 입력해 주세요."></td>	 					
	 					</tr>
	 					<tr>
	 						<td><label class="input_lab" for="user_type">암호</label></td>
	 						<td><input type="password" name="passwd" size="100" maxlength="30" value="${entity.PASSWD}"></td>
	 					</tr>
	 					<tr>
	 						<td><label class="input_lab" for="user_name">사용자명 </label></td>
	 						<td><input type="text" name="user_name" size="100" maxlength="60" value="${entity.USER_NAME}" placeholder="10자 이내로 입력해 주세요."></td>
	 					</tr>
	 					<tr>
	 						<td><label class="input_lab" for="user_name">연락처</label></td>
	 						<td><input type="text" name="hp_num" size="100" maxlength="11" value="${entity.HP_NUM}" placeholder="'-'하이픈없이 입력해 주세요(01012345678)"></td>
	 					</tr>
	 					<tr>
	 						<td><label class="input_lab" for="gender">성별 </label></td>
	 						<td>
	 						<input type="radio" name="gender"  value="1">남자
	 						<input type="radio" name="gender"  value="2">여자
	 						<%-- <c:set var="gender" value="${entity.GENDER}"/>
	 						<c:if test="${gender eq 1}">
	 							<input type="radio" name="gender"  value="1" checked="checked">남자
	 							<input type="radio" name="gender"  value="2">여자
	 						</c:if>
	 						<c:if test="${gender eq 2">
	 							<input type="radio" name="gender"  value="1">남자
	 							<input type="radio" name="gender"  value="2" checked="checked">여자
	 						</c:if>
	 						<c:if test="${empty gender">
	 							<input type="radio" name="gender"  value="1">남자
	 							<input type="radio" name="gender"  value="2">여자
	 						</c:if> --%>
	 							<%-- <input type="" name="grade" size="100" value="${entity.GENDER}"> --%>
	 						</td>
	 					</tr>
						<tr>
	 						<td><label class="input_lab" for="grade">학년 </label></td>
	 						<td>
	 							<select name="grade">
	 								<option value="">학년 선택</option>
	 								<option value="초1">초1</option>
	 								<option value="초2">초2</option>
	 								<option value="초3">초3</option>
	 								<option value="초4">초4</option>
	 								<option value="초5">초5</option>
	 								<option value="초6">초6</option>
	 								<option value="중1">중1</option>
	 								<option value="중2">중2</option>
	 								<option value="중3">중3</option>
	 								<option value="고1">고1</option>
	 								<option value="고2">고2</option>
	 								<option value="고3">고3</option>
	 								<option value="성인">성인</option>	 								
	 							</select>
	 							<%-- <input type="" name="grade" size="100" value="${entity.GRADE}"> --%>
	 						</td>
	 					</tr>
	 					<tr>
	 						<td><label class="input_lab" for="grade">유저타입 </label></td>
	 						<td>
	 							<select name="user_type">
	 								<option value="">타입 선택</option>
	 								<option value="UT000">관리자</option>
	 								<option value="UT001">원장</option>
	 								<option value="UT002">강사</option>
	 								<option value="UT003">학생</option>
	 							</select>
	 							<%-- <input type="text" name="user_type" size="100" maxlength="5" value="${entity.USER_TYPE}"> --%>
	 						</td>
	 					</tr>
	 					<tr>
	 						<td><label class="input_lab" for="grade">유저권한 </label></td>
	 						<td><input type="text" name="user_auth" size="100" maxlength="5" value="${entity.USER_AUTH}"></td>
	 					</tr>	 										 					
 					</table>

 					<input type="hidden" name="user_seq" size="100" value="${entity.USER_SEQ}">
 					<input type="hidden" name="remove_yn" size="100" value="${entity.REMOVE_YN}">
 					<input type="hidden" name="remove_date" size="100" value="${entity.REMOVE_DATE}">
 				</div>
 				<ul class="send">
	 				<li class="write_button"><input id="btn_save" type="button" value="등록"></li>
	 				<li class="cancle_button"><input id="btn_cancel" type="button" value="취소" ></li>
				</ul> 			
			</form>
 			</div>
</div>
