package com.threefriend.lightspace.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.xcx.OrderMapper;

public interface OrderRepository extends JpaRepository<OrderMapper,Integer>{

	Page<OrderMapper> findByStudentIdAndDisplayOrderByIdDesc(Integer studentId,Integer display , Pageable Pageable);
	
	Page<OrderMapper> findByStatusOrderByIdDesc(String  status,Pageable Pageable);
}
