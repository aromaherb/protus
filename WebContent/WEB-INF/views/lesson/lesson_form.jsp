
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
/***************************************************************************************************
 * 파일명 : lesson_form.jsp
 * 작성자 : 장재현 (jang1945@raonsolution.co.kr)
 * 작성일 : 2016-8-19
 * 내  용 : ( TB_LESSON )의 입력 및 수정 form출력/저장/취소/입력 값 검증
 ***************************************************************************************************/
%>

<script type="text/javaScript">
//<![CDATA[
$(function() {

	/**
	 * ( TB_LESSON ) 저장
	 */
	 /*
	 $("#formFile").submit(function(e)
				{
				    var postData = $(this).serializeArray();
				    var formURL = $(this).attr("action");
				    $.ajax(
				    {
				        url : formURL,
				        enctype: "multipart/form-data",
				        type: "POST",
				        data : postData,
				        success:function(data, textStatus, jqXHR) 
				        {
				        	alert(jqXHR);
				            //data: return data from server
				        },
				        error: function(jqXHR, textStatus, errorThrown) 
				        {
				            //if fails      
				        }
				    });
				    e.preventDefault(); //STOP default action
				    e.unbind(); //unbind. to stop multiple form submit.
				});
	*/
	$("#btn_save").click(function() {
	    /* if( verifyForm() == false) return; */
	
		$("#formEdit").submit();
	    //location.href = "/lesson/lesson_proc.do";
	    //$("form[name=formFile]").submit();
	   
	    /*
	    $.ajax({
	    	url		: "<c:url value='/lesson/lesson_save.do'/>",
	        type	: "POST",
	        data	: $("#formEdit").serialize(),
	        dataType: "text",
	        success : function(data) {
	        	data = data.replace(/[<][^>]*[>]/gi, '');
	            var jData = JSON.parse(data);
	            if(jData.result_cd == "200") {
	            	alert("저장되었습니다.");
	        		$("form[name=formList]").submit();
	        		
	            }
	            else {
	                alert(jData.result_msg);
	            }
	        },
	        error : function(xhr,status,error) {
	            alert(error);
	        }
	    });
	    */
	    
	});
	
	/**
	 * ( TB_LESSON ) 취소
	 */
	$("#btn_cancel").click(function(e) {
	    history.back(-1);
	    e.preventDefault();
	});
	
	/**
     * ( TB_LESSON ) 목록
     */
  	$("#btn_list").click(function() {
		$("#formEdit").attr("action", "/lesson/lesson_list.do");
		$("#formEdit").submit();
	});
}); 

/**
 * ( TB_LESSON ) 입력 값 검증
 */
/* function verifyForm() {	
	if(Valid.isEmpty($("input[name=lesson_seq]").val())) {
		alert("강의 일련번호 는 필수입력 항목입니다.");
		return false;
	}
	return true;
} */
//]]>
</script>
<!-- 목록 이동 폼 -->
<form id="formList" name="formList" class="inline" method="post" action="lesson_list.do">
	<input type="hidden" name="page" value="${pageInfo.page}"> 
	<input type="hidden" name="search_word" size="20" value="${pageInfo.search_word}">
</form>
<!-- //목록 이동 폼 -->
<div class="content">
	<ul class="tab">
		<li class="on">강의동영상</li>
		<!-- <li class="on"><a href="..\problem\problem_form.do">질문하기</a></li> -->
	</ul>
	<div class="visual">
		<h2>
			protus<br> 
			강의동영상
		</h2>
	</div>
	<div class="tab_con">
		<!-- 폼 -->
		<!-- <form id="formEdit" class="inline" name="formEdit" method="post"> -->
		<form id="formEdit" class="inline" name="formEdit" method="post" action="/lesson_proc.jsp" onsubmit="return doSubmit(event,this)">
			<input type="hidden" name="method" value="${pageInfo.method}">
			<div class="board_write">

				<!-- 테이블 내용 -->
				<table class="table02">

					<tr>
						<th><label class="input_lab" for="lesson_title">강의제목</label></th>
						<td><input type="text" name="lesson_title" size="100" value="${entity.LESSON_TITLE}"></td>
					</tr>				
					<tr>
						<th><label class="input_lab" for="lesson_content">강의소개</label></th>
						<td><input type="text" name="lesson_content" size="100" value="${entity.LESSON_CONTENT}"></td>
					</tr>
					<tr>
						<th><label class="input_lab" for="fileupload">파일 올리기</label></th>				
						<td>
							<!-- API 추가 -->
							<span class="blue_01">
									<script type="text/javascript" src="http://play.smartucc.kr/gabiaSmartHDUploader.js.php?k=faa130992e807d4101989853aa6e627c&c=기본분류&e=utf-8">							
									</script>
							</span>
							<!-- API 완료 --> 
						</td>
					</tr>
					<tr>
						<th><label class="input_lab" for="course">과정</label></th>
						<td><input type="text" name="course" size="100" maxlength="4" value="${entity.COURSE}"></td>
					</tr>
					<tr>
						<th><label class="input_lab" for="subject">과목</label></th>
						<td><input type="text" name="subject" size="100" maxlength="4" value="${entity.SUBJECT}"></td>
					</tr>
					<tr>
						<th><label class="input_lab" for="category">유형</label></th>
						<td><input type="text" name="category" size="100" maxlength="4" value="${entity.CATEGORY}"></td>
					</tr>
					<tr>
						<th><label class="input_lab" for="charge_yn">과금여부</label></th>
						<td><input type="text" name="charge_yn" size="100" value="${entity.CHARGE_YN}"></td>
					</tr>
					<tr>
						<th><label class="input_lab" for="close_date">강의종료일자</label></th>
						<td><input type="date" name="close_date" size="100" value="${entity.CLOSE_DATE}"></td>
					</tr>
					<tr>
						<th><label class="input_lab" for="reserve_close_yn">종료예약</label></th>
						<td><input type="text" name="reserve_close_yn" size="100" maxlength="1" value="${entity.RESERVE_CLOSE_YN}"></td>
					</tr>
					<tr>
						<th><label class="input_lab" for="close_yn">종료여부</label></th>
						<td><input type="text" name="close_yn" size="100" maxlength="1" value="${entity.CLOSE_YN}"></td>
					</tr>
					<tr>
						<th><label class="input_lab" for="lesson_price">강의료</label></th>
						<td><input type="text" name="lesson_price" size="100" value="${entity.LESSON_PRICE}"></td>
					</tr>
					<tr>
						<th><label class="input_lab" for="user_seq">사용자 코드</label></th>
						<td><input type="text" name="user_seq" size="100" value="${entity.USER_SEQ}"></td>
					</tr>
					<tr>
						<th><label class="input_lab" for="lesson_doc">강의자료</label></th>
						<td><input type="text" name="lesson_doc" size="100" value="${entity.LESSON_DOC}"></td>
					</tr>
					<tr>
						<th><label class="input_lab" for="approve_yn">승인여부</label></th>
						<td><input type="text" name="approve_yn" size="100" maxlength="1" value="${entity.APPROVE_YN}"></td>
					</tr>
					<tr>
						<th><label class="input_lab" for="approve_dt">승인일시</label></th>
						<td><input type="date" name="approve_dt" size="100" value="${entity.APPROVE_DT}"></td>
					</tr>

				</table>
				<input type="hidden" name="lesson_seq" size="100" value="${entity.LESSON_SEQ}">
				<input type="hidden" name="write_dt" size="100" value="${entity.WRITE_DT}">
				<input type="hidden" name="modify_dt" size="100" value="${entity.MODIFY_DT}">
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
