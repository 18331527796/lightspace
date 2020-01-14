package com.threefriend.lightspace.service.Impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threefriend.lightspace.mapper.SchoolMapper;
import com.threefriend.lightspace.repository.RegionRepository;
import com.threefriend.lightspace.repository.SchoolRepository;
import com.threefriend.lightspace.service.SchoolService;

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

	/* 
	 * 添加学校
	 */
	@Override
	public void addSchool(Map<String, String> params) {
		SchoolMapper newSchool = new SchoolMapper();
		newSchool.setAddress(params.get("address"));
		newSchool.setName(params.get("name"));
		newSchool.setRegionId(1);
		newSchool.setRegionName(region_dao.findById(1).get().getName());
		school_dao.save(newSchool);
	}

	/* 
	 * 学校列表
	 */
	@Override
	public List<SchoolMapper> findAllSchool(String token) {
		System.out.println("这里是学校列表方法"+token);
		List<SchoolMapper> findAll = school_dao.findAll();
		return findAll;
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
		return school_dao.findAll();
	}

	/*
	 * 删除学校
	 */
	@Override
	public void deleteSchool(Integer id) {
		school_dao.deleteById(id);
	}

	/* 
	 * 按照id查找学校
	 */
	@Override
	public SchoolMapper findSchoolById(Map<String, String> params) {
		System.out.println("按照id查找学校的方法中"+params.get("token"));
		SchoolMapper findById = school_dao.findById(Integer.valueOf(params.get("id"))).get();
		return findById;
	}

	/* 
	 * 模糊查询
	 */
	@Override
	public List<SchoolMapper> findAllSchoolLike(String name) {
		List<SchoolMapper> findByNameLike = school_dao.findByNameLike(name);
		return findByNameLike;
	}

}
