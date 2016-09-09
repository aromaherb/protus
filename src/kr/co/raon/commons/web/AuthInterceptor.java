package kr.co.raon.commons.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 인증, 권한 처리를 위한 인터셉트 클래스
 * @author wicked
 * @since 2012.03.11
 * @see kr.co.raon.common.SessionContext
 * @see kr.co.raon.common.BaseController 
 *  
 */

@SuppressWarnings({"rawtypes"})
public class AuthInterceptor  extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
	
	@Resource(name="sessionContextFactory") ObjectFactory sessionContextFactory;
	
	/** 권한 체크를 포함할 컨트롤 클래스 목록 */
	private List<String> includeTarget;
	/** 권한 체크를 배제할 컨트롤 클래스 목록 */
	private List<String> excludeTarget;
	/** 로그인 페이지 */
	private String loginUrl;
	/** 로그인 후 referer 페이지 이동 여부 */
	private boolean useReferer;
	/** referer:false 일 경우 이동 페이지 */
	private String defaultTargetUrl;
	/** 권한 오류 페이지 */
	private String unauthorizedUrl;
	
	public void setIncludeTarget(List<String> target) 
	{
		includeTarget = target;
	}
	
	public void setExcludeTarget(List<String> target)
	{
		excludeTarget = target;
	}
	
	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public boolean isUseReferer() {
		return useReferer;
	}

	public void setUseReferer(boolean useReferer) {
		this.useReferer = useReferer;
	}

	public String getDefaultTargetUrl() {
		return defaultTargetUrl;
	}

	public void setDefaultTargetUrl(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}

	public String getUnauthorizedUrl() {
		return unauthorizedUrl;
	}

	public void setUnauthorizedUrl(String unauthorizedUrl) {
		this.unauthorizedUrl = unauthorizedUrl;
	}

	/**
	 * 컨트롤 클래스 실행 전 인터셉트<br/>
	 * 인증 체크 -> 로그인 페이지<br/>
	 * 권한 체크 -> 권한 오류 페이지<br/>
	 * @param request	HttpServletRequest
	 * @param response	HttpServletResponse
	 * @param handler	컨트롤 또는 핸들러(HandlerMethod) 클래스
	 * @return true:continue <br/>
	 *          false:stop <br/>
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		BaseController controller = null;
		String contextPath = request.getContextPath();
		
		/** get Controller Class */
		if ( handler.getClass().getName().matches(".*HandlerMethod$") ) {
			controller = (BaseController)((org.springframework.web.method.HandlerMethod)handler).getBean();
		} else {
			controller = (BaseController)handler;
		}
		
		String strClassName = controller.getClass().getName();

		/** check includeTarget class */
		boolean bInclude = false;
		for(String target: includeTarget) {
			if(strClassName.matches(target)) {
				bInclude = true;
				break;
			}
		}
		
		if(bInclude == false) return true;

		/** check excludeTarget class */
		boolean bExclude = false;
		for(String target: excludeTarget){
			if(strClassName.matches(target)) {
				bExclude = true;
				break;
			}
		}
		
		SessionContext session = (SessionContext)sessionContextFactory.getObject();

		/** 현재 URL 등록 */
		session.setUrl(request.getRequestURL().toString());

		if(bExclude == true) return true;
		
		/** contextPath Action */
		if ( !"/".equals(contextPath) ) {
			if ( !getDefaultTargetUrl().startsWith(contextPath) ) {
				setDefaultTargetUrl(contextPath + getDefaultTargetUrl());
				setLoginUrl(contextPath + getLoginUrl());
				setUnauthorizedUrl(contextPath + getUnauthorizedUrl());
			}
		}
		
		/** check Authentication (for login) */
		if ( !session.isAuthenticated() ) {
			if ( useReferer ) {
				session.setReferer(request.getRequestURI());
			} else {
				session.setReferer(defaultTargetUrl);
			}
			
			response.sendRedirect(loginUrl);
			logger.debug("require Authenticated : redirect login url=" + loginUrl);
			return false;
		}
		
		/** check Authorization (read,write,execute) */
		int mode = controller.getAccessMode(request.getRequestURI());
		String function = strClassName.toLowerCase().replaceAll(".+\\.", "").replaceAll("controller$", "");
		
		if( !session.isAuthorized(function,mode)) {
			response.sendRedirect(unauthorizedUrl);
			logger.debug("unauthorized: className=" + strClassName);
			return false;
		}
		
		return true;
    }
}
