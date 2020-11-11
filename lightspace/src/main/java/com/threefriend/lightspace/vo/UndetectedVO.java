package com.threefriend.lightspace.vo;

import java.util.List;

import com.threefriend.lightspace.mapper.StudentMapper;

public class UndetectedVO {

	private String name;
	
	private int undetectedNumber;
	
	private List<UndetectedVO> classData;
	
	private List<StudentMapper> studentData;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getUndetectedNumber() {
		return undetectedNumber;
	}
	public void setUndetectedNumber(int undetectedNumber) {
		this.undetectedNumber = undetectedNumber;
	}
	public List<StudentMapper> getStudentData() {
		return studentData;
	}
	public void setStudentData(List<StudentMapper> studentData) {
		this.studentData = studentData;
	}
	public List<UndetectedVO> getClassData() {
		return classData;
	}
	public void setClassData(List<UndetectedVO> classData) {
		this.classData = classData;
	}
	
}
