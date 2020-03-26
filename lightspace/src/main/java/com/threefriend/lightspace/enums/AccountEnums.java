package com.threefriend.lightspace.enums;

public enum AccountEnums {
	
	APIKEY("wx6992c25f5c91f13c"),
	SECRETKEY("f31ee1679f8d2b452fa961437d996f9e"),
	;
	
	private String url;

	
	
	private AccountEnums(String url) {
		this.url = url;
	}



	public String getUrl() {
		return url;
	}

	
}
