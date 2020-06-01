package com.threefriend.lightspace.service;

import java.util.List;
import java.util.Map;

import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.vo.SchoolVO;

/**
 *	班级业务逻辑
 */
public interface ClassesService {
	
	//新增班级
	public ResultVO addClasses(Map<String, String> params);
	//班级列表
	public ResultVO findAllClasses(Map<String, String> params);
	//修改班级信息
	public ResultVO alterClasses(Map<String, String> params);
	//删除班级
	public ResultVO deleteClasses(Integer id,String token);
	//按照学校查询班级
	public ResultVO findBySchoolId(Integer sId);
	//按照id查询班级
	public ResultVO findById(Integer id);
	//升年级
	public ResultVO elevateClass(Map<String, String> params);
	//判定年级名称
	public String equalsClass(String name);
	//方便于下拉框的方法 （学校——>班级——>学生）
	public List<SchoolVO> cascade();
	//方便于下拉框的方法 （学校——>班级）
	public List<SchoolVO> cascade1();
}
