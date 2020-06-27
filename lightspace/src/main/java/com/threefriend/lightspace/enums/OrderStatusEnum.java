package com.threefriend.lightspace.enums;


/**
 * Created by 廖师兄
 * 2017-06-11 17:12
 */

public enum OrderStatusEnum implements CodeEnum {
    NEW(0, "新订单"),
    SUCCESS(1, "待发货"),
    WAIT(2, "已发货"),
    FINISHED(3, "已完成"),
    EXCHANGE(4,"待体验"),
    
    ;

    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
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
