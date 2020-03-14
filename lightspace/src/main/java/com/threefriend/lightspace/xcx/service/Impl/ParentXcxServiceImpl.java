package com.threefriend.lightspace.xcx.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class ParentXcxServiceImpl implements ParentXcxService{
	
	@Autowired
	private ParentRepository parent_dao;
	@Autowired
	private StudentRepository student_dao;
	@Autowired
	private ParentStudentRepository p_s_dao;
	
	//测试用的验证码
	private final String check="1234";

	/* 
	 * 登陆验证
	 */
	@Override
	public ResultVO loginXcx(Map<String, String> params) {
		if(!params.get("chk").equals(check)) return ResultVOUtil.error(ResultEnum.CHECK_ERROR.getStatus(), ResultEnum.CHECK_ERROR.getMessage());
		ParentMapper findByOpenId = parent_dao.findByOpenId(params.get("openId"));
		if(findByOpenId==null) {
			ParentMapper parent = new ParentMapper();
			parent.setName(params.get("name"));
			parent.setOpenId(params.get("openId"));
			parent.setPhone(Long.valueOf(params.get("phone")));
			parent.setGenTime(new Date());
			parent_dao.save(parent);
			return ResultVOUtil.success(parent);
		}
		return ResultVOUtil.success(findByOpenId);
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
			end.add(student_dao.findById(id.getStudentId()).get());
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
		ParentStudentRelation findByStudentId = p_s_dao.findByStudentId(studentId);
		//如果非空 就是被绑定了 返回错误提示 孩子被其他账号绑定  （这个情况暂时不适用）
		//if(findByStudentId!=null)return ResultVOUtil.error(ResultEnum.PARENTSTUDENT_ERROR.getStatus(),ResultEnum.PARENTSTUDENT_ERROR.getMessage() );
		//家长
		ParentMapper parent = parent_dao.findByOpenId(params.get("openId"));
		//学生
		StudentMapper studentMapper = student_dao.findById(studentId).get();
		studentMapper.setBirthday(params.get("birthday"));
		student_dao.save(studentMapper);
		
		Integer parentId = parent.getId();
		String phone = parent.getPhone().toString();
		String parentPhone = studentMapper.getParentPhone();
		//如果手机号对不上 返回提示
		if(!phone.equals(parentPhone))return ResultVOUtil.error(ResultEnum.STUDENTPHONE_ERROR.getStatus(),ResultEnum.STUDENTPHONE_ERROR.getMessage() );
		//从中间表中查询
		List<ParentStudentRelation> findByParentId = p_s_dao.findByParentId(parentId);
		//这个孩子绑定过了 而且是这个家长 就直接返回成功
		if(findByStudentId!=null&&parentId==findByStudentId.getParentId()) return childrenList(params);
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
		p_s_dao.deleteByStudentId(studentId);
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
	
}
