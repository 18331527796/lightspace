package com.threefriend.lightspace.vo;

import java.util.ArrayList;
import java.util.List;

public class OneStatisticsVO {

	private String name;
	
	private List<Double> yDataList=new ArrayList<>();
	
	private List<String> xDataList=new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Double> getyDataList() {
		return yDataList;
	}

	public void setyDataList(List<Double> yDataList) {
		this.yDataList = yDataList;
	}

	public List<String> getxDataList() {
		return xDataList;
	}

	public void setxDataList(List<String> xDataList) {
		this.xDataList = xDataList;
	}
	
	
}
