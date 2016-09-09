package kr.co.raon.commons.util;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class PageNavigation {
	
	public PageNavigation() {
		
		ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
		HttpServletRequest hsr = sra.getRequest();
		
		this.page = "".equals(StringUtil.NVL(hsr.getParameter("page"), "")) ? 1 : Integer.parseInt(StringUtil.NVL(hsr.getParameter("page"), ""));
		this.rows = "".equals(StringUtil.NVL(hsr.getParameter("rows"), "")) ? 20 : Integer.parseInt(hsr.getParameter("rows"));
		this.searchFiled = StringUtil.NVL(hsr.getParameter("searchFiled"), "");
		this.searchValue = StringUtil.NVL(hsr.getParameter("searchValue"), "");

		
	}

	/** navi type */
	private String naviType;
	/** 시작 페이지 넘버 */
	private int firstPageNo;
	/** 마지막 페이지 넘버 */
	private int lastPageNo;
	/** 이전 페이지 넘버 */
	private int prevPageNo;
	/** 다음 페이지 넘버 */
	private int nextPageNo;
	/**  */
	private int beginPageNo;
	/**  */
	private int endPageNo;
	/**  */
	private int beginRowNo;
	/**  */
	private int endRowNo;
	/**  */
	private int totalCnt;
	/** 현제 페이지 */
	private int page;
	/**  */
	private int rows;
	/**  */
	private String searchFiled;
	/**  */
	private String searchValue;
	
	/**
	 * @return the firstPageNo
	 */
	public int getFirstPageNo() {
		return firstPageNo;
	}

	/**
	 * @param firstPageNo the firstPageNo to set
	 */
	public void setFirstPageNo(int firstPageNo) {
		this.firstPageNo = firstPageNo;
	}

	/**
	 * @return the lastPageNo
	 */
	public int getLastPageNo() {
		return lastPageNo;
	}

	/**
	 * @param lastPageNo the lastPageNo to set
	 */
	public void setLastPageNo(int lastPageNo) {
		this.lastPageNo = lastPageNo;
	}

	/**
	 * @return the prevPageNo
	 */
	public int getPrevPageNo() {
		return prevPageNo;
	}

	/**
	 * @param prevPageNo the prevPageNo to set
	 */
	public void setPrevPageNo(int prevPageNo) {
		this.prevPageNo = prevPageNo;
	}

	/**
	 * @return the nextPageNo
	 */
	public int getNextPageNo() {
		return nextPageNo;
	}

	/**
	 * @param nextPageNo the nextPageNo to set
	 */
	public void setNextPageNo(int nextPageNo) {
		this.nextPageNo = nextPageNo;
	}

	/**
	 * @return the beginPageNo
	 */
	public int getBeginPageNo() {
		return beginPageNo;
	}

	/**
	 * @param beginPageNo the beginPageNo to set
	 */
	public void setBeginPageNo(int beginPageNo) {
		this.beginPageNo = beginPageNo;
	}

	/**
	 * @return the endPageNo
	 */
	public int getEndPageNo() {
		return endPageNo;
	}

	/**
	 * @param endPageNo the endPageNo to set
	 */
	public void setEndPageNo(int endPageNo) {
		this.endPageNo = endPageNo;
	}

	/**
	 * @return the beginRowNo
	 */
	public int getBeginRowNo() {
		return beginRowNo;
	}

	/**
	 * @param beginRowNo the beginRowNo to set
	 */
	public void setBeginRowNo(int beginRowNo) {
		this.beginRowNo = beginRowNo;
	}

	/**
	 * @return the endRowNo
	 */
	public int getEndRowNo() {
		return endRowNo;
	}

	/**
	 * @param endRowNo the endRowNo to set
	 */
	public void setEndRowNo(int endRowNo) {
		this.endRowNo = endRowNo;
	}

	/**
	 * @return the totalCnt
	 */
	public int getTotalCnt() {
		return totalCnt;
	}

	/**
	 * @param totalCnt the totalCnt to set
	 */
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}

	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return the rows
	 */
	public int getRwos() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * @return the searchFiled
	 */
	public String getSearchFiled() {
		return searchFiled;
	}

	/**
	 * @param searchFiled the searchFiled to set
	 */
	public void setSearchFiled(String searchFiled) {
		this.searchFiled = searchFiled;
	}

	/**
	 * @return the searchValue
	 */
	public String getSearchValue() {
		return searchValue;
	}

	/**
	 * @param searchValue the searchValue to set
	 */
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	/**
	 * @return the navitype
	 */
	public String getNaviType() {
		return naviType;
	}

	/**
	 * @param navitype the navitype to set
	 */
	public void setNaviType(String naviType) {
		this.naviType = naviType;
	}

	/**
	 * 1. 개요 : 페이지 정보를 입력 받아 게시판 네비 게이션 정보 생성
	 * 2. 처리내용 : 페이지 정보를 입력 받아 게시판 네비 게이션 정보 생성
	 * 3. 입력 Data : int totalCnt, int rows, int page
	 * 4. 출력 Data : 없음
	 * <pre>
	 * @Method Name : setNavigationInfo
	 * </pre>
	 * @param totalCnt
	 * @param pageRowCnt
	 * @param pageNo
	 * @param request
	 */
	public void setNavigationInfo(int totalCnt, int rows, int page){
		this.totalCnt = totalCnt;
		this.rows = rows;
		this.page = page;
		
		
		this.firstPageNo = 1;
		this.lastPageNo = (totalCnt / rows);
		if((totalCnt % rows) > 0){
			lastPageNo++;
		}
		
		this.beginPageNo = ((page - 1) / 10) * 10 + 1;
		this.endPageNo = beginPageNo + 9;
		if(endPageNo > lastPageNo){
			endPageNo = lastPageNo;
		}
		
		this.prevPageNo = beginPageNo - 1;
		if(prevPageNo < firstPageNo){
			prevPageNo = 1;
		}
		this.nextPageNo = endPageNo + 1;
		if(nextPageNo > lastPageNo){
			nextPageNo = lastPageNo;
		}
		
		this.beginRowNo = (page - 1) * rows;
		//orcale or ms-sql
		//this.endRowNo = (page * rows) + 1;
		this.endRowNo = (page * rows);
		//mysql
		//this.endRowNo = rows;
	}
	
	/**
	 * 1. 개요 : 페이지 정보를 입력 받아 게시판 네비 게이션 정보 생성
	 * 2. 처리내용 : 페이지 정보를 입력 받아 게시판 네비 게이션 정보 생성
	 * 3. 입력 Data : int totalCnt
	 * 4. 출력 Data : 없음
	 * <pre>
	 * @Method Name : setNavigationInfo
	 * </pre>
	 * @param totalCnt
	 * @param pageRowCnt
	 * @param pageNo
	 * @param request
	 */
	public void setNavigationInfo(int totalCnt){
		this.totalCnt = totalCnt;
		
		this.firstPageNo = 1;
		this.lastPageNo = (totalCnt / this.rows);
		if((totalCnt % this.rows) > 0){
			lastPageNo++;
		}
		
		this.beginPageNo = ((this.page - 1) / 10) * 10 + 1;
		
		this.endPageNo = this.beginPageNo + 9;
		if(this.endPageNo > this.lastPageNo){
			this.endPageNo = this.lastPageNo;
		}
		
		this.prevPageNo = this.page - 1;
		if(this.prevPageNo < this.firstPageNo){
			prevPageNo = 1;
		}
		this.nextPageNo = this.page + 1;
		if(this.nextPageNo > this.lastPageNo){
			this.nextPageNo = this.lastPageNo;
		}
		
		this.beginRowNo = ((this.page - 1) * this.rows)+1;
		//orcale or ms-sql
		//this.endRowNo = (page * rows) + 1;
		this.endRowNo = (page * rows);
		//mysql
		//this.endRowNo = this.rows;
	}
	
}
