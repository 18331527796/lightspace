package com.threefriend.schoolclient.service.impl;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.enums.SemesterEnum;
import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.SchoolMapper;
import com.threefriend.lightspace.mapper.SortMapper;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.mapper.TeacherMapper;
import com.threefriend.lightspace.mapper.UserMapper;
import com.threefriend.lightspace.mapper.schoolclient.SchoolClassesMapper;
import com.threefriend.lightspace.mapper.schoolclient.SchoolRightMapper;
import com.threefriend.lightspace.mapper.schoolclient.SchoolRoleRightRelation;
import com.threefriend.lightspace.mapper.schoolclient.SchoolSemesterMapper;
import com.threefriend.lightspace.mapper.schoolclient.SchoolStudentRecordMapper;
import com.threefriend.lightspace.mapper.schoolclient.UserSchoolsMapper;
import com.threefriend.lightspace.mapper.xcx.ScreeningMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.SchoolRepository;
import com.threefriend.lightspace.repository.ScreeningRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.repository.TeacherRepository;
import com.threefriend.lightspace.repository.UserRepository;
import com.threefriend.lightspace.repository.UserRoleRepository;
import com.threefriend.lightspace.repository.schoolclient.SchoolClassRepository;
import com.threefriend.lightspace.repository.schoolclient.SchoolRightRepository;
import com.threefriend.lightspace.repository.schoolclient.SchoolRoleRightRepository;
import com.threefriend.lightspace.repository.schoolclient.SchoolSemesterRepository;
import com.threefriend.lightspace.repository.schoolclient.SchoolStudentRecordRepository;
import com.threefriend.lightspace.repository.schoolclient.UserSchoolsRepository;
import com.threefriend.lightspace.util.ListUtils;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.vo.SchoolMenuListVo;
import com.threefriend.lightspace.vo.Top5VO;
import com.threefriend.lightspace.vo.UndetectedVO;
import com.threefriend.lightspace.xcx.util.SendMessageUtils;
import com.threefriend.schoolclient.service.SchoolUserService;

@Service
public class SchoolUserServiceImpl implements SchoolUserService{
	
	@Autowired
	private UserRepository user_dao;
	@Autowired
	private UserRoleRepository user_role_dao;
	@Autowired
	private StudentRepository student_dao;
	@Autowired
	private ClassesRepository class_dao;
	@Autowired
	private SchoolRepository school_dao;
	@Autowired
	private SchoolClassRepository school_class_dao;
	@Autowired
	private SchoolSemesterRepository school_semester_dao;
	@Autowired
	private SchoolStudentRecordRepository school_student_record_dao;
	@Autowired
	private TeacherRepository teacher_dao;
	@Autowired
	private UserSchoolsRepository u_s_dao;
	@Autowired
	private ScreeningRepository screening_dao;
	
	
	@Override
	public ResultVO login(Map<String, String> params,HttpSession session) {
		
		// 比如对密码进行 md5 加密
		String md5 = DigestUtils.md5DigestAsHex(params.get("password").getBytes());
		List<UserMapper> user = user_dao.findByloginNameAndPassword(params.get("loginName"), md5);
		List<TeacherMapper> teacher = teacher_dao.findByPhoneAndPassword(params.get("loginName"), params.get("password"));
		if(user==null&&teacher==null&&user.size()!=1&&teacher.size()==0)return ResultVOUtil.error(ResultEnum.LOGIN_FAIL.getStatus(), ResultEnum.LOGIN_FAIL.getMessage());
		Integer roleId = 0;
		if(1==user.size()) {
			roleId = user_role_dao.findByUserId(user.get(0).getId()).get(0).getRoleId();
		}else {
			roleId = 3;
			Integer finish = class_dao.findById(teacher.get(0).getClassId()).get().getFinish();
			if(finish!=null&&finish==1)return ResultVOUtil.error(ResultEnum.TEACHER_CLASS_ERROR.getStatus(), ResultEnum.TEACHER_CLASS_ERROR.getMessage());
		}
		//如果不是校园管理 班级管理 不让进
		if(roleId!=2&&roleId!=3)return ResultVOUtil.error(ResultEnum.LOGIN_FAIL.getStatus(), ResultEnum.LOGIN_FAIL.getMessage());
		Map<String , String> end = new HashMap<>();
		SchoolMapper school = null;
		List<Integer> schoolIds = new ArrayList<>();
		 if(roleId == 3 && (user==null||0 ==user.size())){
			 school=school_dao.findById(teacher.get(0).getSchoolId()).get();
			 end.put("classId", teacher.get(0).getClassId()+"");
			 end.put("className", class_dao.findById(teacher.get(0).getClassId()).get().getClassName());
			 end.put("userName", teacher.get(0).getName());
			 session.setAttribute("classId",teacher.get(0).getClassId());
		 }else {
			 school=school_dao.findById(user.get(0).getSchoolId()).get();
			 end.put("userName", user.get(0).getName());
			 session.setAttribute("userId",user.get(0).getId());
			 List<UserSchoolsMapper> u_s_list = u_s_dao.findByUserId(user.get(0).getId());
			 if(u_s_list.size()>1) {
				 roleId = 0 ;
				 for (UserSchoolsMapper userSchoolsMapper : u_s_list) {
					 schoolIds.add(userSchoolsMapper.getSchoolId());
				}
			 }
		 }
		end.put("schoolName", school.getName());
		end.put("schoolId", school.getId()+"");
		end.put("roleId", roleId+"");
		
		session.setAttribute("roleId",roleId);
		session.setAttribute("schoolId",school.getId()+"");
		
		
		if(schoolIds.size()==0) {
			schoolIds.add(school.getId());
		}
		new Thread(
			    new Runnable(){
					public void run(){
						for (Integer integer : schoolIds) {
							initialize(integer);
						}
			        }
			    }
			).start();
		
		return ResultVOUtil.success(end);
	}
	
