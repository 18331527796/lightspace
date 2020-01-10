package com.threefriend.lightspace.mapper;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 角色表
 * @author Administrator
 *
 */
@Entity
public class RoleMapper {

	@Id
	@GeneratedValue
	//主键
	private Integer id;
	//角色名称
	@Column(name="right_name")
	private String roleName;
	//创建时间
	@Column(name="gen_time")
	private Date genTime;
	//角色描述
	private String description;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Date getGenTime() {
		return genTime;
	}
	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
