package com.threefriend.lightspace.xcx.service.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.mapper.RegionMapper;
import com.threefriend.lightspace.repository.RegionRepository;
import com.threefriend.lightspace.service.RegionService;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;

@Service
public class RegionServiceImpl implements RegionService{

	@Autowired
	private RegionRepository region_dao;
	
	@Override
	public ResultVO findRegion(Map<String, String> params) {
		
		System.out.println(params.get("type")+"-------"+params.get("pId"));
		List<RegionMapper> end = null;
		Integer type = 1 ;
		if(!StringUtils.isEmpty(params.get("type"))) type = Integer.valueOf(params.get("type")) ;
		if(type == 1) {
			end = region_dao.findByParentidOrderByVieworder(0);
		}else {
			end = region_dao.findByParentidOrderByVieworder(Integer.valueOf(params.get("pId")));
		}
		return ResultVOUtil.success(end);
	}

}
