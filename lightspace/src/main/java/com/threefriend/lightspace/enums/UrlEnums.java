package com.threefriend.lightspace.enums;

/**
 *	路径枚举类
 */
public enum UrlEnums {
	//线下
	//CODE_PATH("F:\\code\\"),
	//线上
	CODE_PATH(System.getProperty("catalina.home")+"\\webapps\\code\\"),
	//接口B
	WECHAT_XCX_CODE_PAHT("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="),
	//线上上传
	TOMCAT_IMG(System.getProperty("catalina.home")+"\\webapps\\images"),
	//线上
	IMG_URL("https://www.guangliangkongjian.com/images/"),
	//线下
	//IMG_URL("F:\\upload\\imgs\\"),
	//线下
	//FILE_PATH("F:\\"),
	//线上
	FILE_PATH(System.getProperty("catalina.home")+"\\webapps\\file\\"),
	;
	
	private String url;

	
	
	private UrlEnums(String url) {
		this.url = url;
	}



	public String getUrl() {
		return url;
	}

	
}
