package com.threefriend.lightspace.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.threefriend.lightspace.mapper.xcx.AnswerMapper;
import com.threefriend.lightspace.mapper.xcx.FabulousRecordMapper;

public interface FabulousRcordRepository extends JpaRepository<FabulousRecordMapper, Integer>{

	List<FabulousRecordMapper> findByParentIdAndTaskExamineIdOrderByIdDesc(Integer parentId,Integer taskExamineId);
}
