package com.threefriend.lightspace.service.Impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.repository.SpecificationsRepository;
import com.threefriend.lightspace.service.SpecificationsService;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.mapper.SpecificationsMapper;

/**
 * 规格业务逻辑实现层
 * @author Administrator
 *
 */
@Service
public class SpecificationsServiceImpl implements SpecificationsService{

	@Autowired
	private SpecificationsRepository specifications_dao;

	/* 
	 * 规格列表
	 */
	@Override
	public ResultVO specificationsList(Integer productId) {
		if(productId==0) {
			return ResultVOUtil.success(specifications_dao.findAll());
		}else {
			return ResultVOUtil.success(specifications_dao.findByProduceId(productId));
		}
	}	
		
	/* 
	 * 新增规格
	 */
	@Override
	public ResultVO addSpecifications(Map<String, String> params) {
		SpecificationsMapper po = new SpecificationsMapper();
		po.setName(params.get("name"));
		po.setIntegral(Long.valueOf(params.get("coin")));
		po.setFreight(Integer.valueOf(params.get("freight")));
		po.setProduceId(Integer.valueOf(params.get("productId")));
		po.setStock(Integer.valueOf(params.get("stock")));
		if(!StringUtils.isEmpty(params.get("price")))po.setPrice(Long.valueOf(params.get("price")));
		if(!StringUtils.isEmpty(params.get("percentage")))po.setPercentage(Integer.valueOf(params.get("percentage")));
		specifications_dao.save(po);
		return specificationsList(Integer.valueOf(params.get("productId")));
	}

	/* 
	 * 修改规格
	 */
	@Override
	public ResultVO editSpecifications(Map<String, String> params) {
		SpecificationsMapper po = specifications_dao.findById(Integer.valueOf(params.get("id"))).get();
		return ResultVOUtil.success(po);
	}

	/* 
	 * 规格修改后保存
	 */
	@Override
	public ResultVO saveSpecifications(Map<String, String> params) {
		SpecificationsMapper po = specifications_dao.findById(Integer.valueOf(params.get("id"))).get();
		if(!StringUtils.isEmpty(params.get("name")))po.setName(params.get("name"));
		if(!StringUtils.isEmpty(params.get("price")))po.setPrice(Long.valueOf(params.get("price")));
		if(!StringUtils.isEmpty(params.get("coin")))po.setIntegral(Long.valueOf(params.get("coin")));
		if(!StringUtils.isEmpty(params.get("freight")))po.setFreight(Integer.valueOf(params.get("freight")));
		if(!StringUtils.isEmpty(params.get("percentage")))po.setPercentage(Integer.valueOf(params.get("percentage")));
		if(!StringUtils.isEmpty(params.get("productId")))po.setProduceId(Integer.valueOf(params.get("productId")));
		if(!StringUtils.isEmpty(params.get("stock")))po.setStock(Integer.valueOf(params.get("stock")));
		specifications_dao.save(po);
		return specificationsList(Integer.valueOf(params.get("id")));
	}

	/* 
	 * 删除规格
	 */
	@Override
	public ResultVO deleteSpecifications(Map<String, String> params) {
		SpecificationsMapper po = specifications_dao.findById(Integer.valueOf(params.get("id"))).get();
		Integer produceId = po.getProduceId();
		specifications_dao.delete(po);
		return specificationsList(produceId);
	}
}
