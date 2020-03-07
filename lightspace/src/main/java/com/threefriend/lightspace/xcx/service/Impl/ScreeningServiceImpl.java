package com.threefriend.lightspace.xcx.service.Impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.OptotypeMapper;
import com.threefriend.lightspace.mapper.ParentMapper;
import com.threefriend.lightspace.mapper.ParentStudentRelation;
import com.threefriend.lightspace.mapper.SchoolMapper;
import com.threefriend.lightspace.mapper.ScreeningMapper;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.OptotypeRepository;
import com.threefriend.lightspace.repository.ParentRepository;
import com.threefriend.lightspace.repository.ParentStudentRepository;
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
	private ScreeningRepository screening_dao;
	@Autowired
	private OptotypeRepository optotype_dao;
	@Autowired
	private ParentRepository parent_dao;
	@Autowired
	private ParentStudentRepository p_s_dao;

	/* 
	 * 三级级联
	 */
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

	

	/* 
	 * 新增的筛查记录（这个方法待定 可能会修改）
	 */
	@Override
	public ResultVO addScreening(Map<String, String> params) {
		Integer studentId = Integer.valueOf(params.get("studentId"));
		Optional<StudentMapper> findById = student_dao.findById(studentId);
		if(findById!=null&&findById.isPresent()) {
			StudentMapper student = findById.get();
			ScreeningMapper po = new ScreeningMapper();
			po.setGenTime(new Date());
			po.setStudentId(studentId);
			po.setStudentName(student.getName());
			po.setVisionLeft(Double.valueOf(params.get("visionLeft")));
			po.setVisionRight(Double.valueOf(params.get("visionRight")));
			po.setBeginEnd(params.get("beginEnd"));
			po.setDistance(params.get("distance"));
			po.setNumber(params.get("number"));
			screening_dao.save(po);
		}
		return ResultVOUtil.success();
	}

	/* 
	 * 所有的视标返回
	 */
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

	/* 
	 * 按照id查找档案
	 */
	@Override
	public ResultVO findById(Map<String, String> params) {
		return ResultVOUtil.success(screening_dao.findById(Integer.valueOf(params.get("id"))));
	}



	/* 
	 * 这个账号的所有绑定孩子的档案
	 */
	@Override
	public ResultVO allChildrenScreening(Map<String, String> params) {
		Calendar c = Calendar.getInstance();
        //过去七天
        c.setTime(new Date());
        c.add(Calendar.DATE, - 7);
        Date beginTime = c.getTime();
		Date eneTime = new Date();
		//↑定义时间 用来满足前台要求的图表返回数据
		List<Map<String, Object>> end = new ArrayList<>();
		//获取这个账号的唯一标识
		ParentMapper parent = parent_dao.findByOpenId(params.get("openId"));
		//找到这个账号绑定的孩子
		List<ParentStudentRelation> all = p_s_dao.findByParentId(parent.getId());
		//如果这个账号没有绑定孩子 返回错误提示
		if(all.size()==0)return ResultVOUtil.error(ResultEnum.BINDINGSTUDENT_ERROR.getStatus(),ResultEnum.BINDINGSTUDENT_ERROR.getMessage());
		//遍历所有的孩子信息来封装数据
		for (ParentStudentRelation ids : all) {
			//找到这个孩子的所有信息
			StudentMapper student = student_dao.findById(ids.getStudentId()).get();
			//建立map容器
			Map<String,Object> map = new HashMap<>();
			//找到这个孩子所有的档案数据
			List<ScreeningMapper> dataList = screening_dao.findByStudentIdOrderByGenTime(student.getId());
			//找到这个孩子七天内的档案数据
			List<ScreeningMapper> picList = screening_dao.findByStudentIdAndGenTimeBetween(student.getId(), beginTime, eneTime);
			map.put("id", student.getId());
			map.put("name", student.getName());
			map.put("birthday", student.getBirthday());
			map.put("dataList", dataList);
			map.put("picList", picList);
			end.add(map);
		}
		return ResultVOUtil.success(end);
	}

}
