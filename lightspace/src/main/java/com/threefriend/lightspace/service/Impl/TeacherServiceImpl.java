package com.threefriend.lightspace.service.Impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.mapper.TeacherMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.SchoolRepository;
import com.threefriend.lightspace.repository.TeacherRepository;
import com.threefriend.lightspace.service.TeacherService;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;

/**
 *	教师业务逻辑实现
 */
@Service
public class TeacherServiceImpl implements TeacherService{
	@Autowired
	private TeacherRepository teacher_dao;
	@Autowired
	private SchoolRepository school_dao;
	@Autowired
	private ClassesRepository class_dao;

	/*  
	 * 教师列表
	 */
	@Override
	public ResultVO teacherList() {
		return ResultVOUtil.success(teacher_dao.findAll());
	}

	/*  
	 * 新增教师
	 */
	@Override
	public ResultVO addTeacher(Map<String, String> params) {
		Integer schoolId = Integer.valueOf(params.get("schoolId"));
		Integer classId = Integer.valueOf(params.get("classId"));
		TeacherMapper teacher = new TeacherMapper();
		teacher.setSchoolId(schoolId);
		teacher.setSchoolName(school_dao.findById(schoolId).get().getName());
		teacher.setClassId(classId);
		teacher.setClassName(class_dao.findById(classId).get().getClassName());
		teacher.setName(params.get("name"));
		teacher.setPhone(params.get("phone"));
		teacher_dao.save(teacher);
		return ResultVOUtil.success(teacher_dao.findAll());
	}

	/*  
	 * 修改教师
	 */
	@Override
	public ResultVO findById(Map<String, String> params) {
		return ResultVOUtil.success(teacher_dao.findById(Integer.valueOf(params.get("id"))));
	}
	/*  
	 * 修改后保存
	 */
	@Override
	public ResultVO alertTeacher(Map<String, String> params) {
		TeacherMapper teacher = teacher_dao.findById(Integer.valueOf(params.get("id"))).get();
		if(!StringUtils.isEmpty(params.get("schoolId"))) {
			Integer schoolId = Integer.valueOf(params.get("schoolId"));
			teacher.setSchoolId(schoolId);
			teacher.setSchoolName(school_dao.findById(schoolId).get().getName());
		}
		if(!StringUtils.isEmpty(params.get("classId"))) {
			Integer classId = Integer.valueOf(params.get("classId"));
			teacher.setClassId(classId);
			teacher.setClassName(class_dao.findById(classId).get().getClassName());
		}
		if(!StringUtils.isEmpty(params.get("name")))teacher.setName(params.get("name"));
		if(!StringUtils.isEmpty(params.get("phone")))teacher.setPhone(params.get("phone"));
		teacher_dao.save(teacher);
		return ResultVOUtil.success(teacher_dao.findAll());
	}
	/*  
	 * 删除教师
	 */
	@Override
	public ResultVO deleteTeacher(Map<String, String> params) {
		teacher_dao.deleteById(Integer.valueOf(params.get("id")));
		return ResultVOUtil.success(teacher_dao.findAll());
	}

}
