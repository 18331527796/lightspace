package com.threefriend.lightspace.service;

import java.util.List;
import java.util.Map;

import com.threefriend.lightspace.mapper.RightMapper;

/**
 * 权限业务逻辑接口
 */
public interface RightService {

	//新增权限
	public List<RightMapper> addRight(Map<String, String> params);
	//按照id查询
	public RightMapper findById(Integer id);
	//修改后保存
	public List<RightMapper> saveRight(Map<String, String> params);
	//删除权限
	public List<RightMapper> deleteRight(Integer id);
	//权限列表
	public List<RightMapper> rightList();
}
