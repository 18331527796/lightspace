package com.threefriend.lightspace.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.mapper.xcx.ScreeningMapper;
import com.threefriend.lightspace.mapper.xcx.ScreeningWearMapper;
import com.threefriend.lightspace.repository.ScreeningRepository;
import com.threefriend.lightspace.repository.ScreeningWearRepository;
import com.threefriend.lightspace.service.ScreeningService;
import com.threefriend.lightspace.util.ExcelUtil;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;

@Service
public class ScreeningServiceImpl implements ScreeningService{
	
	@Autowired
	private ScreeningRepository screening_dao;
	@Autowired
	private ScreeningWearRepository screening_wear_dao;
	
	@Override
	public ResultVO screeningList(Map<String, String> params) {
		Integer page = 0;
		if(!StringUtils.isEmpty(params.get("page")))page = Integer.valueOf(params.get("page"))-1;
		String type = params.get("type");
		if("student".equals(type)) {
			Page<ScreeningMapper> list = screening_dao.findByStudentIdOrderByGenTimeDesc(Integer.valueOf(params.get("id")),new PageRequest(page, 10));
			return (list!=null)?ResultVOUtil.success(list):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
		}
		if("class".equals(type)) {
			Page<ScreeningMapper> list = screening_dao.findByClassIdOrderByGenTimeDesc(Integer.valueOf(params.get("id")),new PageRequest(page, 10));
			return (list!=null)?ResultVOUtil.success(list):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
		}
		if("school".equals(type)) {
			Page<ScreeningMapper> list = screening_dao.findBySchoolIdOrderByGenTimeDesc(Integer.valueOf(params.get("id")),new PageRequest(page, 10));
			return (list!=null)?ResultVOUtil.success(list):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
		}
		Page<ScreeningMapper> list = screening_dao.findAllByOrderByGenTimeDesc(new PageRequest(page, 10));
		return (list!=null)?ResultVOUtil.success(list):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
	}

	@Override
	public ResultVO deleteScreening(Map<String, String> params) {
		String[] split = params.get("token").split("-");
		if(!"1".equals(split[1]))return ResultVOUtil.error(ResultEnum.RIGHT_ERROR.getStatus(), ResultEnum.RIGHT_ERROR.getMessage());
		ScreeningMapper po = screening_dao.findById(Integer.valueOf(params.get("id"))).get();
		screening_dao.delete(po);
		return screeningList(params);
	}
	
	@Override
	public ResultVO screeningWearList(Map<String, String> params) {
		Integer page = 0;
		if(!StringUtils.isEmpty(params.get("page")))page = Integer.valueOf(params.get("page"))-1;
		String type = params.get("type");
		if("student".equals(type)) {
			Page<ScreeningWearMapper> list = screening_wear_dao.findByStudentIdOrderByGenTimeDesc(Integer.valueOf(params.get("id")),new PageRequest(page, 10));
			return (list!=null)?ResultVOUtil.success(list):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
		}
		if("class".equals(type)) {
			Page<ScreeningWearMapper> list = screening_wear_dao.findByClassIdOrderByGenTimeDesc(Integer.valueOf(params.get("id")),new PageRequest(page, 10));
			return (list!=null)?ResultVOUtil.success(list):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
		}
		if("school".equals(type)) {
			Page<ScreeningWearMapper> list = screening_wear_dao.findBySchoolIdOrderByGenTimeDesc(Integer.valueOf(params.get("id")),new PageRequest(page, 10));
			return (list!=null)?ResultVOUtil.success(list):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
		}
		Page<ScreeningWearMapper> list = screening_wear_dao.findAllByOrderByGenTimeDesc(new PageRequest(page, 10));
		return (list!=null)?ResultVOUtil.success(list):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
	}

	@Override
	public ResultVO deleteScreeningWear(Map<String, String> params) {
		String[] split = params.get("token").split("-");
		if(!"1".equals(split[1]))return ResultVOUtil.error(ResultEnum.RIGHT_ERROR.getStatus(), ResultEnum.RIGHT_ERROR.getMessage());
		ScreeningWearMapper po = screening_wear_dao.findById(Integer.valueOf(params.get("id"))).get();
		screening_wear_dao.delete(po);
		return screeningWearList(params);
	}

	@Override
	public ResultVO screeningExcel(Map<String, String> params) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		String[] strArray = { "学校名称", "班级名称", "学生姓名", "右眼裸眼视力", "左眼裸眼视力" };
		String type = params.get("type");
		System.out.println("进来"+type+params.get("id"));
		List<ScreeningMapper> list = new ArrayList<>();
		if("student".equals(type)) {
			list = screening_dao.findByStudentIdOrderByGenTimeDesc(Integer.valueOf(params.get("id")));
		}else if("class".equals(type)) {
			list = screening_dao.findByClassIdOrderByGenTimeDesc(Integer.valueOf(params.get("id")));
		}else if("school".equals(type)) {
			list = screening_dao.findBySchoolIdOrderByGenTimeDesc(Integer.valueOf(params.get("id")));
		}else {
			list = screening_dao.findAllByOrderByGenTimeDesc();
		}
		for (int i = 0; i < list.size(); i++) {
            ArrayList<String> members = new ArrayList<String>();
            members.add(list.get(i).getSchoolName() + "");
            members.add(list.get(i).getClassName());
            members.add(list.get(i).getStudentName());
            members.add(list.get(i).getVisionRight());
            members.add(list.get(i).getVisionLeft());
            map.put(list.get(i).getId() + "", members);
        }
		ExcelUtil.createExcel(map, strArray);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO screeningWearExcel(Map<String, String> params) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		String[] strArray = { "学校名称", "班级名称", "学生姓名", "右眼裸眼视力", "左眼裸眼视力" };
		String type = params.get("type");
		System.out.println("进来"+type+params.get("id"));
		List<ScreeningWearMapper> list = new ArrayList<>();
		if("student".equals(type)) {
			list = screening_wear_dao.findByStudentIdOrderByGenTimeDesc(Integer.valueOf(params.get("id")));
		}else if("class".equals(type)) {
			list = screening_wear_dao.findByClassIdOrderByGenTimeDesc(Integer.valueOf(params.get("id")));
		}else if("school".equals(type)) {
			list = screening_wear_dao.findBySchoolIdOrderByGenTimeDesc(Integer.valueOf(params.get("id")));
		}else {
			list = screening_wear_dao.findAllByOrderByGenTimeDesc();
		}
		for (int i = 0; i < list.size(); i++) {
            ArrayList<String> members = new ArrayList<String>();
            members.add(list.get(i).getSchoolName() + "");
            members.add(list.get(i).getClassName());
            members.add(list.get(i).getStudentName());
            members.add(list.get(i).getVisionRight());
            members.add(list.get(i).getVisionLeft());
            map.put(list.get(i).getId() + "", members);
        }
		ExcelUtil.createExcel(map, strArray);
		return ResultVOUtil.success();
	}

	
}
