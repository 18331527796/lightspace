package com.threefriend.lightspace.xcx.service.Impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.enums.AccountEnums;
import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.RegionMapper;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.mapper.xcx.CalibrationMapper;
import com.threefriend.lightspace.mapper.xcx.GzhUserMapper;
import com.threefriend.lightspace.mapper.xcx.IntegralMapper;
import com.threefriend.lightspace.mapper.xcx.OrderMapper;
import com.threefriend.lightspace.mapper.xcx.ParentMapper;
import com.threefriend.lightspace.mapper.xcx.ParentStudentRelation;
import com.threefriend.lightspace.mapper.xcx.ScreeningMapper;
import com.threefriend.lightspace.mapper.xcx.ScreeningWearMapper;
import com.threefriend.lightspace.mapper.xcx.TaskRecordMapper;
import com.threefriend.lightspace.repository.CalibrationRepository;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.GzhUserRepository;
import com.threefriend.lightspace.repository.IntegralRepository;
import com.threefriend.lightspace.repository.OrderRepository;
import com.threefriend.lightspace.repository.ParentRepository;
import com.threefriend.lightspace.repository.ParentStudentRepository;
import com.threefriend.lightspace.repository.RegionRepository;
import com.threefriend.lightspace.repository.ScreeningRepository;
import com.threefriend.lightspace.repository.ScreeningWearRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.repository.TaskRecordRepository;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.ParentXcxService;
import com.threefriend.lightspace.xcx.util.WeChatUtils;
import com.threefriend.lightspace.xcx.util.XcxDecryptUtils;

@Service
public class ParentXcxServiceImpl implements ParentXcxService{
	
	@Autowired
	private ParentRepository parent_dao;
	@Autowired
	private StudentRepository student_dao;
	@Autowired
	private ParentStudentRepository p_s_dao;
	@Autowired
	private GzhUserRepository gzh_dao;
	@Autowired
	private ClassesRepository class_Dao;
	@Autowired
	private IntegralRepository integral_dao;
	@Autowired
	private ScreeningRepository screening_dao;
	@Autowired
	private ScreeningWearRepository screening_wear_Dao;
	@Autowired
	private OrderRepository order_Dao;
	@Autowired
	private RegionRepository region_dao;
	@Autowired
	private TaskRecordRepository taskrecord_dao;
	@Autowired
	private CalibrationRepository calibration_dao;

	/* 
	 * 登陆验证
	 */
	@Override
	public ResultVO loginXcx(Map<String, String> params) throws Exception {
		Map<String, String> end = new HashMap<>();
		String type="";
		//从微信的接口获取sessionkey openId
		Map getsessionKey = WeChatUtils.getsessionKey(AccountEnums.APIKEY.getUrl(), AccountEnums.SECRETKEY.getUrl(), params.get("code"));
		params.put("sessionKey", getsessionKey.get("sessionkey").toString());
		String openId = getsessionKey.get("openId").toString();
		//查一下有这个用户吗
		ParentMapper findByOpenId = parent_dao.findByOpenId(openId);
		if(findByOpenId==null) {
			Map<String, Object> userData = getUserData(params);
			ParentMapper parent = new ParentMapper();
			parent.setOpenId(userData.get("openId").toString());
			parent.setUnionId(userData.get("unionId").toString());
			parent.setGenTime(new Date());
			parent_dao.save(parent);
			type = "new";
			end.put("openId", openId);
			end.put("type", type);
			return ResultVOUtil.success(end);
		}
		type = "old";
		openId = findByOpenId.getOpenId();
		end.put("openId", openId);
		end.put("type", type);
		return ResultVOUtil.success(end);
	}

