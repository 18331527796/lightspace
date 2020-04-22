package com.threefriend.lightspace.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.SysLogMapper;
import com.threefriend.lightspace.mapper.TaskRecordMapper;
import com.threefriend.lightspace.mapper.xcx.IntegralMapper;
import com.threefriend.lightspace.mapper.xcx.MarkMapper;

/**
 * 每日签到层
 * @author Administrator
 *
 */
public interface MarkRepository extends JpaRepository<MarkMapper, Integer>{

	List<MarkMapper> findByParentId(Integer parentId);
}
