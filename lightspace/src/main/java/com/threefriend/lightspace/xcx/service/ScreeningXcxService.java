package com.threefriend.lightspace.xcx.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.threefriend.lightspace.mapper.MsgTempMapper;
import com.threefriend.lightspace.vo.ResultVO;

/**
 *  筛查业务逻辑接口
 *
 */
public interface ScreeningXcxService {
	//三级级联
	ResultVO selectStudent();
	//新增筛查记录
	ResultVO addScreening(Map<String, String> params)throws ParseException;
	//新增筛查记录(戴镜)
	ResultVO addScreeningWear(Map<String, String> params)throws ParseException;
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
	//获取access-token
	String getAccessToken();
	//发送通知
	void screeningMessage(MsgTempMapper msg, Integer studentId, String left, String right, Integer type);
	//获得爱眼币  type 是用来判断是不是戴镜筛查
	ResultVO getCoin(Integer parentId , Integer studentId )throws ParseException;
}
