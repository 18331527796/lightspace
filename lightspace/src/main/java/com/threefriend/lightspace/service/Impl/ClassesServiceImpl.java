package com.threefriend.lightspace.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.SchoolMapper;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.RecordRepository;
import com.threefriend.lightspace.repository.SchoolRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.service.ClassesService;
import com.threefriend.lightspace.util.RedisUtils;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ClassesVO;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.vo.SchoolVO;
import com.threefriend.lightspace.vo.StudentVO;

/**
 * 班级业务逻辑实现
 * 
 * @author Administrator
 *
 */
@Service
public class ClassesServiceImpl implements ClassesService {

	@Resource
	private RedisUtils redisUtil;
	@Autowired
	private ClassesRepository classes_dao;
	@Autowired
	private SchoolRepository school_dao;
	@Autowired
	private StudentRepository student_dao;
	@Autowired
	private RecordRepository record_dao;

	/*
	 * 新增班级方法
	 */
	@Override
	public ResultVO addClasses(Map<String, String> params) {
		List<ClassesMapper> findBySchoolIdAndClassName = classes_dao.findBySchoolIdAndClassName(Integer.valueOf(params.get("schoolId")), params.get("className"));
		if(findBySchoolIdAndClassName.size()>=1)return ResultVOUtil.error(ResultEnum.CLASSNAME_REPEAT.getStatus(), ResultEnum.CLASSNAME_REPEAT.getMessage());
		ClassesMapper classes = new ClassesMapper();
		classes.setBbLength(params.get("bbLength"));
		classes.setExperiment(Integer.valueOf(params.get("experiment")));
		classes.setClassName(params.get("className"));
		classes.setRegionId(1);
		classes.setRegionName("唐山");
		classes.setRoomLength(params.get("roomLength"));
		classes.setRoomWidth(params.get("roomWidth"));
		classes.setSchoolId(Integer.valueOf(params.get("schoolId")));
		classes.setSchoolName(school_dao.findById(Integer.valueOf(params.get("schoolId"))).get().getName());
		classes.setVolume(Integer.valueOf(params.get("volume")));
		if (!StringUtils.isEmpty(params.get("description")))
			classes.setDescription(params.get("description"));
		classes_dao.save(classes);
		String[] split = params.get("token").split("-");
		if(split[1].equals("3"))return ResultVOUtil.success(classes_dao.findBySchoolIdOrderByIdDesc(Integer.valueOf(split[2])));
		return ResultVOUtil.success(classes_dao.findAllByOrderByIdDesc());
	}

	/*
	 * 班级列表
	 */
	@Override
	public List<ClassesMapper> findAllClasses(Map<String, String> params) {
		String[] split = params.get("token").split("-");
		if(split[1].equals("3"))return classes_dao.findBySchoolIdOrderByIdDesc(Integer.valueOf(split[2]));
		return classes_dao.findAllByOrderByIdDesc();
	}

	/*
	 * 班级修改方法
	 */
	@Override
	public List<ClassesMapper> alterClasses(Map<String, String> params) {
		ClassesMapper classes = classes_dao.findById(Integer.valueOf(params.get("id"))).get();
		if (!StringUtils.isEmpty(params.get("bbLength")))
			classes.setBbLength(params.get("bbLength"));
		if (!StringUtils.isEmpty(params.get("experiment")))
			classes.setExperiment(Integer.valueOf(params.get("experiment")));
		if (!StringUtils.isEmpty(params.get("className")))
			classes.setClassName(params.get("className"));
		if (!StringUtils.isEmpty(params.get("roomLength")))
			classes.setRoomLength(params.get("roomLength"));
		if (!StringUtils.isEmpty(params.get("roomWidth")))
			classes.setRoomWidth(params.get("roomWidth"));
		if (!StringUtils.isEmpty(params.get("schoolId"))) {
			classes.setSchoolId(Integer.valueOf(params.get("schoolId")));
			classes.setSchoolName(school_dao.findById(Integer.valueOf(params.get("schoolId"))).get().getName());
		}
		if (!StringUtils.isEmpty(params.get("volume")))
			classes.setVolume(Integer.valueOf(params.get("volume")));
		if (!StringUtils.isEmpty(params.get("description"))) {
			classes.setDescription(params.get("description"));
		}else {
			classes.setDescription("");
		}
		classes_dao.save(classes);
		String[] split = params.get("token").split("-");
		if(split[1].equals("3"))return classes_dao.findBySchoolIdOrderByIdDesc(Integer.valueOf(split[2]));
		return classes_dao.findAllByOrderByIdDesc();
	}

