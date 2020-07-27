package com.threefriend.lightspace.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
	public ResultVO parentList(Map<String, String> params) {
		int page = 0 ;
		if(!StringUtils.isEmpty(params.get("page")))page = Integer.valueOf(params.get("page")) - 1 ;
		Page<ParentMapper> All = parent_dao.findAll(PageRequest.of(page, 10));
		List<ParentVO> end = new ArrayList<>();
		for (ParentMapper parentMapper : All.getContent()) {
			ParentVO vo = new ParentVO();
			BeanUtils.copyProperties(parentMapper, vo);
			List<ParentStudentRelation> findByParentId = p_s_dao.findByParentIdOrderByIdDesc(parentMapper.getId());
			for (ParentStudentRelation p_s : findByParentId) {
				vo.getStudent().add(student_dao.findById(p_s.getStudentId()).get());
			}
			end.add(vo);
		}
		Page<ParentVO> endlist = new PageImpl<>(end, All.getPageable(), All.getTotalElements());
		return ResultVOUtil.success(endlist);
	}

}
