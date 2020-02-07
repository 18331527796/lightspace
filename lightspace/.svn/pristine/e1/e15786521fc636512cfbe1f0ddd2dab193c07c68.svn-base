package com.threefriend.lightspace.mapper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 角色权限表
 * @author Administrator
 *
 */
@Entity
public class RoleRightRelation {

	@Id
	@GeneratedValue
	//主键
	private Integer id;
	//角色
	@Column(name="role_id")
	private Integer roleId;
	//权限
	@Column(name="right_id")
	private Integer rightId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getRightId() {
		return rightId;
	}
	public void setRightId(Integer rightId) {
		this.rightId = rightId;
	}
	

	
}
