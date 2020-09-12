package com.threefriend.lightspace.vo;

import java.util.Map;

public class StatisticsVO {
	
	private String name;
	
	private Integer value;
	
	private String url;
	
	private Map<String, String> itemStyle;
	

	public Map<String, String> getItemStyle() {
		return itemStyle;
	}

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
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public StatisticsVO (String name,Integer value,String url,Map<String, String> itemStyle) {
        this.name=name;
        this.value=value;
        this.url=url;
        this.itemStyle=itemStyle;
	}
	
}
