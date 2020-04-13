package com.threefriend.lightspace.Exception;

import java.util.List;

import com.threefriend.lightspace.enums.ResultEnum;

/**
 *	排座错误类
 */
public class SortException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<String> studentName;
	
	public SortException(List<String> studentName) {
		this.studentName=studentName;
    }

	public List<String> getStudentName() {
		return studentName;
	}

	

}
