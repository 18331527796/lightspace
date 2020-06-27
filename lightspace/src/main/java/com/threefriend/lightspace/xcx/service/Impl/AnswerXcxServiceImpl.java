package com.threefriend.lightspace.xcx.service.Impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.enums.UrlEnums;
import com.threefriend.lightspace.mapper.AnswerConfigMapper;
import com.threefriend.lightspace.mapper.xcx.AnswerMapper;
import com.threefriend.lightspace.mapper.xcx.IntegralMapper;
import com.threefriend.lightspace.repository.AnswerConfigRepository;
import com.threefriend.lightspace.repository.AnswerRepository;
import com.threefriend.lightspace.repository.IntegralRepository;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.AnswerXcxVO;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.AnswerXcxService;

@Service
public class AnswerXcxServiceImpl implements AnswerXcxService{

	@Autowired
	private AnswerRepository answer_dao;
	@Autowired
	private IntegralRepository integral_dao;
	@Autowired
	private AnswerConfigRepository config_dao;

	@Override
	public ResultVO list() {
		List<AnswerXcxVO> end = new ArrayList<>();
		List<AnswerMapper> poList = new ArrayList<>();
		int levelOne = answer_dao.countByLevel(1);
		int levelTwo = answer_dao.countByLevel(2);
		int levelThree = answer_dao.countByLevel(3);
		if(levelTwo<2||levelThree<1) {
			poList = answer_dao.findByLevel(1, 5);
		}else {
			poList = answer_dao.findByLevel(1, 2);
			poList.addAll(answer_dao.findByLevel(2, 2));
			poList.addAll(answer_dao.findByLevel(3, 1));
		}
		//测试用的方法
		/*poList.addAll(answer_dao.findByType(1,PageRequest.of(41, 4,Sort.by("id").ascending())).getContent());
		poList.addAll(answer_dao.findByLevel(3, 1));*/
		for (AnswerMapper answerMapper : poList) {
			AnswerXcxVO vo = new AnswerXcxVO(answerMapper);
			end.add(vo);
		}
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO submit(Map<String, String> params) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date begin = format.parse(DateFormatUtils.format(new Date(), "yyyy-MM-dd 00:00:00"));
		Date end = format.parse(DateFormatUtils.format(new Date(), "yyyy-MM-dd 23:59:59"));
		Long number = Long.valueOf(params.get("number"));
		Integer studentId = Integer.valueOf(params.get("studentId"));
		int count = integral_dao.countByStudentIdAndDetailedAndGenTimeBetween(studentId,"获得爱眼答题奖励",begin,end);
		if((number==null||number==0)&&count==0)return ResultVOUtil.error(ResultEnum.ANSWER_ZERO_ERROR.getStatus(), ResultEnum.ANSWER_ZERO_ERROR.getMessage());
		
		if(count>0) return ResultVOUtil.error(ResultEnum.ANSWER_ERROR.getStatus(),ResultEnum.ANSWER_ERROR.getMessage() );
		
		IntegralMapper po = new IntegralMapper();
		po.setGenTime(new Date());
		po.setState(1);
		po.setDetailed("获得爱眼答题奖励");
		po.setIntegral(number);
		po.setStudentId(studentId);
		integral_dao.save(po);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO answerConfig() {
		AnswerConfigMapper config = config_dao.findById(1).get();
		config.setPic(UrlEnums.IMG_URL.getUrl()+config.getPic());
		return ResultVOUtil.success(config);
	}
}
