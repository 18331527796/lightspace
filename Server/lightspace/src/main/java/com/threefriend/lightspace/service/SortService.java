package com.threefriend.lightspace.service;

import java.util.List;

import com.threefriend.lightspace.vo.SortVO;

/**
 * 
 * 排座业务逻辑
 *
 */
public interface SortService {
	
	public List<List<SortVO>> studentSort(Integer classId,Integer type);

}
