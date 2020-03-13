package com.threefriend.lightspace.xcx.service;

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
public interface StudentXcxService {

	
	//按照学校班级查询
	ResultVO queryBySidCid(Map<String, String> params);
	
}
