package com.price.aspect;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class TestAspect implements HandlerInterceptor {

	// 在请求处理之前进行调用（Controller方法调用之前
	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o)
			throws Exception {
		httpServletResponse.setHeader("Access-Control-Allow-Origin","*");
		//每次操作的时间监控
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:dd").format(new Date()));
		
		return true;
	}

}
