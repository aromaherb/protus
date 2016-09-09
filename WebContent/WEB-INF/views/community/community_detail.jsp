<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	/***************************************************************************************************
	 * 파일명 : community_form.jsp
	 * 작성자 : 장재현 (jang1945@raonsolution.co.kr)
	 * 작성일 : 2016-8-19
	 * 내  용 : ( TB_COMMUNITY )의 상세보기/목록/수정/삭제
	 ***************************************************************************************************/
%>

<script type="text/javaScript">
<!--
	$(function() {

		/**
		 * ( TB_COMMUNITY ) 목록
		 */
		$("#btn_list").click(function() {
			$("#frm").attr("action", "/community/community_list.do");
			$("#frm").submit();
		});

		/**
		 * ( TB_COMMUNITY ) 목록
		 */
		$("#btn_cancel").click(function() {
			$("#frm").attr("action", "/community/community_list.do");
			$("#frm").submit();
		});

		/**
		 * ( TB_COMMUNITY ) 수정
		 */
		$("#btn_modify").click(function() {
			$("input[name=method]").val("M");
			$("#frm").attr("action", "/community/community_form.do");
			$("#frm").submit();
		});

		/**
		 * ( TB_COMMUNITY ) 삭제
		 */
		$("#btn_delete")
				.click(
						function() {
							if (!confirm(" 삭제하시겠습니까 ?"))
								return;
							$("input[name=method]").val("D");
							$.ajax({
										url : "<c:url value='/community/community_delete.do'/>",
										type : "POST",
										data : $("#frm").serialize(),
										dataType : "text",
										success : function(data) {
											data = data.replace(
													/[<][^>]*[>]/gi, '');
											var jData = JSON.parse(data);
											if (jData.result_cd == "200") {
												alert('삭제하였습니다.');
												$("#frm")
														.attr("action",
																"/community/community_list.do");
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
	<!-- 이동 폼 -->
	<form id="frm" name="frm" method="post">
		<input type="hidden" name="page" value="${pageInfo.page}" />
		<input type="hidden" name="search_word" size="20" value="${pageInfo.search_word}">
		<input type="hidden" name="method" value="M" /> 
		<input type="hidden" name="community_seq" value="${entity.COMMUNITY_SEQ}" />
	</form>
	<!-- //이동 폼-->
	<!-- 컨텐츠 본문-->
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
	<div class="tab_con">
		<div class="board_write_in">
			<!-- 상세보기 내용 -->
			<table>
				<%-- <tr>
					<td>글 일련번호</td>
					<td>${entity.COMMUNITY_SEQ}</td>
				</tr> --%>
				<tr>
					<td>제목</td>
					<td>${entity.TITLE}</td>
				</tr>
				<tr>
					<td>글쓴이</td>
					<td>${entity.USER_SEQ}</td>
				</tr>
				<tr>
					<td>작성일자</td>
					<td>${entity.WRITE_DATE}</td>
				</tr>				
				<tr>
					<td>내용</td>
					<td>${entity.CONTENT}</td>
				</tr>
				
			</table>
		</div>
		<!-- //상세보기 내용 -->

		<!-- 표 이동 버튼 -->
		<ul class="send_in">
			<li class="write_button"><input id="btn_list" type="button" value="목록으로" onclick="location.href='/community/community_list.do'"></li>
			<li class="cancle_button"><input id="btn_modify" type="button" value="수정하기" onclick="location.href='/community/community_form.do'"></li>
			<li class="cancle_button"><input id="btn_delete" type="button" value="삭제하기" onclick="location.href='/community/community_delete.do'"></li>
		</ul>
	</div>
	<!-- //표 이동 버튼 -->
</div>
