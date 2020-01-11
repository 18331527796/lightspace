package com.threefriend.lightspace.util;

import com.alibaba.fastjson.JSON;

public class ResultUtil {

    private String msg;
    private  Integer code;
    private Object data;

    
    
    public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ResultUtil() {
    }

    public ResultUtil(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultUtil(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

   

    public static Object successResult(Integer code, String msg, Object data){
        ResultUtil resultUtils = new ResultUtil();
        resultUtils.setCode(code);
        resultUtils.setMsg(msg);
        resultUtils.setData(data);
        return JSON.toJSON(resultUtils);
    }


    
}