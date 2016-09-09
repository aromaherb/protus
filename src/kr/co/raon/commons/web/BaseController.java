package kr.co.raon.commons.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import kr.co.raon.commons.CommonProperty;
import kr.co.raon.commons.db.MyBatisMapper;
import kr.co.raon.commons.util.StringUtil;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;

import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
 
@SuppressWarnings({"rawtypes"})
public class BaseController {
	
	/**
	 * 로그
	 */
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	public MyBatisMapper mapper;
	
	@Resource(name="sessionContextFactory")
	private ObjectFactory sessionContextFactory;

	private int m_nCurPage 	= 1;
	private int m_nCntPerPage 	= 15;
	private int m_nCntPaging 	= 10;
	private int m_nCntTotal  	= 0;

	/**
	 * 생성자
	 */
	public BaseController() {
		mapper = MyBatisMapper.getInstance();
	
	}
	
	/**
	 * 세션 가져오기
	 * @return
	 */
	public SessionContext getSession()
	{
		SessionContext sessionContext = (SessionContext)sessionContextFactory.getObject();
		return sessionContext;
	}
	
	/**
	 * Access Mode 가져오기
	 * @param strURI
	 * @return
	 */
	public int getAccessMode(String strURI) 
	{
		int mode = 2;
		
		/*
		if( strURI.matches("..*_(list|detail).do$")) mode = Consts.ACCESS_READ;
		if( strURI.matches("..*_(list|detail).365$")) mode = Consts.ACCESS_READ;
		if( strURI.matches("..*_(form|write|process|mprocess).do$")) mode = Consts.ACCESS_WRITE;
		if( strURI.matches("..*_(form|write|process|mprocess).365$")) mode = Consts.ACCESS_WRITE;
		if( strURI.matches("..*_(download).do$")) mode = Consts.ACCESS_READ;
		if( strURI.matches("..*_(download).365$")) mode = Consts.ACCESS_READ;
		if( strURI.matches("..*_(test).do$")) mode = Consts.ACCESS_READ;
		*/		
		return mode;
	}
	
	/**
	 * 페이징 정보 가져오기
	 * @return
	 */
	public PagingInfo getPagingInfo()
	{
		PagingInfo pagingInfo = new PagingInfo();
		pagingInfo.setCurPage(m_nCurPage);
		pagingInfo.setCntPerPage(m_nCntPerPage);
		pagingInfo.setCntPaging(m_nCntPaging);
		pagingInfo.setCntTotal(m_nCntTotal);

		return pagingInfo;
	}

	/**
	 * 페이지 정보 가져오기
	 * @param pagingInfo
	 * @return
	 */
	public Map<String, Object> getPageInfo(PagingInfo pagingInfo)
	{
		Map<String,Object> pageInfo = new HashMap<String,Object>();
		pageInfo.put("page", 			pagingInfo.getCurPage());
		pageInfo.put("cnt_per_page", 	pagingInfo.getCntPerPage());
		pageInfo.put("cntTotal", 		pagingInfo.getCntTotal());
		pageInfo.put("title",	 		"동네학원");
		pageInfo.put("pageTitle", 		getSession().getPageTitle());
		pageInfo.put("pageNavi", 		getSession().getPageNavi());
		
		SessionContext sessionContext = getSession();
		sessionContext.setAccesstime(String.valueOf(System.currentTimeMillis()));
		
		return pageInfo;
	}	

	/**
	 * 페이지 정보 가져오기
	 * @param params
	 * @return
	 */
	public Map<String, Object> getPageInfo(Map<String, Object> params)
	{
		Map<String,Object> pageInfo = new HashMap<String, Object>();
		
		setCurPage(params.get("page"));
		setCntPerPage(params.get("cnt_per_page"));
		
		pageInfo.put("page", 			this.m_nCurPage);
		pageInfo.put("cnt_per_page", 	this.m_nCntPerPage);
		pageInfo.put("cur_url", 	    getSession().getReferer());
		pageInfo.put("title",	 		"동네학원");
		pageInfo.put("pageTitle", 		getSession().getPageTitle());
		pageInfo.put("pageNavi", 		getSession().getPageNavi());

		SessionContext sessionContext = getSession();
		sessionContext.setAccesstime(String.valueOf(System.currentTimeMillis()));
		
		return pageInfo;
	}		
	
