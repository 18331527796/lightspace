package com.threefriend.lightspace.xcx.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.enums.UrlEnums;
import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.util.CreateQrcore;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.util.WaterMarkUtils;
import com.threefriend.lightspace.util.ZipUtils;
import com.threefriend.lightspace.vo.ResultVO;

@RestController
@RequestMapping("/xcx")
public class HelloWord {
	@Autowired
	private StudentRepository dao;
	@Autowired
	private ClassesRepository class_dao;
	@Autowired
	private StudentRepository student_dao;

	@RequestMapping("/hello")
	public String HelloWorld() {
		System.out.println("成功了");
		return "HelloWorld";
	}
	
	
    @RequestMapping("/changeAge")
    public ResultVO changeAge(){
       List<StudentMapper> findAll = dao.findAll();
       for (StudentMapper studentMapper : findAll) {
    	   String birthday = studentMapper.getBirthday();
    	   if(StringUtils.isEmpty(birthday))continue;
    	   String string = birthday.substring(0, 4);
    	   studentMapper.setAge(2020-Integer.valueOf(string));
       }
       dao.saveAll(findAll);
        return ResultVOUtil.success();
    }
    
    @RequestMapping("/changeClassNumber")
    public ResultVO changeClassNumber(){
    	List<ClassesMapper> findAll = class_dao.findAll();
       for (ClassesMapper classesMapper : findAll) {
    	   int size = dao.findByClassesId(classesMapper.getId()).size();
    	   classesMapper.setVolume(size);
       }
       class_dao.saveAll(findAll);
        return ResultVOUtil.success();
    }
    
    @RequestMapping("/shengri")
    public ResultVO testsss() {
    	List<StudentMapper> all = student_dao.findAll();
    	for (StudentMapper po : all) {
    		String birthday = po.getBirthday();
    		if(birthday==null)continue;
    		if(birthday.contains("-"))po.setBirthday(birthday.replace("-", "/"));
    		if(birthday.contains("."))po.setBirthday(birthday.replace(".", "/"));
		}
    	student_dao.saveAll(all);
    	return ResultVOUtil.success();
    }
	
	
}
