package com.threefriend.schoolclient.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.threefriend.lightspace.Exception.SortException;
import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.enums.SortEnums;
import com.threefriend.lightspace.mapper.RecordMapper;
import com.threefriend.lightspace.mapper.SortMapper;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.mapper.xcx.ScreeningMapper;
import com.threefriend.lightspace.repository.ClassesRepository;
import com.threefriend.lightspace.repository.RecordRepository;
import com.threefriend.lightspace.repository.ScreeningRepository;
import com.threefriend.lightspace.repository.SortRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.service.SortService;
import com.threefriend.lightspace.util.ListUtils;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.vo.SortVO;
import com.threefriend.schoolclient.service.SchoolSortService;

/**
 * 
 * 排座的实现类
 *
 */
@Service
public class SchoolSortServiceImpl implements SchoolSortService {

	@Autowired
	private RecordRepository record_dao;
	@Autowired
	private StudentRepository student_dao;
	@Autowired
	private SortRepository sort_dao;
	@Autowired
	private ClassesRepository class_dao;
	@Autowired
	private ScreeningRepository screening_dao;
	/*
	 * 学生排座
	 * 通过传输的班级id 排座类型 保存时间来返回排座的数据
	 * 按照身高查询学生的顺序 再按照学生的平均裸眼视力排序 封装数据返回前台
	 * 时间单位 传输过来单位为（秒）
	 */
	@Override
	public ResultVO studentSort(Map<String, String> params) {
		Long time = Long.valueOf(params.get("time"));
		Integer classId = Integer.valueOf(params.get("classId"));
		Integer type = Integer.valueOf(params.get("type"));
		time=time*1000;
		Date nowtime=new Date();
		//一个初始的坐高
		Double sittingHeight = 0d;
		
		// 这里是暂时移除的（本来是用来分行的临时行数据）
		List<SortVO> temporary = new ArrayList<>();
		List<List<SortVO>> sort_group= new ArrayList<>();
		// 排座完成后的
		List<SortVO> end = new ArrayList<>();
		
		Map<String, Object> endMap = new HashMap<>();
		
		// 接收按照坐姿高度排序后的
		List<SortVO> sort = new ArrayList<>();
		//用来存放数据空的学生姓名返回前台 告知老师哪个学生都需要检测
		List<Map<String, String>> nullStudent= new ArrayList<>();
		List<StudentMapper> allStudent = student_dao.findByClassesIdOrderBySittingHeight(classId);
		//循环每个学生的数据做封装
		for (StudentMapper student : allStudent) {
			Integer id=student.getId();
			SortVO vo = new SortVO();
			vo.setStudentId(student.getId());
			vo.setStudentName(student.getName());
			vo.setGender(student.getGender());
			vo.setCorrect(student.getCorrect());
			if(student.getSittingHeight()==null||student.getSittingHeight()=="") {
				nullStudent.add(new HashMap<String, String>() {{ put("name", student.getName());put("cause", "坐姿高度信息不完整");}});
				continue;
			}
			vo.setSittingHeight(Double.valueOf(student.getSittingHeight()));
			//通过判断学生是否有最新的视力记录
			if(student.getVisionLeftStr()!=null&&student.getVisionRightStr()!=null) {
				vo.setAvgRecord(student.getVisionLeftStr()>=student.getVisionRightStr()?student.getVisionRightStr():student.getVisionLeftStr());
			}else {
				RecordMapper record = record_dao.findTopByStudentIdOrderByGenTimeDesc(id);
				if(record!=null) {
					vo.setAvgRecord(record.getVisionLeftStr()>=record.getVisionRightStr()?record.getVisionRightStr():record.getVisionLeftStr());
				}else {
					nullStudent.add(new HashMap<String, String>() {{ put("name", student.getName());put("cause", "视力信息不完整");}});
					continue;
				}
			}
			//存放集合
			sort.add(vo);
			
		}
		//两个数据都是空的 就抛异常
		if(nullStudent!=null&&!nullStudent.isEmpty())throw new SortException(nullStudent);
		int size = sort.size();
		ListUtils.sort(sort, true,"sittingHeight"/*, "avgRecord"*/);
		StringBuilder sortMark = new StringBuilder("");
		sittingHeight = sort.get(0).getSittingHeight();
		
		while (sittingHeight<=sort.get(size-1).getSittingHeight()) {
			for (SortVO vo : sort) {
				if(vo.getSittingHeight()>=sittingHeight&&vo.getSittingHeight()<sittingHeight+3)temporary.add(vo);
			}
			sittingHeight+=3;
			if(temporary.size()<1) {
				// 清除临时的list 进入下一行存储
				temporary = new ArrayList<>();
				continue;
			}
			Collections.shuffle(temporary);
			sort_group.add(temporary);
			end.addAll(temporary);
			// 清除临时的list 进入下一行存储
			temporary = new ArrayList<>();
		}
		//这里是修改后的逻辑
		for (int i = 0; i < end.size() ; i++) {
			if (i == 0) {
				sortMark.append(end.get(i).getStudentId());
			} else {
				sortMark.append("," + end.get(i).getStudentId());
			}
		}
		
		SortMapper po = sort_dao.findByClassId(classId);
		if(po==null)po = new SortMapper();
		po.setName(allStudent.get(0).getClassesName()+"座次表");
		po.setClassId(classId); 
		po.setType(type);
		po.setGenTime(nowtime); 
		po.setEndTime(time);
		po.setSort(sortMark.toString());
		sort_dao.save(po);
		//这里本来是用来颠倒顺序的（面向黑板坐的顺序）
		//Collections.reverse(end);
		
		endMap.put("id", po.getId());
		endMap.put("data", end);
		endMap.put("sort_group", sort_group);
		return ResultVOUtil.success(endMap);
	}

