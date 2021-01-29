package com.price.util;

import com.price.vo.ResultVO;

/**
 * 返回工具类
 */
@SuppressWarnings("rawtypes")
public class ResultVOUtil {

	@SuppressWarnings("unchecked")
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
    

    @SuppressWarnings("unchecked")
	public static ResultVO error(Integer code, String msg,Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setStatus(code);
        resultVO.setMsg(msg);
        resultVO.setData(object);
        return resultVO;
    }
}
