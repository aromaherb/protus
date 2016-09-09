
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/WEB-INF/tld/pagination.tld" prefix="page" %>

<%
/***************************************************************************************************
 * 파일명 : lesson_list.html
 * 작성자 : 장재현 (jang1945@raonsolution.co.kr)
 * 작성일 : 2016-8-19
 * 내  용 : ( TB_LESSON )의 리스트 출력/등록/상세보기/검색/페이지 이동
 ***************************************************************************************************/
%>
<%
String file_key_W = request.getParameter("file_key_W");
String file_key_I = request.getParameter("file_key_I");
String file_key_A = request.getParameter("file_key_A");
String file_key_M = request.getParameter("file_key_M");
String media_key = request.getParameter("origin_file_key");
System.out.println("ajax_list : " + file_key_W + " | " + file_key_I +  " | " + file_key_A +  " | " + file_key_M +  " | " + media_key);
%>
<script type="text/javaScript">
//<![CDATA[
$(function() {
	/**
	 * ( TB_LESSON ) 일반 검색
	 */
	$("#go_search").click(function() {
		$("#formList").submit();
	});
	
	/**
	 * ( TB_LESSON ) 페이지 이동
	 */
	$(".go_page").click(function() {
		$("input[name=method]").val("P");
		$("input[name=page]").val($(this).attr("page"));
		$("#formList").submit();
	});
	
	/**
	 * ( TB_LESSON ) 상세보기
	 */
	$(".go_detail").click(function() {
		$("input[name=method]").val("V");
		$("input[name=lesson_seq]").val($(this).attr("lesson_seq"));
		$("#formList").submit();
	});
	
	/**
	 * ( TB_LESSON ) 등록
	 */
	$("#go_regist").click(function() {
		$("input[name=method]").val("A");
		$("#formList").submit();
	});
	
	/**
	 * ( TB_LESSON ) formList Submit
	 */
	$("#formList").submit(function() {
		var method = $("input[name=method]").val();
		
		if ( method == "A" ) {			// 등록
			$("form[name=formList]").attr("action", "lesson_form.do");
		} else if ( method == "V" ) {	// 상세보기
			$("form[name=formList]").attr("action", "lesson_form.do");
		} else if ( method == "P" ) {	// 페이지
			$("form[name=formList]").attr("action", "lesson_list.do");
		} else {						// 일반 검색
			$("form[name=formList]").attr("action", "lesson_list.do");
			$("input[name=page]").val("1");
		}
	});
});

//]]>
</script>
<div class="content">
	<ul class="tab">
		<li class="on">강의동영상</li>
		<!-- <li class="off"><a href="..\problem\problem_form.do"></a></li> -->
	</ul>
	<div class="visual">
		<h2>
			protus<br> 
			강의동영상
		</h2>
	</div>
	<div class="tab_con">
		<div class="board">
			<!-- 목록 폼 -->
			<form id="formList" name="formList" class="inline" method="post">
				<input type="hidden" name="page" value="${pageInfo.page}" /> 
				<input type="hidden" name="method" value="" /> 
				<input type="hidden" name="lesson_seq" value="${pageInfo.lesson_seq}" />

				<p class="img" style="text-align: right">
					총 : <span class="point">${pageInfo.cntTotal} </span>건 | 
					현재페이지 <span class="point">${pageInfo.page} &nbsp;&nbsp; </span>
				</p>
				<!-- 표 내용 -->
				<table class="table01">
				<c:set var="numlab" value="${pageInfo.cntTotal}"/>
					<c:choose>
						<c:when test="${!empty entities}">
							<c:forEach var="entity" items="${entities}" varStatus="status">
								<tr>
									<td width="30%"><c:out value="${numlab}"/><c:set var="numlab" value="${numlab - 1}"/></td>
									<td width="10%">강의명</td>
									<td colspan="3"><a class="go_detail" href="#" lesson_seq="${entity.LESSON_SEQ}">${entity.LESSON_TITLE}</a></td>
								</tr>
								<tr>
									<td rowspan="4">썸네일</td>
									<td>강사</td>
									<td width="20%">${entity.USER_SEQ}</td>
									<td width="10%">과목</td>
									<td width="20%">${entity.SUBJECT}</td>
								</tr>
								<tr>
									<td>과정</td>
									<td>${entity.COURSE}</td>
									<td>유형</td>
									<td>${entity.CATEGORY}</td>
								</tr>
								<tr>
									<td>게시일</td>
									<td>${entity.WRITE_DT}</td>
									<td>종료일</td>
									<td>${entity.CLOSE_DATE}</td>
								</tr>
								<tr>
									<td>가격</td>
									<td>${entity.LESSON_PRICE}</td>
									<td></td>
									<td></td>
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