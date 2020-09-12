package com.threefriend.schoolclient.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.enums.RecordEnums;
import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.SchoolMapper;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.mapper.StudentWordMapper;
import com.threefriend.lightspace.mapper.xcx.ScreeningMapper;
import com.threefriend.lightspace.mapper.xcx.ScreeningWearMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.IntegralRepository;
import com.threefriend.lightspace.repository.ParentStudentRepository;
import com.threefriend.lightspace.repository.RecordRepository;
import com.threefriend.lightspace.repository.SchoolRepository;
import com.threefriend.lightspace.repository.ScreeningRepository;
import com.threefriend.lightspace.repository.ScreeningWearRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.repository.StudentWordRepository;
import com.threefriend.lightspace.service.Impl.ReadStudentExcel;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.OneStatisticsVO;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.vo.StudentStatisticsVO;
import com.threefriend.lightspace.vo.StudentWordVO;
import com.threefriend.schoolclient.service.SchoolStudentService;

@Service
public class SchoolStudentServiceImpl implements SchoolStudentService{
	@Autowired
	private StudentRepository student_dao;
	@Autowired
	private ClassesRepository class_dao;
	@Autowired
	private SchoolRepository school_dao;
	@Autowired
	private ScreeningRepository screening_dao;
	@Autowired
	private ScreeningWearRepository screening_wear_dao;
	@Autowired
	private IntegralRepository integral_dao;
	@Autowired
	private ParentStudentRepository parent_student_dao;
	@Autowired
	private RecordRepository record_dao;
	@Autowired
	private ReadStudentExcel readexcel;
	@Autowired
	private StudentWordRepository studentword_dao;
	
	

	@Override
	public ResultVO getAllStudent(Map<String, String> params, HttpSession session) {
		Integer schoolId = Integer.valueOf(session.getAttribute("schoolId").toString());
		String type = "" ; 
		int page = 0 ;
		if(!StringUtils.isEmpty(params.get("page"))) page = Integer.valueOf(params.get("page")) - 1 ;
		if(!StringUtils.isEmpty(params.get("type"))) type = params.get("type");
		if("class".equals(type))return ResultVOUtil.success(student_dao.findByClassesId(Integer.valueOf(params.get("id")),PageRequest.of(page, 10)));
		if("student".equals(type))return ResultVOUtil.success(student_dao.findBySchoolIdAndNameLike(schoolId,"%"+params.get("name")+"%",PageRequest.of(page, 10)));
		return ResultVOUtil.success(student_dao.findBySchoolId(schoolId,PageRequest.of(page, 10)));
	}


	@Override
	public ResultVO addStudent(Map<String, String> params) {
		ClassesMapper classpo = class_dao.findById(Integer.valueOf(params.get("classId"))).get();
		SchoolMapper school = school_dao.findById(classpo.getSchoolId()).get();
		StudentMapper student = new StudentMapper();
		student.setClassesId(classpo.getId());
		student.setClassesName(classpo.getClassName());
		student.setGender(Integer.valueOf(params.get("gender")));
		if(!StringUtils.isEmpty(params.get("correct")))student.setCorrect(Integer.valueOf(params.get("correct")));
		if(!StringUtils.isEmpty(params.get("height")))student.setHeight(params.get("height"));
		student.setName(params.get("name"));
		student.setRegionId(school.getRegionId());
		student.setRegionName(school.getRegionName());
		student.setSchoolId(school.getId());
		student.setSchoolName(school.getName());
		if(!StringUtils.isEmpty(params.get("sittingHeight")))student.setSittingHeight(params.get("sittingHeight"));
		if(!StringUtils.isEmpty(params.get("weight")))student.setWeight(params.get("weight"));
		if(!StringUtils.isEmpty(params.get("birthday")))student.setBirthday(params.get("birthday"));
		student_dao.save(student);
		return ResultVOUtil.success();
	}


	@Override
	public ResultVO editStudent(Map<String, String> params) {
		StudentMapper po = student_dao.findById(Integer.valueOf(params.get("id"))).get();
		return ResultVOUtil.success(po);
	}


	@Override
	public ResultVO saveStudent(Map<String, String> params) {
		StudentMapper student = student_dao.findById(Integer.valueOf(params.get("id"))).get();
		if(!StringUtils.isEmpty(params.get("classId"))) {
			ClassesMapper classpo = class_dao.findById(Integer.valueOf(params.get("classId"))).get();
			student.setClassesId(classpo.getId());
			student.setClassesName(classpo.getClassName());
		}
		if(!StringUtils.isEmpty(params.get("gender")))student.setGender(Integer.valueOf(params.get("gender")));
		if(!StringUtils.isEmpty(params.get("correct")))student.setCorrect(Integer.valueOf(params.get("correct")));
		if(!StringUtils.isEmpty(params.get("height")))student.setHeight(params.get("height"));
		if(!StringUtils.isEmpty(params.get("name")))student.setName(params.get("name"));
		if(!StringUtils.isEmpty(params.get("sittingHeight")))student.setSittingHeight(params.get("sittingHeight"));
		if(!StringUtils.isEmpty(params.get("weight")))student.setWeight(params.get("weight"));
		if(!StringUtils.isEmpty(params.get("birthday")))student.setBirthday(params.get("birthday"));
		student_dao.save(student);
		return ResultVOUtil.success();
	}


