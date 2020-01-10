package com.threefriend.lightspace.mapper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 用户角色表
 * @author Administrator
 *
 */
@Entity
public class UserRoleRelation {

	@Id
	@GeneratedValue
	//主键
	private Integer id;
	//用户id
	@Column(name="u_id")
	private Integer	UId;
	//角色id
	@Column(name="r_id")
	private Integer RId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUId() {
		return UId;
	}
	public void setUId(Integer uId) {
		UId = uId;
	}
	public Integer getRId() {
		return RId;
	}
	public void setRId(Integer rId) {
		RId = rId;
	}

	
	
}
