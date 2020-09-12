package com.threefriend.lightspace.enums;


/**
 * Created by 廖师兄
 * 2017-06-11 17:12
 */

public enum SemesterEnum implements CodeEnum {
	SUMMER(0, "暑假"),
    ONESEMESTER(1, "1学期"),
    WINTER(2, "寒假"),
    TOWSEMESTER(3, "2学期"),
    
    ;

    private Integer code;

    private String message;

    SemesterEnum(Integer code, String message) {
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
