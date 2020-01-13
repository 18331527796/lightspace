package com.threefriend.lightspace.mapper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 用户角色表
 * 
 * @author Administrator
 *
 */
@Entity
public class UserRoleRelation {

	@Id
	@GeneratedValue
	// 主键
	private Integer id;
	// 用户id
	@Column(name = "u_id")
	private Integer userId;
	// 角色id
	@Column(name = "r_id")
	private Integer roleId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}
