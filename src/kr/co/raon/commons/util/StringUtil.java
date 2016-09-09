package kr.co.raon.commons.util;

import java.util.List;

public class StringUtil {
	
	public static boolean isString(Object element) 
	{
		if (element instanceof String)
			return true;
		else
			return false;
	}
	
	public static String null2Str(String strValue) {
		return (strValue == null ? "" : strValue);
	}
	
	public static String object2Str(Object object) {
		String strReturn = "";
		if(object != null) {
			strReturn = object.toString();
		}
		return strReturn;
	}

	
	public static String null2Obj(Object object) {
		return String.valueOf((object == null ? "" : object));
	}

	/**
	 * 1. 개요 : String null check
	 * 2. 처리내용 : String null check
	 * 3. 입력 Data : input String
	 * 4. 출력 Data : output String
	 * <pre>
	 * @Method Name : NVL
	 * </pre>
	 * @author : RedEye
	 * @param input
	 * @param output
	 * @return
	 */
	public static String NVL(String input, String output) {
		if (input == null || input.equals("") || input.toUpperCase().equals("NULL")) {
			input = output;
		}
		return input;
	}
	
	/**
	 * 1. 개요 : Object null check
	 * 2. 처리내용 : Object null check
	 * 3. 입력 Data : input object
	 * 4. 출력 Data : output String
	 * <pre>
	 * @Method Name : NVL
	 * </pre>
	 * @author : RedEye
	 * @param input
	 * @param output
	 * @return
	 */
	public static String NVL(Object input, String output) {
		String returnValue = null;
		if (input == null || input.equals("")) {
			returnValue = output;
		}
		else {
			returnValue = (String) input;
		}
		return returnValue;
	}

	/**
	 * 1. 개요 : Object null check
	 * 2. 처리내용 : Object null check
	 * 3. 입력 Data : input object
	 * 4. 출력 Data : 
	 * <pre>
	 * @Method Name : NVL
	 * </pre>
	 * @author : RedEye
	 * @param input
	 * @return
	 */
	public static String NVL(Object input) {
		return NVL(input, "");
	}
	
	/**
	 * 1. 개요 : String null check
	 * 2. 처리내용 : String null check
	 * 3. 입력 Data : input String
	 * 4. 출력 Data : 
	 * <pre>
	 * @Method Name : NVL
	 * </pre>
	 * @author : RedEye
	 * @param input
	 * @return
	 */
	public static String NVL(String input) {
		return NVL(input, "");
	}

	/**
	 * 1. 개요 : String trim null check
	 * 2. 처리내용 : String trim null check
	 * 3. 입력 Data : String input
	 * 4. 출력 Data : 
	 * <pre>
	 * @Method Name : NVLTrim
	 * </pre>
	 * @author : RedEye
	 * @param input
	 * @return
	 */
	public static String NVLTrim(String input) {
		String returnValue="";

		if (input == null || input.equals("")) {
			returnValue = "";
		}
		else {
			returnValue = (String) input.trim();
		}
		return returnValue;
	}
	
	/**
	 * 1. 개요 :  배열을 받아 구분자(,) 스트링 리턴
	 * 2. 처리내용 :  배열을 받아 구분자(,) 스트링 리턴
	 * 3. 입력 Data : String 배열
	 * 4. 출력 Data : String
	 * <pre>
	 * @Method Name : arrayToString
	 * </pre>
	 * @author : RedEye
	 * @param args
	 * @return
	 */
	public static String arrayToString(String args[]) { 
	    StringBuilder builder = new StringBuilder(); 
	    for (int i = 0; i < args.length;) { 
	        builder.append(args[i]); 
	        if (++i < args.length) { 
	            builder.append(","); 
	        } 
	    } 
	    return builder.toString(); 
	}
	
	/**
	 * 1. 개요 : 배열과 구분자를 받아 스트링 리턴
	 * 2. 처리내용 : 배열과 구분자를 받아 스트링 리턴
	 * 3. 입력 Data : String 배열, String 구분자
	 * 4. 출력 Data : String
	 * <pre>
	 * @Method Name : arrayToString
	 * </pre>
	 * @author : RedEye
	 * @param args
	 * @param separator
	 * @return
	 */
	public static String arrayToString(String args[], String separator) { 
	    StringBuilder builder = new StringBuilder(); 
	    for (int i = 0; i < args.length;) { 
	        builder.append(args[i]); 
	        if (++i < args.length) { 
	            builder.append(separator); 
	        } 
	    } 
	    return builder.toString(); 
	}
	
	public static String listToString(List<String> args, String separator) { 
		String listString = ""; 
	    for (int i = 0; i < args.size(); i++) { 
	        if (i == (args.size()-1)) {
	        	listString = listString + args.get(i);
	        }else{
	        	listString = listString + args.get(i) + separator;	
	        }
	    } 
	    return listString; 
	}
	
	/**
	 * 1. 개요 : 배열값 확인
	 * 2. 처리내용 : 배열을 확인해 배열안에 값이 존재 하면 true 아니면  false
	 * 3. 입력 Data : String[] array, String in
	 * 4. 출력 Data : boolean
	 * <pre>
	 * @Method Name : inArray
	 * </pre>
	 * @author : RedEye
	 * @param array
	 * @param in
	 * @return
	 */
	public static boolean inArray(String[] array, String in){
	
		int count = 0;
		
		for (int i = 0; i < array.length; i++) {
			if(in.equals(array[i].trim())){
				count++;
			}
		}
		
		return count > 0 ? true : false;
		
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : left trim 왼쪽 공백제거
	 * </pre>
	 * @Method Name : ltrim
	 * @author : RedEye
	 *
	 * @param s
	 * @return
	 */
	public static String ltrim(String s) {
	    return s.replaceAll("^\\s+","");
	}

	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : right trim 오른쪽 공백제거
	 * </pre>
	 * @Method Name : rtrim
	 * @author : RedEye
	 *
	 * @param s
	 * @return
	 */
	public static String rtrim(String s) {
	    return s.replaceAll("\\s+$","");
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : left rigth trim 양쪽 공백제거
	 * </pre>
	 * @Method Name : lrtrim
	 * @author : RedEye
	 *
	 * @param s
	 * @return
	 */
	public static String lrtrim(String s) {
		return s.replaceAll("^\\s+","").replaceAll("\\s+$","");
	}
}
