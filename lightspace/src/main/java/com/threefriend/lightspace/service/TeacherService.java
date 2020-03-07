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
 *	教师业务逻辑接口
 */
public interface TeacherService {
	//教师列表
	ResultVO teacherList();
	//新增教师
	ResultVO addTeacher(Map<String, String> params);
	//修改教师
	ResultVO findById(Map<String, String> params);
	//修改后保存
	ResultVO alertTeacher(Map<String, String> params);
	//删除教师
	ResultVO deleteTeacher(Map<String, String> params);
}
