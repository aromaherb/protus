
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	/***************************************************************************************************
	 * 파일명 : community_form.jsp
	 * 작성자 : 장재현 (jang1945@raonsolution.co.kr)
	 * 작성일 : 2016-8-19
	 * 내  용 : ( TB_COMMUNITY )의 입력 및 수정 form출력/저장/취소/입력 값 검증
	 ***************************************************************************************************/
%>

<script type="text/javaScript">
<!--
	$(function() {

		/**
		 * ( TB_COMMUNITY ) 저장
		 */
		$("#btn_save").click(function() {
			if (verifyForm() == false)
				return;

			$.ajax({
				url : "<c:url value='/community/community_save.do'/>",
				type : "POST",
				data : $("#formEdit").serialize(),
				dataType : "text",
				success : function(data) {
					data = data.replace(/[<][^>]*[>]/gi, '');
					var jData = JSON.parse(data);
					if (jData.result_cd == "200") {
						alert("저장되었습니다.");
						$("form[name=formList]").submit();
					} else {
						alert(jData.result_msg);
					}
				},
				error : function(xhr, status, error) {
					alert(error);
				}
			});
		});

		/**
		 * ( TB_COMMUNITY ) 취소
		 */
		$("#btn_cancel").click(function(e) {
			history.back(-1);
			e.preventDefault();
		});

		/**
		 * ( TB_COMMUNITY ) 목록
		 */
		$("#btn_list").click(function() {
			$("#formEdit").attr("action", "/community/community_list.do");
			$("#formEdit").submit();
		});
	});

	/**
	 * ( TB_COMMUNITY ) 입력 값 검증
	 */
	function verifyForm() {
		if (Valid.isEmpty($("input[name=user_seq]").val())) {
			alert("글쓴이 는 필수입력 항목입니다.");
			return false;
		}
		return true;
	}
//-->
</script>
<!-- 목록 이동 폼 -->
<form id="formList" name="formList" class="inline" method="post" action="community_list.do">
	<input type="hidden" name="page" value="${pageInfo.page}">
	<input type="hidden" name="search_word" size="20" value="${pageInfo.search_word}">
</form>
<div class="content">
	<ul class="tab">
		<li class="on">자유게시판</li>
	</ul>
	<div class="visual">
		<h2>
			protus<br> 
			자유게시판
		</h2>
	</div>
	<!--위에 비주얼추가될 -->
	<!-- 테이블 내용 -->
	<div class="tab_con">
		<form id="formEdit" class="inline" name="formEdit" method="post">
			<input type="hidden" name="method" value="${pageInfo.method}">
			<div class="board_write">
				<table class="table02">
					<tr>
						<th><label class="input_lab" for="title">제목 </label></th>
						<td><input type="text" id="title" name="title" size="100" maxlength="128" value="${entity.TITLE}"></td>
					</tr>
					<tr>
						<th><label class="input_lab" for="write_date">작성일 </label></th>
						<td><input type="text" id="write_date" name="write_date" size="100" maxlength="19" value="${entity.WRITE_DATE}"></td>
					</tr>
					<tr>
						<th><label class="input_lab" for="user_seq">작성자 </label></th>
						<td><input type="text" id="user_seq" name="user_seq" size="100" maxlength="18" value="${entity.USER_SEQ}"></td>
					</tr>
					<tr>
						<th><label class="input_lab" for="content">내용 </label></th>
						<td><input type="text" id="content" name="content" size="100" maxlength="3000" value="${entity.CONTENT}"></td>
					</tr>

				</table>
				<!-- 글번호 community_seq -->
				<input type="hidden" name="community_seq" size="100" value="${entity.COMMUNITY_SEQ}">
			</div>
			<!-- //테이블 내용 -->
		</form>
		<!-- //폼 -->
	</div>
	<!-- 표 이동 버튼 -->
	<ul class="send">
		<li class="write_button"><input id="btn_save" type="button" value="글쓰기"></li>
		<li class="cancle_button"><input id="btn_cancel" type="button" value="취소"></li>
	</ul>
	<!-- //표 이동 버튼 -->
</div>
