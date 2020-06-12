package com.threefriend.lightspace.service.Impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.mapper.xcx.SpecificationsMapper;
import com.threefriend.lightspace.repository.ProductRepository;
import com.threefriend.lightspace.repository.SpecificationsRepository;
import com.threefriend.lightspace.service.SpecificationsService;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;

/**
 * 规格业务逻辑实现层
 * @author Administrator
 *
 */
@Service
public class SpecificationsServiceImpl implements SpecificationsService{

	@Autowired
	private SpecificationsRepository specifications_dao;
	@Autowired
	private ProductRepository product_dao;

	/* 
	 * 规格列表
	 */
	@Override
	public ResultVO specificationsList(Map<String, String> params) {
		int page = 0 ;
		Integer productId = 0;
		if(!StringUtils.isEmpty(params.get("page")))page = Integer.valueOf(params.get("page"))-1;
		if(!StringUtils.isEmpty(params.get("productId")))productId = Integer.valueOf(params.get("productId"));
		if(productId==0) {
			return ResultVOUtil.success(specifications_dao.findAllByOrderByIdDesc(PageRequest.of(page, 10)));
		}else {
			return ResultVOUtil.success(specifications_dao.findByProductId(productId,PageRequest.of(page, 10)));
		}
	}	
		
	/* 
	 * 新增规格
	 */
	@Override
	public ResultVO addSpecifications(Map<String, String> params) {
		SpecificationsMapper po = new SpecificationsMapper();
		po.setName(params.get("name"));
		po.setIntegral(Long.valueOf(params.get("integral")));
		po.setFreight(Integer.valueOf(params.get("freight")));
		po.setProductId(Integer.valueOf(params.get("productId")));
		po.setProductType(Integer.valueOf(params.get("productType")));
		po.setProductName(product_dao.findById(Integer.valueOf(params.get("productId"))).get().getName());
		po.setStock(Integer.valueOf(params.get("stock")));
		if(!StringUtils.isEmpty(params.get("price")))po.setPrice(Long.valueOf(params.get("price")));
		if(!StringUtils.isEmpty(params.get("percentage")))po.setPercentage(Integer.valueOf(params.get("percentage")));
		specifications_dao.save(po);
		return ResultVOUtil.success();
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
		if(!StringUtils.isEmpty(params.get("integral")))po.setIntegral(Long.valueOf(params.get("integral")));
		if(!StringUtils.isEmpty(params.get("freight")))po.setFreight(Integer.valueOf(params.get("freight")));
		if(!StringUtils.isEmpty(params.get("percentage")))po.setPercentage(Integer.valueOf(params.get("percentage")));
		if(!StringUtils.isEmpty(params.get("stock")))po.setStock(Integer.valueOf(params.get("stock")));
		if(!StringUtils.isEmpty(params.get("productType")))po.setProductType(Integer.valueOf(params.get("productType")));
		if(!StringUtils.isEmpty(params.get("productId"))) {
			po.setProductId(Integer.valueOf(params.get("productId")));
			po.setProductName(product_dao.findById(Integer.valueOf(params.get("productId"))).get().getName());
		}
		specifications_dao.save(po);
		return ResultVOUtil.success();
	}

	/* 
	 * 删除规格
	 */
	@Override
	public ResultVO deleteSpecifications(Map<String, String> params) {
		specifications_dao.deleteById(Integer.valueOf(params.get("id")));
		return ResultVOUtil.success();
	}
}
