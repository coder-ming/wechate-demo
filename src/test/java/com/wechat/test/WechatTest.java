package com.wechat.test;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wechat.pojo.AccessToken;
import com.wechat.pojo.Button;
import com.wechat.pojo.CommonButton;
import com.wechat.pojo.ComplexButton;
import com.wechat.pojo.Menu;
import com.wechat.util.JsonUtils;
import com.wechat.util.WexinUtil;

public class WechatTest {
	
	private static Logger log = LoggerFactory.getLogger(WexinUtil.class);  
	
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	public static String token = "CW5h6UlQklbcvuC4OpZA-RBeud48x6Mqapjf32a4gORnpLJX43hrYVHmgt3KKTmTtXN0zmQ7xf9cyQ74mXGUpASvytF7M5kqctrVFrBZVhM";
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";  
	
	@Test
	@Ignore
	public void testJson() throws JsonParseException, JsonMappingException, IOException {
		String str = "{\"access_token\":\"ACCESS_TOKEN\",\"expires_in\":7200}";
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String,Object> map = objectMapper.readValue(str, Map.class);
		System.out.println(map);
		System.out.println(map.get("access_token"));
		System.out.println(map.get("expires_in"));
	}
	
	public static AccessToken getToken() throws JsonParseException, JsonMappingException, IOException {
		AccessToken accessToken = null;  
		  
	    String requestUrl = access_token_url.replace("APPID", "wxf85663b50a7f7930").replace("APPSECRET", "a7afc135a7112936a62b2ed342d77dcc");  
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
	
	@Test
	@Ignore
	public void testGetToken() {
		try {
			System.out.println(getToken().toString());
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static String createMenu(Menu menu, String accessToken) {  
	    String result = "0";  
	  
	    // 拼装创建菜单的url  
	    String url = menu_create_url.replace("ACCESS_TOKEN", token);  
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
	
	@Test
	@Ignore
	public void testCreateMenu() {  
        // 调用接口创建菜单  
        String result = createMenu(getMenu(), token);  
  
            // 判断菜单创建结果  
        if ("0".equals(result))  
        	log.error("菜单创建成功！");  
        else  
        	log.error("菜单创建失败，错误码：" + result);  
	}  
	
	
	public static Menu getMenu() {
		CommonButton btn11 = new CommonButton();  
        btn11.setName("天气预报");  
        btn11.setType("click");  
        btn11.setKey("11");  
  
        CommonButton btn12 = new CommonButton();  
        btn12.setName("公交查询");  
        btn12.setType("click");  
        btn12.setKey("12");  
  
        CommonButton btn13 = new CommonButton();  
        btn13.setName("周边搜索");  
        btn13.setType("click");  
        btn13.setKey("13");  
  
        CommonButton btn14 = new CommonButton();  
        btn14.setName("历史上的今天");  
        btn14.setType("click");  
        btn14.setKey("14");  
  
        CommonButton btn21 = new CommonButton();  
        btn21.setName("歌曲点播");  
        btn21.setType("click");  
        btn21.setKey("21");  
  
        CommonButton btn22 = new CommonButton();  
        btn22.setName("经典游戏");  
        btn22.setType("click");  
        btn22.setKey("22");  
  
        CommonButton btn23 = new CommonButton();  
        btn23.setName("美女电台");  
        btn23.setType("click");  
        btn23.setKey("23");  
  
        CommonButton btn24 = new CommonButton();  
        btn24.setName("人脸识别");  
        btn24.setType("click");  
        btn24.setKey("24");  
  
        CommonButton btn25 = new CommonButton();  
        btn25.setName("聊天唠嗑");  
        btn25.setType("click");  
        btn25.setKey("25");  
  
        CommonButton btn31 = new CommonButton();  
        btn31.setName("Q友圈");  
        btn31.setType("click");  
        btn31.setKey("31");  
  
        CommonButton btn32 = new CommonButton();  
        btn32.setName("电影排行榜");  
        btn32.setType("click");  
        btn32.setKey("32");  
  
        CommonButton btn33 = new CommonButton();  
        btn33.setName("幽默笑话");  
        btn33.setType("click");  
        btn33.setKey("33");  
  
        ComplexButton mainBtn1 = new ComplexButton();  
        mainBtn1.setName("生活助手");  
        mainBtn1.setSub_button(new CommonButton[] { btn11, btn12, btn13, btn14 });  
  
        ComplexButton mainBtn2 = new ComplexButton();  
        mainBtn2.setName("休闲驿站");  
        mainBtn2.setSub_button(new CommonButton[] { btn21, btn22, btn23, btn24, btn25 });  
  
        ComplexButton mainBtn3 = new ComplexButton();  
        mainBtn3.setName("更多体验");  
        mainBtn3.setSub_button(new CommonButton[] { btn31, btn32, btn33 });  
  
        /** 
         * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br> 
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br> 
         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br> 
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 }); 
         */  
        Menu menu = new Menu();  
        menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });  
  
        return menu;
	}
	
	

}
