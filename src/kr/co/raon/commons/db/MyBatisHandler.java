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

public class MyBatisHandler {
    /**
     * 싱글톤 패턴을 이용하기 위한 자신 Instance
     */
    private volatile static MyBatisHandler mybatisInstance;

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
     * SqlMap 용 SqlSession
     */    
    private SqlSession sqlSession2;    
    

    /**
     * SqlMap 용 SqlMapper 설정 변수
     */    
    private String sqlMapper2;
    

    /**
     * SqlMap 용 SqlSession
     */    
    private SqlSession sqlSession3;    
    

    /**
     * SqlMap 용 SqlMapper 설정 변수
     */    
    private String sqlMapper3;
    
    public void setSqlSession( SqlSession _sqlSession ) { this.sqlSession = _sqlSession; }
    public void setSqlMapper( String _sqlMapper ) { this.sqlMapper = _sqlMapper; }
    public String getSqlMapper() { return this.sqlMapper; }    
    
    /**
     * 로그 출력
     */
    private static final Logger logger = LoggerFactory.getLogger(MyBatisHandler.class);
    
    /**
     * 생성자
     */
    private MyBatisHandler() {
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
    public static MyBatisHandler getInstance() {
		if (mybatisInstance == null) {
			synchronized(MyBatisHandler.class) {
				if ( mybatisInstance == null ) {
					mybatisInstance = new MyBatisHandler();
				}
			}
		}
		
		return mybatisInstance;    	
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
			if ( sqlSession != null ) {
				sqlSession = sqlSessionFactory.openSession();
			}
		} catch (Exception e) {
			logger.error("SqlSession Open 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());
		}
	}    
	
	/**
	 * SqlSession 가져오기
	 * @return
	 */
	public SqlSession getSqlSession() {
		if ( sqlSession == null ) {
			sqlSession = sqlSessionFactory.openSession();
		}
		return sqlSession;
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
	 * 초기화
	 * @throws IOException
	 */
    public static void reset() throws IOException {
    	mybatisInstance = new MyBatisHandler();
    }
    
    /**
     * 목록 
     * @return
     */
	public List<Map<String, Object>> list(Map<String, Object> params) 
	{
		List<Map<String, Object>> outputs = null;
		String sId     = (String)params.get("sId");
		
		if ( !( sId != null && !"".equals(sId) ) ) {
			sId = "selectList";
		}
		
		try {
			outputs = selectList(sqlMapper + "." + sId, params);
		} catch(Exception e) { 
			logger.error(sqlMapper + " > " + sId + " > 목록 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());
			
			outputs = null;
		} finally {
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
		String sId     = (String)params.get("sId");
		
		if ( !( sId != null && !"".equals(sId) ) ) {
			sId = "selectIndexList";
		}
		
		try {
			outputs = selectList(sqlMapper + "." + sId, params);
		} catch(Exception e) { 
			logger.error(sqlMapper + " > " + sId + " > 목록 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());
			
			outputs = null;
		} finally {

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
		String sId     = (String)params.get("sId");

		if ( !( sId != null && !"".equals(sId) ) ) {
			sId = "selectListCnt";
		}
		
		try {
			outputs = selectOne(sqlMapper + "." + sId, params);
		} catch(Exception e) {
			logger.error(sqlMapper + " > " + sId + " > 목록 갯수 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());
			
			outputs = -999;
		} finally {
			
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
		String sId     = (String)params.get("sId");

		if ( !( sId != null && !"".equals(sId) ) ) {
			sId = "selectData";
		}		
		
		try {
			outputs = selectOne(sqlMapper + "." + sId, params);
		} catch(Exception e) {  
			logger.error(sqlMapper + " > " + sId + " > 상세 데이타 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());			

			outputs = null;
		} finally {

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
		String sId     = (String)params.get("sId");

		if ( !( sId != null && !"".equals(sId) ) ) {
			sId = "insert";
		}		
		
		try {
			outputs = insert(sqlMapper + "." + sId, params);
		} catch(Exception e) {  
			logger.error(sqlMapper + " > " + sId + " > 입력 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());			

			outputs = -999;
		} finally {
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
		String sId     = (String)params.get("sId");

		if ( !( sId != null && !"".equals(sId) ) ) {
			sId = "update";
		}		
		
		open();	
		try {
			outputs = update(sqlMapper + "." + sId, params);
		} catch(Exception e) {  
			logger.error(sqlMapper + " > " + sId + " > 수정 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());

			outputs = -999;
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
		String sId     = (String)params.get("sId");

		if ( !( sId != null && !"".equals(sId) ) ) {
			sId = "delete";
		}		
		
		open();	
		try {
			outputs = delete(sqlMapper + "." + sId, params);
		} catch(Exception e) {  
			logger.error(sqlMapper + " > " + sId + " > 삭제 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());			

			outputs = -999;
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
		//String sqlMapper = (String)params.get("sqlMapper");
		String sId     = (String)params.get("sId");
		
		open();	
		try {
			if ( method.equals("A") ) {
				// 삽입
				outputs = insert(sqlMapper + ".insert", params);
			} else if ( method.equals("M") ) {
				// 수정
				outputs = update(sqlMapper + ".update", params);
			} else if ( method.equals("D") ) {
				// 삭제
				outputs = delete(sqlMapper + ".delete", params);
			} else if ( method.equals("D_M") ) {
				// 삭제 : delete_yn = 'Y' 값 처리 
				outputs = update(sqlMapper + ".deleteUpdate", params);
			} else if ( method.equals("D_R") ) {
				// 삭제 복원 : delete_yn = 'N' 값 처리
				outputs = update(sqlMapper + ".deleteRestore", params);
			} else if ( method.equals("D_A") ) {
				// 전체삭제
				outputs = delete(sqlMapper + ".deleteAll", params);
			} else if ( method.equals("A_X") ) {
				// 삽입(sId를 이용) 
				outputs = insert(sqlMapper + "." + sId, params);
			} else if ( method.equals("M_X") ) {
				// 수정(sId를 이용) 
				outputs = update(sqlMapper + "." + sId, params);
			} else if ( method.equals("D_X") ) {
				// 삭제(sId를 이용) 
				outputs = delete(sqlMapper + "." + sId, params);
			} else {
				//
			}
			
			commit();
		} catch(Exception e) {  
			logger.error(sqlMapper + " > " + sId + " > 프로세스 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());			

			outputs = -999;
			rollback();
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
		//String sqlMapper 	= (String)params.get("sqlMapper");
		
		open();	
		try {
			if ( method.equals("A") ) {
				// 삽입
				outputs = insert(sqlMapper + ".insert", params);
			} else if ( method.equals("M") ) {
				// 수정
				outputs = update(sqlMapper + ".update", params);
			} else if ( method.equals("D") ) {
				// 삭제
				outputs = delete(sqlMapper + ".delete", params);
			} else if ( method.equals("D_M") ) {
				// 삭제 : delete_yn = 'Y' 값 처리 
				outputs = delete(sqlMapper + ".deleteUpdate", params);
			} else if ( method.equals("D_R") ) {
				// 삭제 복원 : delete_yn = 'N' 값 처리
				outputs = delete(sqlMapper + ".deleteRestore", params);
			} else if ( method.equals("D_A") ) {
				// 전체삭제
				outputs = delete(sqlMapper + ".deleteAll", params);
			}
			
			commit();
		} catch(Exception e) {  
			logger.error(sqlMapper + " > 프로세스 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());	

			outputs = -999;
			rollback();			
		} finally {
			close();
		}
		
		return outputs;		
	}	

	
    /**
     * 목록(Transaction) 
     * @return
     */
	public List<Map<String, Object>> listTrx(Map<String, Object> params) 
	{
		List<Map<String, Object>> outputs = null;
		String sId     = (String)params.get("sId");
		
		if ( !( sId != null && !"".equals(sId) ) ) {
			sId = "selectList";
		}
		
		try {
			outputs = selectList(sqlMapper + "." + sId, params);
		} catch(Exception e) { 
			logger.error(sqlMapper + " > " + sId + " > 목록 Transaction 오류");
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
	public List<Map<String, Object>> listIdxTrx(Map<String, Object> params) 
	{
		List<Map<String, Object>> outputs = null;
		//String sqlMapper = (String)params.get("sqlMapper");
		String sId     = (String)params.get("sId");
		
		if ( !( sId != null && !"".equals(sId) ) ) {
			sId = "selectList";
		}
		
		open();
		try {
			outputs = selectList(sqlMapper + "." + sId, params);
		} catch(Exception e) { 
			if ( sqlSession != null ) {
				sqlSession.close();
			}

			logger.error(sqlMapper + " > " + sId + " > 목록 Transaction 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());			
		} finally {
			close();
		}
		
		return outputs;
	}	
	
    /**
     * 목록 개수 (Transaction)
     * @return
     */
	public int listCntTrx(Map<String, Object> params) 
	{
		int outputs = -999;
		String sId     = (String)params.get("sId");

		if ( !( sId != null && !"".equals(sId) ) ) {
			sId = "selectListCnt";
		}
		
		open();
		try {
			outputs = selectOne(sqlMapper + "." + sId, params);
		} catch(Exception e) {
			logger.error(sqlMapper + " > " + sId + " > 목록 갯수 Transaction 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());			

			outputs = -999;
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
	public Map<String, Object> detailTrx(Map<String, Object> params)
	{
		Map<String, Object> outputs = null;
		String sId     = (String)params.get("sId");

		if ( !( sId != null && !"".equals(sId) ) ) {
			sId = "selectData";
		}		
		
		open();	
		try {
			outputs = selectOne(sqlMapper + "." + sId, params);
		} catch(Exception e) {  
			logger.error(sqlMapper + " > " + sId + " > 상세 데이타 Transaction 오류");
			logger.error("=========================================================");
			logger.error(e.getMessage());			

			outputs = null;
		} finally {
			close();
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
}// end class