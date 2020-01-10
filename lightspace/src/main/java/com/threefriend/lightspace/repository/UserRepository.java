package com.threefriend.lightspace.repository;

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

	List<UserMapper> findByloginName(String loginname);

}