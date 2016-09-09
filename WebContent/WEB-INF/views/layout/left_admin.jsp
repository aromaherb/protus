<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript">
	
function login(){
    var email = $('input[name="email"]').val();
    var passwd = $('input[name="passwd"]').val();
    
    if(email==""){
       alert('아이디를 입력하세요');
       return;
    }else if(passwd==""){
       alert('비밀번호를 입력하세요');
       return;
    }
    login.submit();
    //document.getElementById("loginform").submit();
 }
 
function movePage(page){
    var url = "/" + page + ".do";  
    $('.content').load(url);
 }
	
</script>

<div class="nav_pc">

	<img class="close_btn" src="/images/nav/close.png" alt="닫기" />
	<h1>
		<a href="..\index.do"><img src="/images/logo.png" alt="PROTUS" /></a>
	</h1>
	<c:if test="${empty email}">
		<form class="login" action="" name="login" method="post">
			<ul>
				<li class="id"><input type="text" id="email" name="email" placeholder=" 아이디"></li>
				<li class="pass"><input type="text" id="passwd" name="passwd" placeholder=" 비밀번호"></li>
				<li class="log"><input type="button" value="로그인" onclick="login()"></li>
				<li class="in"><a href="..\user\user_form.do"><input type="button" value="회원가입" ></a></li>
				<li class="find"><input type="button" value="아이디/비밀번호찾기" ></li>
			</ul>
		</form>
	</c:if>
	<c:if test="${not empty emial}">
			<c:out value="${emial}" />	님께서 접속 하셨습니다.
			<ul>

			</ul>
	</c:if>

	<ul class="menu">
		<li>
			<h3>
				<span class="icon_01"></span>PROTUS란?
			</h3>
			<ul>
				<li><a href="..\index.do">PROTUS소개</a></li>
				<li><a href="..\index.do">PROTUS이용방법</a></li>
			</ul>
		</li>
		<li>
			<h3>
				<span class="icon_02"></span>공지사항
			</h3>
			<ul>
				<li><a href="..\notice\notice_list.do">전체공지사항</a></li>
				<li><a href="..\event\event_list.do">이벤트</a></li>
			</ul>
		</li>
		<li>
			<h3>
				<span class="icon_03"></span>강의동영상
			</h3>
			<ul>
				<li><a href="study.do">추천강의</a></li>
				<li><a href="study.do">인기강의</a></li>
				<li><a href="study.do">학원강의</a></li>
				<li><a href="..\lesson\lesson_list.do">전체강의</a></li>
			</ul>
		</li>
		<li>
			<h3>
				<span class="icon_04"></span>난제타파
			</h3>
			<ul>
				<li><a href="..\problem\problem_list.do">질문보기</a></li>
				<li><a href="..\problem\problem_form.do">질문하기</a></li>
			</ul>
		</li>
		<li>
			<h3>
				<span class="icon_05"></span>내강의관리
			</h3>
			<ul>
				<li><a href="study_info.do">내강의관리</a></li>
				<li><a href="study_info.do">강의올리기</a></li>
			</ul>
		</li>
		<li>
			<h3>
				<span class="icon_06"></span>커뮤니티
			</h3>
			<ul>
				<li><a href="..\community\community_list.do">자유게시판</a></li>
				<li><a href="..\institute_notice\institute_notice_list.do">학원게시판</a></li>
			</ul>
		</li>
		<li>
			<h3>
				<span class="icon_07"></span>관리페이지
			</h3>
			<ul>
				<li><a href="..\user\user_list.do">사용자관리</a></li>
				<li><a href="..\institutee_teacher\institute_teacher_list.do">강사관리</a></li>
				<li><a href="..\institute_student\institute_student_list.do">학생관리</a></li>
			</ul>
		</li>
		<li>
			<h3>
				<span class="icon_08"></span>매출관리
			</h3>
			<ul>
				<li><a href="pay.do">매출관리</a></li>

			</ul>
		</li>
	</ul>
</div>
