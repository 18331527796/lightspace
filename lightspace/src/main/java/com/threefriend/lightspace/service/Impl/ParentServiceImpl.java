package com.threefriend.lightspace.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threefriend.lightspace.mapper.xcx.ParentMapper;
import com.threefriend.lightspace.mapper.xcx.ParentStudentRelation;
import com.threefriend.lightspace.repository.ParentRepository;
import com.threefriend.lightspace.repository.ParentStudentRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.service.ParentService;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ParentVO;
import com.threefriend.lightspace.vo.ResultVO;

/**
 *	家长逻辑实现层
 */
@Service
public class ParentServiceImpl implements ParentService{
	@Autowired
	private ParentRepository parent_dao;
	@Autowired
	private StudentRepository student_dao;
	@Autowired
	private ParentStudentRepository p_s_dao;
	
	
	
	
	@Override
	public ResultVO parentList() {
		List<ParentMapper> All = parent_dao.findAll();
		List<ParentVO> end = new ArrayList<>();
		for (ParentMapper parentMapper : All) {
			ParentVO vo = new ParentVO();
			BeanUtils.copyProperties(parentMapper, vo);
			List<ParentStudentRelation> findByParentId = p_s_dao.findByParentId(parentMapper.getId());
			for (ParentStudentRelation p_s : findByParentId) {
				vo.getStudent().add(student_dao.findById(p_s.getStudentId()).get());
			}
			end.add(vo);
		}
		return ResultVOUtil.success(end);
	}

}
