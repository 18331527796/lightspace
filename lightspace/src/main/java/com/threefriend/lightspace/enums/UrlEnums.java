package com.threefriend.lightspace.enums;

public enum UrlEnums {
	
	CODE_PATH("F:\\code\\"),
	//WECHAT_XCX_CODE_PAHT("https://api.weixin.qq.com/wxa/getwxacode?access_token="),
	//接口A
	WECHAT_XCX_CODE_PAHT("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="),
	//接口B
	TOMCAT_IMG(System.getProperty("catalina.home")+"\\webapps\\images"),
	//线上
	//IMG_URL("https://www.guangliangkongjian.com/images/"),
	//线下
	IMG_URL("F:\\upload\\imgs\\"),
	;
	
	private String url;

	
	
	private UrlEnums(String url) {
		this.url = url;
	}



	public String getUrl() {
		return url;
	}

	
}
