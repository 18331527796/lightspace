package com.threefriend.lightspace.util;

import com.threefriend.lightspace.vo.ResultVO;

/**
 * 返回工具类
 */
public class ResultVOUtil {

    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setStatus(200);
        resultVO.setMsg("操作成功");
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setStatus(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
    

    public static ResultVO error(Integer code, String msg,Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setStatus(code);
        resultVO.setMsg(msg);
        resultVO.setData(object);
        return resultVO;
    }
}
