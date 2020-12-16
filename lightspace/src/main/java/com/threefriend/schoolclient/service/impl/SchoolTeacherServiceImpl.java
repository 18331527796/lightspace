package com.threefriend.schoolclient.service.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.enums.AccountEnums;
import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.SchoolMapper;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.mapper.TeacherMapper;
import com.threefriend.lightspace.mapper.xcx.GzhUserMapper;
import com.threefriend.lightspace.mapper.xcx.ParentMapper;
import com.threefriend.lightspace.mapper.xcx.ParentStudentRelation;
import com.threefriend.lightspace.mapper.xcx.ScreeningMapper;
import com.threefriend.lightspace.mapper.xcx.ScreeningWearMapper;
import com.threefriend.lightspace.mapper.xcx.TaskMapper;
import com.threefriend.lightspace.mapper.xcx.TaskRecordMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.GzhUserRepository;
import com.threefriend.lightspace.repository.ParentRepository;
import com.threefriend.lightspace.repository.ParentStudentRepository;
import com.threefriend.lightspace.repository.SchoolRepository;
import com.threefriend.lightspace.repository.ScreeningRepository;
import com.threefriend.lightspace.repository.ScreeningWearRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.repository.TaskRecordRepository;
import com.threefriend.lightspace.repository.TaskRepository;
import com.threefriend.lightspace.repository.TeacherRepository;
import com.threefriend.lightspace.util.RedisUtils;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.vo.TaskVO;
import com.threefriend.lightspace.vo.TeacherVO;
import com.threefriend.lightspace.xcx.util.SendMessageUtils;
import com.threefriend.lightspace.xcx.util.WeChatUtils;
import com.threefriend.schoolclient.service.SchoolTeacherService;

/**
 *	教师业务逻辑实现
 */
@Service
public class SchoolTeacherServiceImpl implements SchoolTeacherService{
	@Autowired
	private TeacherRepository teacher_dao;
	@Autowired
	private SchoolRepository school_dao;
	@Autowired
	private ClassesRepository class_dao;
	@Resource
	private RedisUtils redisUtil;
	@Autowired
	private ParentRepository parent_dao;
	@Autowired
	private StudentRepository student_dao;
	@Autowired
	private ParentStudentRepository p_s_dao;
	@Autowired
	private TaskRecordRepository taskrecord_dao;
	@Autowired
	private TaskRepository task_dao;
	@Autowired
	private ScreeningRepository screening_dao;
	@Autowired
	private ScreeningWearRepository screening_wear_dao;
	@Autowired
	private GzhUserRepository gzh_dao;

	/*  
	 * 教师列表
	 */
	@Override
	public ResultVO teacherList(Map<String, String> params,HttpSession session) {
		Integer schoolId = Integer.valueOf(session.getAttribute("schoolId").toString());
		int page = 0 ; 
		if(!StringUtils.isEmpty(params.get("page")))page = Integer.valueOf(params.get("page")) - 1 ;
		return ResultVOUtil.success(teacher_dao.findBySchoolId(schoolId,PageRequest.of(page, 10,Sort.by("id").descending())));
	}

	/*  
	 * 新增教师
	 */
	@Override
	public ResultVO addTeacher(Map<String, String> params,HttpSession session) {
		Integer schoolId = Integer.valueOf(session.getAttribute("schoolId").toString());
		Integer classId = Integer.valueOf(params.get("classId"));
		SchoolMapper school = school_dao.findById(schoolId).get();		
		TeacherMapper teacher = new TeacherMapper();
		teacher.setSchoolId(schoolId);
		teacher.setSchoolName(school.getName());
		teacher.setClassId(classId);
		teacher.setClassName(class_dao.findById(classId).get().getClassName());
		teacher.setRegionId(school.getRegionId());
		teacher.setRegionName(school.getRegionName());
		teacher.setName(params.get("name"));
		teacher.setPhone(params.get("phone"));
		teacher.setPassword(params.get("password"));
		teacher_dao.save(teacher);
		return ResultVOUtil.success();
	}

