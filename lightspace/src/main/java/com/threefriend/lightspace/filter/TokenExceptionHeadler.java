package com.threefriend.lightspace.filter;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.threefriend.lightspace.Exception.TokenException;
import com.threefriend.lightspace.Exception.SortException;
import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;

@ControllerAdvice
public class TokenExceptionHeadler {

	// 拦截登录异常
	@ExceptionHandler(value = TokenException.class)
	@ResponseBody
    public ResultVO handlerAuthorizeException() {
        return ResultVOUtil.error(ResultEnum.TOKEN_OVERDUE.getStatus(), ResultEnum.TOKEN_OVERDUE.getMessage());
    }
	
	
	// 拦截登录异常
	@ExceptionHandler(value = SortException.class)
	@ResponseBody
    public ResultVO SortException(Exception e) {
		SortException ex = (SortException) e;
        return ResultVOUtil.error(ResultEnum.STUDENTRECORD_ERROR.getStatus(), ResultEnum.STUDENTRECORD_ERROR.getMessage(),ex.getStudentName());
    }
}
