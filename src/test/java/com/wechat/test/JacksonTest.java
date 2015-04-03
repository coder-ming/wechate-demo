package com.wechat.test;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

public class JacksonTest {

	// 解析普通JSON字符串
	@Test
	public void testOne() {
		String str = "{\"CellPhone\":\"0963198851\",\"Email\":\"02026@foodchina.com.tw\",\"RealName\":\"吳小姐\"}";
		JsonFactory jsonFactory = new MappingJsonFactory();
		JsonParser parser;
		try {
			parser = jsonFactory.createJsonParser(str);
			parser.nextToken();
			while (parser.nextToken() != JsonToken.END_OBJECT) {
				parser.nextToken();
				System.out.println(parser.getCurrentName() + ":"
						+ parser.getText());
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// 解析普通JSON字节数组
	@Test
	public void testParseArrays() {
		try {
			// String str =
			// "{ \"groupOp\" :\"AND\",\"rules\":[{ \"field\":\"lastName\",\"op\":\"cn\",\"data\":\"Test\"},{\"field\":\"firstName\",\"op\":\"cn\",\"data\":\"Test2222\"}]}";

			String test = "{\"results\":[{\"objectID\":357,\"geoPoints\":[{\"x\":504604.59,\"y\":305569.91}]},{\"objectID\":358,\"geoPoints\":[{\"x\":504602.2680,\"y\":305554.4360}]}]}";

			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode node = objectMapper.readTree(test); // 将Json串以树状结构读入内存
			JsonNode contents = node.get("results");// 得到results这个节点下的信息
			for (int i = 0; i < contents.size(); i++) // 遍历results下的信息，size()函数可以得节点所包含的的信息的个数，类似于数组的长度
			{
				System.out.println(contents.get(i).get("objectID").getIntValue()); // 读取节点下的某个子节点的值
				JsonNode geoNumber = contents.get(i).get("geoPoints");
				for (int j = 0; j < geoNumber.size(); j++) {  // 循环遍历子节点下的信息
					JsonNode jsonNode = geoNumber.get(j);
					Iterator<Entry<String, JsonNode>> iter = jsonNode.getFields();
					while(iter.hasNext()) {
						Entry<String, JsonNode> entry = iter.next();
						System.out.println(entry.getKey() + "," + entry.getValue().toString());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	//解析复杂JSON
	@Test
	public void testComplexJson() {
		try {
			 String test = "{ \"groupOp\" :\"AND\",\"rules\":[{ \"field\":\"lastName\",\"op\":\"cn\",\"data\":\"Test\"},{\"field\":\"firstName\",\"op\":\"cn\",\"data\":\"Test2222\"}]}";
//			String test = "{\"results\":[{\"objectID\":357,\"geoPoints\":[{\"x\":504604.59,\"y\":305569.91}]},{\"objectID\":358,\"geoPoints\":[{\"x\":504602.2680,\"y\":305554.4360}]}]}";

			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode node = objectMapper.readTree(test); // 将Json串以树状结构读入内存
			parseJson(node);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void parseJson(JsonNode jsonNode) {
		if(jsonNode.isArray()) {
			Iterator<JsonNode> js = jsonNode.iterator();
			while(js.hasNext()) {
				 JsonNode n = js.next();
				 parseJson(n);
			}
			return;
		}
		
		
		Iterator<Entry<String, JsonNode>> iter = jsonNode.getFields();
		while(iter.hasNext()) {
			Entry<String, JsonNode> entry = iter.next();
			JsonNode jn = entry.getValue();
			System.out.println("key:" + entry.getKey() + ",value:" + jn.toString());
			parseJson(jn);
		}
		
	}
	

}
