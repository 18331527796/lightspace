package com.threefriend.lightspace.xcx.service.Impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.enums.OrderStatusEnum;
import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.mapper.ClertMapper;
import com.threefriend.lightspace.mapper.SortMapper;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.mapper.StudentWordMapper;
import com.threefriend.lightspace.mapper.xcx.OrderMapper;
import com.threefriend.lightspace.mapper.xcx.ParentMapper;
import com.threefriend.lightspace.mapper.xcx.ParentStudentRelation;
import com.threefriend.lightspace.mapper.xcx.ScanningCodeMapper;
import com.threefriend.lightspace.mapper.xcx.ScreeningMapper;
import com.threefriend.lightspace.mapper.xcx.ScreeningWearMapper;
import com.threefriend.lightspace.repository.ClertRepository;
import com.threefriend.lightspace.repository.OrderRepository;
import com.threefriend.lightspace.repository.ParentRepository;
import com.threefriend.lightspace.repository.ScanningCodeRepository;
import com.threefriend.lightspace.repository.ScreeningRepository;
import com.threefriend.lightspace.repository.ScreeningWearRepository;
import com.threefriend.lightspace.repository.SortRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.repository.StudentWordRepository;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.AxisLengthShowVO;
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
	@Autowired
	private ScreeningRepository screening_dao;
	@Autowired
	private ScreeningWearRepository screening_wear_dao;
	@Autowired
	private SortRepository sort_dao;
	@Autowired
	private StudentWordRepository student_word_dao;

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
		Integer orderId = Integer.valueOf(params.get("id"));
		ScanningCodeMapper findByOrderId = scanning_code_dao.findByOrderId(orderId);
		if(findByOrderId!=null)return ResultVOUtil.success();
		
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

	
	/*
	 * 这个账号的所有绑定孩子的档案
	 */
	@Override
	public ResultVO childrenScreening(Map<String, String> params) {
		Calendar c = Calendar.getInstance();
		// 过去90天
		c.setTime(new Date());
		c.add(Calendar.DATE, -90);
		Date beginTime = c.getTime();
		Date eneTime = new Date();
		// ↑定义时间 用来满足前台要求的图表返回数据
		List<Map<String, Object>> end = new ArrayList<>();
		// 找到这个孩子的所有信息
		StudentMapper student = student_dao.findById(Integer.valueOf(params.get("studentId"))).get();
		// 建立map容器
		Map<String, Object> map = new HashMap<>();
		// 找到这个孩子所有的档案数据
		List<ScreeningMapper> dataList = screening_dao.findByStudentIdOrderByGenTimeDesc(student.getId());
		// 找到这个孩子90内的档案数据
		List<ScreeningMapper> picList = screening_dao.findByStudentIdAndGenTimeBetweenOrderById(student.getId(), beginTime,
				eneTime);
		// 找到这个孩子所有的档案数据（戴镜）
		List<ScreeningWearMapper> weardataList = screening_wear_dao.findByStudentIdOrderByGenTimeDesc(student.getId());
		// 找到这个孩子90天内的档案数据（戴镜）
		List<ScreeningWearMapper> wearpicList = screening_wear_dao.findByStudentIdAndGenTimeBetweenOrderById(student.getId(),
				beginTime, eneTime);

		map.put("id", student.getId());
		map.put("name", student.getName());
		map.put("gender", student.getGender());
		map.put("myIntegral", student.getMyIntegral());
		map.put("birthday", student.getBirthday());
		map.put("dataList", dataList);
		map.put("picList", picList);
		map.put("weardataList", weardataList);
		map.put("wearpicList", wearpicList);
		end.add(map);
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO orderShow(Map<String, String> params) {
		Integer studentId = Integer.valueOf(params.get("studentId"));
		StudentMapper studentMapper = student_dao.findById(studentId).get();
		int page = 0;
		if(!StringUtils.isEmpty(params.get("page")))page = Integer.valueOf(params.get("page")) - 1 ;
		Page<OrderMapper> orders = order_dao.findByStudentIdAndStatus(studentId , OrderStatusEnum.EXCHANGE.getMessage(), PageRequest.of(page, 5));
		List<Map<String, String>> list = new ArrayList<>();
		
		for (OrderMapper order : orders.getContent()) {
			list.add(new HashMap<String, String>() {{
				put("id", order.getId()+"");
				put("productName", order.getProductName());
				put("specificationName", order.getSpecificationName());
				put("studentName", studentMapper.getName());}});
		}
		Page<Map<String, String>> end =new PageImpl<>(list, orders.getPageable(), orders.getTotalElements());
		
		return ResultVOUtil.success(end);
	}


	@Override
	public ResultVO insertStudentWord(StudentWordMapper word) {
		StudentMapper student = student_dao.findById(word.getStudentId()).get();
		StudentWordMapper po = new StudentWordMapper();
		BeanUtils.copyProperties(word, po);
		po.setGenTime(new Date());
		po.setSchoolId(student.getSchoolId());
		po.setSchool(student.getSchoolName());
		po.setSchoolName(student.getSchoolName());
		po.setClassId(student.getClassesId());
		po.setClassName(student.getClassesName());
		student_word_dao.save(po);
		return ResultVOUtil.success(po.getId());
	}

	@Override
	public ResultVO axisLengthShow(Map<String, String> params) {
		System.out.println("in"+params.get("studentId"));
		List<StudentWordMapper> list = student_word_dao.findByStudentIdOrderByGenTimeDesc(Integer.valueOf(params.get("studentId")));
		List<AxisLengthShowVO> end = new ArrayList<>();
		for (StudentWordMapper po : list) {
			/*if(StringUtils.isEmpty(po.getLevelRight())||StringUtils.isEmpty(po.getLevelLeft())||
			   StringUtils.isEmpty(po.getVerticalRight())||StringUtils.isEmpty(po.getVerticalLeft())||
			   StringUtils.isEmpty(po.getAxialLengthRight())||StringUtils.isEmpty(po.getAxialLengthLeft())
			   )continue;*/
			
			AxisLengthShowVO vo = new AxisLengthShowVO(po);
			end.add(vo);
		}
		return ResultVOUtil.success(end);
	}
	

}
