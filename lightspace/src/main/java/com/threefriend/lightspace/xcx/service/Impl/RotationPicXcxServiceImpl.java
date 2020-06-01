package com.threefriend.lightspace.xcx.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threefriend.lightspace.enums.UrlEnums;
import com.threefriend.lightspace.mapper.RotationPicMapper;
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
		List<RotationPicMapper> findAll = rotationpic_dao.findAll();
		for (RotationPicMapper rotation : findAll) {
			rotation.setPath(UrlEnums.IMG_URL.getUrl()+rotation.getPath());
		}
		return ResultVOUtil.success(findAll);
	}
	
	
}
