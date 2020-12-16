package com.threefriend.train.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.mapper.train.TrainChildrenWordMapper;
import com.threefriend.lightspace.mapper.train.TrainChildrenCombinationMapper;
import com.threefriend.lightspace.mapper.train.TrainChildrenMapper;
import com.threefriend.lightspace.mapper.train.TrainClertMapper;
import com.threefriend.lightspace.mapper.train.TrainCombinationMapper;
import com.threefriend.lightspace.mapper.train.TrainParentMapper;
import com.threefriend.lightspace.mapper.train.TrainProgramMapper;
import com.threefriend.lightspace.repository.train.TrainChildrenWordRepository;
import com.threefriend.lightspace.repository.train.TrainChildrenCombinationRepository;
import com.threefriend.lightspace.repository.train.TrainChildrenRepository;
import com.threefriend.lightspace.repository.train.TrainClertRepository;
import com.threefriend.lightspace.repository.train.TrainCombinationRepository;
import com.threefriend.lightspace.repository.train.TrainParentRepository;
import com.threefriend.lightspace.repository.train.TrainProgramRepository;
import com.threefriend.lightspace.util.MyBeanUtils;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.vo.train.TrainProgramVO;
import com.threefriend.lightspace.xcx.util.NumToChinese;
import com.threefriend.train.service.TrainClertService;

@Service
public class TrainClertServiceImpl implements TrainClertService {

	@Autowired
	private TrainClertRepository clert_dao;
	@Autowired
	private TrainChildrenWordRepository word_dao;
	@Autowired
	private TrainChildrenRepository children_dao;
	@Autowired
	private TrainProgramRepository program_dao;
	@Autowired
	private TrainCombinationRepository combination_dao;;
	@Autowired
	private TrainParentRepository parent_dao;
	@Autowired
	private TrainChildrenCombinationRepository c_c_dao;
	
	@Override
	public ResultVO clertLogin(TrainClertMapper vo) {
		TrainClertMapper clert = clert_dao.findByLoginNameAndPassword(vo.getLoginName(),vo.getPassword());
		if(clert==null) 
			return ResultVOUtil.error(ResultEnum.TEACHER_LOGIN_ERROR.getStatus(), ResultEnum.TEACHER_LOGIN_ERROR.getMessage());
		clert.setState(1);
		clert.setOpenId(vo.getOpenId());
		clert_dao.save(clert);
		return ResultVOUtil.success();
	}
	
	@Override
	public ResultVO chkState(Map<String, String> params) {
		TrainClertMapper clert = clert_dao.findByOpenId(params.get("openId"));
		if (clert != null && clert.getState() == 1)
			return ResultVOUtil.success();
		return ResultVOUtil.error(ResultEnum.CHKSTATE_ERROR.getStatus(), ResultEnum.CHKSTATE_ERROR.getMessage());
	}

