package kr.co.raon.commons;

import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.*;
import oauth.signpost.http.HttpParameters;


abstract public class AbstractOAuthHandler {
	private static final Logger logger = LoggerFactory.getLogger(AbstractOAuthHandler.class);
	private static boolean debugFlag = false;

	protected String requestTokenUrl;
	protected String authorizeUrl;
	protected String accessTokenUrl;
	protected String callbackUrl;
	protected String consumerKey;
	protected String consumerSecret;
	protected String accessToken;
	protected String tokenSecret;

	protected OAuthConsumer consumer;
	protected OAuthProvider provider;
	protected Map<String,String> customOAuthParams = new HashMap<String,String>();
	
	/** OAuthHandler의 상태
	 *  uninitialized 			: 초기화 함수 (initialize) 호출을 해야 함.
	 *  authenticated		: oauth 인증을 받은 상태
	 *  require_request		: requestToken 요청이 필요함.
	 *  require_authorize	: authorize url 에서 인증이 필요함.
	 *  require_access		: accessToken 요청이 필요함.
	 */
	protected String oauthStatus;
	protected String refererUrl = "";
	
	public AbstractOAuthHandler() {
		oauthStatus = "uninitialized";
	}
	
	public boolean isAuthentication()
	{
		return oauthStatus.equals("authenticated");
	}
	
	public String getOauthStatus() {
		return oauthStatus;
	}

	public void setOauthStatus(String oauthStatus) {
		this.oauthStatus = oauthStatus;
	}

	public void putCustomOAuthParams(String key,String value)
	{
		customOAuthParams.put(key,value);
	}
	
	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public String getRequestTokenUrl() {
		return requestTokenUrl;
	}

	public void setRequestTokenUrl(String requestTokenUrl) {
		this.requestTokenUrl = requestTokenUrl;
	}

	public String getAuthorizeUrl() {
		return authorizeUrl;
	}

	public void setAuthorizeUrl(String authorizeUrl) {
		this.authorizeUrl = authorizeUrl;
	}

	public String getAccessTokenUrl() {
		return accessTokenUrl;
	}

	public void setAccessTokenUrl(String accessTokenUrl) {
		this.accessTokenUrl = accessTokenUrl;
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenSecret() {
		return tokenSecret;
	}

	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}

	public OAuthConsumer getConsumer() {
		return consumer;
	}

	public void setConsumer(OAuthConsumer consumer) {
		this.consumer = consumer;
	}
	
	public void setTokenWithSecret(String accessToken,String tokenSecret)
	{
		this.accessToken = accessToken;
		this.tokenSecret = tokenSecret;
		
		HttpParameters additionalParameters = new HttpParameters();
		
		for (Entry<String,String> entry : customOAuthParams.entrySet()) {
			additionalParameters.put(entry.getKey(),entry.getValue());
		    consumer.setAdditionalParameters(additionalParameters);
		}
	    consumer.setTokenWithSecret(this.accessToken,this.tokenSecret);
	    
	    oauthStatus = "authenticated";
	}

	public void initialize()
	{
		consumer = new CommonsHttpOAuthConsumer(consumerKey,consumerSecret);
		provider = new CommonsHttpOAuthProvider(requestTokenUrl,accessTokenUrl,authorizeUrl);
		
		oauthStatus = "require_request";
		
		if( accessToken != null && tokenSecret != null ) {
			setTokenWithSecret(accessToken,tokenSecret);
		}
		
		if ( debugFlag ) logger.debug("");
	}
	
	abstract public String get(String urlApi, Map<String,String> params) throws Exception;
}
