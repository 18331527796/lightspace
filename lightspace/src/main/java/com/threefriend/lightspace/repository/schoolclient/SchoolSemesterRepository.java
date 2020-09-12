package com.threefriend.lightspace.repository.schoolclient;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.threefriend.lightspace.mapper.schoolclient.SchoolSemesterMapper;

public interface SchoolSemesterRepository extends JpaRepository<SchoolSemesterMapper, Integer>{

	@Query(value="SELECT * from school_semester_mapper group by year",nativeQuery = true)
	List<SchoolSemesterMapper> findBySchool(Integer schoolId);
	
	SchoolSemesterMapper findByYearAndSemesterAndSchoolId(String year , Integer semester ,Integer schoolId);
}
