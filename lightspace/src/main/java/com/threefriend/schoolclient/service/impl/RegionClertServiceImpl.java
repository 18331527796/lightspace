package com.threefriend.schoolclient.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.mapper.ClertMapper;
import com.threefriend.lightspace.mapper.xcx.PartnershipMapper;
import com.threefriend.lightspace.repository.ClertRepository;
import com.threefriend.lightspace.repository.PartnershipRepository;
import com.threefriend.lightspace.service.ClertService;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;

/**
 *	店员业务逻辑实现
 */
@Service
public class RegionClertServiceImpl implements ClertService{
	
	@Autowired
	private ClertRepository clert_dao;
	@Autowired
	private PartnershipRepository partnership_dao;

	@Override
	public ResultVO clertList(Map<String, String> params,HttpSession session) {
		Integer regionId = Integer.valueOf(session.getAttribute("regionId").toString());
		int page = 0 ;
		if(!StringUtils.isEmpty(params.get("page")))page = Integer.valueOf(params.get("page")) - 1 ;
		String type = "region";
		if(!StringUtils.isEmpty(params.get("type")))type = params.get("type");
		Page<ClertMapper> findAll = null;
		List<Integer> partnershipIds =new ArrayList<>();
		if("partnership".equals(type)) {
			partnershipIds.add(Integer.valueOf(params.get("id")));
			findAll = clert_dao.findByPartnershipIdIn(partnershipIds,PageRequest.of(page, 10));
		}else {
			List<PartnershipMapper> ids = partnership_dao.findByRegionId(regionId);
			for (PartnershipMapper po : ids) {
				partnershipIds.add(po.getId());
			}
			findAll = clert_dao.findByPartnershipIdIn(partnershipIds,PageRequest.of(page, 10));
		}
		return ResultVOUtil.success(findAll);
	}

	@Override
	public ResultVO addClert(Map<String, String> params) {
		ClertMapper po = new ClertMapper();
		po.setGenTime(new Date());
		po.setName(params.get("name"));
		po.setLoginName(params.get("loginName"));
		po.setPassword(params.get("password"));
		po.setPartnershipId(Integer.valueOf(params.get("partnershipId")));
		po.setPartnershipName(partnership_dao.findById(Integer.valueOf(params.get("partnershipId"))).get().getName());
		clert_dao.save(po);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO findById(Map<String, String> params) {
		return ResultVOUtil.success(clert_dao.findById(Integer.valueOf(params.get("id"))).get());
	}

	@Override
	public ResultVO alertClert(Map<String, String> params) {
		ClertMapper clert = clert_dao.findById(Integer.valueOf(params.get("id"))).get();
		if(!StringUtils.isEmpty(params.get("name")))clert.setName(params.get("name"));
		if(!StringUtils.isEmpty(params.get("loginName")))clert.setLoginName(params.get("loginName"));
		if(!StringUtils.isEmpty(params.get("password")))clert.setPassword(params.get("password"));
		if(!StringUtils.isEmpty(params.get("partnershipId"))) {
			clert.setPartnershipId(Integer.valueOf(params.get("partnershipId")));
			clert.setPartnershipName(partnership_dao.findById(Integer.valueOf(params.get("partnershipId"))).get().getName());
		}
		clert_dao.save(clert);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO deleteClert(Map<String, String> params) {
		clert_dao.deleteById(Integer.valueOf(params.get("id")));
		return ResultVOUtil.success();
	}
	

}