	/**
	 * 페이지 정보 가져오기
	 * @param params
	 * @return
	 */
	public Map<String, Object> getPageInfo(Map<String, Object> params, HttpServletRequest request)
	{
		Map<String,Object> pageInfo = new HashMap<String, Object>();
		
		setCurPage(params.get("page"));
		setCntPerPage(params.get("cnt_per_page"));
		
		pageInfo.put("page", 			this.m_nCurPage);
		pageInfo.put("cnt_per_page", 	this.m_nCntPerPage);
		pageInfo.put("url",             request.getRequestURI());
		
		SessionContext sessionContext = getSession();
		sessionContext.setAccesstime(String.valueOf(System.currentTimeMillis()));
		
		return pageInfo;
	}	
	
	/**
	 * 페이지 번호 가져오기
	 * @param strPage
	 * @return
	 */
	public int getPageNo(String strPage)
	{
		int nPage = 0;
		
		if ( isEmpty(strPage) ) strPage = "1";
		nPage = Integer.parseInt(strPage);
		if(nPage <= 0) nPage = 1;
		
		return nPage;
	}

	/**
	 * 현재 페이지 가져오기
	 * @return
	 */
	public int getCurPage() {
		return this.m_nCurPage;
	}	
	
	/**
	 * 현재 페이지 셋팅
	 * @param nValue
	 */
	public void setCurPage(Object nValue) {
		if ( nValue == null || isEmpty(String.valueOf(nValue)) ) {
			this.m_nCurPage = 1;	
		} else {
			this.m_nCurPage = Integer.parseInt(String.valueOf(nValue));
		}
		
		if ( this.m_nCurPage <= 0 ) this.m_nCurPage = 1; 
	}

	/**
	 * 페이지 마다 보여줄 갯수 가져오기
	 * @return
	 */
	public int getCntPerPage() {
		return this.m_nCntPerPage;
	}	
	
	/**
	 * 페이지 마다 보여줄 갯수 셋팅
	 * @param nValue
	 */
	public void setCntPerPage(Object nValue) {
		if ( !( nValue == null || isEmpty(String.valueOf(nValue)) ) ) {
			this.m_nCntPerPage = Integer.parseInt(String.valueOf(nValue));
		}
		
		if ( this.m_nCntPerPage <= 0 ) this.m_nCntPerPage = 15;
	}

	
	public int getCntPaging() {
		return this.m_nCntPaging;
	}
	
	public void setCntPaging(Object nValue) {
		if ( !( nValue == null || isEmpty(String.valueOf(nValue)) ) ) {
			this.m_nCntPaging = Integer.parseInt(String.valueOf(nValue));
		}
		
		if ( this.m_nCntPaging <= 0 ) this.m_nCntPaging = 10;
	}

	public int getCntTotal() {
		return this.m_nCntTotal;
	}	

	public void setCntTotal(Object nValue) {
		if ( !( nValue == null || isEmpty(String.valueOf(nValue)) ) ) {
			this.m_nCntTotal = Integer.parseInt(String.valueOf(nValue));
		}
		
		if ( this.m_nCntTotal <= 0 ) this.m_nCntTotal = 0;
	}
	

    /**
     * 문자열이 공백인지 체크
     * @param strData
     * @return
     */
	public boolean isEmpty(String strData) {
		boolean flag = false;
		
		if ( strData != null && !"".equals(strData) && !"null".equals(strData) ) {
			flag = false;
		} else {
			flag = true;
		}
		
		return flag;
	}
	

