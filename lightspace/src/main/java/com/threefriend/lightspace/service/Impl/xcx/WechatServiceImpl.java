package com.threefriend.lightspace.service.Impl.xcx;


import java.io.PrintWriter;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threefriend.lightspace.enums.AccountEnums;
import com.threefriend.lightspace.mapper.xcx.GzhUserMapper;
import com.threefriend.lightspace.repository.GzhUserRepository;
import com.threefriend.lightspace.service.xcx.WechatService;
import com.threefriend.lightspace.util.RedisUtils;
import com.threefriend.lightspace.util.xcx.MessageUtil;
import com.threefriend.lightspace.util.xcx.WeChatUtils;
import com.threefriend.lightspace.util.xcx.XmlUtil;
@Service
public class WechatServiceImpl implements WechatService{
	
	@Resource
	private RedisUtils redisUtil;
	@Autowired
	private GzhUserRepository gzh_dao;

	/* 
	 * 有效url认证
	 */
	@Override
	public void checkSignature(Map<String, String> params,HttpServletRequest request, HttpServletResponse response) throws Exception {
		String signature = params.get("signature");
		String timestamp = params.get("timestamp");
		String nonce = params.get("nonce");
		String echostr = params.get("echostr");
		PrintWriter out = response.getWriter();
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (WeChatUtils.checkSignature(signature, timestamp, nonce)) {
            System.out.print("echostr=" + echostr);
            out.print(echostr);
        }

        out.close();
        out = null;
	}

	/* 
	 * 用户关注/取消关注回调方法
	 */
	@Override
	public synchronized void responseEvent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String message = "success";
		try {
			//把微信返回的xml信息转义成map
			Map<String, String> map = XmlUtil.xmlToMap(request);
			System.out.println(map.toString());
			String fromUserName = map.get("FromUserName");//消息来源用户标识
			String toUserName = map.get("ToUserName");//消息目的用户标识
			String msgType = map.get("MsgType");//消息类型
			String content = map.get("Content");//消息内容
			String eventType = map.get("Event");
			if(MessageUtil.MSGTYPE_EVENT.equals(msgType)){//如果为事件类型
				if(MessageUtil.MESSAGE_SUBSCIBE.equals(eventType)){//处理订阅事件
					String ACCESS_TOKEN = redisUtil.get("GZHTOKEN");
					if(ACCESS_TOKEN==null||"".equals(ACCESS_TOKEN)) {
					    ACCESS_TOKEN = WeChatUtils.findAccessToken(AccountEnums.GZHAPPID.getUrl(), AccountEnums.GZHSECRET.getUrl());
						redisUtil.setValueTime("GZHTOKEN", ACCESS_TOKEN, 7000);
					}
					gzh_dao.save(WeChatUtils.findWxUserInfo(ACCESS_TOKEN, fromUserName));
					System.out.println(fromUserName+"用户关注了公众号");
					message = MessageUtil.subscribeForText(toUserName, fromUserName);
				}else if(MessageUtil.MESSAGE_UNSUBSCIBE.equals(eventType)){//处理取消订阅事件
					GzhUserMapper findByOpenid = gzh_dao.findByOpenid(fromUserName);
					if(findByOpenid!=null)gzh_dao.delete(findByOpenid);
					System.out.println(fromUserName+"用户取消了关注");
					message = MessageUtil.unsubscribe(toUserName, fromUserName);
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}finally{
			out.println(message);
			if(out!=null){
				out.close();
			}
		}

    }
    

	
}
