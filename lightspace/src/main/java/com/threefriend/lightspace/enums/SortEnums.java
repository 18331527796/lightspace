package com.threefriend.lightspace.enums;

/**
 * 
 * 排座的类型
 *（需要注意 type 为类型code number 为每行的人数）
 */
public enum SortEnums {
	
	TYPEONE(1,8),
	TYPETWO(2,9),
	TYPETHREE(3,6),
	TYPEFOUR(4,6),
	;
	
	private Integer type;
	
	private Integer number;
	
	SortEnums(Integer type,Integer number){
		this.type = type;
		this.number = number;
	}

	public Integer getType() {
		return type;
	}

	public Integer getNumber() {
		return number;
	}
	
	
}
