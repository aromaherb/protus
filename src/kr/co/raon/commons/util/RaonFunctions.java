package kr.co.raon.commons.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// 암호화 관련 주석처리 : 실 개발시 적용 모듈로 코딩 
//import com.ksign.securedb.api.SDBCrypto;
//import com.ksign.securedb.api.util.SDBException;
//import org.apache.commons.codec.binary.Base64;

import kr.co.raon.commons.web.BaseController;


public class RaonFunctions extends BaseController {
	static String pageCR = "";
	
	/**
	 * 생성자
	 */
	public RaonFunctions() {
		super();
	}
    
	/**
     * 데이터 암호화
     * @param field 필드값
     * @return
     */
	/*
    public static String encodingData(String field) {
    	
    	String DomainIP = "211.51.233.45";
    	int DomainPort = 8629;
    	String ServerIP = "211.51.233.55";
    	int ServerPort = 9003;
        
    	String result = field;
    	
        try {
        	SDBCrypto crypto = new SDBCrypto();
			crypto = SDBCrypto.getInstance(DomainIP, DomainPort, ServerIP, ServerPort);
			result = crypto.encrypt("KSIGN", "TB_KEY", "ARIA256", field);
		} catch (SDBException e) {
			e.printStackTrace();
		}
        return result;
	}
    */
	
	/**
     * 데이터 복호화
     * @param field 필드값
     * @return
     */
	/*
    public static String decodingData(String field) {
    	
    	String DomainIP = "211.51.233.45";
    	int DomainPort = 8629;
    	String ServerIP = "211.51.233.55";
    	int ServerPort = 9003;
        
    	String result = field;
    	
        try {
        	SDBCrypto crypto = new SDBCrypto();
			crypto = SDBCrypto.getInstance(DomainIP, DomainPort, ServerIP, ServerPort);
			result = crypto.decrypt("KSIGN", "TB_KEY", "ARIA256", field);
		} catch (SDBException e) {
			e.printStackTrace();
		}
        return result;
	}
	*/
    
    /**
     * 데이터 암호화(BASE 64)
     * @param field 필드값
     * @return
     */
	/*
    public static byte[] encodeBase64(String field) {
    	
    	byte[] encoded = Base64.encodeBase64(field.getBytes());
        return encoded;
	}
    */
    /**
     * 데이터 복호화(BASE 64)
     * @param field 필드값
     * @return
     */
	/*
    public static byte[] decodeBase64(byte[] field) {
    	
    	byte[] decoded = Base64.decodeBase64(field);
        return decoded;
	}
    */
   
    /**
     * 전화번호 표시
     * @param regno    전화번호
     * @return
     */
    public static String fnTelNo(String telno) {
		return fnTelNo(telno, "");
	}
    
	/**
     * 전화번호 표시
     * @param regno    전화번호
     * @param option   옵션
     * @return
     */
    public static String fnTelNo(String telno, String option) {
		String sValue    = "";
		String pattern     = "^(01\\d{1}|02|0505|0502|0506|0\\d{1,2})-?(\\d{3,4})-?(\\d{4})"; // 핸드폰/일반전화번호
		String pattern15xx = "^(1544|1566|1577|1588|1644|1688)-?([0-9]{4})";                  // 15XX 번호

		if ( telno != null ) {
			if ( "".equals(telno) ) {
				sValue = "";
			} else {
				Pattern telNoPattern = Pattern.compile(pattern); 

				Matcher matcher = telNoPattern.matcher(telno);

				if ( matcher.matches() ) {
					sValue = matcher.group(1) + "-" + matcher.group(2) + "-" + matcher.group(3); 
				} else {
					telNoPattern = Pattern.compile(pattern15xx); 
					matcher = telNoPattern.matcher(telno);

					if ( matcher.matches() ) {
						sValue = matcher.group(1) + "-" + matcher.group(2); 
					} else {
						sValue = telno;
					}
				}
			}
		} else {
			sValue = "";
		}
		
		return sValue;
	}	
        
