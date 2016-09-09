
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
/***************************************************************************************************
 * 파일명 : name_check_form.jsp
 * 작성자 : 장재현 (jang1945@raonsolution.co.kr)
 * 작성일 : 2016-8-19
 * 내  용 : ( TB_NAME_CHECK )의 상세보기/목록/수정/삭제
 ***************************************************************************************************/
%>

<script type="text/javaScript">
<!--
$(function() {

	/**
     * ( TB_NAME_CHECK ) 목록
     */
  	$("#btn_list").click(function() {
		$("#frm").attr("action", "/name_check/name_check_list.do");
		$("#frm").submit();
	});

	/**
     * ( TB_NAME_CHECK ) 목록
     */
  	$("#btn_cancel").click(function() {
		$("#frm").attr("action", "/name_check/name_check_list.do");
		$("#frm").submit();
	});

	/**
     * ( TB_NAME_CHECK ) 수정
     */
	$("#btn_modify").click(function() {
		$("input[name=method]").val("M");
		$("#frm").attr("action", "/name_check/name_check_form.do");
		$("#frm").submit();
	});

	/**
     * ( TB_NAME_CHECK ) 삭제
     */
	$("#btn_delete").click(function() {
		if(!confirm(" 삭제하시겠습니까 ?")) return;
		$("input[name=method]").val("D");
		$.ajax({
	    	url		: "<c:url value='/name_check/name_check_delete.do'/>",
	        type	: "POST",
	        data	: $("#frm").serialize(),
	        dataType: "text",
	        success : function(data) {
	        	data = data.replace(/[<][^>]*[>]/gi, '');
	        	var jData = JSON.parse(data);
	            if(jData.result_cd == "200") {
	            	alert('삭제하였습니다.');
	        		$("#frm").attr("action", "/name_check/name_check_list.do");
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
//-->
</script>
<div id="con_tit">
	<!-- 타이틀 -->
	<h3>  정보</h3>
	<p class="jump_menu">TB_NAME_CHECK 관리 &gt <b>TB_NAME_CHECK 관리</b></p>
	</div>

	<!-- 이동 폼 -->
	<form id="frm" name="frm" method="post">
		<input type="hidden" name="page" value="${pageInfo.page}"/>
		<input type="hidden" name="search_word" size="20" value="${pageInfo.search_word}">
		<input type="hidden" name="method" value="M"/>
		<input type="hidden" name="check_seq" value="${entity.CHECK_SEQ}"/>
	</form>
	<!-- //이동 폼-->
	<!-- 컨텐츠 본문-->
	<div id="content">
	<!-- 상세보기 내용 -->
	<table class="table02">
			<tr>
				<th>일련번호</th>
				<td>${entity.CHECK_SEQ}</td>
			</tr>
			<tr>
				<th>식별번호</th>
				<td>${entity.CI}</td>
			</tr>
			<tr>
				<th>폰번호</th>
				<td>${entity.PHONE_NO}</td>
			</tr>
			<tr>
				<th>통신사</th>
				<td>${entity.PHONE_CORP}</td>
			</tr>
			<tr>
				<th>생년월일</th>
				<td>${entity.BIRTHDAY}</td>
			</tr>
			<tr>
				<th>성별</th>
				<td>${entity.GENDER}</td>
			</tr>
			<tr>
				<th>이름</th>
				<td>${entity.CHECK_NAME}</td>
			</tr>
			<tr>
				<th>확인방법</th>
				<td>${entity.CERT_MET}</td>
			</tr>
			<tr>
				<th>이용자 IP</th>
				<td>${entity.IP}</td>
			</tr>
			<tr>
				<th>내_외국인</th>
				<td>${entity.NATION}</td>
			</tr>
			<tr>
				<th>미성년_이름</th>
				<td>${entity.M_CHECK_NAME}</td>
			</tr>
			<tr>
				<th>미만_생일</th>
				<td>${entity.M_BIRTHDAY}</td>
			</tr>
			<tr>
				<th>미만_성별</th>
				<td>${entity.M_GENDER}</td>
			</tr>
			<tr>
				<th>미만_국가</th>
				<td>${entity.M_NATION}</td>
			</tr>
			<tr>
				<th>사용자 코드</th>
				<td>${entity.USER_SEQ}</td>
			</tr>	
		
	</table>
	<!-- //상세보기 내용 -->
	
	<!-- 표 이동 버튼 -->
	<p class="left"><a href="#"><img id="btn_list" src="<c:url value='/images/other/b-list01.gif'/>" alt="목록" /></a> </p>
	<p>
	<ul class="list_btn">
		<li><a href="#"><img id="btn_modify" src="<c:url value='/images/other/b-re01.gif'/>" alt="수정" /></a></li>
		<li><a href="#"><img id="btn_delete" src="<c:url value='/images/other/b-del01.gif'/>" alt="삭제" /></a></li>
		<li><a href="#"><img id="btn_cancel" src="<c:url value='/images/other/b-cancel01.gif'/>" alt="취소" /></a></li>
	</ul>
	</p>
	<!-- //표 이동 버튼 -->
	</div>
