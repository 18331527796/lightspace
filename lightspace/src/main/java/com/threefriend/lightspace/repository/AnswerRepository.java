package com.threefriend.lightspace.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.threefriend.lightspace.mapper.xcx.AnswerMapper;

public interface AnswerRepository extends JpaRepository<AnswerMapper, Integer>{

	Page<AnswerMapper> findByType(Integer type , Pageable page);
	
	@Query(value="SELECT * FROM answer_mapper  WHERE level = ?1 order by rand() limit ?2",nativeQuery = true)
	List<AnswerMapper> findByLevel(Integer level , Integer page );
	
	int countByLevel(Integer level);
}
