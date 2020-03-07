package com.threefriend.lightspace.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class OptotypeVO {

	private Integer levelId;

	private String levelName;
	
	private String levelName5;

	private String showName;

	private List<Map<String, String>> path = new ArrayList<>();

	
	public String getLevelName5() {
		return levelName5;
	}

	public void setLevelName5(String levelName5) {
		this.levelName5 = levelName5;
	}

	public Integer getLevelId() {
		return levelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public List<Map<String, String>> getPath() {
		return path;
	}

	public void setPath(List<Map<String, String>> path) {
		this.path = path;
	}


}
