package com.threefriend.lightspace.service.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.mapper.xcx.ScreeningMapper;
import com.threefriend.lightspace.mapper.xcx.ScreeningWearMapper;
import com.threefriend.lightspace.repository.ScreeningRepository;
import com.threefriend.lightspace.repository.ScreeningWearRepository;
import com.threefriend.lightspace.service.ScreeningService;
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
		String type = params.get("type");
		if("student".equals(type)) {
			List<ScreeningMapper> list = screening_dao.findByStudentIdOrderByGenTimeDesc(Integer.valueOf(params.get("id")));
			return (list.size()!=0)?ResultVOUtil.success(list):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
		}
		if("class".equals(type)) {
			List<ScreeningMapper> list = screening_dao.findByClassIdOrderByGenTimeDesc(Integer.valueOf(params.get("id")));
			return (list.size()!=0)?ResultVOUtil.success(list):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
		}
		if("school".equals(type)) {
			List<ScreeningMapper> list = screening_dao.findBySchoolIdOrderByGenTimeDesc(Integer.valueOf(params.get("id")));
			return (list.size()!=0)?ResultVOUtil.success(list):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
		}
		List<ScreeningMapper> list = screening_dao.findAllByOrderByGenTimeDesc();
		return (list.size()!=0)?ResultVOUtil.success(list):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
	}

	@Override
	public ResultVO deleteScreening(Map<String, String> params) {
		String[] split = params.get("token").split("-");
		if(!"1".equals(split[1]))return ResultVOUtil.error(ResultEnum.RIGHT_ERROR.getStatus(), ResultEnum.RIGHT_ERROR.getMessage());
		ScreeningMapper po = screening_dao.findById(Integer.valueOf(params.get("id"))).get();
		params.put("type", "student");
		params.put("id", po.getStudentId()+"");
		screening_dao.delete(po);
		return screeningList(params);
	}
	
	@Override
	public ResultVO screeningWearList(Map<String, String> params) {
		String type = params.get("type");
		if("student".equals(type)) {
			List<ScreeningWearMapper> list = screening_wear_dao.findByStudentIdOrderByGenTimeDesc(Integer.valueOf(params.get("id")));
			return (list.size()!=0)?ResultVOUtil.success(list):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
		}
		if("class".equals(type)) {
			List<ScreeningWearMapper> list = screening_wear_dao.findByClassIdOrderByGenTimeDesc(Integer.valueOf(params.get("id")));
			return (list.size()!=0)?ResultVOUtil.success(list):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
		}
		if("school".equals(type)) {
	        List<ScreeningWearMapper> list = screening_wear_dao.findBySchoolIdOrderByGenTimeDesc(Integer.valueOf(params.get("id")));
			return (list.size()!=0)?ResultVOUtil.success(list):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
		}
		List<ScreeningWearMapper> list = screening_wear_dao.findAllByOrderByGenTimeDesc();
		return (list.size()!=0)?ResultVOUtil.success(list):ResultVOUtil.error(ResultEnum.STUDENTSIZE_NULL.getStatus(), ResultEnum.STUDENTSIZE_NULL.getMessage());
	}

	@Override
	public ResultVO deleteScreeningWear(Map<String, String> params) {
		String[] split = params.get("token").split("-");
		if(!"1".equals(split[1]))return ResultVOUtil.error(ResultEnum.RIGHT_ERROR.getStatus(), ResultEnum.RIGHT_ERROR.getMessage());
		ScreeningWearMapper po = screening_wear_dao.findById(Integer.valueOf(params.get("id"))).get();
		params.put("type", "student");
		params.put("id", po.getStudentId()+"");
		screening_wear_dao.delete(po);
		return screeningWearList(params);
	}

	
}
