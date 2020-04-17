package com.threefriend.lightspace.vo;

public class StudentWordVO {

	private Integer id;
	
	private String name;
	
	private Integer gender;
	
	private String time;
	//学校名称
	private String schoolName;
	//班级名称
	private String className;

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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}
	
	public void setGender(String gender) {
		if("男".equals(gender)) {
			this.gender = 0;
		}else {
			this.gender = 1;
		}
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	
}
