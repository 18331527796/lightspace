package com.threefriend.lightspace.xcx.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface WechatService {
	
	void checkSignature(Map<String, String> params,HttpServletRequest request, HttpServletResponse response)throws Exception;

	void responseEvent(HttpServletRequest request, HttpServletResponse response)throws Exception;
}
