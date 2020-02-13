package com.threefriend.lightspace.enums;


/**
 * 
 */
public enum ResultEnum {

    SUCCESS(200, "操作成功"),

    PARAM_ERROR(10201, "参数不正确"),

    LOGIN_FAIL(10202, "登录失败, 登录信息不正确"),

    LOGOUT_SUCCESS(10203, "登出成功"),
    
    TOKEN_OVERDUE(10204,"TOKEN过期，请重新登录~"),
    
    LOGINNAME_REPEAT(10205,"账号已被占用~"),
    
    SCHOOLNAME_REPEAT(10206,"新建学校重复，请核实后添加~"),
    
    CLASSNAME_REPEAT(10207,"新建班级重复，请核实后添加~"),
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
