package com.threefriend.lightspace.xcx.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threefriend.lightspace.mapper.PartnershipMapper;
import com.threefriend.lightspace.repository.PartnershipRepository;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.PartnershipService;

@Service
public class PartnershipServiceImpl implements PartnershipService{
	@Autowired
	private PartnershipRepository partnership_dao;

	@Override
	public ResultVO partnershipList() {
		return ResultVOUtil.success(partnership_dao.findAll());
	}

}
