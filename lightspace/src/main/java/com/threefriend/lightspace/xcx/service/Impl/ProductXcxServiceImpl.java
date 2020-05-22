package com.threefriend.lightspace.xcx.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.enums.UrlEnums;
import com.threefriend.lightspace.mapper.RotationPicMapper;
import com.threefriend.lightspace.mapper.xcx.ProductMapper;
import com.threefriend.lightspace.mapper.xcx.SpecificationsMapper;
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
			List<SpecificationsMapper> content = specifications_dao.findByProductId(productMapper.getId(), PageRequest.of(0, 1)).getContent();
			if(content.size()==0)continue;
			ProductVO vo = new ProductVO();
			BeanUtils.copyProperties(productMapper, vo);
			if(!productMapper.getPicture().isEmpty()) {
				String[] split = productMapper.getPicture().split(",");
				vo.getPictures().add(UrlEnums.IMG_URL.getUrl()+split[0]);
			}
			vo.setIntegral(content.get(0).getIntegral());
			productList.add(vo);
		}
		return ResultVOUtil.success(productList);
	}

	@Override
	public ResultVO productDetils(Map<String, String> params) {
		Integer productId = Integer.valueOf(params.get("id"));
		ProductVO end = new ProductVO();
		ProductMapper productMapper = product_dao.findById(productId).get();
		List<SpecificationsMapper> content = specifications_dao.findByProductId(productMapper.getId(), PageRequest.of(0, 20)).getContent();
		BeanUtils.copyProperties(productMapper, end);
		if(!productMapper.getPicture().isEmpty()) {
			String[] split = productMapper.getPicture().split(",");
			for (String string : split) {
				end.getPictures().add(UrlEnums.IMG_URL.getUrl()+string);
			}
		}
		end.setIntegral(content.get(0).getIntegral());
		end.setSpecificationsList(content);
		List<ProductMapper> content2 = product_dao.findAll(PageRequest.of(0, 4,Sort.by("id").ascending())).getContent();
		for (ProductMapper po : content2) {
			List<SpecificationsMapper> specification = specifications_dao.findByProductId(po.getId(), PageRequest.of(0, 20)).getContent();
			if(specification.size()==0)continue;
			ProductVO vo = new ProductVO();
			BeanUtils.copyProperties(po, vo);
			if(!po.getPicture().isEmpty()) {
				String[] split = po.getPicture().split(",");
				vo.getPictures().add(UrlEnums.IMG_URL.getUrl()+split[0]);
			}
			vo.setIntegral(content.get(0).getIntegral());
			vo.setSpecificationsList(specification);
			end.getProductList().add(vo);
		}
		return ResultVOUtil.success(end);
	}
	
	
	
}
