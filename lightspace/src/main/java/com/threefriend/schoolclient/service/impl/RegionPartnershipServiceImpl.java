package com.threefriend.schoolclient.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.enums.UrlEnums;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.mapper.xcx.PartnershipMapper;
import com.threefriend.lightspace.repository.PartnershipRepository;
import com.threefriend.lightspace.repository.RegionRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.service.PartnershipService;
import com.threefriend.lightspace.util.ImguploadUtils;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;

/**
 *	合作商业务逻辑实现
 */
@Service
public class RegionPartnershipServiceImpl implements PartnershipService{

	@Autowired
	private PartnershipRepository partnership_dao;
	@Autowired
	private RegionRepository region_dao;
	@Autowired
	private StudentRepository student_dao;

	@Override
	public ResultVO partnershipList(Map<String, String> params,HttpSession session) {
		Integer regionId = Integer.valueOf(session.getAttribute("regionId").toString());
		int page = 0 ;
		if(!StringUtils.isEmpty(params.get("page")))page = Integer.valueOf(params.get("page")) - 1 ;
		Page<PartnershipMapper> end = partnership_dao.findByRegionId(regionId,PageRequest.of(page, 10));
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO addPartnership(MultipartFile file,Map<String, String> params,HttpServletRequest request,HttpSession session) {
		Integer regionId = Integer.valueOf(session.getAttribute("regionId").toString());
		String imgurl= UrlEnums.IMG_URL.getUrl()+ImguploadUtils.uploadImg(file,"partnership");
		PartnershipMapper partner = new PartnershipMapper();
		partner.setAddress(params.get("address"));
		partner.setName(params.get("name"));
		partner.setPhone(params.get("phone"));
		partner.setImgurl(imgurl);
		partner.setRegionId(regionId);
		partner.setRegionName(region_dao.findById(regionId).get().getName());
		if(!StringUtils.isEmpty(params.get("details"))) partner.setDetails(params.get("details"));
		partnership_dao.save(partner);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO deletePartnership(Map<String, String> params) {
		partnership_dao.deleteById(Integer.valueOf(params.get("id")));
		return ResultVOUtil.success();
	}

	public ResultVO getAllPartnership(HttpSession session) {
		Integer regionId = Integer.valueOf(session.getAttribute("regionId").toString());
		List<PartnershipMapper> end = partnership_dao.findByRegionId(regionId);
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO undetectedList(HttpSession session) {
		Integer regionId = Integer.valueOf(session.getAttribute("regionId").toString());
		List<StudentMapper> student = student_dao.findByRegionId(regionId);
		List<StudentMapper> end = new ArrayList<>();
		for (StudentMapper s : student) {
			if(s.getLastTime()==null||s.getVisionLeftStr()==null||s.getVisionRightStr()==null) {
				end.add(s);
			}
		}
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO badList(HttpSession session) {
		Integer regionId = Integer.valueOf(session.getAttribute("regionId").toString());
		List<StudentMapper> student = student_dao.findByRegionId(regionId);
		List<StudentMapper> end = new ArrayList<>();
		Double LEFT,RIGHT,AVG;
		for (StudentMapper s : student) {
			if(s.getLastTime()==null||s.getVisionLeftStr()==null||s.getVisionRightStr()==null) {
				continue;
			}
			
			LEFT = s.getVisionLeftStr();
			RIGHT = s.getVisionRightStr();
			
			if(LEFT>=RIGHT) {
				AVG = RIGHT;
			}else {
				AVG = LEFT;
			}
			if(AVG<1.0)end.add(s);
		}
		return ResultVOUtil.success(end);
	}
	
	
}
