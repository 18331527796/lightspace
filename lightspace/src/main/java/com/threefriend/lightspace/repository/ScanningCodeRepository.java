package com.threefriend.lightspace.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.threefriend.lightspace.mapper.xcx.ScanningCodeMapper;



public interface ScanningCodeRepository extends JpaRepository<ScanningCodeMapper, Integer>{

	Page<ScanningCodeMapper> findByClertId(Integer clertId,Pageable page);
	
	ScanningCodeMapper findByOrderId(Integer orderId);
}