	@Override
	public ResultVO sortShow(Integer Id) {
		SortMapper sortmapper = sort_dao.findById(Id).get();
		List<SortVO> sort = new ArrayList<>();
		
		String[] split = sortmapper.getSort().split(",");
		String[] mr = null;
		if(sortmapper.getMr()!=null) mr=sortmapper.getMr().split(",");
		List<String> list = Arrays.asList(split);
		
		for (String string : list) {
			if(StringUtils.isEmpty(string))continue;
			if(string.contains("m")) {
				SortVO vo=new SortVO();
				vo.setGender(2);
				if(mr.length!=0&&"1".equals(mr[list.indexOf(string)])) {
					vo.setMr(true);
				}
				sort.add(vo);
				continue;
			}
			SortVO vo=new SortVO();
			StudentMapper studentMapper = student_dao.findById(Integer.valueOf(string)).get();
			vo.setStudentId(Integer.valueOf(string));
			vo.setStudentName(studentMapper.getName());
			vo.setGender(studentMapper.getGender());
			vo.setCorrect(studentMapper.getCorrect());
			if(mr.length!=0&&"1".equals(mr[list.indexOf(string)])) {
				vo.setMr(true);
			}
			sort.add(vo);
		}
		
		return ResultVOUtil.success(sort);
	}

	@Override
	public List<SortMapper> byClassId(Integer classId) {
		return sort_dao.findByClassIdOrderByGenTimeDesc(classId);
	}

