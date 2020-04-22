package com.threefriend.lightspace.service.Impl.xcx;

import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threefriend.lightspace.repository.IntegralRepository;
import com.threefriend.lightspace.repository.ParentRepository;
import com.threefriend.lightspace.service.xcx.IntegralService;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;

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
		Integer parentId=parent_dao.findByOpenId(params.get("openId")).getId();
		Long income = Integral_dao.findIntegtalByState(1,parentId);
		Long expenditure = Integral_dao.findIntegtalByState(0,parentId);
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
		end.put("data", Integral_dao.findByParentIdOrderByGenTime(parentId));
		return ResultVOUtil.success(end);
	}

	
}
