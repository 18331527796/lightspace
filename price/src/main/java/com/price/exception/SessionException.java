package com.price.exception;

import com.price.enums.ResultEnum;

public class SessionException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer status;

    public SessionException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.status = resultEnum.getStatus();
    }
}
