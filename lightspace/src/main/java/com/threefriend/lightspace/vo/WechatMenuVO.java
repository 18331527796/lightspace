package com.threefriend.lightspace.vo;

import java.util.ArrayList;
import java.util.List;

import com.threefriend.lightspace.mapper.WechatMenuMapper;

public class WechatMenuVO {

private Integer id;
	
	private Integer pId;
	
	private String type;
	
	private String name;
	
	private String url;
	
	private String pagepath;
	
	private List<WechatMenuMapper> children = new ArrayList<WechatMenuMapper>();

	
	public List<WechatMenuMapper> getChildren() {
		return children;
	}

	public String getPagepath() {
		return pagepath;
	}

	public void setPagepath(String pagepath) {
		this.pagepath = pagepath;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
