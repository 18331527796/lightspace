package com.threefriend.dingding.dto;

public class DeptDTO {
	
	public DeptDTO() {
		
	}
	
	public DeptDTO(String id,String name) {
		this.dept_id=id;
		this.name = name;
	}

	private String dept_id;
	
	private String name;

	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
