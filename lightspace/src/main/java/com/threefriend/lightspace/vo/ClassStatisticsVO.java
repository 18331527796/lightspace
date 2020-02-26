package com.threefriend.lightspace.vo;

import java.util.List;

public class ClassStatisticsVO {
	
	private Integer totalStudent;
	private Integer correctedStudent;
	private Integer uncorrectStudent;
	private List<List<StatisticsVO>> data;
	public Integer getTotalStudent() {
		return totalStudent;
	}
	public void setTotalStudent(Integer totalStudent) {
		this.totalStudent = totalStudent;
	}
	public Integer getCorrectedStudent() {
		return correctedStudent;
	}
	public void setCorrectedStudent(Integer correctedStudent) {
		this.correctedStudent = correctedStudent;
	}
	public Integer getUncorrectStudent() {
		return uncorrectStudent;
	}
	public void setUncorrectStudent(Integer uncorrectStudent) {
		this.uncorrectStudent = uncorrectStudent;
	}
	public List<List<StatisticsVO>> getData() {
		return data;
	}
	public void setData(List<List<StatisticsVO>> data) {
		this.data = data;
	}
	
	

}