	/* 
	 * 绑定所有孩子的信息
	 */
	@Override
	public ResultVO childrenList(Map<String, String> params) {
		ParentMapper parent = parent_dao.findByOpenId(params.get("openId"));
		List<ParentStudentRelation> findByParentId = p_s_dao.findByParentIdOrderByIdDesc(parent.getId());
		//如果这个账号没有绑定孩子 返回错误提示
		if(findByParentId.size()==0)return ResultVOUtil.error(ResultEnum.BINDINGSTUDENT_ERROR.getStatus(),ResultEnum.BINDINGSTUDENT_ERROR.getMessage());
		List<StudentMapper> end =new ArrayList<>();
		for (ParentStudentRelation id : findByParentId) {
			Optional<StudentMapper> findById = student_dao.findById(id.getStudentId());
			if(findById!=null&&findById.isPresent()) {
				end.add(findById.get());
			}
		}
		return ResultVOUtil.success(end);
	}

	
	/* 
	 * 绑定孩子
	 */
	@Override
	public synchronized ResultVO  insertStudent(Map<String, String> params) {
		Integer studentId=Integer.valueOf(params.get("studentId"));
		//查找家长学生表中的信息 看看这个孩子有没有被绑定
		List<ParentStudentRelation> findByStudentId = p_s_dao.findByStudentId(studentId);
		//家长
		ParentMapper parent = parent_dao.findByOpenId(params.get("openId"));
		Integer parentId = parent.getId();
		for (ParentStudentRelation parentStudentRelation : findByStudentId) {
			//这个孩子绑定过了 而且是这个家长 就直接返回成功
			if(parentStudentRelation!=null&&parentId==parentStudentRelation.getParentId()) return childrenList(params);
		}
		//保存信息到中间表中
		ParentStudentRelation po =new ParentStudentRelation();
		po.setParentId(parentId);
		po.setStudentId(studentId);
		p_s_dao.save(po);
		return childrenIntegral(params);
	}

	/* 
	 * 解除绑定
	 */
	@Override
	public ResultVO relieveStudent(Map<String, String> params) {
		Integer parentId = parent_dao.findByOpenId(params.get("openId")).getId();
		Integer studentId=Integer.valueOf(params.get("studentId"));
		String classesName = student_dao.findById(studentId).get().getClassesName();
		if("社会".equals(classesName)) {
			screening_dao.deleteAll(screening_dao.findByStudentIdOrderByGenTimeDesc(studentId));
			screening_wear_Dao.deleteAll(screening_wear_Dao.findByStudentIdOrderByGenTimeDesc(studentId));
			integral_dao.deleteAll(integral_dao.findByStudentIdOrderByGenTimeDesc(studentId));
			student_dao.deleteById(studentId);
		}
		p_s_dao.deleteByStudentIdAndParentId(studentId,parentId);
		List<ParentStudentRelation> findByParentId = p_s_dao.findByParentIdOrderByIdDesc(parentId);
		List<StudentMapper> end = new ArrayList<>();
		for (ParentStudentRelation parentStudentRelation : findByParentId) {
			Optional<StudentMapper> findById = student_dao.findById(parentStudentRelation.getStudentId());
			if(findById!=null&&findById.isPresent())end.add(findById.get());
		} 
		return ResultVOUtil.success(end);
	}

	/* 
	 * 查找孩子的详细信息
	 */
	@Override
	public ResultVO findStudent(Map<String, String> params) {
		Integer studentId=Integer.valueOf(params.get("studentId"));
		return ResultVOUtil.success(student_dao.findById(studentId).get());
	}
	/* 
	 * 我的首页数据
	 */
	@Override
	public ResultVO mine(Map<String, String> params) {
		if(StringUtils.isEmpty(params.get("openId")))return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
		ParentMapper findByOpenId = parent_dao.findByOpenId(params.get("openId"));
		Map<String , String > end = new HashMap<>();
		String phone="";
		if(findByOpenId.getPhone()!=null) {
			String phoneDate=findByOpenId.getPhone().toString();
			phone=phoneDate.substring(0, 3)+"****"+phoneDate.substring(7, phoneDate.length());
		}
		end.put("id", findByOpenId.getId().toString());
		end.put("phone", phone);
		return ResultVOUtil.success(end);
	}
	
