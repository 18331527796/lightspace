package com.threefriend.priceclient.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.enums.UrlEnums;
import com.threefriend.lightspace.mapper.GlassesMapper;
import com.threefriend.lightspace.mapper.LabelMapper;
import com.threefriend.lightspace.mapper.PriceUserMapper;
import com.threefriend.lightspace.mapper.SeriesMapper;
import com.threefriend.lightspace.mapper.SeriesProductMapper;
import com.threefriend.lightspace.repository.GlassesRepository;
import com.threefriend.lightspace.repository.LabelRepository;
import com.threefriend.lightspace.repository.PriceUserRepository;
import com.threefriend.lightspace.repository.SeriesProductRepository;
import com.threefriend.lightspace.repository.SeriesRepository;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.priceclient.service.PriceService;

@Service
public class PriceServiceImpl implements PriceService{

	@Autowired
	private LabelRepository label_dao;
	@Autowired
	private SeriesRepository series_dao;
	@Autowired
	private SeriesProductRepository product_dao;
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
	public ResultVO getProduceBySeries(Map<String, String> params) {
		//查找详情图和产品价格图 整合到一起 返回
		List<SeriesProductMapper> end = product_dao.findBySeriesIdAndType(Integer.valueOf(params.get("id")),1);
		List<SeriesProductMapper> products = product_dao.findBySeriesIdAndType(Integer.valueOf(params.get("id")),2);
		end.addAll(products);
		for (SeriesProductMapper seriesProductMapper : end) {
			seriesProductMapper.setPic(UrlEnums.IMG_URL.getUrl()+seriesProductMapper.getPic());
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
	public ResultVO contrast(Map<String, String> params) {
		List<SeriesProductMapper> end = new ArrayList<>();
		//第一张对比图
		String[] split = params.get("one").split(",");
		for (String string : split) {
			//查找这个系列的产品价格图
			List<SeriesProductMapper> oneList = product_dao.findBySeriesIdAndType(Integer.valueOf(string),2);
			end.addAll(oneList);
		}
		for (SeriesProductMapper seriesProductMapper : end) {
			seriesProductMapper.setPic(UrlEnums.IMG_URL.getUrl()+seriesProductMapper.getPic());
		}
		return ResultVOUtil.success(end);
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
