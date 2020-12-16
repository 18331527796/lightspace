package com.threefriend.lightspace.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.threefriend.lightspace.mapper.ClassesMapper;
import com.threefriend.lightspace.mapper.SysLogMapper;
import com.threefriend.lightspace.mapper.TeacherMapper;
import com.threefriend.lightspace.mapper.xcx.IntegralMapper;

/**
 * 教师层
 * @author Administrator
 *
 */
public interface TeacherRepository extends JpaRepository<TeacherMapper, Integer>{

	List<TeacherMapper> findByPhoneAndPassword(String phone,String password);
	
	TeacherMapper findByParentId(Integer parentId);
	
	List<TeacherMapper> findAllByParentId(Integer parentId);
	
	List<TeacherMapper> findByNameLike(String name);
	
	List<TeacherMapper> findBySchoolIdAndNameLike(Integer schoolId ,String name);
	
	List<TeacherMapper> findByClassId(Integer classId);
	
	Page<TeacherMapper> findBySchoolId(Integer schoolId,Pageable page);
	
	Page<TeacherMapper> findByRegionId(Integer regionId,Pageable page);
}
