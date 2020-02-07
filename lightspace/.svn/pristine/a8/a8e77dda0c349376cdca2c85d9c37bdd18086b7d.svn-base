package com.threefriend.lightspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.UserRoleRelation;
import java.lang.Integer;
import java.util.List;

import javax.transaction.Transactional;

/**
 * 用户角色操作层
 * @author Administrator
 *
 */
public interface UserRoleRepository extends JpaRepository<UserRoleRelation, Integer>{
	
	List<UserRoleRelation> findByUserId(Integer uid);
	@Transactional
	void deleteByUserId(Integer uid);
}
