package com.threefriend.lightspace.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.ClertMapper;


public interface ClertRepository extends JpaRepository<ClertMapper, Integer>{

	List<ClertMapper> findByLoginNameAndPassword(String loginname,String password);
	
	ClertMapper findByParentId(Integer parentId);
}
