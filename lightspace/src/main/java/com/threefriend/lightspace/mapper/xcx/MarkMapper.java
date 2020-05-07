package com.threefriend.lightspace.mapper.xcx;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *  每日签到
 */
@Entity
public class MarkMapper {

	@Id
	@GeneratedValue
	// 主键
	private Integer id;
	
	private Integer parentId;
	
	private Date genTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}
	
	
}
