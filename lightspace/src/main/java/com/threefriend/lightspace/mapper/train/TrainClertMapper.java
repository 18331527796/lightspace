package com.threefriend.lightspace.mapper.train;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TrainClertMapper {

	@Id
	@GeneratedValue
	// 主键
	private Integer id;
	// 姓名
	private String name;
	// 账号
	private String loginName;
	// 密码
	private String password;
	//登录状态(0未登录 1登录)
	private Integer state = 0;
	//关联的小程序账号
	private String openId;
	//创建时间
	private String genTime;
	
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
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public void setGenTime(String genTime) {
		this.genTime = genTime;
	}
	public String getGenTime() {
		return genTime;
	}
	public void setGenTime(Date genTime) {
		this.genTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(genTime);
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	
}
