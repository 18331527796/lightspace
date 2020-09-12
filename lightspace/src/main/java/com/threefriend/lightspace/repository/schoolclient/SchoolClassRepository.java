package com.threefriend.lightspace.repository.schoolclient;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.schoolclient.SchoolClassesMapper;

public interface SchoolClassRepository extends JpaRepository<SchoolClassesMapper, Integer>{

	List<SchoolClassesMapper> findBySemesterId(Integer semesterId);
	
	SchoolClassesMapper findBySemesterIdAndClassId(Integer semesterId,Integer classId);
	
	List<SchoolClassesMapper> findBySemesterIdAndGradeOrderByClassNumber(Integer semesterId , Integer grade);
	
	
	@Transactional
	void deleteBySemesterId(Integer semesterId);
}
