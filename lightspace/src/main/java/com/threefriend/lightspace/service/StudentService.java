package com.threefriend.lightspace.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.vo.ResultVO;

/**
 *	学生业务逻辑接口
 */
public interface StudentService {

	
	//学生列表
	ResultVO studentList(Map<String, String> params,HttpSession session);
	//按照学校班级查询
	ResultVO queryBySidCid(Integer cId);
	//新增学生
	ResultVO addStudent(Map<String, String> params);
	//删除学生
	ResultVO deleteStudent(Integer id,String token);
	//修改学生
	ResultVO saveStudent(Map<String, String> params);
	//按照id查询学生
	ResultVO findById(Integer id);
	//模糊查询
	ResultVO findByNameLike(String name);
	//按照学校班级姓名模糊查询
	ResultVO findBySchoolIdAndClassesIdAndNameLike(Integer sId,Integer cId,String name);
	//导入excel
	ResultVO readStudentExcel(MultipartFile file, String token);
	
	
}
