package com.threefriend.lightspace.enums;

public enum AnswerTypeEnums implements CodeEnum{
	
	SINGLE(1,"单选题"),

	MULTIPLE(2,"多选题"),
	;
	
	AnswerTypeEnums(Integer code,String message) {
		this.code = code;
		this.message = message;
	}

	
	private Integer code;
	private String message; 
	@Override
	public Integer getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
