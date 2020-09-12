package com.threefriend.lightspace.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.xcx.OrderMapper;

public interface OrderRepository extends JpaRepository<OrderMapper,Integer>{

	Page<OrderMapper> findByStudentIdAndDisplayOrderByGenTimeDateDesc(Integer studentId,Integer display , Pageable Pageable);
	
	Page<OrderMapper> findByStatusOrderByIdDesc(String  status,Pageable Pageable);
	
	List<OrderMapper> findByStudentId(Integer studentId);
	
	Page<OrderMapper> findByStudentIdAndStatus(Integer studentId,String  status,Pageable Pageable);
	
	int countByProductId(Integer productId);
}
