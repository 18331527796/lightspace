package com.threefriend.lightspace.xcx.service.Impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.Exception.SendMessageException;
import com.threefriend.lightspace.enums.AccountEnums;
import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.DiopterMapper;
import com.threefriend.lightspace.mapper.MsgTempMapper;
import com.threefriend.lightspace.mapper.SchoolMapper;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.mapper.StudentWordMapper;
import com.threefriend.lightspace.mapper.xcx.GzhUserMapper;
import com.threefriend.lightspace.mapper.xcx.IntegralMapper;
import com.threefriend.lightspace.mapper.xcx.MarkMapper;
import com.threefriend.lightspace.mapper.xcx.OptotypeMapper;
import com.threefriend.lightspace.mapper.xcx.ParentMapper;
import com.threefriend.lightspace.mapper.xcx.ParentStudentRelation;
import com.threefriend.lightspace.mapper.xcx.ScreeningMapper;
import com.threefriend.lightspace.mapper.xcx.ScreeningWearMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.DiopterRepository;
import com.threefriend.lightspace.repository.GzhUserRepository;
import com.threefriend.lightspace.repository.IntegralRepository;
import com.threefriend.lightspace.repository.MsgTempRepository;
import com.threefriend.lightspace.repository.OptotypeRepository;
import com.threefriend.lightspace.repository.ParentRepository;
import com.threefriend.lightspace.repository.ParentStudentRepository;
import com.threefriend.lightspace.repository.SchoolRepository;
import com.threefriend.lightspace.repository.ScreeningRepository;
import com.threefriend.lightspace.repository.ScreeningWearRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.util.RedisUtils;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ClassesVO;
import com.threefriend.lightspace.vo.OptotypeVO;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.vo.SchoolVO;
import com.threefriend.lightspace.vo.ScreeningVO;
import com.threefriend.lightspace.vo.StudentVO;
import com.threefriend.lightspace.xcx.service.ScreeningXcxService;
import com.threefriend.lightspace.xcx.util.SendMessageUtils;
import com.threefriend.lightspace.xcx.util.WeChatUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 筛查业务逻辑实现类
 *
 */
@Service
public class ScreeningXcxServiceImpl implements ScreeningXcxService {
	@Autowired
	private SchoolRepository school_dao;
	@Autowired
	private ClassesRepository classes_dao;
	@Autowired
	private StudentRepository student_dao;
	@Autowired
	private ScreeningRepository screening_dao;
	@Autowired
	private ScreeningWearRepository screening_wear_dao;
	@Autowired
	private OptotypeRepository optotype_dao;
	@Autowired
	private ParentRepository parent_dao;
	@Autowired
	private ParentStudentRepository p_s_dao;
	@Autowired
	private ParentStudentRepository parent_student_dao;
	@Autowired
	private GzhUserRepository gzh_dao;
	@Autowired
	private MsgTempRepository msg_temp_dao;
	@Autowired
	private IntegralRepository integral_dao;
	@Resource
	private RedisUtils redisUtil;
	@Autowired
	private DiopterRepository diopter_dao;
	

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
	public ResultVO addScreening(Map<String, String> params) throws ParseException {
		ParentMapper parent = parent_dao.findByOpenId(params.get("openId"));
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Integer studentId = Integer.valueOf(params.get("studentId"));
		Optional<StudentMapper> findById = student_dao.findById(studentId);
		if (findById != null && findById.isPresent()) {
			StudentMapper student = findById.get();
			ScreeningMapper po = new ScreeningMapper();
			po.setGenTime(new Date());
			po.setStudentId(studentId);
			po.setStudentName(student.getName());
			po.setBirthday(student.getBirthday());
			po.setVisionLeftStr(Double.valueOf(params.get("visionLeft")));
			po.setVisionRightStr(Double.valueOf(params.get("visionRight")));
			po.setProcessLeft(params.get("processLeft"));
			po.setProcessRight(params.get("processRight"));
			po.setGender(student.getGender());
			po.setClassId(student.getClassesId());
			po.setClassName(student.getClassesName());
			po.setSchoolId(student.getSchoolId());
			po.setSchoolName(student.getSchoolName());
			screening_dao.save(po);
			student.setLastTime(new Date());
			student.setVisionLeftStr(Double.valueOf(params.get("visionLeft")));
			student.setVisionRightStr(Double.valueOf(params.get("visionRight")));
			student.setScreeningType(1);
			MsgTempMapper msgtemp = msg_temp_dao.findByTypeAndSelected("screening",1);
			screeningMessage(msgtemp,student.getId(),1,student.getName());
			student.setSendTime(new Date());
			
			student.setMyIntegral(integral_dao.findIntegtalByState(1, studentId));
			student_dao.save(student);
		}
			return getCoin(parent.getId(),studentId);
	}

