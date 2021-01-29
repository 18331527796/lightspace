package com.threefriend.dingding.enums;


/**
 * 错误参数
 */
public enum ResultEnum {

    SUCCESS(200, "操作成功"),
    
    MARK_SUCCESS(200, "签到成功"),
    
    TASK_SUCCESS(10199, "全部打卡完成~"),
    
    MARK_ERROR(10200, "所有任务未完成~"),

    PARAM_ERROR(10201, "参数不正确"),

    
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
