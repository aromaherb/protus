<%@page import="kr.co.protus.controllers.base.UserController"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javaScript">
<!--
$(function() {

	/**
	 * ( TB_USER ) 저장
	 */
	$("#btn_save").click(function() {
//	    if( verifyForm() == false) return;
	    
	    $.ajax({
	    	url		: "<c:url value='/user/user_save.do'/>",
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
	 * ( TB_USER ) 취소
	 */
	$("#btn_cancel").click(function(e) {
	    history.back(-1);
	    e.preventDefault();
	});
	
	/**
     * ( TB_USER ) 목록
     */
  	$("#btn_list").click(function() {
		$("#formEdit").attr("action", "/user/user_list.do");
		$("#formEdit").submit();
	});
}); 

/**
 * ( TB_USER ) 입력 값 검증
 */
/* function verifyForm() {	
	if(Valid.isEmpty($("input[name=user_seq]").val())) {
		alert(" 는 필수입력 항목입니다.");
		return false;
	}
	return true;
} */

//-->
</script>

<div class="content">
	<ul class="tab">
		<li class="on">회원가입</li>
	</ul>
	<!--<div class="visual">
 				 <h2>
 				 	protus</br>
 				 	강의올리기
 				 </h2>
 			</div> 위에 비주얼추가될 -->
	<div class="tab_con">
		<div class="member">
			<ul>
				<li><a href="owner_join.html">원장 회원가입</a>
					<ul>
						<li><span></span>학원을 운영하고 계시는 원장선생님</li>
						<li><span></span>가입비 : 10,000원(동영상 강의 최초 업로드시 환불)</li>
					</ul>
				</li>
				<li><a href="teacher_join.html">강사 회원가입</a>
					<ul>
						<li><span></span>학원에서 강의를 하시는 선생님</li>
						<li><span></span>본인의 강의를 등록하고 싶은 개인강사</li>
						<li><span></span>가입비 : 10,000원(동영상 강의 최초 업로드시 환불)</li>
					</ul>
				</li>
				<li><a href="student_join.html">학생 회원가입</a>
					<ul>
						<li><span></span>공부하는 사람 누구나</li>
						<li><span></span>가입비 : 없음</li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
</div>