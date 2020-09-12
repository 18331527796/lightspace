package com.threefriend.lightspace.service.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.Exception.ReadWordException;
import com.threefriend.lightspace.Exception.SendMessageException;
import com.threefriend.lightspace.enums.AccountEnums;
import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.mapper.MsgTempMapper;
import com.threefriend.lightspace.mapper.SchoolMapper;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.mapper.StudentWordMapper;
import com.threefriend.lightspace.mapper.xcx.GzhUserMapper;
import com.threefriend.lightspace.mapper.xcx.ParentMapper;
import com.threefriend.lightspace.mapper.xcx.ParentStudentRelation;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.GzhUserRepository;
import com.threefriend.lightspace.repository.IntegralRepository;
import com.threefriend.lightspace.repository.MsgTempRepository;
import com.threefriend.lightspace.repository.ParentRepository;
import com.threefriend.lightspace.repository.ParentStudentRepository;
import com.threefriend.lightspace.repository.RecordRepository;
import com.threefriend.lightspace.repository.SchoolRepository;
import com.threefriend.lightspace.repository.ScreeningRepository;
import com.threefriend.lightspace.repository.ScreeningWearRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.repository.StudentWordRepository;
import com.threefriend.lightspace.service.StudentService;
import com.threefriend.lightspace.util.DownTemplateUtil;
import com.threefriend.lightspace.util.RedisUtils;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.vo.StudentVO;
import com.threefriend.lightspace.xcx.util.SendMessageUtils;
import com.threefriend.lightspace.xcx.util.WeChatUtils;

/**
 *	学生实现类
 */
@Service
public class StudentServiceImpl implements StudentService{
    		
	@Autowired
	private StudentRepository student_dao;
	@Autowired
	private SchoolRepository school_dao;
	@Autowired
	private ClassesRepository classes_dao;
	@Autowired
	private RecordRepository record_dao;
	@Autowired
	private ReadStudentExcel readexcel;
	@Autowired
	private ParentStudentRepository parent_student_dao;
	@Autowired
	private ParentRepository parent_dao;
	@Autowired
	private IntegralRepository integral_dao;
	@Autowired
	private ScreeningRepository screening_dao;
	@Autowired
	private ScreeningWearRepository screening_wear_dao;
	

	/* 
	 * 学生列表
	 */
	@Override
	public ResultVO studentList(Map<String, String> params,HttpSession session) {
		String type = "" ; 
		int page = 0 ;
		if(!StringUtils.isEmpty(params.get("page"))) page = Integer.valueOf(params.get("page")) - 1 ;
		if(!StringUtils.isEmpty(params.get("type"))) type = params.get("type");
		if("school".equals(type))return ResultVOUtil.success(student_dao.findBySchoolId(Integer.valueOf(params.get("id")),PageRequest.of(page, 10)));
		if("class".equals(type))return ResultVOUtil.success(student_dao.findByClassesId(Integer.valueOf(params.get("id")),PageRequest.of(page, 10)));
		if("student".equals(type))return ResultVOUtil.success(student_dao.findById(Integer.valueOf(params.get("id")),PageRequest.of(page, 10)));
		
		Integer roleId = Integer.valueOf(session.getAttribute("roleId").toString());
		if(roleId == 5 ) {
			Integer regionId = Integer.valueOf(session.getAttribute("regionId").toString());
			return ResultVOUtil.success(student_dao.findByRegionId(regionId, PageRequest.of(page, 10)));
		}
		return ResultVOUtil.success(student_dao.findAll(PageRequest.of(page, 10)));
	}
	

	/* 
	 * 按照学校班级查学生
	 */
	@Override
	public ResultVO queryBySidCid(Integer cId) {
		return ResultVOUtil.success(student_dao.findByClassesId(cId));
	}

	/* 
	 * 新增学生信息
	 */
	@Override
	public ResultVO addStudent(Map<String, String> params) {
		StudentMapper student = new StudentMapper();
		SchoolMapper school = school_dao.findById(Integer.valueOf(params.get("schoolId"))).get();
		if(!StringUtils.isEmpty(params.get("age")))student.setAge(Integer.valueOf(params.get("age")));
		if(!StringUtils.isEmpty(params.get("chairHeight")))student.setChairHeight(params.get("chairHeight"));
		student.setClassesId(Integer.valueOf(params.get("classId")));
		student.setClassesName(classes_dao.findById(Integer.valueOf(params.get("classId"))).get().getClassName());
		if(!StringUtils.isEmpty(params.get("correct")))student.setCorrect(Integer.valueOf(params.get("correct")));
		student.setGender(Integer.valueOf(params.get("gender")));
		if(!StringUtils.isEmpty(params.get("height")))student.setHeight(params.get("height"));
		student.setName(params.get("name"));
		student.setRegionId(school.getRegionId());
		student.setRegionName(school.getRegionName());
		student.setSchoolId(Integer.valueOf(params.get("schoolId")));
		student.setSchoolName(school.getName());
		if(!StringUtils.isEmpty(params.get("sittingHeight")))student.setSittingHeight(params.get("sittingHeight"));
		if(!StringUtils.isEmpty(params.get("weight")))student.setWeight(params.get("weight"));
		if(!StringUtils.isEmpty(params.get("parentPhone")))student.setParentPhone(params.get("parentPhone"));
		if(!StringUtils.isEmpty(params.get("birthday")))student.setBirthday(params.get("birthday"));
		if(!StringUtils.isEmpty(params.get("nature")))student.setNature(params.get("nature"));
		if(!StringUtils.isEmpty(params.get("description")))student.setDescription(params.get("description"));
		student_dao.save(student);
		return ResultVOUtil.success();
	}

