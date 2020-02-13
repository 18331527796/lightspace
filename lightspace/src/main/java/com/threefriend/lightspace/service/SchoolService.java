package com.threefriend.lightspace.service;

import java.util.List;
import java.util.Map;

import com.threefriend.lightspace.mapper.SchoolMapper;
import com.threefriend.lightspace.vo.ResultVO;

/**
 * 学校业务逻辑
 * @author Administrator
 *
 */
public interface SchoolService {

	//新增学校
	public ResultVO addSchool(Map<String, String> params);
	//按照id查询学校
	public SchoolMapper findSchoolById(Map<String, String> params);
	//查询学校
	public List<SchoolMapper> findAllSchool(String token);
	//修改学校信息
	public List<SchoolMapper> alterSchool(Map<String, String> params);
	//删除学校
	public List<SchoolMapper> deleteSchool(Integer id);
	//模糊查询
	public List<SchoolMapper> findAllSchoolLike(String name);
	
}
