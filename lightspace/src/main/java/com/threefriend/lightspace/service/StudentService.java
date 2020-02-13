package com.threefriend.lightspace.service;

import java.util.List;
import java.util.Map;

import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.vo.StudentVO;

/**
 *	学生业务逻辑接口
 */
public interface StudentService {

	
	//学生列表
	public List<StudentMapper> studentList(Map<String, String> params);
	//按照学校班级查询
	public List<StudentMapper> queryBySidCid(Integer sId,Integer cId);
	//新增学生
	public List<StudentMapper> addStudent(Map<String, String> params);
	//删除学生
	public List<StudentMapper> deleteStudent(Integer id,String token);
	//修改学生
	public List<StudentMapper> saveStudent(Map<String, String> params);
	//按照id查询学生
	public StudentVO findById(Integer id);
	//模糊查询
	public 	List<StudentMapper> findByNameLike(String name);
	//按照学校班级姓名模糊查询
	public List<StudentVO> findBySchoolIdAndClassesIdAndNameLike(Integer sId,Integer cId,String name);
}
