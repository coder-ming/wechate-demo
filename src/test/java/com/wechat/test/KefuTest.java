package com.wechat.test;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wechat.util.WexinUtil;

public class KefuTest {
	
	
	
	public static String token = "JvvbSqepYMmTfeEpHgVBCj1e4QUAAk8pkaTJd8AASTXg814MwVytbcnPM6V94wzT-vcl_MSWHJ750MIapSsz7ZIgRX22defMXyQkUBafw0U";
	  
	
	@Test
	public void testGetToken() {  
		try {
			System.out.println(WexinUtil.getToken().getToken());
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}  
	
	
	
	

}
