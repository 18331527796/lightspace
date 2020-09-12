package com.threefriend.lightspace.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SchoolRecordVO {

	private String name;
	
	private Integer classId;
	
	private Integer good;
	
	private Integer mild;
	
	private Integer moderate;
	
	private Integer serious;
	
	private Integer all;
	
	private Integer myopia;
	
	private String  myopiaRate;
	
	private List<SchoolRecordVO> children = new ArrayList<>();
	

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Integer getAll() {
		return all;
	}

	public void setAll(Integer all) {
		this.all = all;
	}

	public Integer getMyopia() {
		return myopia;
	}

	public void setMyopia(Integer myopia) {
		this.myopia = myopia;
	}

	public String getMyopiaRate() {
		return myopiaRate;
	}

	public void setMyopiaRate(String myopiaRate) {
		this.myopiaRate = myopiaRate;
	}

	public List<SchoolRecordVO> getChildren() {
		return children;
	}

	public void setChildren(List<SchoolRecordVO> children) {
		this.children = children;
	}
	
	
	
}
