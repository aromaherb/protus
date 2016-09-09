package kr.co.raon.commons.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Arrays;
import java.util.List;

public class FileControl {

    private static final int COMPRESSION_LEVEL = 8;

    private static final int BUFFER_SIZE = 1024 * 2;

    /**
     * 파일의 존재여부를 확인하는 메소드
     * @param isLivefile
     * @return
     */
    public static Boolean fileIsLive(String isLivefile) {
    	File f1 = new File(isLivefile);
     
    	if ( f1.exists() ) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    /**
     * 파일을 생성하는 메소드
     * @param makeFileName
     */
    public static void fileMake(String makeFileName) {
    	File f1 = new File(makeFileName);

    	try {
    		f1.createNewFile();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * 파일을 삭제하는 메소드
     * @param deleteFileName
     */
    public static void fileDelete(String deleteFileName) {
    	// 파일이 존재할 경우 삭제
    	if ( fileIsLive(deleteFileName) ) {
	    	File f1 = new File(deleteFileName);
	    	f1.delete();
    	}
    }
    
    /**
     * 파일을 복사하는 메소드
     * @param inFileName  원본 파일
     * @param outFileName 복사본 파일
     */
    public static void fileCopy(String inFileName, String outFileName) {
    	try {
    		// 파일이 존재하는 경우 복사
    		if ( inFileName != null && !"".equals(inFileName) && fileIsLive( inFileName ) ) {
	    		FileInputStream fis  = new FileInputStream(inFileName);
	    		FileOutputStream fos = new FileOutputStream(outFileName);
	      
	    		int data = 0;
	    		
	    		while((data=fis.read())!=-1) {
	    			fos.write(data);
	    		}
	
	    		fis.close();
	    		fos.close();
    		}      
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * 파일 이동 메소드
     * @param inFileName  원본 파일
     * @param outFileName 복사본 파일
     */
    public static void fileTrans(String inFileName, String outFileName) {
    	try {
    		// 파일을 다른 폴더로 이동
    		File file = new File(inFileName);
    		File file2 = new File(outFileName);
    		if(file.exists()){
    			file.renameTo(file2);
    		}
    	} finally {
    		//
    	}
    }
    
    /**
     * 파일 복사,이동 후 원본파일 삭제 메소드
     * @param inFileName  원본 파일
     * @param outFileName 복사본 파일
     */
    public static void fileMove(String inFileName, String outFileName) {
    	try {
    		// 파일복사
    		fileCopy(inFileName, outFileName);
      
    		// 복사한 파일 다른 폴더로 이동
    		fileTrans(inFileName, outFileName);
    		
    	} finally {
    		//
    	}
    }
    
    /**
     * 디렉토리를 복사하는 메소드(하위폴더까지 복사)
     * @param sourceLocation  원본 폴더
     * @param targetLocation 복사본 폴더
     */
    public static void copyDirectory(File sourceLocation, File targetLocation) throws IOException {
		  
		if(sourceLocation.isDirectory()){
			if(!targetLocation.isDirectory()){
				targetLocation.mkdir();
			}
			String[] children  = sourceLocation.list();
			for(int i=0;i<children.length;i++){
				copyDirectory(new File(sourceLocation, children[i]),new File(targetLocation, children[i]));
			}
		}else{
			InputStream in  = new FileInputStream(sourceLocation);
			OutputStream out = new FileOutputStream(targetLocation);
		  
			byte[] buf   = new byte[1024];
			int len    = 0;
			while((len = in.read(buf)) > 0){
			out.write(buf, 0, len);
			}
			in.close();
			out.close();
		}
	}
    
    /**
     * 디렉토리 통채 삭제
     * @param path
     * @return
     */
    public static boolean directoryDelete(File path) {
    	if ( !path.exists() ) {             
    		return false;         
    	}                   
    	
    	File[] files = path.listFiles();         
    	for (File file : files) {             
    		if (file.isDirectory()) {                 
    			directoryDelete(file);             
    		} else {                 
    			file.delete();             
    		}         
    	}                   
    	
    	return path.delete();   
    }     

    /**
     * 디렉토리 통채 삭제
     * @param strDirPath 문자열 디렉토리 경로
     * @return
     */
    public static boolean directoryDelete(String strDirPath) {
    	return directoryDelete(new File(strDirPath));
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
    		bReturn = dirPath.mkdirs();
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
     * 디렉토리의 파일 리스트를 읽는 메소드
     * @param dirPath
     * @return
     */
    public static List<File> getDirFileList(String dirPath)
    {
    	// 디렉토리 파일 리스트
    	List <File> dirFileList = null;
     
    	// 파일 목록을 요청한 디렉토리를 가지고 파일 객체를 생성함
    	File dir = new File(dirPath);
     
    	// 디렉토리가 존재한다면
    	if (dir.exists()) {
    		// 파일 목록을 구함
    		File[] files = dir.listFiles();
      
    		// 파일 배열을 파일 리스트로 변화함 
    		dirFileList = Arrays.asList(files);
    	}
     
    	return dirFileList;
    }    
}