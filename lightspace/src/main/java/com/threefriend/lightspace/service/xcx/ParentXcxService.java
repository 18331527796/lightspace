package com.threefriend.lightspace.service.xcx;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.threefriend.lightspace.vo.ResultVO;

public interface ParentXcxService {

	/**
	 * 验证登录
	 * @param params
	 * @return
	 */
	ResultVO loginXcx(Map<String, String> params)throws Exception;
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
	
	/**
	 * 绑定手机号
	 * @param params
	 * @return
	 * @throws Exception
	 */
	ResultVO bindingPhone(Map<String, String> params)throws Exception;
	
	/**
	 * 获取手机号
	 * @param params
	 * @return
	 */
	String getPhoneDate(Map<String, String> params)throws Exception;
	
	/**
	 * 获取用户信息
	 * @param params
	 * @return
	 */
	Map<String, Object> getUserData(Map<String, String> params)throws Exception;
}
