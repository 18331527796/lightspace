package com.threefriend.lightspace.xcx.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
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
import com.threefriend.lightspace.mapper.xcx.IntegralMapper;
import com.threefriend.lightspace.mapper.xcx.ParentStudentRelation;
import com.threefriend.lightspace.mapper.xcx.ScreeningMapper;
import com.threefriend.lightspace.mapper.xcx.ScreeningWearMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.IntegralRepository;
import com.threefriend.lightspace.repository.ParentStudentRepository;
import com.threefriend.lightspace.repository.ScreeningRepository;
import com.threefriend.lightspace.repository.ScreeningWearRepository;
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
	@Autowired
	private ScreeningRepository screening_dao;
	@Autowired
	private ScreeningWearRepository screening_wear_dao;
	@Autowired
	private IntegralRepository integral_dao;
	@Autowired
	private ParentStudentRepository p_s_dao;

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
	
    
    @RequestMapping("/yizhixinxi")
    @Transactional
    public void yizhixinxi() {
    	List<StudentMapper> all = student_dao.findAll();
    	//用来找最小的id 学校的孩子
    	Integer xuexiao = null;
    	for (StudentMapper po : all) {
    		
    		xuexiao = 0;
    		
    		List<StudentMapper> names = student_dao.findByNameAndBirthday(po.getName(),po.getBirthday());
    		//如果长度小于2 就是没有问题的
    		if(names.size()<2)continue;
    		//找 学校的孩子
    		for (StudentMapper name : names) {
				if(name.getClassesId()!=202) {
					xuexiao = name.getId();
				}
			}
    		//说明没有找到有学校的孩子
    		if(xuexiao==0)continue;
    		//学校的孩子
    		StudentMapper student = student_dao.findById(xuexiao).get();
    		
    		for (StudentMapper po1 : names) {
    			//这个孩子是学校的 跳过
				if(po1.getId()==xuexiao)continue;
				
				List<ScreeningMapper> screening = screening_dao.findByStudentIdOrderByGenTimeDesc(po1.getId());
				if(screening.size()>0) {
					for (ScreeningMapper screeningMapper : screening) {
						screeningMapper.setStudentId(student.getId());
						screeningMapper.setClassId(student.getClassesId());
						screeningMapper.setClassName(student.getClassesName());
						screeningMapper.setSchoolId(student.getSchoolId());
						screeningMapper.setSchoolName(student.getSchoolName());
					}
					screening_dao.saveAll(screening);
				}
				
				List<ScreeningWearMapper> screeningwear = screening_wear_dao.findByStudentIdOrderByGenTimeDesc(po1.getId());
				if(screeningwear.size()>0) {
					for (ScreeningWearMapper screeningWearMapper : screeningwear) {
						screeningWearMapper.setStudentId(student.getId());
						screeningWearMapper.setClassId(student.getClassesId());
						screeningWearMapper.setClassName(student.getClassesName());
						screeningWearMapper.setSchoolId(student.getSchoolId());
						screeningWearMapper.setSchoolName(student.getSchoolName());
					}
					screening_wear_dao.saveAll(screeningwear);
				}
				
				List<IntegralMapper> integral = integral_dao.findByStudentIdOrderByGenTimeDesc(po1.getId());
				if(integral.size()>0) {
					for (IntegralMapper integralMapper : integral) {
						integralMapper.setStudentId(student.getId());
					}
					integral_dao.saveAll(integral);
				}
				
				List<ParentStudentRelation> p_s = p_s_dao.findByStudentId(po1.getId());
				if(p_s.size()>0) {
					for (ParentStudentRelation p : p_s) {
						p.setStudentId(student.getId());
					}
					p_s_dao.saveAll(p_s);
				}
				
				
				if(po1.getLastTime()!=null&&po.getSendTime()!=null) {
					student.setLastTime(po1.getSendTime());
					student.setSendTime(po1.getSendTime());
					student.setVisionLeftStr(po1.getVisionLeftStr());
					student.setVisionRightStr(po1.getVisionRightStr());
					student.setScreeningType(po1.getScreeningType());
					student.setDominantEye(po1.getDominantEye());
					student.setRemindDecline(po1.getRemindDecline());
					student.setRemindUndetected(po1.getRemindDecline());
					student.setRemindUntask(po1.getRemindDecline());
				}
				
				student_dao.delete(po1);
    		}
    		
    		student_dao.save(student);
		}
    	System.out.println("成功移植所有的孩子信息");
    }
    
    @RequestMapping("/fugaixinxi")
    public void fugai() {
    	List<StudentMapper> all = student_dao.findAll();
    	for (StudentMapper po : all) {
    		ScreeningWearMapper screening_wear = screening_wear_dao.findTopByStudentIdOrderByGenTime(po.getId());
    		ScreeningMapper screening = screening_dao.findTopByStudentIdOrderByGenTimeDesc(po.getId());
    		if(screening==null&&screening_wear==null)continue;
    		if(screening!=null&&screening_wear==null) {
    			po.setLastTime(screening.getGenTime());
    			po.setSendTime(screening.getGenTime());
    			po.setVisionLeftStr(screening.getVisionLeftStr());
    			po.setVisionRightStr(screening.getVisionRightStr());
    			po.setScreeningType(1);
    			po.setRemindDecline(screening.getGenTime());
    			po.setRemindUndetected(screening.getGenTime());
    			po.setRemindUntask(screening.getGenTime());
    		}else if(screening_wear!=null&&screening==null) {
    			po.setLastTime(screening_wear.getGenTime());
    			po.setSendTime(screening_wear.getGenTime());
    			po.setVisionLeftStr(screening_wear.getVisionLeftStr());
    			po.setVisionRightStr(screening_wear.getVisionRightStr());
    			po.setScreeningType(2);
    			po.setRemindDecline(screening_wear.getGenTime());
    			po.setRemindUndetected(screening_wear.getGenTime());
    			po.setRemindUntask(screening_wear.getGenTime());
    		}else if(screening_wear!=null&&screening!=null){
    			if(screening.getGenTime().getTime()>=screening_wear.getGenTime().getTime()) {
        			po.setLastTime(screening.getGenTime());
        			po.setSendTime(screening.getGenTime());
        			po.setVisionLeftStr(screening.getVisionLeftStr());
        			po.setVisionRightStr(screening.getVisionRightStr());
        			po.setScreeningType(1);
        			po.setRemindDecline(screening.getGenTime());
        			po.setRemindUndetected(screening.getGenTime());
        			po.setRemindUntask(screening.getGenTime());
        		}else {
        			po.setLastTime(screening_wear.getGenTime());
        			po.setSendTime(screening_wear.getGenTime());
        			po.setVisionLeftStr(screening_wear.getVisionLeftStr());
        			po.setVisionRightStr(screening_wear.getVisionRightStr());
        			po.setScreeningType(2);
        			po.setRemindDecline(screening_wear.getGenTime());
        			po.setRemindUndetected(screening_wear.getGenTime());
        			po.setRemindUntask(screening_wear.getGenTime());
        		}
    		}
    		
		}
    	student_dao.saveAll(all);
    }
	
}
