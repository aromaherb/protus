
function downloadfile(num, board_data_id, downloadurl){
	//alert(num+','+board_data_id+','+downloadurl);
	location.href = downloadurl+"?num="+num+"&board_data_id="+board_data_id;
}


function refreshfiledata(filediv, filelisturl){
	var param = "num="+$('#num').val();
	$.ajax({
		url:filelisturl, //데이터를 요청할 페이지
		dataType: 'json',                   //데이터 유형
		data:param,  //요청할 페이지에 전송할 파라메터
		error:function(xhr,status,e){       //에러 발생시 처리함수
			alert('통신에러가 발생했습니다.');
		},
		success: function(jdata){           //성공시 처리 함수, 인수는 위에서 data를 사용한 경우
			var data = jdata;
			if(jdata.result_cd=='200'){
				var boardfiles = '';
				if(jdata.entities!=''){
				for(var i=0; i<jdata.entities.length; i++){
					boardfiles+= "<a href=\"javascript:downloadfile('"+jdata.entities[i].num+"','"+jdata.entities[i].board_data_id+"','"+downloadurl+"')\">"+jdata.entities[i].attached_file_realname+"</a>&nbsp;";
				}
				}else{
					boardfiles = '첨부파일없음';
				}
				$('#'+filediv).html(boardfiles);
			}
	    }
	});	
}

//replaceAll
function replaceAll(sValue, param1, param2) {
    return sValue.split(param1).join(param2);
}
/*
 * str에서 특수문자를 변경한다.
 */
function titleBanScrpt(str) {
	str = str.replace(/</gi, "&lt;");
	str = str.replace(/%/gi, "&#37;");
	str = str.replace(/>/gi, "&gt;");
	
	return str;
}

function contentBanScrpt(str) {
	str = str.replace(/<script/gi, "&lt;");
	str = str.replace(/%/gi, "&#37;");

	return str;
}
//array to object
function serializeObject(array){
	var obj = {};
	$.each(array, function(){
		if(obj[this.name]){
			if(!obj[this.name].push){
				obj[this.name] = [obj[this.name]];
			}
			obj[this.anme].push(this.vale || '');
		}else {
			obj[this.name] = this. value || '';
		}
	});
	return obj;
}
//USAGE: $("#form").serializefiles();
(function($) {
	$.fn.serializefiles = function() {
	    var obj = $(this);
	    /* ADD FILE TO PARAM AJAX */
	    var formData = new FormData();
	    $.each($(obj).find("input[type='file']"), function(i, tag) {
	        $.each($(tag)[0].files, function(i, file) {
	            formData.append(tag.name, file);
	        });
	    });
	    var params = $(obj).serializeArray();
	    $.each(params, function (i, val) {
	        formData.append(val.name, val.value);
	    });
	    return formData;
	};
})(jQuery);

//레이어팝업
function layerOpen(el){
	 var temp = $('#' + el);
	 var bg = temp.prev().hasClass('bg');    //dimmed 레이어를 감지하기 위한 boolean 변수
	 if(bg){
	  $('.layer').fadeIn();   //'bg' 클래스가 존재하면 레이어가 나타나고 배경은 dimmed 된다.
	 }else{
	  temp.fadeIn();
	 }
	 // 화면의 중앙에 레이어를 띄운다.
	 
	if (temp.outerHeight() < $(document).height() ) temp.css('margin-top', '-'+temp.outerHeight()/2+'px');
	else temp.css('top', '0px');
	if (temp.outerWidth() < $(document).width() ) temp.css('margin-left', '-'+temp.outerWidth()/2+'px');
	else temp.css('left', '0px');
	 
	temp.find('a.cbtn').click(function(e){
	  if(bg){
	   $('.layer').fadeOut(); //'bg' 클래스가 존재하면 레이어를 사라지게 한다.
	    }else{
	      temp.fadeOut();
	    }
	  e.preventDefault();
	 });   
	 
}
// 시간 체크 
function timeCheck (insTime) {     
	// 정규표현식
    var languageCheck = /^[0-9]{2}:[0-9]{2}$/;
    if (languageCheck.test(insTime)) {        
        return true;
    }
    return false;    
}
// 공백체크 확인 및 문자 앞뒤 공백 제거
function blankCheck (insDate) {     
	// 정규표현식
    var languageCheck = insDate.trim();
    if (languageCheck != "") {        
        return true;
    }
    return false;    
}
// 날짜 체크
function dateCheck(srtDt, endDt){	 
	var arySrtDt = srtDt.split("-"); // ex) 시작일자
    var aryEndDt = endDt.split("-"); // ex) 종료일자
 
    if( arySrtDt.length != 3 || aryEndDt.length != 3){ 
        alert("날짜 형식이 잘못되었습니다."); return false;
    }
 
    var startDt = new Date(Number(arySrtDt[0]),Number(arySrtDt[1])-1,Number(arySrtDt[2]));
    var endDt	= new Date(Number(aryEndDt[0]),Number(aryEndDt[1])-1,Number(aryEndDt[2]));
    resultDt	= Math.floor(endDt.valueOf()/(24*60*60*1000)- startDt.valueOf()/(24*60*60*1000));
 
    if(resultDt < 0 ){ alert("시작일자와 종료일자를 확인하여 주세요!."); return false; }
     
    return true;
}
