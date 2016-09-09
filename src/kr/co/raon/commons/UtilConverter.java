package kr.co.raon.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

public class UtilConverter 
{
	private static final Logger logger = LoggerFactory.getLogger(UtilConverter.class);

	static public String toJson(Map<?,?> map) 
	{
		ObjectMapper mapper = new ObjectMapper();

		try {
			return mapper.writeValueAsString(map);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	static public Map<?,?> jsonToMap(String json) 
	{
		ObjectMapper mapper = new ObjectMapper();

		try {
			Map<String,Object> map = mapper.readValue(json, new TypeReference<Map<String,Object>>() {}); 
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	static public String toXml(Map<?,?> map,String rootName) 
	{
		XmlFriendlyNameCoder replacer = new XmlFriendlyNameCoder("__", "_");
		XStream xStream = new XStream(new DomDriver("UTF-8", replacer));
		xStream.alias(rootName, java.util.Map.class);
		xStream.registerConverter(new CustomMapConverter());
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + xStream.toXML(map);
	}
	
	static public Map<?,?> XmlToMap(String xml,String rootName)
	{
		XStream xStream = new XStream(new DomDriver("UTF-8"));
		xStream.alias(rootName, java.util.Map.class);
		xStream.registerConverter(new CustomMapConverter());
		return (Map<?,?>)xStream.fromXML(xml);
	}
	
	static public String toEncodedUrlParam(Map<String,String> params)
	{
		if( params == null || params.size() == 0 ) return null;
		
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		for (Entry<String,String> param : params.entrySet()) {
			qparams.add(new BasicNameValuePair(param.getKey(), param.getValue()));
		}
		return URLEncodedUtils.format(qparams, "UTF-8");
	}
	
	static public Map<String,String> EncodedUrlParamToMap(String urlParam)
	{
		try {
			URI uri = URI.create("http://localhost/parse?"+urlParam);
		
			List<NameValuePair> list = URLEncodedUtils.parse(uri,"UTF-8");
			if(list.size() == 0) return null;
			
			Map<String,String> result = new HashMap<String,String>();
			for(NameValuePair entity : list) {
				logger.debug("name=" + entity.getName() + ",value=" + entity.getValue());
				result.put(entity.getName(), entity.getValue());
			}
			return result;
			
		} catch (Exception ex) {
			return null;
		}
	}
	
	static public String toString(InputStream is)	throws IOException {
		if (is != null) {
			Writer writer = new StringWriter();
			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				//is.close();
			}
			return writer.toString();
		} else {        
			return "";
		}
	}
	
	static public String getLt() {
		return "<";
	}

	static public String getGt() {
		return ">";
	}

	static public String lpad(String src, String padChar, int padLength) {
		String padString = "";
		String sValue    = "";
		
		int srcLength = src.length();
		
		sValue = src;
		
		if ( srcLength >= padLength ) {
			sValue = src;
		} else {
			for ( int i = 0; i < padLength - srcLength; i++ ) {
				padString += padChar;
			}
		}
		
		sValue = padString + sValue;
		
		return sValue;
	}	
	
	
	static public String rpad(String src, String padChar, int padLength) {
		String padString = "";
		String sValue    = "";
		
		int srcLength = src.length();
		
		sValue = src;
		
		if ( srcLength >= padLength ) {
			sValue = src;
		} else {
			for ( int i = 0; i < padLength - srcLength; i++ ) {
				padString += padChar;
			}
		}
		
		sValue += padString;
		
		return sValue;
	}	

}
