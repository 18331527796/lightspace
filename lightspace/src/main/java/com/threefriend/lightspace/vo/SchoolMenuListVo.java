package com.threefriend.lightspace.vo;

import java.util.List;

import com.threefriend.lightspace.mapper.RightMapper;
import com.threefriend.lightspace.mapper.schoolclient.SchoolRightMapper;

public class SchoolMenuListVo {
	
	private Integer id;
	
	private String authName;
	
	private String path;
	
	private List<SchoolRightMapper> children;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	public List<SchoolRightMapper> getChildren() {
		return children;
	}

	public void setChildren(List<SchoolRightMapper> children) {
		this.children = children;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
	

}
