package com.threefriend.lightspace.xcx.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threefriend.lightspace.enums.AccountEnums;
import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.mapper.ParentMapper;
import com.threefriend.lightspace.mapper.ParentStudentRelation;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.repository.ParentRepository;
import com.threefriend.lightspace.repository.ParentStudentRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.ParentXcxService;
import com.threefriend.lightspace.xcxutil.WeChatUtils;
import com.threefriend.lightspace.xcxutil.XcxDecryptUtils;

@Service
public class ParentXcxServiceImpl implements ParentXcxService{
	
	@Autowired
	private ParentRepository parent_dao;
	@Autowired
	private StudentRepository student_dao;
	@Autowired
	private ParentStudentRepository p_s_dao;

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
		System.out.println(params.get("sessionKey"));
		String openId = getsessionKey.get("openId").toString();
		//查一下有这个用户吗
		ParentMapper findByOpenId = parent_dao.findByOpenId(openId);
		String resphone ="";
		String phone = "";
		if(findByOpenId==null) {
			phone = getPhoneDate(params);
			ParentMapper parent = new ParentMapper();
			parent.setOpenId(openId);
			parent.setPhone(Long.valueOf(phone));
			parent.setGenTime(new Date());
			parent_dao.save(parent);
			type = "new";
			resphone= phone.substring(0, 3)+"****"+ phone.substring(7, 11);
			end.put("phone", resphone);
			end.put("openId", openId);
			end.put("type", type);
			return ResultVOUtil.success(end);
		}
		phone = findByOpenId.getPhone().toString();
		type = "old";
		openId = findByOpenId.getOpenId();
		resphone= phone.substring(0, 3)+"****"+ phone.substring(7, 11);
		end.put("phone", resphone);
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
	public ResultVO insertStudent(Map<String, String> params) {
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
		return ResultVOUtil.success(parent_dao.findByOpenId(params.get("openId")));
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
	public String getUserDate(Map<String, String> params) throws Exception {
		String sessionKey=params.get("sessionKey");
		String encryptedData = params.get("encryptedData");
		String iv = params.get("iv");
		Map<String, Object> userInfo = XcxDecryptUtils.getUserInfo(encryptedData, sessionKey, iv);
		String phone = (String) userInfo.get("purePhoneNumber"); //手机号
		return phone;
	}
	
}
