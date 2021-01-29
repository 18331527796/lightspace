package com.price.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.price.enums.ResultEnum;
import com.price.enums.UrlEnums;
import com.price.mapper.GlassesMapper;
import com.price.mapper.LabelMapper;
import com.price.mapper.PriceUserMapper;
import com.price.mapper.SeriesMapper;
import com.price.repository.GlassesRepository;
import com.price.repository.LabelRepository;
import com.price.repository.PriceUserRepository;
import com.price.repository.SeriesRepository;
import com.price.service.PriceService;
import com.price.util.ResultVOUtil;
import com.price.vo.ResultVO;


@Service
public class PriceServiceImpl implements PriceService{

	@Autowired
	private LabelRepository label_dao;
	@Autowired
	private SeriesRepository series_dao;
	@Autowired
	private PriceUserRepository price_user_dao;
	@Autowired
	private GlassesRepository glasses_dao;
	
	@Override
	public ResultVO getAllLabel() {
		List<LabelMapper> end = label_dao.findAll();
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO getSeriesByLabel(Map<String, String> params) {
		//按照标签查找所属的系列 返回
		List<SeriesMapper> end = series_dao.findByLabelId(Integer.valueOf(params.get("id")));
		for (SeriesMapper seriesMapper : end) {
			seriesMapper.setIntroduce(UrlEnums.IMG_URL.getUrl()+seriesMapper.getIntroduce());
			seriesMapper.setPicture(UrlEnums.IMG_URL.getUrl()+seriesMapper.getPicture());
		}
		return ResultVOUtil.success(end);
	}


	@Override
	public ResultVO loginPrice(Map<String, String> params) {
		String loginname = params.get("loginName");
		String password = params.get("password");
		System.out.println(loginname+"-----"+password);
		PriceUserMapper user = price_user_dao.findByLoginNameAndPassword(loginname,password);
		if(user==null) return ResultVOUtil.error(ResultEnum.LOGIN_FAIL.getStatus(), ResultEnum.LOGIN_FAIL.getMessage());
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO changePassword(Map<String, String> params) {
		PriceUserMapper user = price_user_dao.findAll().get(0);
		//老的密码
		String oldPassword = params.get("oldPassword");
		//新修改的密码
		String newPassword = params.get("newPassword");
		//老密码输入错误 返回不得修改
		if(!user.getPassword().equals(oldPassword)) return ResultVOUtil.error(ResultEnum.CREATEQRCORE_CHK.getStatus(), ResultEnum.CREATEQRCORE_CHK.getMessage());
		user.setPassword(newPassword);
		//修改成功保存
		price_user_dao.save(user);
		return ResultVOUtil.success();
	}


	@Override
	public ResultVO findGlassesBySeries(Map<String, String> params) {
		Integer seriesId = Integer.valueOf(params.get("seriesId"));
		List<GlassesMapper> poList = glasses_dao.findBySeriesIdOrderById(seriesId);
		for (GlassesMapper po : poList) {
			po.setPicture(UrlEnums.IMG_URL.getUrl()+po.getPicture());
			if(!StringUtils.isEmpty(po.getCustomFile()))
			po.setCustomFile(UrlEnums.IMG_URL.getUrl()+po.getCustomFile());
		}
		return ResultVOUtil.success(poList);
	}

	@Override
	public ResultVO contrastGlasses(Map<String, String> params) {
		List<GlassesMapper> end = new ArrayList<>();
		if(StringUtils.isEmpty(params.get("glasses"))) return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
		String[] split = params.get("glasses").split(",");
		for (String string : split) {
			GlassesMapper po = glasses_dao.findById(Integer.valueOf(string)).get();
			po.setPicture(UrlEnums.IMG_URL.getUrl()+po.getPicture());
			if(!StringUtils.isEmpty(po.getCustomFile()))
			po.setCustomFile(UrlEnums.IMG_URL.getUrl()+po.getCustomFile());
			end.add(po);
		}
		return ResultVOUtil.success(end);
	}
	
	

}