	/* 
	 * 获取手机号
	 */
	@Override
	public String getPhoneDate(Map<String, String> params) throws Exception {
		String sessionKey=params.get("sessionKey");
		String encryptedData = params.get("encryptedData");
		String iv = params.get("iv");
		System.out.println(sessionKey+"+"+encryptedData+"+"+iv);
		Map<String, Object> userInfo = XcxDecryptUtils.getUserInfo(encryptedData, sessionKey, iv);
		String phone = (String) userInfo.get("purePhoneNumber"); //手机号
		return phone;
	}
	
	/* 
	 * 获取用户信息
	 */
	@Override
	public Map<String, Object> getUserData(Map<String, String> params) throws Exception {
		String sessionKey=params.get("sessionKey");
		String encryptedData = params.get("encryptedData");
		String iv = params.get("iv");
		Map<String, Object> userInfo = XcxDecryptUtils.getUserInfo(encryptedData, sessionKey, iv);
		return userInfo;
	}

	/* 
	 * 绑定手机号
	 */
	@Override
	public ResultVO bindingPhone(Map<String, String> params) throws Exception {
		//从微信的接口获取sessionkey openId
		Map getsessionKey = WeChatUtils.getsessionKey(AccountEnums.APIKEY.getUrl(), AccountEnums.SECRETKEY.getUrl(), params.get("code"));
		params.put("sessionKey", getsessionKey.get("sessionkey").toString());
		String phoneDate = getPhoneDate(params);
		if(StringUtils.isEmpty(phoneDate))ResultVOUtil.error(ResultEnum.BINDINGPHONE_ERROR.getStatus(), ResultEnum.BINDINGPHONE_ERROR.getMessage());
		ParentMapper parent = parent_dao.findByOpenId(params.get("openId"));
		parent.setPhone(Long.valueOf(phoneDate));
		parent_dao.save(parent);
	    String phone="";
	    phone=phoneDate.substring(0, 3)+"****"+phoneDate.substring(7, phoneDate.length());
		return ResultVOUtil.success(phone);
	}

	/* 验证是否关注公众号
	 */
	@Override
	public ResultVO chkGzh(Map<String, String> params) {
		ParentMapper parent = parent_dao.findByOpenId(params.get("openId"));
		GzhUserMapper findByUnionid = gzh_dao.findByUnionid(parent.getUnionId());
		if(findByUnionid==null)return ResultVOUtil.error(ResultEnum.UNFOLLOW_ERROR.getStatus(), ResultEnum.UNFOLLOW_ERROR.getMessage());
		return ResultVOUtil.success();
	}

	/* 
	 * 社会注册的孩子
	 */
	@Override
	public ResultVO registerStudent(Map<String, String> params) {
		ParentMapper parent = parent_dao.findByOpenId(params.get("openId"));
		ClassesMapper classPo = class_Dao.findByClassNameLikeOrderByFinish("社会",PageRequest.of(0, 2)).getContent().get(0);
		StudentMapper newspo = new StudentMapper();
		newspo.setBirthday(params.get("birthday"));
		newspo.setClassesId(classPo.getId());
		newspo.setClassesName(classPo.getClassName());
		newspo.setSchoolId(classPo.getSchoolId());
		newspo.setSchoolName(classPo.getSchoolName());
		if(!StringUtils.isEmpty(params.get("regionId"))) {
			RegionMapper region = region_dao.findById(Integer.valueOf(params.get("regionId"))).get();
			newspo.setRegionId(region.getId());
			newspo.setRegionName(region.getName());
		}else {
			newspo.setRegionId(classPo.getRegionId());
			newspo.setRegionName(classPo.getRegionName());
		}
		newspo.setGender(Integer.valueOf(params.get("gender")));
		newspo.setName(params.get("name"));
		student_dao.save(newspo);
		ParentStudentRelation p_s = new ParentStudentRelation();
		p_s.setParentId(parent.getId());
		p_s.setStudentId(newspo.getId());
		p_s_dao.save(p_s);
		Map<String, String> end = new HashMap<>();
		end.put("id", newspo.getId()+"");
		end.put("balance", "0");
		return ResultVOUtil.success(end);
	}

