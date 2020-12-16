package com.threefriend.lightspace.mapper.train;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 方案组合 应16种（视觉训练 8 套   弱视训练 8 套）
 * @author Administrator
 *
 */
@Entity
public class TrainCombinationMapper {

	@Id
	@GeneratedValue
	// 主键
	private Integer id;
	
	private String name;
	
	private String pName;
	
	private String combination;
	
	private String openId;
	
	private Integer isShow = 1;
	
	private String genTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());;

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

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getCombination() {
		return combination;
	}

	public void setCombination(String combination) {
		this.combination = combination;
	}

	public String getGenTime() {
		return genTime;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}


	
	
}
