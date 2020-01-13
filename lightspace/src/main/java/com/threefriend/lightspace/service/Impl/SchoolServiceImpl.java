package com.threefriend.lightspace.service.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threefriend.lightspace.mapper.SchoolMapper;
import com.threefriend.lightspace.repository.RegionRepository;
import com.threefriend.lightspace.repository.SchoolRepository;
import com.threefriend.lightspace.service.SchoolService;

@Service
public class SchoolServiceImpl implements SchoolService{
	
	@Autowired
	private SchoolRepository school_dao;
	@Autowired
	private RegionRepository region_dao;

	@Override
	public void addSchool(Map<String, String> params) {
		SchoolMapper newSchool = new SchoolMapper();
		newSchool.setAddress(params.get("adress"));
		newSchool.setName(params.get("name"));
		newSchool.setRegionId(1);
		newSchool.setRegionName(region_dao.findById(1).get().getName());
		school_dao.save(newSchool);
	}

	@Override
	public List<SchoolMapper> findAllSchool() {
		List<SchoolMapper> findAll = school_dao.findAll();
		return findAll;
	}

}
