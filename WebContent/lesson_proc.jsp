<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	String file_key_W = "";
	String file_key_I = "";
	String file_key_A = "";
	String file_key_M = "";
	String media_key = "";
	file_key_W = request.getParameter("file_key_W");
    file_key_I = request.getParameter("file_key_I");
    file_key_A = request.getParameter("file_key_A");
    file_key_M = request.getParameter("file_key_M");
    media_key = request.getParameter("origin_file_key");
    System.out.println("proc : " + file_key_W + " | " + file_key_I +  " | " + file_key_A +  " | " + file_key_M +  " | " + media_key);
%>

<script type="text/javaScript">
//<![CDATA[

	$(function() {
		/**
		 * ( TB_LESSON ) 저장
		 */
		$.ajax({
			url : "<c:url value='/lesson/lesson_save.do'/>",
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

	//]]>
</script>

<div>
	<form id="formList" name="formList" class="inline" method="post" action="lesson_list.do">
		<input type="hidden" name="page" value="${pageInfo.page}"> 
		<input type="hidden" name="search_word" size="20" value="${pageInfo.search_word}">
	</form>
	
	<form id="formEdit" class="inline" name="formEdit" method="post" >
		<input type="hidden" name="method" value="${pageInfo.method}">
		<input type="hidden" name="lesson_title" size="100" value="${entity.LESSON_TITLE}">
		<input type="hidden" name="lesson_content" size="100" value="${entity.LESSON_CONTENT}">
		<input type="hidden" name="file_key_W" size="100" value="<%=file_key_W%>">
		<input type="hidden" name="file_key_I" size="100" value="<%=file_key_I%>">
		<input type="hidden" name="file_key_A" size="100" value="<%=file_key_A%>">
		<input type="hidden" name="file_key_M" size="100" value="<%=file_key_M%>">
		<input type="hidden" name="media_key" size="100" value="<%=media_key%>">
		<input type="hidden" name="course" size="100" value="${entity.COURSE}">
		<input type="hidden" name="subject" size="100" value="${entity.SUBJECT}">
		<input type="hidden" name="category" size="100" value="${entity.CATEGORY}">
		<input type="hidden" name="charge_yn" size="100" value="${entity.CHARGE_YN}">
		<input type="hidden" name="close_date" size="100" value="${entity.CLOSE_DATE}">
		<input type="hidden" name="reserve_close_yn" size="100" value="${entity.RESERVE_CLOSE_YN}">
		<input type="hidden" name="close_yn" size="100" value="${entity.CLOSE_YN}">
		<input type="hidden" name="lesson_price" size="100" value="${entity.LESSON_PRICE}">
		<input type="hidden" name="user_seq" size="100" value="${entity.USER_SEQ}">
		<input type="hidden" name="lesson_doc" size="100" value="${entity.LESSON_DOC}">
		<input type="hidden" name="approve_yn" size="100" value="${entity.APPROVE_YN}">
		<input type="hidden" name="approve_dt" size="100" value="${entity.APPROVE_DT}">
		<input type="hidden" name="lesson_seq" size="100" value="${entity.LESSON_SEQ}">
		<input type="hidden" name="write_dt" size="100" value="${entity.WRITE_DT}">
		<input type="hidden" name="modify_dt" size="100" value="${entity.MODIFY_DT}">
	</form>
</div>