var Valid = {
	isNotKorean : function(str){
		var reg = /^[\w\/]*$/i;
		return reg.test(str);
	},
	
	isHangle : function(str) {
	    var reg = /[a-zA-Z0-9\s~!@#\$%\^&\*\(\)_\+\{\}|:"<>\?`\-=\[\]\\;',\.\/]/; // matches a alphanumeric character or space

	    return ! reg.test(str);
	},
	
	isEmpty : function(str) {
		var reg = /[^ ]+/;
		return ! reg.test(str);
	},

	isNumber : function(str){
		var reg = /^\d*$/i;
		return reg.test(str);
	},
	
	isDouble : function(str){
		var reg = /(^\d+$)|(^\d+\.\d+$)/;
		return reg.test(str);
	},

	isDateFmt : function(str){
		var reg = /^\d{4}-\d{2}-\d{2}$/;
		return reg.test(str);
	},

	isApiUrl : function(str){
		var reg = /^[\w]+[\w\/\.]*$/i;
		return reg.test(str);
	},

	isUrl : function(str){
		// var reg = /^[\w]([\w-]+\.)+[\w-]+([\w-./?%&=]*)$/i;
		// var reg = /^([a-z]+):\/\/((?:[a-z\d\-]{2,}\.)+[a-z]{2,})(:\d{1,5})?(\/[^\?]*)?(\?.+)?$/i;
		var reg = /^((?:[a-z\d\-]{2,}\.)+[a-z]{2,})(:\d{1,5})?(\/[^\?]*)?(\?.+)?$/i;
		return reg.test(str);
	},

	isPhone : function(str){
		var reg1 = /^\d{9,12}$/i;
		var reg2 = /^\d{2,4}-\d{3,4}-\d{4}$/i;
		return (reg1.test(str) || reg2.test(str));
	},

	isEmail : function(str){
		var reg = /^[\w]*@[\w.]*$/i;
		return reg.test(str);
	},

	isAcl : function(str){
		var reg = /^[\d]{1,3}$/i;
		return reg.test(str);
	},

	isPassward : function(str) {
		var reg = /[\W]+/i; // 특수문자
		if ( reg.test(str) ){
			// 특수문자 들어있을 경우 8~20
			var reg = /^[\w\W]{8,20}$/i;
			return reg.test(str);
		} else {
			// 영숫자 혼용 체크 12~20
			var numreg = /[\d]+/i;
			var strreg = /[a-zA-Z]+/i;
			if (numreg.test(str) && strreg.test(str)){
				var reg = /^[\w]{12,20}$/i;
				return reg.test(str);
			} else {
				return false;
			}
		}
	},
	
	/** 주민번호 체크 */
	isResidentId : function(ju_id1,ju_id2)	{
	    var codesum = 0;
	    var coderet = 0;
	    
	    var reg1 = /^\d{6}$/;
	    var reg2 = /^\d{7}$/;

	    if( !reg1.test(ju_id1) ) return false;
	    if( !reg2.test(ju_id2) ) return false;

	    codesum = ( eval( ju_id1.substring( 0, 1 ) ) * 2 )
	            + ( eval( ju_id1.substring( 1, 2 ) ) * 3 )
	            + ( eval( ju_id1.substring( 2, 3 ) ) * 4 )
	            + ( eval( ju_id1.substring( 3, 4 ) ) * 5 )
	            + ( eval( ju_id1.substring( 4, 5 ) ) * 6 )
	            + ( eval( ju_id1.substring( 5, 6 ) ) * 7 )

	            + ( eval( ju_id2.substring( 0, 1 ) ) * 8 )
	            + ( eval( ju_id2.substring( 1, 2 ) ) * 9 )
	            + ( eval( ju_id2.substring( 2, 3 ) ) * 2 )
	            + ( eval( ju_id2.substring( 3, 4 ) ) * 3 )
	            + ( eval( ju_id2.substring( 4, 5 ) ) * 4 )
	            + ( eval( ju_id2.substring( 5, 6 ) ) * 5 );

	    coderet = 11 - ( eval( codesum % 11 ) );

	    if ( coderet >= 10 )
	        coderet = coderet - 10;

	    if ( coderet >= 10 )
	        coderet = coderet - 10;

	    if ( eval( ju_id2.substring( 6, 7 ) ) != coderet ) {
			return false;
	    }
		return true;
	}
};
