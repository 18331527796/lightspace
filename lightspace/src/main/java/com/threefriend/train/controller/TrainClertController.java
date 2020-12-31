package com.threefriend.train.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.mapper.train.TrainChildrenWordMapper;
import com.threefriend.lightspace.mapper.train.TrainChildrenCombinationMapper;
import com.threefriend.lightspace.mapper.train.TrainChildrenMapper;
import com.threefriend.lightspace.mapper.train.TrainClertMapper;
import com.threefriend.lightspace.mapper.train.TrainCombinationMapper;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.train.service.impl.TrainClertServiceImpl;

@RestController
@RequestMapping("/train")
public class TrainClertController {

	@Autowired
	private TrainClertServiceImpl clert_impl;
	
	@PostMapping("/chkState")
	public ResultVO loginXcx(@RequestParam Map<String, String> params){
		return clert_impl.chkState(params);
	}
	
	@PostMapping("/clertLogin")
	public ResultVO clertLogin(@Valid TrainClertMapper vo){
		return clert_impl.clertLogin(vo);
	}
	
	@PostMapping("/insertChildrenWord")
	public ResultVO insertChildrenWord(@Valid TrainChildrenWordMapper vo){
		return clert_impl.insertChildrenWord(vo);
	}
	
	@PostMapping("/getChildren")
	public ResultVO getChildren(@Valid TrainChildrenMapper vo){
		return clert_impl.getChildren(vo);
	}
	
	@PostMapping("/getProgram")
	public ResultVO getProgram(@RequestParam Map<String, String> params){
		return clert_impl.getProgram(params);
	}
	
	@PostMapping("/pushTrainCombination")
	public ResultVO pushTrainCombination(@Valid TrainCombinationMapper vo){
		return clert_impl.pushTrainCombination(vo);
	}

	@PostMapping("/combinationList")
	public ResultVO combinationList(@RequestParam Map<String, String> params){
		return clert_impl.combinationList(params);
	}
	
	@PostMapping("/getCombination")
	public ResultVO getCombination(@Valid TrainCombinationMapper vo){
		return clert_impl.getCombination(vo);
	}
	
	@PostMapping("/deleteCombination")
	public ResultVO deleteCombination(@Valid TrainCombinationMapper vo){
		return clert_impl.deleteCombination(vo);
	}
	
	@PostMapping("/getChildrenByPhone")
	public ResultVO getChildrenByPhone(@RequestParam Map<String, String> params){
		return clert_impl.getChildrenByPhone(params);
	}
	
	@PostMapping("/relieveNextCombination")
	public ResultVO relieveNextCombination(@RequestParam Map<String, String> params){
		return clert_impl.relieveNextCombination(params);
	}
	
	@PostMapping("/combinationToChildren")
	public ResultVO combinationToChildren(@Valid TrainChildrenCombinationMapper vo ){
		return clert_impl.combinationToChildren(vo);
	}
	
	@PostMapping("/deleteChildrenCombination")
	public ResultVO deleteChildrenCombination(@RequestParam Map<String, String> params){
		return clert_impl.deleteChildrenCombination(params);
	}
	
	@PostMapping("/saveSort")
	public ResultVO saveSort(@RequestParam Map<String, String> params){
		return clert_impl.saveSort(params);
	}
	
	@PostMapping("/clertChildrenCombinationList")
	public ResultVO clertChildrenCombinationList(@RequestParam Map<String, String> params){
		return clert_impl.clertChildrenCombinationList(params);
	}
	
	
}
