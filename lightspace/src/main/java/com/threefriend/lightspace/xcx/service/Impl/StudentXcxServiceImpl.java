package com.threefriend.lightspace.xcx.service.Impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.mapper.StudentWordMapper;
import com.threefriend.lightspace.repository.ParentRepository;
import com.threefriend.lightspace.repository.ParentStudentRepository;
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
		DateFormat Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<StudentWordVO> end = new ArrayList<StudentWordVO>();
		List<StudentWordMapper> words = student_word_dao.findByStudentIdOrderByGenTimeDesc(Integer.valueOf(params.get("studentId")));
		try {
			for (StudentWordMapper Word : words) {
				StudentWordVO vo = new StudentWordVO();
				BeanUtils.copyProperties(Word, vo);
				vo.setGender(Integer.valueOf(Word.getGender()));
				vo.setTime(Format.format(Word.getGenTime()));
				end.add(vo);
			}
		}catch (Exception e) {
			System.out.println("copy失败");
		}
		//Collections.sort(end, Comparator.comparing(StudentWordVO::getTime).reversed());
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO queryStudentWordById(Map<String, String> params) {
		return ResultVOUtil.success(student_word_dao.findById(Integer.valueOf(params.get("id"))).get());
	}

	@Override
	public ResultVO perfectStudent(Map<String, String> params) {
		StudentMapper student = student_dao.findById(Integer.valueOf(params.get("id"))).get();
		if(!StringUtils.isEmpty(params.get("name")))student.setName(params.get("name"));
		if(!StringUtils.isEmpty(params.get("gender")))student.setGender(Integer.valueOf(params.get("gender")));
		if(!StringUtils.isEmpty(params.get("height"))&&!params.get("height").equals("null")) 
			student.setHeight(params.get("height"));
		if(!StringUtils.isEmpty(params.get("chairHeight"))&&!params.get("chairHeight").equals("null")) 
			student.setChairHeight(params.get("chairHeight"));
		
		if(!StringUtils.isEmpty(params.get("sittingHeight"))&&!params.get("sittingHeight").equals("null")) 
			student.setSittingHeight(params.get("sittingHeight"));
		
		if(!StringUtils.isEmpty(params.get("birthday"))&&!params.get("birthday").equals("null")) 
			student.setBirthday(params.get("birthday"));
		
		student_dao.save(student);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO chkDominantEye(Map<String, String> params) {
		StudentMapper student = student_dao.findById(Integer.valueOf(params.get("studentId"))).get();
		if(student.getDominantEye()==null||"".equals(student.getDominantEye())) {
			return ResultVOUtil.error(ResultEnum.CHK_DOMINANTEYE_ERROR.getStatus(), ResultEnum.CHK_DOMINANTEYE_ERROR.getMessage());
		}
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO saveDominantEye(Map<String, String> params) {
		StudentMapper student = student_dao.findById(Integer.valueOf(params.get("studentId"))).get();
		student.setDominantEye(params.get("dominantEye"));
		student_dao.save(student);
		return ResultVOUtil.success();
	}
}
