package com.threefriend.lightspace.enums;

public enum UrlEnums {
	
	CODE_PATH("F:\\code\\"),
	WECHAT_XCX_CODE_PAHT("https://api.weixin.qq.com/wxa/getwxacode?access_token="),
	//WECHAT_XCX_CODE_PAHT("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="),
	;
	
	private String url;

	
	
	private UrlEnums(String url) {
		this.url = url;
	}



	public String getUrl() {
		return url;
	}

	
}
