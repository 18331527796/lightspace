package com.threefriend.lightspace.service.Impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.enums.AnswerTypeEnums;
import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.enums.UrlEnums;
import com.threefriend.lightspace.mapper.AnswerConfigMapper;
import com.threefriend.lightspace.mapper.xcx.AnswerMapper;
import com.threefriend.lightspace.repository.AnswerConfigRepository;
import com.threefriend.lightspace.repository.AnswerRepository;
import com.threefriend.lightspace.service.AnswerService;
import com.threefriend.lightspace.util.ImguploadUtils;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.AnswerVO;
import com.threefriend.lightspace.vo.ResultVO;

/**
 * 答题业务逻辑接口实现类
 * @author Administrator
 *
 */
@Service
public class AnswerServiceImpl implements AnswerService{

	@Autowired
	private AnswerRepository answer_dao;
	@Autowired
	private ReadAnswerExcel excel_dao;
	
	
	/* 批量导入	 */
	@Override
	public ResultVO addAnswer(MultipartFile file) {
		List<AnswerMapper> AnswerInfo = excel_dao.getAnswerInfo(file);
		if(AnswerInfo==null) {
			return ResultVOUtil.error(ResultEnum.READEXCEL_ERROR.getStatus(), ResultEnum.READEXCEL_ERROR.READEXCEL_ERROR.getMessage());
		}
		
        for (AnswerMapper AnswerMapper : AnswerInfo) {
        	answer_dao.save(AnswerMapper);
        }
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO delete(Map<String, String> params) {
		answer_dao.deleteById(Integer.valueOf(params.get("id")));
		return ResultVOUtil.success();
	}


	@Override
	public ResultVO list(Map<String, String> params) {
		int page = 0 ;
		int type = AnswerTypeEnums.SINGLE.getCode();
		if(!StringUtils.isEmpty(params.get("page")))page = Integer.valueOf(params.get("page")) - 1 ;
		if(!StringUtils.isEmpty(params.get("type")))type = Integer.valueOf(params.get("type"));
		Page<AnswerMapper> findAll = answer_dao.findByType(type,PageRequest.of(page, 10));
		List<AnswerVO> endlist = new ArrayList<>();
		for (AnswerMapper answerMapper : findAll.getContent()) {
			AnswerVO vo = new AnswerVO(answerMapper);
			endlist.add(vo);
		}
		Page<AnswerVO> end = new PageImpl<>(endlist, findAll.getPageable(), findAll.getTotalElements());
		return ResultVOUtil.success(end);
	}


}
