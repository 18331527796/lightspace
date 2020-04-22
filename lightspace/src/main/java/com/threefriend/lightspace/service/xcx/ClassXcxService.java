package com.threefriend.lightspace.service.xcx;

import java.util.List;
import java.util.Map;

import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.vo.SchoolVO;

public interface ClassXcxService {
	
	//按照学校查询班级
	ResultVO findBySchoolId(Map<String, String> params);
}
