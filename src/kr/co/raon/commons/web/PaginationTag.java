package kr.co.raon.commons.web;

import java.io.IOException;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
 
public class PaginationTag extends TagSupport {

	private static final long serialVersionUID = -1014840972464662621L;

	private PagingInfo m_pagingInfo;
	private String m_strType;
	private String m_strFunction;
	
	public void setPagingInfo(PagingInfo value)	{ this.m_pagingInfo = value; }		
	public PagingInfo getPagingInfo()			{ return this.m_pagingInfo; }
	public void setType(String value)			{ this.m_strType = value; }
	public String getType()						{ return this.m_strType; }
	public void setFunction(String value)		{ this.m_strFunction = value; }
	public String getFunction()					{ return this.m_strFunction; }
	
	/**
	 * 페이지 네비게이션 (jQuery 이용)
	 * @return
	 * @throws JspTagException
	 */
	public int doStartTag() throws JspTagException {
		
		try {
			StringBuffer pagination = new StringBuffer();;
			
			// pagination.append("<div class='paging_icon_block'><a href='#' class='" + this.getFunction() + "' page='1' ><img src='../images/icon/paging_left.png' alt='처음' /></a></div>");
			
			pagination.append("<a href='#' class='" + this.getFunction() + "' page='1' >[처음]</a>");
			
			if(this.m_pagingInfo !=null) {
				if(this.m_pagingInfo.getStartPage() > this.m_pagingInfo.getCntPaging() ) {
					pagination.append("<a href='#' class='" + this.getFunction() + "' page='" + (this.m_pagingInfo.getStartPage() - 1) + "' >[이전]</a> ");
				}
			
				int nEndPage = this.m_pagingInfo.getEndPage();
				
				if(nEndPage > this.m_pagingInfo.getCntTotalPaging())
					nEndPage = this.m_pagingInfo.getCntTotalPaging();
				
				if ( nEndPage <= 1 ) {
					pagination.append("1");
				} else {
					for(int i = this.m_pagingInfo.getStartPage() ; i <= nEndPage ; i++) { 
						pagination.append("<a href='#' class='" + this.getFunction() + "' page='" + i + "' >");
						if(i == this.m_pagingInfo.getCurPage() ) pagination.append("" + i + "");
						else	pagination.append("" + i + "");
						pagination.append("</a> ");
					}
					
				}
				
				
				if(this.m_pagingInfo.getEndPage() <  this.m_pagingInfo.getCntTotalPaging()) {
					pagination.append("<a href='#' class='" + this.getFunction() + "' page='" + (this.m_pagingInfo.getEndPage() + 1) + "' >[다음]</a> ");
				}	
				//pagination.append("<div class='paging_icon_block'><a href='#' class='" + this.getFunction() + "' page='" + this.m_pagingInfo.getCntTotalPaging() + "' ><img src='../images/icon/paging_right.png' alt='다음' /></a></div>");
				pagination.append("<a href='#' class='" + this.getFunction() + "' page='" + this.m_pagingInfo.getCntTotalPaging() + "' >[끝]</a>");
			}else {
				pagination.append("<div class='paging_block'>1</div>");
			}
			System.out.println(pagination);
			pageContext.getOut().print(pagination);
			
		} catch(IOException ioe) {}
		
		return(EVAL_BODY_INCLUDE);
	}

	public int doEndTag() {
		return(EVAL_PAGE);
	}
	
	/**
	 * 페이지 네비게이션 (javascript 이용)
	 * @return
	 * @throws JspTagException
	 */
	public int doStartTagJavaScript() throws JspTagException {
		
		try {
			StringBuffer pagination = new StringBuffer();;
			
			pagination.append("<a href='javascript:" + this.getFunction() +"(1);'>[처음]</a>");
			
			if(this.m_pagingInfo !=null ) {
				if(this.m_pagingInfo.getStartPage() > this.m_pagingInfo.getCntPaging() ) {
					pagination.append("<a href='javascript:"+ this.getFunction() +"(" + (this.m_pagingInfo.getStartPage() - 1) + ");'>[이전]</a> ");
				}
		
				int nEndPage = this.m_pagingInfo.getEndPage();
				if(nEndPage > this.m_pagingInfo.getCntTotalPaging())
					nEndPage = this.m_pagingInfo.getCntTotalPaging();
				
				if ( nEndPage <= 1 ) {
					pagination.append("1");
				} else {
					for(int i = this.m_pagingInfo.getStartPage() ; i <= nEndPage ; i++) { 
						pagination.append("<a href='javascript:"+ this.getFunction() + "(" + i + ");'>");
						if(i == this.m_pagingInfo.getCurPage() ) pagination.append("<b>[" + i + "]</b>");
						else	pagination.append("[" + i + "]");
						pagination.append("</a> ");
					}
				}
				
				if(this.m_pagingInfo.getEndPage() <  this.m_pagingInfo.getCntTotalPaging()) {
					pagination.append("<a href='javascript:"+ this.getFunction() +"(" + (this.m_pagingInfo.getEndPage() + 1) + ");'>[다음]</a> ");
				}	
				pagination.append("<a href='javascript:" + this.getFunction() +"(" + this.m_pagingInfo.getCntTotalPaging() + ");'>[마지막]</a>");
			}else {
				pagination.append("1");
			}
			
			pageContext.getOut().print(pagination);
			
		} catch(IOException ioe) {}
		
		return(EVAL_BODY_INCLUDE);		
	}		
	

}
