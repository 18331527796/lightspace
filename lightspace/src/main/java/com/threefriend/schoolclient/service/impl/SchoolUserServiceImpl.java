package com.threefriend.schoolclient.service.impl;

import java.io.IOException;
import java.text.DecimalFormat;
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
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.SchoolRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.repository.TeacherRepository;
import com.threefriend.lightspace.repository.UserRepository;
import com.threefriend.lightspace.repository.UserRoleRepository;
import com.threefriend.lightspace.repository.schoolclient.SchoolClassRepository;
import com.threefriend.lightspace.repository.schoolclient.SchoolRightRepository;
import com.threefriend.lightspace.repository.schoolclient.SchoolRoleRightRepository;
import com.threefriend.lightspace.repository.schoolclient.SchoolSemesterRepository;
import com.threefriend.lightspace.repository.schoolclient.SchoolStudentRecordRepository;
import com.threefriend.lightspace.util.ListUtils;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.vo.SchoolMenuListVo;
import com.threefriend.lightspace.vo.Top5VO;
import com.threefriend.lightspace.xcx.util.SendMessageUtils;
import com.threefriend.schoolclient.service.SchoolUserService;

@Service
public class SchoolUserServiceImpl implements SchoolUserService{
	
	@Autowired
	private UserRepository user_dao;
	
	@Autowired
	private UserRoleRepository user_role_dao;
	
	@Autowired
	private SchoolRightRepository right_dao;
	
	@Autowired
	private SchoolRoleRightRepository role_right_dao;
	
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
	
	
	@Override
	public ResultVO login(Map<String, String> params,HttpSession session) {
		
		// 比如对密码进行 md5 加密
		String md5 = DigestUtils.md5DigestAsHex(params.get("password").getBytes());
		List<UserMapper> user = user_dao.findByloginNameAndPassword(params.get("loginName"), md5);
		List<TeacherMapper> teacher = teacher_dao.findByPhoneAndPassword(params.get("loginName"), params.get("password"));
		if(1 !=user.size()&&teacher==null&&user.size()==0&&teacher.size()==0)return ResultVOUtil.error(ResultEnum.LOGIN_FAIL.getStatus(), ResultEnum.LOGIN_FAIL.getMessage());
		Integer roleId = 0;
		if(1 ==user.size()) {
			roleId = user_role_dao.findByUserId(user.get(0).getId()).get(0).getRoleId();
		}else {
			roleId = 3;
		}
		//如果不是校园管理 班级管理 不让进
		if(roleId!=2&&roleId!=3)return ResultVOUtil.error(ResultEnum.LOGIN_FAIL.getStatus(), ResultEnum.LOGIN_FAIL.getMessage());
		Map<String , String> end = new HashMap<>();
		SchoolMapper school = null;
		 if(roleId == 3 && 1 ==user.size()) {
			 school=school_dao.findById(user.get(0).getSchoolId()).get();
			 end.put("classId", user.get(0).getClassesId()+"");
			 end.put("className", class_dao.findById(user.get(0).getClassesId()).get().getClassName());
			 end.put("userName", user.get(0).getName());
			 session.setAttribute("classId",user.get(0).getClassesId());
		 }else if(roleId == 3 && 1 !=user.size()){
			 school=school_dao.findById(teacher.get(0).getSchoolId()).get();
			 end.put("classId", teacher.get(0).getClassId()+"");
			 end.put("className", class_dao.findById(teacher.get(0).getClassId()).get().getClassName());
			 end.put("userName", teacher.get(0).getName());
			 session.setAttribute("classId",teacher.get(0).getClassId());
		 }else {
			 school=school_dao.findById(user.get(0).getSchoolId()).get();
			 end.put("userName", user.get(0).getName());
		 }
		end.put("schoolName", school.getName());
		end.put("schoolId", school.getId()+"");
		end.put("roleId", roleId+"");
		
		session.setAttribute("roleId",roleId);
		session.setAttribute("schoolId",school.getId()+"");
		
		Integer schoolId = school.getId();
		new Thread(
			    new Runnable(){
					public void run(){
						initialize(schoolId);
			        }
			    }
			).start();
		
		return ResultVOUtil.success(end);
	}
	
	/* 
	 * 菜单list
	 */
	@Override
	public ResultVO getUserRight(HttpSession session) {
		Integer roleId = Integer.valueOf(session.getAttribute("roleId").toString());
		// 根据角色查找相应的权限
		List<SchoolRoleRightRelation> findByRoleId = role_right_dao.findByRoleId(roleId);
		List<Integer> ids = new ArrayList<>();
		for (SchoolRoleRightRelation roleRightRelation : findByRoleId) {
			ids.add(roleRightRelation.getRightId());
		}
		//组装树形
		List<SchoolRightMapper> rights = right_dao.findAllById(ids);
		List<SchoolMenuListVo> trees = new ArrayList<SchoolMenuListVo>();
		for (SchoolRightMapper role1 : rights) {
			SchoolMenuListVo menu = new SchoolMenuListVo();
			if (role1.getpId() == 0) {
				menu.setId(role1.getId());
				menu.setAuthName(role1.getAuthName());
				menu.setPath(role1.getPath());
				for (SchoolRightMapper it : rights) {
					if (it.getpId() == role1.getId()) {
						if (menu.getChildren() == null) {
							menu.setChildren(new ArrayList<SchoolRightMapper>());
						}
						menu.setId(role1.getId());
						menu.setAuthName(role1.getAuthName());
						menu.getChildren().add(it);
					}
				}
				trees.add(menu);
			}
		}
		return ResultVOUtil.success(trees);
	}