	/**
     * 숫자 표시
     * @param strDate  		날짜
     * @param strFormat   	포맷
     * @return
     */
    public static String fnNumber(String strNumber, String strPattern) {
    	String formatNumber = "";
    	
		// 숫자 형식 변환시 파싱 오류를 try.. catch..로 체크한다.
		try {
			// 형식 선언
		    DecimalFormat df = new DecimalFormat(strPattern);

		    // 변환
		    //formatNumber = df.format(Float.parseFloat(strNumber));
		    formatNumber = df.format(Double.parseDouble(strNumber));
		} catch (Exception e) {
			formatNumber = "0";
		}
		
		return formatNumber;
	}      
    
	/**
     * 날짜 표시
     * @param strDate  		날짜
     * @param strFormat   	포맷
     * @return
     */
    public static String fnDate(String strDate, String src_format, String dest_format) {
    	String formatDate = "";
    	
		// 날짜 형식 변환시 파싱 오류를 try.. catch..로 체크한다.
		try {
			// SimpleDateFormat의 형식을 선언한다.
			SimpleDateFormat sdfSrcFormat  = new SimpleDateFormat(src_format);
			SimpleDateFormat sdfDestFormat = new SimpleDateFormat(dest_format);

			// 문자열 타입을 날짜 타입으로 변환한다. 
			Date original_date = sdfSrcFormat.parse(strDate);
			
			formatDate = sdfDestFormat.format(original_date);
		} catch (ParseException e) {
			formatDate = "";
		}
		
		return formatDate;
	}    
  
    
    /**
     * 메뉴위치 조절을 위한 space 채우기
     * @param spaceLength 채울 길이
     * @return
     */
    public static String fnMenuSpace(int spaceLength) {
    	String strReturn = "";
    	int checkLength = spaceLength;

    	if ( checkLength >= 500 ) {
    		for ( int i = 0; i < checkLength / 500; i++ ) {
    			strReturn += "<li><img src=\"/images/menu/space_500.gif\" alt=\"\"></li>";
    		}
    		
    		checkLength = checkLength - ( checkLength / 500 * 500 ); 
    	}
    	
    	if ( checkLength >= 200 ) {
    		for ( int i = 0; i < checkLength / 200; i++ ) {
    			strReturn += "<li><img src=\"/images/menu/space_200.gif\" alt=\"\"></li>";
    		}
    		
    		checkLength = checkLength - ( checkLength / 200 * 200 ); 
    	}
    	
    	if ( checkLength >= 100 ) {
    		for ( int i = 0; i < checkLength / 100; i++ ) {
    			strReturn += "<li><img src=\"/images/menu/space_100.gif\" alt=\"\"></li>";
    		}
    		
    		checkLength = checkLength - ( checkLength / 100 * 100 ); 
    	}

    	if ( checkLength >= 50 ) {
    		for ( int i = 0; i < checkLength / 50; i++ ) {
    			strReturn += "<li><img src=\"/images/menu/space_50.gif\" alt=\"\"></li>";
    		}
    		
    		checkLength = checkLength - ( checkLength / 50 * 50 );
    	}    	

    	if ( checkLength >= 20 ) {
    		for ( int i = 0; i < checkLength / 20; i++ ) {
    			strReturn += "<li><img src=\"/images/menu/space_20.gif\" alt=\"\"></li>";
    		}
    		
    		checkLength = checkLength - ( checkLength / 20 * 20 );
    	}    	

    	if ( checkLength >= 10 ) {
    		for ( int i = 0; i < checkLength / 10; i++ ) {
    			strReturn += "<li><img src=\"/images/menu/space_10.gif\" alt=\"\"></li>";
    		}
    		
    		checkLength = checkLength - ( checkLength / 10 * 10 );
    	}

    	if ( checkLength >= 5 ) {
    		for ( int i = 0; i < checkLength / 5; i++ ) {
    			strReturn += "<li><img src=\"/images/menu/space_5.gif\" alt=\"\"></li>";
    		}
    		
    		checkLength = checkLength - ( checkLength / 5 * 5 );
    	}
    	
		for ( int i = 0; i < checkLength; i++ ) {
			strReturn += "<li><img src=\"/images/menu/space_1.gif\" alt=\"\"></li>";
		}    	
    	
    	return strReturn;
    }
    
