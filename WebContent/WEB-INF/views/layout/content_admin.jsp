<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script language="javascript">
$(document).ready(function(){
	$("#admin").load(function(){
		//this.style.width  = this.contentWindow.document.body.offsetWidth + 100 + 'px';
		this.style.width  = 1150 + 10 + 'px';
		if(document.getElementById('autoSize').value == 'Y'){
			this.style.height = this.contentWindow.document.body.offsetHeight + 200 + 'px';
		}else{
			this.style.height = this.contentWindow.document.body.offsetHeight + 100 + 'px';	
		}
	});
});
</script>
<!--컨텐츠 본문-->
<input type="hidden" id="autoSize" name="autoSize" value="">
<div id="adright">
	<iframe id="admin" name="admin" frameborder="2" width="1150" height="400" marginheight="0" marginwidth="0" src="/admin_main.jsp" scrolling="no" ></iframe>	
</div>