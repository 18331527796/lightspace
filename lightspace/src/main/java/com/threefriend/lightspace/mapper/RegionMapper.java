package com.threefriend.lightspace.mapper;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 地区表
 * @author Administrator
 *
 */
@Entity
public class RegionMapper {

	@Id
	private Integer id;
	
	private String name;
	
	private Integer parentid;
	//顺序
	private Integer vieworder;
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
	public Integer getParentId() {
		return parentid;
	}
	public void setParentId(Integer parentId) {
		this.parentid = parentId;
	}
	public Integer getVieworder() {
		return vieworder;
	}
	public void setVieworder(Integer vieworder) {
		this.vieworder = vieworder;
	}
	
	
}
