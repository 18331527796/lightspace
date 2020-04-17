package com.threefriend.lightspace.mapper;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
/**
 *	模板表
 */
@Entity
public class MsgTempMapper {
	@Id
	@GeneratedValue
	private Integer id;
	
	private Integer selected;
	
	private String name;
	
	private String first;
	
	private String remark;
	
	private String type; 
	
	private String url;
	
	private Integer carry;//(0:不带跳转 1:跳url 2:跳小程序)

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSelected() {
		return selected;
	}

	public void setSelected(Integer selected) {
		this.selected = selected;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getCarry() {
		return carry;
	}

	public void setCarry(Integer carry) {
		this.carry = carry;
	}
	
	
}
