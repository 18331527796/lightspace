package com.threefriend.schoolclient.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.SchoolMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.SchoolRepository;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.schoolclient.service.SchoolClassService;

@Service
public class SchoolClassServiceImpl implements SchoolClassService{
	
	@Autowired
	private ClassesRepository class_dao;
	@Autowired
	private SchoolRepository school_dao;
	

	@Override
	public ResultVO classList(HttpSession session) {
		Integer schoolId = Integer.valueOf(session.getAttribute("schoolId").toString());
		List<Map<String, Object>> end = new ArrayList<Map<String,Object>>();
		for (int i = 1; i < 7; i++) {
			Map<String, Object> gradeList = new HashedMap();
			List<ClassesMapper> allClass = class_dao.findBySchoolIdAndGradeOrderByClassNumber(schoolId, i);
			gradeList.put("name", i);
			gradeList.put("number", allClass.size()+"个");
			gradeList.put("children", allClass);
			end.add(gradeList);
		}
		return ResultVOUtil.success(end);
	}

	
	@Override
	public ResultVO editClass(Map<String, String> params) {
		ClassesMapper classPo = class_dao.findById(Integer.valueOf(params.get("id"))).get();
		return ResultVOUtil.success(classPo);
	}

	@Override
	public ResultVO saveClass(Map<String, String> params) {
		ClassesMapper po = class_dao.findById(Integer.valueOf(params.get("id"))).get();
		
		return null;
	}
	
	@Override
	public ResultVO deleteClass(Map<String, String> params) {
		class_dao.deleteById(Integer.valueOf(params.get("id")));
		return ResultVOUtil.success();
	}


	@Override
	public ResultVO classNumber() {
		System.out.println("进来了");
		List<ClassesMapper> findAll = class_dao.findAll();
		for (ClassesMapper po : findAll) {
			String name = po.getClassName();
			if(name.contains("中")||name.contains("已毕业")||name.contains("社会")) continue;
			System.out.println(name+"没跳过");
			String grade = name.substring(0, 1);
			Integer classNumber = 0;
			if(name.length()==5) {
				classNumber = Integer.valueOf(name.substring(2, 3));
			}else if(name.length()==6){
				classNumber = Integer.valueOf(name.substring(2, 4));
			}
			System.out.println("班级数 classnumber："+classNumber);
			po.setClassNumber(classNumber);
			po.setGrade(equlsClassNumber(grade));
		}
		class_dao.saveAll(findAll);
		System.out.println("保存了");
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO addClass(Map<String, String> params, HttpSession session) {
		Integer schoolId = Integer.valueOf(session.getAttribute("schoolId").toString());
		SchoolMapper school = school_dao.findById(schoolId).get();
		Integer grade = Integer.valueOf(params.get("grade"));
		Integer classNumber = Integer.valueOf(params.get("classNumber"));
		
		List<ClassesMapper> haveClass = class_dao.findBySchoolIdAndGradeAndClassNumber(schoolId, grade,classNumber);
		if(haveClass!=null&&haveClass.size()>0) return ResultVOUtil.error(ResultEnum.CLASSNAME_REPEAT.getStatus(), ResultEnum.CLASSNAME_REPEAT.getMessage());
		
		ClassesMapper po = new ClassesMapper();
		po.setClassNumber(classNumber);
		po.setGrade(grade);
		po.setClassName(getClassName(grade, classNumber));
		po.setSchoolId(schoolId);
		po.setSchoolName(school.getName());
		po.setRegionId(school.getRegionId());
		po.setRegionName(school.getRegionName());
		class_dao.save(po);
		return classList(session);
	}
	
	private Integer equlsClassNumber(String str) {
		Integer classNumber = 0;
		switch (str) {
		case "一":
			classNumber = 1;
			break;
		case "二":
			classNumber = 2;
			break;
		case "三":
			classNumber = 3;
			break;
		case "四":
			classNumber = 4;
			break;
		case "五":
			classNumber = 5;
			break;
		case "六":
			classNumber = 6;
			break;
		}
		return classNumber;
	}

	private String getClassName(Integer grade,Integer classNumber) {
		StringBuilder name = new StringBuilder();
		
		switch (grade) {
		case 1:
			name.append("一");
			break;
		case 2:
			name.append("二");
			break;
		case 3:
			name.append("三");
			break;
		case 4:
			name.append("四");
			break;
		case 5:
			name.append("五");
			break;
		case 6:
			name.append("六");
			break;
		}
		name.append("（"+classNumber+"）班");
		
		return name.toString();
	}
	


	

	
}