	/* 
	 * 删除学生
	 */
	@Override
	public ResultVO deleteStudent(Integer id,String token) {
		screening_dao.deleteAll(screening_dao.findByStudentIdOrderByGenTimeDesc(id));
		screening_wear_dao.deleteAll(screening_wear_dao.findByStudentIdOrderByGenTimeDesc(id));
		integral_dao.deleteAll(integral_dao.findByStudentIdOrderByGenTimeDesc(id));
		parent_student_dao.deleteAll(parent_student_dao.findByStudentId(id));
		record_dao.deleteByStudentId(id);
		student_dao.deleteById(id);
		return ResultVOUtil.success();
	}

	/* 
	 * 保存修改后信息
	 */
	@Override
	public ResultVO saveStudent(Map<String, String> params) {
		StudentMapper student = student_dao.findById(Integer.valueOf(params.get("id"))).get();
		if(!StringUtils.isEmpty(params.get("age")))student.setAge(Integer.valueOf(params.get("age")));
		if(!StringUtils.isEmpty(params.get("chairHeight")))student.setChairHeight(params.get("chairHeight"));
		if(!StringUtils.isEmpty(params.get("correct")))student.setCorrect(Integer.valueOf(params.get("correct")));
		if(!StringUtils.isEmpty(params.get("gender")))student.setGender(Integer.valueOf(params.get("gender")));
		if(!StringUtils.isEmpty(params.get("height")))student.setHeight(params.get("height"));
		if(!StringUtils.isEmpty(params.get("name")))student.setName(params.get("name"));
		if(!StringUtils.isEmpty(params.get("nature"))) {
			student.setNature(params.get("nature"));
		}else {
			student.setNature("");
		}
		if(!StringUtils.isEmpty(params.get("birthday")))student.setBirthday(params.get("birthday"));
		if(!StringUtils.isEmpty(params.get("sittingHeight")))student.setSittingHeight(params.get("sittingHeight"));
		if(!StringUtils.isEmpty(params.get("weight")))student.setWeight(params.get("weight"));
		if(!StringUtils.isEmpty(params.get("parentPhone")))student.setParentPhone(params.get("parentPhone"));
		if(!StringUtils.isEmpty(params.get("description"))) {
			student.setDescription(params.get("description"));
		}else {
			student.setDescription("");
		}
		if(!StringUtils.isEmpty(params.get("schoolId"))) {
			student.setSchoolId(Integer.valueOf(params.get("schoolId")));
			student.setSchoolName(school_dao.findById(Integer.valueOf(params.get("schoolId"))).get().getName());
		}
		if(!StringUtils.isEmpty(params.get("classId"))) {
			student.setClassesId(Integer.valueOf(params.get("classId")));
			student.setClassesName(classes_dao.findById(Integer.valueOf(params.get("classId"))).get().getClassName());
		}
		student_dao.save(student);
		return ResultVOUtil.success();
	}

	/* 
	 * 按照id查学生
	 */
	@Override
	public ResultVO findById(Integer id) {
		StudentMapper studentMapper = student_dao.findById(id).get();
		StudentVO vo = new StudentVO();
		BeanUtils.copyProperties(studentMapper,vo);
		vo.getStu_cat().add(studentMapper.getSchoolId());
		vo.getStu_cat().add(studentMapper.getClassesId());
		return ResultVOUtil.success(vo);
	}

	/* 
	 * 模糊查询
	 */
	@Override
	public ResultVO findByNameLike(String name) {
		List<StudentMapper> list = student_dao.findByNameLike("%"+name+"%");
		if(list==null||list.size()==0)return ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
		return ResultVOUtil.success(list);
	}

	/* 
	 * 按照学校班级姓名模糊
	 */
	@Override
	public ResultVO findBySchoolIdAndClassesIdAndNameLike(Integer sId, Integer cId, String name) {
		List<StudentMapper> students = student_dao.findBySchoolIdAndClassesIdAndNameLike(sId, cId, "%"+name+"%");
		List<StudentVO> vo = new ArrayList<>();
		for (StudentMapper studentMapper : students) {
			vo.add(new StudentVO(studentMapper.getId(), studentMapper.getName()));
		}
		return ResultVOUtil.success(vo);
	}

	/* 
	 * 批量导入学生数据
	 */
	@Override
	@Transactional
	public ResultVO readStudentExcel(MultipartFile file,String token) {
		List<StudentMapper> studentInfo = readexcel.getStudentInfo(file);
		if(studentInfo==null) {
			System.out.println("读取excel数据返回值是空的");
			return ResultVOUtil.error(ResultEnum.READEXCEL_ERROR.getStatus(), ResultEnum.READEXCEL_ERROR.READEXCEL_ERROR.getMessage());
		}
        for (StudentMapper studentMapper : studentInfo) {
        	student_dao.save(studentMapper);
        }
		return ResultVOUtil.success();
	}

	


	

	
	
}
