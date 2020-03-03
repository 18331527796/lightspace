package com.threefriend.lightspace.xcx.service.Impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threefriend.lightspace.repository.IntegralRepository;
import com.threefriend.lightspace.repository.ParentRepository;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.IntegralService;

@Service
public class IntegralServiceImpl implements IntegralService{
	@Autowired
	private ParentRepository parent_dao;
	@Autowired
	private IntegralRepository Integral_dao;

	@Override
	public ResultVO IntegralListByParentId(Map<String, String> params) {
		Integer parentId=parent_dao.findByOpenId(params.get("openId")).getId();
		return ResultVOUtil.success(Integral_dao.findByParentIdOrderByGenTime(parentId));
	}

	
}
