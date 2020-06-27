package com.threefriend.lightspace.mapper;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class TaskExamineConfigMapper {

	@Id
	@GeneratedValue
	private Integer id;
	
	private String details;
	
	private Integer status;
	
	private String statusStr;
	
	private String path;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
		if(status==1) {
			this.statusStr = "使用中";
		}else {
			this.statusStr = "未使用";
		}
	}

	public String getStatusStr() {
		return statusStr;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
}
