<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
	<meta charset="utf-8" /> 
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>PROTUS</title>
	<meta name="viewport" content="width=device-width; initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=medium-dpi" /> 
	<meta name="author" content="PROTUS" />
	<meta name="apple-mobile-web-app-capable" content="yes"> 
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="theme-color" content="#323333">
  
  	<link rel="stylesheet" href="/css/import.css" type="text/css"/>
  	<link rel="stylesheet" type="text/css" href="<c:url value='/css/jquery-ui-1.8.18.custom.css'/>"/>
	
	<script type="text/javaScript" src="<c:url value='/js/jquery-2.1.3.min.js'/>"></script>
    <script type="text/javaScript" src="<c:url value='/js/jquery-3.0.0.js'/>"></script>
    
	<script type="text/javascript" src="<c:url value='/js/jquery-ui.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/action.js'/>"></script>
	
	<script type="text/javascript" src="<c:url value='/js/wysiwyg.js'/>"></script>
	<script type="text/javaScript" src="<c:url value='/js/jquery.form.js'/>"></script>
	<script type="text/javaScript" src="<c:url value='/js/valid.js'/>"></script>
	
	<script type="text/javaScript" src="<c:url value='/js/json2.js'/>"></script>
	<script type="text/javaScript" src="<c:url value='/js/popup.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/common_admin.js'/>"></script>
</head>
<body>
	<div class="home">
 		<a href="index.html" ><img src="/images/home.png" alt="홈으로" /></a>
 	</div>
 	<tiles:insertAttribute name="left_admin_mobile"/>
 	<div class="mask"></div>
 	<div class="container">
 		<div class="mobile_menu"><img src="/images/nav/menu.png" alt="모바일메뉴" /></div>
 		<section class="content07_01">
 		<!--좌측메뉴 시작-->
 		<tiles:insertAttribute name="left_admin"/>
 		<!--좌측메뉴 끝-->
 		
 		<!--우측 컨텐츠 시작-->
		<tiles:insertAttribute name="content"/>
		<!--우측 컨텐츠 끝-->
		
		</section>
 	</div>	
	<!--footer 영역 시작-->
	<tiles:insertAttribute name="footer"/>
	<!--footer 영역 끝-->

</body>
</html>
