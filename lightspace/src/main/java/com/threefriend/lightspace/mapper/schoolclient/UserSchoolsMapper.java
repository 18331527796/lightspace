package com.threefriend.lightspace.mapper.schoolclient;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 教育集团关联表
 * 
 * @author Administrator
 *
 */
@Entity
public class UserSchoolsMapper {
	

	@Id
	@GeneratedValue
	// 主键
	private Integer id;
	// 所属学校id
	private Integer schoolId;
	
	private Integer userId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
