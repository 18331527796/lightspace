package com.threefriend.lightspace.enums;

public enum TaskExamineStatesEnum implements CodeEnum{
	NULL(0,"未审核"),
	EXAMINE(1,"已通过"),
	UNEXAMINE(2,"未通过"),
	;

	
	private Integer code;
	
	private String message;
	
	TaskExamineStatesEnum(Integer code,String message){
		this.code = code;
		this.message = message;
	}
	
	@Override
	public Integer getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
