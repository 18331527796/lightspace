package com.threefriend.lightspace.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.SchoolMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.SchoolRepository;
import com.threefriend.lightspace.service.ClassesService;
import com.threefriend.lightspace.vo.ClassesVO;
import com.threefriend.lightspace.vo.SchoolVO;

/**
 * 班级业务逻辑实现
 * 
 * @author Administrator
 *
 */
@Service
public class ClassesServiceImpl implements ClassesService {

	@Autowired
	private ClassesRepository classes_dao;
	@Autowired
	private SchoolRepository school_dao;

	/*
	 * 新增班级方法
	 */
	@Override
	public List<ClassesMapper> addClasses(Map<String, String> params) {
		ClassesMapper classes = new ClassesMapper();
		System.out.println(params.get("bbLength") + "--" + params.get("experiment") + "---" + params.get("className")
				+ "---" + params.get("roomLength"));
		classes.setBbLength(Double.valueOf(params.get("bbLength")));
		System.out.println(params.get("experiment"));
		classes.setExperiment(Integer.valueOf(params.get("experiment")));
		classes.setName(params.get("className"));
		classes.setRegionId(1);
		classes.setRegionName("唐山");
		classes.setRoomLength(Double.valueOf(params.get("roomLength")));
		classes.setRoomWidth(Double.valueOf(params.get("roomWidth")));
		classes.setSchoolId(Integer.valueOf(params.get("schoolId")));
		classes.setSchoolName(school_dao.findById(Integer.valueOf(params.get("schoolId"))).get().getName());
		classes.setVolume(Integer.valueOf(params.get("volume")));
		if (!StringUtils.isEmpty(params.get("description")))
			classes.setDescription(params.get("description"));
		classes_dao.save(classes);
		return classes_dao.findAll();
	}

	/*
	 * 班级列表
	 */
	@Override
	public List<ClassesMapper> findAllClasses() {
		return classes_dao.findAll();
	}

	/*
	 * 班级修改方法
	 */
	@Override
	public List<ClassesMapper> alterClasses(Map<String, String> params) {
		ClassesMapper classes = classes_dao.findById(Integer.valueOf(params.get("id"))).get();
		if (!StringUtils.isEmpty(params.get("bbLength")))
			classes.setBbLength(Double.valueOf(params.get("bbLength")));
		if (!StringUtils.isEmpty(params.get("experiment")))
			classes.setExperiment(Integer.valueOf(params.get("experiment")));
		if (!StringUtils.isEmpty(params.get("className")))
			classes.setName(params.get("className"));
		if (!StringUtils.isEmpty(params.get("roomLength")))
			classes.setRoomLength(Double.valueOf(params.get("roomLength")));
		if (!StringUtils.isEmpty(params.get("roomWidth")))
			classes.setRoomWidth(Double.valueOf(params.get("roomWidth")));
		if (!StringUtils.isEmpty(params.get("schoolId"))) {
			classes.setSchoolId(Integer.valueOf(params.get("schoolId")));
			classes.setSchoolName(school_dao.findById(Integer.valueOf(params.get("schoolId"))).get().getName());
		}
		if (!StringUtils.isEmpty(params.get("volume")))
			classes.setVolume(Integer.valueOf(params.get("volume")));
		if (!StringUtils.isEmpty(params.get("description")))
			classes.setDescription(params.get("description"));
		classes_dao.save(classes);
		return classes_dao.findAll();
	}

	/*
	 * 班级删除方法
	 */
	@Override
	public List<ClassesMapper> deleteClasses(Integer id) {
		classes_dao.deleteById(id);
		return classes_dao.findAll();
	}

	/*
	 * 按照学校查询班级
	 */
	@Override
	public List<ClassesMapper> findBySchoolId(Integer sId) {
		return classes_dao.findBySchoolId(sId);
	}

	/*
	 * 按照id查询班级
	 */
	@Override
	public ClassesMapper findById(Integer id) {
		return classes_dao.findById(id).get();
	}

	/*
	 * 模糊查询
	 */
	@Override
	public List<ClassesMapper> findByNameLike(String name) {
		return classes_dao.findByNameLike("%" + name + "%");
	}

	/*
	 * 级联方法（服务于下拉框）
	 */
	@Override
	public List<SchoolVO> cascade() {
		List<SchoolMapper> school = school_dao.findAll();
		List<ClassesMapper> classes = classes_dao.findAll();
		List<SchoolVO> list = new ArrayList<>();
		for (SchoolMapper school1 : school) {
			SchoolVO po = new SchoolVO();
			po.setId(school1.getId());
			po.setName(school1.getName());
			for (ClassesMapper classes1 : classes) {
				ClassesVO it = new ClassesVO();
				it.setId(classes1.getId());
				it.setName(classes1.getName());
				if (school1.getId() == classes1.getSchoolId()) {
					if (po.getChildren() == null) {
						po.setChildren(new ArrayList<ClassesVO>());
					}
					po.getChildren().add(it);
				}
			}
			list.add(po);
		}
		return list;
	}

}
