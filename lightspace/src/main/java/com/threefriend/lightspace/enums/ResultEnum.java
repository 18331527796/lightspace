package com.threefriend.lightspace.enums;


/**
 * Created by 廖师兄
 * 2017-06-11 18:56
 */
public enum ResultEnum {

    SUCCESS(200, "操作成功"),

    PARAM_ERROR(10201, "参数不正确"),

    LOGIN_FAIL(10202, "登录失败, 登录信息不正确"),

    LOGOUT_SUCCESS(10203, "登出成功"),
    
    TOKEN_OVERDUE(10204,"TOKEN过期，请重新登录~"),
    
    LOGINNAME_REPEAT(10205,"账号已被占用~"),
    ;

    private Integer status;

    private String message;

    ResultEnum(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
    
}