	/*  
	 * 修改教师
	 */
	@Override
	public ResultVO findById(Map<String, String> params) {
		TeacherMapper teacherMapper = teacher_dao.findById(Integer.valueOf(params.get("id"))).get();
		TeacherVO vo = new TeacherVO();
		BeanUtils.copyProperties(teacherMapper, vo);
		vo.getTea_cat().add(teacherMapper.getSchoolId());
		vo.getTea_cat().add(teacherMapper.getClassId());
		return ResultVOUtil.success(vo);
	}
	/*  
	 * 修改后保存
	 */
	@Override
	public ResultVO alertTeacher(Map<String, String> params) {
		TeacherMapper teacher = teacher_dao.findById(Integer.valueOf(params.get("id"))).get();
		if(!StringUtils.isEmpty(params.get("classId"))) {
			Integer classId = Integer.valueOf(params.get("classId"));
			ClassesMapper classpo = class_dao.findById(classId).get();
			teacher.setSchoolId(classpo.getSchoolId());
			teacher.setSchoolName(classpo.getSchoolName());
			teacher.setClassId(classId);
			teacher.setClassName(classpo.getClassName());
		}
		if(!StringUtils.isEmpty(params.get("name")))teacher.setName(params.get("name"));
		if(!StringUtils.isEmpty(params.get("phone")))teacher.setPhone(params.get("phone"));
		if(!StringUtils.isEmpty(params.get("password")))teacher.setPassword(params.get("password"));
		teacher_dao.save(teacher);
		return ResultVOUtil.success();
	}
	/*  
	 * 删除教师
	 */
	@Override
	public ResultVO deleteTeacher(Map<String, String> params) {
		teacher_dao.deleteById(Integer.valueOf(params.get("id")));
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO queryTeacher(Map<String, String> params,HttpSession session) {
		Integer schoolId = Integer.valueOf(session.getAttribute("schoolId").toString());
		List<TeacherMapper> list = teacher_dao.findBySchoolIdAndNameLike(schoolId,"%"+params.get("name")+"%");
		return ResultVOUtil.success(list);
	}
	
	@Override
	public Map<String, Date> beginAndEnd() throws Exception {
		Map<String, Date> map = new HashMap<String, Date>();
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date begin = simpleDateFormat.parse(DateFormatUtils.format(new Date(), "yyyy-MM-dd 00:00:00"));
		Date end = simpleDateFormat.parse(DateFormatUtils.format(new Date(), "yyyy-MM-dd 23:59:59"));
		map.put("begin", begin);
		map.put("end", end);
		return map;
	}

	@Override
	public ResultVO undetected(Map<String, String> params,HttpSession session) {
		SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd"); // 定义想要的格式
		String now = Format.format(new Date()).substring(0, 10);
		long today = new Date().getTime();
		Integer classId = Integer.valueOf(session.getAttribute("classId").toString());
		List<StudentMapper> student = student_dao.findByClassesId(classId);
		List<StudentMapper> end = new ArrayList<>();
		for (StudentMapper s : student) {
			if (now.equals(Format.format(s.getRemindUndetected()).substring(0, 10)))
				continue;
			if (s.getVisionLeftStr() == null || s.getVisionRightStr() == null || s.getSendTime() == null
					|| today - s.getSendTime().getTime() >= 604800000) {
				end.add(s);
			}
		}
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO untask(Map<String, String> params,HttpSession session) throws Exception {
		SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd"); // 定义想要的格式
		String now = Format.format(new Date()).substring(0, 10);
		Map<String, Date> map = beginAndEnd();
		Integer classId = Integer.valueOf(session.getAttribute("classId").toString());
		List<StudentMapper> student = student_dao.findByClassesId(classId);
		List<Map<String, Object>> end = new ArrayList<>();
		List<TaskVO> TaskVO = null;
		Map<String, Object> endmap = null;
		List<TaskMapper> alltask = task_dao.findAll();
		long tasksize = task_dao.count();
		for (StudentMapper one : student) {
			if (now.equals(Format.format(one.getRemindUntask()).substring(0, 10)))
				continue;
			List<TaskRecordMapper> tasks = taskrecord_dao.findByStudentIdAndGenTimeBetween(one.getId(),
					map.get("begin"), map.get("end"));
			if (tasks.size() != tasksize) {
				endmap = new HashMap<>();
				TaskVO = new ArrayList<>();
				for (TaskMapper task : alltask) {
					TaskVO vo = new TaskVO();
					BeanUtils.copyProperties(task, vo);
					vo.setSuccess(0);
					for (TaskRecordMapper record : tasks) {
						if (record.getTaskId() == task.getId()) {
							vo.setSuccess(1);
						}
					}
					TaskVO.add(vo);
				}
				endmap.put("id", one.getId());
				endmap.put("name", one.getName());
				endmap.put("gender", one.getGender());
				endmap.put("schoolName", one.getSchoolName());
				endmap.put("className", one.getClassesName());
				endmap.put("taskList", TaskVO);
				end.add(endmap);
			}
		}
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO decline(Map<String, String> params,HttpSession session) {
		SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd"); // 定义想要的格式
		String now = Format.format(new Date()).substring(0, 10);
		Integer classId = Integer.valueOf(session.getAttribute("classId").toString());
		List<StudentMapper> student = student_dao.findByClassesId(classId);
		List<Map<String, Object>> end = new ArrayList<>();
		Map<String, Object> endmap = null;
		for (StudentMapper one : student) {
			if (now.equals(Format.format(one.getRemindDecline()).substring(0, 10)))
				continue;
			if (one.getScreeningType() == 1) {
				List<ScreeningMapper> screening = screening_dao
						.findByStudentIdOrderByGenTimeDesc(one.getId(), PageRequest.of(0, 2)).getContent();
				if (screening.size() == 2 && (screening.get(0).getVisionLeftStr() < screening.get(1).getVisionLeftStr()
						|| screening.get(0).getVisionRightStr() < screening.get(1).getVisionRightStr())) {
					endmap = new HashMap<>();
					endmap.put("id", one.getId());
					endmap.put("name", one.getName());
					endmap.put("gender", one.getGender());
					endmap.put("type", 1);
					endmap.put("new", screening.get(0));
					endmap.put("old", screening.get(1));
					end.add(endmap);
				}
			} else if (one.getScreeningType() == 2) {
				List<ScreeningWearMapper> screening = screening_wear_dao
						.findByStudentIdOrderByGenTimeDesc(one.getId(), PageRequest.of(0, 2)).getContent();
				if (screening.size() == 2 && (screening.get(0).getVisionLeftStr() < screening.get(1).getVisionLeftStr()
						|| screening.get(0).getVisionRightStr() < screening.get(1).getVisionRightStr())) {
					endmap = new HashMap<>();
					endmap.put("id", one.getId());
					endmap.put("name", one.getName());
					endmap.put("gender", one.getGender());
					endmap.put("type", 2);
					endmap.put("new", screening.get(0));
					endmap.put("old", screening.get(1));
					end.add(endmap);
				}
			}
		}
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO remindUndetected(Integer[] params) throws IOException {
		List<Integer> ids = new ArrayList<>();
		for (Integer integer : params) {
			ids.add(integer);
		}
		long now = new Date().getTime();
		String time = "";
		List<StudentMapper> Allstudent = student_dao.findAllById(ids);
		for (StudentMapper student : Allstudent) {
			if (student.getSendTime() != null) {
				now = (now - student.getSendTime().getTime()) / 1000 / 86400;
				time = now + "";
			} else {
				time = "超过7";
			}
			sendmsg(student, time, "undetected", null);
			student.setRemindUndetected(new Date());
		}
		student_dao.saveAll(Allstudent);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO remindDecline(Integer[] params) throws IOException {
		List<Integer> ids = new ArrayList<>();
		for (Integer integer : params) {
			ids.add(integer);
		}
		List<StudentMapper> Allstudent = student_dao.findAllById(ids);
		for (StudentMapper student : Allstudent) {
			sendmsg(student, null, "decline", null);
			student.setRemindDecline(new Date());
		}
		student_dao.saveAll(Allstudent);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO remindUntask(Integer[] params) throws Exception {
		Map<String, Date> map = beginAndEnd();
		List<Integer> ids = new ArrayList<>();
		for (Integer integer : params) {
			ids.add(integer);
		}
		List<StudentMapper> Allstudent = student_dao.findAllById(ids);
		int flag = 0;
		for (StudentMapper student : Allstudent) {
			StringBuilder taskstr = new StringBuilder();
			List<TaskMapper> alltask = task_dao.findAll();
			List<TaskRecordMapper> tasks = taskrecord_dao.findByStudentIdAndGenTimeBetween(student.getId(),
					map.get("begin"), map.get("end"));
			for (TaskMapper taskMapper : alltask) {
				flag = 1;
				for (TaskRecordMapper taskRecordMapper : tasks) {
					if (taskMapper.getId() == taskRecordMapper.getTaskId())
						flag = 2;
				}
				if (flag == 1 && taskstr.length() != 0)
					taskstr.append("，" + taskMapper.getTitle());
				if (flag == 1 && taskstr.length() == 0)
					taskstr.append(taskMapper.getTitle());
			}
			sendmsg(student, null, "untask", taskstr.toString());
			student.setRemindUntask(new Date());
		}
		student_dao.saveAll(Allstudent);
		return ResultVOUtil.success();
	}

	/*
	 * 拿到公众号的token
	 */
	@Override
	public String getAccessToken() {
		String ACCESS_TOKEN = redisUtil.get("GZHTOKEN");
		if (ACCESS_TOKEN == null || "".equals(ACCESS_TOKEN)) {
			ACCESS_TOKEN = WeChatUtils.findAccessToken(AccountEnums.GZHAPPID.getUrl(), AccountEnums.GZHSECRET.getUrl());
			redisUtil.setValueTime("GZHTOKEN", ACCESS_TOKEN, 7000);
		}
		return ACCESS_TOKEN;
	}

	@Override
	public void sendmsg(StudentMapper student, String time, String type, String task) throws IOException {
		String accessToken = getAccessToken();
		List<ParentStudentRelation> allparent = p_s_dao.findByStudentId(student.getId());
		for (ParentStudentRelation parents : allparent) {
			// 找到这个家长
			Optional<ParentMapper> ParentMapper = parent_dao.findById(parents.getParentId());
			// 如果没有就跳过
			if (!ParentMapper.isPresent())
				continue;
			ParentMapper parent = ParentMapper.get();
			// 家长的unionid不能是空的
			if (!StringUtils.isEmpty(parent.getUnionId())) {
				// 通过unionid查找这个家长的公众号openId
				GzhUserMapper po = gzh_dao.findByUnionid(parent.getUnionId());
				if (po != null && "undetected".equals(type))
					SendMessageUtils.undetectedMessage(po.getOpenid(), accessToken, student.getName(), time);
				if (po != null && "decline".equals(type))
					SendMessageUtils.declineMessage(po.getOpenid(), accessToken, student.getName());
				if (po != null && "untask".equals(type))
					SendMessageUtils.untaskMessage(po.getOpenid(), accessToken, student.getName(), task);
			}
		}
	}
}
