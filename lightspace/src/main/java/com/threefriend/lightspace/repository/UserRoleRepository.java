package com.threefriend.lightspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.UserRoleRelation;
import java.lang.Integer;
import java.util.List;

/**
 * 用户角色操作层
 * @author Administrator
 *
 */
public interface UserRoleRepository extends JpaRepository<UserRoleRelation, Integer>{
	
	List<UserRoleRelation> findByUserId(Integer uid);
	
	void deleteByUserId(Integer uid);
}
