package com.threefriend.lightspace.service.Impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.mapper.MsgTempMapper;
import com.threefriend.lightspace.repository.MsgTempRepository;
import com.threefriend.lightspace.service.MsgTempService;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;

@Service
public class MsgTempServiceImpl implements MsgTempService{

	@Autowired
	private MsgTempRepository msg_temp_dao;

	@Override
	public ResultVO tempList(Map<String, String> params) {
		return ResultVOUtil.success(msg_temp_dao.findByType(params.get("type")));
	}

	@Override
	public ResultVO editTemp(Map<String, String> params) {
		return ResultVOUtil.success(msg_temp_dao.findById(Integer.valueOf(params.get("id"))).get());
	}

	@Override
	public ResultVO saveTemp(Map<String, String> params) {
		Optional<MsgTempMapper> findById = msg_temp_dao.findById(Integer.valueOf(params.get("id")));
		if(findById.isPresent()) {
			MsgTempMapper msg = findById.get();
			if(!StringUtils.isEmpty(params.get("name")))msg.setName(params.get("name"));
			if(!StringUtils.isEmpty(params.get("first")))msg.setName(params.get("first"));
			if(!StringUtils.isEmpty(params.get("remark")))msg.setName(params.get("remark"));
			if(!StringUtils.isEmpty(params.get("url")))msg.setName(params.get("url"));
			msg_temp_dao.save(msg);
		}
		return tempList(params);
	}

	@Override
	public ResultVO selectedTemp(Map<String, String> params) {
		List<MsgTempMapper> findByType = msg_temp_dao.findByType(params.get("type"));
		for (MsgTempMapper msgTemp : findByType) {
			if(msgTemp.getId()==Integer.valueOf(params.get("id"))) {
				msgTemp.setSelected(1);
			}else {
				msgTemp.setSelected(0);
			}
			msg_temp_dao.save(msgTemp);
		}
		return tempList(params);
	}
}
