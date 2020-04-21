package com.threefriend.lightspace.aspect;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

public class TestAspect implements HandlerInterceptor {

	// 在请求处理之前进行调用（Controller方法调用之前
	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o)
			throws Exception {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM"); // 定义想要的格式
		String timeString = simpleDateFormat.format(new Date()).substring(0, 7); // 双重保险，最好能获得系统时间，以免第三方网站异常时程序无法运行
//		if (!timeString.equals("2020-04")) { // 我把时间设定成为2012年12月有效
//			return false;
//		}
		//每次操作的时间监控
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:dd").format(new Date()));
		return true;
	}

}
