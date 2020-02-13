package com.threefriend.lightspace.service;

import java.util.List;
import java.util.Map;

import com.threefriend.lightspace.mapper.RecordMapper;
import com.threefriend.lightspace.vo.OneStatisticsVO;
import com.threefriend.lightspace.vo.RecordVO;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.vo.StatisticsVO;

/**
 * 基础数据业务逻辑接口
 */
public interface RecordService {

	//新增记录
	public List<RecordMapper> addRecord(Map<String, String> params);
	//修改记录
	public List<RecordMapper> saveRecord(Map<String, String> params);
	//删除记录
	public List<RecordMapper> deleteRecord(Integer id,String token);
	//按照id查询记录
	public RecordVO editRecord(Integer id);
	//记录列表
	public List<RecordMapper> recordList(String token);
	//模糊
	public ResultVO findByName(String name);
	//按照学生id查询最新的基础数据
	public RecordMapper findByStudentId(Integer id);
	//按照学生id查询所有的数据
	public List<OneStatisticsVO> findAllByStudentId(Integer id,Long time);
	//按照学校数据分析
	public List<List<StatisticsVO>> schoolStatistics(Integer schoolId);
	//按照班级数据分析
	public List<List<StatisticsVO>> classStatistics(Integer classId);
}
