package com.threefriend.lightspace.mapper;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 标签
 * 
 * @author Administrator
 *
 */
@Entity
public class LabelMapper {
	

	@Id
	@GeneratedValue
	// 主键
	private Integer id;
	// 标签名
	private String name;
	
	private String path;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	

}
