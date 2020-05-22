package com.threefriend.lightspace.xcx.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.xcx.service.Impl.WechatServiceImpl;


/**
 * 微信回调控制器
 */
@RestController
@RequestMapping("/xcx")
public class WeChatController {

	@Autowired
	private WechatServiceImpl wechat_impl;
	
	@RequestMapping(value="/checkSignature",method=RequestMethod.GET)
	public void checkSignature(@RequestParam Map<String, String> params,HttpServletRequest request, HttpServletResponse response)throws Exception {
		wechat_impl.checkSignature(params, request,  response);
	}
	
	@RequestMapping(value="/checkSignature",method=RequestMethod.POST)
	public void responseEvent(HttpServletRequest request, HttpServletResponse response)throws Exception {
		wechat_impl.responseEvent(request,  response);
	}
	
	@RequestMapping("/payReport")
	public void payReport(HttpServletRequest request, HttpServletResponse response)throws Exception {
		wechat_impl.notifyWeiXinPay(request,  response);
	}
}
