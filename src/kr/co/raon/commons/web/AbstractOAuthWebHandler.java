package kr.co.raon.commons.web;

import java.util.Map;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.raon.commons.AbstractOAuthHandler;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import oauth.signpost.http.HttpParameters;

abstract public class AbstractOAuthWebHandler extends AbstractOAuthHandler {
	private static final Logger logger = LoggerFactory.getLogger(AbstractOAuthWebHandler.class);

	public AbstractOAuthWebHandler() {
		super();
	}

	public boolean oauthProcess(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if(oauthStatus.equals("uninitialized")) {
			initialize();
		}
		if(isAuthentication()) return true;

		String[] addParams = new String[customOAuthParams.size()*2];

		int idx=0;
		for (Entry<String,String> entry : customOAuthParams.entrySet()) {
			addParams[idx++] = entry.getKey();
			addParams[idx++] = entry.getValue();
		}

		if(!oauthStatus.equals("require_access")) {
			refererUrl = request.getRequestURL().toString();
			
			String authUrl = provider.retrieveRequestToken(consumer,callbackUrl,addParams);

			logger.debug("authUrl=" + authUrl);
			logger.debug("token=" + consumer.getToken() + ",secret=" + consumer.getTokenSecret());
			
			oauthStatus = "require_authorize";
			response.sendRedirect(authUrl);
			
			return false;
		}
		else if(oauthStatus.equals("require_access")) {
			String oauth_verifier = request.getParameter("oauth_verifier");

			provider.retrieveAccessToken(consumer, oauth_verifier, addParams);
			accessToken = consumer.getToken();
			tokenSecret = consumer.getTokenSecret();

			logger.debug("token=" + consumer.getToken() + ",secret=" + consumer.getTokenSecret());
		
			HttpParameters additionalParameters = new HttpParameters();
			for (Entry<String,String> entry : customOAuthParams.entrySet()) {
				additionalParameters.put(entry.getKey(),entry.getValue());
			    consumer.setAdditionalParameters(additionalParameters);
			}
			oauthStatus = "authenticated";
			response.sendRedirect(refererUrl);

			return true;
		}
		
		return true;
	}

	abstract public String get(String urlApi, Map<String,String> params) throws Exception;
}
