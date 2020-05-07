package com.threefriend.lightspace.xcx.util;

import java.io.IOException;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.threefriend.lightspace.enums.AccountEnums;
import com.threefriend.lightspace.mapper.MsgTempMapper;
import com.threefriend.lightspace.mapper.xcx.GzhUserMapper;
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
	public static void wordMessage(MsgTempMapper msg,String openId , String ACCESS_TOKEN , String left , String right ,String name ,String date) throws IOException {
	        String postUrl = URL + ACCESS_TOKEN;
	        JSONObject jsonObject = new JSONObject();
	        jsonObject.put("touser", openId);   // openid
	        jsonObject.put("template_id", "q0MYzCcTVALPXjG00Nd-OYlLW53Y9D6CxROI6J_CYvs");
	        if(msg.getCarry()==1) {
	        	jsonObject.put("url", msg.getUrl());
	        }else if(msg.getCarry()==2) {
	        	// 主要是这里， 设置小程序的appid和转发的页面
	        	TreeMap<String, String> miniprograms = new TreeMap<String, String>();
	        	miniprograms.put("appid",AccountEnums.APIKEY.getUrl());
	        	miniprograms.put("pagepath",msg.getUrl());// 注意，这里是支持传参的！！！
	        	jsonObject.put("miniprogram", miniprograms);
	        }
	        JSONObject data = new JSONObject();
	        //副标题
	        JSONObject first = new JSONObject();
	        first.put("value", msg.getFirst());
	        //姓名
	        JSONObject keyword1 = new JSONObject();
	        keyword1.put("value", name);
	        //检查日期
	        JSONObject keyword2 = new JSONObject();
	        keyword2.put("value", date);
	        //裸眼视力
	        JSONObject keyword3 = new JSONObject();
	        keyword3.put("value", "R: "+right+" L: "+left);
	        //矫正视力
	        JSONObject keyword4 = new JSONObject();
	        keyword4.put("value", "R: 1.0 L: 1.0");
	        //温馨提示
	        JSONObject remark = new JSONObject();
	        remark.put("value", msg.getRemark());
	        
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
	            System.out.println("分析报告通知，发送成功");
	        } else {
	            // 发送失败
	            System.out.println("分析报告通知，发送失败");
	        }
		
	}
	
	/**
	 * 筛查消息推送
	 * @param unionId
	 * @throws IOException
	 */
	public static void screeningMessage(MsgTempMapper msg,String openId , String ACCESS_TOKEN , String left , String right , String date ,Integer type) throws IOException {
	        String postUrl = URL + ACCESS_TOKEN;
	        JSONObject jsonObject = new JSONObject();
	        jsonObject.put("touser", openId);   // openid
	        jsonObject.put("template_id", "b5IdG6vybpK8oSK_OhN4pTX0cvzGIYsD2Yghr3CA8-M");
	        if(msg.getCarry()==1) {
	        	jsonObject.put("url", msg.getUrl());
	        }else if(msg.getCarry()==2) {
	        	// 主要是这里， 设置小程序的appid和转发的页面
		    	TreeMap<String, String> miniprograms = new TreeMap<String, String>();
		    	miniprograms.put("appid",AccountEnums.APIKEY.getUrl());
		    	miniprograms.put("pagepath",msg.getUrl());// 注意，这里是支持传参的！！！
		    	jsonObject.put("miniprogram", miniprograms);
	        }
	        JSONObject data = new JSONObject();
	        //副标题
	        JSONObject first = new JSONObject();
        	first.put("value", msg.getFirst());
	        //左眼视力
	        JSONObject keyword1 = new JSONObject();
	        keyword1.put("value", left);
	        //右眼视力
	        JSONObject keyword2 = new JSONObject();
	        keyword2.put("value", right);
	        //测试时间
	        JSONObject keyword3 = new JSONObject();
	        keyword3.put("value", date);
	        //温馨提示
	        JSONObject remark = new JSONObject();
	        remark.put("value", msg.getRemark());
	        
	        data.put("first",first);
	        data.put("keyword1",keyword1);
	        data.put("keyword2",keyword2);
	        data.put("keyword3",keyword3);
	        data.put("remark",remark);
	 
	        jsonObject.put("data", data);
	 
	        String string = HttpClientUtils.sendPostJsonStr(postUrl, jsonObject.toJSONString());
	        JSONObject result = JSON.parseObject(string);
	        int errcode = result.getIntValue("errcode");
	        if(errcode == 0){
	            // 发送成功
	            System.out.println("筛查通知，发送成功");
	        } else {
	            // 发送失败
	            System.out.println("筛查通知，发送失败");
	        }
		
	}
}
