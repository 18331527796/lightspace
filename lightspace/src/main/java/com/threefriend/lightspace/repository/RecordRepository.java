package com.threefriend.lightspace.repository;


import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.w3c.dom.ls.LSInput;

import com.threefriend.lightspace.mapper.RecordMapper;

/**
 * 基础数据操作层
 * @author Administrator
 *
 */
public interface RecordRepository extends JpaRepository<RecordMapper, Integer>{
	
	List<RecordMapper> findAllByStudentNameLike(String name);
	
	List<RecordMapper> findByClassesId(Integer classId);
	
	List<RecordMapper> findBySchoolId(Integer schoolId);
	@Transactional
	void deleteBySchoolId(Integer sId);
	@Transactional
	void deleteByClassesId(Integer cId);
	@Transactional
	void deleteByStudentId(Integer Id);
	
	RecordMapper findTopByStudentIdOrderByGenTimeDesc(Integer id);
	//按照学生id查询所有的数据
	List<RecordMapper> findAllByStudentIdAndGenTimeBetweenOrderByGenTime(Integer id,Date befor , Date after);
	
	Page<RecordMapper> findAllByRegionIdOrderByIdDesc(Pageable Pageable,Integer regionId);
	
	Page<RecordMapper> findAllByOrderByIdDesc(Pageable Pageable);
	
	Page<RecordMapper> findAllByStudentNameLikeOrderByIdDesc(String name,Pageable Pageable);
	
	Page<RecordMapper> findByClassesIdOrderByIdDesc(Integer classId,Pageable Pageable);
	
	Page<RecordMapper> findBySchoolIdOrderByIdDesc(Integer schoolId,Pageable Pageable);
}
