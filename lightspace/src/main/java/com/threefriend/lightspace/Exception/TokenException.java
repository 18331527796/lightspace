package com.threefriend.lightspace.Exception;

import com.threefriend.lightspace.enums.ResultEnum;

public class TokenException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer status;

    public TokenException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.status = resultEnum.getStatus();
    }

    public TokenException(Integer status, String message) {
        super(message);
        this.status = status;
    }

}
