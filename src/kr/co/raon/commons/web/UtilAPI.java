package kr.co.raon.commons.web;

import kr.co.raon.commons.*;
import java.util.Map;

public class UtilAPI 
{
	static public String makeApiResult(String accept,Map<?,?>map,String... params) 
	{
		if(accept.toLowerCase().matches(".*xml$")) {
			String rootName = "root";
			if(params.length > 0) rootName = params[0].toString();
			return UtilConverter.toXml(map,rootName);
		}
		else
			return UtilConverter.toJson(map);
	}
}
 