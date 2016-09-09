var Popup = {
		/*
	    $(function(){
	        $("#dialog_load").dialog({
	            modal : true,
	            autoOpen : false,
	            closeOnEscape : false,
	            resizable : false,
	            open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); }
	        });
	    });
	    */
	crtPopup : function( url, name , width, height,title){
		if(!title) title = "Information";
		var isSuccess = true;

		str = "<div id='" + name + "' style='display:none;' ></div>";
		$("body").append(str);

		$.ajax({
			url : url,
			type : 'GET',
			//async : false,
			success : function(data, textStatus, jqXHR){
					$("#"+name).append(data);
					$("#" + name ).dialog({
						modal : true,
						width : width,
						height : height,
						closeOnEscape : false,
			            resizable : false,
			            title : title,
						close : function(){
							$("#" + name).remove();
						}
					});
			},
			errer : function(jqXHR, textStatus, errorThrown) {
				alert(textStatus);
			},
			statusCode : {
				400: function(){
					alert("Popup 화면 생성에 실패하였습니다.");
					$("#" + name).remove();
				},
				404: function(){
					alert("Profile정보가 존재하지 않습니다.");
					$("#" + name).remove();
				},
				501: function(){
					alert("Profile정보 조회중 서버 오류가 발생하였습니다.");
					$("#" + name).remove();
				}
					
			}
		});
	},

	crtPostPopup : function( url, data, name , width, height){
		var isSuccess = true;

		str = "<div id='" + name + "' style='display:none;' ></div>";
		$("body").append(str);

		$.ajax({
			url : url,
			type : 'POST',
			data : data,
			//async : false,
			success : function(data, textStatus, jqXHR){
					$("#"+name).append(data);
					$("#" + name ).dialog({
						modal : true,
						width : width,
						height : height,
						close : function(){
							$("#" + name).remove();
						}
					});
			},
			errer : function(jqXHR, textStatus, errorThrown) {
				alert(textStatus);
			},
			statusCode : {
				400: function(){
					alert("Popup 화면 생성에 실패하였습니다.");
					$("#" + name).remove();
				},
				404: function(){
					alert("Profile정보가 존재하지 않습니다.");
					$("#" + name).remove();
				},
				501: function(){
					alert("Profile정보 조회중 서버 오류가 발생하였습니다.");
					$("#" + name).remove();
				}
					
			}
		});
	},
	clsPopup : function(name) {
		$("#" + name).dialog("close");
	}

}

Popup.create = Popup.crtPopup;
Popup.close = Popup.clsPopup;
Popup.createPost = Popup.crtPostPopup;