	/**
	 * 엑셀 저장후 다운로드
	 * @param excelData
	 * @param seq_div
	 * @param seq
	 * @param templeteFilename
	 * @param saveFilename
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public Map<String, Object> excel_download(HttpServletRequest request, ExcelData excelData, String saveFilename) throws Exception{
		// 결과 상태값 셋팅
		Map<String, Object> result = new HashMap<String, Object>();


		Date today = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
		String timestamp = formatter.format(today);
		
		CommonProperty cf = new CommonProperty();
		String eclipse_path = StringUtil.null2Str(cf.getProp("eclipse_path"));
		
  		//String realpath = request.getSession().getServletContext().getRealPath("/");
  		//realpath = realpath+"saveTempDir\\";     		
  		//params.put("saveDir", realpath);

		String outFileName = eclipse_path + "/excel_download/" + saveFilename + "_" + timestamp + ".xls";
		String downloadFileName = saveFilename + "_" + timestamp + ".xls";

		
		try {
			// 1. 엑셀에 내용 저장
			Workbook wb = new HSSFWorkbook();
			Sheet sheet = wb.createSheet(String.valueOf(excelData.getSheetNum()));
			
		    String[]   	arrHeadData 		= excelData.getIsHead()  ? (String[])excelData.getHeadData()    : null;
		    String[][] 	arrTitleData 		= excelData.getIsTitle() ? (String[][])excelData.getTitleData() : null;
		    double[][]	arrTableData 		= excelData.getIsTable() ? (double[][])excelData.getTableData() : null;
		    String[][] 	arrTableStringData 	= excelData.getIsTableString() ? (String[][])excelData.getTableStringData() : null;
		    String[]  	arrDataFormat    	= excelData.getIsDataFormat() ? (String[])excelData.getDataFormat() : null;
		    
		    Row  row  = null;
		    Cell cell = null;
	
		    // Head 데이타가 있는 경우
		    if ( excelData.getIsHead() ) {
		    	row = sheet.getRow(excelData.getStartHeadRow());
		    	
			    if (row == null)
			    	row = sheet.createRow(excelData.getStartHeadRow());				    	
		    	
		    	for ( int j = 0; j < arrHeadData.length; j++ ) {
				    cell = row.getCell(excelData.getStartHeadCell() + j);
				    if (cell == null)
				        cell = row.createCell(excelData.getStartHeadCell() + j);
				
				    cell.setCellValue(arrHeadData[j]);
		    	}
		    }
		    
		    // 타이틀 데이타가 있는 경우
		    if ( excelData.getIsTitle() ) {			    
			    for ( int j = 0; j < arrTitleData.length; j++ ) {
			    	row = sheet.getRow(excelData.getStartTitleRow() + j);
				    if (row == null)
				    	row = sheet.createRow(excelData.getStartTitleRow() + j);				    	
			    	
			    	for ( int k = 0; k < arrTitleData[j].length; k++ ) {
					    cell = row.getCell(excelData.getStartTitleCell() + k);
					    if (cell == null)
					        cell = row.createCell(excelData.getStartTitleCell() + k);
					
					    cell.setCellValue(arrTitleData[j][k]);
			    	}
			    }
		    }			    
		    
		    // 더블형 데이타가 있는 경우
		    if ( excelData.getIsTable() ) {
			    for ( int j = 0; j < arrTableData.length; j++ ) {
			    	row = sheet.getRow(excelData.getStartDataRow() + j);
				    if (row == null)
				    	row = sheet.createRow(excelData.getStartDataRow() + j);				    	
			    	
			    	for ( int k = 0; k < arrTableData[j].length; k++ ) {
					    cell = row.getCell(excelData.getStartDataCell() + k);
					    if (cell == null)
					        cell = row.createCell(excelData.getStartDataCell() + k);
	
					    if ( arrTableData[j][k] != -999 ) {
					    	cell.setCellValue(arrTableData[j][k]);
					    }
			    	}
			    }
		    }
	
		    // 문자형 데이타가 있는 경우
		    if ( excelData.getIsTableString() ) {			    
			    for ( int j = 0; j < arrTableStringData.length; j++ ) {
			    	row = sheet.getRow(excelData.getStartDataRow() + j);
				    if (row == null)
				    	row = sheet.createRow(excelData.getStartDataRow() + j);
	
			    	for ( int k = 0; k < arrTableStringData[j].length; k++ ) {
					    cell = row.getCell(excelData.getStartDataCell() + k);
					    if (cell == null) {
					        cell = row.createCell(excelData.getStartDataCell() + k);
					    }

					    // 숫자와 문자가 혼재할 경우 처리
					    if ( excelData.getIsDataFormat() ) {
					    	if ( "D".equals(raonString(arrDataFormat[k])) ) {
							    if ( isEmpty( raonString(arrTableStringData[j][k]) ) ) {
							    	cell.setCellValue("");
							    } else {
							    	try {
							    		cell.setCellValue( Double.parseDouble(arrTableStringData[j][k]) );
							    	} catch( Exception e ) {
							    		cell.setCellValue(arrTableStringData[j][k]);	
							    	}
							    }
					    	} else {
					    		if ( isEmpty( raonString(arrTableStringData[j][k]) ) ) {
							    	cell.setCellValue("");
							    } else {
							    	cell.setCellValue(arrTableStringData[j][k]);
							    }					    		
					    	}
					    } else {
						    if ( isEmpty( raonString(arrTableStringData[j][k]) ) ) {
						    	cell.setCellValue("");
						    } else {
							    // 숫자인지 체크 하여 숫자이면 숫자 필드로 처리
							    if ( isStringDouble(arrTableStringData[j][k]) ) {
							    	cell.setCellValue( Double.parseDouble(arrTableStringData[j][k]) );
							    } else {
							    	cell.setCellValue(arrTableStringData[j][k]);
							    }
						    }
					    }
			    	}
			    }
		    }
			
		    // 파일 저장 및 객제 null 처리
		    FileOutputStream fileOut = new FileOutputStream(outFileName);
		    wb.write(fileOut);
		    fileOut.close();
		    
		    wb = null;
			
			result.put("result_cd", "200");
			result.put("result_msg", "Success");
			result.put("download_file", downloadFileName);
		} catch ( FileNotFoundException fe ) {
			result.put("result_cd", "500");
			result.put("result_msg", "Fail");
		} catch ( IOException ie ) {
			result.put("result_cd", "500");
			result.put("result_msg", "Fail");
		} finally {
			//
		}
		
		return result;
	}

	/**
	 * 첨부파일 저장후 파일크기, 파일명 return
	 * @param request
	 * @param params
	 * @return
	 */
    public List<Map<String, Object>> writeFile(HttpServletRequest request, Map<String, Object> params) {
		String saveDir = (String)params.get("saveDir");

    	List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

    	/* 파일 객체 */
        final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        
        /* 파일 분해 */        
        final MultiValueMap<String, MultipartFile> map = multiRequest.getMultiFileMap();

		if ( map != null ) {
			Iterator iter = map.keySet().iterator();
			while(iter.hasNext()) {
				String str = (String) iter.next();
				List<MultipartFile> fileList =  map.get(str);
				
				for(MultipartFile mpf : fileList) {
		        	logger.debug("명칭 : " + mpf.getName() + ", 파일명 : " + mpf.getOriginalFilename() + " 파일크기 : " + mpf.getSize());
		        	
		        	// 파일크기가 0보다 큰 경우만 첨부파일 저장
		        	if ( mpf.getSize() > 0 ) {
			        	BufferedInputStream bis = null;
			            OutputStream        fos = null;

			            try {
				            HashMap<String, Object> hmData = new HashMap<String, Object>();

				            hmData.put("file_nm", mpf.getName());
				            hmData.put("file_size", mpf.getSize());
				            hmData.put("file_org_nm", mpf.getOriginalFilename());
				            
				            result.add( hmData );
				            
				        	bis = new BufferedInputStream(mpf.getInputStream());
							fos = new FileOutputStream(saveDir + "/" + mpf.getOriginalFilename());
							 
				            byte[] buffer = new byte[8192];
				            int read;
				            while ((read = bis.read(buffer)) > 0) {
				            	fos.write(buffer, 0, read);
				            }						
				        } catch (IOException ioe) {
				            logger.error(ioe.toString());
				        } finally {
				            IOUtils.closeQuietly(fos);
				        }
		        	}
				}
			}
		}
		
		return result;
    }
    
