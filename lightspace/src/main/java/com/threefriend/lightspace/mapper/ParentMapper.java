package com.threefriend.lightspace.mapper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 小程序用户
 *
 */
@Entity
public class ParentMapper {

	@Id
	@GeneratedValue
	// 主键
	private Integer id;
	// 家长昵称
	private String name;
	// 家长手机号
	private Long phone;
	// 家长openId
	private String openId;
	//积分
	private Long Intrgral;
	
	public Long getIntrgral() {
		return Intrgral;
	}
	public void setIntrgral(Long intrgral) {
		Intrgral = intrgral;
	}
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
	public Long getPhone() {
		return phone;
	}
	public void setPhone(Long phone) {
		this.phone = phone;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}

	
}
