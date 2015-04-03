package com.wechat.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wechat.message.resp.Article;
import com.wechat.message.resp.NewsMessage;
import com.wechat.message.resp.TextMessage;
import com.wechat.util.MessageUtil;

public class CoreService {
	
	private static final Logger logger = LoggerFactory.getLogger(CoreService.class);
	
	/** 
     * 处理微信发来的请求 
     *  
     * @param request 
     * @return 
     */  
    public static String processRequest(HttpServletRequest request) {  
        String respMessage = null;  
        try {  
            // 默认返回的文本消息内容  
            String respContent = "请求处理异常，请稍候尝试！";  
  
            // xml请求解析  
            Map<String, String> requestMap = MessageUtil.parseXml(request);  
  
            // 发送方帐号（open_id）  
            String fromUserName = requestMap.get("FromUserName");  
            // 公众帐号  
            String toUserName = requestMap.get("ToUserName");  
            // 消息类型  
            String msgType = requestMap.get("MsgType");  
  
            // 回复文本消息  
            TextMessage textMessage = new TextMessage();  
            textMessage.setToUserName(fromUserName);  
            textMessage.setFromUserName(toUserName);  
            textMessage.setCreateTime(new Date().getTime());  
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
            textMessage.setFuncFlag(0);  
  
            // 文本消息  
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {  
                respContent = "您发送的是文本消息！";  
            }  
            // 图片消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {  
                respContent = "您发送的是图片消息！";  
            }  
            // 地理位置消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {  
                respContent = "您发送的是地理位置消息！";  
            }  
            // 链接消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {  
                respContent = "您发送的是链接消息！";  
            }   
            // 音频消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {  
                respContent = "您发送的是音频消息！";  
            }  
            // 事件推送  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {  
                // 事件类型  
                String eventType = requestMap.get("Event");  
                // 订阅  
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {  
                    respContent = "谢谢您的关注！";  
                }  
                // 取消订阅  
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {  
                    // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息  
                }  
                // 自定义菜单点击事件  
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {  
                    // 事件KEY值，与创建自定义菜单时指定的KEY值对应  
                    String eventKey = requestMap.get("EventKey");  
  
                    if (eventKey.equals("11")) {  
                        respContent = "天气预报菜单项被点击！";  
                    } else if (eventKey.equals("12")) {  
                        respContent = "公交查询菜单项被点击！";  
                    } else if (eventKey.equals("13")) {  
                        respContent = "周边搜索菜单项被点击！";  
                    } else if (eventKey.equals("14")) {  
                        respContent = TodayInHistoryService.getTodayInHistoryInfo();  
                    } else if (eventKey.equals("21")) {  
                        respContent = "歌曲点播菜单项被点击！";  
                    } else if (eventKey.equals("22")) {  
                        respContent = "经典游戏菜单项被点击！";  
                    } else if (eventKey.equals("23")) {  
                        respContent = "美女电台菜单项被点击！";  
                    } else if (eventKey.equals("24")) {  
                        respContent = "人脸识别菜单项被点击！";  
                    } else if (eventKey.equals("25")) {  
                        respContent = "聊天唠嗑菜单项被点击！";  
                    } else if (eventKey.equals("31")) {  
                        respContent = "Q友圈菜单项被点击！";  
                    } else if (eventKey.equals("32")) {  
                        respContent = "电影排行榜菜单项被点击！";  
                    } else if (eventKey.equals("33")) {  
                        respContent = "幽默笑话菜单项被点击！";  
                    }  
                }  
            }  
  
            textMessage.setContent(respContent);  
            respMessage = MessageUtil.textMessageToXml(textMessage);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        return respMessage;  
    }  
	
	
	 /** 
     * 处理微信发来的请求 
     *  
     * @param request 
     * @return 
     */  
    public static String processRequestMessage(HttpServletRequest request) {  
        String respMessage = null;  
        try {  
            // xml请求解析  
            Map<String, String> requestMap = MessageUtil.parseXml(request);  
  
            logger.info("request map data = " + requestMap);
            
            // 发送方帐号（open_id）  
            String fromUserName = requestMap.get("FromUserName");  
            // 公众帐号  
            String toUserName = requestMap.get("ToUserName");  
            // 消息类型  
            String msgType = requestMap.get("MsgType");  
  
            // 默认回复此文本消息  
            TextMessage textMessage = new TextMessage();  
            textMessage.setToUserName(fromUserName);  
            textMessage.setFromUserName(toUserName);  
            textMessage.setCreateTime(new Date().getTime());  
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
            textMessage.setFuncFlag(0);  
            // 由于href属性值必须用双引号引起，这与字符串本身的双引号冲突，所以要转义  
            textMessage.setContent("欢迎访问<a href=\"http://blog.csdn.net/lyq8479\">柳峰的博客</a>![微笑]");  
            // 将文本消息对象转换成xml字符串  
            respMessage = MessageUtil.textMessageToXml(textMessage); 
            
  
            // 文本消息  
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {  
                // 接收用户发送的文本消息内容  
                String content = requestMap.get("Content");  
  
                // 创建图文消息  
                NewsMessage newsMessage = new NewsMessage();  
                newsMessage.setToUserName(fromUserName);  
                newsMessage.setFromUserName(toUserName);  
                newsMessage.setCreateTime(new Date().getTime());  
                newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);  
                newsMessage.setFuncFlag(0);  
  
                List<Article> articleList = new ArrayList<Article>();  
                // 单图文消息  
                if ("1".equals(content)) {  
                    Article article = new Article();  
                    article.setTitle("12306图片验证码被曝存漏洞");  
                    article.setDescription("新浪科技讯 3月24日晚间消息，白帽子路人甲在乌云漏洞报告平台提交了一个12306图片验证码的漏洞，称这会导致限制可被绕过，理论上可以令抢票软件复活。");  
                    article.setPicUrl("http://n.sinaimg.cn/transform/20150324/OSSU-awzuney1039897.jpg");  
                    article.setUrl("http://tech.sina.cn/i/gn/2015-03-24/detail-icczmvun7085932.d.html?vt=4&pos=108");  
                    articleList.add(article);  
                    // 设置图文消息个数  
                    newsMessage.setArticleCount(articleList.size());  
                    // 设置图文消息包含的图文集合  
                    newsMessage.setArticles(articleList);  
                    // 将图文消息对象转换成xml字符串  
                    respMessage = MessageUtil.newsMessageToXml(newsMessage);  
                }  
                // 单图文消息---不含图片  
                else if ("2".equals(content)) {  
                    Article article = new Article();  
                    article.setTitle("12306图片验证码被曝存漏洞");  
                    // 图文消息中可以使用QQ表情、符号表情  
                    article.setDescription("新浪科技讯 3月24日晚间消息，白帽子路人甲在乌云漏洞报告平台提交了一个12306图片验证码的漏洞，称这会导致限制可被绕过，理论上可以令抢票软件复活。[微笑]");  
                    // 将图片置为空  
                    article.setPicUrl("");  
                    article.setUrl("http://tech.sina.cn/i/gn/2015-03-24/detail-icczmvun7085932.d.html?vt=4&pos=108");  
                    articleList.add(article);  
                    newsMessage.setArticleCount(articleList.size());  
                    newsMessage.setArticles(articleList);  
                    respMessage = MessageUtil.newsMessageToXml(newsMessage);  
                }  
                // 多图文消息  
                else if ("3".equals(content)) {  
                    Article article1 = new Article();  
                    article1.setTitle("光头男收小贩保护费\n街头围殴城管");  
                    article1.setDescription("");  
                    article1.setPicUrl("http://m1.sinaimg.cn/maxwidth.2880/m1.sinaimg.cn/14593096bcf2536dcf893bc269f7640f_852_640.jpg");  
                    article1.setUrl("http://photo.sina.cn/album?ch=1&sid=2841&aid=82554&vt=4&pos=108");  
  
                    Article article2 = new Article();  
                    article2.setTitle("司机错把油门当刹车\n致8车连环相撞");  
                    article2.setDescription("");  
                    article2.setPicUrl("http://m1.sinaimg.cn/maxwidth.2880/m1.sinaimg.cn/72727e9b32fa5a6c6aafa47098b674e4_525_640.jpg");  
                    article2.setUrl("http://photo.sina.cn/album?ch=1&sid=2841&aid=82560&vt=4&pos=108");  
  
                    Article article3 = new Article();  
                    article3.setTitle("英菲尼迪QX30概念车官方图");  
                    article3.setDescription("");  
                    article3.setPicUrl("http://m1.sinaimg.cn/maxwidth.2880/m1.sinaimg.cn/2c4c6dfd707c29ca0331c280dd0e22e8_950_633.jpg");  
                    article3.setUrl("http://photo.sina.cn/album?ch=90&sid=2&aid=17608&vt=4&pos=108");  
  
                    articleList.add(article1);  
                    articleList.add(article2);  
                    articleList.add(article3);  
                    newsMessage.setArticleCount(articleList.size());  
                    newsMessage.setArticles(articleList);  
                    respMessage = MessageUtil.newsMessageToXml(newsMessage);  
                }  
                // 多图文消息---首条消息不含图片  
                else if ("4".equals(content)) {  
                    Article article1 = new Article();  
                    article1.setTitle("2015新车上市");  
                    article1.setDescription("");  
                    // 将图片置为空  
                    article1.setPicUrl("");  
                    article1.setUrl("http://auto.sina.cn/?sa=t111d31v47&vt=4&pos=108");  
  
                    Article article2 = new Article();  
                    article2.setTitle("吉利GX7经典版 售6.99万起");  
                    article2.setDescription("");  
                    article2.setPicUrl("http://r3.sinaimg.cn/33/2015/0324/bc/c/90519305/auto.jpg");  
                    article2.setUrl("http://auto.sina.cn/?sa=t94d1123961v50&cid=1373&vt=4&clicktime=1427208175563&userid=user1427208175563808211418101564");  
  
                    Article article3 = new Article();  
                    article3.setTitle("众泰T600运动版设计图曝光");  
                    article3.setDescription("");  
                    article3.setPicUrl("http://r3.sinaimg.cn/33/2015/0324/bc/c/90519305/original.jpg");  
                    article3.setUrl("http://auto.sina.cn/?sa=t94d1123912v50&cid=1373&vt=4&clicktime=1427208205576&userid=user14272082055769895577542483807");  
  
                    Article article4 = new Article();  
                    article4.setTitle("保时捷Boxster Spyder今年发");  
                    article4.setDescription("");  
                    article4.setPicUrl("http://r3.sinaimg.cn/33/2015/0324/56/3/74512316/original.jpg");  
                    article4.setUrl("http://auto.sina.cn/?sa=t94d1123824v50&cid=1373&vt=4&clicktime=1427208271231&userid=user14272082712319199717040173709");  
  
                    articleList.add(article1);  
                    articleList.add(article2);  
                    articleList.add(article3);  
                    articleList.add(article4);  
                    newsMessage.setArticleCount(articleList.size());  
                    newsMessage.setArticles(articleList);  
                    respMessage = MessageUtil.newsMessageToXml(newsMessage);  
                }  
                // 多图文消息---最后一条消息不含图片  
                else if ("5".equals(content)) {  
                    Article article1 = new Article();  
                    article1.setTitle("奥迪发R18 e-tron quattro");  
                    article1.setDescription("1111111");  
                    article1.setPicUrl("http://r3.sinaimg.cn/33/2015/0324/de/a/09519402/original.jpg");  
                    article1.setUrl("http://auto.sina.cn/?sa=t94d1123814v50&cid=1373&vt=4&clicktime=1427208319617&userid=user14272083196170002702747005969286");  
  
                    Article article2 = new Article();  
                    article2.setTitle("菲亚特/Jeep新车计划曝光");  
                    article2.setDescription("22222222");  
                    article2.setPicUrl("http://r3.sinaimg.cn/33/2015/0324/89/3/57512694/original.jpg");  
                    article2.setUrl("http://auto.sina.cn/?sa=t94d1123776v50&cid=1373&vt=4&clicktime=1427208345431&userid=user14272083454318000841706525534");
                    
                    Article article3 = new Article();  
                    article3.setTitle("大众凌渡性能版-申报图曝光");  
                    article3.setDescription("3333333");  
                    article3.setPicUrl("http://r3.sinaimg.cn/33/2015/0324/1f/9/08519849/original.jpg");  
                    article3.setUrl("http://auto.sina.cn/?sa=t94d1123738v50&cid=1373&vt=4&clicktime=1427208372712&userid=user14272083727128775865293573588");  
  
                    Article article4 = new Article();  
                    article4.setTitle("查看更多内容[微笑]");  
                    article4.setDescription("");  
                    // 将图片置为空  
                    article4.setPicUrl("");  
                    article4.setUrl("http://auto.sina.cn/?sa=t111d31v47&vt=4&pos=108");  
  
                    articleList.add(article1);  
                    articleList.add(article2);  
                    articleList.add(article3); 
                    articleList.add(article4);
                    newsMessage.setArticleCount(articleList.size());  
                    newsMessage.setArticles(articleList);  
                    respMessage = MessageUtil.newsMessageToXml(newsMessage);  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        
        logger.info("response data = " + respMessage);
        
        return respMessage;  
    }  
  
    /** 
     * emoji表情转换(hex -> utf-16) 
     *  
     * @param hexEmoji 
     * @return 
     */  
    public static String emoji(int hexEmoji) {  
        return String.valueOf(Character.toChars(hexEmoji));  
    }  
	
	
	/** 
     * 处理微信发来的请求 
     *  
     * @param request 
     * @return 
     */  
    public static String processRequestTest(HttpServletRequest request) {  
        String respMessage = null;  
        try {  
            // 默认返回的文本消息内容  
            String respContent = "请求处理异常，请稍候尝试！";  
  
            // xml请求解析  
            Map<String, String> requestMap = MessageUtil.parseXml(request);  
  
            // 发送方帐号（open_id）  
            String fromUserName = requestMap.get("FromUserName");  
            // 公众帐号  
            String toUserName = requestMap.get("ToUserName");  
            // 消息类型  
            String msgType = requestMap.get("MsgType");  
  
            // 回复文本消息  
            TextMessage textMessage = new TextMessage();  
            textMessage.setToUserName(fromUserName);  
            textMessage.setFromUserName(toUserName);  
            textMessage.setCreateTime(new Date().getTime());  
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
            textMessage.setFuncFlag(0);  
  
            // 文本消息  
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {  
                respContent = "您发送的是文本消息！";  
            }  
            // 图片消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {  
                respContent = "您发送的是图片消息！";  
            }  
            // 地理位置消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {  
                respContent = "您发送的是地理位置消息！";  
            }  
            // 链接消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {  
                respContent = "您发送的是链接消息！";  
            }  
            // 音频消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {  
                respContent = "您发送的是音频消息！";  
            }  
            // 事件推送  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {  
                // 事件类型  
                String eventType = requestMap.get("Event");  
                // 订阅  
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {  
                    respContent = "谢谢您的关注！";  
                }  
                // 取消订阅  
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {  
                    // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息  
                }  
                // 自定义菜单点击事件  
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {  
                    // TODO 自定义菜单权没有开放，暂不处理该类消息  
                }  
            }  
  
            textMessage.setContent(respContent);  
            respMessage = MessageUtil.textMessageToXml(textMessage);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        return respMessage;  
    }  
	

}
