package com.threefriend.lightspace.xcx.service.Impl;

import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
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
		//收入
		Long income = Integral_dao.findIntegtalByState(1);
		//支出
		Long expenditure = Integral_dao.findIntegtalByState(0);
		//余额
		Long balance = income - expenditure;
		Map<String, Object> end = new HashedMap();
		//收入
		end.put("income", income);
		end.put("expenditure", expenditure);
		end.put("balance", balance);
		end.put("data", Integral_dao.findByParentIdOrderByGenTime(parentId));
		return ResultVOUtil.success(end);
	}

	
}
