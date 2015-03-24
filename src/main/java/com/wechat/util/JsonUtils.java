package com.wechat.util;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtils {
	
    private static final ObjectMapper mapper = new ObjectMapper();  
    public static String jsonFromObject(Object object) {  
        StringWriter writer = new StringWriter();  
        try {  
            mapper.writeValue(writer, object);  
        } catch (RuntimeException e) {  
            throw e;  
        } catch (Exception e) {  
        	e.printStackTrace();
            System.err.print("Unable to serialize to json: " + object);  
            return null;  
        }  
        return writer.toString();  
    }  
    
    public static Map<String, String> jsonToMap(String json) throws Exception {  
    	 JsonFactory jsonFactory = new MappingJsonFactory();  
         // Json解析器  
         JsonParser jsonParser = jsonFactory.createJsonParser(json);  
         // 跳到结果集的开始  
         jsonParser.nextToken();  
         // 接受结果的HashMap  
         HashMap<String, String> map = new HashMap<String, String>();  
         // while循环遍历Json结果  
         while (jsonParser.nextToken() != JsonToken.END_OBJECT) {  
             // 跳转到Value  
             jsonParser.nextToken();  
             // 将Json中的值装入Map中  
             map.put(jsonParser.getCurrentName(), jsonParser.getText());  
         }  
    	
         return map;
    }  
  
    
    

}
