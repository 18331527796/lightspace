package com.threefriend.lightspace.xcx.service.Impl;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.enums.TaskExamineStatesEnum;
import com.threefriend.lightspace.enums.UrlEnums;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.mapper.TaskExamineConfigMapper;
import com.threefriend.lightspace.mapper.TaskExamineMapper;
import com.threefriend.lightspace.mapper.xcx.FabulousRecordMapper;
import com.threefriend.lightspace.mapper.xcx.FlowerRecordMapper;
import com.threefriend.lightspace.mapper.xcx.IntegralMapper;
import com.threefriend.lightspace.mapper.xcx.ParentMapper;
import com.threefriend.lightspace.repository.FabulousRcordRepository;
import com.threefriend.lightspace.repository.FlowerRcordRepository;
import com.threefriend.lightspace.repository.IntegralRepository;
import com.threefriend.lightspace.repository.ParentRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.repository.TaskExamineConfigRepository;
import com.threefriend.lightspace.repository.TaskExamineRepository;
import com.threefriend.lightspace.util.ImguploadUtils;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.FabulousRecordVO;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.vo.TaskExamineVO;
import com.threefriend.lightspace.xcx.service.TaskExamineXcxService;
/**
 * 爱眼秀实现类
 * @author Administrator
 *
 */
@Service
public class TaskExamineXcxServiceImpl implements TaskExamineXcxService{

	@Autowired
	private TaskExamineRepository task_examine_dao;
	@Autowired
	private StudentRepository student_dao;
	@Autowired
	private ParentRepository parent_dao;
	@Autowired
	private FabulousRcordRepository fabulous_record_dao;
	@Autowired
	private IntegralRepository integral_dao;
	@Autowired
	private FlowerRcordRepository flower_dao;
	@Autowired
	private TaskExamineConfigRepository config_dao;
	
	/* 
	 * 发布爱眼秀
	 */
	@Override
	public ResultVO addTaskPic(Map<String, String> params, MultipartFile[] file) throws Exception {
		if(!StringUtils.isEmpty(params.get("id"))) {
			String path = ImguploadUtils.uploadImg(file, "TaskPictures");
			TaskExamineMapper taskExamineMapper = task_examine_dao.findById(Integer.valueOf(params.get("id"))).get();
			if(StringUtils.isEmpty(taskExamineMapper.getPath())) {
				taskExamineMapper.setPath(path);
			}else {
				taskExamineMapper.setPath(taskExamineMapper.getPath()+","+path);
			}
			task_examine_dao.save(taskExamineMapper);
			return ResultVOUtil.success(taskExamineMapper.getId());
		}
		System.out.println(params.get("contents"));
		Integer studentId = Integer.valueOf(params.get("studentId"));
		ParentMapper parent = parent_dao.findByOpenId(params.get("openId"));
		StudentMapper student = student_dao.findById(studentId).get();
		TaskExamineMapper TaskExamine = new TaskExamineMapper();
		TaskExamine.setAvatarUrl(params.get("avatarUrl"));
		TaskExamine.setNickName(params.get("nickName"));
		TaskExamine.setParentId(parent.getId());
		TaskExamine.setContents(params.get("contents"));
		TaskExamine.setClassId(student.getClassesId());
		TaskExamine.setClassName(student.getClassesName());
		TaskExamine.setExamineStatus(TaskExamineStatesEnum.NULL.getCode());
		TaskExamine.setGenTime(new Date());
		TaskExamine.setSchoolId(student.getSchoolId());
		TaskExamine.setSchoolName(student.getSchoolName());
		TaskExamine.setStudentId(studentId);
		TaskExamine.setStudentName(student.getName());
		task_examine_dao.save(TaskExamine);
		Thread.currentThread().sleep(200);
		return ResultVOUtil.success(TaskExamine.getId());
	}

	/* 
	 * 爱眼秀首页列表
	 */
	@Override
	public ResultVO momentsList(Map<String, String> params) {
		Map<String, Object> end = new HashMap<String, Object>();
		Map<String, String > msg = new HashMap<>();
		List<TaskExamineVO> list = new ArrayList<>();
		int page = 0 ; 
		if(!StringUtils.isEmpty(params.get("page"))) page = Integer.valueOf(params.get("page")) - 1 ; 
		Integer parentId = parent_dao.findByOpenId(params.get("openId")).getId();
		
		List<FabulousRecordMapper> fabulousRecord = fabulous_record_dao.findBySendIdAndSendType(parentId, 2);

		List<TaskExamineMapper> content = task_examine_dao.findByExamineStatus(TaskExamineStatesEnum.EXAMINE.getCode(),PageRequest.of(page, 5 ,Sort.by("id").descending() )).getContent();
		
		for (TaskExamineMapper taskExamineMapper : content) {
			List<FabulousRecordMapper> FabulousRecord = fabulous_record_dao.findByParentIdAndTaskExamineIdOrderByIdDesc(parentId, taskExamineMapper.getId());
			List<FabulousRecordMapper>  fabulousLists= fabulous_record_dao.findByTaskExamineIdOrderByIdDesc(taskExamineMapper.getId());
			TaskExamineVO vo = new TaskExamineVO(taskExamineMapper);
			for (FabulousRecordMapper fabulousList : fabulousLists) {
				vo.getFabulousList().add(fabulousList.getNickName());
			}
			if(FabulousRecord.size()==0) {
				vo.setIsFabulous(2);
			}else {
				vo.setIsFabulous(1);
			}
			list.add(vo);
		}
		
		msg.put("value", fabulousRecord.size()+"");
		if(fabulousRecord.size()!=0)
		msg.put("avatarUrl", fabulousRecord.get(0).getAvatarUrl());
		
		end.put("list", list);
		end.put("newMessage", msg);
		return ResultVOUtil.success(end);
	}

