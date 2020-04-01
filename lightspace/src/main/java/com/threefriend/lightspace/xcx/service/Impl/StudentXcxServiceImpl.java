package com.threefriend.lightspace.xcx.service.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threefriend.lightspace.mapper.StudentWordMapper;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.repository.StudentWordRepository;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.StudentXcxService;

@Service
public class StudentXcxServiceImpl implements StudentXcxService{

	@Autowired
	private StudentRepository student_dao;
	@Autowired
	private StudentWordRepository student_word_dao;

	@Override
	public ResultVO queryBySidCid(Map<String, String> params) {
		Integer schoolId = Integer.valueOf(params.get("schoolId"));
		Integer classId = Integer.valueOf(params.get("classId"));
		return ResultVOUtil.success(student_dao.findBySchoolIdAndClassesId(schoolId, classId));
	}

	@Override
	public ResultVO queryStudentWord(Map<String, String> params) {
		return ResultVOUtil.success(student_word_dao.findByStudentIdOrderByGenTimeDesc(Integer.valueOf(params.get("studentId"))));
	}
}
