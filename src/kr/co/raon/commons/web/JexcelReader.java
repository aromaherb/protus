package kr.co.raon.commons.web;

import java.io.*;
import java.util.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;


// 클래스 선언
// 엑셀2007 파일을 셀단위로 읽어 데이터형 변환등을 하여 엑세스하는 클래스
public class JexcelReader {

	// 엑셀 파일 관련 선언
	private String fileName_		= "";	// 엑셀 파일명
	//private XSSFWorkbook workBook_	= null; // 엑셀
	//private XSSFSheet curSheet_		= null;	// 현재 Sheet
	private Workbook workBook_	= null; // 엑셀
	private Sheet curSheet_		= null;	// 현재 Sheet
	private int nSheets_	= 0;			// 총 Sheet 수
	private int curSheetNo_ = 0;			// 현재 Sheet

	// 생성자
	public JexcelReader() {
		fileName_ = "";
	}

	public JexcelReader(String filename) {
		setExcelFile(filename);
	}

	public String getfileName() {
		return fileName_;
	}

	public void setfileName(String filename) {
		fileName_ = filename;
	}

	public void closeFile() {

		//workBook_.close();

		curSheet_ = null;
		workBook_ = null;
		nSheets_  = 0;
	}

	public void setcurSheet(int sheetno) {
		setcurSheetNo(sheetno);
		curSheet_	= workBook_.getSheetAt(curSheetNo_);
	}

	public void setNumSheets(int nsheets) {
		nSheets_ = nsheets;
	}

	public int getNumSheets() {
		return nSheets_;
	}

	public void setcurSheetNo(int sheetno) {
		curSheetNo_ = sheetno;
	}

	public int getcurSheetNo() {
		return curSheetNo_;
	}

	public String getcurSheetName() {
		//return curSheet_.getName();
		return curSheet_.getSheetName();
	}

	public Sheet getSheet()
	{
		return curSheet_;
	}

	public int getColumns() {
		//return curSheet_.getColumns();
		// 우라질레이션 이거 대체함수 없다.
		// 현제 알수 있는 방법은 모든 로우를 돌며 비교해서 제일 큰 Column 값을 가져오는 방법뿐.
		// 부하가 많이 걸리면 다른 방법을 찾자

		int maxCellNum = 0;

		int rowsphy = curSheet_.getPhysicalNumberOfRows(); // Row에 값이 있는 넘만 카운팅 중간에 빈칸이면 카운팅 하지 않느다.
		if( rowsphy == 0 )
			return maxCellNum;

		int rows = curSheet_.getLastRowNum() + 1; // 마지막 row 수    실제 20줄이면 19줄나온다. 0~19 개념

		for( int ii=0; ii<rows; ++ii ) {
			//XSSFRow row = curSheet_.getRow(ii);
			Row row = curSheet_.getRow(ii);

			if( row == null )
				continue;

			int cells = row.getPhysicalNumberOfCells(); // row 의 column 수
			if( cells != 0 )
				cells = row.getLastCellNum() + 1;

			if( maxCellNum < cells )	// 가장 큰 cell(column)값 저장
				maxCellNum = cells;
		}

		return maxCellNum;
	}

