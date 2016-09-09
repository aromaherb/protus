<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/WEB-INF/tld/pagination.tld" prefix="page" %>

<%
/***************************************************************************************************
 * 파일명 : user_list.html
 * 작성자 : 장재현 (jang1945@raonsolution.co.kr)
 * 작성일 : 2016-7-11
 * 내  용 : ( TB_USER )의 리스트 출력/등록/상세보기/검색/페이지 이동
 ***************************************************************************************************/
%>

<script type="text/javaScript">
<!-- 
$(function() {
	/**
	 * ( TB_USER ) 일반 검색
	 */
	$("#go_search").click(function() {
		$("#formList").submit();
	});
	
	/**
	 * ( TB_USER ) 페이지 이동
	 */
	$(".go_page").click(function() {
		$("input[name=method]").val("P");
		$("input[name=page]").val($(this).attr("page"));
		$("#formList").submit();
	});
	
	/**
	 * ( TB_USER ) 상세보기
	 */
	$(".go_detail").click(function() {
		$("input[name=method]").val("V");
		$("input[name=email]").val($(this).attr("email"));
		$("#formList").submit();
	});
	
	/**
	 * ( TB_USER ) 등록
	 */
	$("#go_regist").click(function() {
		$("input[name=method]").val("A");
		$("#formList").submit();
	});
	
	/**
	 * ( TB_USER ) formList Submit
	 */
	$("#formList").submit(function() {
		var method = $("input[name=method]").val();
		
		if ( method == "A" ) {			// 등록
			$("form[name=formList]").attr("action", "user_form.do");
		} else if ( method == "V" ) {	// 상세보기
			$("form[name=formList]").attr("action", "user_form.do");
		} else if ( method == "P" ) {	// 페이지
			$("form[name=formList]").attr("action", "user_list.do");
		} else {						// 일반 검색
			$("form[name=formList]").attr("action", "user_list.do");
			$("input[name=page]").val("1");
		}
	});
});

//-->
</script>
	<div class="content">
	<ul class="tab">
		<li class="on">사용자관리</li>
	</ul>
	<div class="visual">
		 <h2>
		 	protus<br>
		 	사용자 관리
		 </h2>
	</div> <!--위에 비주얼추가될 -->
	<div class="tab_con">
		<div class="board">
		<!-- 목록 폼 -->
			<form id="formList" name="formList" class="inline" method="post" >
			<input type="hidden" name="page" value="${pageInfo.page}"/>
			<input type="hidden" name="method" value=""/>
			<input type="hidden" name="email" value="${pageInfo.email}"/>
		<table>
			<thead>
			<tr>
				<th width="10%">이메일(ID)</th>
				<th width="10%">사용자 명</th>
				<th width="10%">핸드폰</th>
				<th width="10%">사용자 코드</th>
				<th width="10%">사용자구분</th>
				<th width="10%">사용자권한</th>
				<th width="10%">성별</th>
				<th width="10%">학년</th>
			</tr>
			</thead>
			<tbody>
		<c:choose>
		  <c:when test="${!empty entities}"> 
		    <c:forEach var="entity" items="${entities}" varStatus="status">
		  		<tr>
			  		<td><a class="go_detail" href="#" email="${entity.EMAIL}" >${entity.EMAIL}</a></td>
					<td>${entity.USER_NAME}</td>
					<td>${entity.HP_NUM}</td>
					<td>${entity.USER_SEQ}</td>
					<td>${entity.USER_TYPE}</td>						
					<td>${entity.USER_AUTH}</td>						
					<td>${entity.GENDER}</td>
					<td>${entity.GRADE}</td>					
				</tr>
		    </c:forEach>
		  </c:when>
		  <c:when test="${empty entities}">
				<tr>
					<td align="center" colspan="9">검색된 자료가 없습니다.</td>
				</tr>
		  </c:when>
		</c:choose>
			</tbody>
		</table>
		</form>	
		</div>
		<input id="go_regist" class="write" type="button" value="사용자 등록" > 	
								
	</div>
</div>
