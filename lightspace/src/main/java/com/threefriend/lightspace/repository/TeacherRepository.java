package com.threefriend.lightspace.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.SysLogMapper;
import com.threefriend.lightspace.mapper.TeacherMapper;
import com.threefriend.lightspace.xcx.mapper.IntegralMapper;

/**
 * 教师层
 * @author Administrator
 *
 */
public interface TeacherRepository extends JpaRepository<TeacherMapper, Integer>{

}
