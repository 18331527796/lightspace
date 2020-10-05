package com.threefriend.lightspace.mapper.schoolclient;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 班级表
 * 
 * @author Administrator
 *
 */
@Entity
public class SchoolClassesMapper {
	

	@Id
	@GeneratedValue
	// 主键
	private Integer id;
	// 原班级Id
	private Integer classId;
	// 班级名称
	private String name;
	// 所属学校id
	private Integer schoolId;
	
	private String schoolName;
	//年级
	private Integer grade;
	//班
	private Integer classNumber;
	//学期
	private Integer semesterId;
	
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
	public Integer getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public Integer getClassNumber() {
		return classNumber;
	}
	public void setClassNumber(Integer classNumber) {
		this.classNumber = classNumber;
	}
	public Integer getSemesterId() {
		return semesterId;
	}
	public void setSemesterId(Integer semesterId) {
		this.semesterId = semesterId;
	}
	public Integer getClassId() {
		return classId;
	}
	public void setClassId(Integer classId) {
		this.classId = classId;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

}
