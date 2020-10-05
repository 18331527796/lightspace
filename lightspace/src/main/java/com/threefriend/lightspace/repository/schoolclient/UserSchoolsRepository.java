package com.threefriend.lightspace.repository.schoolclient;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.schoolclient.UserSchoolsMapper;

public interface UserSchoolsRepository extends JpaRepository<UserSchoolsMapper, Integer>{
	
	List<UserSchoolsMapper> findByUserId(Integer userId);
	
	UserSchoolsMapper findBySchoolIdAndUserId(Integer schoolId , Integer userId);
	@Transactional
	void deleteBySchoolIdAndUserId(Integer schoolId , Integer userId);
	
	@Transactional
	void deleteByUserId(Integer userId);
}
