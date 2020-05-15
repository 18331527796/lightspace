package com.threefriend.lightspace.vo;

import java.util.ArrayList;
import java.util.List;


public class TeacherVO {

	// 主键
	private Integer id;
	//姓名
	private String name;
	//手机号
	private String phone;
	//密码
	private String password;
	//学校Id
	private Integer schoolId;
	//学校名称	
	private String schoolName;
	//班级Id
	private Integer classId;
	//班级名称
	private String className;
	// 前台需要
	private List<Integer> tea_cat = new ArrayList<>();
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public Integer getClassId() {
		return classId;
	}
	public void setClassId(Integer classId) {
		this.classId = classId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public List<Integer> getTea_cat() {
		return tea_cat;
	}
	public void setTea_cat(List<Integer> tea_cat) {
		this.tea_cat = tea_cat;
	}
	
	
}
