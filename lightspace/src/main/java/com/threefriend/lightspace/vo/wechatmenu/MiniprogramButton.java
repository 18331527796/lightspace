package com.threefriend.lightspace.vo.wechatmenu;


public class MiniprogramButton extends Button{
	 private  String type ;
	 private  String url ;
	 private  String appid; 
	 private  String pagepath ;
	public String getType() {
		return type;
	}
	public String getUrl() {
		return url;
	}
	public String getAppid() {
		return appid;
	}
	public String getPagepath() {
		return pagepath;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public void setPagepath(String pagepath) {
		this.pagepath = pagepath;
	}
	 
}
