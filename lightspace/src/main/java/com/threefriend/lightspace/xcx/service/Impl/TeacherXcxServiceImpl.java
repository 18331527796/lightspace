package com.threefriend.lightspace.xcx.service.Impl;

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

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.enums.AccountEnums;
import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.mapper.TeacherMapper;
import com.threefriend.lightspace.mapper.xcx.GzhUserMapper;
import com.threefriend.lightspace.mapper.xcx.ParentMapper;
import com.threefriend.lightspace.mapper.xcx.ParentStudentRelation;
import com.threefriend.lightspace.mapper.xcx.ScreeningMapper;
import com.threefriend.lightspace.mapper.xcx.ScreeningWearMapper;
import com.threefriend.lightspace.mapper.xcx.TaskMapper;
import com.threefriend.lightspace.mapper.xcx.TaskRecordMapper;
import com.threefriend.lightspace.repository.GzhUserRepository;
import com.threefriend.lightspace.repository.ParentRepository;
import com.threefriend.lightspace.repository.ParentStudentRepository;
import com.threefriend.lightspace.repository.ScreeningRepository;
import com.threefriend.lightspace.repository.ScreeningWearRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.repository.TaskRecordRepository;
import com.threefriend.lightspace.repository.TaskRepository;
import com.threefriend.lightspace.repository.TeacherRepository;
import com.threefriend.lightspace.util.OptotypeUtils;
import com.threefriend.lightspace.util.RedisUtils;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.vo.TaskVO;
import com.threefriend.lightspace.vo.TeacherPageVO;
import com.threefriend.lightspace.xcx.service.TeacherXcxService;
import com.threefriend.lightspace.xcx.util.SendMessageUtils;
import com.threefriend.lightspace.xcx.util.WeChatUtils;

@Service
public class TeacherXcxServiceImpl implements TeacherXcxService {

	@Resource
	private RedisUtils redisUtil;
	@Autowired
	private TeacherRepository teacher_dao;
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
	 * 教师登录
	 */
	@Override
	public ResultVO teacherLogin(Map<String, String> params) {
		System.err.println("教师登录账号："+params.get("phone") +"    密码："+ params.get("password"));
		String phone = params.get("phone");
		String password = params.get("password");
		List<TeacherMapper> teacher = teacher_dao.findByPhoneAndPassword(phone, password);
		if (teacher.size() != 1)
			return ResultVOUtil.error(ResultEnum.TEACHER_LOGIN_ERROR.getStatus(),
					ResultEnum.TEACHER_LOGIN_ERROR.getMessage());
		ParentMapper parent = parent_dao.findByOpenId(params.get("openId"));
		
		TeacherMapper po = teacher_dao.findByParentId(parent.getId());
		if (null != po ) {
			po.setParentId(null);
			po.setState(0);
			teacher_dao.save(po);
		}
		
		teacher.get(0).setState(1); // 改变登录状态
		teacher.get(0).setParentId(parent.getId());// 关联小程序账号
		teacher_dao.save(teacher.get(0));
		return ResultVOUtil.success();
	}

	/*
	 * 验证登录状态
	 */
	@Override
	public ResultVO chkState(Map<String, String> params) {
		ParentMapper parent = parent_dao.findByOpenId(params.get("openId"));
		TeacherMapper po = teacher_dao.findByParentId(parent.getId());
		if (null != po && null != po.getState() && po.getState()== 1)
			return ResultVOUtil.success();
		return ResultVOUtil.error(ResultEnum.CHKSTATE_ERROR.getStatus(), ResultEnum.CHKSTATE_ERROR.getMessage());
	}

