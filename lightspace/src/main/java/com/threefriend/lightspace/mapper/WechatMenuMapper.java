package com.threefriend.lightspace.mapper;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 微信的自定义菜单表
 * 
 * @author Administrator
 *
 */
@Entity
public class WechatMenuMapper {

	@Id
	@GeneratedValue
	// 主键
	private Integer id;
	
	private Integer pId;
	
	private String type;
	
	private String name;
	
	private String url;

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
