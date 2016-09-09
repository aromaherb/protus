
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
/***************************************************************************************************
 * 파일명 : lesson_form.jsp
 * 작성자 : 장재현 (jang1945@raonsolution.co.kr)
 * 작성일 : 2016-8-19
 * 내  용 : ( TB_LESSON )의 상세보기/목록/수정/삭제
 ***************************************************************************************************/
%>

<script type="text/javaScript">
//<![CDATA[
$(function() {

	/**
     * ( TB_LESSON ) 목록
     */
  	$("#btn_list").click(function() {
		$("#frm").attr("action", "/lesson/lesson_list.do");
		$("#frm").submit();
	});

	/**
     * ( TB_LESSON ) 목록
     */
  	$("#btn_cancel").click(function() {
		$("#frm").attr("action", "/lesson/lesson_list.do");
		$("#frm").submit();
	});

	/**
     * ( TB_LESSON ) 수정
     */
	$("#btn_modify").click(function() {
		$("input[name=method]").val("M");
		$("#frm").attr("action", "/lesson/lesson_form.do");
		$("#frm").submit();
	});

	/**
     * ( TB_LESSON ) 삭제
     */
	$("#btn_delete").click(function() {
		if(!confirm(" 삭제하시겠습니까 ?")) return;
		$("input[name=method]").val("D");
		$.ajax({
	    	url		: "<c:url value='/lesson/lesson_delete.do'/>",
	        type	: "POST",
	        data	: $("#frm").serialize(),
	        dataType: "text",
	        success : function(data) {
	        	data = data.replace(/[<][^>]*[>]/gi, '');
	        	var jData = JSON.parse(data);
	            if(jData.result_cd == "200") {
	            	alert('삭제하였습니다.');
	        		$("#frm").attr("action", "/lesson/lesson_list.do");
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
		<input type="hidden" name="lesson_seq" value="${entity.LESSON_SEQ}" />
	</form>
	<!-- //이동 폼-->
	<!-- 컨텐츠 본문-->
	<ul class="tab">
		<li class="on">강의동영상</li>
	</ul>
	<div class="visual">
		<h2>
			protus<br> 
			강의 동영상
		</h2>
	</div>
	<!--위에 비주얼추가될 -->
	<div class="tab_con">
		<div class="board_write_in">
			<!-- 상세보기 내용 -->
			<table class="table02">
				<tr>
					<th>강의 일련번호</th>
					<td>${entity.LESSON_SEQ}</td>
				</tr>
				<tr>
					<th>강의제목</th>
					<td>${entity.LESSON_TITLE}</td>
				</tr>
				<tr>
					<th>강의소개</th>
					<td>${entity.LESSON_CONTENT}</td>
				</tr>
				<tr>
					<th>강의동영상</th>
					<td>${entity.LESSON_VIDEO}</td>
				</tr>
				<tr>
					<th>과정</th>
					<td>${entity.COURSE}</td>
				</tr>
				<tr>
					<th>과목</th>
					<td>${entity.SUBJECT}</td>
				</tr>
				<tr>
					<th>유형</th>
					<td>${entity.CATEGORY}</td>
				</tr>
				<tr>
					<th>과금여부</th>
					<td>${entity.CHARGE_YN}</td>
				</tr>
				<tr>
					<th>강의종료일자</th>
					<td>${entity.CLOSE_DATE}</td>
				</tr>
				<tr>
					<th>종료예약</th>
					<td>${entity.RESERVE_CLOSE_YN}</td>
				</tr>
				<tr>
					<th>종료여부</th>
					<td>${entity.CLOSE_YN}</td>
				</tr>
				<tr>
					<th>강의료</th>
					<td>${entity.LESSON_PRICE}</td>
				</tr>
				<tr>
					<th>사용자 코드</th>
					<td>${entity.USER_SEQ}</td>
				</tr>
				<tr>
					<th>강의자료</th>
					<td>${entity.LESSON_DOC}</td>
				</tr>
				<tr>
					<th>작성일시</th>
					<td>${entity.WRITE_DT}</td>
				</tr>
				<tr>
					<th>수정일시</th>
					<td>${entity.MODIFY_DT}</td>
				</tr>
				<tr>
					<th>승인여부</th>
					<td>${entity.APPROVE_YN}</td>
				</tr>
				<tr>
					<th>승인일시</th>
					<td>${entity.APPROVE_DT}</td>
				</tr>

			</table>
		</div>
		<!-- //상세보기 내용 -->

		<!-- 표 이동 버튼 -->
		<ul class="send_in">
			<li class="write_button"><input id="btn_list" type="button" value="목록으로" onclick="location.href='/lesson/lesson_list.do'"></li>
			<li class="cancle_button"><input id="btn_modify" type="button" value="수정하기" onclick="location.href='/lesson/lesson_form.do'"></li>
			<li class="cancle_button"><input id="btn_delete" type="button" value="삭제하기" onclick=""></li>
		</ul>
		<!-- //표 이동 버튼 -->
	</div>
</div>