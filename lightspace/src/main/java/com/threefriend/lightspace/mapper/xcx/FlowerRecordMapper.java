package com.threefriend.lightspace.mapper.xcx;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 送花记录表
 * @author Administrator
 *
 */
@Entity
public class FlowerRecordMapper {

	@Id
	@GeneratedValue
	private Integer id;
	
	private Integer parentId;
	
	private Integer frequency ;
	
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

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}
	
	
}
