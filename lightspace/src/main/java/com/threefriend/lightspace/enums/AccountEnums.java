package com.threefriend.lightspace.enums;

/**
 * wechatconfig
 * @author Administrator
 *
 */
public enum AccountEnums {
	
	APIKEY("wx6992c25f5c91f13c"),
	SECRETKEY("f31ee1679f8d2b452fa961437d996f9e"),
	GZHAPPID("wx130d58f71d7978d7"),
	GZHSECRET("f342831bea3d4941b85c34d0d5bc60ec"),
	LIGHTSPACE("www.guangliangkongjian.com"),
	//训练的小程序参数
	TRAINAPIKEY("wx293d274148d756c1"),
	TRAINSECRETKEY("306922f557d29d998cc2935f274f7305"),
	;
	
	private String url;

	
	
	private AccountEnums(String url) {
		this.url = url;
	}



	public String getUrl() {
		return url;
	}

	
}