	/* 
	 * 我的爱眼秀列表
	 */
	@Override
	public ResultVO myMomentsList(Map<String, String> params) {
		List<TaskExamineVO> end = new ArrayList<>();
		Integer parentId = parent_dao.findByOpenId(params.get("openId")).getId();
		int page = 0 ; 
		if(!StringUtils.isEmpty(params.get("page"))) page = Integer.valueOf(params.get("page")) - 1 ;
		List<TaskExamineMapper> content = task_examine_dao.findByParentId(parentId,PageRequest.of(page, 5 ,Sort.by("id").descending())).getContent();
		for (TaskExamineMapper taskExamineMapper : content) {
			List<FabulousRecordMapper> FabulousRecord = fabulous_record_dao.findByParentIdAndTaskExamineIdOrderByIdDesc(parentId, taskExamineMapper.getId());
			List<FabulousRecordMapper>  fabulousLists= fabulous_record_dao.findByTaskExamineIdOrderByIdDesc(taskExamineMapper.getId());
			TaskExamineVO vo = new TaskExamineVO(taskExamineMapper);
			for (FabulousRecordMapper fabulousList : fabulousLists) {
				vo.getFabulousList().add(fabulousList.getNickName());
			}
			if(FabulousRecord.size()==0) {
				vo.setIsFabulous(2);
			}else {
				vo.setIsFabulous(1);
			}
			end.add(vo);
		}
		return ResultVOUtil.success(end);
	}

	/* 
	 * 点赞
	 */
	@Override
	public synchronized ResultVO fabulous(Map<String, String> params) {
		TaskExamineMapper taskExamineMapper = task_examine_dao.findById(Integer.valueOf(params.get("id"))).get();
		ParentMapper parent = parent_dao.findByOpenId(params.get("openId"));
		//查看这个用户 有没有对这条 点过赞
		List<FabulousRecordMapper> FabulousRecord = fabulous_record_dao.findByParentIdAndTaskExamineIdOrderByIdDesc(parent.getId(), taskExamineMapper.getId());
		//点过返回提示
		if(FabulousRecord.size()!=0)return ResultVOUtil.error(ResultEnum.TASK_FABULOUS_ERROR.getStatus(), ResultEnum.TASK_FABULOUS_ERROR.getMessage());
		//没点过 向下进行 这条的点赞数量加1
		taskExamineMapper.setFabulous((taskExamineMapper.getFabulous()==null?1:taskExamineMapper.getFabulous())+1);
		task_examine_dao.save(taskExamineMapper);
		//判断这条点赞的数量是不是可以奖励 爱眼币      
		if(taskExamineMapper.getFabulous()>1&&taskExamineMapper.getFabulous()%10==0) {
			IntegralMapper integralMapper = new IntegralMapper(taskExamineMapper.getStudentId(), 1, Long.valueOf(1), "爱眼秀点赞奖励", new Date());
			integral_dao.save(integralMapper);
		}
		//新增记录用户的点赞行为 匹配用户和这一条
		FabulousRecordMapper po = new FabulousRecordMapper();
		po.setParentId(parent.getId());
		po.setTaskExamineId(taskExamineMapper.getId());
		po.setAvatarUrl(params.get("avatarUrl"));
		po.setDate(new Date());
		po.setNickName(params.get("nickName"));
		po.setSendId(taskExamineMapper.getParentId());
		po.setSendType(2);
		fabulous_record_dao.save(po);
		//po to vo
		TaskExamineVO vo = new TaskExamineVO(taskExamineMapper);
		List<FabulousRecordMapper>  fabulousLists= fabulous_record_dao.findByTaskExamineIdOrderByIdDesc(taskExamineMapper.getId());
		for (FabulousRecordMapper fabulousList : fabulousLists) {
			vo.getFabulousList().add(fabulousList.getNickName());
		}
		vo.setIsFabulous(1);
		return ResultVOUtil.success(vo);
	}

