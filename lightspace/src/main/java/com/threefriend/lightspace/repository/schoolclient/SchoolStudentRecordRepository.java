package com.threefriend.lightspace.repository.schoolclient;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.schoolclient.SchoolStudentRecordMapper;

public interface SchoolStudentRecordRepository extends JpaRepository<SchoolStudentRecordMapper,Integer>{

	List<SchoolStudentRecordMapper> findByClassIdAndSemester(Integer classId ,Integer semester);
	
	List<SchoolStudentRecordMapper> findByClassIdAndSemesterIn(Integer classId ,List<Integer> semester);
	
	List<SchoolStudentRecordMapper> findBySemesterAndClassIdIn(Integer semester,List<Integer> classId );
	
	List<SchoolStudentRecordMapper> findBySemesterInAndClassIdIn(List<Integer> semester,List<Integer> classId );
	
	int countByClassIdAndSemester(Integer classId ,Integer semester);
	
	List<SchoolStudentRecordMapper> findBySchoolIdAndSemester(Integer schoolId ,Integer semester);
	
	@Transactional
	void deleteBySchoolIdAndSemester(Integer schoolId , Integer semesterId);
}
