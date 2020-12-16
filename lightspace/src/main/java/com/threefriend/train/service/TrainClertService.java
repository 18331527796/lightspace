package com.threefriend.train.service;

import java.util.Map;

import com.threefriend.lightspace.mapper.train.TrainChildrenWordMapper;
import com.threefriend.lightspace.mapper.train.TrainChildrenCombinationMapper;
import com.threefriend.lightspace.mapper.train.TrainChildrenMapper;
import com.threefriend.lightspace.mapper.train.TrainClertMapper;
import com.threefriend.lightspace.mapper.train.TrainCombinationMapper;
import com.threefriend.lightspace.vo.ResultVO;

public interface TrainClertService {

	ResultVO clertLogin(TrainClertMapper vo);
	
	ResultVO chkState(Map<String, String> params);
	//录入眼健康的档案
	ResultVO insertChildrenWord(TrainChildrenWordMapper word);
	
	ResultVO getChildren(TrainChildrenMapper vo);
	//获取计划参数
	ResultVO getProgram(Map<String, String> params);
	//设置训练计划
	ResultVO pushTrainCombination(TrainCombinationMapper vo);
	//查看训练计划
	ResultVO combinationList(Map<String, String> params);
	//查看训练计划详情
	ResultVO getCombination(TrainCombinationMapper vo);
	//删除训练计划
	ResultVO deleteCombination(TrainCombinationMapper vo);
	//根据手机号获取孩子列表
	ResultVO getChildrenByPhone(Map<String, String> params);
	//扫码开启下一个训练计划
	ResultVO relieveNextCombination(Map<String, String> params);
	//推送计划给孩子
	ResultVO combinationToChildren(TrainChildrenCombinationMapper vo);
	//查看孩子的训练计划
	ResultVO clertChildrenCombinationList(Map<String, String> params);
	//删除孩子的训练计划
	ResultVO deleteChildrenCombination(TrainChildrenCombinationMapper vo);
	//保存训练计划顺序
	ResultVO saveSort(Map<String, String> params);
	
	
	
	
}
