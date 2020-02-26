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
    
    SCHOOLSIZE_NULL(10208,"无相关学校信息~"),
    
    RECORDSIZE_NULL(10209,"无相关数据信息~"),
    
    CLASSSIZE_NULL(10210,"无相关班级信息~"),
    
    STUDENTSIZE_NULL(10211,"无相关学生信息~"),
    
    ROLESIZE_NULL(10212,"无相关角色信息~"),
    
    USERSIZE_NULL(10213,"无相关用户信息~"),
    
    DOWNLOAD_ERROR(10214,"下载模板失败"),
    
    READEXCEL_ERROR(10215,"读取EXCEL文件失败，请检查文件是否规范"),
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
