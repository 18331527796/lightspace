package com.threefriend.lightspace.xcx.service.Impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.threefriend.lightspace.mapper.xcx.SpecificationsMapper;
import com.threefriend.lightspace.repository.SpecificationsRepository;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.SpecificationsXcxService;

@Service
public class SpecificationsXcxServiceImpl implements SpecificationsXcxService{

	@Autowired
	private SpecificationsRepository Specifications_dao;
	
	@Override
	public ResultVO SpecificationsList(Map<String, String> params) {
		Page<SpecificationsMapper> allSpecifications = Specifications_dao.findByProductId(Integer.valueOf(params.get("id")), PageRequest.of(0, 10));
		return ResultVOUtil.success(allSpecifications.getContent());
	}

}
