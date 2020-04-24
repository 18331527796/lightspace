package com.threefriend.lightspace.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.threefriend.lightspace.enums.UrlEnums;

public class ProductVO {
	
private Integer id;
	
	private String name; 

	private List<String> pictures = new ArrayList<>();
	
	private String details;
	
	private Date genTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = UrlEnums.IMG_URL.getUrl()+details;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public List<String> getPictures() {
		return pictures;
	}

	public void setPictures(List<String> picture) {
		this.pictures = picture;
	}

	
}
