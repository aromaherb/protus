package kr.co.raon.commons.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class FileEncoding {
	private static String fileSeperater = "\n";

	/**
	 * @param args
	 */
/*	
	public static void main(String[] args) {
	 String filePath = "C:\\Users/Administrator/Desktop/egov_201210151115/bonus/Bonus.java";
	 
		// TODO Auto-generated method stub
		// UTF-8파일쓰기
//		fileWrite(filePath, "이파일은 UTF-8파일입니다.", "UTF-8");
		// UTF-8파일읽기
		fileRead(filePath, "UTF-8");
  
		// UTF-8파일을 MS949로 읽을경우
//		fileRead(filePath, "MS949");
  
		// MS949파일쓰기
//		fileWrite(filePath, "이파일은 MS949파일입니다.", "MS949");
		// MS949파일읽기
//		fileRead(filePath, "MS949");
		  
		// MS949파일을 UTF-8로 읽을경우
//		fileRead(filePath, "UTF-8");
	 
	 	// MS949파일을 EUC-KR로 읽을경우
		fileRead(filePath, "EUC-KR");

	}
*/
	/**
	 * 파일읽기
	 * @param filePath
	 */
	public static String fileRead(String filePath, String encoding) {
		BufferedReader reader = null;
		String defaultContents = "";
		String result = null; 
		try{
			File configFile = new File(filePath);
			if(configFile.exists() && configFile.isFile() && configFile.length()>0){
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(configFile),encoding));
				
                String str = "";
                while ((str = reader.readLine()) != null) {
                	if ( defaultContents.equals("") ) {
                		defaultContents += str;
                	} else {
                		defaultContents += fileSeperater + str;
                	}
                }
/*				
				char[] contents = new char[(int)configFile.length()];
				reader.read(contents);
				defaultContents = new String(contents);
				System.out.println(defaultContents);
*/                
				result = defaultContents;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(reader != null){
				try{
					reader.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return result;
	}
 
	/**
	 * 파일쓰기
	 * @param filePath
	 * @param memo
	 * @param encode
	 */
	public static void fileWrite(String filePath, String memo, String encode) {
  
		try {
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(filePath), encode);
			out.write(memo);
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
  
	}
	
    /**
     * 원하는 위치의 파일 replace
     * @param filePath 파일 위치
     * @return
     */
    public void replaceFile(String filePath) {
    	try {
    		String oldtext = fileRead(filePath, "UTF-8");    		
    		String newtext = "";
    		
    		if ( oldtext != null && "".equals(newtext) ) {
    			newtext = oldtext.replaceAll("&lt;", "<" ).replaceAll("&gt;", ">");
    		} else {
    			newtext = "";
    		}
        
    		fileWrite(filePath, newtext, "UTF-8");
	    } catch (Exception e){
	        e.printStackTrace();
	    }
    }
    
    public void replaceFile(String filePath, String encoding) {
    	try {
    		String oldtext = fileRead(filePath, "UTF-8");    		
    		String newtext = "";
    		
    		if ( oldtext != null && "".equals(newtext) ) {
    			newtext = oldtext.replaceAll("&lt;", "<" ).replaceAll("&gt;", ">");
    		} else {
    			newtext = "";
    		}
        
    		fileWrite(filePath, newtext, encoding);
	    } catch (Exception e){
	        e.printStackTrace();
	    }
    }
}
 
