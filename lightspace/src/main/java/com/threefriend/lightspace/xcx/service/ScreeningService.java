package com.threefriend.lightspace.xcx.service;

import java.util.List;
import java.util.Map;

import com.threefriend.lightspace.vo.ResultVO;

/**
 *  筛查业务逻辑接口
 *
 */
public interface ScreeningService {
	//三级级联
	ResultVO selectStudent();
	//新增筛查记录
	ResultVO addScreening(Map<String, String> params);
	//新增筛查记录(戴镜)
	ResultVO addScreeningWear(Map<String, String> params);
	//查询所有视标 随即返回
	ResultVO selectOptotype();
	//按照id查找档案
	ResultVO findById(Map<String, String> params);
	//戴镜按照id查
	ResultVO findWearById(Map<String, String> params);
	//档案首页数据
	ResultVO allChildrenScreening(Map<String, String> params);
	//解析josn串
	List<Map<String, String>> pushjosn(String josn);
}