	/* 
	 * 移植孩子信息
	 */
	@Override
	public ResultVO transplantStudent(Map<String, String> params) {
		ParentMapper parent = parent_dao.findByOpenId(params.get("openId"));
		Integer oldId = Integer.valueOf(params.get("oldId"));
		Integer newId = Integer.valueOf(params.get("newId"));
		StudentMapper newStudent = student_dao.findById(newId).get();
		
		List<IntegralMapper> integral = integral_dao.findByStudentIdOrderByGenTimeDesc(oldId);
		List<ScreeningMapper> screening = screening_dao.findByStudentIdOrderByGenTimeDesc(oldId);
		List<ScreeningWearMapper> screening_wear = screening_wear_Dao.findByStudentIdOrderByGenTimeDesc(oldId);
		List<OrderMapper> order = order_Dao.findByStudentId(oldId);
		//移植订单
		if(order.size()!=0) {
			for (OrderMapper orderMapper : order) {
				orderMapper.setStudentId(newId);
			}
			order_Dao.saveAll(order);
		}
		//移植戴镜记录
		if(screening_wear.size()!=0) {
			for (ScreeningWearMapper screeningWearMapper : screening_wear) {
				screeningWearMapper.setStudentId(newId);
			}
			screening_wear_Dao.saveAll(screening_wear);
		}
		//移植裸视记录
		if(screening.size()!=0) {
			for (ScreeningMapper screeningMapper : screening) {
				screeningMapper.setStudentId(newId);
			}
			screening_dao.saveAll(screening);
		}
		//移植积分
		if(integral.size()!=0) {
			for (IntegralMapper integralMapper : integral) {
				integralMapper.setStudentId(newId);
			}
			integral_dao.saveAll(integral);
		}
		ParentStudentRelation findByStudentIdAndParentId = p_s_dao.findByStudentIdAndParentId(newId,parent.getId());
		if(findByStudentIdAndParentId==null) {
			ParentStudentRelation po = new ParentStudentRelation();
			po.setParentId(parent.getId());
			po.setStudentId(newId);
			p_s_dao.save(po);
		}else {
			p_s_dao.deleteByStudentIdAndParentId(oldId, parent.getId());
		}
		student_dao.deleteById(oldId);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO firstPage(Map<String, String> params) throws Exception {
		Map<String, Date> map = beginAndEnd();
		
		Integer studentId = Integer.valueOf(params.get("studentId"));
		StudentMapper student = student_dao.findById(studentId).get();
		Map<String , String> end = new HashMap<String, String>();
		
		SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd"); // 定义想要的格式
		String now = Format.format(new Date()).substring(0, 10);
		
		Long income = integral_dao.findIntegtalByState(1,studentId);
		Long expenditure = integral_dao.findIntegtalByState(0,studentId);
		//收入
		income = (income==null)?0:income;
		//支出
		expenditure = (expenditure==null)?0:expenditure;
		//余额
		Long balance = income - expenditure;
		//我的排名
		Long myRanking = integral_dao.myRanking(studentId);
		//打卡记录
		List<TaskRecordMapper> allRecords = taskrecord_dao.findByStudentIdAndGenTimeBetween(studentId, map.get("begin"), map.get("end"));
		
		IntegralMapper answer = integral_dao.findByStudentIdAndDetailedAndGenTimeBetween(studentId,"获得爱眼答题奖励",map.get("begin"), map.get("end"));
		
		//爱眼币数量
		end.put("balance", balance.toString());
		//爱眼币排名
		end.put("ranking", myRanking.toString());
		
		
		if(student.getSendTime()!=null) {
			if(now.equals(Format.format(student.getSendTime()).substring(0, 10))) {
				//筛查情况
				end.put("undetected", "1");
			}else {
				end.put("undetected", "0");
			}
			end.put("lastTime", student.getLastTime());
		}else {
			end.put("undetected", "0");
			end.put("lastTime", "");
		}
		//打卡任务
		end.put("task", allRecords.size()+"");
		//答题竞赛
		if(answer==null) {
			end.put("answer", "0");
		}else {
			end.put("answer", answer.getIntegral()==null?"0":(answer.getIntegral()+""));
		}
		//秀  一  秀
		return ResultVOUtil.success(end);
	}
	
	public Map<String, Date> beginAndEnd() throws Exception {
		Map<String, Date> map = new HashMap<String, Date>();
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date begin = simpleDateFormat.parse(DateFormatUtils.format(new Date(), "yyyy-MM-dd 00:00:00"));
		Date end = simpleDateFormat.parse(DateFormatUtils.format(new Date(), "yyyy-MM-dd 23:59:59"));
		map.put("begin", begin);
		map.put("end", end);
		return map;
	}

	/**
	 * 验证校验信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	public ResultVO chkCalibration(Map<String, String> params) {
		CalibrationMapper findByOpenId = calibration_dao.findByOpenId(params.get("openId"));
		if(findByOpenId==null) {
			return ResultVOUtil.success();
		}else {
			return ResultVOUtil.success(findByOpenId.getLv());
		}
	}

	/**
	 * 更改校验信息
	 * @param params
	 * @return
	 */
	@Override
	public ResultVO editCalibration(Map<String, String> params) {
		CalibrationMapper findByOpenId = calibration_dao.findByOpenId(params.get("openId"));
		if(findByOpenId!=null) {
			findByOpenId.setLv(params.get("scale"));
			calibration_dao.save(findByOpenId);
			return ResultVOUtil.success(findByOpenId.getLv());
		}else {
			CalibrationMapper po = new CalibrationMapper();
			po.setOpenId(params.get("openId"));
			po.setLv(params.get("scale"));
			calibration_dao.save(po);
			return ResultVOUtil.success(po.getLv());
		}
	}
	
	
	@Override
	public ResultVO childrenIntegral(Map<String, String> params) {
		ParentMapper parent = parent_dao.findByOpenId(params.get("openId"));
		List<ParentStudentRelation> findByParentId = p_s_dao.findByParentIdOrderByIdDesc(parent.getId());
		//如果这个账号没有绑定孩子 返回错误提示
		if(findByParentId.size()==0)return ResultVOUtil.error(ResultEnum.BINDINGSTUDENT_ERROR.getStatus(),ResultEnum.BINDINGSTUDENT_ERROR.getMessage());
		Long income = 0l,expenditure = 0l, balance = 0l , myRanking = 0l;
		List<Map<String, String>> end =new ArrayList<>();
		
		for (ParentStudentRelation id : findByParentId) {
			Map<String, String> object = new HashMap<>();
			StudentMapper studentMapper = student_dao.findById(id.getStudentId()).get();
			
			myRanking = integral_dao.myRanking(studentMapper.getId());
			income = integral_dao.findIntegtalByState(1,studentMapper.getId());
			expenditure = integral_dao.findIntegtalByState(0,studentMapper.getId());
			//收入
			income = (income==null)?0:income;
			//支出
			expenditure = (expenditure==null)?0:expenditure;
			//余额
			balance = income - expenditure;
			
			object.put("id", studentMapper.getId()+"");
			object.put("name", studentMapper.getName());
			object.put("balance", balance+"");
			object.put("income", income+"");
			object.put("myIntegral", income+"");
			object.put("gender", studentMapper.getGender()+"");
			object.put("ranking", myRanking+"");
			object.put("birthday", studentMapper.getBirthday());
			
			end.add(object);
		}
		return ResultVOUtil.success(end);
	}
}
