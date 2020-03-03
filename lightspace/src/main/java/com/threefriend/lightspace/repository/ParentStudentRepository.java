package com.threefriend.lightspace.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.IntegralMapper;
import com.threefriend.lightspace.mapper.ParentStudentRelation;
import com.threefriend.lightspace.mapper.SysLogMapper;

/**
 * 家长孩子层
 * @author Administrator
 *
 */
public interface ParentStudentRepository extends JpaRepository<ParentStudentRelation, Integer>{

	List<ParentStudentRelation> findByParentId(Integer parentId);
}
