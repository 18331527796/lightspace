package com.threefriend.lightspace.enums;

public enum DeliveryTypeEnums implements CodeEnum{

	HOME(1,"邮寄"),
	SCHOOL(2,"学校自取"),
	SERVICE(3,"到店领取"),
	;
	private Integer code;
	
	private String message;
	
	DeliveryTypeEnums(Integer code,String message){
		this.code = code;
		this.message = message;
	}

	@Override
	public Integer getCode() {
		// TODO Auto-generated method stub
		return code;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return message;
	}
}
