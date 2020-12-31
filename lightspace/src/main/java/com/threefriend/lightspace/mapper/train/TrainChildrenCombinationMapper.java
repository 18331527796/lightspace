package com.threefriend.lightspace.mapper.train;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TrainChildrenCombinationMapper {

	@Id
	@GeneratedValue
	private Integer id;
	
	private Integer combinationId;
	
	private String combinationName;
	
	private Integer childrenId;
	
	private Integer isOpen = 2;
	
	private Integer isSuccess = 2;
	
	private Integer sort;
	
	private String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	
	private String successTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCombinationId() {
		return combinationId;
	}

	public void setCombinationId(Integer combinationId) {
		this.combinationId = combinationId;
	}

	public Integer getChildrenId() {
		return childrenId;
	}

	public void setChildrenId(Integer childrenId) {
		this.childrenId = childrenId;
	}

	public Integer getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}

	public Integer getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Integer isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getTime() {
		return time;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getCombinationName() {
		return combinationName;
	}

	public void setCombinationName(String combinationName) {
		this.combinationName = combinationName;
	}

	public String getSuccessTime() {
		return successTime;
	}

	public void setSuccessTime(Date successTime) {
		this.successTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(successTime);
	}
	
	
}
