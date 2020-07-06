package com.threefriend.lightspace.service.Impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.enums.TaskExamineStatesEnum;
import com.threefriend.lightspace.enums.UrlEnums;
import com.threefriend.lightspace.mapper.TaskExamineConfigMapper;
import com.threefriend.lightspace.mapper.TaskExamineMapper;
import com.threefriend.lightspace.mapper.xcx.IntegralMapper;
import com.threefriend.lightspace.repository.IntegralRepository;
import com.threefriend.lightspace.repository.TaskExamineConfigRepository;
import com.threefriend.lightspace.repository.TaskExamineRepository;
import com.threefriend.lightspace.service.TaskExamineService;
import com.threefriend.lightspace.util.ImguploadUtils;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.vo.TaskExamineVO;

@Service
public class TaskExamineServiceImpl implements TaskExamineService{
	@Autowired
	private TaskExamineRepository task_examine_dao;
	@Autowired
	private IntegralRepository integral_dao;
	@Autowired
	private TaskExamineConfigRepository config_dao;

	@Override
	public ResultVO taskExamineList(Map<String, String> params) {
		List<TaskExamineVO> list = new ArrayList<>();
		int page = 0;
		String type = params.get("type"); 
		if(!StringUtils.isEmpty(params.get("page")))page = Integer.valueOf(params.get("page")) - 1 ;
		if("NULL".equals(type)) {
			Page<TaskExamineMapper> all = task_examine_dao.findByExamineStatus(TaskExamineStatesEnum.NULL.getCode(),PageRequest.of(page, 10, Sort.by("id").descending()));
			for (TaskExamineMapper po : all.getContent()) {
				list.add(new TaskExamineVO(po));
			}
			return ResultVOUtil.success(new PageImpl<>(list, all.getPageable(), all.getTotalElements()));
		}else {
			Page<TaskExamineMapper> all = task_examine_dao.findByExamineStatusNot(TaskExamineStatesEnum.NULL.getCode(),PageRequest.of(page, 10, Sort.by("id").descending()));
			for (TaskExamineMapper po : all.getContent()) {
				list.add(new TaskExamineVO(po));
			}
			return ResultVOUtil.success(new PageImpl<>(list, all.getPageable(), all.getTotalElements()));
		}
	}

	@Override
	public ResultVO deleteTaskExamine(Map<String, String> params) {
		TaskExamineMapper po = task_examine_dao.findById(Integer.valueOf(params.get("id"))).get();
		String[] split = po.getPath().split(",");
		for (String string : split) {
			File file = new File(UrlEnums.TOMCAT_IMG.getUrl()+"\\"+string);
			file.delete();
		}
		task_examine_dao.delete(po);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO examineTask(Map<String, String> params) {
		Long integral = 0l;
		if(!StringUtils.isEmpty(params.get("integral")))integral = Long.valueOf(params.get("integral")) ;
		TaskExamineMapper po = task_examine_dao.findById(Integer.valueOf(params.get("id"))).get();
		if(integral != 0) {
			po.setExamineStatus(TaskExamineStatesEnum.EXAMINE.getCode());
			IntegralMapper integralpo = new IntegralMapper(po.getStudentId(), 1, integral, "优秀爱眼秀奖励", new Date());
			integral_dao.save(integralpo);
		}else {
			po.setExamineStatus(TaskExamineStatesEnum.UNEXAMINE.getCode());
		}
		task_examine_dao.save(po);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO addMomentsConfig(Map<String, String> params, MultipartFile file) {
		String path = ImguploadUtils.uploadImg(file, "moments");
		TaskExamineConfigMapper po = new TaskExamineConfigMapper();
		if(!StringUtils.isEmpty(params.get("details")))po.setDetails(params.get("details"));
		po.setPath(path);
		po.setStatus(2);
		config_dao.save(po);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO momentsConfigList(Map<String, String> params) {
		int page = 0 ; 
		if(!StringUtils.isEmpty(params.get("page"))) page = Integer.valueOf(params.get("page")) - 1 ;
		Page<TaskExamineConfigMapper> findAll = config_dao.findAll(PageRequest.of(page, 10, Sort.by("id").descending()));
		for (TaskExamineConfigMapper po : findAll.getContent()) {
			po.setPath(UrlEnums.IMG_URL.getUrl()+po.getPath());
		}
		return ResultVOUtil.success(findAll);
	}

	@Override
	public ResultVO deleteMomentsConfig(Map<String, String> params) {
		TaskExamineConfigMapper po = config_dao.findById(Integer.valueOf(params.get("id"))).get();
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
		config_dao.delete(po);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO changeMomentsConfigDisplay(Map<String, String> params) {
		Integer id = Integer.valueOf(params.get("id"));
		List<TaskExamineConfigMapper> findAll = config_dao.findAll();
		for (TaskExamineConfigMapper po : findAll) {
			if(po.getId()==id) {
				po.setStatus(1);
			}else {
				po.setStatus(2);
			}
		}
		config_dao.saveAll(findAll);
		return ResultVOUtil.success();
	}

}
