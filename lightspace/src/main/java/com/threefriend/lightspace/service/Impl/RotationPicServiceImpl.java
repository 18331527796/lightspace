package com.threefriend.lightspace.service.Impl;

import java.io.File;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.enums.UrlEnums;
import com.threefriend.lightspace.mapper.RotationPicMapper;
import com.threefriend.lightspace.repository.ProductRepository;
import com.threefriend.lightspace.repository.RotationPicRepository;
import com.threefriend.lightspace.service.RotationPicService;
import com.threefriend.lightspace.util.ImguploadUtils;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;

@Service
public class RotationPicServiceImpl implements RotationPicService{

	@Autowired
	private RotationPicRepository RotationPic_dao;
	@Autowired
	private ProductRepository product_dao;
	
	/*  
	 * 查
	 */
	@Override
	public ResultVO RotationPicList(Map<String, String> params) {
		int page = 0 ;
		if(!StringUtils.isEmpty(params.get("page")))page = Integer.valueOf(params.get("page"))-1;
		Page<RotationPicMapper> findAll = RotationPic_dao.findAll(PageRequest.of(page, 10));
		return ResultVOUtil.success(findAll);
	}

	/*  
	 * 增
	 */
	@Override
	public ResultVO addRotationPic(Map<String, String> params,MultipartFile pic) {
		String path = ImguploadUtils.uploadImg(pic, "rotation");
		Integer productId = Integer.valueOf(params.get("productId"));
		RotationPicMapper po = new RotationPicMapper();
		po.setProductId(productId);
		po.setProductName(product_dao.findById(productId).get().getName());
		po.setPath(path);
		po.setGenTime(new Date());
		RotationPic_dao.save(po);
		return ResultVOUtil.success();
	}

	/*  
	 * 改
	 */
	@Override
	public ResultVO editRotationPic(Map<String, String> params) {
		RotationPicMapper po = RotationPic_dao.findById(Integer.valueOf(params.get("id"))).get();
		po.setPath(UrlEnums.IMG_URL.getUrl()+po.getPath());
		return ResultVOUtil.success(po);
	}

	
	/*  
	 * 改存
	 */
	@Override
	public ResultVO saveRotationPic(Map<String, String> params,MultipartFile pic) {
		String path = "";
		RotationPicMapper po = RotationPic_dao.findById(Integer.valueOf(params.get("id"))).get();
		if(pic!=null) {
			File file = new File(UrlEnums.TOMCAT_IMG.getUrl()+"\\"+po.getPath());
			file.delete();
			path = ImguploadUtils.uploadImg(pic, "rotation");
			po.setPath(path);
		}
		if(!StringUtils.isEmpty(params.get("productId"))) {
			Integer productId = Integer.valueOf(params.get("productId"));
		po.setProductId(productId);
		po.setProductName(product_dao.findById(productId).get().getName());
		}
		po.setGenTime(new Date());
		RotationPic_dao.save(po);
		return ResultVOUtil.success();
	}

	
	/*  
	 * 删
	 */
	@Override
	public ResultVO deleteRotationPic(Map<String, String> params) {
		RotationPicMapper po = RotationPic_dao.findById(Integer.valueOf(params.get("id"))).get();
		File file = new File(UrlEnums.TOMCAT_IMG.getUrl()+"\\"+po.getPath());
		file.delete();
		RotationPic_dao.delete(po);
		return ResultVOUtil.success();
	}
	

}
