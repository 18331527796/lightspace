package com.threefriend.lightspace.xcx.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.enums.UrlEnums;
import com.threefriend.lightspace.mapper.RotationPicMapper;
import com.threefriend.lightspace.mapper.xcx.ProductMapper;
import com.threefriend.lightspace.repository.ProductRepository;
import com.threefriend.lightspace.repository.RotationPicRepository;
import com.threefriend.lightspace.repository.SpecificationsRepository;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ProductVO;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.ProductXcxService;

@Service
public class ProductXcxServiceImpl implements ProductXcxService{

	@Autowired
	private ProductRepository product_dao;
	@Autowired
	private SpecificationsRepository specifications_dao;

	@Override
	public ResultVO productPage(Map<String, String> params) {
		int page = 0 ;
		List<ProductVO> productList = new ArrayList<>();
		if(!StringUtils.isEmpty(params.get("page")))page = Integer.valueOf(params.get("page"))-1;
		Page<ProductMapper> findAll = product_dao.findAll(PageRequest.of(page, 10));
		for (ProductMapper productMapper : findAll.getContent()) {
			ProductVO vo = new ProductVO();
			BeanUtils.copyProperties(productMapper, vo);
			if(!productMapper.getPicture().isEmpty()) {
				String[] split = productMapper.getPicture().split(",");
				for (String string : split) {
					vo.getPictures().add(UrlEnums.IMG_URL.getUrl()+string);
				}
			}
			vo.setIntegral(specifications_dao.findByProductId(productMapper.getId(), PageRequest.of(0, 1)).getContent().get(0).getIntegral());
			productList.add(vo);
		}
		return ResultVOUtil.success(productList);
	}
	
	
	
}
