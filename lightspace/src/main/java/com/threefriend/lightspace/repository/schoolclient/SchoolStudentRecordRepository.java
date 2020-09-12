package com.threefriend.lightspace.repository.schoolclient;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.schoolclient.SchoolStudentRecordMapper;

public interface SchoolStudentRecordRepository extends JpaRepository<SchoolStudentRecordMapper,Integer>{

	List<SchoolStudentRecordMapper> findByClassIdAndSemester(Integer classId ,Integer semester);
	
	int countByClassIdAndSemester(Integer classId ,Integer semester);
	
	List<SchoolStudentRecordMapper> findBySemesterAndClassIdIn(Integer semester,List<Integer> classId );
	
	List<SchoolStudentRecordMapper> findBySchoolIdAndSemester(Integer schoolId ,Integer semester);
}
