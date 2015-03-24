package com.wechat.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wechat.pojo.AccessToken;
import com.wechat.pojo.Menu;

public class WexinUtil {
	
	
	private static Logger log = LoggerFactory.getLogger(WexinUtil.class);  
	
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	
	private static ObjectMapper objectManger = new ObjectMapper(); 
	  
    /** 
     * 发起https请求并获取结果 
     *  
     * @param requestUrl 请求地址 
     * @param requestMethod 请求方式（GET、POST） 
     * @param outputStr 提交的数据 
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值) 
     */  
    public static Map<String,String> httpRequest(String requestUrl, String requestMethod, String outputStr) {  
        StringBuffer buffer = new StringBuffer();  
        try {  
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化  
            TrustManager[] tm = { new MyX509TrustManager() };  
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");  
            sslContext.init(null, tm, new java.security.SecureRandom());  
            // 从上述SSLContext对象中得到SSLSocketFactory对象  
            SSLSocketFactory ssf = sslContext.getSocketFactory();  
  
            URL url = new URL(requestUrl);  
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();  
            httpUrlConn.setSSLSocketFactory(ssf);  
  
            httpUrlConn.setDoOutput(true);  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setUseCaches(false);  
            // 设置请求方式（GET/POST）  
            httpUrlConn.setRequestMethod(requestMethod);  
  
            if ("GET".equalsIgnoreCase(requestMethod))  
                httpUrlConn.connect();  
  
            // 当有数据需要提交时  
            if (null != outputStr) {  
                OutputStream outputStream = httpUrlConn.getOutputStream();  
                // 注意编码格式，防止中文乱码  
                outputStream.write(outputStr.getBytes("UTF-8"));  
                outputStream.close();  
            }  
  
            // 将返回的输入流转换成字符串  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // 释放资源  
            inputStream.close();  
            inputStream = null;  
            httpUrlConn.disconnect();  
            return JsonUtils.jsonToMap(buffer.toString());
            
        } catch (ConnectException ce) {  
            log.error("Weixin server connection timed out.");  
        } catch (Exception e) {  
            log.error("https request error:{}", e);  
        }  
        return null;  
    }  
    
    
    public static AccessToken getToken() throws JsonParseException, JsonMappingException, IOException {
		AccessToken accessToken = null;  
		  
	    String requestUrl = access_token_url.replace("APPID", "xxxxxxxxxxx").replace("APPSECRET", "xxxxxxxxxxxxxx");  
	    Map<String,String> map = WexinUtil.httpRequest(requestUrl, "GET", null);  
	    // 如果请求成功  
	    if (null != map) {  
            accessToken = new AccessToken();  
            accessToken.setToken(map.get("access_token"));  
            accessToken.setExpiresIn(Integer.valueOf(map.get("expires_in"))); 
            
            System.out.println(accessToken);
            
	    }  
	    return accessToken;  
	}
    
    public static String createMenu(Menu menu, String accessToken) throws JsonParseException, IOException, Exception {  
	    String result = "0";  
	  
	    // 拼装创建菜单的url  
	    String url = menu_create_url.replace("ACCESS_TOKEN", getToken().getToken());  
	    // 将菜单对象转换成json字符串  
	    String jsonMenu = JsonUtils.jsonFromObject(menu);  
	    // 调用接口创建菜单  
	    Map<String,String> map = WexinUtil.httpRequest(url, "POST", jsonMenu);  
	  
	    log.error(map.toString());
	    
	    if (null != map) {  
	        if (null != map.get("errcode")) {  
	            result = map.get("errcode");  
	            System.out.println("创建菜单 " + map.get("errmsg"));  
	        }  
	    }  
	    return result;  
	}
    
    
	

}
