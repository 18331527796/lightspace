package com.threefriend.lightspace.xcx.service.Impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.mapper.xcx.ParentMapper;
import com.threefriend.lightspace.mapper.xcx.ParentStudentRelation;
import com.threefriend.lightspace.repository.IntegralRepository;
import com.threefriend.lightspace.repository.ParentRepository;
import com.threefriend.lightspace.repository.ParentStudentRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.IntegralVO;
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
	@Autowired
	private StudentRepository student_dao;
	@Autowired
	private ParentStudentRepository p_s_dao;

	/* 
	 * 这个账号的 积分列表
	 */
	@Override
	public ResultVO IntegralListByParentId(Map<String, String> params) {
		Map<String, Object> end = new HashedMap();
		int page = 0 ; 
		//当type == 1 时，携带积分的运算数据 否则就是下拉加载 所以不用带
		Integer type = 1 ;
		if(!StringUtils.isEmpty(params.get("integralType")))
			type = Integer.valueOf(params.get("integralType"));
		Integer studentId = Integer.valueOf(params.get("studentId"));
		if(!StringUtils.isEmpty(params.get("integralPage")))page = Integer.valueOf(params.get("integralPage")) - 1 ;
		
		if(type == 1) {
			Long income = Integral_dao.findIntegtalByState(1,studentId);
			Long expenditure = Integral_dao.findIntegtalByState(0,studentId);
			//收入
			income = (income==null)?0:income;
			//支出
			expenditure = (expenditure==null)?0:expenditure;
			//余额
			Long balance = income - expenditure;
			//收入
			end.put("income", income);
			end.put("expenditure", expenditure);
			end.put("balance", balance);
		}
		end.put("data", Integral_dao.findByStudentId(studentId,PageRequest.of(page, 5,Sort.by("genTime").descending())).getContent());
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO rankingNO10(Map<String, String> params) {
		Integer studentId = Integer.valueOf(params.get("studentId"));
		Map<String, Object> end = new HashMap<>();
		List<IntegralVO> integtalRanking = new ArrayList<>();
		Long myIntegral = Integral_dao.findIntegtalByState(1,studentId);
		Long myRanking = Integral_dao.myRanking(studentId);
		List<Integer> ids = Integral_dao.integtalRanking();
		for (Integer id : ids) {
			StudentMapper student = student_dao.findById(id).get();
			IntegralVO vo = new IntegralVO(id, student.getName(), student.getSchoolName(), Integral_dao.findIntegtalByState(1, id));
			integtalRanking.add(vo);
		}
		end.put("myIntegral", myIntegral==null?0:myIntegral);
		end.put("myRanking", myRanking);
		end.put("integtalRanking", integtalRanking);
		return ResultVOUtil.success(end);
	}


	
}
