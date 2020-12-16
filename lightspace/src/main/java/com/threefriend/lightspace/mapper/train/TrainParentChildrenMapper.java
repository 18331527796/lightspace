package com.threefriend.lightspace.mapper.train;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class TrainParentChildrenMapper {

	@Id
	@GeneratedValue
	private Integer id;
	
	private Integer childrenId;
	
	private String openId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getChildrenId() {
		return childrenId;
	}

	public void setChildrenId(Integer childrenId) {
		this.childrenId = childrenId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public TrainParentChildrenMapper() {}
	public TrainParentChildrenMapper(Integer childrenId , String openId) {
		this.childrenId = childrenId;
		this.openId = openId;
	}
}