	/*
	 * 新增的筛查记录（这个方法待定 可能会修改）(戴镜)
	 */
	@Override
	public ResultVO addScreeningWear(Map<String, String> params) throws ParseException {
		ParentMapper parent = parent_dao.findByOpenId(params.get("openId"));
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Integer studentId = Integer.valueOf(params.get("studentId"));
		Optional<StudentMapper> findById = student_dao.findById(studentId);
		if (findById != null && findById.isPresent()) {
			StudentMapper student = findById.get();
			ScreeningWearMapper po = new ScreeningWearMapper();
			po.setGenTime(new Date());
			po.setStudentId(studentId);
			po.setStudentName(student.getName());
			po.setBirthday(student.getBirthday());
			po.setVisionLeftStr(Double.valueOf(params.get("visionLeft")));
			po.setVisionRightStr(Double.valueOf(params.get("visionRight")));
			po.setProcessLeft(params.get("processLeft"));
			po.setProcessRight(params.get("processRight"));
			po.setGender(student.getGender());
			po.setClassId(student.getClassesId());
			po.setClassName(student.getClassesName());
			po.setSchoolId(student.getSchoolId());
			po.setSchoolName(student.getSchoolName());
			screening_wear_dao.save(po);
			student.setLastTime(new Date());
			student.setVisionLeftStr(Double.valueOf(params.get("visionLeft")));
			student.setVisionRightStr(Double.valueOf(params.get("visionRight")));
			student.setScreeningType(2);
			student.setCorrect(1);
			
			MsgTempMapper msgtemp = msg_temp_dao.findByTypeAndSelected("screening",1);
			screeningMessage(msgtemp,student.getId(),2,student.getName());
			student.setSendTime(new Date());
			student.setMyIntegral(integral_dao.findIntegtalByState(1, studentId));
			
			student_dao.save(student);
			
		}
			return getCoin(parent.getId(),studentId);
	}

