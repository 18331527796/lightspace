package com.threefriend.lightspace.xcx.service;

import java.util.Map;

import com.threefriend.lightspace.vo.ResultVO;

public interface ParentXcxService {

	/**
	 * 验证登录
	 * @param params
	 * @return
	 */
	ResultVO loginXcx(Map<String, String> params);
	/**
	 * 查询账号所绑定的所有孩子信息
	 * @param params
	 * @return
	 */
	ResultVO childrenList(Map<String, String> params);
	/**
	 * 绑定孩子
	 * @param params
	 * @return
	 */
	ResultVO insertStudent(Map<String, String> params);
	/**
	 * 解除绑定
	 * @param params
	 * @return
	 */
	ResultVO relieveStudent(Map<String, String> params);
	/**
	 * 查询学生信息
	 * @param params
	 * @return
	 */
	ResultVO findStudent(Map<String, String> params);
	/**
	 * 我的首页数据
	 * @param params
	 * @return
	 */
	ResultVO mine(Map<String, String> params);
}
