package com.threefriend.lightspace.vo;

import java.io.Serializable;
import java.util.List;

public class SchoolVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String name;
	
	private List<ClassesVO> children;

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

	public List<ClassesVO> getChildren() {
		return children;
	}

	public void setChildren(List<ClassesVO> children) {
		this.children = children;
	}

	

}
