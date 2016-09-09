package kr.co.raon.commons.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


/**
 * <pre>
 * FileUpLoadUtil.java
 * </pre>
 * 
 * @author RedEye
 * @version : 
 */
public class FileUpLoadUtil {
	
	private static String fileType = "doc|docx|xls|xlsx|ppt|pptx|jpg|gif|png|bmp|jpeg|txt|zip|war|iso|hwp|pdf|csv";
	
	/**
	* 1. 개요 : 파일 사이즈
	* 2. 처리내용 : 파일 사이즈계산
	* <pre>
	* @Method Name : checkFileSize
	* </pre>
	* @return
	* @throws Exception
	*/
	public static boolean checkFileSize(MultipartHttpServletRequest mrequest,  String FileInput){
    	MultiValueMap<String, MultipartFile> MVMap  = mrequest.getMultiFileMap();
		Map<String, MultipartFile> MF = MVMap.toSingleValueMap();
		MultipartFile File = MF.get(FileInput);
		long fileSize = 0;
		
		fileSize = File.getSize();
		
		if( fileSize > 10*1024*1024){
			return false;
		} else {
			return true;
		}
	}
	
	/**
	* 1. 개요 : 파일 업로더
	* 2. 처리내용 : 파일 업로드
	* 3. 입력 Data : String realPath, HttpServletRequest request
	* 4. 출력 Data : 업로드 된 파일들 List<FileInfo> 반환 
	* <pre>
	* @Method Name : uploadFiles
	* </pre>
	* @param realPath
	* @param request
	* @param fileRandomName
	* @return
	* @throws Exception
	*/
	public static List<FileInfo> uploadFiles(String realPath, HttpServletRequest request,boolean fileRandomName,boolean extensionName) throws Exception {
		
		MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest) request;
	    Iterator<String> fileNameIterator = mpRequest.getFileNames();
	    Iterator<String> fileNameIteratorTemp = mpRequest.getFileNames();
	    int count = 0;
	    
	    //저장 할수 없는 확장자 오면 에러 발생
	    while (fileNameIteratorTemp.hasNext()) {
	    	
	    	MultipartFile multiFile = mpRequest.getFile(fileNameIteratorTemp.next());
	    	//System.out.println("multiFile.getOriginalFilename() : "+multiFile.getOriginalFilename());
	    	String fileExt = multiFile.getOriginalFilename().substring(multiFile.getOriginalFilename().lastIndexOf(".")+1, multiFile.getOriginalFilename().length()).toLowerCase();
			
	    	/* JSP쪽에서 확장자 지정함
	    	if(!"".equals(StringUtil.NVL(fileExt.trim()))  && !StringUtil.inArray(fileType.split("[|]"), fileExt.trim())){
				count++;
			}
			 */
	    	
	    }
	    if(count > 0){
			throw new Exception();
		}
	    List<FileInfo> fileInfos = new ArrayList<FileInfo>(); 
	    

	    // List<MultipartFile> fileList = mpRequest.getFiles("fileData");
	    /*
	    for(int i=0;i<fileList.size();i++){
			System.out.println("11111111111111111\n");
	        MultipartFile multiFile = mpRequest.getFile(fileNameIterator.next());
	        if (multiFile.getSize() > 0) {
	        	FileInfo fileInfo;
				try {
					fileInfo = FileUploadUtil.uploadFormFile(multiFile, realPath, fileRandomName);
					fileInfos.add(fileInfo);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
	        }
		}
	    */
	    
	    while (fileNameIterator.hasNext()) {
	        MultipartFile multiFile = mpRequest.getFile(fileNameIterator.next());
	        if (multiFile.getSize() > 0) {
	        	FileInfo fileInfo = FileUpLoadUtil.uploadFormFile(multiFile, realPath,fileRandomName,extensionName);
	        	fileInfos.add(fileInfo);
	        }
	    }
	    
	    return fileInfos;
	}
	/**
	 * 1. 개요 : 파일 업로드
	 * 2. 처리내용 : 파일을 받아 저장 한다~
	 * 3. 입력 Data : MultipartFile, String(업로드 경로)
	 * 4. 출력 Data : FileInfo 파일 정보
	 * <pre>
	 * @Method Name : uploadFormFile
	 * </pre>
	 * @param MultipartFile
	 * @param String
	 * @return
	 */
	public static FileInfo uploadFormFile(MultipartFile formFile, String realPath,boolean fileRandomName,boolean extensionName) throws Exception {
		
		InputStream stream = null;
		OutputStream bos = null;

		/* Random파일명
		UUID uuid = UUID.randomUUID();
		String tempFileName = uuid.toString();
		*/
		/* 날짜(yyyyMMddHHmmss)+Random*/
		String tempFileName = randomNumber();
		//디렉토리 유무 체크 없으면 생성
		FileUpLoadUtil.uploadCreateDir(realPath);
		
		if(!realPath.substring(realPath.length()-1, realPath.length()).equals("/")){
			realPath = realPath + "/";
		}
		
		String uploadFileName = "";		
		uploadFileName = formFile.getOriginalFilename();
		
		// 랜덤파일명 만들기
		if(fileRandomName){
			uploadFileName = tempFileName;
			// 확장자 붙이는거면...~~~~~~~
			if(extensionName){
				uploadFileName = uploadFileName + "." + formFile.getOriginalFilename().substring(formFile.getOriginalFilename().lastIndexOf(".")+1, formFile.getOriginalFilename().length());
			}
		}else{
			uploadFileName = formFile.getOriginalFilename();
		}
		
		FileInfo fileInfo = new FileInfo();
		
		try {
			stream = formFile.getInputStream();
			bos = new FileOutputStream(realPath + uploadFileName);
			int bytesRead = 0;
			
			byte[] buffer = new byte[8192];
			
			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
			
			fileInfo.setFileName(formFile.getOriginalFilename());
			fileInfo.setFileSize(formFile.getSize());
			fileInfo.setContentType(formFile.getContentType());
			fileInfo.setRealFileName(uploadFileName);
			fileInfo.setUploadStatus(true);
			
		} catch (FileNotFoundException e) {
			fileInfo.setUploadStatus(false);
			fileInfo.setUplaodFailReason(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			fileInfo.setUploadStatus(false);
			fileInfo.setUplaodFailReason(e.getMessage());
			e.printStackTrace();
		} finally {
			bos.close();
			stream.close();
		}


		return fileInfo;
	}
	/**
	 * 1. 개요 : 파일 업로드 디렉토리
	 * 2. 처리내용 : 디렉토리가 없으면 생성한다~
	 * <pre>
	 * @Method Name : uploadFormFile
	 * </pre>
	 * @param MultipartFile
	 * @param String
	 * @return
	 */
	private static void uploadCreateDir(String realPath) throws Exception {
		String realPathSplit = "/";
		String[] realPathArray = realPath.split("["+realPathSplit+"]");
		String path = "";
		for (int i = 0; i < realPathArray.length; i++) {
			path += realPathArray[i]+realPathSplit;
			File saveDir = new File(path);
			if(!saveDir.exists()){
				saveDir.mkdir();
			}
		}
	}
	/**
	 * 1. 개요 : 날짜별랜덤파일명
	 * 2. 처리내용 : 날짜별랜덤파일명만든다~~~~~
	 * <pre>
	 * @Method Name : randomNumber
	 * </pre>
	 * @return
	 */
	private static String randomNumber() {
		int nRand;
		while(true)
		{
			nRand = (int)(Math.random()*10000000);
			if (nRand>1000000) break;
		}
		
		return new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()) + "" + nRand;
	}
	
}
