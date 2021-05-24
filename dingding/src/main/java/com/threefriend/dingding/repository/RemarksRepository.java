package com.threefriend.dingding.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.dingding.mapper.RemarksMapper;

public interface RemarksRepository extends JpaRepository<RemarksMapper, Integer>{

	RemarksMapper findByUserIdAndTimeBetween(String userId , Date begin ,Date end);
}
