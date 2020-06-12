package com.threefriend.lightspace.xcx.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.enums.AccountEnums;
import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.mapper.xcx.GzhUserMapper;
import com.threefriend.lightspace.mapper.xcx.IntegralMapper;
import com.threefriend.lightspace.mapper.xcx.OrderMapper;
import com.threefriend.lightspace.mapper.xcx.ParentMapper;
import com.threefriend.lightspace.mapper.xcx.ParentStudentRelation;
import com.threefriend.lightspace.mapper.xcx.ScreeningMapper;
import com.threefriend.lightspace.mapper.xcx.ScreeningWearMapper;
import com.threefriend.lightspace.mapper.xcx.TaskRecordMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.GzhUserRepository;
import com.threefriend.lightspace.repository.IntegralRepository;
import com.threefriend.lightspace.repository.OrderRepository;
import com.threefriend.lightspace.repository.ParentRepository;
import com.threefriend.lightspace.repository.ParentStudentRepository;
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
		List<ParentStudentRelation> findByParentId = p_s_dao.findByParentId(parent.getId());
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
		return childrenList(params);
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
		List<ParentStudentRelation> findByParentId = p_s_dao.findByParentId(parentId);
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
		newspo.setRegionId(classPo.getRegionId());
		newspo.setRegionName(classPo.getRegionName());
		newspo.setGender(Integer.valueOf(params.get("gender")));
		newspo.setName(params.get("name"));
		student_dao.save(newspo);
		ParentStudentRelation p_s = new ParentStudentRelation();
		p_s.setParentId(parent.getId());
		p_s.setStudentId(newspo.getId());
		p_s_dao.save(p_s);
		return ResultVOUtil.success();
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
	
}
