package com.price.enums;

/**
 *	路径枚举类
 */
public enum UrlEnums {
	//线上上传
	TOMCAT_IMG(System.getProperty("catalina.home")+"\\webapps\\images"),
	//线上
	IMG_URL("http://103.242.175.188:8095/images/"),
	;
	
	private String url;

	
	
	private UrlEnums(String url) {
		this.url = url;
	}



	public String getUrl() {
		return url;
	}

	
}
