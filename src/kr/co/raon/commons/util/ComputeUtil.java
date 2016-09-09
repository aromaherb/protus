package kr.co.raon.commons.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class ComputeUtil {

	private static String fileSeperater = "\n";
	
	/**
	 * file name 만들어주는 함수
	 * 소문자, _ 포함해서 리턴
	 * TB_제거후 호출해야함
	 * @param name
	 * @return
	 */
	public String make_file_name(String name){
		String result_name = "";
		try{
			String[] str = new String(name).split("_");
			for(int i=0;i<str.length;i++) {
				str[i]=transCharDown(str[i]);
				if( i == 0){
					result_name = str[i];
				}else{
					result_name = result_name+ "_" + str[i];
				}
			}
		}catch(Exception e){
			System.err.println(e.getMessage());
			return name;
		}
		return result_name;
	}
	
	/**
	 * 첫글자'_'다음 첫번째 글자 대문자로 변환
	 * @param name 
	 * @return
	 */
	public String splitName(String name){
		String result_name = "";
		try{
			String[] str = new String(name).split("_");
			for(int i=0;i<str.length;i++) {
				str[i]=transCharFistUp(str[i]);
				result_name = result_name + str[i];
			}
		}catch(Exception e){
			System.err.println(e.getMessage());
			return name;
		}
		return result_name;
	}
	
	/**
     * TB_, Tb_, TS_, Ts_, CT_, Ct_삭제
     * @param
     * @return
     */
	public String subStringName(String name){
		if(name.startsWith("TB_")||name.startsWith("Tb_")){
			name = name.substring(3);
		}else if(name.startsWith("TS_")||name.startsWith("Ts_")){
			name = name.substring(3);
		}else if(name.startsWith("CT_")||name.startsWith("Ct_")){
			name = name.substring(3);
		}
		return name;
	}
	
	/**
     * 문자열변환
     * @param 대문자를 소문자로
     * @return
     */
	public String transCharDown(String name){
		return name.toLowerCase();
		/**
		String result = "";
		char[] imsi    = name.toCharArray();
		for(int i=0; i<imsi.length; i++){
			if(Character.isUpperCase(imsi[i])){
				result = result + Character.toLowerCase(imsi[i]);
			}
		}
		return result;
		*/
	}
	
	/**
     * 스키마 소문자로 변환
     * @param
     * @return
     */
	public static String transSchema(String name){
		String result = "";
		String[] names = name.split("\\.");
		char[] imsi    = names[0].toCharArray();
		if(names.length>1){
			for(int i=0; i<imsi.length; i++){
				if(Character.isUpperCase(imsi[i])){
					result = result + Character.toLowerCase(imsi[i]);
				}
			}
			result = result + names[1];
		}else{
			for(int i=0; i<imsi.length; i++){
				if(Character.isUpperCase(imsi[i])){
					result = result + Character.toLowerCase(imsi[i]);
				}
			}	
		}
		return result;
	}
	
	/**
     * 인코딩 소문자로 변환
     * @param
     * @return
     */
	public static String transEncoding(String name){
		String result = "";
		String result1 = "";
		String[] names = name.split("\\-");
		char[] imsi    = names[0].toCharArray();
		char[] imsi1    = names[1].toCharArray();
			for(int i=0; i<imsi.length; i++){
				if(Character.isUpperCase(imsi[i])){
					result = result + Character.toLowerCase(imsi[i]);
				}
			}
			for(int j=0; j<imsi1.length; j++){
				if(Character.isUpperCase(imsi[j])){
					result1 = result1 + Character.toLowerCase(imsi1[j]);
				}
			}
			result = result + "-" + result1;
		return result;
	}
	
	/**
     * 문자열변환
     * @param 첫자만 대문자로
     * @return
     */
	public static String transCharFistUp(String name){
		String result = "";
		char[] imsi    = name.toLowerCase().toCharArray();
		for(int i=0; i<imsi.length; i++){
			if ( i == 0 ) {
				result = result + String.valueOf(imsi[i]).toUpperCase();
			} else {
				result = result + Character.toLowerCase(imsi[i]);
			}
		}
		return result;
	}
	
	public static String transCharDownToFistUp(String name){
		String result = "";
		char[] imsi    = name.toCharArray();
		for(int i=0; i<imsi.length; i++){
			if(i==0){
				result = result + Character.toUpperCase(imsi[i]);
			}else{
				result = result + Character.toLowerCase(imsi[i]);
			}
		}
		return result;
	}
	
	
	public String[] splitString(String name){
		String[] result = name.split("\\_");
		return result;
	}
	
	public String splitUsingLastValue(String name){
		
		String[] splitStr = name.split("\\/");
		int len = splitStr.length;
		String result = splitStr[len-1];
		
		return result;
	}
	
	public String fieldName1(String name){
		String[] names = splitString(name);
		String fieldName1 = "";
		for(int i=0; i<names.length; i++){
			fieldName1 = fieldName1 + transCharFistUp(names[i]);
		}
		return fieldName1;
	}
	
	public String fieldName2(String name){
		String[] names = splitString(name);
		String fieldName2 = "";
		for(int i=0; i<names.length; i++){
			if(i>0){
				fieldName2 = fieldName2 + "_" + transCharDown(names[i]);
			}else{
				fieldName2 = fieldName2 + transCharDown(names[i]);
			}
		}
		return fieldName2;
	}
	
	public String fieldName3(String name){
		String[] names = splitString(name);
		String fieldName3 = "";
		for(int i=0; i<names.length; i++){
			if(i>0){
				fieldName3 = fieldName3 + transCharFistUp(names[i]);
			}else{
				fieldName3 = fieldName3 + transCharDown(names[i]);
			}
		}
		return fieldName3;
	}
	
	public String transPrefix(String columnType){
		String result = "";
		if( columnType.equals("CHAR") ||
		    columnType.equals("VARCHAR") ||
		    columnType.equals("VARCHAR2") ){
			result = "Str";
		}else if(columnType.equals("NUMBER")){
			result = "n";
		}else{
			result = "";
		}
		return result;
	}
	
	public String transType1(String columnType){
		String result = "";
		if( columnType.equals("CHAR") ||
		    columnType.equals("VARCHAR") ||
		    columnType.equals("VARCHAR2") ){
			result = "String";
		}else if(columnType.equals("NUMBER")){
			result = "int";
		}else if(columnType.equals("DATE")){
			result = "String";
		}else{
			result = "";
		}
		return result;
	}
	
	public String transType2(String columnType){
		String result = "";
		if( columnType.equals("CHAR") ||
		    columnType.equals("VARCHAR") ||
		    columnType.equals("VARCHAR2") ){
			result = "String";
		}else if(columnType.equals("NUMBER")){
			result = "Int";
		}else if(columnType.equals("DATE")){
			result = "String";
		}else{
			result = "";
		}
		return result;
	}
	
	public String transType3(String columnType){
		String result = "";
		if( columnType.equals("CHAR") ||
		    columnType.equals("VARCHAR") ||
		    columnType.equals("VARCHAR2") ){
			result = "String";
		}else if(columnType.equals("NUMBER")){
			result = "Integer";
		}else if(columnType.equals("DATE")){
			result = "DateTime";
		}else{
			result = "";
		}
		return result;
	}
	
	/**
     * 디렉토리 생성
     * @param dirPath 디렉토리 경로
     * @return
     */
    public static boolean directoryCreate(File dirPath) {
    	boolean bReturn = true; 
    	
    	// 디렉토리가 없으면 생성
    	if ( !dirPath.exists() ) {
    		bReturn = dirPath.mkdir();
    	}
    	
    	return bReturn;
    }
    
    /**
     * 디렉토리 생성
     * @param strDirPath 문자열 디렉토리 경로
     * @return
     */
    public static boolean directoryCreate(String strDirPath) {
    	return directoryCreate(new File(strDirPath));    	
    }
    
    /**
     * 원하는 위치의 파일 replace
     * @param filePath 파일 위치
     * @return
     */
    public void replaceFile(String filePath){
    	try{
        File file = new File(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
        String line = "", oldtext = "";
        while((line = reader.readLine()) != null){
            oldtext += line + "\r\n";
        }
        reader.close();
        
        String newtext = oldtext.replaceAll("&lt;", "<" ).replaceAll("&gt;", ">");
//        String newtext = oldtext.replaceAll("&lt;", "<" ).replaceAll("&gt;", ">").replaceAll("<?xml version=\"", "");        
        
        FileWriter writer = new FileWriter(filePath);
        writer.write(newtext);
        writer.close();
	    }catch (IOException ioe){
	        ioe.printStackTrace();
	    }
    }
    
    /**
     * \ => / 로 변경
     * @param myStr
     * @return
     */
    public String backlashReplace(String myStr){
        final StringBuilder result = new StringBuilder();
        final StringCharacterIterator iterator = new StringCharacterIterator(myStr);
        char character =  iterator.current();
        
        while (character != CharacterIterator.DONE ) {
        	if (character == '\\') {
        		result.append("/");
        	} else {
        		result.append(character);
        	}
          
        	character = iterator.next();
        }
        
        return result.toString();
    }    
    /**
     * 
     * @param src
     * @param padChar
     * @param padLength
     * @return
     */
    static public String lpad(String src, String padChar, int padLength) {
		String padString = "";
		String sValue    = "";
		
		int srcLength = src.length();
		
		sValue = src;
		
		if ( srcLength >= padLength ) {
			sValue = src;
		} else {
			for ( int i = 0; i < padLength - srcLength; i++ ) {
				padString += padChar;
			}
		}
		
		sValue = padString + sValue;
		
		return sValue;
	}	
	
    /**
     * 
     * @param src
     * @param padChar
     * @param padLength
     * @return
     */
    static public String rpad(String src, String padChar, int padLength) {
		String padString = "";
		String sValue    = "";
		
		int srcLength = src.length();
		
		sValue = src;
		
		if ( srcLength >= padLength ) {
			sValue = src;
		} else {
			for ( int i = 0; i < padLength - srcLength; i++ ) {
				padString += padChar;
			}
		}
		
		sValue += padString;
		
		return sValue;
	}	
    
}
