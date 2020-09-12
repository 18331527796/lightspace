package com.threefriend.lightspace.Exception;

import java.util.List;
import java.util.Map;


/**
 *	排座错误类
 */
public class SortException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Map<String, String>> studentName;
	
	public SortException(List<Map<String, String>> nullStudent) {
		this.studentName=nullStudent;
    }

	public List<Map<String, String>> getStudentName() {
		return studentName;
	}

	

}
