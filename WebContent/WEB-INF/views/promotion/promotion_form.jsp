
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
/***************************************************************************************************
 * 파일명 : promotion_form.jsp
 * 작성자 : 장재현 (jang1945@raonsolution.co.kr)
 * 작성일 : 2016-8-19
 * 내  용 : ( TB_PROMOTION )의 입력 및 수정 form출력/저장/취소/입력 값 검증
 ***************************************************************************************************/
%>

<script type="text/javaScript">
<!--
$(function() {

	/**
	 * ( TB_PROMOTION ) 저장
	 */
	$("#btn_save").click(function() {
	    if( verifyForm() == false) return;
	    
	    $.ajax({
	    	url		: "<c:url value='/promotion/promotion_save.do'/>",
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
	 * ( TB_PROMOTION ) 취소
	 */
	$("#btn_cancel").click(function(e) {
	    history.back(-1);
	    e.preventDefault();
	});
	
	/**
     * ( TB_PROMOTION ) 목록
     */
  	$("#btn_list").click(function() {
		$("#formEdit").attr("action", "/promotion/promotion_list.do");
		$("#formEdit").submit();
	});
}); 

/**
 * ( TB_PROMOTION ) 입력 값 검증
 */
function verifyForm() {	
	if(Valid.isEmpty($("input[name=promotion_seq]").val())) {
		alert("프로모션 일련번호 는 필수입력 항목입니다.");
		return false;
	}
	return true;
}
//-->
</script>


<!-- 목록 이동 폼 -->
<form id="formList" name="formList" class="inline" method="post" action="promotion_list.do">
    <input type="hidden" name="page" value="${pageInfo.page}">
    <input type="hidden" name="search_word" size="20" value="${pageInfo.search_word}">
</form>
<!-- //목록 이동 폼 -->
<div id="con_tit">
<!-- 타이틀 -->
<h3>  수정/추가(method 구분)</h3>
<p class="jump_menu">TB_PROMOTION 관리 &gt <b>TB_PROMOTION 관리</b></p>
</div>

<!--컨텐츠 본문-->
<div id="content">	
<!-- 폼 -->
<form id="formEdit" class="inline" name="formEdit" method="post">
<input type="hidden" name="method" value="${pageInfo.method}">

<!-- 테이블 내용 -->
<table class="table02">
    		
		<tr>
			<th>프로모션 일련번호</th>
			<td><input type="text" name="promotion_seq" size="100" value="${entity.PROMOTION_SEQ}"></td>
		</tr>		
		<tr>
			<th>프로모션 코드</th>
			<td><input type="text" name="promotion_cd" size="100" value="${entity.PROMOTION_CD}"></td>
		</tr>		
		<tr>
			<th>시작일자</th>
			<td><input type="text" name="statr_date" size="100" value="${entity.STATR_DATE}"></td>
		</tr>		
		<tr>
			<th>종료일자</th>
			<td><input type="text" name="end_date" size="100" value="${entity.END_DATE}"></td>
		</tr>		
		<tr>
			<th>사용여부</th>
			<td><input type="text" name="use_yn" size="100" value="${entity.USE_YN}"></td>
		</tr>		
		<tr>
			<th>사용일자</th>
			<td><input type="text" name="use_date" size="100" value="${entity.USE_DATE}"></td>
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
