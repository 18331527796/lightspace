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
			if(!StringUtils.isEmpty(params.get("first")))msg.setFirst(params.get("first"));
			if(!StringUtils.isEmpty(params.get("remark")))msg.setRemark(params.get("remark"));
			if(!StringUtils.isEmpty(params.get("url")))msg.setUrl(params.get("url"));
			if(Integer.valueOf(params.get("selected"))==1)selectedTemp(msg.getType(),msg.getId());
			msg_temp_dao.save(msg);
		}
		return tempList(params);
	}

	@Override
	public void selectedTemp(String type , Integer id) {
		List<MsgTempMapper> findByType = msg_temp_dao.findByType(type);
		for (MsgTempMapper msgTemp : findByType) {
			if(msgTemp.getId()==id) {
				msgTemp.setSelected(1);
			}else {
				msgTemp.setSelected(0);
			}
			msg_temp_dao.save(msgTemp);
		}
	}
}
