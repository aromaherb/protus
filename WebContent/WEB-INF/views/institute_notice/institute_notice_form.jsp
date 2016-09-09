<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
/***************************************************************************************************
 * 파일명 : institute_notice_form.jsp
 * 작성자 : 장재현 (jang1945@raonsolution.co.kr)
 * 작성일 : 2016-8-19
 * 내  용 : ( TB_INSTITUTE_NOTICE )의 입력 및 수정 form출력/저장/취소/입력 값 검증
 ***************************************************************************************************/
%>

<script type="text/javaScript">
<!--
$(function() {

	/**
	 * ( TB_INSTITUTE_NOTICE ) 저장
	 */
	$("#btn_save").click(function() {
	    /* if( verifyForm() == false) return; */
	    
	    $.ajax({
	    	url		: "<c:url value='/institute_notice/institute_notice_save.do'/>",
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
	 * ( TB_INSTITUTE_NOTICE ) 취소
	 */
	$("#btn_cancel").click(function(e) {
	    history.back(-1);
	    e.preventDefault();
	});
	
	/**
     * ( TB_INSTITUTE_NOTICE ) 목록
     */
  	$("#btn_list").click(function() {
		$("#formEdit").attr("action", "/institute_notice/institute_notice_list.do");
		$("#formEdit").submit();
	});
}); 

/**
 * ( TB_INSTITUTE_NOTICE ) 입력 값 검증
 */
/* function verifyForm() {	
	if(Valid.isEmpty($("input[name=notice_seq]").val())) {
		alert("공지일련번호 는 필수입력 항목입니다.");
		return false;
	}
	if(Valid.isEmpty($("input[name=notice_title]").val())) {
		alert("공지제목 는 필수입력 항목입니다.");
		return false;
	}
	return true;
} */
//-->
</script>


<!-- 목록 이동 폼 -->
<form id="formList" name="formList" class="inline" method="post" action="institute_notice_list.do">
    <input type="hidden" name="page" value="${pageInfo.page}">
    <input type="hidden" name="search_word" size="20" value="${pageInfo.search_word}">
    <input type="hidden" name="search_word2" size="20" value="${pageInfo.search_word2}">
</form>
<!-- //목록 이동 폼 -->
<div class="content">
	<ul class="tab">
		<li class="on">학원게시판</li>
	</ul>
	<div class="visual">
		<h2>
			protus<br> 
			학원게시판
		</h2>
	</div>
	<div class="tab_con">
		<!-- 폼 -->
		<form id="formEdit" class="inline" name="formEdit" method="post">
			<input type="hidden" name="method" value="${pageInfo.method}">

			<!-- 테이블 내용 -->
			<table class="table02">

				<tr>
					<th><label class="input_lab" for="notice_title">제목</label></th>
					<td><input type="text" id="notice_title" name="notice_title" size="100" maxlength="60" value="${entity.NOTICE_TITLE}"></td>
				</tr>
				<tr>
					<th><label class="input_lab" for="institute_seq">학원일련번호</label></th>
					<td><input type="text" id="institute_seq" name="institute_seq" size="100" maxlength="18" value="${entity.INSTITUTE_SEQ}"></td>
				</tr>
				<tr>
					<th><label class="input_lab" for="write_date">일자</label></th>
					<td><input type="text" id="write_date" name="write_date" size="100" maxlength="19" value="${entity.WRITE_DATE}"></td>
				</tr>
				<tr>
					<th><label class="input_lab" for="notice_content">내용</label></th>
					<td><input type="text" id="notice_content" name="notice_content" size="100" value="${entity.NOTICE_CONTENT}"></td>
				</tr>				
				

			</table>
			<input type="hidden" name="notice_seq" size="18" value="${entity.NOTICE_SEQ}">
			<!-- //테이블 내용 -->
		</form>
		<!-- //폼 -->
		<!-- 표 이동 버튼 -->
		<ul class="send">
			<li class="write_button"><input id="btn_save" type="button"	value="글쓰기"></li>
			<li class="cancle_button"><input id="btn_cancel" type="button" value="취소"></li>
		</ul>
		<!-- //표 이동 버튼 -->
	</div>
</div>