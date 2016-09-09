<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
/***************************************************************************************************
 * 파일명 : code_form.jsp
 * 작성자 : 장재현 (jang1945@raonsolution.co.kr)
 * 작성일 : 2016-7-6
 * 내  용 : ( TB_CODE )의 입력 및 수정 form출력/저장/취소/입력 값 검증
 ***************************************************************************************************/
%>

<script type="text/javaScript">
<!--
$(function() {

	/**
	 * ( TB_CODE ) 저장
	 */
	$("#btn_save").click(function() {
	    if( verifyForm() == false) return;
	    
	    $.ajax({
	    	url		: "<c:url value='/code/code_save.do'/>",
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
	});
	
	/**
	 * ( TB_CODE ) 취소
	 */
	$("#btn_cancel").click(function(e) {
	    history.back(-1);
	    e.preventDefault();
	});
	
	/**
     * ( TB_CODE ) 목록
     */
  	$("#btn_list").click(function() {
		$("#formEdit").attr("action", "/code/code_list.do");
		$("#formEdit").submit();
		
	});
}); 

/**
 * ( TB_CODE ) 입력 값 검증
 */
function verifyForm() {	
	if(Valid.isEmpty($("input[name=group_code]").val())) {
		alert("그룹코드 는 필수입력 항목입니다.");
		return false;
	}
	if(Valid.isEmpty($("input[name=code]").val())) {
		alert("코드 는 필수입력 항목입니다.");
		return false;
	}
	return true;
}
//-->
</script>


<!-- 목록 이동 폼 -->
<form id="formList" name="formList" class="inline" method="post" action="code_list.do">
    <input type="hidden" name="page" value="${pageInfo.page}">
    <input type="hidden" name="search_word" size="20" value="${pageInfo.search_word}">
    <input type="hidden" name="search_word2" size="20" value="${pageInfo.search_word2}">
</form>
<div class="content">
 			<ul class="tab">
				<li class="on">코드관리</li>
			</ul>
			<div class="visual">
				 <h2>
				 	protus<br>
				 	코드관리
				 </h2>
			</div> <!--위에 비주얼추가될 -->
		 	<div class="tab_con">
 			<form id="formEdit" class="inline" name="formEdit" method="post">
				<input type="hidden" name="method" value="${pageInfo.method}">
 				<div class="board_write">
 				
 					<ul>
 						<li class="subject"><input type="text" name="group_code" size="100" value="${entity.GROUP_CODE}"></li>
 						<li class="write"><input type="text" name="code" size="100" value="${entity.CODE}"></li>
 						<li class="write"><input type="text" name="code_name" size="100" value="${entity.CODE_NAME}"></li>
 						<li class="write"><input type="text" name="v_value" size="100" value="${entity.V_VALUE}"></li>
 						<li class="write"><input type="text" name="v_value1" size="100" value="${entity.V_VALUE1}"></li>
 						<li class="write"><input type="text" name="n_value" size="100" value="${entity.N_VALUE}"></li>
 						<li class="write"><input type="text" name="n_value1" size="100" value="${entity.N_VALUE1}"></li>
 						<li class="write"><input type="text" name="d_value" size="100" value="${entity.D_VALUE}"></li>
 						<li class="con_box"><input type="text" name="etc" placeholder="내용을 입력하세요." value="${entity.ETC}"></li>
 					</ul>
 					
 				</div>
 				<ul class="send">
	 				<li class="write_button"><input id="btn_save" type="button" value="글쓰기"></li> 								
	 				<li class="cancle_button"><input id="btn_cancel" type="button" value="취소" ></li>								
				</ul> 			
			</form>
 			</div>
</div>
