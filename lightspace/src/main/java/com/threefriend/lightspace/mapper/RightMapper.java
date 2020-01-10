package com.threefriend.lightspace.mapper;

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
public class RightMapper {
	
	@Id
	@GeneratedValue
	//主键
	private Integer id;
	//权限名称
	@Column(name="right_name")
	private String rightName;
	//权限描述
	private String description;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRightName() {
		return rightName;
	}
	public void setRightName(String rightName) {
		this.rightName = rightName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	

	
}