	public int getRows() {
		int rows = curSheet_.getPhysicalNumberOfRows();
		if( rows == 0 )
			return rows;
		else
			rows = curSheet_.getLastRowNum() + 1;  // 마지막 row 수    실제 20줄이면 19줄나온다. 0~19 개념

		return rows;
	}
	// 엑셀파일을 읽기위한 초기화 이다.
	// filename 은 절대경로를 포함한 FullPath 와 파일명이다.
	public void setExcelFile(String filename) {
		setfileName(filename);

		try {

			File inputFile = new File( filename );
			FileInputStream fileIStream = new FileInputStream( inputFile );
			workBook_ = WorkbookFactory.create( fileIStream );

			//workBook_	= new XSSFWorkbook();

			//Workbook workbook = new XSSFWorkbook("D:/XexcelReaderTest/(확정)2008손익계산서_실적(r091210).xlsx");

			nSheets_	= workBook_.getNumberOfSheets();
			if (nSheets_ > 0 )
			{
				curSheetNo_ = 0;
				curSheet_	= workBook_.getSheetAt(curSheetNo_);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// cell 이 날짜형인지 판단
	public boolean isDateCell(int col,int row) {
		boolean ret = false;
		/*
		Cell cell = curSheet_.getCell(col,row);
		if( (cell.getType() == CellType.DATE) || ( cell.getType() == CellType.DATE_FORMULA) ) {
			ret = true;
		}
		*/

		//XSSFRow xrow = curSheet_.getRow(row);
		Row xrow = curSheet_.getRow(row);
		if(xrow == null )  return ret;
		//XSSFCell xcell = xrow.getCell(col);
		Cell xcell = xrow.getCell(col);

		if(( xcell != null ) && ( xcell.getCellType() == XSSFCell.CELL_TYPE_FORMULA ))	// 비슷한 타입이 이넘 밖에 없는데 DATE 도 여기에 포함되겠지?
			ret = true;

		return ret;
	}
	// 날짜형 cell
	public Date getDateCell(int col,int row) {
		Date date = new Date();
		try {
			/*
			Cell cell = curSheet_.getCell(col,row);
			if( (cell.getType() == CellType.DATE) || ( cell.getType() == CellType.DATE_FORMULA) ) {
				DateCell datecell = (DateCell)cell;
				date = datecell.getDate();
			}
			else {
				System.out.println(" 날짜형이 아닙니다. 확인하십시요!! ");
			}
			*/

			//XSSFRow xrow = curSheet_.getRow(row);
			Row xrow = curSheet_.getRow(row);
			//XSSFCell xcell = xrow.getCell(col);
			if(xrow == null )  {
				System.out.println(" 날짜형이 아닙니다. 확인하십시요!! ");
				return date;
			}
			Cell xcell = xrow.getCell(col);
			if(xcell == null) {
				System.out.println(" 날짜형이 아닙니다. 확인하십시요!! ");
			}else if( xcell.getCellType() == XSSFCell.CELL_TYPE_FORMULA )	// 비슷한 타입이 이넘 밖에 없는데 DATE 도 여기에 포함되겠지?
				date = xcell.getDateCellValue();
			else
				System.out.println(" 날짜형이 아닙니다. 확인하십시요!! ");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally	 {
			return date;
		}
	}

	// cell 이 숫자형 인지 판단 : int double 포함
	public boolean isNumberCell(int col,int row) {
		boolean ret = false;

		/*
		Cell cell = curSheet_.getCell(col,row);
		if ((cell.getType() == CellType.NUMBER_FORMULA ) || (cell.getType() == CellType.NUMBER)) {
			ret = true;
		}
		*/

		//XSSFRow xrow = curSheet_.getRow(row);
		Row xrow = curSheet_.getRow(row);
		if(xrow == null ) return ret;
		//XSSFCell xcell = xrow.getCell(col);
		Cell xcell = xrow.getCell(col);
		if (xcell == null)
			return ret;
		if( xcell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC || xcell.getCellType() == XSSFCell.CELL_TYPE_FORMULA  )	// 비슷한 타입이 이넘 밖에 없는데 DATE 도 여기에 포함되겠지?
			ret = true;

		return ret;
	}

	// 숫자형 cell 또는 계산식
	public int getIntCell(int col,int row) {
		int rtn = 0;
		try {
			/*
			Cell cell = curSheet_.getCell(col,row);
			if ((cell.getType() == CellType.NUMBER_FORMULA ) || (cell.getType() == CellType.NUMBER)) {
				NumberCell numbercell = (NumberCell)cell;
				rtn = (int)numbercell.getValue();
			}
			else {
				System.out.println(" 숫자형이 아닙니다. 확인하십시요!! ");
			}
			*/

			//XSSFRow xrow = curSheet_.getRow(row);
			Row xrow = curSheet_.getRow(row);
			//XSSFCell xcell = xrow.getCell(col);
			if(xrow == null ) return rtn;
			Cell xcell = xrow.getCell(col);
			if(xcell == null)
				rtn = 0;
			else if( xcell.getCellType() == XSSFCell.CELL_TYPE_FORMULA )	// 비슷한 타입이 이넘 밖에 없는데 DATE 도 여기에 포함되겠지?
				rtn = (int)xcell.getNumericCellValue();
			else if( xcell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC )
				rtn = (int)xcell.getNumericCellValue();
			else
				System.out.println(" 숫자형이 아닙니다. 확인하십시요!! ");

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally	 {
			return rtn;
		}
	}

	// 숫자형 소수점 , ciphers : 소수점 자릿수
	public double getDoubleCell(int col,int row,int ciphers) {
		double rtn = -1.0;
		try {
			/*
			Cell cell = curSheet_.getCell(col,row);
			double value;
			long lvalue;
			if ((cell.getType() == CellType.NUMBER_FORMULA ) || (cell.getType() == CellType.NUMBER)) {
				NumberCell numbercell = (NumberCell)cell;
				value = numbercell.getValue();
				String str_ciphers = "" + ciphers;

				value = Double.parseDouble(String.format("%." + str_ciphers +"f", value));

				//lvalue = Math.round(value * (Math.pow(10,ciphers)));
				//value = (double)lvalue / (Math.pow(10,ciphers));

				rtn = value;
			}
			else {
				System.out.println(" 숫자형이 아닙니다. 확인하십시요!! ");
			}
			*/

			double value;

			//XSSFRow xrow = curSheet_.getRow(row);
			Row xrow = curSheet_.getRow(row);
			//XSSFCell xcell = xrow.getCell(col);
			if(xrow == null ) return rtn;
			Cell xcell = xrow.getCell(col);
			if(xcell == null)
				rtn = 0.0;
			else if( xcell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC )	{ // 비슷한 타입이 이넘 밖에 없는데 DATE 도 여기에 포함되겠지?
				value = xcell.getNumericCellValue();
				String str_ciphers = "" + ciphers;

				value = Double.parseDouble(String.format("%." + str_ciphers +"f", value));

				rtn = value;
			}
			else if( xcell.getCellType() == XSSFCell.CELL_TYPE_FORMULA ) {
				value = xcell.getNumericCellValue();
				String str_ciphers = "" + ciphers;

				value = Double.parseDouble(String.format("%." + str_ciphers +"f", value));

				rtn = value;
			}
			else {
				System.out.println(" 숫자형이 아닙니다. 확인하십시요!! ");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally	 {
			return rtn;
		}
	}

	// cell 이 문자열 인지 판단
	public boolean isStringCell(int col,int row) {
		boolean ret = false;
		/*
		Cell cell = curSheet_.getCell(col,row);
		if(  !(cell.getType() == CellType.ERROR) ) {
			ret = true;
		}
		*/

		//XSSFRow xrow = curSheet_.getRow(row);
		Row xrow = curSheet_.getRow(row);
		if(xrow == null ) return ret;
		//XSSFCell xcell = xrow.getCell(col);
		Cell xcell = xrow.getCell(col);

		if( xcell == null )
			return ret;

		if( !(xcell.getCellType() == XSSFCell.CELL_TYPE_ERROR) ) {
			ret = true;
		}

		return ret;
	}

	// 문자열
	public String getStringCell(int col,int row,Boolean trim) {
		String rtn = "";
		double dValue = 0F;
		
		try {
			rtn = "";
			/*
			Cell cell = curSheet_.getCell(col,row);
			String value = "";

			if(  !(cell.getType() == CellType.ERROR) ) {
				value = cell.getContents();
				if (trim) {
					value = value.replace(" " ,"");
				}
				rtn = value;
			}
			else {
				System.out.println(" 문자형이 아닙니다. 확인하십시요!! ");
			}
			*/

			//XSSFRow xrow = curSheet_.getRow(row);
			Row xrow = curSheet_.getRow(row);
			if(xrow == null ) return rtn;
			//XSSFCell xcell = xrow.getCell(col);
			Cell xcell = xrow.getCell(col);
			String value = "";
			if( xcell == null ) {
				rtn = value;
			} else if( (xcell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) || (xcell.getCellType() == XSSFCell.CELL_TYPE_FORMULA) ) {
				value = "" + (double)xcell.getNumericCellValue();
				
				if ( value.indexOf("E") > -1 ) {
					value = Long.toString((long)xcell.getNumericCellValue());					
				}
				
				//dValue = (long)xcell.getNumericCellValue();
				//value  = Double.toString(dValue);
				
				if ( ".0".equals(value.substring(value.length()-2, value.length())) ) {
					value = value.substring(0, value.length()-2);
				}

				
				if (trim) {
					value = value.replace(" " ,"");
				}
				rtn = value;
			}
			else if( !(xcell.getCellType() == XSSFCell.CELL_TYPE_ERROR) ) {
				value = xcell.getStringCellValue();
				if (trim) {
					value = value.replace(" " ,"");
				}
				rtn = value;
			}
			else {
				System.out.println(" 문자형이 아닙니다. 확인하십시요!! ");
			}


		} catch (Exception e) {
			e.printStackTrace();
		}finally	 {
			rtn = rtn.replaceAll("_x000D_", "").trim();
			return rtn;
		}
	}

	/**
	 * 셀에 문자열 입력
	 * @param col
	 * @param row
	 * @param data
	 * @param trim
	 */
	public void setStringCell(int col, int row, String data, Boolean trim) {
		String rtn = "";

		try {
			rtn = "";

			Row xrow = curSheet_.getRow(row);
			if(xrow == null ) return;

			Cell xcell = xrow.getCell(col);


		    if (xcell == null)
		    	xcell = xrow.createCell(col);

			xcell.setCellValue(data);

		} catch (Exception e) {
			// 에러
			e.printStackTrace();
		} finally {

		}
	}

	/**
	 * row 삭제
	 * @param row
	 */
	public void deleteRow(int row) {
		try {
			Row xrow = curSheet_.getRow(row);
			if(xrow == null ) return;

			getSheet().removeRow(xrow);
		} catch (Exception e) {
			// 에러
			//e.printStackTrace();
		} finally {

		}
	}

	/**
	 * 엑셀 저장
	 * @param outFileName
	 * @throws IOException
	 */
	public void excelSave(String outFileName) throws IOException {
		try {
			FileOutputStream fileOut = new FileOutputStream(outFileName);
		    workBook_.write(fileOut);
		    fileOut.close();
		} catch (FileNotFoundException e) {
			//
		}
	}
}