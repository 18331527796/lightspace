package com.threefriend.lightspace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.SchoolMapper;

/**
 * 学校操作层
 * @author Administrator
 *
 */
public interface SchoolRepository extends JpaRepository<SchoolMapper, Integer>{

	List<SchoolMapper> findByNameLike(String name);
}
