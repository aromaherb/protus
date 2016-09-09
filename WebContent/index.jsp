<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="kr.co.raon.commons.CommonProperty" %>
<%@ page import="kr.co.raon.commons.util.*" %>
<%
	CommonProperty cf;
	String loginProcURL = "";
	cf = new CommonProperty();
	loginProcURL = StringUtil.null2Str(cf.getProp("loginProcURL"));
	
	response.sendRedirect(request.getContextPath() + loginProcURL);
%>