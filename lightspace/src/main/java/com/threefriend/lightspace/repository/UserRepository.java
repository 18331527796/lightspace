package com.threefriend.lightspace.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.UserMapper;
import java.lang.String;
import java.util.List;

/**
 * 用户操作层
 * @author Administrator
 *
 */
public interface UserRepository extends JpaRepository<UserMapper, Integer>{
	
	Page<UserMapper> findByRegionId(Pageable page , Integer regionId);

	List<UserMapper> findByloginNameAndPassword(String loginname, String password);

	List<UserMapper> findByLoginName(String loginname);
	
	List<UserMapper> findByNameLike(String name);
	
	Page<UserMapper> findBySchoolIdIn(List<Integer> ids,Pageable page);
	
}