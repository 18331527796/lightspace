package com.threefriend.lightspace.service.Impl.xcx;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.service.xcx.ClassXcxService;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;

@Service
public class ClassXcxServiceImpl implements ClassXcxService{

	@Autowired
	private ClassesRepository class_dao;
	@Override
	public ResultVO findBySchoolId(Map<String, String> params) {
		Integer schoolId = Integer.valueOf(params.get("id")); 
		return ResultVOUtil.success(class_dao.findBySchoolIdOrderByFinish(schoolId));
	}

}
