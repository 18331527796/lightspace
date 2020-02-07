package com.threefriend.lightspace.Exception;

import com.threefriend.lightspace.enums.ResultEnum;

public class LightException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer code;

    public LightException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());

        this.code = resultEnum.getStatus();
    }

    public LightException(Integer code, String message) {
        super(message);
        this.code = code;
    }

}