	/* 
	 * 送花
	 */
	@Override
	public ResultVO flowers(Map<String, String> params) throws ParseException {
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date begin = simpleDateFormat.parse(DateFormatUtils.format(new Date(), "yyyy-MM-dd 00:00:00"));
		Date end = simpleDateFormat.parse(DateFormatUtils.format(new Date(), "yyyy-MM-dd 23:59:59"));
		ParentMapper parent = parent_dao.findByOpenId(params.get("openId"));
		TaskExamineMapper taskExamineMapper = task_examine_dao.findById(Integer.valueOf(params.get("id"))).get();
		//查找这个用户的送花限制记录
		FlowerRecordMapper FlowerRecord = flower_dao.findByParentId(parent.getId());
		//如果这个用户的 记录不为空  时间符合今天内 没有余下次数      送她个提示 今天次数用光
		if(FlowerRecord!=null&&FlowerRecord.getGenTime().after(begin)&&FlowerRecord.getGenTime().before(end)&&FlowerRecord.getFrequency()<1){
			return ResultVOUtil.error(ResultEnum.TASK_FLOWERS_ERROR.getStatus(), ResultEnum.TASK_FLOWERS_ERROR.getMessage());
		}
		//如果没有记录   新建
		if(FlowerRecord==null) {
			FlowerRecordMapper po = new FlowerRecordMapper();
			po.setFrequency(2);
			po.setGenTime(new Date());
			po.setParentId(parent.getId());
			flower_dao.save(po);
		}else {
			//有记录 但不在今天内 重置时间 重置次数
			if(FlowerRecord.getGenTime().before(begin)) {
				FlowerRecord.setFrequency(2);
				FlowerRecord.setGenTime(new Date());
			}else {
			//有记录 在今天内 次数减1	
				FlowerRecord.setFrequency(FlowerRecord.getFrequency()-1);
			}
			flower_dao.save(FlowerRecord);
		}
		//这条爱眼秀的花朵数量加1
		taskExamineMapper.setFlower((taskExamineMapper.getFlower()==null?0:taskExamineMapper.getFlower())+1);
		task_examine_dao.save(taskExamineMapper);
		//查一下用户的点赞 控制页面内点赞的状态
		List<FabulousRecordMapper> FabulousRecord = fabulous_record_dao.findByParentIdAndTaskExamineIdOrderByIdDesc(parent.getId(), taskExamineMapper.getId());
		//po to vo
		TaskExamineVO vo = new TaskExamineVO(taskExamineMapper);
		if(FabulousRecord.size()==0) {
			vo.setIsFabulous(2);
		}else {
			vo.setIsFabulous(1);
		}
		
		return ResultVOUtil.success(vo);
	}

	/* 
	 * 爱眼秀首页图片更改
	 */
	@Override
	public ResultVO configPic() {
		TaskExamineConfigMapper po = config_dao.findByStatus(1);
		return ResultVOUtil.success(UrlEnums.IMG_URL.getUrl()+po.getPath());
	}

	/* 
	 * 删除爱眼秀
	 */
	@Override
	public ResultVO deleteMyMoments(Map<String, String> params) {
		TaskExamineMapper po = task_examine_dao.findById(Integer.valueOf(params.get("id"))).get();
		if(!StringUtils.isEmpty(po.getPath())) {
			if(po.getPath().contains(",")) {
				String[] split = po.getPath().split(",");
				for (String string : split) {
					File file = new File(UrlEnums.TOMCAT_IMG.getUrl()+"\\"+string);
					file.delete();
				}
			}else {
				File file = new File(UrlEnums.TOMCAT_IMG.getUrl()+"\\"+po.getPath());
				file.delete();
			}
		}	
		task_examine_dao.delete(po);
		return ResultVOUtil.success();
	}

	/* 
	 * 爱眼秀点赞的新消息列表
	 */
	@Override
	public ResultVO allFabulousMsg(Map<String, String> params) {
		List<FabulousRecordVO> end = new ArrayList<>();
		Integer parentId = parent_dao.findByOpenId(params.get("openId")).getId();
		List<FabulousRecordMapper> allrecord = fabulous_record_dao.findBySendIdAndSendType(parentId, 2);
		for (FabulousRecordMapper po : allrecord) {
			po.setSendType(1);
			TaskExamineMapper taskExamineMapper = task_examine_dao.findById(po.getTaskExamineId()).get();
			FabulousRecordVO vo = new FabulousRecordVO();
			BeanUtils.copyProperties(po, vo);
			if(!StringUtils.isEmpty(taskExamineMapper.getPath())) {
				if(taskExamineMapper.getPath().contains(",")) {
					vo.setContent(UrlEnums.IMG_URL.getUrl()+taskExamineMapper.getPath().split(",")[0]);
				}else {
					vo.setContent(UrlEnums.IMG_URL.getUrl()+taskExamineMapper.getPath());
				}
			}else {
				vo.setContent(taskExamineMapper.getContents());
			}
			end.add(vo);
		}
		fabulous_record_dao.saveAll(allrecord);
		return ResultVOUtil.success(end);
	}

	
	/* 
	 * 单条的详细点赞信息
	 */
	@Override
	public ResultVO fabulousMsg(Map<String, String> params) {
		
		return null;
	}

}
