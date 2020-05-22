package com.threefriend.lightspace.xcx.service.Impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threefriend.lightspace.repository.IntegralRepository;
import com.threefriend.lightspace.repository.ParentRepository;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.IntegralService;

/**
 * 积分实现类
 * @author Administrator
 *
 */
@Service
public class IntegralServiceImpl implements IntegralService{
	@Autowired
	private ParentRepository parent_dao;
	@Autowired
	private IntegralRepository Integral_dao;

	/* 
	 * 这个账号的 积分列表
	 */
	@Override
	public ResultVO IntegralListByParentId(Map<String, String> params) {
        Integer studentId = Integer.valueOf(params.get("studentId"));
		Long income = Integral_dao.findIntegtalByState(1,studentId);
		Long expenditure = Integral_dao.findIntegtalByState(0,studentId);
		//收入
		income = (income==null)?0:income;
		//支出
		expenditure = (expenditure==null)?0:expenditure;
		//余额
		Long balance = income - expenditure;
		Map<String, Object> end = new HashedMap();
		//收入
		end.put("income", income);
		end.put("expenditure", expenditure);
		end.put("balance", balance);
		end.put("data", Integral_dao.findByStudentIdOrderByGenTimeDesc(studentId));
		return ResultVOUtil.success(end);
	}

	
}
