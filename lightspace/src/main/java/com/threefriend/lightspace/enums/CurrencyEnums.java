package com.threefriend.lightspace.enums;

/**
 * 角色枚举类
 * @author Administrator
 *
 */
public enum CurrencyEnums {
	
	ADMIN(1,"超级管理员"),
	PROVINCE(2,"省级管理员"),
	SCHOOL(3,"学校管理员"),
	CLASS(4,"班级管理员"),
	ORDINARY(5,"普通账号"),
	;
	
	private Integer code;
	
	private String message;
	
	CurrencyEnums(Integer code,String message){
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
	
}
