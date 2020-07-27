package com.threefriend.lightspace.mapper.xcx;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
/**
 * 校准表
 * @author Administrator
 *
 */
@Entity
public class CalibrationMapper {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String openId;
	
	private String lv;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getLv() {
		return lv;
	}

	public void setLv(String lv) {
		this.lv = lv;
	}
	
	

}
