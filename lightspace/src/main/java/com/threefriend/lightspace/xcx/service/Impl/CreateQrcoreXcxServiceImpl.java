package com.threefriend.lightspace.xcx.service.Impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.SchoolRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.CreateQrcoreXcxService;
/**
 * 主要是公众号的跳转页功能
 * @author Administrator
 *
 */
@Service
public class CreateQrcoreXcxServiceImpl implements CreateQrcoreXcxService{

	@Autowired
	private SchoolRepository school_dao;
	@Autowired
	private ClassesRepository class_dao;
	@Autowired
	private StudentRepository student_dao;
	
	@Override
	public ResultVO allSchool() {
		return ResultVOUtil.success(school_dao.findAll());
	}

	@Override
	public ResultVO classByGradeAndSchool(Map<String, String> params) {
		Integer schoolId = Integer.valueOf(params.get("schoolId"));
		Integer grade = Integer.valueOf(params.get("grade"));
		return ResultVOUtil.success(class_dao.findBySchoolIdAndGradeOrderByClassNumber(schoolId,grade));
	}

	@Override
	public ResultVO studentByClassId(Map<String, String> params) {
		Integer classId = Integer.valueOf(params.get("classId"));
		return ResultVOUtil.success(student_dao.findByClassesId(classId));
	}

	@Override
	public ResultVO chkStudent(Map<String, String> params) {
		Integer studentId = Integer.valueOf(params.get("id"));
		String birthday = params.get("birthday");
		String birthday2 = student_dao.findById(studentId).get().getBirthday();
		if(birthday2.equals(birthday)) {
			return ResultVOUtil.success();
		}else {
			return ResultVOUtil.error(ResultEnum.CREATEQRCORE_CHK.getStatus(),ResultEnum.CREATEQRCORE_CHK.getMessage());
		}
	}

}
