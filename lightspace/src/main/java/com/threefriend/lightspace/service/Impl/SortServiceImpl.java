package com.threefriend.lightspace.service.Impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hibernate.query.criteria.internal.expression.function.AggregationFunction.AVG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threefriend.lightspace.constant.SortEnums;
import com.threefriend.lightspace.mapper.RecordMapper;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.repository.RecordRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.service.SortService;
import com.threefriend.lightspace.vo.SortVO;
/**
 * 
 * 排座的实现类
 *
 */
@Service
public class SortServiceImpl implements SortService{

	@Autowired
	private RecordRepository record_dao;
	@Autowired
	private StudentRepository student_dao;
	
	
	
	
	/* 
	 * 学生排座
	 */
	@Override
	public List<SortVO> studentSort(Integer classId,Integer type) {
		Integer number= 0;
		//根据不同的type来确定一行多少人 在进行下列的数据处理
		if(type==SortEnums.TYPEONE.getType())number=SortEnums.TYPEONE.getNumber();
		if(type==SortEnums.TYPETWO.getType())number=SortEnums.TYPETWO.getNumber();
		if(type==SortEnums.TYPETHREE.getType())number=SortEnums.TYPETHREE.getNumber();
		if(type==SortEnums.TYPEFOUR.getType())number=SortEnums.TYPEFOUR.getNumber();
		//接收按照身高排序后的
		List<SortVO> sort = new ArrayList<>();
		//临时行的排序
		List<SortVO> temporary = new ArrayList<>();
		//排座完成后的
		List<SortVO> end = new ArrayList<>();
		//按照班级查出来的身高表
		List<Integer> ids = new ArrayList<>();
		List<StudentMapper> allStudent = student_dao.findByClassesIdOrderBySittingHeight(classId);
		for (StudentMapper studentMapper : allStudent) {
			ids.add(studentMapper.getId());
		}
		for (Integer id : ids) {
			SortVO vo =new SortVO();
			//拿出来每个人的最新数据
			RecordMapper top = record_dao.findTopByStudentIdOrderByGenTime(id);
			if(top==null)break;
			vo.setStudentId(top.getStudentId());
			vo.setStudentName(top.getStudentName());
			vo.setAvgRecord((top.getVisionLeft()+top.getVisionRight())/2);
			sort.add(vo);
		}
		int size = sort.size();
		for (int i = 0; i < size; i++) {
			temporary.add(sort.get(i));
			if(i%number==0||(((size-i)<number)&&i==(size-1))) {
				//lamdba表达式排序 按照裸眼视力的平均值
				Collections.sort(temporary, Comparator.comparing(SortVO::getAvgRecord).thenComparing(SortVO::getAvgRecord));
				//组合最后的list
				end.addAll(0, temporary);
				//清除临时的list 进入下一行存储
				temporary.clear();
			}
		}
		return end;
	}

	
}
