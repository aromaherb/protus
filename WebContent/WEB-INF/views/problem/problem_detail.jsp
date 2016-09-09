
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
/***************************************************************************************************
 * 파일명 : problem_form.jsp
 * 작성자 : 장재현 (jang1945@raonsolution.co.kr)
 * 작성일 : 2016-8-19
 * 내  용 : ( TB_PROBLEM )의 상세보기/목록/수정/삭제
 ***************************************************************************************************/
%>

<script type="text/javaScript">
//<![CDATA[
$(function() {

	/**
     * ( TB_PROBLEM ) 목록
     */
  	$("#btn_list").click(function() {
		$("#frm").attr("action", "/problem/problem_list.do");
		$("#frm").submit();
	});

	/**
     * ( TB_PROBLEM ) 목록
     */
  	$("#btn_cancel").click(function() {
		$("#frm").attr("action", "/problem/problem_list.do");
		$("#frm").submit();
	});

	/**
     * ( TB_PROBLEM ) 수정(질문)
     */
	$("#btn_modify").click(function() {
		$("input[name=method]").val("M");
		$("#frm").attr("action", "/problem/problem_form.do");
		$("#frm").submit();
	});
	
	/**
     * ( TB_PROBLEM ) 작성(답변) V1 ----------------------
     */
	$("#btn_create").click(function() {
		$("input[name=method]").val("D");
		$("#frm").attr("action", "/problem/problem_form.do");
		$("#frm").submit();
	});
	
	/**
     * ( TB_PROBLEM ) 수정(답변) V1 ----------------------
     */
	$("#btn_modify2").click(function() {
		$("input[name=method]").val("R");
		$("#frm").attr("action", "/problem/problem_form.do");
		$("#frm").submit();
	});
	
	/**
     * ( TB_PROBLEM ) 삭제
     */
	$("#btn_delete").click(function() {
		if(!confirm(" 삭제하시겠습니까 ?")) return;
		$("input[name=method]").val("D");
		$.ajax({
	    	url		: "<c:url value='/problem/problem_delete.do'/>",
	        type	: "POST",
	        data	: $("#frm").serialize(),
	        dataType: "text",
	        success : function(data) {
	        	data = data.replace(/[<][^>]*[>]/gi, '');
	        	var jData = JSON.parse(data);
	            if(jData.result_cd == "200") {
	            	alert('삭제하였습니다.');
	        		$("#frm").attr("action", "/problem/problem_list.do");
					$("#frm").submit();
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

});
//]]>
</script>

<div class="content">
	<!-- 이동 폼 -->
	<form id="frm" name="frm" method="post">
		<input type="hidden" name="page" value="${pageInfo.page}" />
		<input type="hidden" name="search_word" size="20" value="${pageInfo.search_word}">
		<input type="hidden" name="method" value="M" />
		<input type="hidden" name="problem_seq" value="${entity.PROBLEM_SEQ}" />
	</form>
	<!-- //이동 폼-->
	<!-- 컨텐츠 본문-->
	<ul class="tab">
		<li class="on"><a href="..\problem\problem_list.do">질문보기</a></li>
		<li class="off"><a href="..\problem\problem_form.do">질문하기</a></li>
	</ul>
	<div class="visual">
		<h2>
			protus<br> 
			난제타파
		</h2>
	</div>
	<!--위에 비주얼추가될 -->
	<div class="tab_con">
		<div class="board_write_in">
			<!-- 상세보기 내용 -->
			<table class="table02">
				<tr>
					<th>문제파일</th>
					<td>${entity.PROBLEM_FILE}</td>
				</tr>
				<tr>
					<th>난제일련번호</th>
					<td>${entity.PROBLEM_SEQ}</td>
				</tr>
				<tr>
					<th>글쓴이</th>
					<td>${entity.USER_SEQ}</td>
				</tr>
				<tr>
					<th>내용</th>
					<td>${entity.CONTENT}</td>
				</tr>
				<tr>
					<th>과목</th>
					<td>${entity.SUBJECT}</td>
				</tr>
				<tr>
					<th>작성일시</th>
					<td>${entity.WRITE_DT}</td>
				</tr>
				<tr>
					<th>풀이과정</th>
					<td>${entity.SOLV_FILE}</td>
				</tr>

			</table>
			<br/>
			<c:if test="${ entity.PROBLEM_REPLY_SEQ ne NULL}">
				<table class="table02">
					<tr>
						<th>작성자</th>
						<td>${entity.USER_SEQ2}</td>
					</tr>					
					<tr>
						<th>작성일시</th>
						<td>${entity.WRITE_DT2}</td>
					</tr>
					<tr>
						<th>내용</th>
						<td>${entity.REPLY}</td>
					</tr>
					<tr>
						<th>풀이과정</th>
						<td>${entity.SOLV_FILE}</td>
					</tr>				
				</table>
			</c:if>
		</div>
		<!-- //상세보기 내용 -->

		<!-- 표 이동 버튼 -->
		<ul class="send_in">
			<li class="write_button"><input id="btn_list" type="button" value="목록으로" onclick="location.href='/problem/problem_list.do'"></li>
			<li class="cancle_button"><input id="btn_modify" type="button" value="수정하기" onclick="location.href='/problem/problem_form.do'"></li>
			<li class="cancle_button"><input id="btn_delete" type="button" value="삭제하기" onclick="location.href='/problem/problem_delete.do'"></li>
			<li class="cancle_button"><input id="btn_create" type="button" value="답변하기" onclick="location.href='/problem/problem_form.do'"></li>
		</ul>
		<!-- //표 이동 버튼 -->
	</div>
</div>
