package com.threefriend.lightspace.repository.schoolclient;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.schoolclient.SchoolRoleRightRelation;

/**
 * 角色权限操作层
 * 
 * @author Administrator
 *
 */
public interface SchoolRoleRightRepository extends JpaRepository<SchoolRoleRightRelation, Integer>{

	List<SchoolRoleRightRelation> findByRoleId(Integer roleid);
	@Transactional
	void deleteByRoleId(Integer roleid);
}