	/*
	 * 所有的视标返回
	 */
	@Override
	public ResultVO selectOptotype() {
		List<OptotypeMapper> allOptotype = optotype_dao.findAll(Sort.by("id").ascending());
		// 准备封装的数据类型
		List<OptotypeVO> voList = new ArrayList<>();
		// 遍历拷贝
		for (OptotypeMapper optotypeMapper : allOptotype) {
			OptotypeVO vo = new OptotypeVO();
			BeanUtils.copyProperties(optotypeMapper, vo);
			// 拆分字符串
			String[] split = optotypeMapper.getPathStr().split(",");
			// new个list 准备实现随机
			List<Integer> ids = Arrays.asList(0, 1, 2, 3, 1);
			Collections.shuffle(ids);
			// 遍历随机的list封装path
			for (int i = 0; i < ids.size(); i++) {
				int p = i;
				if (ids.get(i) == 0)
					vo.getPath().add(new HashMap<String, String>() {
						{
							put("id", p + 1 + "");
							put("src", split[ids.get(p)]);
							put("answer", "左");
						}
					});
				if (ids.get(i) == 1)
					vo.getPath().add(new HashMap<String, String>() {
						{
							put("id", p + 1 + "");
							put("src", split[ids.get(p)]);
							put("answer", "右");
						}
					});
				if (ids.get(i) == 2)
					vo.getPath().add(new HashMap<String, String>() {
						{
							put("id", p + 1 + "");
							put("src", split[ids.get(p)]);
							put("answer", "上");
						}
					});
				if (ids.get(i) == 3)
					vo.getPath().add(new HashMap<String, String>() {
						{
							put("id", p + 1 + "");
							put("src", split[ids.get(p)]);
							put("answer", "下");
						}
					});
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
		ScreeningMapper screeningMapper = screening_dao.findById(Integer.valueOf(params.get("id"))).get();
		ScreeningVO vo = new ScreeningVO();
		BeanUtils.copyProperties(screeningMapper, vo);
		JSONArray leftarray = JSONArray.fromObject(screeningMapper.getProcessLeft());
		vo.setMyIntegral(student_dao.findById(screeningMapper.getStudentId()).get().getMyIntegral());
		vo.setProcessLeftList(pushjosn(screeningMapper.getProcessLeft()));
		vo.setProcessRightList(pushjosn(screeningMapper.getProcessRight()));
		return ResultVOUtil.success(vo);
	}

	/*
	 * 这个账号的所有绑定孩子的档案
	 */
	@Override
	public ResultVO allChildrenScreening(Map<String, String> params) {
		Calendar c = Calendar.getInstance();
		// 过去90天
		c.setTime(new Date());
		c.add(Calendar.DATE, -90);
		Date beginTime = c.getTime();
		Date eneTime = new Date();
		// ↑定义时间 用来满足前台要求的图表返回数据
		List<Map<String, Object>> end = new ArrayList<>();
		// 获取这个账号的唯一标识
		ParentMapper parent = parent_dao.findByOpenId(params.get("openId"));
		// 找到这个账号绑定的孩子
		List<ParentStudentRelation> all = p_s_dao.findByParentIdOrderByIdDesc(parent.getId());
		// 如果这个账号没有绑定孩子 返回错误提示
		if (all.size() == 0)
			return ResultVOUtil.error(ResultEnum.BINDINGSTUDENT_ERROR.getStatus(),
					ResultEnum.BINDINGSTUDENT_ERROR.getMessage());
		// 遍历所有的孩子信息来封装数据
		for (ParentStudentRelation ids : all) {
			// 找到这个孩子的所有信息
			StudentMapper student = student_dao.findById(ids.getStudentId()).get();
			// 建立map容器
			Map<String, Object> map = new HashMap<>();
			// 找到这个孩子所有的档案数据
			List<ScreeningMapper> dataList = screening_dao.findByStudentIdOrderByGenTimeDesc(student.getId());
			// 找到这个孩子90内的档案数据
			List<ScreeningMapper> picList = screening_dao.findByStudentIdAndGenTimeBetweenOrderById(student.getId(), beginTime,
					eneTime);
			// 找到这个孩子所有的档案数据（戴镜）
			List<ScreeningWearMapper> weardataList = screening_wear_dao
					.findByStudentIdOrderByGenTimeDesc(student.getId());
			// 找到这个孩子90天内的档案数据（戴镜）
			List<ScreeningWearMapper> wearpicList = screening_wear_dao.findByStudentIdAndGenTimeBetweenOrderById(student.getId(),
					beginTime, eneTime);
			// 找到这个孩子所有的屈光度档案数据
			List<DiopterMapper> diopterList = diopter_dao.findByStudentIdOrderByIdDesc(student.getId());
			
			map.put("id", student.getId());
			map.put("name", student.getName());
			map.put("gender", student.getGender());
			map.put("myIntegral", student.getMyIntegral());
			map.put("birthday", student.getBirthday());
			map.put("dataList", dataList);
			map.put("picList", picList);
			map.put("weardataList", weardataList);
			map.put("wearpicList", wearpicList);
			map.put("diopterList", diopterList);
			end.add(map);
		}
		return ResultVOUtil.success(end);
	}

	/* 
	 * 自定义的字符串解析方法
	 */
	@Override
	public List<Map<String, String>> pushjosn(String josn) {
		JSONArray array = JSONArray.fromObject(josn);
		List<Map<String, String>> endlist = new ArrayList<>();
		for (Object object : array) {
			Map<String, String> end = new HashMap<>();
			JSONObject jsonObj = JSONObject.fromObject(object);
			end.put("levelPre", jsonObj.get("l").toString());
			end.put("right", jsonObj.get("r").toString());
			end.put("wrong", jsonObj.get("w").toString());
			endlist.add(end);
		}
		return endlist;
	}

	/*  
	 * 按照id查到档案
	 */
	@Override
	public ResultVO findWearById(Map<String, String> params) {
		ScreeningWearMapper screeningMapper = screening_wear_dao.findById(Integer.valueOf(params.get("id"))).get();
		ScreeningVO vo = new ScreeningVO();
		BeanUtils.copyProperties(screeningMapper, vo);
		JSONArray leftarray = JSONArray.fromObject(screeningMapper.getProcessLeft());
		vo.setMyIntegral(student_dao.findById(screeningMapper.getStudentId()).get().getMyIntegral());
		vo.setProcessLeftList(pushjosn(screeningMapper.getProcessLeft()));
		vo.setProcessRightList(pushjosn(screeningMapper.getProcessRight()));
		return ResultVOUtil.success(vo);
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
	public void screeningMessage(MsgTempMapper msg,Integer studentId,Integer type,String name) {
		System.out.println("现在进来了");
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String accessToken = getAccessToken();
		String left = "",right = "";
		// 通过学生id找到所有家长
		List<ParentStudentRelation> findByStudentId = parent_student_dao.findByStudentId(studentId);
		try {
			for (ParentStudentRelation parentStudent : findByStudentId) {
				// 找到这个家长
				Optional<ParentMapper> ParentMapper = parent_dao.findById(parentStudent.getParentId());
				// 如果没有就抛异常
				if (!ParentMapper.isPresent())
					throw new Exception();
				ParentMapper parent = ParentMapper.get();
				// 家长的unionid不能是空的
				if (parent.getUnionId() != null && !"".equals(parent.getUnionId())) {
					// 通过unionid查找这个家长的公众号openId
					GzhUserMapper findByUnionid = gzh_dao.findByUnionid(parent.getUnionId());
					if(type==1) {
						List<ScreeningMapper> screening = screening_dao.findByStudentIdOrderByGenTimeDesc(studentId, PageRequest.of(0, 2)).getContent();
						if(screening.size()>1) {
							left = screening.get(1).getVisionLeft()+"→"+screening.get(0).getVisionLeft();
							right = screening.get(1).getVisionLeft()+"→"+screening.get(0).getVisionLeft();
						}else {
							left = screening.get(0).getVisionLeft();
							right = screening.get(0).getVisionLeft();
						}
					}else {
						List<ScreeningWearMapper> screening = screening_wear_dao.findByStudentIdOrderByGenTimeDesc(studentId, PageRequest.of(0, 2)).getContent();
						if(screening.size()>1) {
							left = screening.get(1).getVisionLeft()+"→"+screening.get(0).getVisionLeft();
							right = screening.get(1).getVisionLeft()+"→"+screening.get(0).getVisionLeft();
						}else {
							left = screening.get(0).getVisionLeft();
							right = screening.get(0).getVisionLeft();
						}
					}
					if(findByUnionid!=null)SendMessageUtils.screeningMessage(msg,findByUnionid.getOpenid(), accessToken,
							left, right,
							simpleDateFormat.format(new Date()), type,name);
				}
			}
		} catch (Exception e) {
			throw new SendMessageException();
		}
	}
	
	/* 
	 * 获得金币
	 */
	public ResultVO getCoin(Integer parentId , Integer studentId ) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date begin = format.parse(DateFormatUtils.format(new Date(), "yyyy-MM-dd 00:00:00"));
		Date end = format.parse(DateFormatUtils.format(new Date(), "yyyy-MM-dd 23:59:59"));
		int count = 0 ;
		List<ScreeningMapper> list = screening_dao.findByStudentIdAndGenTimeBetweenOrderById(studentId,begin,end);
		count+=list.size();
		List<ScreeningWearMapper> list1 = screening_wear_dao.findByStudentIdAndGenTimeBetweenOrderById(studentId,begin,end);
		count+=list1.size();
		if(count>1&&count!=0) {
			//这里返回的是 今日已签到 的提示
			return ResultVOUtil.error(ResultEnum.SCREENING_ERROR.getStatus(),ResultEnum.SCREENING_ERROR.getMessage() );
		}
		IntegralMapper integral = new IntegralMapper();
		integral.setIntegral(3l);
		integral.setDetailed("筛查奖励");
		integral.setState(1);
		integral.setStudentId(studentId);
		integral.setGenTime(new Date());
		integral_dao.save(integral);
		//这里返回的其实是成功 就是用的err方法带回去的不一样的提示
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO deleteScreening(Map<String, String> params) {
		Integer type = Integer.valueOf(params.get("type"));
		Integer id = Integer.valueOf(params.get("id"));
		if(type == 1) {
			screening_dao.deleteById(id);
		}else {
			screening_wear_dao.deleteById(id);
		}
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO diopterList(Map<String, String> params) {
		Integer studentId = Integer.valueOf(params.get("studentId"));
		// 找到这个孩子所有的屈光度档案数据
		List<DiopterMapper> diopterList = diopter_dao.findByStudentIdOrderByIdDesc(studentId);
		return ResultVOUtil.success(diopterList);
	}

	@Override
	public ResultVO diopterById(Map<String, String> params) {
		Integer id = Integer.valueOf(params.get("id"));
		DiopterMapper diopterMapper = diopter_dao.findById(id).get();
		return ResultVOUtil.success(diopterMapper);
	}

	@Override
	public ResultVO screeningTopByStudent(Map<String, String> params) {
		Double shibiao  = 0.6d;
		Integer levelId = 11;
		Integer studentId = 0 ;
		if(!StringUtils.isEmpty(params.get("studentId"))) {
			studentId = Integer.valueOf(params.get("studentId"));
			//区分戴镜还是裸视 0:screening 1:wear
			Integer detectType	= Integer.valueOf(params.get("detectType"));
			//左右眼 type 1:R 2:L
			Integer type 	= Integer.valueOf(params.get("type"));
			if(detectType==0) {
				ScreeningMapper screening = screening_dao.findTopByStudentIdOrderByGenTimeDesc(studentId);
				if(screening!=null) {
					if(type==1) {
						shibiao = screening.getVisionRightStr();
					}else {
						shibiao = screening.getVisionLeftStr();
					}
				}
			}else {
				ScreeningWearMapper wear = screening_wear_dao.findTopByStudentIdOrderByGenTime(studentId);
				if(wear!=null) {
					if(type==1) {
						shibiao = wear.getVisionRightStr();
					}else {
						shibiao = wear.getVisionLeftStr();
					}
				}
			}
		}
		List<OptotypeMapper> findAll = optotype_dao.findAll();
		for (OptotypeMapper optotypeMapper : findAll) {
			if(optotypeMapper.getLevelName().equals(shibiao.toString())) {
				levelId = optotypeMapper.getLevelId();
				break;
			}
		}
		return ResultVOUtil.success(levelId);
	}

}
