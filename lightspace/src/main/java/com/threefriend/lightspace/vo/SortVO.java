package com.threefriend.lightspace.vo;

public class SortVO{

	private Integer studentId;
	
	private String studentName;
	
	private Integer gender;
	
	private Integer correct;
	
	private Double avgRecord;

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
	

	public Double getAvgRecord() {
		return avgRecord;
	}

	public void setAvgRecord(Double avgRecord) {
		this.avgRecord = avgRecord;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getCorrect() {
		return correct;
	}

	public void setCorrect(Integer correct) {
		this.correct = correct;
	}
	

}
