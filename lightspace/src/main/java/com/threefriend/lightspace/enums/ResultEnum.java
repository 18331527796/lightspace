package com.threefriend.lightspace.enums;


/**
 * 错误参数
 */
public enum ResultEnum {

    SUCCESS(200, "操作成功"),
    
    MARK_SUCCESS(200, "签到成功"),
    
    TASK_SUCCESS(10199, "全部打卡完成~"),
    
    MARK_ERROR(10200, "今日已签到~"),

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
    
    STUDENTRECORD_ERROR(10216,"学生检测数据不完整，请核实"),
    
    READWORD_ERROR(10217,"导入格式不正确，请核实"),
    
    RIGHT_ERROR(10218,"无权限操作，请确认账号权限"), 
    
    PARENTSTUDENT_ERROR(10219,"孩子已经被账号绑定，请核实"), //这条废弃
    
    BINDINGSTUDENT_ERROR(10220,"您尚未绑定孩子，请先完成绑定操作"),
    
    STUDENTPHONE_ERROR(10221,"您的手机号和孩子预留手机号不符，请核实"), // 这条废弃
    
    BINDINGPHONE_ERROR(10222,"手机号绑定失败，请重试"),
    
    SENDMESSAGE_ERROR(10223,"读取成功，发送失败！"),
    
    SCREENING_ERROR(10224,"今日筛查奖励已领取~"),
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
