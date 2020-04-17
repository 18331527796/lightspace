package com.threefriend.lightspace.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.vo.StudentVO;

/**
 *	学生业务逻辑接口
 */
public interface StudentService {

	
	//学生列表
	List<StudentMapper> studentList(Map<String, String> params);
	//按照学校班级查询
	List<StudentMapper> queryBySidCid(Integer sId,Integer cId);
	//新增学生
	List<StudentMapper> addStudent(Map<String, String> params);
	//删除学生
	List<StudentMapper> deleteStudent(Integer id,String token);
	//修改学生
	List<StudentMapper> saveStudent(Map<String, String> params);
	//按照id查询学生
	StudentVO findById(Integer id);
	//模糊查询
	ResultVO findByNameLike(String name);
	//按照学校班级姓名模糊查询
	List<StudentVO> findBySchoolIdAndClassesIdAndNameLike(Integer sId,Integer cId,String name);
	//导入excel
	ResultVO readStudentExcel(MultipartFile file, String token);
	//下载学生导入模板（流方式）（暂停使用）
	void download(HttpServletResponse response);
	
	
}
