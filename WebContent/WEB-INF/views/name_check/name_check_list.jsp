
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/WEB-INF/tld/pagination.tld" prefix="page" %>

<%
/***************************************************************************************************
 * 파일명 : name_check_list.html
 * 작성자 : 장재현 (jang1945@raonsolution.co.kr)
 * 작성일 : 2016-8-19
 * 내  용 : ( TB_NAME_CHECK )의 리스트 출력/등록/상세보기/검색/페이지 이동
 ***************************************************************************************************/
%>

<script type="text/javaScript">
<!--
$(function() {
	/**
	 * ( TB_NAME_CHECK ) 일반 검색
	 */
	$("#go_search").click(function() {
		$("#formList").submit();
	});
	
	/**
	 * ( TB_NAME_CHECK ) 페이지 이동
	 */
	$(".go_page").click(function() {
		$("input[name=method]").val("P");
		$("input[name=page]").val($(this).attr("page"));
		$("#formList").submit();
	});
	
	/**
	 * ( TB_NAME_CHECK ) 상세보기
	 */
	$(".go_detail").click(function() {
		$("input[name=method]").val("V");
		$("input[name=check_seq]").val($(this).attr("check_seq"));
		$("#formList").submit();
	});
	
	/**
	 * ( TB_NAME_CHECK ) 등록
	 */
	$("#go_regist").click(function() {
		$("input[name=method]").val("A");
		$("#formList").submit();
	});
	
	/**
	 * ( TB_NAME_CHECK ) formList Submit
	 */
	$("#formList").submit(function() {
		var method = $("input[name=method]").val();
		
		if ( method == "A" ) {			// 등록
			$("form[name=formList]").attr("action", "name_check_form.do");
		} else if ( method == "V" ) {	// 상세보기
			$("form[name=formList]").attr("action", "name_check_form.do");
		} else if ( method == "P" ) {	// 페이지
			$("form[name=formList]").attr("action", "name_check_list.do");
		} else {						// 일반 검색
			$("form[name=formList]").attr("action", "name_check_list.do");
			$("input[name=page]").val("1");
		}
	});
});

//-->
</script>

<div id="con_tit">
	<!-- 타이틀 -->
	<h3>  목록</h3>
	<p class="jump_menu">TB_NAME_CHECK 관리 &gt <b>TB_NAME_CHECK 관리</b></p>
	</div>

	<div id="content">
	<!-- 목록 폼 -->
	<form id="formList" name="formList" class="inline" method="post" >
	<input type="hidden" name="page" value="${pageInfo.page}"/>
	<input type="hidden" name="method" value=""/>
	<input type="hidden" name="check_seq" value="${pageInfo.check_seq}"/>
	
	<p class="img" style="text-align:right"> 총 : <span class="point">${pageInfo.cntTotal} </span>건  | 현재페이지 <span class="point">${pageInfo.page} &nbsp;&nbsp; <img id="go_regist" src="<c:url value='/images/other/b-add02.gif'/>" alt="추가" /></span> </p>
	<!-- 표 내용 -->	
	<table class="table01">
		<tr>
			<th width="10%">일련번호</th>
			<th width="10%">식별번호</th>
			<th width="10%">폰번호</th>
			<th width="10%">통신사</th>
			<th width="10%">생년월일</th>
			<th width="10%">성별</th>
			<th width="10%">이름</th>
			<th width="10%">확인방법</th>
			<th width="10%">이용자 IP</th>
			<th width="10%">내_외국인</th>
			<th width="10%">미성년_이름</th>
			<th width="10%">미만_생일</th>
			<th width="10%">미만_성별</th>
			<th width="10%">미만_국가</th>
			<th width="10%">사용자 코드</th>
		</tr>
	<c:choose>
	  <c:when test="${!empty entities}"> 
	    <c:forEach var="entity" items="${entities}" varStatus="status">
	  		<tr>
				<td><a class="go_detail" href="#" check_seq="${entity.CHECK_SEQ}" >${entity.CHECK_SEQ}</a></td>
				<td>${entity.CI}</td>
				<td>${entity.PHONE_NO}</td>
				<td>${entity.PHONE_CORP}</td>
				<td>${entity.BIRTHDAY}</td>
				<td>${entity.GENDER}</td>
				<td>${entity.CHECK_NAME}</td>
				<td>${entity.CERT_MET}</td>
				<td>${entity.IP}</td>
				<td>${entity.NATION}</td>
				<td>${entity.M_CHECK_NAME}</td>
				<td>${entity.M_BIRTHDAY}</td>
				<td>${entity.M_GENDER}</td>
				<td>${entity.M_NATION}</td>
				<td>${entity.USER_SEQ}</td>
			</tr>
	    </c:forEach>
	  </c:when>
	  <c:when test="${empty entities}">
			<tr>
				<td align="center" colspan="15">검색된 자료가 없습니다.</td>
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
