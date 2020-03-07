package com.threefriend.lightspace.mapper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 视标表（小程序）
 * 
 * @author Administrator
 *
 */
@Entity
public class OptotypeMapper {

	@Id
	@GeneratedValue
	// 主键
	private Integer id;

	private Integer levelId;

	private String levelName;
	
	private String levelName5;

	private String showName;

	private String pathStr;

	
	public String getLevelName5() {
		return levelName5;
	}

	public void setLevelName5(String levelName5) {
		this.levelName5 = levelName5;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getPathStr() {
		return pathStr;
	}

	public void setPathStr(String pathStr) {
		this.pathStr = pathStr;
	}


}
