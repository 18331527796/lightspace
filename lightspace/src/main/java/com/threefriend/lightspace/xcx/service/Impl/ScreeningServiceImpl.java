package com.threefriend.lightspace.xcx.service.Impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.OptotypeMapper;
import com.threefriend.lightspace.mapper.ParentMapper;
import com.threefriend.lightspace.mapper.SchoolMapper;
import com.threefriend.lightspace.mapper.ScreeningMapper;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.OptotypeRepository;
import com.threefriend.lightspace.repository.ParentRepository;
import com.threefriend.lightspace.repository.SchoolRepository;
import com.threefriend.lightspace.repository.ScreeningRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.repository.UserRepository;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ClassesVO;
import com.threefriend.lightspace.vo.OptotypeVO;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.vo.SchoolVO;
import com.threefriend.lightspace.vo.StudentVO;
import com.threefriend.lightspace.xcx.service.ScreeningService;

/**
 * 筛查业务逻辑实现类
 *
 */
@Service
public class ScreeningServiceImpl implements ScreeningService{
	@Autowired
	private SchoolRepository school_dao;
	@Autowired
	private ClassesRepository classes_dao;
	@Autowired
	private StudentRepository student_dao;
	@Autowired
	private UserRepository user_dao;
	@Autowired
	private ScreeningRepository screening_dao;
	@Autowired
	private OptotypeRepository optotype_dao;
	@Autowired
	private ParentRepository parent_dao;

	@Override
	public ResultVO selectStudent() {
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
		return ResultVOUtil.success(list);
	}

	@Override
	public ResultVO insertStudent(Map<String, String> params) {
		Integer studentId=Integer.valueOf(params.get("studentId"));
		ParentMapper parent = parent_dao.findByOpenId(params.get("openId"));
		parent.setStudentId(studentId);
		parent_dao.save(parent);
		return ResultVOUtil.success(student_dao.findById(studentId).get());
	}

	@Override
	public ResultVO addScreening(Map<String, String> params) {
		Integer studentId = Integer.valueOf(params.get("studentId"));
		StudentMapper student = student_dao.findById(studentId).get();
		ScreeningMapper po = new ScreeningMapper();
		po.setClassesId(student.getClassesId());
		po.setClassesName(student.getClassesName());
		po.setGenTime(new Date());
		po.setRegionId(student.getRegionId());
		po.setRegionName(student.getRegionName());
		po.setSchoolId(student.getSchoolId());
		po.setSchoolName(student.getSchoolName());
		po.setStudentId(studentId);
		po.setStudentName(student.getName());
		po.setVisionLeft(Double.valueOf(params.get("visionLeft")));
		po.setVisionRight(Double.valueOf(params.get("visionRight")));
		screening_dao.save(po);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO selectOptotype() {
		List<OptotypeMapper> allOptotype = optotype_dao.findAll();
		//准备封装的数据类型
		List<OptotypeVO> voList = new ArrayList<>();
		//遍历拷贝
		for (OptotypeMapper optotypeMapper : allOptotype) {
			OptotypeVO vo = new OptotypeVO();
			BeanUtils.copyProperties(optotypeMapper, vo);
			//拆分字符串
			String[] split = optotypeMapper.getPathStr().split(",");
			//new个list 准备实现随机
			List<Integer> ids = Arrays.asList(0,1,2,3,1);
			Collections.shuffle(ids);
			//遍历随机的list封装path
			for (int i = 0; i < ids.size(); i++) {
				int p = i;
				if(ids.get(i)==0)vo.getPath().add(new HashMap<String, String>(){{put("id", p+1+"");put("src", split[ids.get(p)]);put("answer", "左");}});
				if(ids.get(i)==1)vo.getPath().add(new HashMap<String, String>(){{put("id", p+1+"");put("src", split[ids.get(p)]);put("answer", "右");}});
				if(ids.get(i)==2)vo.getPath().add(new HashMap<String, String>(){{put("id", p+1+"");put("src", split[ids.get(p)]);put("answer", "上");}});
				if(ids.get(i)==3)vo.getPath().add(new HashMap<String, String>(){{put("id", p+1+"");put("src", split[ids.get(p)]);put("answer", "下");}});
			}
			voList.add(vo);
		}
		return ResultVOUtil.success(voList);
	}

}
