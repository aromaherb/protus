
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
/***************************************************************************************************
 * 파일명 : event_form.jsp
 * 작성자 : 장재현 (jang1945@raonsolution.co.kr)
 * 작성일 : 2016-8-19
 * 내  용 : ( TB_EVENT )의 상세보기/목록/수정/삭제
 ***************************************************************************************************/
%>

<script type="text/javaScript">
//<![CDATA[
$(function() {

	/**
     * ( TB_EVENT ) 목록
     */
  	$("#btn_list").click(function() {
		$("#frm").attr("action", "/event/event_list.do");
		$("#frm").submit();
	});

	/**
     * ( TB_EVENT ) 목록
     */
  	$("#btn_cancel").click(function() {
		$("#frm").attr("action", "/event/event_list.do");
		$("#frm").submit();
	});

	/**
     * ( TB_EVENT ) 수정
     */
	$("#btn_modify").click(function() {
		$("input[name=method]").val("M");
		$("#frm").attr("action", "/event/event_form.do");
		$("#frm").submit();
	});

	/**
     * ( TB_EVENT ) 삭제
     */
	$("#btn_delete").click(function() {
		if(!confirm(" 삭제하시겠습니까 ?")) return;
		$("input[name=method]").val("D");
		$.ajax({
	    	url		: "<c:url value='/event/event_delete.do'/>",
	        type	: "POST",
	        data	: $("#frm").serialize(),
	        dataType: "text",
	        success : function(data) {
	        	data = data.replace(/[<][^>]*[>]/gi, '');
	        	var jData = JSON.parse(data);
	            if(jData.result_cd == "200") {
	            	alert('삭제하였습니다.');
	        		$("#frm").attr("action", "/event/event_list.do");
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
		<input type="hidden" name="event_seq" value="${entity.EVENT_SEQ}" />
	</form>
	<!-- //이동 폼-->
	<!-- 컨텐츠 본문-->
	<ul class="tab">
		<li class="on">이벤트 게시판</li>
	</ul>
	<div class="visual">
		<h2>
			protus<br> 
			이벤트 게시판
		</h2>
	</div>
	<!--위에 비주얼추가될 -->
	<div class="tab_con">
		<div class="board_write_in">
			<!-- 상세보기 내용 -->
			<table>
				<%-- <tr>
				<th>이벤트 일련번호</th>
				<td>${entity.EVENT_SEQ}</td>
			</tr> --%>
				<tr>
					<th>이벤트 제목</th>
					<td>${entity.EVENT_TITLE}</td>
				</tr>
				<tr>
					<th>이벤트 내용</th>
					<td>${entity.EVENT_CONTENT}</td>
				</tr>
				<tr>
					<th>작성일자</th>
					<td>${entity.WRITE_DATE}</td>
				</tr>
				<tr>
					<th>시작일</th>
					<td>${entity.START_DATE}</td>
				</tr>
				<tr>
					<th>마감일</th>
					<td>${entity.CLOSE_DATE}</td>
				</tr>
			</table>
		</div>
		<!-- //상세보기 내용 -->

		<!-- 표 이동 버튼 -->
		<ul class="send_in">
			<li class="write_button"><input id="btn_list" type="button" value="목록으로" onclick="location.href='/event/event_list.do'"></li>
			<li class="cancle_button"><input id="btn_modify" type="button" value="수정하기" onclick="location.href='/event/event_form.do'"></li>
			<li class="cancle_button"><input id="btn_delete" type="button" value="삭제하기" onclick="location.href='/event/event_delete.do'"></li>
		</ul>
		<!-- //표 이동 버튼 -->
	</div>

</div>
