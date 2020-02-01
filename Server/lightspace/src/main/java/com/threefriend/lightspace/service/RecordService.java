package com.threefriend.lightspace.service;

import java.util.List;
import java.util.Map;

import com.threefriend.lightspace.mapper.RecordMapper;

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
	public RecordMapper editRecord(Integer id);
	//记录列表
	public List<RecordMapper> recordList(String token);
	//模糊
	public List<RecordMapper> findByName(String name);
	//按照学生id查询最新的基础数据
	public RecordMapper findByStudentId(Integer id);
}
