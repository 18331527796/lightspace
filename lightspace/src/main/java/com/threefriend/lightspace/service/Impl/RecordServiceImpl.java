package com.threefriend.lightspace.service.Impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.mapper.RecordMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.RecordRepository;
import com.threefriend.lightspace.repository.RegionRepository;
import com.threefriend.lightspace.repository.SchoolRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.service.RecordService;

/**
 * 基础数据业务逻辑实现类
 */
@Service
public class RecordServiceImpl implements RecordService{
	
	@Autowired
	private RecordRepository record_dao;
	@Autowired
	private SchoolRepository school_dao;
	@Autowired
	private ClassesRepository class_dao;
	@Autowired
	private StudentRepository student_dao;
	@Autowired
	private RegionRepository region_dao;
	
	
	/* 
	 * 新增数据
	 */
	@Override
	public List<RecordMapper> addRecord(Map<String, String> params) {
		RecordMapper record = new RecordMapper();
		if(!StringUtils.isEmpty(params.get("curvatureLeft")))record.setCurvatureLeft(Double.valueOf(params.get("curvatureLeft")));
		if(!StringUtils.isEmpty(params.get("curvatureRight")))record.setCurvatureRight(Double.valueOf(params.get("curvatureRight")));
		if(!StringUtils.isEmpty(params.get("cvaLeft")))record.setCvaLeft(Double.valueOf(params.get("cvaLeft")));
		if(!StringUtils.isEmpty(params.get("cvaRight")))record.setCvaRight(Double.valueOf(params.get("cvaRight")));
		if(!StringUtils.isEmpty(params.get("diopterLeft")))record.setDiopterLeft(params.get("diopterLeft"));
		if(!StringUtils.isEmpty(params.get("diopterRight")))record.setDiopterRight(params.get("diopterRight"));
		if(!StringUtils.isEmpty(params.get("eyeAxisLengthLeft")))record.setEyeAxisLengthLeft(Double.valueOf(params.get("eyeAxisLengthLeft")));
		if(!StringUtils.isEmpty(params.get("eyeAxisLengthRight")))record.setEyeAxisLengthRight(Double.valueOf(params.get("eyeAxisLengthRight")));
		if(!StringUtils.isEmpty(params.get("visionLeft")))record.setVisionLeft(Double.valueOf(params.get("visionLeft")));
		if(!StringUtils.isEmpty(params.get("visionRight")))record.setVisionRight(Double.valueOf(params.get("visionRight")));
		record.setRegionId(1);
		record.setRegionName("唐山");
		record.setStudentId(Integer.valueOf(params.get("studentId")));
		record.setStudentName(student_dao.findById(Integer.valueOf(params.get("studentId"))).get().getName());
		record.setSchoolId(Integer.valueOf(params.get("schoolId")));
		record.setSchoolName(school_dao.findById(Integer.valueOf(params.get("schoolId"))).get().getName());
		record.setClassesId(Integer.valueOf(params.get("classId")));
		record.setClassesName(class_dao.findById(Integer.valueOf(params.get("classId"))).get().getClassName());
		record.setGenTime(new Date());
		record_dao.save(record);
		String[] split = params.get("token").split("-");
		if(split[1].equals("3"))return record_dao.findBySchoolId(Integer.valueOf(split[2]));
		if(split[1].equals("4"))return record_dao.findByClassesId(Integer.valueOf(split[2]));
		return record_dao.findAll();
	}

	/* 
	 * 修改后保存数据
	 */
	@Override
	public List<RecordMapper> saveRecord(Map<String, String> params) {
		RecordMapper record = record_dao.findById(Integer.valueOf(params.get("roleId"))).get();
		if(!StringUtils.isEmpty(params.get("curvatureLeft")))record.setCurvatureLeft(Double.valueOf(params.get("curvatureLeft")));
		if(!StringUtils.isEmpty(params.get("curvatureRight")))record.setCurvatureRight(Double.valueOf(params.get("curvatureRight")));
		if(!StringUtils.isEmpty(params.get("cvaLeft")))record.setCvaLeft(Double.valueOf(params.get("cvaLeft")));
		if(!StringUtils.isEmpty(params.get("cvaRight")))record.setCvaRight(Double.valueOf(params.get("cvaRight")));
		if(!StringUtils.isEmpty(params.get("diopterLeft")))record.setDiopterLeft(params.get("diopterLeft"));
		if(!StringUtils.isEmpty(params.get("diopterRight")))record.setDiopterRight(params.get("diopterRight"));
		if(!StringUtils.isEmpty(params.get("eyeAxisLengthLeft")))record.setEyeAxisLengthLeft(Double.valueOf(params.get("eyeAxisLengthLeft")));
		if(!StringUtils.isEmpty(params.get("eyeAxisLengthRight")))record.setEyeAxisLengthRight(Double.valueOf(params.get("eyeAxisLengthRight")));
		if(!StringUtils.isEmpty(params.get("visionLeft")))record.setVisionLeft(Double.valueOf(params.get("visionLeft")));
		if(!StringUtils.isEmpty(params.get("visionRight")))record.setVisionRight(Double.valueOf(params.get("visionRight")));
		record_dao.save(record);
		String[] split = params.get("token").split("-");
		if(split[1].equals("3"))return record_dao.findBySchoolId(Integer.valueOf(split[2]));
		if(split[1].equals("4"))return record_dao.findByClassesId(Integer.valueOf(split[2]));
		return record_dao.findAll();
	}

	/* 
	 * 删除数据
	 */
	@Override
	public List<RecordMapper> deleteRecord(Integer id,String token) {
		record_dao.deleteById(id);
		String[] split = token.split("-");
		if(split[1].equals("3"))return record_dao.findBySchoolId(Integer.valueOf(split[2]));
		if(split[1].equals("4"))return record_dao.findByClassesId(Integer.valueOf(split[2]));
		return record_dao.findAll();
	}

	/* 
	 * 修改数据
	 */
	@Override
	public RecordMapper editRecord(Integer id) {
		return record_dao.findById(id).get();
	}

	/* 
	 * 数据列表
	 */
	@Override
	public List<RecordMapper> recordList(String token) {
		String[] split = token.split("-");
		if(split[1].equals("3"))return record_dao.findBySchoolId(Integer.valueOf(split[2]));
		if(split[1].equals("4"))return record_dao.findByClassesId(Integer.valueOf(split[2]));
		return record_dao.findAll();
	}

	/* 
	 * 模糊查询
	 */
	@Override
	public List<RecordMapper> findByName(String name) {
		return record_dao.findAllByStudentNameLike("%"+name+"%");
	}

}
