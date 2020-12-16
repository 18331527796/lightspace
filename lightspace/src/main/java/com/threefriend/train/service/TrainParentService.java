package com.threefriend.train.service;

import java.util.Map;

import com.threefriend.lightspace.mapper.train.TrainChildrenWordMapper;
import com.threefriend.lightspace.mapper.train.TrainChildrenCombinationMapper;
import com.threefriend.lightspace.mapper.train.TrainChildrenMapper;
import com.threefriend.lightspace.mapper.train.TrainParentChildrenMapper;
import com.threefriend.lightspace.vo.ResultVO;

public interface TrainParentService {
	//登录
	ResultVO loginXcx(Map<String, String> params) throws Exception;
	//孩子列表
	ResultVO childrenList(Map<String, String> params);
	//注册/修改后保存孩子
	ResultVO addChildren(TrainChildrenMapper vo);
	//修改孩子
	ResultVO editChildren(TrainChildrenMapper vo);
	//删除
	ResultVO deleteChildren(Map<String, String> params);
	//孩子的健康档案
	ResultVO childrenWordList(TrainChildrenMapper vo);
	//查看健康档案详情
	ResultVO getWord(TrainChildrenWordMapper vo);
	//绑定手机号
	ResultVO bindingPhone(Map<String, String> params) throws Exception;
	//扫孩子码同步绑定
	ResultVO bindingChildren(TrainParentChildrenMapper vo);
	//完成计划
	ResultVO combinationSuccess(TrainChildrenCombinationMapper vo);
	//查看孩子的训练计划
	ResultVO childrenCombinationList(Map<String, String> params);
}
