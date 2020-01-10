package com.threefriend.lightspace.mapper;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserMapper {

	@Id
	@GeneratedValue
	// 主键
	private Integer id;
	// 用户姓名
	private String name;
	// 微信昵称
	@Column(name="wechat_name")
	private String wechatName;
	// 用户账号
	@Column(name="login_name")
	private String loginName;
	// 用户密码
	private String password;
	// 创建时间
	@Column(name="gen_time")
	private Date genTime;
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
	public String getWechatName() {
		return wechatName;
	}
	public void setWechatName(String wechatName) {
		this.wechatName = wechatName;
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
	public Date getGenTime() {
		return genTime;
	}
	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

}