	/**
	 * 첨부파일 저장후 파일크기, 파일명 return
	 * @param request
	 * @param params
	 * @return
	 */
    public List<Map<String, Object>> writeTempFile(HttpServletRequest request, Map<String, Object> params) {
		String saveDir = (String)params.get("saveDir");
		String inputFilePath = (String)params.get("org_file_path");

    	List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

    	/* 파일 객체 */
        final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        
        /* 파일 분해 */        
        final MultiValueMap<String, MultipartFile> map = multiRequest.getMultiFileMap();

		if ( map != null ) {
			Iterator iter = map.keySet().iterator();
			while(iter.hasNext()) {
				String str = (String) iter.next();
				List<MultipartFile> fileList =  map.get(str);
				
				for(MultipartFile mpf : fileList) {
		        	logger.debug("명칭 : " + mpf.getName() + ", 파일명 : " + mpf.getOriginalFilename() + " 파일크기 : " + mpf.getSize());
		        	
		        	// 파일크기가 0보다 큰 경우만 첨부파일 저장
		        	if(mpf.getName().equals("imagefile")){
			        	if ( mpf.getSize() > 0 ) {
				        	BufferedInputStream bis = null;
				            FileOutputStream    fos = null;
	
				            try {
					            HashMap<String, Object> hmData = new HashMap<String, Object>();
	
					            long unixTime = System.currentTimeMillis()/ 1000L;
					 
					            hmData.put("file_nm", mpf.getName());
					            hmData.put("file_size", mpf.getSize());
					            hmData.put("file_org_nm", mpf.getOriginalFilename());
					            hmData.put("file_timestamp", unixTime);
					            
					            String filearr[] = hmData.get("file_org_nm").toString().split("\\.");
					            String fileextension = "";
					            if(filearr.length>1) fileextension = filearr[filearr.length-1];  
					            
					            result.add( hmData );
					            
					        	bis = new BufferedInputStream(mpf.getInputStream());
								fos = new FileOutputStream(saveDir + "/" + unixTime+"."+fileextension);
								hmData.put("file_save_nm", unixTime+"."+fileextension);
					            byte[] buffer = new byte[8192];
					            int read;
					            while ((read = bis.read(buffer)) > 0) {
					            	fos.write(buffer, 0, read);
					            }			
					            
					        } catch (IOException ioe) {
					            logger.error(ioe.toString());
					        } finally {
					            IOUtils.closeQuietly(fos);
					        }
			        	}
		        	}
				}
			}
		}
		
		return result;
    } 
    
