package com.threefriend.lightspace.service.Impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.enums.UrlEnums;
import com.threefriend.lightspace.mapper.xcx.ProductMapper;
import com.threefriend.lightspace.repository.ProductRepository;
import com.threefriend.lightspace.service.ProductService;
import com.threefriend.lightspace.util.ImguploadUtils;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ProductVO;
import com.threefriend.lightspace.vo.ResultVO;

/**
 * 商品逻辑实现类
 */
@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository product_dao;

	/*
	 * 商品列表
	 */
	@Override
	public ResultVO productList() {
		List<ProductVO> end = new ArrayList<>();
		List<ProductMapper> findAll = product_dao.findByOrderByGenTimeDesc();
		for (ProductMapper productMapper : findAll) {
			ProductVO vo = new ProductVO();
			BeanUtils.copyProperties(productMapper, vo);
			String[] split = productMapper.getPicture().split(",");
			for (String string : split) {
				vo.getPictures().add(UrlEnums.IMG_URL.getUrl()+string);
			}
			end.add(vo);
		}
		return ResultVOUtil.success(end);
	}

	/*
	 * 新增商品
	 */
	@Override
	public ResultVO addProduct(Map<String, String> params,MultipartFile[] picture,MultipartFile details) {
		String detailsstr = ImguploadUtils.uploadImg(details, "product");
		String picturestr = ImguploadUtils.uploadImg(picture, "product");
		ProductMapper po = new ProductMapper();
		po.setName(params.get("name"));
		po.setPicture(picturestr);
		po.setDetails(detailsstr);
		po.setGenTime(new Date());
		product_dao.save(po);
		return productList();
	}

	/*
	 * 删除商品
	 */
	@Override
	public ResultVO deleteProduct(Map<String, String> params) {
		product_dao.deleteById(Integer.valueOf(params.get("id")));
		return productList();
	}

	/*
	 * 修改商品
	 */
	@Override
	public ResultVO editProduct(Map<String, String> params) {
		ProductMapper productMapper = product_dao.findById(Integer.valueOf(params.get("id"))).get();
		ProductVO vo = new ProductVO();
		BeanUtils.copyProperties(productMapper, vo);
		String[] split = productMapper.getPicture().split(",");
		for (String string : split) {
			vo.getPictures().add(UrlEnums.IMG_URL.getUrl()+string);
		}
		return ResultVOUtil.success(vo);
	}

	/*
	 * 商品修改后保存
	 */
	@Override
	public ResultVO saveProduct(Map<String, String> params,MultipartFile picture[],MultipartFile details) {
		ProductMapper productMapper = product_dao.findById(Integer.valueOf(params.get("id"))).get();
		productMapper.setGenTime(new Date());
		if(!StringUtils.isEmpty(params.get("name")))productMapper.setName(params.get("name"));
		if(picture!=null) {
			String[] split = productMapper.getPicture().split(",");
			for (String string : split) {
				File file = new File(UrlEnums.TOMCAT_IMG.getUrl()+"\\"+string);
				file.delete();
			}
			String picstr = ImguploadUtils.uploadImg(picture, "product");
			productMapper.setPicture(picstr);
		}
		if(details!=null) {
			String details2 = productMapper.getDetails();
			File file = new File(UrlEnums.TOMCAT_IMG.getUrl()+"\\"+details2);
			file.delete();
			String detailsstr = ImguploadUtils.uploadImg(details, "product");
			productMapper.setDetails(detailsstr);
		}
		product_dao.save(productMapper);
		
		return productList();
	}

	/*
	 * 商品模糊查询
	 */
	@Override
	public ResultVO findByName(Map<String, String> params) {
		List<ProductVO> end = new ArrayList<>();
		List<ProductMapper> findByNameLike = product_dao.findByNameLike("%"+params.get("name")+"%");
		for (ProductMapper productMapper : findByNameLike) {
			ProductVO vo = new ProductVO();
			BeanUtils.copyProperties(productMapper, vo);
			String[] split = productMapper.getPicture().split(",");
			for (String string : split) {
				vo.getPictures().add(UrlEnums.IMG_URL.getUrl()+string);
			}
			end.add(vo);
		}
		return ResultVOUtil.success(end);
	}
	
	
}
