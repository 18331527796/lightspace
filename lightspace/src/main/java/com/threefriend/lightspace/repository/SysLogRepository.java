package com.threefriend.lightspace.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.SysLogMapper;

/**
 * 班级操作层
 * @author Administrator
 *
 */
public interface SysLogRepository extends JpaRepository<SysLogMapper, Integer>{

}
