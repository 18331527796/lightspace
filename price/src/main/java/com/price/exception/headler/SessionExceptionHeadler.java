package com.price.exception.headler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.price.enums.ResultEnum;
import com.price.exception.SessionException;
import com.price.util.ResultVOUtil;
import com.price.vo.ResultVO;


/**
 *	错误拦截处理类
 */
@ControllerAdvice
public class SessionExceptionHeadler {

	// 拦截登录异常
	@ExceptionHandler(value = SessionException.class)
	@ResponseBody
    public ResultVO handlerAuthorizeException() {
        return ResultVOUtil.error(ResultEnum.LOGIN_FAIL.getStatus(), ResultEnum.LOGIN_FAIL.getMessage());
    }
	
}
