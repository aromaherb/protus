
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
/***************************************************************************************************
 * 파일명 : note_form.jsp
 * 작성자 : 장재현 (jang1945@raonsolution.co.kr)
 * 작성일 : 2016-8-19
 * 내  용 : ( TB_NOTE )의 상세보기/목록/수정/삭제
 ***************************************************************************************************/
%>

<script type="text/javaScript">
<!--
$(function() {

	/**
     * ( TB_NOTE ) 목록
     */
  	$("#btn_list").click(function() {
		$("#frm").attr("action", "/note/note_list.do");
		$("#frm").submit();
	});

	/**
     * ( TB_NOTE ) 목록
     */
  	$("#btn_cancel").click(function() {
		$("#frm").attr("action", "/note/note_list.do");
		$("#frm").submit();
	});

	/**
     * ( TB_NOTE ) 수정
     */
	$("#btn_modify").click(function() {
		$("input[name=method]").val("M");
		$("#frm").attr("action", "/note/note_form.do");
		$("#frm").submit();
	});

	/**
     * ( TB_NOTE ) 삭제
     */
	$("#btn_delete").click(function() {
		if(!confirm(" 삭제하시겠습니까 ?")) return;
		$("input[name=method]").val("D");
		$.ajax({
	    	url		: "<c:url value='/note/note_delete.do'/>",
	        type	: "POST",
	        data	: $("#frm").serialize(),
	        dataType: "text",
	        success : function(data) {
	        	data = data.replace(/[<][^>]*[>]/gi, '');
	        	var jData = JSON.parse(data);
	            if(jData.result_cd == "200") {
	            	alert('삭제하였습니다.');
	        		$("#frm").attr("action", "/note/note_list.do");
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
//-->
</script>
<div id="con_tit">
	<!-- 타이틀 -->
	<h3>  정보</h3>
	<p class="jump_menu">TB_NOTE 관리 &gt <b>TB_NOTE 관리</b></p>
	</div>

	<!-- 이동 폼 -->
	<form id="frm" name="frm" method="post">
		<input type="hidden" name="page" value="${pageInfo.page}"/>
		<input type="hidden" name="search_word" size="20" value="${pageInfo.search_word}">
		<input type="hidden" name="method" value="M"/>
		<input type="hidden" name="note_seq" value="${entity.NOTE_SEQ}"/>
	</form>
	<!-- //이동 폼-->
	<!-- 컨텐츠 본문-->
	<div id="content">
	<!-- 상세보기 내용 -->
	<table class="table02">
			<tr>
				<th>쪽지 번호</th>
				<td>${entity.NOTE_SEQ}</td>
			</tr>
			<tr>
				<th>보낸이</th>
				<td>${entity.USER_SEQ}</td>
			</tr>
			<tr>
				<th>받는이</th>
				<td>${entity.RECEIVER_SEQ}</td>
			</tr>
			<tr>
				<th>읽기여부</th>
				<td>${entity.OPEN_YN}</td>
			</tr>
			<tr>
				<th>쪽지내용</th>
				<td>${entity.NOTE_SUBJECT}</td>
			</tr>
			<tr>
				<th>답장내용</th>
				<td>${entity.REPLY_SUBJECT}</td>
			</tr>
			<tr>
				<th>답장여부</th>
				<td>${entity.REPLY_YN}</td>
			</tr>
			<tr>
				<th>쪽지일시</th>
				<td>${entity.NOTE_DT}</td>
			</tr>
			<tr>
				<th>답장일시</th>
				<td>${entity.REPLY_DT}</td>
			</tr>	
		
	</table>
	<!-- //상세보기 내용 -->
	
	<!-- 표 이동 버튼 -->
	<p class="left"><a href="#"><img id="btn_list" src="<c:url value='/images/other/b-list01.gif'/>" alt="목록" /></a> </p>
	<p>
	<ul class="list_btn">
		<li><a href="#"><img id="btn_modify" src="<c:url value='/images/other/b-re01.gif'/>" alt="수정" /></a></li>
		<li><a href="#"><img id="btn_delete" src="<c:url value='/images/other/b-del01.gif'/>" alt="삭제" /></a></li>
		<li><a href="#"><img id="btn_cancel" src="<c:url value='/images/other/b-cancel01.gif'/>" alt="취소" /></a></li>
	</ul>
	</p>
	<!-- //표 이동 버튼 -->
	</div>
