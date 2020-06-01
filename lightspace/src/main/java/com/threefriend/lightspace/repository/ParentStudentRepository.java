package com.threefriend.lightspace.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.SysLogMapper;
import com.threefriend.lightspace.mapper.xcx.IntegralMapper;
import com.threefriend.lightspace.mapper.xcx.ParentStudentRelation;

/**
 * 家长孩子层
 * @author Administrator
 *
 */
public interface ParentStudentRepository extends JpaRepository<ParentStudentRelation, Integer>{

	List<ParentStudentRelation> findByParentId(Integer parentId);
	
	List<ParentStudentRelation> findByStudentId(Integer studentId);
	
	ParentStudentRelation findByStudentIdAndParentId(Integer studentId,Integer parentId);
	@Transactional
	void deleteByStudentIdAndParentId(Integer studentId,Integer parentId);
}
