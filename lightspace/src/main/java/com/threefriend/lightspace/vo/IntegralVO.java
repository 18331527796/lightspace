package com.threefriend.lightspace.vo;

import java.util.Date;

public class IntegralVO {
	
	public IntegralVO(Integer studentId,String studentName,String schoolName,Long sum) {
		this.studentId = studentId;
		this.sum = sum;
		this.studentName = studentName;
		this.schoolName = schoolName;
	}
	
	//学生Id、
	private Integer studentId;

	private String studentName;
	
	private String schoolName;
	//总积分
	private Long sum;
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public Long getSum() {
		return sum;
	}
	public void setSum(Long sum) {
		this.sum = sum;
	}
	
	
	
}