    /**
     * 페이지 Tip 표시 함수
     * @param pageInfo  페이지정보
     * @return
     */
    public static String fnPageTip(Map<String, Object> pageInfo) {
		String sValue    = "";
// TODO 페이지 정보 표시 개발 팔요
		
		if ( pageInfo != null && !pageInfo.isEmpty() && pageInfo.size() > 0 
				&& ( pageInfo.get("pageTipYn") != null && "Y".equals(pageInfo.get("pageTipYn")) ) 
				&& ( pageInfo.get("pageTip")   != null && !"".equals(pageInfo.get("pageTip"))   ) ) {
			sValue = "<table width=\"990\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">                                                      "
				       + "  <tr>                                                                                                                  "
				       + "    <td colspan=\"2\"  background=\"/images/line_1.gif\" height=\"21\"></td>                                          "
				       + "    </tr>                                                                                                               "
				       + "  <tr>                                                                                                                  "
				       + "    <td width=\"150\" height=\"80\" align=\"right\"><img src=\"/images/check_03.gif\" width=\"69\" height=\"66\" /></td>"
				       + "    <td width=\"850\" align=\"left\" valign=\"top\">" + (String)pageInfo.get("pageTip") + "</td>                        "
				       + "    </tr>                                                                                                               "
				       + "  <tr>                                                                                                                  "
				       + "    <td colspan=\"2\" background=\"/images/line_2.gif\" height=\"21\"></td>                                             "
				       + "    </tr>                                                                                                               "
				       + "</table>                                                                                                                "
				       + "<div class=\"margin\"></div>";
		}
		
		return sValue;
	}    

    /**
     * 페이지 Tip 표시 함수(2단 layout용)
     * @param pageInfo  페이지정보
     * @return
     */
    public static String fnPageTip2(Map<String, Object> pageInfo) {
		String sValue    = "";
// TODO 페이지 정보 표시 개발 팔요
		
		if ( pageInfo != null && !pageInfo.isEmpty() && pageInfo.size() > 0 
				&& ( pageInfo.get("pageTipYn") != null && "Y".equals(pageInfo.get("pageTipYn")) ) 
				&& ( pageInfo.get("pageTip")   != null && !"".equals(pageInfo.get("pageTip"))   ) ) {
			sValue = "<table width=\"790\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">                                                      "
				       + "  <tr>                                                                                                                  "
				       + "    <td colspan=\"2\"  background=\"/images/line_1.gif\" height=\"21\"></td>                                          "
				       + "  </tr>                                                                                                                 "
				       + "  <tr>                                                                                                                  "
				       + "    <td width=\"100\" height=\"80\" align=\"right\"><img src=\"/images/check_03.gif\" width=\"69\" height=\"66\" /></td>"
				       + "    <td width=\"700\" align=\"left\" valign=\"top\">" + (String)pageInfo.get("pageTip") + "</td>                        "
				       + "    </tr>                                                                                                               "
				       + "  <tr>                                                                                                                  "
				       + "    <td colspan=\"2\" background=\"/images/line_2.gif\" height=\"21\"></td>                                             "
				       + "  </tr>                                                                                                                 "
				       + "</table>                                                                                                                "
				       + "<div class=\"margin\"></div>";
		}
		
		return sValue;
	}    
	/**
	 * 페이지 당 갯수 Select Box
	 * @param lstPage          페이지 당 갯수(,로 구분) 
	 * @param id				select id 및 name
	 * @param selectedValue		select 선택값
	 * @param optionValue		select 초기 옵션 값 [(선택), (전체) 등등]
	 * @param className			select class 명
	 * @param styleOption		select style 값
	 * @return
	 */
    public static String fnCntPerPageSelect(String lstPage, String id, String selectedValue, String optionValue, String className, String styleOption) {
    	String[] cntPerPage = lstPage.split(",");
    	
    	List<Map<String, Object>> lstData = new ArrayList<Map<String, Object>>();
    	
    	for ( int i = 0; i < cntPerPage.length; i++) {
    		Map<String, Object> mapData = new HashMap<String, Object>();
    		mapData.put("CODE",      cntPerPage[i]);
    		mapData.put("CODE_NAME", cntPerPage[i] + "개");
    		
    		lstData.add(mapData);
    	}
    	
    	return fnCodeSelect(lstData, id, selectedValue, optionValue, className, styleOption);
    }    
    
