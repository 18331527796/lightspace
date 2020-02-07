package com.threefriend.lightspace.vo;

public class StatisticsVO {
	
	private String name;
	
	private Integer value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
	public StatisticsVO (String name,Integer value) {
        this.name=name;
        this.value=value;
	}
	
}
