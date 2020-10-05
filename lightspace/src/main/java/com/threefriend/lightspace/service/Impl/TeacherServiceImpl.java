package com.threefriend.lightspace.service.Impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.SchoolMapper;
import com.threefriend.lightspace.mapper.TeacherMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.SchoolRepository;
import com.threefriend.lightspace.repository.TeacherRepository;
import com.threefriend.lightspace.service.TeacherService;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.vo.TeacherVO;

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
	public ResultVO teacherList(Map<String, String> params,HttpSession session) {
		int page = 0 ; 
		String type = "";
		if(!StringUtils.isEmpty(params.get("type"))) type = params.get("type");
		if(!StringUtils.isEmpty(params.get("page")))page = Integer.valueOf(params.get("page")) - 1 ;
		if("school".equals(type))return ResultVOUtil.success(teacher_dao.findBySchoolId(Integer.valueOf(params.get("id")),PageRequest.of(page, 10,Sort.by("id").descending())));
		return ResultVOUtil.success(teacher_dao.findAll(PageRequest.of(page, 10,Sort.by("id").descending())));
	}

	/*  
	 * 新增教师
	 */
	@Override
	public ResultVO addTeacher(Map<String, String> params) {
		Integer schoolId = Integer.valueOf(params.get("schoolId"));
		Integer classId = Integer.valueOf(params.get("classId"));
		SchoolMapper school = school_dao.findById(schoolId).get();		
		TeacherMapper teacher = new TeacherMapper();
		teacher.setSchoolId(schoolId);
		teacher.setSchoolName(school.getName());
		teacher.setClassId(classId);
		teacher.setClassName(class_dao.findById(classId).get().getClassName());
		teacher.setRegionId(school.getRegionId());
		teacher.setRegionName(school.getRegionName());
		teacher.setName(params.get("name"));
		teacher.setPhone(params.get("phone"));
		teacher.setPassword(params.get("password"));
		teacher_dao.save(teacher);
		return ResultVOUtil.success();
	}

	/*  
	 * 修改教师
	 */
	@Override
	public ResultVO findById(Map<String, String> params) {
		TeacherMapper teacherMapper = teacher_dao.findById(Integer.valueOf(params.get("id"))).get();
		TeacherVO vo = new TeacherVO();
		BeanUtils.copyProperties(teacherMapper, vo);
		vo.getTea_cat().add(teacherMapper.getSchoolId());
		vo.getTea_cat().add(teacherMapper.getClassId());
		return ResultVOUtil.success(vo);
	}
	/*  
	 * 修改后保存
	 */
	@Override
	public ResultVO alertTeacher(Map<String, String> params) {
		TeacherMapper teacher = teacher_dao.findById(Integer.valueOf(params.get("id"))).get();
		if(!StringUtils.isEmpty(params.get("classId"))) {
			Integer classId = Integer.valueOf(params.get("classId"));
			ClassesMapper classpo = class_dao.findById(classId).get();
			teacher.setSchoolId(classpo.getSchoolId());
			teacher.setSchoolName(classpo.getSchoolName());
			teacher.setClassId(classId);
			teacher.setClassName(classpo.getClassName());
		}
		if(!StringUtils.isEmpty(params.get("name")))teacher.setName(params.get("name"));
		if(!StringUtils.isEmpty(params.get("phone")))teacher.setPhone(params.get("phone"));
		if(!StringUtils.isEmpty(params.get("password")))teacher.setPassword(params.get("password"));
		teacher_dao.save(teacher);
		return ResultVOUtil.success();
	}
	/*  
	 * 删除教师
	 */
	@Override
	public ResultVO deleteTeacher(Map<String, String> params) {
		teacher_dao.deleteById(Integer.valueOf(params.get("id")));
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO queryTeacher(Map<String, String> params) {
		List<TeacherMapper> list = teacher_dao.findByNameLike("%"+params.get("name")+"%");
		return ResultVOUtil.success(list);
	}

}
