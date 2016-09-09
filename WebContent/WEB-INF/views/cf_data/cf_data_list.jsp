
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/WEB-INF/tld/pagination.tld" prefix="page" %>

<%
/***************************************************************************************************
 * 파일명 : cf_data_list.html
 * 작성자 : 장재현 (jang1945@raonsolution.co.kr)
 * 작성일 : 2016-8-19
 * 내  용 : ( TB_CF_DATA )의 리스트 출력/등록/상세보기/검색/페이지 이동
 ***************************************************************************************************/
%>

<script type="text/javaScript">
<!--
$(function() {
	/**
	 * ( TB_CF_DATA ) 일반 검색
	 */
	$("#go_search").click(function() {
		$("#formList").submit();
	});
	
	/**
	 * ( TB_CF_DATA ) 페이지 이동
	 */
	$(".go_page").click(function() {
		$("input[name=method]").val("P");
		$("input[name=page]").val($(this).attr("page"));
		$("#formList").submit();
	});
	
	/**
	 * ( TB_CF_DATA ) 상세보기
	 */
	$(".go_detail").click(function() {
		$("input[name=method]").val("V");
		$("input[name=lesson_seq]").val($(this).attr("lesson_seq"));
		$("input[name=cf_seq]").val($(this).attr("cf_seq"));
		$("#formList").submit();
	});
	
	/**
	 * ( TB_CF_DATA ) 등록
	 */
	$("#go_regist").click(function() {
		$("input[name=method]").val("A");
		$("#formList").submit();
	});
	
	/**
	 * ( TB_CF_DATA ) formList Submit
	 */
	$("#formList").submit(function() {
		var method = $("input[name=method]").val();
		
		if ( method == "A" ) {			// 등록
			$("form[name=formList]").attr("action", "cf_data_form.do");
		} else if ( method == "V" ) {	// 상세보기
			$("form[name=formList]").attr("action", "cf_data_form.do");
		} else if ( method == "P" ) {	// 페이지
			$("form[name=formList]").attr("action", "cf_data_list.do");
		} else {						// 일반 검색
			$("form[name=formList]").attr("action", "cf_data_list.do");
			$("input[name=page]").val("1");
		}
	});
});

//-->
</script>

<div id="con_tit">
	<!-- 타이틀 -->
	<h3>  목록</h3>
	<p class="jump_menu">TB_CF_DATA 관리 &gt <b>TB_CF_DATA 관리</b></p>
	</div>

	<div id="content">
	<!-- 목록 폼 -->
	<form id="formList" name="formList" class="inline" method="post" >
	<input type="hidden" name="page" value="${pageInfo.page}"/>
	<input type="hidden" name="method" value=""/>
	<input type="hidden" name="lesson_seq" value="${pageInfo.lesson_seq}"/>
	<input type="hidden" name="cf_seq" value="${pageInfo.cf_seq}"/>
	
	<p class="img" style="text-align:right"> 총 : <span class="point">${pageInfo.cntTotal} </span>건  | 현재페이지 <span class="point">${pageInfo.page} &nbsp;&nbsp; <img id="go_regist" src="<c:url value='/images/other/b-add02.gif'/>" alt="추가" /></span> </p>
	<!-- 표 내용 -->	
	<table class="table01">
		<tr>
			<th width="10%">강의 일련번호</th>
			<th width="10%">CF_SEQ</th>
			<th width="10%">성별</th>
			<th width="10%">학년</th>
			<th width="10%">일자</th>
			<th width="10%">나이</th>
			<th width="10%">과목</th>
			<th width="10%">과정</th>
			<th width="10%">유형</th>
		</tr>
	<c:choose>
	  <c:when test="${!empty entities}"> 
	    <c:forEach var="entity" items="${entities}" varStatus="status">
	  		<tr>
				<td><a class="go_detail" href="#" lesson_seq="${entity.LESSON_SEQ}" cf_seq="${entity.CF_SEQ}" >${entity.LESSON_SEQ}</a></td>
				<td>${entity.CF_SEQ}</td>
				<td>${entity.GENDER}</td>
				<td>${entity.GRADE}</td>
				<td>${entity.CF_DATE}</td>
				<td>${entity.AGE}</td>
				<td>${entity.SUBJECT}</td>
				<td>${entity.COURSE}</td>
				<td>${entity.CATEGORY}</td>
			</tr>
	    </c:forEach>
	  </c:when>
	  <c:when test="${empty entities}">
			<tr>
				<td align="center" colspan="9">검색된 자료가 없습니다.</td>
			</tr>
	  </c:when>
	</c:choose>		
		
	</table>
	<!-- //표 내용 -->
		
	<!-- 페이지 네비게이션 -->
	<div class="margin_4"></div>
	<div class="adbtn">
	<ul class="number">
		<li style="padding:5px 10px;">
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
