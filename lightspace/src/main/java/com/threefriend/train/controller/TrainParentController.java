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
import com.threefriend.lightspace.mapper.train.TrainParentChildrenMapper;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.train.service.impl.TrainPartenrServiceImpl;

@RestController
@RequestMapping("/train")
public class TrainParentController {

	@Autowired
	private TrainPartenrServiceImpl partenr_impl;
	
	@PostMapping("/loginXcx")
	public ResultVO loginXcx(@RequestParam Map<String, String> params) throws Exception{
		return partenr_impl.loginXcx(params);
	}
	
	@PostMapping("/childrenList")
	public ResultVO childrenList(@RequestParam Map<String, String> params){
		return partenr_impl.childrenList(params);
	}
	
	@PostMapping("/addOrSaveChildren")
	public ResultVO addChildren(@Valid TrainChildrenMapper vo){
		return partenr_impl.addChildren(vo);
	}
	
	@PostMapping("/editChildren")
	public ResultVO editChildren(@Valid TrainChildrenMapper vo){
		return partenr_impl.editChildren(vo);
	}
	
	@PostMapping("/deleteChildren")
	public ResultVO deleteChildren(@RequestParam Map<String, String> params){
		return partenr_impl.deleteChildren(params);
	}
	
	@PostMapping("/childrenWordList")
	public ResultVO childrenWordList(@Valid TrainChildrenMapper vo){
		return partenr_impl.childrenWordList(vo);
	}
	
	@PostMapping("/getWord")
	public ResultVO getWord(@Valid TrainChildrenWordMapper vo){
		return partenr_impl.getWord(vo);
	}
	
	@PostMapping("/manualBindingPhone")
	public ResultVO manualBindingPhone(@RequestParam Map<String, String> params){
		return partenr_impl.manualBindingPhone(params);
	}
	
	@PostMapping("/bindingPhone")
	public ResultVO bindingPhone(@RequestParam Map<String, String> params) throws Exception{
		return partenr_impl.bindingPhone(params);
	}
	
	@PostMapping("/bindingChildren")
	public ResultVO bindingChildren(@Valid TrainParentChildrenMapper vo) throws Exception{
		return partenr_impl.bindingChildren(vo);
	}
	
	@PostMapping("/combinationSuccess")
	public ResultVO combinationSuccess(@RequestParam Map<String, String> params) throws Exception{
		return partenr_impl.combinationSuccess(params);
	}
	
	@PostMapping("/childrenCombinationList")
	public ResultVO childrenCombinationList(@RequestParam Map<String, String> params){
		return partenr_impl.childrenCombinationList(params);
	}
	
	@PostMapping("/getCombinationHistory")
	public ResultVO getCombinationHistory(@Valid TrainChildrenCombinationMapper vo){
		return partenr_impl.getCombinationHistory(vo);
	}
	
	@PostMapping("/getCombinationParent")
	public ResultVO getCombinationParent(@Valid TrainChildrenCombinationMapper vo){
		return partenr_impl.getCombinationParent(vo);
	}
}
