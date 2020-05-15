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
		//获取当前月第一天：
        Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);
        Date beginTime = c.getTime();
        //获取当前月最后一天
        Calendar ca = Calendar.getInstance();    
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endTime = ca.getTime();
        
		Integer parentId=parent_dao.findByOpenId(params.get("openId")).getId();
		Long income = Integral_dao.findIntegtalByState(1,parentId,beginTime,endTime);
		Long expenditure = Integral_dao.findIntegtalByState(0,parentId,beginTime,endTime);
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
		end.put("data", Integral_dao.findByParentIdOrderByGenTimeDesc(parentId));
		return ResultVOUtil.success(end);
	}

	
}
