package com.threefriend.lightspace.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threefriend.lightspace.aspect.Mylog;
import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.SchoolMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.RecordRepository;
import com.threefriend.lightspace.repository.RegionRepository;
import com.threefriend.lightspace.repository.SchoolRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.service.SchoolService;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;

/**
 * 学校逻辑实现类
 * @author Administrator
 *
 */
@Service
public class SchoolServiceImpl implements SchoolService{
	
	@Autowired
	private SchoolRepository school_dao;
	@Autowired
	private RegionRepository region_dao;
	@Autowired
	private ClassesRepository class_dao;
	@Autowired
	private StudentRepository student_dao;
	@Autowired
	private RecordRepository record_dao;

	/* 
	 * 添加学校
	 */
	@Override
	@Mylog(value=("添加学校"))
	public ResultVO addSchool(Map<String, String> params) { 
		List<SchoolMapper> findByName = school_dao.findByName(params.get("name"));
		if(findByName.size()>=1) return ResultVOUtil.error(ResultEnum.SCHOOLNAME_REPEAT.getStatus(),ResultEnum.SCHOOLNAME_REPEAT.getMessage());
		SchoolMapper newSchool = new SchoolMapper();
		newSchool.setAddress(params.get("address"));
		newSchool.setName(params.get("name"));
		newSchool.setRegionId(1);
		newSchool.setRegionName(region_dao.findById(1).get().getName());
		school_dao.save(newSchool);
		return ResultVOUtil.success(school_dao.findAllByOrderByIdDesc());
	}

	/* 
	 * 学校列表
	 */
	@Override
	public List<SchoolMapper> findAllSchool(String token) {
		System.out.println("这里是学校列表方法"+token);
		return school_dao.findAllByOrderByIdDesc();
	}

	/*  
	 * 修改学校信息
	 */
	@Override
	public List<SchoolMapper> alterSchool(Map<String, String> params) {
		SchoolMapper findById = school_dao.findById(Integer.valueOf(params.get("id"))).get();
		if(params.get("address")!=(null) && !params.get("address").equals("")) findById.setAddress(params.get("address"));
		if(params.get("name")!=(null) && !params.get("name").equals("")) findById.setName(params.get("name"));
		school_dao.save(findById);
		return school_dao.findAllByOrderByIdDesc();
	}

	/*
	 * 删除学校
	 */
	@Override
	@Mylog(value=("删除学校"))
	public List<SchoolMapper> deleteSchool(Integer id) {
		List<ClassesMapper> findBySchoolId = class_dao.findBySchoolIdOrderByIdDesc(id);
		List<Integer> classids= new ArrayList<>();
		for (ClassesMapper po : findBySchoolId) {
			classids.add(po.getId());
		}
		student_dao.deleteByClassesId(classids);
		record_dao.deleteBySchoolId(id);
		class_dao.deleteBySchoolId(id);
		school_dao.deleteById(id);
		return school_dao.findAllByOrderByIdDesc();
	}

	/* 
	 * 按照id查找学校
	 */
	@Override
	public SchoolMapper findSchoolById(Map<String, String> params) {
		SchoolMapper findById = school_dao.findById(Integer.valueOf(params.get("id"))).get();
		return findById;
	}

	/* 
	 * 模糊查询
	 */
	@Override
	public ResultVO findAllSchoolLike(String name) {
		List<SchoolMapper> findByNameLike = school_dao.findByNameLike("%"+name+"%");
		if(findByNameLike==null||findByNameLike.size()==0) return ResultVOUtil.error(ResultEnum.SCHOOLSIZE_NULL.getStatus(),ResultEnum.SCHOOLSIZE_NULL.getMessage());
		return ResultVOUtil.success(findByNameLike);
	}

}
