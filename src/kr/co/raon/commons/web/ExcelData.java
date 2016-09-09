/**
 * 파   일   명 : ExcelData.java
 * 작   업   자 : 장재현 (jang1945@raonsolution.co.kr)
 * 작 업  일 자 : 2016-02-03
 * 설        명 : 엑셀 다운로드를 위한 구조
 * ------------------------------------------------------------------------------- 이력 사항 -------------------------------------------------------------------------------
 * 순번  작성자   작성일자    변경사항
 * ---- --------  ----------- ----------------------------------------------------------------------------------------------------------------------------------------------
 *  1    장재현   2013-04-22  최초작성
 */
package kr.co.raon.commons.web;

public class ExcelData {
 
	 //멤버변수
	 private int         sheet_num_          = 0;    // Sheet 번호
	 
	 private int         start_head_row_     = 0;    // Head 제목 Row
	 private int         start_head_cell_    = 0;    // Head 제목 Cell
	
	 private int         start_title_row_    = 0;    // 제목 Row
	 private int         start_title_cell_   = 0;    // 제목 Cell
	
	 private int         start_data_row_     = 0;    // 데이타 Row
	 private int         start_data_cell_    = 0;    // 데이타 Cell
	 
	 private String[]    head_data_;                 // Head 데이타
	 private String[][]  title_data_;                // title 데이타
	 private double[][]  table_data_;                // 더블형 데이타 
	 private String[][]  table_string_data_;         // 문자형 데이타
	 private String[]    data_format_;               // 문자형 데이타 포맷 (문자, 숫자 공용시 처리)
	 
	 private boolean     is_head_            = false;    // 헤드라인 제목 여부
	 private boolean     is_title_           = false;    // 왼쪽 타이틀 여부
	 private boolean     is_table_           = false;    // 더블형 데이타 여부
	 private boolean     is_table_string_    = false;    // 문자형 데이타여부
	 private boolean     is_data_format_     = false;    // 데이타 포맷 여부 
	 
	 private boolean     is_encrypt_         = false;    // 암호화 여부
	 
	 //////////
	 /**
	  * 엑셀 Sheet 번호 가져오기
	  */
	 public int getSheetNum() {
	     return sheet_num_;
	 }
	
	 /**
	  * 엑셀 Sheet 번호 셋팅
	  */ 
	 public void setSheetNum(int sheet_num) {
	     this.sheet_num_ = sheet_num;
	 }
	
	 /**
	  * Head 제목 시작 Row 번호 가져오기
	  */
	 public int getStartHeadRow() {
	     return start_head_row_;
	 }
	
	 /**
	  * Head 제목 시작 Row 번호 셋팅
	  */ 
	 public void setStartHeadRow(int start_head_row) {
	     this.start_head_row_ = start_head_row;
	 }
	 
	 /**
	  * Head 제목 시작 Cell 번호 가져오기
	  */
	 public int getStartHeadCell() {
	     return start_head_cell_;
	 }
	
	 /**
	  * Head 제목 시작 Cell 번호 셋팅
	  */ 
	 public void setStartHeadCell(int start_head_cell) {
	     this.start_head_cell_ = start_head_cell;
	 }
	
	 /**
	  * Head 제목 시작 Row 번호 가져오기
	  */
	 public int getStartTitleRow() {
	     return start_title_row_;
	 }
	
	 /**
	  * Head 제목 시작 Row 번호 셋팅
	  */ 
	 public void setStartTitleRow(int start_title_row) {
	     this.start_title_row_ = start_title_row;
	 }
	 
	 /**
	  * Title 제목 시작 Cell 번호 가져오기
	  */
	 public int getStartTitleCell() {
	     return start_title_cell_;
	 }
	
	 /**
	  * Title 제목 시작 Cell 번호 셋팅
	  */ 
	 public void setStartTitleCell(int start_title_cell) {
	     this.start_title_cell_ = start_title_cell;
	 }
	
	 /**
	  * Data 제목 시작 Row 번호 가져오기
	  */
	 public int getStartDataRow() {
	     return start_data_row_;
	 }
	
	 /**
	  * Data 제목 시작 Row 번호 셋팅
	  */ 
	 public void setStartDataRow(int start_data_row) {
	     this.start_data_row_ = start_data_row;
	 }
	 
