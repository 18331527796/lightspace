package com.threefriend.lightspace.mapper.schoolclient;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SchoolStudentRecordMapper {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private Integer schoolId;
	
	private Integer classId;
	
	private Integer gender;
	
	private Double visionLeftStr;
	
	private Double visionRightStr;
	
	private Integer semester;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}


	public Double getVisionLeftStr() {
		return visionLeftStr;
	}

	public void setVisionLeftStr(Double visionLeftStr) {
		this.visionLeftStr = visionLeftStr;
	}

	public Double getVisionRightStr() {
		return visionRightStr;
	}

	public void setVisionRightStr(Double visionRightStr) {
		this.visionRightStr = visionRightStr;
	}

	public Integer getSemester() {
		return semester;
	}

	public void setSemester(Integer semester) {
		this.semester = semester;
	}

	

}
