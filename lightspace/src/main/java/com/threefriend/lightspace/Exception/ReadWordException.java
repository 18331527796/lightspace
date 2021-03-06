package com.threefriend.lightspace.Exception;

import java.util.List;

import com.threefriend.lightspace.enums.ResultEnum;

/**
 *	读取学生分析报告错误类
 */
public class ReadWordException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	public ReadWordException(String name) {
		this.name=name;
    }
	public ReadWordException() {
    }
	
	public String getName() {
		return name;
	}

}