	/**
	 * 첨부파일 저장후 파일크기, 파일명 return
	 * @param request
	 * @param params
	 * @return
	 */
    public List<Map<String, Object>> writeTempImage(HttpServletRequest request, Map<String, Object> params) {
		String saveDir = (String)params.get("saveDir");

    	List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

    	/* 파일 객체 */
        final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        
        /* 파일 분해 */        
        final MultiValueMap<String, MultipartFile> map = multiRequest.getMultiFileMap();

		if ( map != null ) {
			Iterator iter = map.keySet().iterator();
			while(iter.hasNext()) {
				String str = (String) iter.next();
				List<MultipartFile> fileList =  map.get(str);
				
				for(MultipartFile mpf : fileList) {
		        	logger.debug("명칭 : " + mpf.getName() + ", 파일명 : " + mpf.getOriginalFilename() + " 파일크기 : " + mpf.getSize());
		        	
		        	// 파일크기가 0보다 큰 경우만 첨부파일 저장
		        	if(mpf.getName().equals("imgfile")){
			        	if ( mpf.getSize() > 0 ) {
				        	BufferedInputStream bis = null;
				            OutputStream        fos = null;
	
				            try {
					            HashMap<String, Object> hmData = new HashMap<String, Object>();
	
					            long unixTime = System.currentTimeMillis()/ 1000L;
					 
					            hmData.put("file_nm", mpf.getName());
					            hmData.put("file_size", mpf.getSize());
					            hmData.put("file_org_nm", mpf.getOriginalFilename());
					            hmData.put("file_timestamp", unixTime);
					            
					            String filearr[] = hmData.get("file_org_nm").toString().split("\\.");
					            String fileextension = "";
					            if(filearr.length>1) fileextension = filearr[filearr.length-1];  
					            
					            result.add( hmData );
					            
					        	bis = new BufferedInputStream(mpf.getInputStream());
								fos = new FileOutputStream(saveDir + "/" + unixTime+"."+fileextension);
								hmData.put("file_save_nm", unixTime+"."+fileextension);
					            byte[] buffer = new byte[8192];
					            int read;
					            while ((read = bis.read(buffer)) > 0) {
					            	fos.write(buffer, 0, read);
					            }			
					            
					        } catch (IOException ioe) {
					            logger.error(ioe.toString());
					        } finally {
					            IOUtils.closeQuietly(fos);
					        }
			        	}
					}
				}
			}
		}
		
		return result;
    } 
    
