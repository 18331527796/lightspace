package com.threefriend.lightspace.enums;

public enum CorrectEnums {
	
	NO(0,"未校验"),
	YES(1,"一校验");
	
	private Integer code;
	
	private String message;
	
	CorrectEnums(Integer code,String message){
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
