package kr.co.raon.commons.util;

import java.sql.Connection; 
import java.sql.DatabaseMetaData; 
import java.sql.DriverManager; 
import java.sql.PreparedStatement;
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.util.ArrayList; 
import java.util.List;  

public class ReadMetaData {

	public List<String> listAllTableNamesMetaData(String schemaName, String jdbcUrl, String dbId, String dbPass, String driver) throws SQLException{
		Connection conn = null;
		List<String> list = new ArrayList<String>(); 
		
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);
		}catch (ClassNotFoundException e){
			String back = "fail";
			String result = "드라이버를 찾을 수 없습니다!";
			list.add(back);
			list.add(result);
			return list;
		}catch(SQLException e){
			String back = "fail";
			String result = "url주소나 계정 또는 비밀번호를 확인하세요.";
			list.add(back);
			list.add(result);
			return list;
		}
		DatabaseMetaData meta = conn.getMetaData();
		
		
		ResultSet rs = meta.getTables(null, schemaName, null, new String[] { "TABLE" });         
		while(rs.next()) {             
			String tableName = rs.getString("TABLE_NAME");
			list.add(tableName);
		}
		rs.close();
		conn.close();
		return list;     
	}      
	
	public List<String> listAllTableNames(String schemaName, String jdbcUrl, String dbId, String dbPass, String driver) throws SQLException{
		Connection conn = null;
		List<String> list = new ArrayList<String>(); 
		
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);
		}catch (ClassNotFoundException e){
			String back = "fail";
			String result = "드라이버를 찾을 수 없습니다!";
			list.add(back);
			list.add(result);
			return list;
		}catch(SQLException e){
			String back = "fail";
			String result = "url주소나 계정 또는 비밀번호를 확인하세요.";
			list.add(back);
			list.add(result);
			return list;
		}
		
		String sql = "SELECT A.TNAME                  AS TABLE_NAME     "
                   + "     , NVL(B.COMMENTS, A.TNAME) AS TABLE_COMMENTS "
                   + "  FROM TAB               A                        "
                   + "     , USER_TAB_COMMENTS B                        "
                   + " WHERE A.TNAME   = B.TABLE_NAME                   "
                   + "   AND A.TABTYPE = B.TABLE_TYPE                   ";

		PreparedStatement psmt = conn.prepareStatement(sql);
		ResultSet rs = psmt.executeQuery();         

		while(rs.next()) {             
			list.add(rs.getString("TABLE_NAME"));
			list.add(rs.getString("TABLE_COMMENTS"));
		}
		rs.close();
		psmt.close();
		conn.close();
		return list;     
	} 	
	
	public ArrayList<String> printPrimaryKeys(String schemaName, String tableName, String jdbcUrl, String dbId, String dbPass, String driver) throws SQLException, ClassNotFoundException {
		
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);         
		DatabaseMetaData meta = conn.getMetaData();
		            
		ResultSet rs = meta.getPrimaryKeys(null, schemaName, tableName);
		
		ArrayList<String> datas = new ArrayList<String>();
		
		while(rs.next()) {
			datas.add(rs.getString("COLUMN_NAME"));
		}
		rs.close();
		conn.close();
		return datas;
	}
	
	public ArrayList<String> printColumnsMetaData(String schemaName, String tableName, String jdbcUrl, String dbId, String dbPass, String driver) throws SQLException, ClassNotFoundException {
		
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);         
		DatabaseMetaData meta = conn.getMetaData();
		            
		ResultSet rs = meta.getColumns(null, schemaName, tableName, null);
		
		ArrayList<String> datas = new ArrayList<String>();
		
		while(rs.next()) {
			datas.add(rs.getString("COLUMN_NAME"));
			datas.add(rs.getString("TYPE_NAME"));
			datas.add(rs.getString("COLUMN_SIZE"));
			datas.add("");
		}
		rs.close();
		conn.close();
		return datas;
	}
	
	public ArrayList<String> printColumns(String schemaName, String tableName, String jdbcUrl, String dbId, String dbPass, String driver) throws SQLException, ClassNotFoundException {
		
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);

		String sql = "SELECT A.COLUMN_NAME  AS COLUMN_NAME     "
                   + "     , A.DATA_TYPE    AS TYPE_NAME       "
                   + "     , A.DATA_LENGTH  AS COLUMN_SIZE     "
                   + "     , B.COMMENTS     AS COLUMN_COMMENTS "
                   + "  FROM USER_TAB_COLUMNS  A               "
                   + "     , USER_COL_COMMENTS B               "
                   + " WHERE A.TABLE_NAME  = B.TABLE_NAME      "
                   + "   AND A.COLUMN_NAME = B.COLUMN_NAME     "
                   + "   AND A.TABLE_NAME  = ?                 "        
                   + " ORDER BY A.COLUMN_ID                    ";

		PreparedStatement psmt = conn.prepareStatement(sql);
		psmt.setString(1, tableName);
		
		ResultSet rs = psmt.executeQuery();
		
		ArrayList<String> datas = new ArrayList<String>();
		
		while(rs.next()) {
			datas.add(rs.getString("COLUMN_NAME"));
			datas.add(rs.getString("TYPE_NAME"));
			datas.add(rs.getString("COLUMN_SIZE"));
			datas.add(rs.getString("COLUMN_COMMENTS"));
		}
		rs.close();
		psmt.close();
		conn.close();
		return datas;
	}	
} 