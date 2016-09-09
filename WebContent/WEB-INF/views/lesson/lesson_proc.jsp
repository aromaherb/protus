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
111111
<script type="text/javaScript">
//<![CDATA[
$(function() {

	/**
	 * ( TB_LESSON ) 저장
	 */
	$("#btn_save").click(function() {
	    /* if( verifyForm() == false) return; */
	    $("form[name=formfile]").submit();
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

//]]>
</script>