	/*
	 * 班级删除方法
	 */
	@Override
	public List<ClassesMapper> deleteClasses(Integer id,String token) {
		student_dao.deleteByClassesId(id);
		record_dao.deleteByClassesId(id);
		classes_dao.deleteById(id);
		String[] split = token.split("-");
		if(split[1].equals("3"))return classes_dao.findBySchoolIdOrderByIdDesc(Integer.valueOf(split[2]));
		return classes_dao.findAllByOrderByIdDesc();
	}

	/*
	 * 按照学校查询班级
	 */
	@Override
	public List<ClassesMapper> findBySchoolId(Integer sId) {
		return classes_dao.findBySchoolIdOrderByIdDesc(sId);
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
	public ResultVO findByNameLike(String name) {
		List<ClassesMapper> list = classes_dao.findByClassNameLike("%" + name + "%");
		if(list==null||list.size()==0)return ResultVOUtil.error(ResultEnum.CLASSSIZE_NULL.getStatus(), ResultEnum.CLASSSIZE_NULL.getMessage());
		return ResultVOUtil.success(list);
	}

	/*
	 * 级联方法（服务于下拉框）学校到学生
	 */
	@Override
	public List<SchoolVO> cascade() {

		List<SchoolMapper> school = school_dao.findAll();
		List<ClassesMapper> classes = classes_dao.findAll();
		List<StudentMapper> student = student_dao.findAll();
		List<SchoolVO> list = new ArrayList<>();
		for (SchoolMapper school1 : school) {
			SchoolVO po = new SchoolVO();
			po.setId(school1.getId());
			po.setName(school1.getName());
			for (ClassesMapper classes1 : classes) {
				ClassesVO it = new ClassesVO();
				for (StudentMapper student1 : student) {
					StudentVO vo = new StudentVO();
					if (classes1.getId() == student1.getClassesId()) {
						if (it.getChildren() == null) {
							it.setChildren(new ArrayList<StudentVO>());
							vo.setId(student1.getId());
							vo.setName(student1.getName());
						}
						vo.setId(student1.getId());
						vo.setName(student1.getName());
						it.getChildren().add(vo);
					}
				}
				if (school1.getId() == classes1.getSchoolId()) {
					if (po.getChildren() == null) {
						po.setChildren(new ArrayList<ClassesVO>());
						it.setId(classes1.getId());
						it.setName(classes1.getClassName());
					}
					it.setId(classes1.getId());
					it.setName(classes1.getClassName());
					po.getChildren().add(it);
				}
			}
			list.add(po);
		}
		return list;
	}

	/*
	 * 级联方法（服务于下拉框）学校到班级
	 */
	@Override
	public List<SchoolVO> cascade1() {
		List<SchoolMapper> school = school_dao.findAll();
		List<ClassesMapper> classes = classes_dao.findAll();
		List<SchoolVO> list = new ArrayList<>();
		for (SchoolMapper school1 : school) {
			SchoolVO po = new SchoolVO();
			po.setId(school1.getId());
			po.setName(school1.getName());
			for (ClassesMapper classes1 : classes) {
				ClassesVO it = new ClassesVO();
				if (school1.getId() == classes1.getSchoolId()) {
					if (po.getChildren() == null) {
						po.setChildren(new ArrayList<ClassesVO>());
						it.setId(classes1.getId());
						it.setName(classes1.getClassName());
					}
					it.setId(classes1.getId());
					it.setName(classes1.getClassName());
					po.getChildren().add(it);
				}
			}
			list.add(po);
		}
		return list;
	}

	/*  
	 * 一键升年级
	 */
	@Override
	public ResultVO elevateClass(Map<String, String> params) {
		List<ClassesMapper> findAll = classes_dao.findAll();
		for (ClassesMapper cpo : findAll) {
			cpo.setClassName(equalsClass(cpo.getClassName()));
			List<StudentMapper> student = student_dao.findByClassesId(cpo.getId());
			for (StudentMapper spo : student) {
				spo.setClassesName(cpo.getClassName());
				student_dao.save(spo);
			}
			classes_dao.save(cpo);
		}
		return ResultVOUtil.success(findAllClasses(params));
	}

	@Override
	public String equalsClass(String name) {
		String str = name.substring(0, 1);
		String end = name.substring(1);
		switch (str) {
		case "一":
			str="二";
			break;
		case "二":
			str="三";
			break;
		case "三":
			str="四";	
			break;
		case "四":
			str="五";		
			break;
		case "五":
			str="六";			
			break;
		default:
			str="已毕业";
			break;
		}
		if("已毕业".equals(str))return str;
		return str+end;
	}

}
