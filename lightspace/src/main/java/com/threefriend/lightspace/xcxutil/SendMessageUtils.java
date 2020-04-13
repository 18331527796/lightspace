package com.threefriend.lightspace.xcxutil;

import java.io.IOException;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.threefriend.lightspace.enums.AccountEnums;
import com.threefriend.lightspace.mapper.GzhUserMapper;
import com.threefriend.lightspace.repository.GzhUserRepository;
import com.threefriend.lightspace.util.RedisUtils;

/**
 * 发送公众号消息模板推送工具类
 * @author Administrator
 *
 */
public class SendMessageUtils {
	
	private final static String URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
	
	

	/**
	 * 分析报告消息推送
	 * @param unionId
	 * @throws IOException
	 */
	public static void wordMessage(String openId , String ACCESS_TOKEN) throws IOException {
			
	        String postUrl = URL + ACCESS_TOKEN;
	 
	        JSONObject jsonObject = new JSONObject();
	        jsonObject.put("touser", openId);   // openid
	        jsonObject.put("template_id", "eJnts9RIDWwME8OnpNYY5d8rWvAwIJolaw_6VVlG88U");
	        // 主要是这里， 设置小程序的appid和转发的页面
	    	TreeMap<String, String> miniprograms = new TreeMap<String, String>();
	    	miniprograms.put("appid",AccountEnums.APIKEY.getUrl());
	    	miniprograms.put("pagepath","page/tabBar/my/my");// 注意，这里是支持传参的！！！
	    	jsonObject.put("miniprogram", miniprograms);
	        JSONObject data = new JSONObject();
	        JSONObject first = new JSONObject();
	        first.put("value", "你瞅啥~~aa");
	        JSONObject keyword1 = new JSONObject();
	        keyword1.put("value", "鲁伯特之泪");
	        JSONObject keyword2 = new JSONObject();
	        keyword2.put("value", "666666");
	        JSONObject keyword3 = new JSONObject();
	        keyword3.put("value", "今儿，逗刚才");
	        JSONObject keyword4 = new JSONObject();
	        keyword4.put("value", "lightspace");
	        JSONObject remark = new JSONObject();
	        remark.put("value", "这就是试试功能");
	        
	        data.put("first",first);
	        data.put("keyword1",keyword1);
	        data.put("keyword2",keyword2);
	        data.put("keyword3",keyword3);
	        data.put("keyword4",keyword4);
	        data.put("remark",remark);
	 
	        jsonObject.put("data", data);
	 
	        String string = HttpClientUtils.sendPostJsonStr(postUrl, jsonObject.toJSONString());
	        JSONObject result = JSON.parseObject(string);
	        int errcode = result.getIntValue("errcode");
	        if(errcode == 0){
	            // 发送成功
	            System.out.println("发送成功");
	        } else {
	            // 发送失败
	            System.out.println("发送失败");
	        }
		
	}
}
