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

	@Column(name = "level_id")
	private Integer levelId;

	@Column(name = "level_name")
	private String levelName;

	@Column(name = "show_name")
	private String showName;

	@Column(name = "path_str")
	private String pathStr;

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