	 /**
	  * Data 제목 시작 Cell 번호 가져오기
	  */
	 public int getStartDataCell() {
	     return start_data_cell_;
	 }
	
	 /**
	  * Data 제목 시작 Cell 번호 셋팅
	  */ 
	 public void setStartDataCell(int start_data_cell) {
	     this.start_data_cell_ = start_data_cell;
	 }
	 
	 /**
	  * 엑셀 Head 제목 데이타 가져오기
	  */
	 public String[] getHeadData() {
	     return head_data_;
	 }
	
	 /**
	  * 엑셀 Head 제목 데이타 셋팅
	  */ 
	 public void setHeadData(String[] head_data) {
	     this.head_data_ = head_data;
	     this.is_head_   = true;
	 }
	
	 /**
	  * 엑셀 제목 데이타 가져오기
	  */
	 public String[][] getTitleData() {
	     return title_data_;
	 }
	
	 /**
	  * 엑셀 제목 데이타 셋팅
	  */ 
	 public void setTitleData(String[][] title_data) {
	     this.title_data_ = title_data;
	     this.is_title_   = true;
	 }   
	 
	 /**
	  * 엑셀  Table 데이타 가져오기
	  */
	 public double[][] getTableData() {
	     return table_data_;
	 }
	
	 /**
	  * 엑셀 Table 데이타 셋팅
	  */ 
	 public void setTableData(double[][] table_data) {
	     this.table_data_ = table_data;
	     this.is_table_   = true;
	 }
	
	 /**
	  * 엑셀  Table 데이타 가져오기
	  */
	 public String[][] getTableStringData() {
	     return table_string_data_;
	 }
	
	 /**
	  * 엑셀 Table 데이타 셋팅
	  */ 
	 public void setTableStringData(String[][] table_string_data) {
	     this.table_string_data_ = table_string_data;
	     this.is_table_string_   = true;
	 }   
	
	 /**
	  * 엑셀 데이타 포맷 가져오기
	  */
	 public String[] getDataFormat() {
	     return data_format_;
	 }
	 
	 /**
	  * 엑셀 Table 데이타 셋팅
	  */ 
	 public void setDataFormat(String[] data_format) {
	     this.data_format_       = data_format;
	     this.is_data_format_    = true;
	 }   
	 
	 /**
	  * Head 데이타 존재 여부 가져오기
	  */
	 public boolean getIsHead() {
	     return is_head_;
	 }
	
	 /**
	  * Head 데이타 존재 여부 셋팅
	  */
	 public void setIsHead(boolean is_head) {
	     this.is_head_ = is_head;
	 }
	
	 /**
	  * Title 데이타 존재 여부 가져오기
	  */
	 public boolean getIsTitle() {
	     return is_title_;
	 }
	
	 /**
	  * Title 데이타 존재 여부 셋팅
	  */
	 public void setIsTitle(boolean is_title) {
	     this.is_title_ = is_title;
	 }
	 
	 /**
	  * Table 데이타 존재 여부 가져오기
	  */
	 public boolean getIsTable() {
	     return is_table_;
	 }
	
	 /**
	  * Table 데이타 존재 여부 셋팅
	  */
	 public void setIsTable(boolean is_table) {
	     this.is_table_ = is_table;
	 }
	 
	 /**
	  * Table 문자형 데이타 존재 여부 가져오기
	  */
	 public boolean getIsTableString() {
	     return is_table_string_;
	 }
	
	 /**
	  * Table 문자형 데이타 존재 여부 셋팅
	  */
	 public void setIsTableString(boolean is_table_string) {
	     this.is_table_string_ = is_table_string;
	 }
	
	 /**
	  * 데이타 포맷 존재 여부 가져오기
	  */
	 public boolean getIsDataFormat() {
	     return is_data_format_;
	 }
	 
	 /**
	  * Table 데이타 포맷 존재 여부 셋팅
	  */
	 public void setIsDataFormat(boolean is_data_format) {
	     this.is_data_format_ = is_data_format;
	 }

	 /**
	  * 암호화 여부 가져오기
	  */
	 public boolean getIsEncrypt() {
	     return is_encrypt_;
	 }	 
	 
	 /**
	  * 암호화 여부 셋팅
	  */
	 public void setIsEncrypt(boolean is_encrypt) {
	     this.is_encrypt_ = is_encrypt;
	 }
}