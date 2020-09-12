package com.threefriend.lightspace.enums;

/**
 * 
 * 视力的程度判断
 */
public enum VisionEnums {
	MILD(0.8,"轻度"),
	MODERATE(0.4,"中度"),
	SERIOUS(0.1,"重度"),
	;
	
	private Double type;
	
	private String msg;
	
	VisionEnums(Double type,String msg){
		this.type = type;
		this.msg = msg;
	}

	public Double getType() {
		return type;
	}

	public String getMsg() {
		return msg;
	}
	
	
}