    public List<Map<String, Object>> writeBrdImage(HttpServletRequest request, Map<String, Object> params) {
		String saveDir = (String)params.get("saveDir");

    	List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

    	/* 파일 객체 */
        final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        
        /* 파일 분해 */        
        final MultiValueMap<String, MultipartFile> map = multiRequest.getMultiFileMap();

		if ( map != null ) {
			Iterator iter = map.keySet().iterator();
			while(iter.hasNext()) {
				String str = (String) iter.next();
				List<MultipartFile> fileList =  map.get(str);
				
				for(MultipartFile mpf : fileList) {
		        	logger.debug("명칭 : " + mpf.getName() + ", 파일명 : " + mpf.getOriginalFilename() + " 파일크기 : " + mpf.getSize());
		        	
		        	// 파일크기가 0보다 큰 경우만 첨부파일 저장
		        	
		        	if ( mpf.getSize() > 0 ) {
			        	BufferedInputStream bis = null;
			            OutputStream        fos = null;

			            try {
				            HashMap<String, Object> hmData = new HashMap<String, Object>();

				            long unixTime = System.currentTimeMillis()/ 1000L;
				 
				            hmData.put("file_nm", mpf.getName());
				            hmData.put("file_size", mpf.getSize());
				            hmData.put("file_org_nm", mpf.getOriginalFilename());
				            hmData.put("file_timestamp", unixTime);
				            
				            String filearr[] = hmData.get("file_org_nm").toString().split("\\.");
				            String fileextension = "";
				            if(filearr.length>1) fileextension = filearr[filearr.length-1];  
				            
				            result.add( hmData );
				            
				        	bis = new BufferedInputStream(mpf.getInputStream());
							fos = new FileOutputStream(saveDir + "/" + unixTime+"."+fileextension);
							hmData.put("file_save_nm", unixTime+"."+fileextension);
				            byte[] buffer = new byte[8192];
				            int read;
				            while ((read = bis.read(buffer)) > 0) {
				            	fos.write(buffer, 0, read);
				            }			
				            
				        } catch (IOException ioe) {
				            logger.error(ioe.toString());
				        } finally {
				            IOUtils.closeQuietly(fos);
				        }
		        	}
					
				}
			}
		}
		
		return result;
    }     
    

	/**
	 * 첨부파일 삭제, 성공여부 boolean 리턴
	 * @param params (saveDir, delete_file_name)
	 * @return true 성공, false 실패
	 */
    public boolean deleteFile(Map<String, Object> params) {
		String saveDir = (String)params.get("saveDir");
		
		boolean delete_done;
		String delete_file_name = (String)params.get("delete_file_name");;

		File deleteFile  = new File ( saveDir+delete_file_name );
		if ( deleteFile.exists() ) {
			delete_done = deleteFile.delete();
		}else{
			delete_done = false;
		}
		
		return delete_done;
    }
    
    /**
	 * 탭메뉴 정보 return
	 * @param request
	 * @param params
	 * @return
	 */
    public List<Map<String, Object>> tabMenuList(Map<String, Object> params) {
    	
    	List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
    	// MyBatisMapperA mapper = new MyBatisMapperA();
    	
    	mapper.setSqlMapper("Menu");     // 매핑 선언
		
		mapper.setSqlId("selectMenuTab");
		List<Map<String, Object>> entities_tab = mapper.list(params);
		
		int len = entities_tab.size();
		
		for(int i=0; i<len; i++){
			
			Map<String, Object> entity_map = new HashMap<String, Object>();
			
			if(entities_tab.get(i).get("is_tab_yn").equals("Y")){
				
				entity_map.put("menu_id", entities_tab.get(i).get("menu_id"));
				entity_map.put("parent_menu_id", entities_tab.get(i).get("parent_menu_id"));
				entity_map.put("menu_name", entities_tab.get(i).get("menu_name"));
				entity_map.put("menu_orderby", entities_tab.get(i).get("menu_orderby"));
				entity_map.put("menu_link", entities_tab.get(i).get("menu_link"));
				entity_map.put("board_link", entities_tab.get(i).get("board_link"));
				entity_map.put("combine_board_id", entities_tab.get(i).get("combine_board_id"));
				entity_map.put("gnb_link", entities_tab.get(i).get("gnb_link"));
				entity_map.put("is_tab_yn", entities_tab.get(i).get("is_tab_yn"));
				
				result.add(entity_map);
			}
		}
		
		return result;
    }

//// Null 등 문자열 처리 함수 모음 //////
/**
 * null, "null", 공백을 빈문자열로 처리
 * @param obj
 * @return
 */
public String raonString(Object obj) {
	String strData = String.valueOf(obj);
	
	if ( isEmpty(strData) ) strData = "";
	
	return strData;
}

