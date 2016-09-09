/*************************************************************************
 * 클 래 스 명 : MyBatisHandler
 * 작   업  자 : 이진영
 * 작 업 일 자 : 2012.09.18
 * 설       명 : MyBatis 용 handler
 * ---------------------------- 이력사항 --------------------------------
 * 순번  작성자   작성일자    변경사항
 * ---- --------  ----------- -------------------------    --------
 *    1 이진영    2012.09.18  최초작성
 **************************************************************************/

/**
* 기능 : MyBatis SqlMap 관련 모듈 <br>
*@author    이진영
*@version   MyBatisHandler 1.0.0
*/
package kr.co.raon.commons.db;

import java.io.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyBatisMapper {
    /**
     * 싱글톤 패턴을 이용하기 위한 자신 Instance
     */
    private volatile static MyBatisMapper instance;

    /**
     * SqlMap 용 SqlSessionFactory
     */
    private static SqlSessionFactory sqlSessionFactory;

    /**
     * SqlMap 용 SqlSession
     */    
    private SqlSession sqlSession;

    /**
     * SqlMap 용 SqlMapper 설정 변수
     */    
    private String sqlMapper;

    /**
     * SqlMap 용 sqlId 설정 변수
     */    
    private String sqlId;    
    
    /**
     * SqlMap 용 txSqlSession
     */    
    private SqlSession txSqlSession;    

    /**
     * SqlMap 용 txSqlMapper 설정 변수
     */    
    private String txSqlMapper;

    /**
     * SqlMap 용 txSqlId 설정 변수
     */    
    private String txSqlId;
    
    
    public void setSqlSession( SqlSession _sqlSession ) { this.sqlSession = _sqlSession; }
	public SqlSession getSqlSession() { 	return sqlSession;	}
    public void setSqlMapper( String _sqlMapper ) { this.sqlMapper = _sqlMapper; }
    public String getSqlMapper() { return this.sqlMapper; }    
    public void setSqlId( String _sqlId ) { this.sqlId = _sqlId; }
    public String getSqlId() { return this.sqlId; }   
    
    public void setTxSqlSession( SqlSession _sqlSession ) { this.txSqlSession = _sqlSession; }
    public SqlSession getTxSqlSession() { 	return this.txSqlSession;	}
    public void setTxSqlMapper( String _sqlMapper ) { this.txSqlMapper = _sqlMapper; }
    public String getTxSqlMapper() { return this.txSqlMapper; }    
    public void setTxSqlId( String _sqlId ) { this.txSqlId = _sqlId; }
    public String getTxSqlId() { return this.txSqlId; }    

    /**
     * 로그 출력
     */
    private static final Logger logger = LoggerFactory.getLogger(MyBatisMapper.class);
    
    /**
     * 생성자
     */
    private MyBatisMapper() {
    	// Xml 데이타 로드 및  SqlSessionFactory 생성
		try {
			String resource = "SqlMapConfig.xml";
			Reader reader = Resources.getResourceAsReader(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
			reader.close();
			logger.debug("데이타 로딩");
		} catch (Exception e) {
			logger.error("SqlMap 정의 XML 메모리 Load 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());
		}
    }

    /** 
     *  인스턴스 
     */
    public static MyBatisMapper getInstance() {
		if (instance == null) {
			synchronized(MyBatisMapper.class) {
				if ( instance == null ) {
					instance = new MyBatisMapper();
				}
			}
		}
		
		return instance;
    }

    /**
     * SqlMap 정의 XML 메모리 Load
     * @return
     */
	public static void getResources() {
		try {
			String resource = "SqlMapConfig.xml";
			Reader reader = Resources.getResourceAsReader(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
			reader.close();
			logger.debug("데이타 로딩");
		} catch (Exception e) {
			logger.error("SqlMap 정의 XML 메모리 Load 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());
		}
	}    

    /**
     * SqlSession 셋팅
     */
	public void open() {
		try {
			sqlSession = sqlSessionFactory.openSession();
		} catch (Exception e) {
			logger.error("SqlSession Open 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());
		}
	}    
	
	/**
	 * SqlSession 닫기
	 */
	public void close() {
		try {
			if ( sqlSession != null ) {
				sqlSession.close();
			}
		} catch (Exception e) {
			logger.error("SqlSession Close 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());
		}
	}

    /**
     * SqlSession 셋팅
     */
	public void txOpen() {
		try {
			txSqlSession = sqlSessionFactory.openSession();
		} catch (Exception e) {
			logger.error("TxSqlSession Open 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());
		}
	}    
	
	/**
	 * SqlSession 닫기
	 */
	public void txClose() {
		try {
			if ( txSqlSession != null ) {
				txSqlSession.close();
			}
		} catch (Exception e) {
			logger.error("TxSqlSession Close 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());
		}
	}	
	
	/**
	 * 초기화
	 * @throws IOException
	 */
    public static void reset() throws IOException {
    	instance = new MyBatisMapper();
    }
    
    /**
     * 목록 
     * @return
     */
	public List<Map<String, Object>> list(Map<String, Object> params) 
	{
		List<Map<String, Object>> outputs = null;
		
		if ( !( this.sqlId != null && !"".equals(this.sqlId) ) ) {
			this.sqlId = "selectList";
		}
		
		open();
		try {
			outputs = selectList(this.sqlMapper + "." + this.sqlId, params);
		} catch(Exception e) { 
			logger.error(this.sqlMapper + " > " + this.sqlId + " > 목록 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());
			
			outputs = null;
		} finally {
			close();
		}
		
		return outputs;
	}

    /**
     * Index를 이용한 목록 
     * @return
     */
	public List<Map<String, Object>> listIdx(Map<String, Object> params) 
	{
		List<Map<String, Object>> outputs = null;
		
		if ( !( this.sqlId != null && !"".equals(this.sqlId) ) ) {
			this.sqlId = "selectIndexList";
		}
		
		open();
		try {
			outputs = selectList(this.sqlMapper + "." + this.sqlId, params);
		} catch(Exception e) { 
			logger.error(this.sqlMapper + " > " + this.sqlId + " > 목록 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());
			
			outputs = null;
		} finally {
			close();
		}
		
		return outputs;
	}	
	
    /**
     * 목록 개수 
     * @return
     */
	public int listCnt(Map<String, Object> params) 
	{
		int outputs = -999;

		if ( !( this.sqlId != null && !"".equals(this.sqlId) ) ) {
			this.sqlId = "selectListCnt";
		}
		
		open();
		try {
			outputs = selectOne(this.sqlMapper + "." + this.sqlId, params);
		} catch(Exception e) {
			logger.error(this.sqlMapper + " > " + this.sqlId + " > 목록 갯수 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());
			
			outputs = -999;
		} finally {
			close();
		}
		
		return outputs;
	}	

    /**
     * 하나의 문자열 데이타 가져오기 
     * @return
     */
	public String oneString(Map<String, Object> params) 
	{
		String outputs = "";

		if ( !( this.sqlId != null && !"".equals(this.sqlId) ) ) {
			this.sqlId = "selectDataString";
		}
		
		open();
		try {
			outputs = selectOne(this.sqlMapper + "." + this.sqlId, params);
		} catch(Exception e) {
			logger.error(this.sqlMapper + " > " + this.sqlId + " > 문자열 가져오기 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());
			
			outputs = "-999";
		} finally {
			close();
		}
		
		return outputs;
	}	
	
	/**
	 * 상세 데이타 가져오기
	 * @param input 입력값
	 * @return 결과값
	 */
	public Map<String, Object> detail(Map<String, Object> params)
	{
		Map<String, Object> outputs = null;

		if ( !( this.sqlId != null && !"".equals(this.sqlId) ) ) {
			this.sqlId = "selectData";
		}		
		
		open();
		try {
			outputs = selectOne(this.sqlMapper + "." + this.sqlId, params);
		} catch(Exception e) {  
			logger.error(this.sqlMapper + " > " + this.sqlId + " > 상세 데이타 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());			

			outputs = null;
		} finally {
			close();
		}
		
		return outputs;		
	}

	/**
	 * 입력
	 * @param input 입력값
	 * @return 결과값
	 */
	public int insert(Map<String, Object> params)
	{
		int outputs = -999;

		if ( !( this.sqlId != null && !"".equals(this.sqlId) ) ) {
			this.sqlId = "insert";
		}		
		
		open();
		try {
			outputs = insert(this.sqlMapper + "." + this.sqlId, params);
			
			commit();
		} catch(Exception e) {  
			logger.error(this.sqlMapper + " > " + this.sqlId + " > 입력 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());			

			outputs = -999;
			rollback();	
			close();
		} finally {			
			close();
		}
		
		return outputs;		
	}	
	
	/**
	 * 수정
	 * @param update 입력값
	 * @return 결과값
	 */
	public int update(Map<String, Object> params)
	{
		int outputs = -999;

		if ( !( this.sqlId != null && !"".equals(this.sqlId) ) ) {
			this.sqlId = "update";
		}		
		
		open();	
		try {
			outputs = update(this.sqlMapper + "." + this.sqlId, params);
			commit();			
		} catch(Exception e) {  
			logger.error(sqlMapper + " > " + this.sqlId + " > 수정 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());

			outputs = -999;
			
			rollback();
			close();
		} finally {
			close();
		}
		
		return outputs;		
	}		
	
	/**
	 * 삭제
	 * @param delete 입력값
	 * @return 결과값
	 */
	public int delete(Map<String, Object> params)
	{
		int outputs = -999;

		if ( !( this.sqlId != null && !"".equals(this.sqlId) ) ) {
			this.sqlId = "delete";
		}		
		
		open();	
		try {
			outputs = delete(this.sqlMapper + "." + this.sqlId, params);
			commit();
		} catch(Exception e) {  
			logger.error(this.sqlMapper + " > " + this.sqlId + " > 삭제 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());			

			outputs = -999;
			
			rollback();
			close();
		} finally {
			close();
		}
		
		return outputs;		
	}		
	
	/**
	 * 입력/수정/삭제/전체삭제
	 * @param input 	입력값
	 * @return 			결과값
	 */
	public int process(Map<String, Object> params)
	{
		int outputs    = 0;
		String method  = (String)params.get("method");
		
		open();	
		try {
			if ( method.equals("A") ) {
				// 삽입
				outputs = insert(this.sqlMapper + ".insert", params);
			} else if ( method.equals("M") ) {
				// 수정
				outputs = update(this.sqlMapper + ".update", params);
			} else if ( method.equals("D") ) {
				// 삭제
				outputs = delete(this.sqlMapper + ".delete", params);
			} else if ( method.equals("D_M") ) {
				// 삭제 : delete_yn = 'Y' 값 처리 
				outputs = update(this.sqlMapper + ".deleteUpdate", params);
			} else if ( method.equals("D_R") ) {
				// 삭제 복원 : delete_yn = 'N' 값 처리
				outputs = update(this.sqlMapper + ".deleteRestore", params);
			} else if ( method.equals("D_A") ) {
				// 전체삭제
				outputs = delete(this.sqlMapper + ".deleteAll", params);
			} else if ( method.equals("A_X") ) {
				// 삽입(sId를 이용) 
				outputs = insert(this.sqlMapper + "." + this.sqlId, params);
			} else if ( method.equals("M_X") ) {
				// 수정(sId를 이용) 
				outputs = update(this.sqlMapper + "." + this.sqlId, params);
			} else if ( method.equals("D_X") ) {
				// 삭제(sId를 이용) 
				outputs = delete(this.sqlMapper + "." + this.sqlId, params);
			} else {
				//
			}
			
			commit();
		} catch(Exception e) {  
			logger.error(this.sqlMapper + " > " + this.sqlId + " > 프로세스 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());			

			outputs = -999;
			
			rollback();
			close();
		} finally {
			close();
		}
		
		return outputs;		
	}	
    
	/**
	 * 입력/수정/삭제/전체삭제
	 * @param input 	입력값
	 * @return 			결과값
	 */
	public int process(Map<String, Object> params, List<Map<String, Object>> lstFiles) // @RequestParam ArrayList<MultipartFile> arrFiles)
	{
		int outputs 	= 0;
		String method 	= (String)params.get("method");
		
		open();	
		try {
			if ( method.equals("A") ) {
				// 삽입
				outputs = insert(this.sqlMapper + ".insert", params);
			} else if ( method.equals("M") ) {
				// 수정
				outputs = update(this.sqlMapper + ".update", params);
			} else if ( method.equals("D") ) {
				// 삭제
				outputs = delete(this.sqlMapper + ".delete", params);
			} else if ( method.equals("D_M") ) {
				// 삭제 : delete_yn = 'Y' 값 처리 
				outputs = delete(this.sqlMapper + ".deleteUpdate", params);
			} else if ( method.equals("D_R") ) {
				// 삭제 복원 : delete_yn = 'N' 값 처리
				outputs = delete(this.sqlMapper + ".deleteRestore", params);
			} else if ( method.equals("D_A") ) {
				// 전체삭제
				outputs = delete(this.sqlMapper + ".deleteAll", params);
			}
			
			commit();
		} catch(Exception e) {  
			logger.error(this.sqlMapper + " > 첨부파일 프로세스 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());	

			outputs = -999;
			rollback();
			close();
		} finally {
			close();
		}
		
		return outputs;		
	}	

	
    /**
     * 목록(Transaction) 
     * @return
     */
	public List<Map<String, Object>> txList(Map<String, Object> params) 
	{
		List<Map<String, Object>> outputs = null;
		
		if ( !( this.txSqlId != null && !"".equals(this.txSqlId) ) ) {
			this.txSqlId = "selectList";
		}
		
		try {
			outputs = txSelectList(this.txSqlMapper + "." + this.txSqlId, params);
		} catch(Exception e) { 
			logger.error(this.txSqlMapper + " > " + this.txSqlId + " > 목록 Transaction 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());		
		} finally {
			//
		}
		
		return outputs;
	}

    /**
     * Index를 이용한 목록 (Transaction)
     * @return
     */
	public List<Map<String, Object>> txListIdx(Map<String, Object> params) 
	{
		List<Map<String, Object>> outputs = null;
		
		if ( !( this.txSqlId != null && !"".equals(this.txSqlId) ) ) {
			this.txSqlId = "selectList";
		}
		
		try {
			outputs = txSelectList(this.txSqlMapper + "." + this.txSqlId, params);
		} catch(Exception e) { 
			logger.error(this.txSqlMapper + " > " + this.txSqlId + " > 목록 Transaction 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());			
		} finally {

		}
		
		return outputs;
	}	
	
    /**
     * 목록 개수 (Transaction)
     * @return
     */
	public int txListCnt(Map<String, Object> params) 
	{
		int outputs = -999;

		if ( !( this.txSqlId != null && !"".equals(this.txSqlId) ) ) {
			this.txSqlId = "selectListCnt";
		}
		
		try {
			outputs = txSelectOne(this.txSqlMapper + "." + this.txSqlId, params);
		} catch(Exception e) {
			logger.error(this.txSqlMapper + " > " + this.txSqlId + " > 목록 갯수 Transaction 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());			

			outputs = -999;
		} finally {
		}
		
		return outputs;
	}	

    /**
     * 하나의 문자열 가져오기 (Transaction)
     * @return
     */
	public String txOneString(Map<String, Object> params) 
	{
		String outputs = "-999";

		if ( !( this.txSqlId != null && !"".equals(this.txSqlId) ) ) {
			this.txSqlId = "selectDataString";
		}
		
		try {
			outputs = txSelectOne(this.txSqlMapper + "." + this.txSqlId, params);
		} catch(Exception e) {
			logger.error(this.txSqlMapper + " > " + this.txSqlId + " > 문자열 가져오기 Transaction 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());			

			outputs = "-999";
		} finally {
		}
		
		return outputs;
	}	
	
	/**
	 * 상세 데이타 가져오기
	 * @param input 입력값
	 * @return 결과값
	 */
	public Map<String, Object> txDetail(Map<String, Object> params)
	{
		Map<String, Object> outputs = null;

		if ( !( this.txSqlId != null && !"".equals(this.txSqlId) ) ) {
			this.txSqlId = "selectData";
		}		
		
		open();	
		try {
			outputs = txSelectOne(this.txSqlMapper + "." + this.txSqlId, params);
		} catch(Exception e) {  
			logger.error(this.txSqlMapper + " > " + this.txSqlId + " > 상세 데이타 Transaction 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());			

			outputs = null;
		} finally {
			close();
		}
		
		return outputs;		
	}	
	
	/**
	 * 입력(Transaction)
	 * @param input 입력값
	 * @return 결과값
	 */
	public int txInsert(Map<String, Object> params)
	{
		int outputs = -999;

		if ( !( this.txSqlId != null && !"".equals(this.txSqlId) ) ) {
			this.txSqlId = "insert";
		}		
		
		try {
			outputs = txInsert(this.txSqlMapper + "." + this.txSqlId, params);
		} catch(Exception e) {  
			logger.error(this.txSqlMapper + " > " + this.txSqlId + " > 입력 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());			

			outputs = -999;
		} finally {
			//
		}
		
		return outputs;		
	}	
	
	/**
	 * 수정(Transaction)
	 * @param update 입력값
	 * @return 결과값
	 */
	public int txUpdate(Map<String, Object> params)
	{
		int outputs = -999;

		if ( !( this.txSqlId != null && !"".equals(this.txSqlId) ) ) {
			this.txSqlId = "update";
		}		
		
		try {
			outputs = txUpdate(this.txSqlMapper + "." + this.txSqlId, params);
		} catch(Exception e) {  
			logger.error(this.txSqlMapper + " > " + this.txSqlId + " > 수정 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());

			outputs = -999;
		} finally {
		}
		
		return outputs;		
	}		
	
	/**
	 * 삭제(Transaction)
	 * @param delete 입력값
	 * @return 결과값
	 */
	public int txDelete(Map<String, Object> params)
	{
		int outputs = -999;

		if ( !( this.txSqlId != null && !"".equals(this.txSqlId) ) ) {
			this.txSqlId = "delete";
		}		
		
		try {
			outputs = txDelete(this.txSqlMapper + "." + this.txSqlId, params);
		} catch(Exception e) {  
			logger.error(this.txSqlMapper + " > " + this.txSqlId + " > 삭제 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());			

			outputs = -999;
		} finally {
		}
		
		return outputs;		
	}		
	
	/**
	 * 입력/수정/삭제/전체삭제 (Transaction)
	 * @param input 	입력값
	 * @return 			결과값
	 */
	public int txProcess(Map<String, Object> params)
	{
		int outputs    = 0;
		String method  = (String)params.get("method");
		
		try {
			if ( method.equals("A") ) {
				// 삽입
				outputs = txInsert(this.txSqlMapper + ".insert", params);
			} else if ( method.equals("M") ) {
				// 수정
				outputs = txUpdate(this.txSqlMapper + ".update", params);
			} else if ( method.equals("D") ) {
				// 삭제
				outputs = txDelete(this.txSqlMapper + ".delete", params);
			} else if ( method.equals("D_M") ) {
				// 삭제 : delete_yn = 'Y' 값 처리 
				outputs = txUpdate(this.txSqlMapper + ".deleteUpdate", params);
			} else if ( method.equals("D_R") ) {
				// 삭제 복원 : delete_yn = 'N' 값 처리
				outputs = txUpdate(this.txSqlMapper + ".deleteRestore", params);
			} else if ( method.equals("D_A") ) {
				// 전체삭제
				outputs = txDelete(this.txSqlMapper + ".deleteAll", params);
			} else if ( method.equals("A_X") ) {
				// 삽입(sId를 이용) 
				outputs = txInsert(this.txSqlMapper + "." + this.txSqlId, params);
			} else if ( method.equals("M_X") ) {
				// 수정(sId를 이용) 
				outputs = txUpdate(this.txSqlMapper + "." + this.txSqlId, params);
			} else if ( method.equals("D_X") ) {
				// 삭제(sId를 이용) 
				outputs = txDelete(this.txSqlMapper + "." + this.txSqlId, params);
			} else {
				//
			}
		} catch(Exception e) {  
			logger.error(this.txSqlMapper + " > " + this.txSqlId + " > 프로세스 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());			

			outputs = -999;
		} finally {
		}
		
		return outputs;		
	}	
    
	/**
	 * 입력/수정/삭제/전체삭제(Transaction)
	 * @param input 	입력값
	 * @return 			결과값
	 */
	public int txProcess(Map<String, Object> params, List<Map<String, Object>> lstFiles) // @RequestParam ArrayList<MultipartFile> arrFiles)
	{
		int outputs 	= 0;
		String method 	= (String)params.get("method");
		
		try {
			if ( method.equals("A") ) {
				// 삽입
				outputs = txInsert(this.txSqlMapper + ".insert", params);
			} else if ( method.equals("M") ) {
				// 수정
				outputs = txUpdate(this.txSqlMapper + ".update", params);
			} else if ( method.equals("D") ) {
				// 삭제
				outputs = txDelete(this.txSqlMapper + ".delete", params);
			} else if ( method.equals("D_M") ) {
				// 삭제 : delete_yn = 'Y' 값 처리 
				outputs = txDelete(this.txSqlMapper + ".deleteUpdate", params);
			} else if ( method.equals("D_R") ) {
				// 삭제 복원 : delete_yn = 'N' 값 처리
				outputs = txDelete(this.txSqlMapper + ".deleteRestore", params);
			} else if ( method.equals("D_A") ) {
				// 전체삭제
				outputs = txDelete(this.txSqlMapper + ".deleteAll", params);
			}
		} catch(Exception e) {  
			logger.error(this.txSqlMapper + " > 프로세스 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());	

			outputs = -999;
		} finally {
		}
		
		return outputs;		
	}	
	
	/**
	 * Map String 데이타를 소문자로 변경
	 * @param hmData
	 * @return
	 */
	public Map<String, Object> convSmallMap(Map<String, Object> hmData) {
		Map<String, Object> newMap = new HashMap<String, Object>();

		Object name[] = hmData.keySet().toArray();

		for ( int cnt=0; cnt < name.length; cnt++) {
			newMap.put(String.valueOf(name[cnt]).toLowerCase(), hmData.get(name[cnt]));
		}

		return newMap;
	}

	/**
	 * List<Map<String, Obejct>> 타입의 String 데이타를 소문자로 변경
	 * @param hmData
	 * @return
	 */
	public List<Map<String, Object>> convSmallList(List<Map<String, Object>> lstData) {
		if ( lstData != null && lstData.size() > 0 ) {
			List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
			
			for ( int i = 0; i < lstData.size(); i++ ) {
				Map<String, Object> hmData = lstData.get(i);
				newList.add(convSmallMap(hmData));
			}
			
			return newList;			
		} else {
			return lstData;
		}
	} 	

	/**
	 * Map String 데이타를 Float 데이타로 변경
	 * @param hmData
	 * @param intFieldName
	 * @return
	 */
	public Map<String, Object> convMapForNumber(Map<String, Object> hmData, String[] floatFieldName) {
		for ( int i = 0; i < floatFieldName.length; i++) {
	        if ( hmData.containsKey(floatFieldName[i]) && hmData.get(floatFieldName[i]) instanceof String ) {
	        	if ( hmData.get(floatFieldName[i]) != null && !"".equals(hmData.get(floatFieldName[i])) ) {
	        		hmData.put(floatFieldName[i], Float.parseFloat(String.valueOf(hmData.get(floatFieldName[i]))));
	        	}
	        }
		}

		return hmData;
	}	
	
	/**
	 * List<Map> String 데이타를 Float 데이타로 변경
	 * @param lstData
	 * @param intFieldName
	 * @return
	 */
	public List<Map<String, Object>> convListMapForNumber(List<Map<String, Object>> lstData, String[] intFieldName) {
		if ( lstData != null && lstData.size() > 0 ) {
			List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
			
			for ( int i = 0; i < lstData.size(); i++ ) {
				Map<String, Object> hmData = lstData.get(i);
				newList.add(convMapForNumber(hmData, intFieldName));
			}
			
			return newList;			
		} else {
			return lstData;
		}
	}		
	
	/**
	 * Mybatis 함수 refactoring 함수들
	 */

	/**
     * Retrieve a single row mapped from the statement key
     * @param <T> the returned object type
     * @param statement
     * @return Mapped object
     */
    @SuppressWarnings("unchecked")
	public <T> T selectOne(String statement) {
    	return (T) getSqlSession().selectOne(statement);
    }

    /**
     * Retrieve a single row mapped from the statement key and parameter.
     * @param <T> the returned object type
     * @param statement Unique identifier matching the statement to use.
     * @param parameter A parameter object to pass to the statement.
     * @return Mapped object
     */
    @SuppressWarnings("unchecked")
	public <T> T selectOne(String statement, Object parameter) {
    	return (T) getSqlSession().selectOne(statement, parameter);
    }
    
    /**
     * Retrieve a list of mapped objects from the statement key and parameter.
     * @param <E> the returned list element type
     * @param statement Unique identifier matching the statement to use.
     * @return List of mapped object
     */
	public <E> List<E> selectList(String statement) {
    	return getSqlSession().selectList(statement);
    }

    /**
     * Retrieve a list of mapped objects from the statement key and parameter.
     * @param <E> the returned list element type
     * @param statement Unique identifier matching the statement to use.
     * @param parameter A parameter object to pass to the statement.
     * @return List of mapped object
     */
	public <E> List<E> selectList(String statement, Object parameter) {
    	return getSqlSession().selectList(statement, parameter);
    }

    /**
     * Retrieve a list of mapped objects from the statement key and parameter,
     * within the specified row bounds.
     * @param <E> the returned list element type
     * @param statement Unique identifier matching the statement to use.
     * @param parameter A parameter object to pass to the statement.
     * @param rowBounds  Bounds to limit object retrieval
     * @return List of mapped object
     */
	public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds) {
    	return getSqlSession().selectList(statement, parameter, rowBounds);
    }

    /**
     * The selectMap is a special case in that it is designed to convert a list
     * of results into a Map based on one of the properties in the resulting
     * objects.
     * Eg. Return a of Map[Integer,Author] for selectMap("selectAuthors","id")
     * @param <K> the returned Map keys type
     * @param <V> the returned Map values type
     * @param statement Unique identifier matching the statement to use.
     * @param mapKey The property to use as key for each value in the list.
     * @return Map containing key pair data.
     */
	public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
    	return getSqlSession().selectMap(statement, mapKey);
    }

    /**
     * The selectMap is a special case in that it is designed to convert a list
     * of results into a Map based on one of the properties in the resulting
     * objects.
     * @param <K> the returned Map keys type
     * @param <V> the returned Map values type
     * @param statement Unique identifier matching the statement to use.
     * @param parameter A parameter object to pass to the statement.
     * @param mapKey The property to use as key for each value in the list.
     * @return Map containing key pair data.
     */
	public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
    	return getSqlSession().selectMap(statement, parameter, mapKey);
    }

    /**
     * The selectMap is a special case in that it is designed to convert a list
     * of results into a Map based on one of the properties in the resulting
     * objects.
     * @param <K> the returned Map keys type
     * @param <V> the returned Map values type
     * @param statement Unique identifier matching the statement to use.
     * @param parameter A parameter object to pass to the statement.
     * @param mapKey The property to use as key for each value in the list.
     * @param rowBounds  Bounds to limit object retrieval
     * @return Map containing key pair data.
     */
	public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds) {
    	return getSqlSession().selectMap(statement, parameter, mapKey, rowBounds);
    }

    /**
     * Retrieve a single row mapped from the statement key and parameter
     * using a {@code ResultHandler}.
     * @param statement Unique identifier matching the statement to use.
     * @param parameter A parameter object to pass to the statement.
     * @param handler ResultHandler that will handle each retrieved row
     * @return Mapped object
     */
    public void select(String statement, Object parameter, ResultHandler handler) {
    	getSqlSession().select(statement, parameter, handler);
    }

    /**
     * Retrieve a single row mapped from the statement
     * using a {@code ResultHandler}.
     * @param statement Unique identifier matching the statement to use.
     * @param handler ResultHandler that will handle each retrieved row
     * @return Mapped object
     */
    public void select(String statement, ResultHandler handler) {
    	getSqlSession().select(statement, handler);
    }

    /**
     * Retrieve a single row mapped from the statement key and parameter
     * using a {@code ResultHandler} and {@code RowBounds}
     * @param statement Unique identifier matching the statement to use.
     * @param rowBounds RowBound instance to limit the query results
     * @param handler ResultHandler that will handle each retrieved row
     * @return Mapped object
     */
    public void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler) {
    	getSqlSession().select(statement, parameter, rowBounds, handler);
    }

    /**
     * Execute an insert statement.
     * @param statement Unique identifier matching the statement to execute.
     * @return int The number of rows affected by the insert.
     */
    public int insert(String statement) {
    	return getSqlSession().insert(statement);
    }

    /**
     * Execute an insert statement with the given parameter object. Any generated
     * autoincrement values or selectKey entries will modify the given parameter
     * object properties. Only the number of rows affected will be returned.
     * @param statement Unique identifier matching the statement to execute.
     * @param parameter A parameter object to pass to the statement.
     * @return int The number of rows affected by the insert.
     */
    public int insert(String statement, Object parameter) {
    	return getSqlSession().insert(statement, parameter);
    }

    /**
     * Execute an update statement. The number of rows affected will be returned.
     * @param statement Unique identifier matching the statement to execute.
     * @return int The number of rows affected by the update.
     */
    public int update(String statement) {
    	return getSqlSession().update(statement);
    }

    /**
     * Execute an update statement. The number of rows affected will be returned.
     * @param statement Unique identifier matching the statement to execute.
     * @param parameter A parameter object to pass to the statement.
     * @return int The number of rows affected by the update.
     */
    public int update(String statement, Object parameter) {
    	return getSqlSession().update(statement, parameter);
    }

    /**
     * Execute a delete statement. The number of rows affected will be returned.
     * @param statement Unique identifier matching the statement to execute.
     * @return int The number of rows affected by the delete.
     */
    public int delete(String statement) {
    	return getSqlSession().delete(statement);
    }

    /**
     * Execute a delete statement. The number of rows affected will be returned.
     * @param statement Unique identifier matching the statement to execute.
     * @param parameter A parameter object to pass to the statement.
     * @return int The number of rows affected by the delete.
     */
    public int delete(String statement, Object parameter) {
    	return getSqlSession().delete(statement, parameter);
    }

    /**
     * Flushes batch statements and commits database connection.
     * Note that database connection will not be committed if no updates/deletes/inserts were called.
     * To force the commit call {@link SqlSession#commit(boolean)}
     */
    public void commit() {
    	getSqlSession().commit();
    }

    /**
     * Flushes batch statements and commits database connection.
     * @param force forces connection commit
     */
    public void commit(boolean force) {
    	getSqlSession().commit(force);
    }

    /**
     * Discards pending batch statements and rolls database connection back.
     * Note that database connection will not be rolled back if no updates/deletes/inserts were called.
     * To force the rollback call {@link SqlSession#rollback(boolean)}
     */
    public void rollback() {
    	getSqlSession().rollback();
    }

    /**
     * Discards pending batch statements and rolls database connection back.
     * Note that database connection will not be rolled back if no updates/deletes/inserts were called.
     * @param force forces connection rollback
     */
    public void rollback(boolean force) {
    	getSqlSession().rollback(force);
    }

    /**
     * Flushes batch statements.
     * @return BatchResult list of updated records
     * @since 3.0.6
     */
    public List<BatchResult> flushStatements() {
    	return getSqlSession().flushStatements();
    }

    /**
     * Clears local session cache
     */
    public void clearCache() {
    	getSqlSession().clearCache();
    }

    /**
     * Retrieves current configuration
     * @return Configuration
     */
    public Configuration getConfiguration() {
    	return getSqlSession().getConfiguration();
    }

    /**
     * Retrieves a mapper.
     * @param <T> the mapper type
     * @param type Mapper interface class
     * @return a mapper bound to this SqlSession
     */
    public <T> T getMapper(Class<T> type) {
    	return getSqlSession().getMapper(type);
    }

    /**
     * Retrieves inner database connection
     * @return Connection
     */
    public Connection getConnection() {
    	return getSqlSession().getConnection();
    }
    
	/**
	 * Mybatis 함수 refactoring 함수들 (Transaction)
	 */

	/**
     * Retrieve a single row mapped from the statement key
     * @param <T> the returned object type
     * @param statement
     * @return Mapped object
     */
    @SuppressWarnings("unchecked")
	public <T> T txSelectOne(String statement) {
    	return (T) getTxSqlSession().selectOne(statement);
    }

    /**
     * Retrieve a single row mapped from the statement key and parameter.
     * @param <T> the returned object type
     * @param statement Unique identifier matching the statement to use.
     * @param parameter A parameter object to pass to the statement.
     * @return Mapped object
     */
    @SuppressWarnings("unchecked")
	public <T> T txSelectOne(String statement, Object parameter) {
    	return (T) getTxSqlSession().selectOne(statement, parameter);
    }
    
    /**
     * Retrieve a list of mapped objects from the statement key and parameter.
     * @param <E> the returned list element type
     * @param statement Unique identifier matching the statement to use.
     * @return List of mapped object
     */
	public <E> List<E> txSelectList(String statement) {
    	return getTxSqlSession().selectList(statement);
    }

    /**
     * Retrieve a list of mapped objects from the statement key and parameter.
     * @param <E> the returned list element type
     * @param statement Unique identifier matching the statement to use.
     * @param parameter A parameter object to pass to the statement.
     * @return List of mapped object
     */
	public <E> List<E> txSelectList(String statement, Object parameter) {
    	return getTxSqlSession().selectList(statement, parameter);
    }

    /**
     * Retrieve a list of mapped objects from the statement key and parameter,
     * within the specified row bounds.
     * @param <E> the returned list element type
     * @param statement Unique identifier matching the statement to use.
     * @param parameter A parameter object to pass to the statement.
     * @param rowBounds  Bounds to limit object retrieval
     * @return List of mapped object
     */
	public <E> List<E> txSelectList(String statement, Object parameter, RowBounds rowBounds) {
    	return getTxSqlSession().selectList(statement, parameter, rowBounds);
    }

    /**
     * The selectMap is a special case in that it is designed to convert a list
     * of results into a Map based on one of the properties in the resulting
     * objects.
     * Eg. Return a of Map[Integer,Author] for selectMap("selectAuthors","id")
     * @param <K> the returned Map keys type
     * @param <V> the returned Map values type
     * @param statement Unique identifier matching the statement to use.
     * @param mapKey The property to use as key for each value in the list.
     * @return Map containing key pair data.
     */
	public <K, V> Map<K, V> txSelectMap(String statement, String mapKey) {
    	return getTxSqlSession().selectMap(statement, mapKey);
    }

    /**
     * The selectMap is a special case in that it is designed to convert a list
     * of results into a Map based on one of the properties in the resulting
     * objects.
     * @param <K> the returned Map keys type
     * @param <V> the returned Map values type
     * @param statement Unique identifier matching the statement to use.
     * @param parameter A parameter object to pass to the statement.
     * @param mapKey The property to use as key for each value in the list.
     * @return Map containing key pair data.
     */
	public <K, V> Map<K, V> txSelectMap(String statement, Object parameter, String mapKey) {
    	return getTxSqlSession().selectMap(statement, parameter, mapKey);
    }

    /**
     * The selectMap is a special case in that it is designed to convert a list
     * of results into a Map based on one of the properties in the resulting
     * objects.
     * @param <K> the returned Map keys type
     * @param <V> the returned Map values type
     * @param statement Unique identifier matching the statement to use.
     * @param parameter A parameter object to pass to the statement.
     * @param mapKey The property to use as key for each value in the list.
     * @param rowBounds  Bounds to limit object retrieval
     * @return Map containing key pair data.
     */
	public <K, V> Map<K, V> txSelectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds) {
    	return getTxSqlSession().selectMap(statement, parameter, mapKey, rowBounds);
    }

    /**
     * Retrieve a single row mapped from the statement key and parameter
     * using a {@code ResultHandler}.
     * @param statement Unique identifier matching the statement to use.
     * @param parameter A parameter object to pass to the statement.
     * @param handler ResultHandler that will handle each retrieved row
     * @return Mapped object
     */
    public void txSelect(String statement, Object parameter, ResultHandler handler) {
    	getTxSqlSession().select(statement, parameter, handler);
    }

    /**
     * Retrieve a single row mapped from the statement
     * using a {@code ResultHandler}.
     * @param statement Unique identifier matching the statement to use.
     * @param handler ResultHandler that will handle each retrieved row
     * @return Mapped object
     */
    public void txSelect(String statement, ResultHandler handler) {
    	getTxSqlSession().select(statement, handler);
    }

    /**
     * Retrieve a single row mapped from the statement key and parameter
     * using a {@code ResultHandler} and {@code RowBounds}
     * @param statement Unique identifier matching the statement to use.
     * @param rowBounds RowBound instance to limit the query results
     * @param handler ResultHandler that will handle each retrieved row
     * @return Mapped object
     */
    public void txSelect(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler) {
    	getTxSqlSession().select(statement, parameter, rowBounds, handler);
    }

    /**
     * Execute an insert statement.
     * @param statement Unique identifier matching the statement to execute.
     * @return int The number of rows affected by the insert.
     */
    public int txInsert(String statement) {
    	return getTxSqlSession().insert(statement);
    }

    /**
     * Execute an insert statement with the given parameter object. Any generated
     * autoincrement values or selectKey entries will modify the given parameter
     * object properties. Only the number of rows affected will be returned.
     * @param statement Unique identifier matching the statement to execute.
     * @param parameter A parameter object to pass to the statement.
     * @return int The number of rows affected by the insert.
     */
    public int txInsert(String statement, Object parameter) {
    	return getTxSqlSession().insert(statement, parameter);
    }

    /**
     * Execute an update statement. The number of rows affected will be returned.
     * @param statement Unique identifier matching the statement to execute.
     * @return int The number of rows affected by the update.
     */
    public int txUpdate(String statement) {
    	return getTxSqlSession().update(statement);
    }

    /**
     * Execute an update statement. The number of rows affected will be returned.
     * @param statement Unique identifier matching the statement to execute.
     * @param parameter A parameter object to pass to the statement.
     * @return int The number of rows affected by the update.
     */
    public int txUpdate(String statement, Object parameter) {
    	return getTxSqlSession().update(statement, parameter);
    }

    /**
     * Execute a delete statement. The number of rows affected will be returned.
     * @param statement Unique identifier matching the statement to execute.
     * @return int The number of rows affected by the delete.
     */
    public int txDelete(String statement) {
    	return getTxSqlSession().delete(statement);
    }

    /**
     * Execute a delete statement. The number of rows affected will be returned.
     * @param statement Unique identifier matching the statement to execute.
     * @param parameter A parameter object to pass to the statement.
     * @return int The number of rows affected by the delete.
     */
    public int txDelete(String statement, Object parameter) {
    	return getTxSqlSession().delete(statement, parameter);
    }

    /**
     * Flushes batch statements and commits database connection.
     * Note that database connection will not be committed if no updates/deletes/inserts were called.
     * To force the commit call {@link SqlSession#commit(boolean)}
     */
    public void txCommit() {
    	getTxSqlSession().commit();
    }

    /**
     * Flushes batch statements and commits database connection.
     * @param force forces connection commit
     */
    public void txCommit(boolean force) {
    	getTxSqlSession().commit(force);
    }

    /**
     * Discards pending batch statements and rolls database connection back.
     * Note that database connection will not be rolled back if no updates/deletes/inserts were called.
     * To force the rollback call {@link SqlSession#rollback(boolean)}
     */
    public void txRollback() {
    	getTxSqlSession().rollback();
    }

    /**
     * Discards pending batch statements and rolls database connection back.
     * Note that database connection will not be rolled back if no updates/deletes/inserts were called.
     * @param force forces connection rollback
     */
    public void txRollback(boolean force) {
    	getTxSqlSession().rollback(force);
    }

    /**
     * Flushes batch statements.
     * @return BatchResult list of updated records
     * @since 3.0.6
     */
    public List<BatchResult> txFlushStatements() {
    	return getTxSqlSession().flushStatements();
    }

    /**
     * Clears local session cache
     */
    public void txClearCache() {
    	getTxSqlSession().clearCache();
    }

    /**
     * Retrieves current configuration
     * @return Configuration
     */
    public Configuration getTxConfiguration() {
    	return getTxSqlSession().getConfiguration();
    }

    /**
     * Retrieves a mapper.
     * @param <T> the mapper type
     * @param type Mapper interface class
     * @return a mapper bound to this SqlSession
     */
    public <T> T getTxMapper(Class<T> type) {
    	return getTxSqlSession().getMapper(type);
    }

    /**
     * Retrieves inner database connection
     * @return Connection
     */
    public Connection getTxConnection() {
    	return getTxSqlSession().getConnection();
    }
}