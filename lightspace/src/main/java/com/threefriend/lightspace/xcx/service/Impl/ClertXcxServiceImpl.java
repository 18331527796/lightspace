package com.threefriend.lightspace.xcx.service.Impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.enums.OrderStatusEnum;
import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.mapper.ClertMapper;
import com.threefriend.lightspace.mapper.xcx.OrderMapper;
import com.threefriend.lightspace.mapper.xcx.ParentMapper;
import com.threefriend.lightspace.mapper.xcx.ScanningCodeMapper;
import com.threefriend.lightspace.repository.ClertRepository;
import com.threefriend.lightspace.repository.OrderRepository;
import com.threefriend.lightspace.repository.ParentRepository;
import com.threefriend.lightspace.repository.ScanningCodeRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.ClertXcxService;

@Service
public class ClertXcxServiceImpl implements ClertXcxService {

	@Autowired
	private ClertRepository clert_dao;
	@Autowired
	private ParentRepository parent_dao;
	@Autowired
	private OrderRepository order_dao;
	@Autowired
	private StudentRepository student_dao;
	@Autowired
	private ScanningCodeRepository scanning_code_dao;

	/*
	 * 店员登录
	 */
	@Override
	public ResultVO clertLogin(Map<String, String> params) {
		System.err.println(params.get("loginName") + params.get("password"));
		String loginName = params.get("loginName");
		String password = params.get("password");
		List<ClertMapper> clert = clert_dao.findByLoginNameAndPassword(loginName, password);
		if (clert.size() != 1)
			return ResultVOUtil.error(ResultEnum.TEACHER_LOGIN_ERROR.getStatus(),
					ResultEnum.TEACHER_LOGIN_ERROR.getMessage());
		ParentMapper parent = parent_dao.findByOpenId(params.get("openId"));
		clert.get(0).setState(1); // 改变登录状态
		clert.get(0).setParentId(parent.getId());// 关联小程序账号
		clert_dao.save(clert.get(0));
		return ResultVOUtil.success();
	}

	/*
	 * 验证登录状态
	 */
	@Override
	public ResultVO chkState(Map<String, String> params) {
		ParentMapper parent = parent_dao.findByOpenId(params.get("openId"));
		ClertMapper po = clert_dao.findByParentId(parent.getId());
		if (po != null && po.getState() == 1)
			return ResultVOUtil.success();
		return ResultVOUtil.error(ResultEnum.CHKSTATE_ERROR.getStatus(), ResultEnum.CHKSTATE_ERROR.getMessage());
	}

	@Override
	public ResultVO clertSanningCode(Map<String, String> params) {
		System.err.println("刺激");
		Integer orderId = Integer.valueOf(params.get("id"));
		ScanningCodeMapper findByOrderId = scanning_code_dao.findByOrderId(orderId);
		if(findByOrderId!=null)ResultVOUtil.success();
		
		ParentMapper parent = parent_dao.findByOpenId(params.get("openId"));
		ClertMapper clert = clert_dao.findByParentId(parent.getId());
		
		OrderMapper order = order_dao.findById(orderId).get();
		order.setStatus(OrderStatusEnum.FINISHED.getMessage());
		order.setSuccesstime(new Date());
		order.setGenTimeDate(new Date());
		order_dao.save(order);
		
		ScanningCodeMapper code = new ScanningCodeMapper();
		code.setGenTime(new Date());
		code.setClertId(clert.getId());
		code.setClertName(clert.getName());
		code.setPartnershipId(clert.getPartnershipId());
		code.setOrderId(orderId);
		code.setProductId(order.getProductId());
		code.setProductName(order.getProductName());
		code.setSpecificationsId(order.getSpecificationId());
		code.setSpecificationsName(order.getSpecificationName());
		code.setStudentName(student_dao.findById(order.getStudentId()).get().getName());
		scanning_code_dao.save(code);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO orderList(Map<String, String> params) {
		ParentMapper parent = parent_dao.findByOpenId(params.get("openId"));
		ClertMapper clert = clert_dao.findByParentId(parent.getId());
		int page = 0 ;
		if(!StringUtils.isEmpty(params.get("page")))page = Integer.valueOf(params.get("page")) - 1 ;
		List<ScanningCodeMapper> content = scanning_code_dao.findByClertId(clert.getId(),PageRequest.of(page, 10,Sort.by("id").descending())).getContent();
		return ResultVOUtil.success(content);
	}

	

}
