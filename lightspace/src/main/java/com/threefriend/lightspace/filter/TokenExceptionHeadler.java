package com.threefriend.lightspace.filter;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.threefriend.lightspace.Exception.LightException;
import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;

@ControllerAdvice
public class TokenExceptionHeadler {

	// 拦截登录异常
	@ExceptionHandler(value = LightException.class)
	@ResponseBody
    public ResultVO handlerAuthorizeException() {
        return ResultVOUtil.error(ResultEnum.TOKEN_OVERDUE.getStatus(), ResultEnum.TOKEN_OVERDUE.getMessage());
    }
}
