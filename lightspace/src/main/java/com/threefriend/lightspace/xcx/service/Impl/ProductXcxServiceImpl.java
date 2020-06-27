package com.threefriend.lightspace.xcx.service.Impl;

import java.util.ArrayList;
import java.util.Comparator;
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

import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.enums.UrlEnums;
import com.threefriend.lightspace.mapper.RotationPicMapper;
import com.threefriend.lightspace.mapper.xcx.ProductMapper;
import com.threefriend.lightspace.mapper.xcx.SpecificationsMapper;
import com.threefriend.lightspace.repository.OrderRepository;
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
	@Autowired
	private OrderRepository order_dao;

	@Override
	public ResultVO productPage(Map<String, String> params) {
		List<ProductVO> productList = new ArrayList<>();
		int page = 0 ;
		String buyNumber = "0";
		if(!StringUtils.isEmpty(params.get("page")))page = Integer.valueOf(params.get("page"))-1;
		Page<ProductMapper> findAll = product_dao.findAll(PageRequest.of(page, 10));
		for (ProductMapper productMapper : findAll.getContent()) {
			
			int count = order_dao.countByProductId(productMapper.getId());
			if(count<9999) {
				buyNumber = String.valueOf(count);
			}else{
				buyNumber = String.valueOf(count).substring(0,1)+"."+String.valueOf(count).substring(1,2)+"万";
			}
			
			List<SpecificationsMapper> content = specifications_dao.findByProductId(productMapper.getId(), PageRequest.of(0, 1)).getContent();
			if(content.size()==0)continue;
			ProductVO vo = new ProductVO();
			BeanUtils.copyProperties(productMapper, vo);
			if(!productMapper.getPicture().isEmpty()) {
				String[] split = productMapper.getPicture().split(",");
				vo.getPictures().add(UrlEnums.IMG_URL.getUrl()+split[0]);
			}
			vo.setBuyNumber(buyNumber);
			vo.setIntegral(content.get(0).getIntegral());
			productList.add(vo);
		}
		productList.sort(Comparator.comparing(ProductVO::getIntegral));
		return ResultVOUtil.success(productList);
	}

	@Override
	public ResultVO productDetils(Map<String, String> params) {
		Integer productId = Integer.valueOf(params.get("id"));
		ProductVO end = new ProductVO();
		String buyNumber = "0";
		String pobuyNumber = "0";
		ProductMapper productMapper = product_dao.findById(productId).get();
		List<SpecificationsMapper> content = specifications_dao.findByProductId(productMapper.getId(), PageRequest.of(0, 20)).getContent();
		
		int count = order_dao.countByProductId(productId);
		if(count<9999) {
			buyNumber = String.valueOf(count);
		}else{
			buyNumber = String.valueOf(count).substring(0,1)+"."+String.valueOf(count).substring(1,2)+"万";
		}
		
		BeanUtils.copyProperties(productMapper, end);
		if(!productMapper.getPicture().isEmpty()) {
			String[] split = productMapper.getPicture().split(",");
			for (String string : split) {
				end.getPictures().add(UrlEnums.IMG_URL.getUrl()+string);
			}
		}
		end.setBuyNumber(buyNumber);
		end.setIntegral(content.get(0).getIntegral());
		end.setSpecificationsList(content);
		List<ProductMapper> content2 = product_dao.findAll(PageRequest.of(0, 4,Sort.by("id").ascending())).getContent();
		for (ProductMapper po : content2) {
			
			int pocount = order_dao.countByProductId(po.getId());
			if(pocount<9999) {
				pobuyNumber = String.valueOf(pocount);
			}else{
				pobuyNumber = String.valueOf(pocount).substring(0,1)+"."+String.valueOf(pocount).substring(1,2)+"万";
			}
			
			List<SpecificationsMapper> specification = specifications_dao.findByProductId(po.getId(), PageRequest.of(0, 20)).getContent();
			if(specification.size()==0)continue;
			ProductVO vo = new ProductVO();
			BeanUtils.copyProperties(po, vo);
			if(!po.getPicture().isEmpty()) {
				String[] split = po.getPicture().split(",");
				vo.getPictures().add(UrlEnums.IMG_URL.getUrl()+split[0]);
			}
			vo.setBuyNumber(pobuyNumber);
			vo.setIntegral(specification.get(0).getIntegral());
			vo.setSpecificationsList(specification);
			end.getProductList().add(vo);
		}
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO findProduct(Map<String, String> params) {
		List<ProductVO> productList = new ArrayList<>();
		int page = 0 ;
		String buyNumber = "";
		String name = "%"+params.get("name")+"%" ;
		if(!StringUtils.isEmpty(params.get("page")))page = Integer.valueOf(params.get("page"))-1;
		Page<ProductMapper> findAll = product_dao.findAllByNameLikeOrderByIdDesc(name, PageRequest.of(page, 10));
		for (ProductMapper productMapper : findAll.getContent()) {
			int count = order_dao.countByProductId(productMapper.getId());
			if(count<9999) {
				buyNumber = String.valueOf(count);
			}else{
				buyNumber = String.valueOf(count).substring(0,1)+"."+String.valueOf(count).substring(1,2)+"万";
			}
			
			List<SpecificationsMapper> content = specifications_dao.findByProductId(productMapper.getId(), PageRequest.of(0, 1)).getContent();
			if(content.size()==0)continue;
			ProductVO vo = new ProductVO();
			BeanUtils.copyProperties(productMapper, vo);
			if(!productMapper.getPicture().isEmpty()) {
				String[] split = productMapper.getPicture().split(",");
				vo.getPictures().add(UrlEnums.IMG_URL.getUrl()+split[0]);
			}
			vo.setBuyNumber(buyNumber);
			vo.setIntegral(content.get(0).getIntegral());
			productList.add(vo);
		}
		if(productList.size()<1) return ResultVOUtil.error(ResultEnum.FINDPRODUCT_ERROR.getStatus(), ResultEnum.FINDPRODUCT_ERROR.getMessage());
		return ResultVOUtil.success(productList);
	}
	
	
	
}
