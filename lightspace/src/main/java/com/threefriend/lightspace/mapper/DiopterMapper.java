package com.threefriend.lightspace.mapper;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class DiopterMapper {
	
	public DiopterMapper(StudentMapper po) {
		this.studentId=po.getId();
		this.classId=po.getClassesId();
		this.className=po.getClassesName();
		this.schoolId=po.getSchoolId();
		this.schoolName=po.getSchoolName();
		this.gender=po.getGender();
		this.studentName=po.getName();
		this.diopterLeft=po.getDiopterLeft();
		this.diopterRight=po.getDiopterRight();
	}
	
	public DiopterMapper() { }
	
	@Id
	@GeneratedValue
	// 主键
	private Integer id;
	// 左眼屈光度
	private String diopterLeft;
	// 右眼屈光度
	private String diopterRight;
	//学生id
	private Integer studentId;
	private Integer classId;
	private String className;
	private Integer schoolId;
	private String schoolName;
	//性别
	private Integer gender;
	// 姓名
	private String studentName;
	//
	private String genTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDiopterLeft() {
		return diopterLeft;
	}
	public void setDiopterLeft(String diopterLeft) {
		this.diopterLeft = diopterLeft;
	}
	public String getDiopterRight() {
		return diopterRight;
	}
	public void setDiopterRight(String diopterRight) {
		this.diopterRight = diopterRight;
	}
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
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
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getGenTime() {
		return genTime;
	}
	public void setGenTime(String genTime) {
		this.genTime = genTime;
	}
	
	
}
