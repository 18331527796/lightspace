package com.threefriend.lightspace.service;

import java.util.List;
import java.util.Map;

import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.vo.SchoolVO;

public interface ClassesService {
	
	//新增班级
	public ResultVO addClasses(Map<String, String> params);
	//班级列表
	public List<ClassesMapper> findAllClasses(Map<String, String> params);
	//修改班级信息
	public List<ClassesMapper> alterClasses(Map<String, String> params);
	//删除班级
	public List<ClassesMapper> deleteClasses(Integer id,String token);
	//按照学校查询班级
	public List<ClassesMapper> findBySchoolId(Integer sId);
	//按照id查询班级
	public ClassesMapper findById(Integer id);
	//模糊查询
	public List<ClassesMapper> findByNameLike(String name);
	//方便于下拉框的方法 （学校——>班级——>学生）
	public List<SchoolVO> cascade();
	//方便于下拉框的方法 （学校——>班级）
	public List<SchoolVO> cascade1();
	//设置座位保存时间（废弃）
	//public void setSaveTime(Integer classId,Integer time);
}