	@Override
	public ResultVO survey(HttpSession session) {
		Integer schoolId = Integer.valueOf(session.getAttribute("schoolId").toString());
		
		Map<String , String> end = new HashMap<>();
		DecimalFormat df=new DecimalFormat("0.00");
		int bad= 0,nullstudent=0;
		Double LEFT = 0d , RIGHT = 0d, AVG = 0d;
		
		//所有学生数量
		int allstudent = student_dao.countBySchoolId(schoolId);
		List<StudentMapper> students = student_dao.findBySchoolId(schoolId);
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
		
		DecimalFormat df=new DecimalFormat("0.00");
		
		List<String> end =new  ArrayList<String>();
		
		List<Integer> allclass = null;
		List<StudentMapper> students = null;
		
		for (int i = 1; i < 7; i++) {
			int bad= 0,nullstudent=0;
			Double LEFT = 0d , RIGHT = 0d, AVG = 0d;
			allclass = class_dao.findIdBySchoolIdAndGrade(schoolId,i);
			
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
			
			allclass = class_dao.findIdBySchoolIdAndGrade(schoolId,i);
			
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
		
		DecimalFormat df=new DecimalFormat("0.00");
		List<String> end = new ArrayList<>();
		
		List<Integer> allclass = null;
		List<StudentMapper> students = null;
		
		for (int i = 1; i < 7; i++) {
			int bad= 0,nullstudent=0;
			Double LEFT = 0d , RIGHT = 0d, AVG = 0d , sum = 0d;
			allclass = class_dao.findIdBySchoolIdAndGrade(schoolId,i);
			
			if(allclass.size()<1||allclass==null) {
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
		
		String type = "";
		if(!StringUtils.isEmpty(params.get("type")))type = params.get("type");
		
		DecimalFormat df=new DecimalFormat("0.00");
		
		List<Top5VO> end = new ArrayList<>();
		List<Integer> grades = new ArrayList<>();
		for (int i = 1; i < 7; i++) {
			grades.add(i);
		}
		List<ClassesMapper> allclass = class_dao.findBySchoolIdAndGradeIn(schoolId,grades);
		
		List<StudentMapper> students = null;
		int allstudent = 0 , bad ;
		Double avg = 0d;
		
		for (ClassesMapper po : allclass) {
			bad = 0;
			allstudent=0;
			students = student_dao.findByClassesId(po.getId());
			Top5VO vo = new Top5VO();
			vo.setName(po.getClassName());
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
		Integer schoolId = Integer.valueOf(session.getAttribute("schoolId").toString());
		return ResultVOUtil.success(school_semester_dao.findBySchool(schoolId));
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
			if((new Date().getTime()-SemesterPo.getGenTime().getTime())>604800000) {
				school_class_dao.deleteBySemesterId(SemesterPo.getId());
				SemesterPo.setGenTime(new Date());
				System.out.println("进行重置");
			}else {
				System.out.println("时间不够，跳出方法");
				return ; 
			}
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
			SchoolClassesMapper newClass = new SchoolClassesMapper();
			newClass.setClassId(po.getId());
			newClass.setGrade(po.getGrade());
			newClass.setName(po.getClassName());
			newClass.setSchoolId(po.getSchoolId());
			newClass.setSemesterId(SemesterPo.getId());
			newClass.setClassNumber(Integer.valueOf(po.getClassName().substring(2, 3)));
			school_class_dao.save(newClass);
			classIds.add(po.getId());
		}
		
		List<StudentMapper> allStudent = student_dao.findByClassesId(classIds);
		List<SchoolStudentRecordMapper> saveRecord = new ArrayList<>();
		for (StudentMapper po : allStudent) {
			if(po.getLastTime()==null||po.getVisionLeftStr()==null||po.getVisionRightStr()==null)continue;
			SchoolStudentRecordMapper record = new SchoolStudentRecordMapper();
			record.setName(po.getName());
			record.setClassId(po.getClassesId());
			record.setGender(po.getGender());
			record.setSchoolId(schoolId);
			record.setVisionLeftStr(po.getVisionLeftStr());
			record.setVisionRightStr(po.getVisionLeftStr());
			record.setSemester(SemesterPo.getId());
			saveRecord.add(record);
		}
		school_student_record_dao.saveAll(saveRecord);
		
	}

	@Override
	public ResultVO undetectedList(HttpSession session) {
		Integer schoolId = Integer.valueOf(session.getAttribute("schoolId").toString());
		List<StudentMapper> student = student_dao.findBySchoolId(schoolId);
		List<StudentMapper> end = new ArrayList<>();
		for (StudentMapper s : student) {
			if(s.getLastTime()==null||s.getVisionLeftStr()==null||s.getVisionRightStr()==null) {
				end.add(s);
			}
		}
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO badList(HttpSession session) {
		Integer schoolId = Integer.valueOf(session.getAttribute("schoolId").toString());
		List<StudentMapper> student = student_dao.findBySchoolId(schoolId);
		List<StudentMapper> end = new ArrayList<>();
		Double LEFT,RIGHT,AVG;
		for (StudentMapper s : student) {
			if(s.getLastTime()==null||s.getVisionLeftStr()==null||s.getVisionRightStr()==null) {
				continue;
			}
			
			LEFT = s.getVisionLeftStr();
			RIGHT = s.getVisionRightStr();
			
			if(LEFT>=RIGHT) {
				AVG = RIGHT;
			}else {
				AVG = LEFT;
			}
			if(AVG<1.0)end.add(s);
		}
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO loginOut(HttpSession session) {
		session.invalidate();
		return ResultVOUtil.success();
	}

	

	

}
