package com.threefriend.constants;

import java.util.Date;

import org.springframework.util.StringUtils;

import com.threefriend.dingding.util.DingDingUtils;

public class DingDingAccessToken {

	private DingDingAccessToken() {
		
	}
	
	private static String token = "";
	
	private static long time = 0;

	public static String getToken() {
		if(StringUtils.isEmpty(token)||new Date().getTime()-time>7000000) {
			token = DingDingUtils.getToken();
			time = new Date().getTime();
		}
		return token;
	}

	public static void setToken(String token) {
		DingDingAccessToken.token = token;
	}
	
	
}
