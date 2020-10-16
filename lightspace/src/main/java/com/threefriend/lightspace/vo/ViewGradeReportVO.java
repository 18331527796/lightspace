package com.threefriend.lightspace.vo;

public class ViewGradeReportVO {
	
    private String name;
	
	private Integer all;
	
	private Integer good;
	
	private Integer mild;
	
	private Integer moderate; 
	
	private Integer serious;
	
	private String percentage;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAll() {
		return all;
	}

	public void setAll(Integer all) {
		this.all = all;
	}

	public Integer getGood() {
		return good;
	}

	public void setGood(Integer good) {
		this.good = good;
	}

	public Integer getMild() {
		return mild;
	}

	public void setMild(Integer mild) {
		this.mild = mild;
	}

	public Integer getModerate() {
		return moderate;
	}

	public void setModerate(Integer moderate) {
		this.moderate = moderate;
	}

	public Integer getSerious() {
		return serious;
	}

	public void setSerious(Integer serious) {
		this.serious = serious;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	
	
}
