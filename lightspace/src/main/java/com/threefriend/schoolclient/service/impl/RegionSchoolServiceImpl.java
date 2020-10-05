package com.threefriend.schoolclient.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.SchoolMapper;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.RegionRepository;
import com.threefriend.lightspace.repository.SchoolRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.service.SchoolService;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;

@Service
public class RegionSchoolServiceImpl implements SchoolService{
	@Autowired
	private SchoolRepository school_dao;
	@Autowired
	private RegionRepository region_dao;
	@Autowired
	private ClassesRepository class_dao;
	@Autowired
	private StudentRepository student_dao;

	@Override
	public ResultVO addSchool(Map<String, String> params, HttpSession session) {
		Integer regionId = Integer.valueOf(session.getAttribute("regionId").toString());
		List<SchoolMapper> findByName = school_dao.findByName(params.get("name"));
		if(findByName.size()>=1) return ResultVOUtil.error(ResultEnum.SCHOOLNAME_REPEAT.getStatus(),ResultEnum.SCHOOLNAME_REPEAT.getMessage());
		SchoolMapper newSchool = new SchoolMapper();
		newSchool.setAddress(params.get("address"));
		newSchool.setName(params.get("name"));
		newSchool.setRegionId(regionId);
		newSchool.setRegionName(region_dao.findById(regionId).get().getName());
		school_dao.save(newSchool);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO findSchoolById(Map<String, String> params) {
		SchoolMapper po = school_dao.findById(Integer.valueOf(params.get("id"))).get();
		return ResultVOUtil.success(po);
	}

	@Override
	public ResultVO findAllSchool(Map<String, String> params,HttpSession session) {
		Integer regionId = Integer.valueOf(session.getAttribute("regionId").toString());
		int page = 0;
		if(!StringUtils.isEmpty(params.get("page"))) page = Integer.valueOf(params.get("page")) - 1;
		Page<SchoolMapper> all = school_dao.findByRegionId(regionId, PageRequest.of(page, 10));
		return ResultVOUtil.success(all);
	}

	@Override
	public ResultVO alterSchool(Map<String, String> params, HttpSession session) {
		SchoolMapper findById = school_dao.findById(Integer.valueOf(params.get("id"))).get();
		List<ClassesMapper> allclass = class_dao.findBySchoolId(findById.getId(), PageRequest.of(0, 100)).getContent();
		List<StudentMapper> student = student_dao.findBySchoolId(findById.getId());
		if(!StringUtils.isEmpty(params.get("address"))) findById.setAddress(params.get("address"));
		if(!StringUtils.isEmpty(params.get("name"))) {
			findById.setName(params.get("name"));
			for (StudentMapper studentMapper : student) {
				studentMapper.setSchoolName(params.get("name"));
			}
			for (ClassesMapper classesMapper : allclass) {
				classesMapper.setSchoolName(params.get("name"));
			}
			student_dao.saveAll(student);
			class_dao.saveAll(allclass);
		}
		school_dao.save(findById);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO deleteSchool(Map<String, String> params, HttpSession session) {
		Integer id = Integer.valueOf(params.get("id"));
		school_dao.deleteById(id);
		return ResultVOUtil.success();
	}

	//咩用
	@Override
	public ResultVO findAllSchoolLike(String name) {
		return null;
	}

}
