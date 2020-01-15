package com.threefriend.lightspace.service;

import java.util.List;
import java.util.Map;

import com.threefriend.lightspace.mapper.ClassesMapper;

public interface ClassesService {
	
	//新增班级
	public List<ClassesMapper> addClasses(Map<String, String> params);
	//班级列表
	public List<ClassesMapper> findAllClasses();
	//修改班级信息
	public List<ClassesMapper> alterClasses(Map<String, String> params);
	//删除班级
	public List<ClassesMapper> deleteClasses(Integer id);
	/*//按照学校查询班级
	public List<ClassesMapper> findBySchoolId(Map<String, String> params);*/
	//按照id查询班级
	public ClassesMapper findById(Integer id);
}
