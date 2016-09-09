package kr.co.raon.commons.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;
 
public class DownloadView extends AbstractView {
	
 
	/**
	 * 생성자
	 */
    public void Download() {
        setContentType("application/download; utf-8");
    }
    
	/**
	 * javascript의 messagebox로 메세지를 출력.
	 * @param response		응답할 HttpServletResponse 객체.
	 * @param message		출력할 메세지.
	 */
	public static void message(HttpServletResponse response, String message) {
		response.setContentType("text/html; charset=utf-8");
		String output = "<script language='javascript'>alert('" + message + "');</script>";
		
		try {
			response.getWriter().print(output);
		} catch(IOException e) {
			//
		}
	}
	
	/**
	 * javascript의 messagebox로 메세지를 출력 후 이전 페이지로 복귀.
	 * @param response		응답할 HttpServletResponse 객체.
	 * @param message		출력할 메세지.
	 * @param back			되돌아갈 단계.
	 */
	public static void messageAndBack(HttpServletResponse response, String message, int back) {
		message(response, message);

		String output = "<script language='javascript'>history.go(" + back + ");</script>";
		
		try {
			response.getWriter().print(output);
		} catch(IOException e) {
			//
		}
	}
	
	/**
	 * 파일명 한글깨짐 방지 코딩을 위한 브라우져의 이름을 리턴
	 * @param request
	 * @return
	 */
	public static String getBrowser(HttpServletRequest request) {
        String header = request.getHeader("User-Agent");
        
        if ( header.indexOf("MSIE") > -1 ) {
            return "MSIE";
        } else if ( header.indexOf("Chrome") > -1 ) {
            return "Chrome";
        } else if ( header.indexOf("Opera") > -1 ) {
            return "Opera";
        } else if ( header.indexOf("Safari") > -1 ) {
        	return "Safari";
        } else {
        	return "Firefox";
        }        
	}
	
	@Override
    protected void renderMergedOutputModel(Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response) throws Exception{
         
        File file = (File)model.get("downloadFile");
        
        /*
        System.out.println("DownloadView --> file.exists() : " + file.exists());
        System.out.println("DownloadView --> file.getPath() : " + file.getPath());
        System.out.println("DownloadView --> file.getName() : " + file.getName());
        */
        
        if ( !file.exists() ) {
        	messageAndBack(response, "다운로드할 파일을 찾을수 없습니다.", -1);
        } else {
	        response.setContentType(getContentType());
	        response.setContentLength((int)file.length());
	         
	        String fileName = file.getName(); 
	        
	        String browser = getBrowser(request);
	        
	        if ( "Opera".equals(browser) ) {
	            response.setContentType("application/octet-stream;charset=UTF-8");
	        }
	        
	        if ( fileName == null || fileName.equals("") ) {
	        	fileName = "UnKnownFileName";
	        }

	        // 웹브라우저 구분
	        if ( browser.indexOf("MSIE") != -1 ) { 
	    		fileName = new String(fileName.getBytes("EUC-KR"), "ISO-8859-1").replaceAll(" ","%20");
	    	} else if ( browser.indexOf("Opera") != -1 ) {     	// Opera
	    		fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
	    	} else if ( browser.indexOf("Chrome") != -1 ) {		// Chrome
	    		fileName = new String(fileName.getBytes("EUC-KR"), "ISO-8859-1");
	    	} else if ( browser.indexOf("Safari") != -1 ) {		// Safari
	    		fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
	    	} else if ( browser.indexOf("Firefox") != -1 ) {		// FireFox
	    		fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
	    	} else {												// Other
	    		fileName = new String(fileName.getBytes("EUC-KR"), "ISO-8859-1");
	    	}
	         
	        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
	        response.setHeader("Content-Transfer-Encoding", "binary");
	         
	        OutputStream out = response.getOutputStream();
	        FileInputStream fis = null;
	         
	        try {
	            fis = new FileInputStream(file);
	            FileCopyUtils.copy(fis, out);
	        } catch( Exception e ) {
	            e.printStackTrace();
	        } finally {
	            if ( fis != null ) {
	                try {
	                    fis.close();
	                } catch( Exception e ) {
	                	//
	                }
	            }
	        }// try end;
	         
	        out.flush();
        } 
    }// render() end;
}