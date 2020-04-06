package com.threefriend.lightspace.xcx.service.Impl;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threefriend.lightspace.mapper.StudentWordMapper;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.repository.StudentWordRepository;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.vo.StudentWordVO;
import com.threefriend.lightspace.xcx.service.StudentXcxService;

@Service
public class StudentXcxServiceImpl implements StudentXcxService{

	@Autowired
	private StudentRepository student_dao;
	@Autowired
	private StudentWordRepository student_word_dao;

	@Override
	public ResultVO queryBySidCid(Map<String, String> params) {
		Integer schoolId = Integer.valueOf(params.get("schoolId"));
		Integer classId = Integer.valueOf(params.get("classId"));
		return ResultVOUtil.success(student_dao.findBySchoolIdAndClassesId(schoolId, classId));
	}

	@Override
	public ResultVO queryStudentWord(Map<String, String> params) {
		DateFormat Format = new SimpleDateFormat("yyyy-MM-dd");
		List<StudentWordMapper> words = student_word_dao.findByStudentIdOrderByGenTimeDesc(Integer.valueOf(params.get("studentId")));
		List<StudentWordVO> end = new ArrayList<StudentWordVO>();
		try {
			for (StudentWordMapper Word : words) {
				StudentWordVO vo = new StudentWordVO();
				BeanUtils.copyProperties(Word, vo);
				vo.setTime(Format.format(Word.getGenTime()));
				end.add(vo);
			}
		}catch (Exception e) {
			System.out.println("copy失败");
		}
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO queryStudentWordById(Map<String, String> params) {
		return ResultVOUtil.success(student_word_dao.findById(Integer.valueOf(params.get("id"))).get());
	}
}
