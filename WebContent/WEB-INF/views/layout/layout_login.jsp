<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="content-language" content="ko" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>${pageInfo.title}</title>
	<!--  link rel="stylesheet" type="text/css" href="<c:url value='/css/reset.css'/>" />  -->
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/admin.css'/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css'/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/style02.css'/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/jquery-ui-1.8.18.custom.css'/>"/>
	
	<script type="text/javaScript" src="<c:url value='/js/jquery-1.7.1.min.js'/>"></script>
	<script type="text/javaScript" src="<c:url value='/js/jquery-ui-1.8.18.custom.min.js'/>"></script>
	<script type="text/javaScript" src="<c:url value='/js/valid.js'/>"></script>
	<script type="text/javaScript" src="<c:url value='/js/json2.js'/>"></script>
	<script type="text/javaScript" src="<c:url value='/js/jquery.form.js'/>"></script>
	<script type="text/javaScript" src="<c:url value='/js/wysiwyg.js'/>"></script>
	<script type="text/javaScript" src="<c:url value='/js/common_board.js'/>"></script>
	<style type="text/css"></style>
</head>
<body>
<tiles:insertAttribute name="content"/>
</body>
</html>