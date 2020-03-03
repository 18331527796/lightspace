package com.threefriend.lightspace.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.IntegralMapper;
import com.threefriend.lightspace.mapper.SysLogMapper;

/**
 * 积分管理层
 * @author Administrator
 *
 */
public interface IntegralRepository extends JpaRepository<IntegralMapper, Integer>{

	List<IntegralMapper> findByParentIdOrderByGenTime(Integer parentId);
}
