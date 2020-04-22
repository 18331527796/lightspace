package com.threefriend.lightspace.mapper.xcx;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *	家长学生关联表
 */
@Entity
public class ParentStudentRelation {

	
	@Id
	@GeneratedValue
	// 主键
	private Integer id;
	
	private Integer parentId;
	
	private Integer studentId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	
	
}
