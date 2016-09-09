<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	/***************************************************************************************************
	 * 파일명 : code_form.jsp
	 * 작성자 : 장재현 (jang1945@raonsolution.co.kr)
	 * 작성일 : 2016-7-6
	 * 내  용 : ( TB_CODE )의 상세보기/목록/수정/삭제
	 ***************************************************************************************************/
%>

<script type="text/javaScript">
<!--
	$(function() {

		/**
		 * ( TB_CODE ) 목록
		 */
		$("#btn_list").click(function() {
			$("#frm").attr("action", "/code/code_list.do");
			$("#frm").submit();
		});

		/**
		 * ( TB_CODE ) 목록
		 */
		$("#btn_cancel").click(function() {
			$("#frm").attr("action", "/code/code_list.do");
			$("#frm").submit();
		});

		/**
		 * ( TB_CODE ) 수정
		 */
		$("#btn_modify").click(function() {
			$("input[name=method]").val("M");
			$("#frm").attr("action", "/code/code_form.do");
			$("#frm").submit();
		});

		/**
		 * ( TB_CODE ) 삭제
		 */
		$("#btn_delete").click(function() {
			if (!confirm(" 삭제하시겠습니까 ?"))
				return;
			$("input[name=method]").val("D");
			$.ajax({
				url : "<c:url value='/code/code_delete.do'/>",
				type : "POST",
				data : $("#frm").serialize(),
				dataType : "text",
				success : function(data) {
					data = data.replace(/[<][^>]*[>]/gi, '');
					var jData = JSON.parse(data);
					if (jData.result_cd == "200") {
						alert('삭제하였습니다.');
						$("#frm").attr("action", "/code/code_list.do");
						$("#frm").submit();
					} else {
						alert(jData.result_msg);
					}
				},
				error : function(xhr, status, error) {
					alert(error);
				}
			});
		});

	});
//-->
</script>

<div class="content">
	<form id="frm" name="frm" method="post">
		<input type="hidden" name="page" value="${pageInfo.page}" />
		<input type="hidden" name="search_word" size="20" value="${pageInfo.search_word}">
		<input type="hidden" name="search_word2" size="20" value="${pageInfo.search_word2}">
		<input type="hidden" name="method" value="M" />
		<input type="hidden" name="group_code" value="${entity.GROUP_CODE}" />
		<input type="hidden" name="code" value="${entity.CODE}" />
	</form>
	<ul class="tab">
		<li class="on">코드관리</li>
	</ul>
	<div class="visual">
		<h2>미안
			protus<br>
			코드관리
		</h2>
	</div>
	<!--위에 비주얼추가될 -->
	<div class="tab_con">
		<div class="board_write_in">
			<ul>
				<li class="subject">${entity.GROUP_CODE}</li>
				<li class="write">${entity.CODE}</li>
				<li class="write">${entity.CODE_NAME}</li>
				<li class="write">${entity.V_VALUE}</li>
				<li class="write">${entity.V_VALUE1}</li>
				<li class="write">${entity.N_VALUE}</li>
				<li class="write">${entity.N_VALUE1}</li>
				<li class="write">${entity.D_VALUE}</li>
				<li class="con_box">${entity.ETC}</li>
			</ul>
		</div>
		<ul class="send_in">
			<li class="write_button"><input id="btn_list" type="button"	value="목록으로" onclick="location.href='/code/board_list.do'"></li>
			<li class="cancle_button"><input id="btn_modify" type="button" value="수정하기" onclick="location.href='/board/board_form.do'"></li>
			<li class="cancle_button"><input id="btn_delete" type="button" value="삭제하기" onclick="location.href='/board/board_delete.do'"></li>
		</ul>
	</div>
</div>
