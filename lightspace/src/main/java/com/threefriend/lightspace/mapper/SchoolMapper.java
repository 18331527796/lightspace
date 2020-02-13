package com.threefriend.lightspace.mapper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 学校表
 * 
 * @author Administrator
 *
 */
@Entity
public class SchoolMapper {

	@Id
	@GeneratedValue
	// 主键
	private Integer id;
	// 学校名称
	private String name;
	// 具体地址
	private String address;
	// 所属地区
	@Column(name="region_id")
	private Integer regionId;
	// 所属地区
	@Column(name="region_name")
	private String regionName;
	//前台用
	private String value;
	
	public String getValue() {
		return value;
	}
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
		this.value = name;
	}
	public Integer getRegionId() {
		return regionId;
	}
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}


}
