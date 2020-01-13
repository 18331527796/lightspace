package com.threefriend.lightspace.service;

import java.util.List;
import java.util.Map;

import com.threefriend.lightspace.mapper.SchoolMapper;

public interface SchoolService {

	//新增学校
	public void addSchool(Map<String, String> params);
	//查询学校
	public List<SchoolMapper> findAllSchool();
	
	
}
