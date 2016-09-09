package kr.co.raon.commons;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map.Entry;
import java.awt.Frame;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import oauth.signpost.http.HttpParameters;

abstract public class AbstractOAuthAppHandler extends AbstractOAuthHandler {
	private static final Logger logger = LoggerFactory.getLogger(AbstractOAuthAppHandler.class);

	protected void oauthProcess() throws Exception
	{
		String[] addParams = new String[customOAuthParams.size()*2];

		int idx=0;
		for (Entry<String,String> entry : customOAuthParams.entrySet()) {
			addParams[idx++] = entry.getKey();
			addParams[idx++] = entry.getValue();
		}
		
		String authUrl = provider.retrieveRequestToken(consumer,callbackUrl,addParams);
		
		oauthStatus = "require_authorize";

		logger.debug("authUrl=" + authUrl);
		logger.debug("token=" + consumer.getToken() + ",secret=" + consumer.getTokenSecret());
		
		boolean browserExec = showInBrowser(authUrl , null);
		if (browserExec == false)
		{
			HashMap<String,String> r = new HashMap<String,String>();
			r.put("result_code", "999");
			r.put("result_msg", "Browser launching Fail");
			return;
		}
		
		byte[] closeScript = 
				( "<html><head>"
				+ "<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"
				+ "</head><body ></body>"
				+ "<SCRIPT LANGUAGE='JavaScript'>"
				+ "window.open('about:blank', '_self').close();"
				+ "</SCRIPT></html>")
				.getBytes();
		byte[] header = ("HTTP/1.1 200 Success\r\nConnection: close\r\nContent-Type: text/html; charset=utf8\r\n"
					+ "Content-Length: "+closeScript.length+"\r\n\r\n").getBytes();
		// Server Wait
		ServerSocket ss = new ServerSocket(65533);
		ss.setSoTimeout(300*1000);
		Socket sock = ss.accept();
		BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		String firstLine = reader.readLine();
        for (String line; (line = reader.readLine()) != null;) {
            if (isEmpty(line)) break; // Stop when headers are completed.
        }
        sock.getOutputStream().write(header);
        sock.getOutputStream().write(closeScript);
        sock.getOutputStream().flush();
		sock.close();
		ss.close();
		if (isEmpty(firstLine))
		{
			HashMap<String,String> r = new HashMap<String,String>();
			r.put("result_code", "999");
			r.put("result_msg", "OAuth Verifier read fail!~");
			return;
		}
		logger.debug("receive=" + firstLine);
		
		int sp = firstLine.indexOf("oauth_verifier=");
		if (sp == -1) return ;
		String vf = firstLine.substring(sp + "oauth_verifier=".length());
		String[] arr = vf.split("&| |=");

		String oauth_verifier =  arr[0];

		oauthStatus = "require_access";
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
	}
	
	private boolean showInBrowser(String url, Frame frame) {
		// minimizes the app
		if (frame != null)
			frame.setExtendedState(JFrame.ICONIFIED);

		String os = System.getProperty("os.name").toLowerCase();
		Runtime rt = Runtime.getRuntime();
		try {
			if (os.indexOf("win") >= 0) {
				String[] cmd = new String[4];
				cmd[0] = "cmd.exe";
				cmd[1] = "/C";
				cmd[2] = "start";
				cmd[3] = url;
				rt.exec(cmd);
			} else if (os.indexOf("mac") >= 0) {
				rt.exec("open " + url);
			} else {
				// prioritized 'guess' of users' preference
				String[] browsers = { "epiphany", "firefox", "mozilla",
						"konqueror", "netscape", "opera", "links", "lynx" };

				StringBuffer cmd = new StringBuffer();
				for (int i = 0; i < browsers.length; i++)
					cmd.append((i == 0 ? "" : " || ") + browsers[i] + " \""
							+ url + "\" ");

				rt.exec(new String[] { "sh", "-c", cmd.toString() });
			}
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane
					.showMessageDialog(
							frame,
							"\n\n The system failed to invoke your default web browser while attempting to access: \n\n "
									+ url + "\n\n", "Browser Error",
							JOptionPane.WARNING_MESSAGE);

			return false;
		}
		return true;
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
	
	abstract public String get(String urlApi, Map<String,String> params) throws Exception;
}