	@Override
	public ResultVO regionLogin(Map<String, String> params, HttpSession session) {
		// 比如对密码进行 md5 加密
		String md5 = DigestUtils.md5DigestAsHex(params.get("password").getBytes());
		List<UserMapper> user = user_dao.findByloginNameAndPassword(params.get("loginName"), md5);
		if(user==null&&user.size()!=1)return ResultVOUtil.error(ResultEnum.LOGIN_FAIL.getStatus(), ResultEnum.LOGIN_FAIL.getMessage());
		
		Integer roleId = user_role_dao.findByUserId(user.get(0).getId()).get(0).getRoleId();
		Integer regionId = user.get(0).getRegionId();
		//如果不是合作商 不让进
		if(roleId!=4)return ResultVOUtil.error(ResultEnum.LOGIN_FAIL.getStatus(), ResultEnum.LOGIN_FAIL.getMessage());
		Map<String , String> end = new HashMap<>();
		end.put("userName", user.get(0).getName());
		end.put("schoolName", user.get(0).getRegionName());
		end.put("schoolId", "0");
		
		session.setAttribute("userId",user.get(0).getId());
		session.setAttribute("roleId",roleId);
		session.setAttribute("schoolId","0");
		session.setAttribute("regionId", regionId);
		
		List<Integer> schoolIds = school_dao.findIdByRegionId(regionId);
		new Thread(
			    new Runnable(){
					public void run(){
						for (Integer integer : schoolIds) {
							initialize(integer);
						}
			        }
			    }
			).start();
		
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO survey(Map<String, String> params ,HttpSession session) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date begintime = null,endtime = null;
		System.out.println(params.get("begin")+"+++++"+params.get("end"));
		if(!StringUtils.isEmpty(params.get("begin"))) {
			try {
				begintime = sdf.parse(params.get("begin"));
				endtime = sdf.parse(params.get("end"));
			} catch (ParseException e) {
				System.out.println("时间解析出错");
			}
		}
		Integer schoolId = Integer.valueOf(session.getAttribute("schoolId").toString());
		List<Integer> schoolIds = new ArrayList<>();
		if(schoolId==0) {
			Integer regionId = Integer.valueOf(session.getAttribute("regionId").toString());
			schoolIds=school_dao.findIdByRegionId(regionId);
		}else if(schoolId == -1){
			Integer userId = Integer.valueOf(session.getAttribute("userId").toString());
			List<UserSchoolsMapper> u_s_list = u_s_dao.findByUserId(userId);
			for (UserSchoolsMapper userSchoolsMapper : u_s_list) {
				schoolIds.add(userSchoolsMapper.getSchoolId());
			}
		}else {
			schoolIds.add(schoolId);
		}
		Map<String , String> end = new HashMap<>();
		DecimalFormat df=new DecimalFormat("0.00");
		int bad= 0,nullstudent=0;
		Double LEFT = 0d , RIGHT = 0d, AVG = 0d;
		
		//所有学生数量
		int allstudent = student_dao.countBySchoolIdIn(schoolIds);
		List<StudentMapper> students = student_dao.findBySchoolIdIn(schoolIds);
		for (StudentMapper s : students) {
			if(s.getLastTime()==null||s.getSendTime()==null||s.getVisionLeftStr()==null||s.getVisionRightStr()==null) {
				nullstudent++;
				continue;
			}
			
			if(begintime!=null) {
				Calendar date = Calendar.getInstance();
				date.setTime(s.getSendTime());
		 
				Calendar beginTime = Calendar.getInstance();
				beginTime.setTime(begintime);
		 
				Calendar endTime = Calendar.getInstance();
				endTime.setTime(endtime);
		 
				if (!(date.after(beginTime) && date.before(endTime))) {
					nullstudent++;
					continue;
				}
			}
			
			LEFT = s.getVisionLeftStr();
			RIGHT = s.getVisionRightStr();
			
			
			if(LEFT>=RIGHT) {
				AVG = RIGHT;
			}else {
				AVG = LEFT;
			}
			
			if(s.getScreeningType()==1) {
				if(AVG<1.0)bad++;
			}else {
				bad++;
			}
		}
		
		end.put("inspect", allstudent+"");
		end.put("actual", (allstudent-nullstudent)+"");
		end.put("bad", bad+"");
		end.put("badPercentage", df.format(((float)bad/((float)(allstudent-nullstudent)))*100));
		end.put("notPercentage", df.format(((float)nullstudent/(float)allstudent)*100));
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO badPercentage(HttpSession session) {
		Integer schoolId = Integer.valueOf(session.getAttribute("schoolId").toString());
		List<Integer> schoolIds = new ArrayList<>();
		if(schoolId==0) {
			Integer regionId = Integer.valueOf(session.getAttribute("regionId").toString());
			schoolIds=school_dao.findIdByRegionId(regionId);
		}else if(schoolId == -1){
			Integer userId = Integer.valueOf(session.getAttribute("userId").toString());
			List<UserSchoolsMapper> u_s_list = u_s_dao.findByUserId(userId);
			for (UserSchoolsMapper userSchoolsMapper : u_s_list) {
				schoolIds.add(userSchoolsMapper.getSchoolId());
			}
		}else {
			schoolIds.add(schoolId);
		}
		DecimalFormat df=new DecimalFormat("0.00");
		
		List<String> end =new  ArrayList<String>();
		
		List<Integer> allclass = null;
		List<StudentMapper> students = null;
		
		for (int i = 1; i < 7; i++) {
			int bad= 0,nullstudent=0;
			Double LEFT = 0d , RIGHT = 0d, AVG = 0d;
			allclass = class_dao.findIdBySchoolIdAndGrade(schoolIds,i);
			
			if(allclass.size()<1||allclass==null) {
				end.add("0");
				continue;
			}
			//所有学生数量
			int allstudent = student_dao.countByClassesId(allclass);
			students = student_dao.findByClassesId(allclass);
			for (StudentMapper s : students) {
				if(s.getLastTime()==null||s.getVisionLeftStr()==null||s.getVisionRightStr()==null) {
					nullstudent++;
					continue;
				}
				
				LEFT = s.getVisionLeftStr();
				RIGHT = s.getVisionRightStr();
				
				if(LEFT>=RIGHT) {
					AVG = RIGHT;
				}else {
					AVG = LEFT;
				}
				
				if(s.getScreeningType()==1) {
					if(AVG<1.0)bad++;
				}else {
					bad++;
				}
			}
			
			end.add(df.format(((float)bad/((float)(allstudent-nullstudent)))*100));
		}
		return ResultVOUtil.success(end);
	}
	
	public String reaGrade(Integer grade) {
		String end = "";
		switch (grade) {
		case 1:end="一年级";
			break;
		case 2:end="二年级";
			break;
		case 3:end="三年级";
			break;
		case 4:end="四年级";
			break;
		case 5:end="五年级";
			break;
		case 6:end="六年级";
			break;
		}
		return end;
	}

	@Override
	public ResultVO visionGrade(HttpSession session) {
		
		Integer schoolId = Integer.valueOf(session.getAttribute("schoolId").toString());
		
		List<Integer> schoolIds = new ArrayList<>();
		if(schoolId==0) {
			Integer regionId = Integer.valueOf(session.getAttribute("regionId").toString());
			schoolIds=school_dao.findIdByRegionId(regionId);
		}else if(schoolId == -1){
			Integer userId = Integer.valueOf(session.getAttribute("userId").toString());
			List<UserSchoolsMapper> u_s_list = u_s_dao.findByUserId(userId);
			for (UserSchoolsMapper userSchoolsMapper : u_s_list) {
				schoolIds.add(userSchoolsMapper.getSchoolId());
			}
		}else {
			schoolIds.add(schoolId);
		}
		
		DecimalFormat df=new DecimalFormat("0.00");
		
		List<Integer> allclass = null;
		List<StudentMapper> students = null;
		
		
		List<List<String>> end = new ArrayList<>();
		List<String> goodList = new ArrayList<>();
		List<String> mildList = new ArrayList<>();
		List<String> moderateList = new ArrayList<>();
		List<String> seriousList = new ArrayList<>();
		
		for (int i = 1; i < 7; i++) {
			
			int avgGood= 0,avgMild= 0,avgModerate= 0,avgSerious= 0,nullstudent=0;
			Double LEFT = 0d , RIGHT = 0d , AVG = 0d;
			
			allclass = class_dao.findIdBySchoolIdAndGrade(schoolIds,i);
			
			if(allclass.size()<1||allclass==null) {
				goodList.add("0");
				mildList.add("0");
				moderateList.add("0");
				seriousList.add("0");
				continue;
			}
			//所有学生数量
			int allstudent = student_dao.countByClassesId(allclass);
			students = student_dao.findByClassesId(allclass);
			
			for (StudentMapper s : students) {
				if(s.getLastTime()==null||s.getVisionLeftStr()==null||s.getVisionRightStr()==null) {
					nullstudent++;
					continue;
				}
				
				LEFT = s.getVisionLeftStr();
				RIGHT = s.getVisionRightStr();
				if(LEFT <= RIGHT) {
					AVG = LEFT;
				}else {
					AVG = RIGHT;
				}
				
				if(AVG>=1.0) {
					avgGood++;
				}else if(AVG >=0.6 && AVG <1.0 ) {
					avgMild++;
				}else if(AVG >=0.1 && AVG <0.4){
					avgModerate++;
				}else {
					avgSerious++;
				}
			}
			
			goodList.add(df.format(((float)avgGood/((float)(allstudent-nullstudent)))*100));
			mildList.add(df.format(((float)avgMild/((float)(allstudent-nullstudent)))*100));
			moderateList.add(df.format(((float)avgModerate/((float)(allstudent-nullstudent)))*100));
			seriousList.add(df.format(((float)avgSerious/((float)(allstudent-nullstudent)))*100));
		}
		
		end.add(goodList);
		end.add(mildList);
		end.add(moderateList);
		end.add(seriousList);
		
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO brokenLine(HttpSession session) {
		Integer schoolId = Integer.valueOf(session.getAttribute("schoolId").toString());
		
		List<Integer> schoolIds = new ArrayList<>();
		if(schoolId==0) {
			Integer regionId = Integer.valueOf(session.getAttribute("regionId").toString());
			schoolIds=school_dao.findIdByRegionId(regionId);
		}else if(schoolId == -1){
			Integer userId = Integer.valueOf(session.getAttribute("userId").toString());
			List<UserSchoolsMapper> u_s_list = u_s_dao.findByUserId(userId);
			for (UserSchoolsMapper userSchoolsMapper : u_s_list) {
				schoolIds.add(userSchoolsMapper.getSchoolId());
			}
		}else {
			schoolIds.add(schoolId);
		}
		
		DecimalFormat df=new DecimalFormat("0.00");
		List<String> end = new ArrayList<>();
		
		List<Integer> allclass = null;
		List<StudentMapper> students = null;
		
		for (int i = 1; i < 7; i++) {
			int bad= 0,nullstudent=0;
			Double LEFT = 0d , RIGHT = 0d, AVG = 0d , sum = 0d;
			allclass = class_dao.findIdBySchoolIdAndGrade(schoolIds,i);
			
			if(allclass.size()<1||allclass==null) {
				end.add("0");
				continue;
			}
			//所有学生数量
			int allstudent = student_dao.countByClassesId(allclass);
			students = student_dao.findByClassesId(allclass);
			for (StudentMapper s : students) {
				if(s.getLastTime()==null||s.getVisionLeftStr()==null||s.getVisionRightStr()==null) {
					nullstudent++;
					continue;
				}
				
				LEFT = s.getVisionLeftStr();
				RIGHT = s.getVisionRightStr();
				
				if(LEFT>=RIGHT) {
					AVG = RIGHT;
				}else {
					AVG = LEFT;
				}
				sum+=AVG;
			}
			
			end.add(df.format(sum/(allstudent-nullstudent)));
		}
		
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO top5(Map<String, String> params,HttpSession session) {
		Integer schoolId = Integer.valueOf(session.getAttribute("schoolId").toString());
		List<Integer> schoolIds = new ArrayList<>();
		if(schoolId==0) {
			Integer regionId = Integer.valueOf(session.getAttribute("regionId").toString());
			schoolIds=school_dao.findIdByRegionId(regionId);
		}else if(schoolId == -1){
			Integer userId = Integer.valueOf(session.getAttribute("userId").toString());
			List<UserSchoolsMapper> u_s_list = u_s_dao.findByUserId(userId);
			for (UserSchoolsMapper userSchoolsMapper : u_s_list) {
				schoolIds.add(userSchoolsMapper.getSchoolId());
			}
		}else {
			schoolIds.add(schoolId);
		}
		
		String type = "";
		if(!StringUtils.isEmpty(params.get("type")))type = params.get("type");
		
		DecimalFormat df=new DecimalFormat("0.00");
		
		List<Top5VO> end = new ArrayList<>();
		List<Integer> grades = new ArrayList<>();
		for (int i = 1; i < 7; i++) {
			grades.add(i);
		}
		List<ClassesMapper> allclass = class_dao.findBySchoolIdInAndGradeIn(schoolIds,grades);
		
		List<StudentMapper> students = null;
		int allstudent = 0 , bad ;
		Double avg = 0d;
		
		for (ClassesMapper po : allclass) {
			bad = 0;
			allstudent=0;
			students = student_dao.findByClassesId(po.getId());
			Top5VO vo = new Top5VO();
			vo.setName(po.getSchoolName()+"   :"+po.getClassName());
			for (StudentMapper s : students) {
				if(s.getLastTime()==null||s.getVisionLeftStr()==null||s.getVisionRightStr()==null)continue;
				if(s.getVisionLeftStr()<=s.getVisionRightStr()) {
					avg = s.getVisionLeftStr();
				}else {
					avg = s.getVisionRightStr();
				}
				allstudent++;
				if(avg<1.0)bad++;
			}
			vo.setBad(bad);
			vo.setPercentage(df.format((((float)bad/(float)allstudent)*100)));
			vo.setPercentageSort(Integer.valueOf((int) (((float)bad/(float)allstudent)*100)));
			end.add(vo);
		}
		ListUtils.sort(end, false,"percentageSort");
		List<Top5VO> subList =null;
		if(end.size()>=5) {
			subList= end.subList(0, 5);
		}else {
			subList = end ;
		}
		if("all".equals(type)) return ResultVOUtil.success(end);
		return ResultVOUtil.success(subList);
	}

	@Override
	public ResultVO getAllClass(HttpSession session) {
		Integer schoolId = Integer.valueOf(session.getAttribute("schoolId").toString());
		List<Integer> grades = new ArrayList<>();
		for (int i = 1; i < 7; i++) {
			grades.add(i);
		}
		List<ClassesMapper> allclass = class_dao.findBySchoolIdAndGradeIn(schoolId,grades);
		return ResultVOUtil.success(allclass);
	}
	
	@Override
	public ResultVO getAllSemester(HttpSession session) {
		return ResultVOUtil.success(school_semester_dao.findBySchool());
	}
	
	
	
	@Override
	public void initialize(Integer schoolId) {
		System.out.println("线程开启");
		//计算当前的学年学期
        Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR),termInt = 0;
		int month = cal.get(Calendar.MONTH )+1;
		String term = SemesterEnum.ONESEMESTER.getMessage(),yearStr = "";
		if(month < 9)
			year = year - 1;
		if (month>=7 && month<9) {
			term = SemesterEnum.SUMMER.getMessage();
			termInt=SemesterEnum.SUMMER.getCode();
		}else if(month>=9 && month<=12){
			term = SemesterEnum.ONESEMESTER.getMessage();
			termInt=SemesterEnum.ONESEMESTER.getCode();
		}else if(month>=1 && month<3) {
			term = SemesterEnum.WINTER.getMessage();
			termInt=SemesterEnum.WINTER.getCode();
		}else if(month>=3 && month<7){
			term = SemesterEnum.TOWSEMESTER.getMessage();
			termInt= SemesterEnum.TOWSEMESTER.getCode();
		}
		yearStr = year + " - " + (year + 1) ;
		System.out.println("现在是：" + year + " - " + (year + 1) +  "学年，" + term + "。");

		SchoolSemesterMapper SemesterPo = school_semester_dao.findByYearAndSemesterAndSchoolId(yearStr,termInt,schoolId);
		
		if(SemesterPo!=null ) {
			
			//System.out.println(new Date().getTime()+"---"+SemesterPo.getGenTime().getTime()+"---"+(new Date().getTime()-SemesterPo.getGenTime().getTime()));
			//if((new Date().getTime()-SemesterPo.getGenTime().getTime())>604800000) {
				school_class_dao.deleteBySchoolIdAndSemesterId(schoolId,SemesterPo.getId());
				school_student_record_dao.deleteBySchoolIdAndSemester(schoolId,SemesterPo.getId());
				SemesterPo.setGenTime(new Date());
				school_semester_dao.save(SemesterPo);
				System.out.println("进行重置");
			//}else {
			//	System.out.println("时间不够，跳出方法");
			//	return ; 
			//}
		}else {
			System.out.println("新学期创建");
			SemesterPo = new SchoolSemesterMapper();
			SemesterPo.setGenTime(new Date());
			SemesterPo.setSchoolId(schoolId);
			SemesterPo.setSemester(termInt);
			SemesterPo.setYear(yearStr);
			school_semester_dao.save(SemesterPo);
		}
		
		System.out.println("向下进行");
		List<Integer> classIds = new ArrayList<>();
		List<ClassesMapper> classes = class_dao.findBySchoolId(schoolId);
		for (ClassesMapper po : classes) {
			if(po.getGrade()<1&&po.getGrade()>6)continue;
			SchoolClassesMapper newClass = new SchoolClassesMapper();
			newClass.setClassId(po.getId());
			newClass.setGrade(po.getGrade());
			newClass.setName(po.getClassName());
			newClass.setSchoolName(po.getSchoolName());
			newClass.setSchoolId(po.getSchoolId());
			newClass.setSemesterId(SemesterPo.getId());
			newClass.setClassNumber(po.getClassNumber());
			school_class_dao.save(newClass);
			classIds.add(po.getId());
		}
		
		List<StudentMapper> allStudent = student_dao.findByClassesId(classIds);
		List<SchoolStudentRecordMapper> saveRecord = new ArrayList<>();
		for (StudentMapper po : allStudent) {
			ScreeningMapper screening = screening_dao.findTopByStudentIdOrderByGenTimeDesc(po.getId());
			if(screening==null)continue;
			SchoolStudentRecordMapper record = new SchoolStudentRecordMapper();
			record.setName(po.getName());
			record.setClassId(po.getClassesId());
			record.setGender(po.getGender());
			record.setSchoolId(schoolId);
			record.setVisionLeftStr(screening.getVisionLeftStr());
			record.setVisionRightStr(screening.getVisionLeftStr());
			record.setSemester(SemesterPo.getId());
			saveRecord.add(record);
		}
		school_student_record_dao.saveAll(saveRecord);
		
	}

	@Override
	public ResultVO undetectedList(Map<String, String> params ,HttpSession session) {
		List<UndetectedVO> end = new ArrayList<>();
		List<UndetectedVO> classList = null;
		List<StudentMapper> studentList = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date begintime = null,endtime = null;
		Calendar date = Calendar.getInstance();
		Calendar beginTime = Calendar.getInstance();
		Calendar endTime = Calendar.getInstance();
		System.out.println(params.get("begin")+"+++++"+params.get("end"));
		if(!StringUtils.isEmpty(params.get("begin"))) {
			try {
				begintime = sdf.parse(params.get("begin"));
				endtime = sdf.parse(params.get("end"));
			} catch (ParseException e) {
				System.out.println("时间解析出错");
			}
		}
		Integer schoolId = Integer.valueOf(session.getAttribute("schoolId").toString());
		int gredeNum = 0,classNum = 0;
		for (int i = 1; i < 7; i++) {
			UndetectedVO gredevo = new UndetectedVO();
			classList = new ArrayList<>();
			gredeNum = 0;
			gredevo.setName(i+"年级");
			List<ClassesMapper> classIds = class_dao.findBySchoolIdAndGradeOrderByClassNumber(schoolId, i);
			for (ClassesMapper classPo : classIds) {
				UndetectedVO classvo = new UndetectedVO();
				studentList = new ArrayList<>();
				classNum = 0;
				List<StudentMapper> students = student_dao.findByClassesId(classPo.getId());
				for (StudentMapper s : students) {
					if(begintime==null) {
						if(s.getLastTime()==null) {
							studentList.add(s);
							classNum++;
							gredeNum++;
						}
					}else {
						if(s.getLastTime()==null) {
							studentList.add(s);
							classNum++;
							gredeNum++;
						}else {
							date.setTime(s.getSendTime());
							beginTime.setTime(begintime);
							endTime.setTime(endtime);
							if(!(date.after(beginTime) && date.before(endTime))) {
								studentList.add(s);
								classNum++;
								gredeNum++;
							}
						}
					}
				}
				classvo.setName(classPo.getClassName());
				classvo.setUndetectedNumber(classNum);
				classvo.setStudentData(studentList);
				classList.add(classvo);
			}
			gredevo.setClassData(classList);
			gredevo.setUndetectedNumber(gredeNum);
			end.add(gredevo);
		}
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO badList(Map<String, String> params ,HttpSession session) {
		List<UndetectedVO> end = new ArrayList<>();
		List<UndetectedVO> classList = null;
		List<StudentMapper> studentList = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date begintime = null,endtime = null;
		Double LEFT,RIGHT,AVG;
		Calendar date = Calendar.getInstance();
		Calendar beginTime = Calendar.getInstance();
		Calendar endTime = Calendar.getInstance();
		System.out.println(params.get("begin")+"+++++"+params.get("end"));
		if(!StringUtils.isEmpty(params.get("begin"))) {
			try {
				begintime = sdf.parse(params.get("begin"));
				endtime = sdf.parse(params.get("end"));
			} catch (ParseException e) {
				System.out.println("时间解析出错");
			}
		}
		Integer schoolId = Integer.valueOf(session.getAttribute("schoolId").toString());
		int gredeNum = 0,classNum = 0;
		for (int i = 1; i < 7; i++) {
			UndetectedVO gredevo = new UndetectedVO();
			classList = new ArrayList<>();
			gredeNum = 0;
			gredevo.setName(i+"年级");
			List<ClassesMapper> classIds = class_dao.findBySchoolIdAndGradeOrderByClassNumber(schoolId, i);
			for (ClassesMapper classPo : classIds) {
				UndetectedVO classvo = new UndetectedVO();
				studentList = new ArrayList<>();
				classNum = 0;
				List<StudentMapper> students = student_dao.findByClassesId(classPo.getId());
				for (StudentMapper s : students) {
					if(s.getLastTime()==null) continue;
					if(begintime==null) {
						LEFT = s.getVisionLeftStr();
						RIGHT = s.getVisionRightStr();
						if(LEFT>=RIGHT) {
							AVG = RIGHT;
						}else {
							AVG = LEFT;
						}
						if(s.getScreeningType()==1) {
							if(AVG<1.0) {
								studentList.add(s);
								classNum++;
								gredeNum++;
							}
						}else {
							studentList.add(s);
							classNum++;
							gredeNum++;
						}
						
					}else {
						date.setTime(s.getSendTime());
						beginTime.setTime(begintime);
						endTime.setTime(endtime);
						if(!(date.after(beginTime) && date.before(endTime))) {
							LEFT = s.getVisionLeftStr();
							RIGHT = s.getVisionRightStr();
							if(LEFT>=RIGHT) {
								AVG = RIGHT;
							}else {
								AVG = LEFT;
							}
							if(s.getScreeningType()==1) {
								if(AVG<1.0) {
									studentList.add(s);
									classNum++;
									gredeNum++;
								}
							}else {
								studentList.add(s);
								classNum++;
								gredeNum++;
							}
						}
					}
				}
				classvo.setName(classPo.getClassName());
				classvo.setUndetectedNumber(classNum);
				classvo.setStudentData(studentList);
				classList.add(classvo);
			}
			gredevo.setClassData(classList);
			gredevo.setUndetectedNumber(gredeNum);
			end.add(gredevo);
		}
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO loginOut(HttpSession session) {
		session.invalidate();
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO changeSession(Map<String, String> params, HttpSession session) {
		Map<String, String> end = new HashMap<>();
		Integer schoolId = Integer.valueOf(params.get("schoolId"));
		session.setAttribute("schoolId", schoolId);
		if(schoolId==0) {
			Integer userId = Integer.valueOf(session.getAttribute("userId").toString());
			end.put("schoolName", user_dao.findById(userId).get().getRegionName());
			end.put("schoolId", "0");
		}else if(schoolId==-1) {
			end.put("schoolName", "集团总计");
			end.put("schoolId", "-1");
		}else {
			SchoolMapper school = school_dao.findById(schoolId).get();
			end.put("schoolName", school.getName());
			end.put("schoolId", school.getId()+"");
		}
		return ResultVOUtil.success(end);
	}


	@Override
	public ResultVO getUserSchools(HttpSession session) {
		Integer userId = Integer.valueOf(session.getAttribute("userId").toString());
		List<SchoolMapper> end = new ArrayList<>();
		List<UserSchoolsMapper> userSchool = u_s_dao.findByUserId(userId);
		for (UserSchoolsMapper po : userSchool) {
			end.add(school_dao.findById(po.getSchoolId()).get());
		}
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO getRegionSchools(HttpSession session) {
		Integer regionId = Integer.valueOf(session.getAttribute("regionId").toString());
		List<SchoolMapper> allSchools = school_dao.findByRegionIdOrderByIdDesc(regionId);
		return ResultVOUtil.success(allSchools);
	}


	

	

	

}
