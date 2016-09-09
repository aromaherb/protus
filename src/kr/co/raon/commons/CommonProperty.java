package kr.co.raon.commons;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Enumeration;
import java.util.Properties;
import kr.co.raon.commons.util.*;

public class CommonProperty {
	HashMap<String,String> map = new HashMap<String,String>();
	public CommonProperty() throws IOException
	{
	  FileInputStream fis;
	  try
	  {
		  String filepath = this.getClass().getClassLoader().getResource("/kr/co/raon/commons/common.properties").getPath().replaceAll("%20", " ");
		  filepath = StringUtil.null2Str(filepath);

		  String osname = System.getProperty("os.name").toLowerCase();
		  if(osname.indexOf("win") != -1){
			  if(filepath.length()>0){
				  if(filepath.substring(0, 1).equals("/")){
					  filepath = filepath.substring(1, filepath.length());
				  }
			  }
		  }
		  
		  fis = new FileInputStream(filepath);
		  Properties prop = new Properties();
		  prop.load(fis);
		  Enumeration<?> set = prop.propertyNames();   
		  while ( set.hasMoreElements()){      
			  String name = (String)set.nextElement();
			  map.put(name, prop.getProperty(name));
		  }
	  }catch(FileNotFoundException e){ 
		  e.printStackTrace();
	  } 
	}

	public String getProp(String name)
	{
		String val;
		val = map.get(name); 
		return val;
	}  
}