	/**
	 * null, "null", 공백을 기본값으로 처리
	 * @param obj
	 * @param obj
	 * @return
	 */
	public String raonString(Object obj, String default_value) {
		String strData = String.valueOf(obj);
		
		if ( isEmpty(strData) ) strData = default_value;
		
		return strData;
	}	
	
	/**
	 * null, "null", 공백을 빈문자열로 처리
	 * @param obj
	 * @return
	 */
	public String raonString(String strData) {
		if ( isEmpty(strData) ) strData = "";
		
		return strData;
	}	
	
	/**
	 * null, "null", 공백을 빈문자열로 처리
	 * @param obj
	 * @return
	 */
	public int raonInt(Object obj) {
		String strData = String.valueOf(obj);
		int returnValue = 0;
		
		if ( isEmpty(strData) ) {
			returnValue = 0;
		} else {
			try {
				returnValue = Integer.parseInt(strData);
			} catch( Exception e ) {
				returnValue = 0;
			}
		}
		
		return returnValue;
	}

	/**
	 * null, "null", 공백을 빈문자열로 처리
	 * @param obj
	 * @return
	 */
	public int raonInt(Object obj, int default_value) {
		String strData = String.valueOf(obj);
		int returnValue = 0;
		
		if ( isEmpty(strData) ) {
			returnValue = default_value;
		} else {
			try {
				returnValue = Integer.parseInt(strData);
			} catch( Exception e ) {
				returnValue = default_value;
			}
		}
		
		return returnValue;
	}	
	
	/**
	 * null, "null", 공백을 빈문자열로 처리
	 * @param obj
	 * @return
	 */
	public float raonFloat(Object obj) {
		String strData = String.valueOf(obj);
		float returnValue = 0F;
		
		if ( isEmpty(strData) ) {
			returnValue = 0F;
		} else {
			try {
				returnValue = Float.parseFloat(strData);
			} catch( Exception e ) {
				returnValue = 0F;
			}
		}
		
		return returnValue;
	}
	
    /**
     * 숫자 체크
     * @param str 문자열
     * @return 
     */
    public boolean isStringDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
	 * 테이블에서 일련번호 가져오기
	 * @param table			데이블명
	 * @param matchField	매치필드명
	 * @param matchValue	매치필드값
	 * @param destField		목적필드
	 * @return
	 */
	public String getTableCode(String table , String field , String prefix, int length){
		String code = prefix;
		
		// MyBatisMapperA mapper = new MyBatisMapperA();
		
		try	{
			
			// 공통 일련번호 생성
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("field", field);
			params.put("table", table);
			mapper.setSqlMapper("CleanCenter");			
			mapper.setSqlId("selectDataForSequenceWhere");
			Map<String, Object> entity = mapper.detail(params);
			
			System.out.println("entity : "+entity);			
			
			int seqnum = 0;
			if ( entity != null && !entity.isEmpty() && entity.size() > 0 ) {
				code   = String.valueOf(entity.get("MAX_CD"));
				System.out.println("code :"+code);
				if (code.length() <= prefix.length()) {
					seqnum = 0;	
				} else {
					seqnum = Integer.parseInt(code.substring(prefix.length()));
				}
			} else {
				seqnum = 0;
			}

			code = prefix + format(seqnum + 1, length);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			
		}
		
		return code;
	}
	
	////format //////
	private static final char[] m_sharedArr = new char[128];
	
    private static String format0(String s, int size) {
        int len = s.length();
        int absSize = (size > 0) ? size : -size;
        if (len < absSize) {
            String sp = null;
            synchronized (m_sharedArr) {
                int l = absSize - len;
                for (int i=0; i < l; i++)
                    m_sharedArr[i] = '0';
                sp = new String(m_sharedArr, 0, l);
            }
            if (size > 0)
                s = sp + s;
            else
                s = s + sp;
        }
        return s;
    }
	
    public static String format(long n, int size) {
        return format0(String.valueOf(n), size);
    }
    
}