	/**
	 * 코드(TB_CODE) 테이블에서 GROUP_CODE 값으로 데이타를 가져와서 select 구성
	 * @param lstData			코드값 데이타
	 * @param groupCode			GROUP_CODE
	 * @param id				select id 및 name
	 * @param selectedValue		select 선택값
	 * @param optionValue		select 초기 옵션 값 [(선택), (전체) 등등]
	 * @param className			select class 명
	 * @param styleOption		select style 값
	 * @return
	 */
    public static String fnCodeSelect(List<Map<String, Object>> lstData, String id, String selectedValue, String optionValue, String className, String styleOption) {
    	String rtnValue  = "";
    	String codeValue = "";
    	String codeName  = "";
    	
    	// select 시작 및 id, name 처리
    	rtnValue = "<select id='" + id + "' name='" + id + "'";
    	
    	// class 처리
    	if ( className != null && !"".equals(className) ) {
    		rtnValue = rtnValue + " class='" + className + "'";
    	}
    	
    	// style 처리
    	if ( styleOption != null && !"".equals(styleOption) ) {
    		rtnValue = rtnValue + " style='" + styleOption + "'";
    	}

    	rtnValue = rtnValue + ">" + pageCR;

    	// 초기값 옵션 처리
    	if ( optionValue != null && !"".equals(optionValue) ) {
    		rtnValue = rtnValue + "    <option value=''>" + optionValue + "</option>" + pageCR;
    	} 	
    	
    	// 선택값 처리
    	if ( lstData != null && !lstData.isEmpty() && lstData.size() > 0 ) {
	    	for ( int i = 0; i < lstData.size(); i++ ) {
		    	codeValue = lstData.get(i).get("CODE") == null ? ( lstData.get(i).get("code") == null ? "" : (String)lstData.get(i).get("code") ) : (String)lstData.get(i).get("CODE");
	    		codeName  = lstData.get(i).get("CODE_NAME") == null ? ( lstData.get(i).get("code_name") == null ? "" : (String)lstData.get(i).get("code_name") ) : (String)lstData.get(i).get("CODE_NAME");
	    		
		    	if ( codeValue != null && !"".equals(codeValue) ) {
		    		rtnValue = rtnValue + "    <option value='" + codeValue + "'";
		    		
		    		if ( selectedValue.equals(codeValue) ) {
		    			rtnValue = rtnValue + " selected";
		    		}
		    		rtnValue = rtnValue + ">" + codeName + "</option> " + pageCR;
		    	} 	
	    	}
    	}
    	
    	// select 종료 처리
    	rtnValue = rtnValue + "</select>" + pageCR;
    	
    	return rtnValue;
    }	

