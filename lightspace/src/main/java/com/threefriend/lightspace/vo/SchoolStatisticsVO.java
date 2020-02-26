package com.threefriend.lightspace.vo;

import java.util.List;

public class SchoolStatisticsVO {
	
	private Integer schoolTotal;
	
	private Integer schoolTest;
	
	private Integer schoolNormal;
	
	private List<List<StatisticsVO>> totalData;
	
	private List<List<StatisticsVO>> testData;
	
	private List<List<StatisticsVO>> normalData;

	public Integer getSchoolTotal() {
		return schoolTotal;
	}

	public void setSchoolTotal(Integer schoolTotal) {
		this.schoolTotal = schoolTotal;
	}

	public Integer getSchoolTest() {
		return schoolTest;
	}

	public void setSchoolTest(Integer schoolTest) {
		this.schoolTest = schoolTest;
	}

	public Integer getSchoolNormal() {
		return schoolNormal;
	}

	public void setSchoolNormal(Integer schoolNormal) {
		this.schoolNormal = schoolNormal;
	}

	public List<List<StatisticsVO>> getTotalData() {
		return totalData;
	}

	public void setTotalData(List<List<StatisticsVO>> totalData) {
		this.totalData = totalData;
	}

	public List<List<StatisticsVO>> getTestData() {
		return testData;
	}

	public void setTestData(List<List<StatisticsVO>> testData) {
		this.testData = testData;
	}

	public List<List<StatisticsVO>> getNormalData() {
		return normalData;
	}

	public void setNormalData(List<List<StatisticsVO>> normalData) {
		this.normalData = normalData;
	}
	
	

}
