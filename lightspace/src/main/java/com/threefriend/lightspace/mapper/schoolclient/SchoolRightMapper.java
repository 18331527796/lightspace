package com.threefriend.lightspace.mapper.schoolclient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


/**
 * 权限表
 * @author Administrator
 *
 */
@Entity
public class SchoolRightMapper {
	
	@Id
	@GeneratedValue
	//主键
	private Integer id;
	//权限名称
	@Column(name="right_name")
	private String authName;
	//权限描述
	private String description;
	//跳转路径
	private String path;
	//父级权限
	@Column(name="p_id")
	private Integer pId;
	
	public Integer getpId() {
		return pId;
	}
	public void setpId(Integer pId) {
		this.pId = pId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAuthName() {
		return authName;
	}
	public void setAuthName(String authName) {
		this.authName = authName;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

	
}