	/**
	 * 코드(TB_CODE) 테이블에서 GROUP_CODE 값으로 데이타를 가져와서 input:radio 구성
	 * @param lstData			코드값 데이타
	 * @param groupCode			GROUP_CODE
	 * @param id				input:radio id 및 name
	 * @param selectedValue		input:radio 선택값
	 * @param optionValue		input:radio 간격 
	 * @param className			input:radio class 명
	 * @param styleOption		input:radio style 값
	 * @return
	 */
    public static String fnCodeRadio(List<Map<String, Object>> lstData, String id, String selectedValue, String optionValue, String className, String styleOption) {
    	String rtnValue  = "";
    	String codeValue = "";
    	String codeName  = "";

    	// 선택값 처리
    	for ( int i = 0; i < lstData.size(); i++ ) {
	    	// input:radio 시작 및 id, name 처리
	    	rtnValue = rtnValue + "<input type=\"radio\" id=\"" + id + "\" name=\"" + id + "\"";
	    	
	    	// class 처리
	    	if ( className != null && !"".equals(className) ) {
	    		rtnValue = rtnValue + " class=\"" + className + "\"";
	    	}
	    	
	    	// style 처리
	    	if ( styleOption != null && !"".equals(styleOption) ) {
	    		rtnValue = rtnValue + " style=\"" + styleOption + "\"";
	    	}

	    	codeValue = lstData.get(i).get("CODE") == null ? ( lstData.get(i).get("code") == null ? "" : (String)lstData.get(i).get("code") ) : (String)lstData.get(i).get("CODE");
    		codeName  = lstData.get(i).get("CODE_NAME") == null ? ( lstData.get(i).get("code_name") == null ? "" : (String)lstData.get(i).get("code_name") ) : (String)lstData.get(i).get("CODE_NAME");
    		
	    	rtnValue = rtnValue + " value=\"" + codeValue + "\"";
    		
    		if ( selectedValue.equals(codeValue) ) {
    			rtnValue = rtnValue + " checked";
    		}
    		
    		rtnValue = rtnValue + ">&nbsp;" + codeName;
    		
	    	// 간격 처리
	    	if ( optionValue != null && !"".equals(optionValue) ) {
	    		for ( int j = 0; j < Integer.parseInt(optionValue); j++ ) {
		    		rtnValue = rtnValue + "&nbsp;";	    			
	    		}

	    	}
	    	
	    	rtnValue = rtnValue + "\n";
    	}
    	
    	return rtnValue;
    }	
    
    /**
     * str문자열을 byte로 체크하여지정한 cutLength 길이보다 클 경우 pstr문자열을 더하여 리턴한다.
     * @param str 입력한문자열
     * @param pstr 넘칠경우 대체 문자열
     * @param cutLength 제한길이
     * @return
     */
    public static String getCutStringByte(String str, String pstr, int cutLength){
    	String str1 = "";
    	String str2 = "";
    	if(str.getBytes().length > cutLength){
    		str1 = str.substring(0, cutLength/2);
    		str2 = str.substring(cutLength/2);
    		
    		str = str1 + pstr + str2;
    		
    		System.out.println(str);
    	}
    	return str;
    }
    
    public static String getLocalServerIp()
    {
            try
            {
                for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)
                {
                    NetworkInterface intf = en.nextElement();
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)
                    {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress())
                        {
                            return inetAddress.getHostAddress().toString();
                        }
                    }
                }
            }
            catch (SocketException ex) {}
            return null;
    } 
    
    /**
     * 랜덤한 문자열을 원하는 길이만큼 반환합니다.
     * 
     * @param length 문자열 길이
     * @return 랜덤문자열
     */
    public static String getRandomString(int length)
    {
      StringBuffer buffer = new StringBuffer();
      Random random = new Random();
     
      String chars[] = 
        "1,2,3,4,5,6,7,8,9,0".split(",");
     
      for (int i=0 ; i<length ; i++)
      {
        buffer.append(chars[random.nextInt(chars.length)]);
      }
      return buffer.toString();
    }
}