	/* 
	 * 新增眼健康档案
	 */
	@Override
	public ResultVO insertChildrenWord(TrainChildrenWordMapper word) {
		TrainChildrenWordMapper po = new TrainChildrenWordMapper();
		BeanUtils.copyProperties(word, po);
		word_dao.save(po);
		return ResultVOUtil.success(po.getId());
	}
	/* 
	 * 获取单个孩子
	 */
	@Override
	public ResultVO getChildren(TrainChildrenMapper vo) {
		TrainChildrenMapper children = children_dao.findById(vo.getId()).get();
		return ResultVOUtil.success(children);
	}
	/* 
	 * 获取计划参数（库）
	 */
	@Override
	public ResultVO getProgram(Map<String, String> params) {
		List<TrainProgramVO> end = new ArrayList<>();
		List<TrainProgramMapper> allProgram = program_dao.findByDirectionOrderById(params.get("direction"));
		for (TrainProgramMapper po : allProgram) {
			end.add(new TrainProgramVO(po));
		}
		return ResultVOUtil.success(end);
	}
	/* 
	 *  
	 * 制定训练计划组合
	 */
	@Override
	public ResultVO pushTrainCombination(TrainCombinationMapper vo) {
		System.out.println(vo.getCombination());
		if(StringUtils.isEmpty(vo.getCombination()))return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
		TrainCombinationMapper po = new TrainCombinationMapper();
		MyBeanUtils.copyProperties(vo, po);
		combination_dao.save(po);
		return ResultVOUtil.success();
	}
	/* 
	 * 组合方案列表
	 */
	@Override
	public ResultVO combinationList(Map<String, String> params) {
		List<Map<String , String>> end = new ArrayList<>();
		List<TrainCombinationMapper> combinations = combination_dao.findByOpenIdAndPNameAndIsShowOrderByIdDesc(params.get("openId"),params.get("pName"),1);
		for (TrainCombinationMapper po : combinations) {
			Map<String , String > map = new HashMap<String , String >();
			map.put("id", po.getId()+"");
			map.put("name", po.getName());
			map.put("time", po.getGenTime());
			end.add(map);
		}
		return ResultVOUtil.success(end);
	}
	/* 
	 * 训练计划组合详情
	 */
	@Override
	public ResultVO getCombination(TrainCombinationMapper vo) {
		List<String> end = new ArrayList<>();
		TrainCombinationMapper combination = combination_dao.findById(vo.getId()).get();
		String[] split = combination.getCombination().split("/");
		for (String string : split) {
			end.add(string);
		}
		Collections.reverse(end);
		return ResultVOUtil.success(end);
	}
	/* 
	 * 删除训练计划组合
	 */
	@Override
	public ResultVO deleteCombination(TrainCombinationMapper vo) {
		TrainCombinationMapper Combination = combination_dao.findById(vo.getId()).get();
		Combination.setIsShow(2);
		combination_dao.save(Combination);
		List<Map<String , String>> end = new ArrayList<>();
		List<TrainCombinationMapper> combinations = combination_dao.findByOpenIdAndPNameAndIsShowOrderByIdDesc(vo.getOpenId(),vo.getpName(),1);
		for (TrainCombinationMapper po : combinations) {
			Map<String , String > map = new HashMap<String , String >();
			map.put("id", po.getId()+"");
			map.put("name", po.getName());
			map.put("time", po.getGenTime());
			end.add(map);
		}
		return ResultVOUtil.success(end);
	}
	/* 
	 * 按照手机号获取孩子列表
	 */
	@Override
	public ResultVO getChildrenByPhone(Map<String, String> params) {
		TrainParentMapper parent = parent_dao.findByPhone(params.get("phone"));
		List<TrainChildrenMapper> childrens = children_dao.findByOpenId(parent.getOpenId());
		return ResultVOUtil.success(childrens);
	}

	/* 
	 * 扫码解除下一个训练计划
	 */
	@Override
	public ResultVO relieveNextCombination(Map<String, String> params) {
		List<TrainChildrenCombinationMapper> all = c_c_dao.findByChildrenIdOrderByIsOpen(Integer.valueOf(params.get("childrenId")));
		if(all.get(0).getIsOpen()==1&&all.get(0).getIsSuccess()==2)return ResultVOUtil.success("请先完成上一组训练哦~");
		if(all.get(0).getIsOpen()==2) {
			all.get(0).setIsOpen(1);
			c_c_dao.save(all.get(0));
		}
		return ResultVOUtil.success("已成功解锁下一组训练");
	}

	@Override
	public ResultVO combinationToChildren(TrainChildrenCombinationMapper vo) {
		TrainChildrenCombinationMapper po = new TrainChildrenCombinationMapper();
		MyBeanUtils.copyProperties(vo, po);
		po.setCombinationName(combination_dao.findById(vo.getCombinationId()).get().getName());
		int sort = c_c_dao.countByChildrenId(vo.getChildrenId());
		po.setSort(sort);
		c_c_dao.save(po);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO clertChildrenCombinationList(Map<String, String> params) {
		List<TrainChildrenCombinationMapper> all = c_c_dao.findByChildrenIdOrderByIsOpen(Integer.valueOf(params.get("childrenId")));
		if(all.size()<1)return ResultVOUtil.success();
		return ResultVOUtil.success(all);
	}

	@Override
	public ResultVO deleteChildrenCombination(TrainChildrenCombinationMapper vo) {
		c_c_dao.deleteById(vo.getId());
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO saveSort(Map<String, String> params) {
		List<TrainChildrenCombinationMapper> list = new ArrayList<>();
		System.out.println(params.get("sort"));
		String[] split = params.get("sort").split("-");
		for (String string : split) {
			list.add(c_c_dao.findById(Integer.valueOf(string)).get());
		}
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setSort(i);
		}
		c_c_dao.saveAll(list);
		return ResultVOUtil.success();
	}
	
	
}

