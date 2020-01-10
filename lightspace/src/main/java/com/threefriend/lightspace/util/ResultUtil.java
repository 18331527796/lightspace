package com.threefriend.lightspace.util;

import com.alibaba.fastjson.JSON;

public class ResultUtil {

    private String msg;
    private boolean success = true;
    private Object result;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public ResultUtil() {
    }

    public ResultUtil(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public ResultUtil(boolean success, String msg, Object result) {
        this.success = success;
        this.msg = msg;
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public Object successResult(){
        ResultUtil resultUtils = new ResultUtil();
        resultUtils.setSuccess(true);
        resultUtils.setMsg("success");
        resultUtils.setResult(null);
        return JSON.toJSON(resultUtils);
    }

    public Object successResult(Object obj){
        if (obj instanceof String){
            return successResult(null, (String) obj);
        }else{
            return successResult(obj, "error");
        }
    }

    public Object successResult(Object obj, String msg){
        ResultUtil resultUtils = new ResultUtil();
        resultUtils.setSuccess(true);
        resultUtils.setMsg(msg);
        resultUtils.setResult(obj);
        return JSON.toJSON(resultUtils);
    }

    public Object errorResult(){
        ResultUtil resultUtils = new ResultUtil();
        resultUtils.setSuccess(false);
        resultUtils.setMsg("error");
        resultUtils.setResult(null);
        return JSON.toJSON(resultUtils);
    }
    /*
     * 杩斿洖閿欒缁撴灉
     * */
    public Object errorResult(Object obj){
        if (obj instanceof String){
            return renderJsonError(null, (String) obj);
        }else{
            return renderJsonError(obj, "error");
        }
    }

    public Object renderJsonError(Object obj, String msg){
        ResultUtil resultUtils = new ResultUtil();
        resultUtils.setSuccess(false);
        resultUtils.setMsg(msg);
        resultUtils.setResult(obj);
        return JSON.toJSON(resultUtils);
    }
}