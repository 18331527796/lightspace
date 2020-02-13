package com.threefriend.lightspace.Exception;

import com.threefriend.lightspace.enums.ResultEnum;

public class LightException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer status;

    public LightException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.status = resultEnum.getStatus();
    }

    public LightException(Integer status, String message) {
        super(message);
        this.status = status;
    }

}
