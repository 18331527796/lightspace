package com.threefriend.lightspace.service.Impl.xcx;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.enums.UrlEnums;
import com.threefriend.lightspace.mapper.PartnershipMapper;
import com.threefriend.lightspace.repository.PartnershipRepository;
import com.threefriend.lightspace.service.xcx.PartnershipXcxService;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.util.xcx.XcxDecryptUtils;
import com.threefriend.lightspace.vo.ResultVO;

@Service
public class PartnershipXcxServiceImpl implements PartnershipXcxService{
	@Autowired
	private PartnershipRepository partnership_dao;

	@Override
	public ResultVO partnershipList() {
		return ResultVOUtil.success(partnership_dao.findAll());
	}

	

}
