$(function(){
	function intro(){
     
    
     
	}

	function mnb(){
	$(".mobile_menu").on("click",function(){
           	$(".nav").animate({
        		left:"0%"
        	},50);
        	var maskHeight = $(document).height();
        	var maskWidth = $(window).width();
        	$(".mask").css({'width':maskWidth, 'height':maskHeight}).css("opacity","0.2")
        	$(".mask").fadeIn(0);
        })
        
    $(".nav").mouseleave(function(){
    	$(".nav").animate({
    		left:"-900%"
    	},200);
    	var maskHeight = $(document).height();  
            var maskWidth = $(window).width();
			$(".mask").css({'width':maskWidth,'height':maskHeight}).css("opacity","0");
			$(".mask").fadeOut(0);
    })
    
       
        
    $(".nav .close_btn").on("click",function(){
    	$(".nav").animate({
    		left:"-9999%"
    	},200);
    	var maskHeight = $(document).height();  
            var maskWidth = $(window).width();
			$(".mask").css({'width':maskWidth,'height':maskHeight}).css("opacity","0");
			$(".mask").fadeOut(200);
    })	
	}

	function gnb(){
     $(".nav_pc .menu li h3").click(function(){
     	$(".nav_pc .menu li ul").slideUp();
     	if(!$(this).next().is(":Visible"))
     	{
     		$(this).next().slideDown();
     	}
     })   
	}


	/*function sub_tab(){
	 $(function(){	
		tab(".tab",0);
	});
	
	function tab(e, num){
    var num = num || 0;
    var menu = $(e).children();
    var con = $(e+'_con').children();
    var select = $(menu).eq(num);
    var i = num;

    select.addClass('on');
    con.eq(num).show();

    menu.click(function(){
        if(select!==null){
            select.removeClass("on");
            con.eq(i).hide();
        }

        select = $(this);	
        i = $(this).index();

        select.addClass('on');
        con.eq(i).show();
    });
	}
}
*/
	function buttons(){
		$(".content03_study").click(function(){
			location.replace('study.html')
		})
	}
////////////////////////////
	intro()
	mnb()
	gnb()		
	sub_tab()
	buttons()	
		
})