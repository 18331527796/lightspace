package com.threefriend.lightspace.mapper.xcx;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *  商品表
 */
@Entity
public class ProductMapper {

	@Id
	@GeneratedValue
	// 主键
	private Integer id;
	
	private String name; 

	private String picture;
	
	private String details;
	
	private String genTime;

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

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(genTime);
	}
	
	
}
