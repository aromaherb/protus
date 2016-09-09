package kr.co.raon.commons.web;

import java.io.Serializable;

public class PagingInfo implements Serializable {
	
	private static final long serialVersionUID = -4111825683577917264L;
	
	private int m_nCntPerPage = 15;
	private int m_nCntPaging = 10;
	private int m_nCurPage;
	private int m_nCntTotal;
	
	public PagingInfo() {}
	public PagingInfo(int nCurPage) {
		m_nCurPage = nCurPage;
	}
	
	public int 	getCntPerPage() 				{ return m_nCntPerPage; }
	public void setCntPerPage(int nValue) 		{ m_nCntPerPage = nValue; }
	public void setCntPerPage(Object nValue) 	{ if ( nValue != null ) { m_nCntPerPage = Integer.parseInt(String.valueOf(nValue)); } }
	public int 	getCntPaging() 					{ return m_nCntPaging; }
	public void setCntPaging(int nValue)		{ m_nCntPaging = nValue; }
	public void setCntPaging(Object nValue)		{ if ( nValue != null ) { m_nCntPaging = Integer.parseInt(String.valueOf(nValue)); } }
	public int 	getCntTotal() 					{ return m_nCntTotal; }
	public void setCntTotal(int nValue) 		{ m_nCntTotal = nValue; }
	public void setCntTotal(Object nValue) 		{ if ( nValue != null ) { m_nCntTotal = Integer.parseInt(String.valueOf(nValue)); } }
	
	public int 	getCurPage() 				
	{
		if(m_nCurPage > getCntTotalPaging()) m_nCurPage = getCntTotalPaging();
		return m_nCurPage; 
	}

	public void setCurPage(int nValue) 		
	{ 
		m_nCurPage = nValue;
		if(m_nCurPage <= 0 ) m_nCurPage = 1;
	}
	
	public void setCurPage(Object nValue) 		
	{ 
		if ( nValue == null || isEmpty(String.valueOf(nValue)) ) {
			m_nCurPage = 1;	
		} else {
			m_nCurPage = Integer.parseInt(String.valueOf(nValue));
		}
		
		if ( m_nCurPage <= 0 ) m_nCurPage = 1;
	}	
	
	public void setCurPage(String value)
	{
		if ( value == null || isEmpty(value) ) value = "1";
		m_nCurPage = Integer.parseInt(value);
		if(m_nCurPage <= 0) m_nCurPage = 1;
	}
	
	public int getCntTotalPaging()
	{
		return (int)Math.ceil((double)getCntTotal() / (double)getCntPerPage()); 
	}

	public int getStartPage()
	{
		int startPage = ((int)Math.ceil((double)getCurPage() / (double)getCntPaging() ) -1 ) * getCntPaging() + 1;
		if ( startPage < 0 ) startPage = 1;
		
		return startPage;
	}
	
	public int getEndPage()
	{
		int endPage = ((int)Math.ceil((double)getCurPage() / (double)getCntPaging() ) -1 ) * getCntPaging() + getCntPaging();
		if ( endPage <= 0 ) endPage = 1;
		
		return endPage;
	}
	
	public int getOffset()
	{
		return (getCurPage()-1)*getCntPerPage();
	}
	
	public boolean isEmpty(String strData) {
		boolean flag = false;
		
		if ( strData != null && !"".equals(strData) ) {
			flag = false;
		} else {
			flag = true;
		}
		
		return flag;		
	}	
}
