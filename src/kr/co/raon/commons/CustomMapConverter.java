package kr.co.raon.commons;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Iterator;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Map <-> XML 변환을 위한 xStream Convert 클래스<br>
 * @author wicked
 * @since 2012.03.11
 */

@SuppressWarnings({"rawtypes","unchecked"})
public class CustomMapConverter implements Converter{

	public boolean canConvert(Class clazz) {
	    return AbstractMap.class.isAssignableFrom(clazz);
	}

	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {

		AbstractMap<String,Object> map = (AbstractMap<String,Object>) value;
	    
		for (Entry<String,Object> entry : map.entrySet()) {
	        if(entry.getValue() instanceof List) {
	        	List list = (List)entry.getValue();
	        	Iterator it = list.iterator();
	        	while( it.hasNext() ) {
	    	        writer.startNode(entry.getKey().toString());
	        		marshal(it.next(),writer,context);
	    	        writer.endNode();
	        	}
	        } else {
		        writer.startNode(entry.getKey().toString());
	        	writer.setValue(entry.getValue().toString());
		        writer.endNode();
	        }
	    }
	}
	
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

		Map<String, Object> map = new HashMap<String, Object>();

	    while(reader.hasMoreChildren()) {
	        reader.moveDown();
	        if(reader.hasMoreChildren()) {
	        	Object value = unmarshal(reader,context);
		        String key = reader.getNodeName();
		        Object object = map.get(key);
		        if(object != null) {
		        	if(object instanceof List) {
		        		((List)object).add(value);
		        	}
		        	else {
		        		List tempList = new ArrayList();
		        		tempList.add(object);
		        		tempList.add(value);
		        		map.put(key, tempList);
		        	}
		        }
		        else {
		        	map.put(key, value);
		        }
	        } else {
		        String key = reader.getNodeName();
		        String value = reader.getValue();
		        Object object = map.get(key);
		        if(object != null) {
					if(object instanceof List) {
						((List) object).add(value);
					}
					else  {
						List tempList = new ArrayList();
						tempList.add(object);
						tempList.add(value);
						map.put(key,tempList);
					}
		        } else {
		        	map.put(key, value);
		        }
	        }
	        reader.moveUp();
	    }
	    return map;
	}
}
