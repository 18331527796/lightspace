package com.threefriend.lightspace.service;

import java.util.Map;

import com.threefriend.lightspace.vo.ResultVO;

/**
 * 模板业务逻辑
 */
public interface MsgTempService {

	/**
	 * 模板列表
	 * @param params
	 * @return
	 */
	ResultVO tempList(Map<String, String> params);
	
	/**
	 * 修改模板
	 * @param params
	 * @return
	 */
	ResultVO editTemp(Map<String, String> params);
	
	/**
	 * 修改后保存
	 * @param params
	 * @return
	 */
	ResultVO saveTemp(Map<String, String> params);
	
	/**
	 * 改选模板
	 * @param params
	 * @return
	 */
	ResultVO selectedTemp(Map<String, String> params);
}
