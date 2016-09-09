
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
/***************************************************************************************************
 * 파일명 : name_check_form.jsp
 * 작성자 : 장재현 (jang1945@raonsolution.co.kr)
 * 작성일 : 2016-8-19
 * 내  용 : ( TB_NAME_CHECK )의 입력 및 수정 form출력/저장/취소/입력 값 검증
 ***************************************************************************************************/
%>

<script type="text/javaScript">
<!--
$(function() {

	/**
	 * ( TB_NAME_CHECK ) 저장
	 */
	$("#btn_save").click(function() {
	    if( verifyForm() == false) return;
	    
	    $.ajax({
	    	url		: "<c:url value='/name_check/name_check_save.do'/>",
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
	 * ( TB_NAME_CHECK ) 취소
	 */
	$("#btn_cancel").click(function(e) {
	    history.back(-1);
	    e.preventDefault();
	});
	
	/**
     * ( TB_NAME_CHECK ) 목록
     */
  	$("#btn_list").click(function() {
		$("#formEdit").attr("action", "/name_check/name_check_list.do");
		$("#formEdit").submit();
	});
}); 

/**
 * ( TB_NAME_CHECK ) 입력 값 검증
 */
function verifyForm() {	
	if(Valid.isEmpty($("input[name=check_seq]").val())) {
		alert("일련번호 는 필수입력 항목입니다.");
		return false;
	}
	return true;
}
//-->
</script>


<!-- 목록 이동 폼 -->
<form id="formList" name="formList" class="inline" method="post" action="name_check_list.do">
    <input type="hidden" name="page" value="${pageInfo.page}">
    <input type="hidden" name="search_word" size="20" value="${pageInfo.search_word}">
</form>
<!-- //목록 이동 폼 -->
<div id="con_tit">
<!-- 타이틀 -->
<h3>  수정/추가(method 구분)</h3>
<p class="jump_menu">TB_NAME_CHECK 관리 &gt <b>TB_NAME_CHECK 관리</b></p>
</div>

<!--컨텐츠 본문-->
<div id="content">	
<!-- 폼 -->
<form id="formEdit" class="inline" name="formEdit" method="post">
<input type="hidden" name="method" value="${pageInfo.method}">

<!-- 테이블 내용 -->
<table class="table02">
    		
		<tr>
			<th>일련번호</th>
			<td><input type="text" name="check_seq" size="100" value="${entity.CHECK_SEQ}"></td>
		</tr>		
		<tr>
			<th>식별번호</th>
			<td><input type="text" name="ci" size="100" value="${entity.CI}"></td>
		</tr>		
		<tr>
			<th>폰번호</th>
			<td><input type="text" name="phone_no" size="100" value="${entity.PHONE_NO}"></td>
		</tr>		
		<tr>
			<th>통신사</th>
			<td><input type="text" name="phone_corp" size="100" value="${entity.PHONE_CORP}"></td>
		</tr>		
		<tr>
			<th>생년월일</th>
			<td><input type="text" name="birthday" size="100" value="${entity.BIRTHDAY}"></td>
		</tr>		
		<tr>
			<th>성별</th>
			<td><input type="text" name="gender" size="100" value="${entity.GENDER}"></td>
		</tr>		
		<tr>
			<th>이름</th>
			<td><input type="text" name="check_name" size="100" value="${entity.CHECK_NAME}"></td>
		</tr>		
		<tr>
			<th>확인방법</th>
			<td><input type="text" name="cert_met" size="100" value="${entity.CERT_MET}"></td>
		</tr>		
		<tr>
			<th>이용자 IP</th>
			<td><input type="text" name="ip" size="100" value="${entity.IP}"></td>
		</tr>		
		<tr>
			<th>내_외국인</th>
			<td><input type="text" name="nation" size="100" value="${entity.NATION}"></td>
		</tr>		
		<tr>
			<th>미성년_이름</th>
			<td><input type="text" name="m_check_name" size="100" value="${entity.M_CHECK_NAME}"></td>
		</tr>		
		<tr>
			<th>미만_생일</th>
			<td><input type="text" name="m_birthday" size="100" value="${entity.M_BIRTHDAY}"></td>
		</tr>		
		<tr>
			<th>미만_성별</th>
			<td><input type="text" name="m_gender" size="100" value="${entity.M_GENDER}"></td>
		</tr>		
		<tr>
			<th>미만_국가</th>
			<td><input type="text" name="m_nation" size="100" value="${entity.M_NATION}"></td>
		</tr>		
		<tr>
			<th>사용자 코드</th>
			<td><input type="text" name="user_seq" size="100" value="${entity.USER_SEQ}"></td>
		</tr>		 
	
</table>
<!-- //테이블 내용 -->
</form>
<!-- //폼 -->
 
<!-- 표 이동 버튼 -->
<p class="left" ><img id="btn_list" src="<c:url value='/images/other/b-list01.gif'/>" alt="목록" style="cursor:pointer;" /></p>
<ul class="list_btn">
    <li><img id="btn_save" src="<c:url value='/images/other/b-save01.gif'/>" alt="저장" style="cursor:pointer;" /></li>
    <li><img id="btn_cancel" src="<c:url value='/images/other/b-cancel01.gif'/>" alt="취소" style="cursor:pointer;" /></li>
</ul>
<!-- //표 이동 버튼 -->
</div>
