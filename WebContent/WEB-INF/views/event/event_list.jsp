
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/WEB-INF/tld/pagination.tld" prefix="page" %>

<%
/***************************************************************************************************
 * 파일명 : event_list.html
 * 작성자 : 장재현 (jang1945@raonsolution.co.kr)
 * 작성일 : 2016-8-19
 * 내  용 : ( TB_EVENT )의 리스트 출력/등록/상세보기/검색/페이지 이동
 ***************************************************************************************************/
%>

<script type="text/javaScript">
<!--
$(function() {
	/**
	 * ( TB_EVENT ) 일반 검색
	 */
	$("#go_search").click(function() {
		$("#formList").submit();
	});
	
	/**
	 * ( TB_EVENT ) 페이지 이동
	 */
	$(".go_page").click(function() {
		$("input[name=method]").val("P");
		$("input[name=page]").val($(this).attr("page"));
		$("#formList").submit();
	});
	
	/**
	 * ( TB_EVENT ) 상세보기
	 */
	$(".go_detail").click(function() {
		$("input[name=method]").val("V");
		$("input[name=event_seq]").val($(this).attr("event_seq"));
		$("#formList").submit();
	});
	
	/**
	 * ( TB_EVENT ) 등록
	 */
	$("#go_regist").click(function() {
		$("input[name=method]").val("A");
		$("#formList").submit();
	});
	
	/**
	 * ( TB_EVENT ) formList Submit
	 */
	$("#formList").submit(function() {
		var method = $("input[name=method]").val();
		
		if ( method == "A" ) {			// 등록
			$("form[name=formList]").attr("action", "event_form.do");
		} else if ( method == "V" ) {	// 상세보기
			$("form[name=formList]").attr("action", "event_form.do");
		} else if ( method == "P" ) {	// 페이지
			$("form[name=formList]").attr("action", "event_list.do");
		} else {						// 일반 검색
			$("form[name=formList]").attr("action", "event_list.do");
			$("input[name=page]").val("1");
		}
	});
});

//-->
</script>
<div class="content">
	<ul class="tab">
		<li class="on">이벤트</li>
	</ul>
	<div class="visual">
		<h2>
			protus<br> 이벤트
		</h2>
	</div>
	<div class="tab_con">
		<div class="board">
			<!-- 목록 폼 -->
			<form id="formList" name="formList" class="inline" method="post">
				<input type="hidden" name="page" value="${pageInfo.page}" />
				 <input type="hidden" name="method" value="" />
				 <input type="hidden" name="event_seq" value="${pageInfo.event_seq}" />

				<p class="img" style="text-align: right">
					총 : <span class="point">${pageInfo.cntTotal} </span>
					건 | 현재페이지 <span class="point">${pageInfo.page} &nbsp;&nbsp;</span>
				</p>
				<!-- 표 내용 -->
				<table class="table01">
					<tr>
						<!-- <th width="10%">이벤트 일련번호</th> -->
						<th width="10%">No.</th>
						<th width="45%">이벤트 제목</th>
						<th width="15%">작성일자</th>
						<th width="15%">시작일</th>
						<th width="15%">마감일</th>

					</tr>
					<c:set var="numlab" value="${pageInfo.cntTotal}"/>
					<c:choose>
						<c:when test="${!empty entities}">
							<c:forEach var="entity" items="${entities}" varStatus="status">
								<tr>
									<td><c:out value="${numlab}"/><c:set var="numlab" value="${numlab - 1}"/></td>
									<td><a class="go_detail" href="#" event_seq="${entity.EVENT_SEQ}">${entity.EVENT_TITLE}</a></td>
									<td>${entity.WRITE_DATE}</td>
									<td>${entity.START_DATE}</td>
									<td>${entity.CLOSE_DATE}</td>

								</tr>
							</c:forEach>
						</c:when>
						<c:when test="${empty entities}">
							<tr>
								<td align="center" colspan="5">검색된 자료가 없습니다.</td>
							</tr>
						</c:when>
					</c:choose>

				</table>
				<!-- //표 내용 -->

				<!-- 페이지 네비게이션 -->
				<div class="margin_4"></div>
				<div class="adbtn">
					<ul class="number">
						<li style="padding: 5px 10px;">
							<div class="paging">
								<page:pagination pagingInfo="${pagingInfo}" type="text" function="go_page" />
							</div>
						</li>
					</ul>
				</div>
				<!-- //페이지 네비게이션 -->
			</form>
			<!-- //목록 폼 -->
		</div>
		<input id="go_regist" class="write" type="button" value="글쓰기">
	</div>
</div>
