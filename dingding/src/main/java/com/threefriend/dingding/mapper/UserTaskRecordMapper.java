package com.threefriend.dingding.mapper;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

/**
 * 用来记录每日打卡完成情况
 * @author Administrator
 *
 */
@Entity
public class UserTaskRecordMapper {
	
	@Id
	@GeneratedValue
	private Integer Id;

	private String userid;
	
	private String name;
	
	private String deptId;
	
	private String isSuccess;
	
	private Date time;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}


	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}


	
	
}
