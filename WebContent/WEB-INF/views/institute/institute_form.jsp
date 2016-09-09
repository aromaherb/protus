
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
/***************************************************************************************************
 * 파일명 : institute_form.jsp
 * 작성자 : 장재현 (jang1945@raonsolution.co.kr)
 * 작성일 : 2016-8-19
 * 내  용 : ( TB_INSTITUTE )의 입력 및 수정 form출력/저장/취소/입력 값 검증
 ***************************************************************************************************/
%>

<script type="text/javaScript">
<!--
$(function() {

	/**
	 * ( TB_INSTITUTE ) 저장
	 */
	$("#btn_save").click(function() {
	    if( verifyForm() == false) return;
	    
	    $.ajax({
	    	url		: "<c:url value='/institute/institute_save.do'/>",
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
	 * ( TB_INSTITUTE ) 취소
	 */
	$("#btn_cancel").click(function(e) {
	    history.back(-1);
	    e.preventDefault();
	});
	
	/**
     * ( TB_INSTITUTE ) 목록
     */
  	$("#btn_list").click(function() {
		$("#formEdit").attr("action", "/institute/institute_list.do");
		$("#formEdit").submit();
	});
}); 

/**
 * ( TB_INSTITUTE ) 입력 값 검증
 */
function verifyForm() {	
	if(Valid.isEmpty($("input[name=institute_seq]").val())) {
		alert("학원일련번호 는 필수입력 항목입니다.");
		return false;
	}
	return true;
}
//-->
</script>


<!-- 목록 이동 폼 -->
<form id="formList" name="formList" class="inline" method="post" action="institute_list.do">
    <input type="hidden" name="page" value="${pageInfo.page}">
    <input type="hidden" name="search_word" size="20" value="${pageInfo.search_word}">
</form>
<!-- //목록 이동 폼 -->
<div id="con_tit">
<!-- 타이틀 -->
<h3>  수정/추가(method 구분)</h3>
<p class="jump_menu">TB_INSTITUTE 관리 &gt <b>TB_INSTITUTE 관리</b></p>
</div>

<!--컨텐츠 본문-->
<div id="content">	
<!-- 폼 -->
<form id="formEdit" class="inline" name="formEdit" method="post">
<input type="hidden" name="method" value="${pageInfo.method}">

<!-- 테이블 내용 -->
<table class="table02">
    		
		<tr>
			<th>학원일련번호</th>
			<td><input type="text" name="institute_seq" size="100" value="${entity.INSTITUTE_SEQ}"></td>
		</tr>		
		<tr>
			<th>학원명</th>
			<td><input type="text" name="institute_name" size="100" value="${entity.INSTITUTE_NAME}"></td>
		</tr>		
		<tr>
			<th>우편번호</th>
			<td><input type="text" name="zip_code" size="100" value="${entity.ZIP_CODE}"></td>
		</tr>		
		<tr>
			<th>주소</th>
			<td><input type="text" name="address" size="100" value="${entity.ADDRESS}"></td>
		</tr>		
		<tr>
			<th>주소상세</th>
			<td><input type="text" name="address_sub" size="100" value="${entity.ADDRESS_SUB}"></td>
		</tr>		
		<tr>
			<th>사업자 등록번호</th>
			<td><input type="text" name="register_num" size="100" value="${entity.REGISTER_NUM}"></td>
		</tr>		
		<tr>
			<th>연락처</th>
			<td><input type="text" name="tel" size="100" value="${entity.TEL}"></td>
		</tr>		
		<tr>
			<th>학원코드</th>
			<td><input type="text" name="institute_cd" size="100" value="${entity.INSTITUTE_CD}"></td>
		</tr>		
		<tr>
			<th>계좌은행</th>
			<td><input type="text" name="bank" size="100" value="${entity.BANK}"></td>
		</tr>		
		<tr>
			<th>예금주</th>
			<td><input type="text" name="account_holder" size="100" value="${entity.ACCOUNT_HOLDER}"></td>
		</tr>		
		<tr>
			<th>계좌번호</th>
			<td><input type="text" name="account_no" size="100" value="${entity.ACCOUNT_NO}"></td>
		</tr>		
		<tr>
			<th>대표자 이름</th>
			<td><input type="text" name="ceo" size="100" value="${entity.CEO}"></td>
		</tr>		
		<tr>
			<th>지역코드</th>
			<td><input type="text" name="area_cd" size="100" value="${entity.AREA_CD}"></td>
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
