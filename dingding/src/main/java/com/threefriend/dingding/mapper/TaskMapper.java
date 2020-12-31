package com.threefriend.dingding.mapper;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TaskMapper {

	@Id
	@GeneratedValue
	private Integer id;
	
	private String content;
	
	private Integer level;
	
	private Integer isShow = 1;
	
	private String genTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getGenTime() {
		return genTime;
	}

	public void setGenTime(String genTime) {
		this.genTime = genTime;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	
	
}
