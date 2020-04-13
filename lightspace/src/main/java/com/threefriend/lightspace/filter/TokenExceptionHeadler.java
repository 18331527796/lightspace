package com.threefriend.lightspace.filter;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.threefriend.lightspace.Exception.TokenException;
import com.threefriend.lightspace.Exception.SortException;
import com.threefriend.lightspace.Exception.ReadWordException;
import com.threefriend.lightspace.Exception.SendMessageException;
import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;

/**
 *	错误拦截处理类
 */
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
	
	
	// 拦截登录异常
	@ExceptionHandler(value = ReadWordException.class)
	@ResponseBody
    public ResultVO ReadWordException() {
        return ResultVOUtil.error(ResultEnum.READWORD_ERROR.getStatus(), ResultEnum.READWORD_ERROR.getMessage());
    }
	
	// 拦截登录异常
	@ExceptionHandler(value = SendMessageException.class)
	@ResponseBody
    public ResultVO SendMessageException() {
        return ResultVOUtil.error(ResultEnum.SENDMESSAGE_ERROR.getStatus(), ResultEnum.SENDMESSAGE_ERROR.getMessage());
    }
}
