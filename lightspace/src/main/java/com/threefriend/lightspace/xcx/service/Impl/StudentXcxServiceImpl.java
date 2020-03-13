package com.threefriend.lightspace.xcx.service.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.StudentXcxService;

@Service
public class StudentXcxServiceImpl implements StudentXcxService{

	@Autowired
	private StudentRepository stuent_dao;

	@Override
	public ResultVO queryBySidCid(Map<String, String> params) {
		Integer schoolId = Integer.valueOf(params.get("schoolId"));
		Integer classId = Integer.valueOf(params.get("classId"));
		return ResultVOUtil.success(stuent_dao.findBySchoolIdAndClassesId(schoolId, classId));
	}
}
