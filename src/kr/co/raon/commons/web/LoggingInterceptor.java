package kr.co.raon.commons.web;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
 
public class LoggingInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

	@SuppressWarnings("unchecked")
	@Override 
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		logger.debug("===================================================================");
        logger.debug("[" + handler + "]");
		logger.debug(request.getRequestURL().toString());
		logger.debug("content-type=" + request.getContentType());
		logger.debug("content-length="+request.getContentLength());

        Enumeration<String> arrHeader =request.getHeaderNames();
		while( arrHeader.hasMoreElements()) {
			String name = arrHeader.nextElement();
			logger.debug("header:" + name + "=" + request.getHeader(name));
		}
		
		Enumeration<String> arrParam = request.getParameterNames();
		while( arrParam.hasMoreElements()) {
			String name = arrParam.nextElement();
			logger.debug("param:" + name + "=" + request.getParameter(name));
		}
		
		
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        return true;
    }
	
	@Override
    public void postHandle( HttpServletRequest request, HttpServletResponse response,
    						Object handler, ModelAndView modelAndView) throws Exception {

        long startTime = (Long)request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;

    	if(modelAndView != null && modelAndView.getModel() != null) 
    		logger.debug(modelAndView.getModel().toString());
        logger.debug("executeTime : " + executeTime + "ms");
		logger.debug("");
    }

	@Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
            					Object handler, Exception ex) throws Exception {
		
    	if(ex != null) logger.debug("exception=" + ex.getMessage());
		logger.debug("===================================================================");
    }
}