	@Override
	public ResultVO deleteStudent(Map<String, String> params) {
		Integer id = Integer.valueOf(params.get("id"));
		screening_dao.deleteAll(screening_dao.findByStudentIdOrderByGenTimeDesc(id));
		screening_wear_dao.deleteAll(screening_wear_dao.findByStudentIdOrderByGenTimeDesc(id));
		integral_dao.deleteAll(integral_dao.findByStudentIdOrderByGenTimeDesc(id));
		parent_student_dao.deleteAll(parent_student_dao.findByStudentId(id));
		record_dao.deleteByStudentId(id);
		student_dao.deleteById(id);
		return ResultVOUtil.success();
	}
	
	/* 
	 * 批量导入学生数据
	 */
	@Override
	@Transactional
	public ResultVO readStudentExcel(MultipartFile file) {
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


	@Override
	public ResultVO getAllWord(Map<String, String> params, HttpSession session) {
		Integer schoolId = 50;
		Integer page = 0;
		String type = "";
		if(!StringUtils.isEmpty(params.get("type")))type = params.get("type");
		if(!StringUtils.isEmpty(params.get("page")))page = Integer.valueOf(params.get("page"))-1;
		Page<StudentWordMapper> list =null;
		List<StudentWordVO> endList = new ArrayList<StudentWordVO>();
		if("class".equals(type)) {
			list = studentword_dao.findByClassIdOrderById(Integer.valueOf(params.get("id")), PageRequest.of(page, 10));
		}else if("student".equals(type)){
			if(StringUtils.isEmpty(params.get("id"))) {
				list = studentword_dao.findByNameLikeOrderById("%"+params.get("name")+"%", PageRequest.of(page, 10));
			}else {
				list = studentword_dao.findByClassIdAndNameLikeOrderById(Integer.valueOf(params.get("id")),"%"+params.get("name")+"%", PageRequest.of(page, 10));
			}
		}else {
			list = studentword_dao.findBySchoolIdOrderById(schoolId, PageRequest.of(page, 10));
		}
		
		for (StudentWordMapper po : list.getContent()) {
			StudentWordVO vo = new StudentWordVO();
			vo.setId(po.getStudentId());
			vo.setClassName(po.getClassName());
			vo.setSchoolName(po.getSchoolName());
			vo.setGender(po.getGender());
			vo.setName(po.getName());
			vo.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(po.getGenTime()));
			endList.add(vo);
		}
		
		Page<StudentWordVO> end = new PageImpl<>(endList, list.getPageable(), list.getTotalElements());
		return ResultVOUtil.success(end);
	}
	
	
	/*
	 * 按照学生id查询所有的数据
	 * 
	 */
	@Override
	public ResultVO findAllByStudentId(Map<String, String> params) {
		Date after= new Date();
		Date befor= new Date(after.getTime()-(604800*1000));
		Integer id = Integer.valueOf(params.get("id"));
		DateFormat format =new SimpleDateFormat("yyyy/MM/dd");
		StudentStatisticsVO end = new StudentStatisticsVO();
		
		OneStatisticsVO visionLeft=new OneStatisticsVO();
		OneStatisticsVO visionRight=new OneStatisticsVO();
		
		Integer screeningType = student_dao.findById(id).get().getScreeningType();
		//这里注释是因为个人概况不能好好的展示  改用日常检测的数据展示
		//List<RecordMapper> all = record_dao.findAllByStudentIdAndGenTimeBetweenOrderByGenTime(id,befor,after);
		if(screeningType==1) {
			List<ScreeningMapper> screening = screening_dao.findByStudentIdAndGenTimeBetweenOrderById(id, befor, after);
			for (ScreeningMapper po : screening) {
				String time =format.format(po.getGenTime());
				if(visionLeft.getName()==null||visionLeft.getName()=="") {
					visionLeft.setName(RecordEnums.VISIONLEFT.getName());
				}
				visionLeft.getyDataList().add(po.getVisionLeftStr());
				visionLeft.getxDataList().add(time);
				
				if(visionRight.getName()==null||visionRight.getName()=="") {
					visionRight.setName(RecordEnums.VISIONRIGHT.getName());
				}
				visionRight.getyDataList().add(po.getVisionRightStr());
				visionRight.getxDataList().add(time);
			}
		}else {
			List<ScreeningWearMapper> screeningwear = screening_wear_dao.findByStudentIdAndGenTimeBetweenOrderById(id, befor, after);
			
			for (ScreeningWearMapper po : screeningwear) {
				String time =format.format(po.getGenTime());
				if(visionLeft.getName()==null||visionLeft.getName()=="") {
					visionLeft.setName(RecordEnums.VISIONLEFT.getName());
				}
				visionLeft.getyDataList().add(po.getVisionLeftStr());
				visionLeft.getxDataList().add(time);
				
				if(visionRight.getName()==null||visionRight.getName()=="") {
					visionRight.setName(RecordEnums.VISIONRIGHT.getName());
				}
				visionRight.getyDataList().add(po.getVisionRightStr());
				visionRight.getxDataList().add(time);
			}
		}
		
		
		end.setVisionLeft(visionLeft);
		end.setVisionRight(visionRight);
		end.setStudnetWord(studentword_dao.findTopByStudentIdOrderByGenTimeDesc(id));
		return ResultVOUtil.success(end);
	}


	@Override
	public ResultVO getStudentByClass(Map<String, String> params) {
		Integer classId = Integer.valueOf(params.get("id"));
		List<StudentMapper> allStudent = student_dao.findByClassesId(classId);
		return ResultVOUtil.success(allStudent);
	}
}
