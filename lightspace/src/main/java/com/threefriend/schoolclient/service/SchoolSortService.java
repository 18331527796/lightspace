package com.threefriend.schoolclient.service;

import java.util.List;
import java.util.Map;

import com.threefriend.lightspace.mapper.SortMapper;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.vo.SortVO;

/**
 * 
 * 排座业务逻辑
 *
 */
public interface SchoolSortService {
	
	public ResultVO studentSort(Map<String, String> params);
	
	public ResultVO sortShow(Integer Id);
	
	public ResultVO chkSort(Map<String, String> params);
	
	public ResultVO adjustSort(Map<String, String> params);
	
	public List<SortMapper> byClassId(Integer classId);

	public ResultVO saveSort(Map<String, String> params);
}
