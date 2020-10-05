package com.threefriend.priceclient.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.enums.UrlEnums;
import com.threefriend.lightspace.mapper.LabelMapper;
import com.threefriend.lightspace.mapper.PriceUserMapper;
import com.threefriend.lightspace.mapper.SeriesMapper;
import com.threefriend.lightspace.mapper.SeriesProductMapper;
import com.threefriend.lightspace.repository.LabelRepository;
import com.threefriend.lightspace.repository.PriceUserRepository;
import com.threefriend.lightspace.repository.SeriesProductRepository;
import com.threefriend.lightspace.repository.SeriesRepository;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.priceclient.service.PriceClientService;

@Service
public class PriceClientServiceImpl implements PriceClientService{

	@Autowired
	private LabelRepository label_dao;
	@Autowired
	private SeriesRepository series_dao;
	@Autowired
	private SeriesProductRepository product_dao;
	@Autowired
	private PriceUserRepository price_user_dao;
	
	@Override
	public ResultVO getAllLabel() {
		List<LabelMapper> end = label_dao.findAll();
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO getSeriesByLabel(Map<String, String> params) {
		List<SeriesMapper> end = series_dao.findByLabelId(Integer.valueOf(params.get("id")));
		for (SeriesMapper seriesMapper : end) {
			seriesMapper.setIntroduce(UrlEnums.IMG_URL.getUrl()+seriesMapper.getIntroduce());
		}
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO getProduceBySeries(Map<String, String> params) {
		List<SeriesProductMapper> end = product_dao.findBySeriesId(Integer.valueOf(params.get("id")));
		for (SeriesProductMapper seriesProductMapper : end) {
			seriesProductMapper.setPic(UrlEnums.IMG_URL.getUrl()+seriesProductMapper.getPic());
		}
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO loginPrice(Map<String, String> params) {
		String loginname = params.get("loginName");
		String password = params.get("password");
		PriceUserMapper user = price_user_dao.findByLoginNameAndPassword(loginname,password);
		if(user==null) return ResultVOUtil.error(ResultEnum.LOGIN_FAIL.getStatus(), ResultEnum.LOGIN_FAIL.getMessage());
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO changePassword(Map<String, String> params) {
		PriceUserMapper user = price_user_dao.findAll().get(0);
		String oldPassword = params.get("oldPassword");
		String newPassword = params.get("newPassword");
		if(!user.getPassword().equals(oldPassword)) return ResultVOUtil.error(ResultEnum.CREATEQRCORE_CHK.getStatus(), ResultEnum.CREATEQRCORE_CHK.getMessage());
		user.setPassword(newPassword);
		price_user_dao.save(user);
		return ResultVOUtil.success();
	}

}
