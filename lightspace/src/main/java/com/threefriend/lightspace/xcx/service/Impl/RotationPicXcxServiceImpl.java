package com.threefriend.lightspace.xcx.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threefriend.lightspace.repository.RotationPicRepository;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.RotationPicXcxService;

@Service
public class RotationPicXcxServiceImpl implements RotationPicXcxService{

	@Autowired
	private RotationPicRepository rotationpic_dao;

	@Override
	public ResultVO rotationPic() {
		return ResultVOUtil.success(rotationpic_dao.findAll());
	}
	
	
}