	@Override
	public ResultVO saveSort(Map<String, String> params) {
		SortMapper sortmapper = sort_dao.findByClassId(Integer.valueOf(params.get("classId")));
		sortmapper.setGenTime(new Date());
		sortmapper.setSort(params.get("sort"));
		sortmapper.setType(Integer.valueOf(params.get("type")));
		sortmapper.setMr(params.get("mr"));
		sort_dao.save(sortmapper);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO adjustSort(Map<String, String> params) {
		SortMapper sortmapper = sort_dao.findByClassId(Integer.valueOf(params.get("classId")));
		if(sortmapper==null)return ResultVOUtil.error(ResultEnum.SORT_ERROR.getStatus(), ResultEnum.SORT_ERROR.getMessage());
		List<SortVO> sort = new ArrayList<>();
		List<SortVO> sortGroups = new ArrayList<>();
		String[] split = sortmapper.getSort().split(",");
		String[] mr = null;
		if(sortmapper.getMr()!=null) mr=sortmapper.getMr().split(",");
		List<String> list = Arrays.asList(split);
		
		for (String string : list) {
			if(StringUtils.isEmpty(string))continue;
			if(string.contains("m")) {
				SortVO vo=new SortVO();
				vo.setGender(2);
				if(mr.length!=0&&"1".equals(mr[list.indexOf(string)])) {
					vo.setMr(true);
				}
				sort.add(vo);
				continue;
			}
			SortVO vo=new SortVO();
			StudentMapper studentMapper = student_dao.findById(Integer.valueOf(string)).get();
			vo.setStudentId(Integer.valueOf(string));
			vo.setStudentName(studentMapper.getName());
			vo.setGender(studentMapper.getGender());
			vo.setCorrect(studentMapper.getCorrect());
			vo.setSittingHeight(Double.valueOf(studentMapper.getSittingHeight()));
			//通过判断学生是否有最新的视力记录
			if(studentMapper.getVisionLeftStr()!=null&&studentMapper.getVisionRightStr()!=null) {
				vo.setAvgRecord(studentMapper.getVisionLeftStr()>=studentMapper.getVisionRightStr()?studentMapper.getVisionRightStr():studentMapper.getVisionLeftStr());
			}else {
				RecordMapper record = record_dao.findTopByStudentIdOrderByGenTimeDesc(studentMapper.getId());
				if(record!=null) {
					vo.setAvgRecord(record.getVisionLeftStr()>=record.getVisionRightStr()?record.getVisionRightStr():record.getVisionLeftStr());
				} 
			}
			if(mr.length!=0&&"1".equals(mr[list.indexOf(string)])) {
				vo.setMr(true);
			}
			sort.add(vo);
		}
		
		
		Double sittingHeight = 0d;
		List<StudentMapper> allStudent = student_dao.findByClassesIdOrderBySittingHeight(Integer.valueOf(params.get("classId")));
		//循环每个学生的数据做封装
		for (StudentMapper student : allStudent) {
			Integer id=student.getId();
			SortVO vo = new SortVO();
			vo.setStudentId(student.getId());
			vo.setStudentName(student.getName());
			vo.setGender(student.getGender());
			vo.setCorrect(student.getCorrect());
			vo.setSittingHeight(Double.valueOf(student.getSittingHeight()));
			//通过判断学生是否有最新的视力记录
			if(student.getVisionLeftStr()!=null&&student.getVisionRightStr()!=null) {
				vo.setAvgRecord(student.getVisionLeftStr()>=student.getVisionRightStr()?student.getVisionRightStr():student.getVisionLeftStr());
			}else {
				RecordMapper record = record_dao.findTopByStudentIdOrderByGenTimeDesc(id);
				if(record!=null) {
					vo.setAvgRecord(record.getVisionLeftStr()>=record.getVisionRightStr()?record.getVisionRightStr():record.getVisionLeftStr());
				} 
			}
			//存放集合
			sortGroups.add(vo);
			
		}
		int size = sortGroups.size();
		ListUtils.sort(sortGroups, true,"sittingHeight"/*, "avgRecord"*/);
		sittingHeight = sortGroups.get(0).getSittingHeight();
		// 这里是暂时移除的（本来是用来分行的临时行数据）
		List<SortVO> temporary = new ArrayList<>();
		List<List<SortVO>> sort_group= new ArrayList<>();
		
		while (sittingHeight<=sortGroups.get(size-1).getSittingHeight()) {
			for (SortVO vo : sortGroups) {
				if(vo.getSittingHeight()>=sittingHeight&&vo.getSittingHeight()<sittingHeight+3)temporary.add(vo);
			}
			sittingHeight+=3;
			if(temporary.size()<1) {
				// 清除临时的list 进入下一行存储
				temporary = new ArrayList<>();
				continue;
			}
			sort_group.add(temporary);
			// 清除临时的list 进入下一行存储
			temporary = new ArrayList<>();
		}
		Map<String, Object> endMap = new HashMap<>();
		endMap.put("data", sort);
		endMap.put("sort_group", sort_group);
		
		return ResultVOUtil.success(endMap);
	}

	@Override
	public ResultVO chkSort(Map<String, String> params) {
		SortMapper sortmapper = sort_dao.findByClassId(Integer.valueOf(params.get("classId")));
		if(sortmapper==null)return ResultVOUtil.error(ResultEnum.SORT_ERROR.getStatus(), ResultEnum.SORT_ERROR.getMessage());
		if(sortmapper.getType()!=Integer.valueOf(params.get("type")))return ResultVOUtil.error(ResultEnum.SORT_TYPE_ERROR.getStatus(), ResultEnum.SORT_TYPE_ERROR.getMessage());
		return ResultVOUtil.success();
	}
}
