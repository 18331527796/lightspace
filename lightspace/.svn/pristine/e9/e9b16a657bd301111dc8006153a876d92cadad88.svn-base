package com.threefriend.lightspace.mapper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 地区表
 * @author Administrator
 *
 */
@Entity
public class RegionMapper {

	@Id
	@GeneratedValue
	//主键
	private Integer id;
	//地区名称
	private String name;
	//地区等级
	private String type;
	//父级
	@Column(name="parent_id")
	private Integer ParentId;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getParentId() {
		return ParentId;
	}
	public void setParentId(Integer parentId) {
		ParentId = parentId;
	}
}
