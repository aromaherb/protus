<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 

<%
/***************************************************************************************************
 * 파일명 : problem_form.jsp
 * 작성자 : 장재현 (jang1945@raonsolution.co.kr)
 * 작성일 : 2016-8-19
 * 내  용 : ( TB_PROBLEM )의 입력 및 수정 form출력/저장/취소/입력 값 검증
 ***************************************************************************************************/
%>

<script type="text/javaScript">
//<![CDATA[

$(function() {

	
	$("#solv_file").change(function(){
			
	});
	/**
	 * ( TB_PROBLEM ) 저장
	 */
	$("#btn_save").click(function() {
	    /* if( verifyForm() == false) return; */
	    var method = $("input[name=method]").val();
	    if (method == "") {
	    	$("input[name=method]").val("A");
	    } 
		$.ajax({
			url		: "<c:url value='/problem/problem_save.do'/>",
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
	 * ( TB_PROBLEM ) 취소
	 */
	$("#btn_cancel").click(function(e) {
	    history.back(-1);
	    e.preventDefault();
	});
	
	/**
     * ( TB_PROBLEM ) 목록
     */
  	$("#btn_list").click(function() {
		$("#formEdit").attr("action", "/problem/problem_list.do");
		$("#formEdit").submit();
	});
	
}); 

/**
 * ( TB_PROBLEM ) 입력 값 검증
 */
/* function verifyForm() {	
	if(Valid.isEmpty($("input[name=problem_file]").val())) {
		alert("문제파일 는 필수입력 항목입니다.");
		return false;
	}
	return true;
} */
//]]>

</script>
<!-- 목록 이동 폼 -->
<form id="formList" name="formList" class="inline" method="post" action="problem_list.do">
	<input type="hidden" name="page" value="${pageInfo.page}"> 
	<input type="hidden" name="search_word" size="20" value="${pageInfo.search_word}">
</form>
<!-- //목록 이동 폼 -->
<div class="content">
	<ul class="tab">
		<li class="off"><a href="..\problem\problem_list.do">질문보기</a></li>
		<li class="on"><a href="..\problem\problem_form.do">질문하기</a></li>
	</ul>
	<div class="visual">
		<h2>
			protus<br> 
			난제타파
		</h2>
	</div>
	<div class="tab_con">
	<!-- 폼 -->
	<form id="formEdit" class="inline" name="formEdit" method="post" enctype="multipart/form-data" >
		<input type="hidden" name="method" value="${pageInfo.method}">
		<c:set var="method" value="${pageInfo.method}" />
		<!-- 테이블 내용 -->
		<c:choose>
			<c:when test="${ method eq 'A' or method eq 'V'}">
			<table class="table02">			
				<tr>
					<th><label class="input_lab" for="problem_title">제목</label></th>
					<td><input type="text" name="problem_title" size="100" maxlength="60" value="${entity.PROBLEM_TITLE}"></td>
				</tr>
				<tr>
					<th><label class="input_lab" for="subject">과목</label></th>
					<td><input type="text" name="subject" size="100" maxlength="8" value="${entity.SUBJECT}"></td>
				</tr>
				<tr>
					<th><label class="input_lab" for="user_seq">글쓴이</label></th>
					<td><input type="text" name="user_seq" size="100" value="${entity.USER_SEQ}"></td>
				</tr>
				<tr>
					<th><label class="input_lab" for="write_dt">작성일시</label></th>
					<td><input type="date" name="write_dt" size="100" value="${entity.WRITE_DT}"></td>
				</tr>
				<tr>
					<th><label class="input_lab" for="content">내용</label></th>
					<td><input type="text" name="content" size="100" value="${entity.CONTENT}"></td>
				</tr>			
				<tr>
					<th><label class="input_lab" for="solv_file">풀이과정</label></th>
					<td><input type="file" name="solv_file" id="sol_file" size="100" value="${entity.SOLV_FILE}"></td>
				</tr>
				<tr>
					<th><label class="input_lab" for="problem_file">문제파일</label></th>
					<td><input type="file" name="problem_file" size="100" value="${entity.PROBLEM_FILE}"></td>
				</tr>
			</table>
			<input type="hidden" name="problem_seq" size="100" value="${entity.PROBLEM_SEQ}">
			<!-- //테이블 내용 -->
		</form>
		</c:when>
		<c:when test="${ method eq 'D' or method eq 'R'}">
		<form id="formEdit" class="inline" name="formEdit" method="post" enctype="multipart/form-data" >
			<input type="hidden" name="method" value="${pageInfo.method}">
			<input type="hidden" name="problem_seq" size="100" value="${entity.PROBLEM_SEQ}">
			<input type="hidden" name="problem_seq" size="100" value="${entity.PROBLEM_SEQ}">
			<!-- 테이블 내용 -->
			<table class="table02">			
				<tr>
					<th><label class="input_lab" for="user_seq">글쓴이</label></th>
					<td><input type="text" name="user_seq" size="100" value="${entity.USER_SEQ}"></td>
				</tr>
				<tr>
					<th><label class="input_lab" for="write_dt">작성일시</label></th>
					<td><input type="date" name="write_dt" size="100" value="${entity.WRITE_DT}"></td>
				</tr>
				<tr>
					<th><label class="input_lab" for="content">내용</label></th>
					<td><input type="text" name="content" size="100" value="${entity.CONTENT}"></td>
				</tr>			
				<tr>
					<th><label class="input_lab" for="solv_file">풀이과정</label></th>
					<td><input type="file" name="solv_file" id="sol_file" size="100" value="${entity.SOLV_FILE}"></td>
				</tr>
				<tr>
					<th><label class="input_lab" for="problem_file">문제파일</label></th>
					<td><input type="file" name="problem_file" size="100" value="${entity.PROBLEM_FILE}"></td>
				</tr>
			</table>
			
			<!-- //테이블 내용 -->
		</form>
		</c:when>
	</c:choose>
		<!-- //폼 -->
		<!-- 표 이동 버튼 -->
		<ul class="send">
			<li class="write_button"><input id="btn_save" type="button"	value="글쓰기"></li>
			<li class="cancle_button"><input id="btn_cancel" type="button" value="취소"></li>
		</ul>
		<!-- //표 이동 버튼 -->
	</div>
</div>
