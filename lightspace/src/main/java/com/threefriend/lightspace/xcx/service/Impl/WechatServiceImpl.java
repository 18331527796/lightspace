package com.threefriend.lightspace.xcx.service.Impl;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.jdom.JDOMException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.threefriend.Constants.WXPayConstants;
import com.threefriend.lightspace.enums.AccountEnums;
import com.threefriend.lightspace.enums.OrderStatusEnum;
import com.threefriend.lightspace.mapper.WechatMenuMapper;
import com.threefriend.lightspace.mapper.xcx.GzhUserMapper;
import com.threefriend.lightspace.mapper.xcx.IntegralMapper;
import com.threefriend.lightspace.mapper.xcx.OrderMapper;
import com.threefriend.lightspace.mapper.xcx.SpecificationsMapper;
import com.threefriend.lightspace.repository.GzhUserRepository;
import com.threefriend.lightspace.repository.IntegralRepository;
import com.threefriend.lightspace.repository.OrderRepository;
import com.threefriend.lightspace.repository.SpecificationsRepository;
import com.threefriend.lightspace.repository.WechatMenuRepository;
import com.threefriend.lightspace.util.RedisUtils;
import com.threefriend.lightspace.util.WXPayUtil;
import com.threefriend.lightspace.xcx.service.WechatService;
import com.threefriend.lightspace.xcx.util.MessageUtil;
import com.threefriend.lightspace.xcx.util.WeChatUtils;
import com.threefriend.lightspace.xcx.util.XmlUtil;
@Service
public class WechatServiceImpl implements WechatService{
	
	@Resource
	private RedisUtils redisUtil;
	@Autowired
	private GzhUserRepository gzh_dao;
	@Autowired
	private WechatMenuRepository wechat_menu;
	@Autowired
	private OrderRepository order_dao;
	@Autowired
	private SpecificationsRepository specifications_dao;
	@Autowired
	private IntegralRepository integral_dao;

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
			String eventKey = map.get("EventKey");
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
				
				}else if(MessageUtil.MESSAGE_CLICK.equals(eventType)){//点击事件
					Optional<WechatMenuMapper> findById = wechat_menu.findById(Integer.valueOf(eventKey));
					if(findById!=null) {
						WechatMenuMapper wechatMenuMapper = findById.get();
						content = wechatMenuMapper.getContent();
						message = MessageUtil.textMsg(toUserName, fromUserName, content);
					}
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
	
	
	 /**
     * 此函数会被执行多次，如果支付状态已经修改为已支付，则下次再调的时候判断是否已经支付，如果已经支付了，则什么也执行
     * @param request
     * @param response
     * @return
	 * @throws Exception 
     */
    @RequestMapping("payReport")
   // @RequestDescription("支付回调地址")
    @ResponseBody
    public String notifyWeiXinPay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("微信支付回调");
        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        String resultxml = new String(outSteam.toByteArray(), "utf-8");
        Map<String, String> params = WXPayUtil.xmlToMap(resultxml);
        outSteam.close();
        inStream.close();
        
        
        Map<String,String> return_data = new HashMap<String,String>();  
        if (!WXPayUtil.isSignatureValid(params, WXPayConstants.KEY)) {
            // 支付失败
        	return_data.put("return_code", "FAIL");  
            return_data.put("return_msg", "return_code不正确");
        	return WXPayUtil.mapToXml(return_data);
        } else {
            System.out.println("===============付款成功==============");
            // ------------------------------
            // 处理业务开始
            // ------------------------------
            // 此处处理订单状态，结合自己的订单数据完成订单状态的更新
            // ------------------------------
            return_data.put("return_code", "SUCCESS");  
            return_data.put("return_msg", "OK");  

            
            Integer orderId = Integer.valueOf(params.get("out_trade_no").split("-")[0]);
            OrderMapper order = order_dao.findById(orderId).get();
            if(!OrderStatusEnum.NEW.getMessage().equals(order.getStatus()))return WXPayUtil.mapToXml(return_data);
            //更改订单状态为支付成功
		    order.setStatus(OrderStatusEnum.SUCCESS.getMessage());
		    order_dao.save(order);
		    //库存减数量
		    SpecificationsMapper specifications = specifications_dao.findById(order.getSpecificationId()).get();
		    specifications.setStock(specifications.getStock()-order.getNumber());
		    specifications_dao.save(specifications);
		    
		    integral_dao.save(new IntegralMapper(order.getStudentId(), 0, specifications.getIntegral()*order.getNumber(), "兑换"+order.getProductName(), new Date()));
		    
		    
			return WXPayUtil.mapToXml(return_data);
        }
    }
    
	
}
