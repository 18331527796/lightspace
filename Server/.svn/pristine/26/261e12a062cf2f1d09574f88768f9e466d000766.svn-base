package com.threefriend.lightspace.service.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.mapper.RightMapper;
import com.threefriend.lightspace.repository.RightRepository;
import com.threefriend.lightspace.service.RightService;

/**
 * 权限逻辑实现
 */
@Service
public class RightServiceImpl implements RightService{

	@Autowired
	private RightRepository right_dao;

	/* 
	 * 新增权限
	 */
	@Override
	public List<RightMapper> addRight(Map<String, String> params) {
		RightMapper right = new RightMapper();
		right.setAuthName(params.get("authName"));
		right.setpId(Integer.valueOf(params.get("pId")));
		if(!StringUtils.isEmpty(params.get("path")))right.setPath(params.get("path"));
		if(!StringUtils.isEmpty(params.get("description")))right.setDescription(params.get("description"));
		right_dao.save(right);
		return right_dao.findAll();
	}

	
	/* 
	 * 按照id查询权限
	 */
	@Override
	public RightMapper findById(Integer id) {
		return right_dao.findById(id).get();
	}

	/* 
	 * 修改后保存
	 */
	@Override
	public List<RightMapper> saveRight(Map<String, String> params) {
		RightMapper right = right_dao.findById(Integer.valueOf(params.get("id"))).get();
		if(!StringUtils.isEmpty(params.get("authName")))right.setAuthName(params.get("authName"));
		if(!StringUtils.isEmpty(params.get("pId")))right.setpId(Integer.valueOf(params.get("pId")));
		if(!StringUtils.isEmpty(params.get("path")))right.setPath(params.get("path"));
		if(!StringUtils.isEmpty(params.get("description")))right.setDescription(params.get("description"));
		right_dao.save(right);
		return right_dao.findAll();
	}

	
	/* 
	 * 删除权限
	 */
	@Override
	public List<RightMapper> deleteRight(Integer id) {
		right_dao.deleteById(id);
		return right_dao.findAll();
	}

	/* 
	 * 权限列表
	 */
	@Override
	public List<RightMapper> rightList() {
		return right_dao.findAll();
	}
}