	@Override
	public ResultVO teacherPage(Map<String, String> params) throws Exception {
		SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd"); // 定义想要的格式
		String now = Format.format(new Date()).substring(0, 10);
		Map<String, Date> map = beginAndEnd();
		ParentMapper parent = parent_dao.findByOpenId(params.get("openId"));
		TeacherMapper po = teacher_dao.findByParentId(parent.getId());
		List<StudentMapper> student = student_dao.findByClassesId(po.getClassId());
		TeacherPageVO vo = new TeacherPageVO();
		long tasksize = task_dao.count(), tasknumber = 0, today = new Date().getTime();
		Integer undetected = 0// 未检测人数
				, decline = 0// 视力下降人数
				, untask = 0// 未打卡人数
				, good = 0// 良好
				, mild = 0// 轻度
				, moderate = 0// 中度
				, serious = 0;// 重度
		Double LEFT = null, RIGHT = null;
		for (StudentMapper one : student) {
			LEFT = one.getVisionLeftStr();
			RIGHT = one.getVisionRightStr();
			tasknumber = taskrecord_dao.countByStudentIdAndGenTimeBetween(one.getId(), map.get("begin"),
					map.get("end"));
			//if (!now.equals(Format.format(one.getRemindDecline()).substring(0, 10))) {
			  if ((today - one.getRemindDecline().getTime())/1000 >= 5184000) {	
				if (one.getScreeningType() == 1) {
					List<ScreeningMapper> screening = screening_dao
							.findByStudentIdOrderByGenTimeDesc(one.getId(), PageRequest.of(0, 2)).getContent();
					if (screening.size() == 2
							&& (screening.get(0).getVisionLeftStr() < screening.get(1).getVisionLeftStr()
									|| screening.get(0).getVisionRightStr() < screening.get(1).getVisionRightStr())) {
						decline++;
					}
				} else if (one.getScreeningType() == 2) {
					List<ScreeningWearMapper> screening = screening_wear_dao
							.findByStudentIdOrderByGenTimeDesc(one.getId(), PageRequest.of(0, 2)).getContent();
					if (screening.size() == 2
							&& (screening.get(0).getVisionLeftStr() < screening.get(1).getVisionLeftStr()
									|| screening.get(0).getVisionRightStr() < screening.get(1).getVisionRightStr())) {
						decline++;
					}
				}
			}
			if (!now.equals(Format.format(one.getRemindUntask()).substring(0, 10))) {
				if (tasknumber != tasksize)
					untask++;
			}
			//if (!now.equals(Format.format(one.getRemindUndetected()).substring(0, 10))) {
				if (LEFT == null || RIGHT == null || one.getSendTime() == null
						|| (today - one.getSendTime().getTime())/1000 >= 5184000) {
					undetected++;
				}
			//}
			if (LEFT != null && RIGHT != null) {
				if (LEFT >= RIGHT) {
					if (RIGHT >= 1.0d) {
						good++;
					} else if (RIGHT < 1.0d && RIGHT >= 0.6d) {
						mild++;
					} else if (RIGHT < 0.6d && RIGHT >= 0.4d) {
						moderate++;
					} else {
						serious++;
					}
				} else {
					if (LEFT >= 1.0d) {
						good++;
					} else if (LEFT < 1.0d && LEFT >= 0.6d) {
						mild++;
					} else if (LEFT < 0.6d && LEFT >= 0.4d) {
						moderate++;
					} else {
						serious++;
					}
				}
			}
		}
		vo.setUndetected(undetected);
		vo.setDecline(decline);
		vo.setUntask(untask);
		vo.setGood(good);
		vo.setMild(mild);
		vo.setModerate(moderate);
		vo.setSerious(serious);
		return ResultVOUtil.success(vo);
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
	public ResultVO undetected(Map<String, String> params) {
		SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd"); // 定义想要的格式
		//String now = Format.format(new Date()).substring(0, 10);
		long today = new Date().getTime();
		ParentMapper parent = parent_dao.findByOpenId(params.get("openId"));
		TeacherMapper po = teacher_dao.findByParentId(parent.getId());
		List<StudentMapper> student = student_dao.findByClassesId(po.getClassId());
		List<StudentMapper> end = new ArrayList<>();
		for (StudentMapper s : student) {
			//这个是用来跳过今天提醒过了的
//			if (now.equals(Format.format(s.getRemindUndetected()).substring(0, 10)))
//				continue;
			if (s.getVisionLeftStr() == null || s.getVisionRightStr() == null|| s.getSendTime() == null
					|| (today - s.getSendTime().getTime())/1000 >= 5184000) {
				end.add(s);
			}
		}
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO untask(Map<String, String> params) throws Exception {
		SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd"); // 定义想要的格式
		String now = Format.format(new Date()).substring(0, 10);
		Map<String, Date> map = beginAndEnd();
		ParentMapper parent = parent_dao.findByOpenId(params.get("openId"));
		TeacherMapper po = teacher_dao.findByParentId(parent.getId());
		List<StudentMapper> student = student_dao.findByClassesId(po.getClassId());
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
				endmap.put("taskList", TaskVO);
				end.add(endmap);
			}
		}
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO decline(Map<String, String> params) {
		SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd"); // 定义想要的格式
		String now = Format.format(new Date()).substring(0, 10);
		long today = new Date().getTime();
		ParentMapper parent = parent_dao.findByOpenId(params.get("openId"));
		TeacherMapper po = teacher_dao.findByParentId(parent.getId());
		List<StudentMapper> student = student_dao.findByClassesId(po.getClassId());
		List<Map<String, Object>> end = new ArrayList<>();
		Map<String, Object> endmap = null;
		for (StudentMapper one : student) {
			if (now.equals(Format.format(one.getRemindDecline()).substring(0, 10))||(today - one.getRemindDecline().getTime())/1000 < 5184000)
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
				time = "超过60";
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

	@Override
	public ResultVO studentByType(Map<String, String> params) {
		ParentMapper parent = parent_dao.findByOpenId(params.get("openId"));
		TeacherMapper po = teacher_dao.findByParentId(parent.getId());
		List<StudentMapper> student = student_dao.findByClassesId(po.getClassId());
		List<Map<String, Object>> end = new ArrayList<>();
		Map<String, Object> endmap = null;
		String type = params.get("type");
		Double LEFT=null,RIGHT=null;
			for (StudentMapper spo : student) {
				if(spo.getVisionLeftStr()==null||spo.getVisionRightStr()==null)continue;
				LEFT = spo.getVisionLeftStr();
				RIGHT = spo.getVisionRightStr();
				endmap = new HashMap<>();
				endmap.put("id", spo.getId());
				endmap.put("name", spo.getName());
				endmap.put("time", spo.getLastTime());
				endmap.put("gender", spo.getGender());
				endmap.put("screeningType", spo.getScreeningType());
				endmap.put("left", OptotypeUtils.vision2vision5(spo.getVisionLeftStr()));
				endmap.put("right", OptotypeUtils.vision2vision5(spo.getVisionRightStr()));
				
				if (LEFT >= RIGHT) {
					if (RIGHT < 1.0d && RIGHT >= 0.6d&&"mild".equals(type))end.add(endmap);
					if (RIGHT < 0.6d && RIGHT >= 0.4d&&"moderate".equals(type))end.add(endmap);
					if (RIGHT < 0.4d && "serious".equals(type)) {System.out.println(spo.getId());end.add(endmap);};
				}else {
					if (LEFT < 1.0d && LEFT >= 0.6d&&"mild".equals(type))end.add(endmap);
					if (LEFT < 0.6d && LEFT >= 0.4d&&"moderate".equals(type))end.add(endmap);
					if (LEFT < 0.4d && "serious".equals(type)){System.out.println(spo.getId());end.add(endmap);};
				}
			}
			return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO loginOut(Map<String, String> params) {
		ParentMapper parent = parent_dao.findByOpenId(params.get("openId"));
		List<TeacherMapper> teachers = teacher_dao.findAllByParentId(parent.getId());
		for (TeacherMapper po : teachers) {
			po.setState(0);
			po.setParentId(null);
		}
		teacher_dao.saveAll(teachers);
		return ResultVOUtil.success();
	}
	

}
