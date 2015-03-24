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
import com.wechat.pojo.ViewButton;
import com.wechat.util.JsonUtils;
import com.wechat.util.WexinUtil;

public class WechatTest {
	
	private static Logger log = LoggerFactory.getLogger(WexinUtil.class);  
	
	
	public static String token = "CW5h6UlQklbcvuC4OpZA-RBeud48x6Mqapjf32a4gORnpLJX43hrYVHmgt3KKTmTtXN0zmQ7xf9cyQ74mXGUpASvytF7M5kqctrVFrBZVhM";
	  
	
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
	
	
	
	
	
	
	@Test
//	@Ignore
	public void testCreateMenu() {  
        // 调用接口创建菜单  
        String result;
		try {
			result = WexinUtil.createMenu(getMenu(), token);
			
			// 判断菜单创建结果  
	        if ("0".equals(result))  
	        	log.error("菜单创建成功！");  
	        else  
	        	log.error("菜单创建失败，错误码：" + result);  
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
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
          
        CommonButton btn15 = new CommonButton();  
        btn15.setName("电影排行榜");  
        btn15.setType("click");  
        btn15.setKey("32");  
  
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
  
        CommonButton btn33 = new CommonButton();  
        btn33.setName("幽默笑话");  
        btn33.setType("click");  
        btn33.setKey("33");  
          
        CommonButton btn34 = new CommonButton();  
        btn34.setName("用户反馈");  
        btn34.setType("click");  
        btn34.setKey("34");  
          
        CommonButton btn35 = new CommonButton();  
        btn35.setName("关于我们");  
        btn35.setType("click");  
        btn35.setKey("35");  
          
        ViewButton btn32 = new ViewButton();  
        btn32.setName("使用帮助");  
        btn32.setType("view");  
        btn32.setUrl("http://mp.weixin.qq.com/wiki/14/89b871b5466b19b3efa4ada8e577d45e.html");  
  
        ComplexButton mainBtn1 = new ComplexButton();  
        mainBtn1.setName("生活助手");  
        mainBtn1.setSub_button(new Button[] { btn11, btn12, btn13, btn14, btn15 });  
  
        ComplexButton mainBtn2 = new ComplexButton();  
        mainBtn2.setName("休闲驿站");  
        mainBtn2.setSub_button(new Button[] { btn21, btn22, btn23, btn24, btn25 });  
  
        ComplexButton mainBtn3 = new ComplexButton();  
        mainBtn3.setName("更多");  
        mainBtn3.setSub_button(new Button[] { btn31, btn33, btn34, btn35, btn32 });  
  
        /** 
         * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br> 
         *  
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br> 
         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br> 
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 }); 
         */  
        Menu menu = new Menu();  
        menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });  
  
        return menu; 
	}
	
	

}
