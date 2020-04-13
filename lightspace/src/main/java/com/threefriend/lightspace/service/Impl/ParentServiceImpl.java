package com.threefriend.lightspace.service.Impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threefriend.lightspace.repository.ParentRepository;
import com.threefriend.lightspace.service.ParentService;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;

/**
 *	家长逻辑实现层
 */
@Service
public class ParentServiceImpl implements ParentService{
	@Autowired
	private ParentRepository parent_dao;

	@Override
	public ResultVO parentList() {
		return ResultVOUtil.success(parent_dao.findAll());
	}

}
