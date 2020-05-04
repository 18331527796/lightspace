package com.threefriend.lightspace.service.Impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.enums.UrlEnums;
import com.threefriend.lightspace.repository.PartnershipRepository;
import com.threefriend.lightspace.service.PartnershipService;
import com.threefriend.lightspace.util.ImguploadUtils;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.mapper.PartnershipMapper;
import com.threefriend.lightspace.xcx.util.XcxDecryptUtils;

/**
 *	合作商业务逻辑实现
 */
@Service
public class PartnershipServiceImpl implements PartnershipService{

	@Autowired
	private PartnershipRepository partnership_dao;

	@Override
	public ResultVO partnershipList() {
		return ResultVOUtil.success(partnership_dao.findAll());
	}

	@Override
	public ResultVO addPartnership(MultipartFile file,Map<String, String> params,HttpServletRequest request) {
		String imgurl= UrlEnums.IMG_URL.getUrl()+ImguploadUtils.uploadImg(file,"partnership");
		PartnershipMapper partner = new PartnershipMapper();
		partner.setAddress(params.get("address"));
		partner.setName(params.get("name"));
		partner.setPhone(params.get("phone"));
		partner.setImgurl(imgurl);
		if(!StringUtils.isEmpty(params.get("details"))) partner.setDetails(params.get("details"));
		partnership_dao.save(partner);
		return ResultVOUtil.success(partnership_dao.findAll());
	}

	@Override
	public ResultVO deletePartnership(Map<String, String> params) {
		partnership_dao.deleteById(Integer.valueOf(params.get("id")));
		return ResultVOUtil.success(partnership_dao.findAll());
	}



	
	
}
