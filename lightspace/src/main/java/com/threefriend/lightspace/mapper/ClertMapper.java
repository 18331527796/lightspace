package com.threefriend.lightspace.mapper;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ClertMapper {

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
	// 所属机构id
	private Integer PartnershipId;
	// 所属机构
	private String PartnershipName;
	//登录状态(0未登录 1登录)
	private Integer state = 0;
	//关联的小程序账号
	private Integer parentId;
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
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	public Integer getPartnershipId() {
		return PartnershipId;
	}
	public void setPartnershipId(Integer partnershipId) {
		PartnershipId = partnershipId;
	}
	public String getPartnershipName() {
		return PartnershipName;
	}
	public void setPartnershipName(String partnershipName) {
		PartnershipName = partnershipName;
	}
	public String getGenTime() {
		return genTime;
	}
	public void setGenTime(Date genTime) {
		this.genTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(genTime);
	}
	
	
}
