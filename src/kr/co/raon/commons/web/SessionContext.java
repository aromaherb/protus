package kr.co.raon.commons.web;
 
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 세션정보 관리 클래스
 * @author wicked
 * @since 2012.03.11
 * @see kr.co.raon.common.UserInfo
 */

@Scope(value="session")
@Component("sessionContext")
public class SessionContext implements Serializable {
	private static final long serialVersionUID = 3463681356366811431L;
	
	private boolean authenticated;
	private UserInfo userInfo;
	private AuthInfo authInfo;
	private BoardInfo boardInfo;
	private Map<String,String> accessAuth;
	private List<Map<String,String>> menuAuth;
	private String referer;
	private String url;
	private String pageTitle;
	private String pageNavi;
	private String menuId;
	private String parentMenuId;
	private String menuLink;
	private String isTabYn;
	private String isAdminMenu;
	private String menuOrderby;
	private String accesstime;
	private String adminYn;
	
	public String getAccesstime() 						{ return accesstime;	}
	public void setAccesstime(String accesstime) 		{ this.accesstime = accesstime; }
	
	public String getReferer() 						{ return referer;	}
	public void setReferer(String referer) 		{ this.referer = referer; }

	public String getUrl() 						    { return url;	}
	public void setUrl(String url) 		        { this.url = url; }
	
	public String getPageTitle() 					{ return pageTitle;	}
	public void setPageTitle(String pageTitle) 	{ this.pageTitle = pageTitle; }

	public String getPageNavi() 					{ return pageNavi;	}
	public void setPageNavi(String pageNavi) 		{ this.pageNavi = pageNavi; }
	
	public UserInfo getUserInfo()					{ return userInfo; }
	public void setUserInfo(UserInfo value) 		{ userInfo = value; }
	
	public AuthInfo getAuthInfo()					{ return authInfo; }
	public void setAuthInfo(AuthInfo value) 		{ authInfo = value; }
	
	public BoardInfo getBoardInfo()					{ return boardInfo; }
	public void setBoardInfo(BoardInfo value) 		{ boardInfo = value; }

	public Map<String, String> getAccessAuth() 		{ return accessAuth;}
	
	public List<Map<String, String>> getMenuAuth() { return menuAuth; }
	
	public String getMenuId() 						{ return menuId;	}
	public void setMenuId(String menuId) 			{ this.menuId = menuId; }
	
	public String getParentMenuId() 						{ return parentMenuId;	}
	public void setParentMenuId(String parentMenuId) 			{ this.parentMenuId = parentMenuId; }
	
	public String getMenuLink() 					{ return menuLink;	}
	public void setMenuLink(String menuLink) 		{ this.menuLink = menuLink; }
	
	public String getIsTabYn() 						{ return isTabYn;	}
	public void setIsTabYn(String isTabYn) 		{ this.isTabYn = isTabYn; }
	
	public String getMenuOrderby() 					{ return menuOrderby;	}
	public void setMenuOrderby(String menuOrderby)	{ this.menuOrderby = menuOrderby; }
	
	public String getIsAdminMenu() 					{ return isAdminMenu;	}
	public void setIsAdminMenu(String isAdminMenu)	{ this.isAdminMenu = isAdminMenu; }
	
	//adlo 경로로 들어와야만 입력된다.
	public String getAdminYn() 					{ return adminYn;	}
	public void setAdminYn(String adminYn)	{ this.adminYn = adminYn; }
	
	/**
	 * 해당 사용자의 기능별 권한정보를 SET<br/>
	 * 로그인 후 사용자 정보와 함께 권한 정보를 함께 설정한다.<br/>
	 * @param accessAuth 기능별 접근권한 정보
	 */
	public void setAccessAuth(Map<String, String> accessAuth) {
		this.accessAuth = accessAuth;
	}

	/**
	 * 해당 사용자의 기능별 권한정보를 SET<br/>
	 * 로그인 후 사용자 정보와 함께 권한 정보를 함께 설정한다.<br/>
	 * @param accessAuth 기능별 접근권한 정보
	 */
	public void setMenuAuth(List<Map<String, String>> menuAuth) {
		this.menuAuth = menuAuth;
	}	
	
	public boolean isAuthenticated() 			{ return authenticated; }
	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}
	
	/**
	 * 기능별 권한 여부 체크
	 * @param function 기능
	 * @param mode 접근권한 1:READ 2:WRITE 4:EXECUTE
	 * @return 권한 여부
	 */
	public boolean isAuthorized(String function,int mode)
	{
		int access = 0;
		if( accessAuth.get(function) != null)
			access = Integer.parseInt(accessAuth.get(function));
		
		// 테스트를 위해서 7로 고정
		access = 7;
		
		if( (mode & access) > 0) return true;
		
		return false;
	}
}
