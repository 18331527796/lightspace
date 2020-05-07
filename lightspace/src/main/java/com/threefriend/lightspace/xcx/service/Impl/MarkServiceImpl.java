package com.threefriend.lightspace.xcx.service.Impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.mapper.xcx.IntegralMapper;
import com.threefriend.lightspace.mapper.xcx.MarkMapper;
import com.threefriend.lightspace.mapper.xcx.ParentMapper;
import com.threefriend.lightspace.repository.IntegralRepository;
import com.threefriend.lightspace.repository.MarkRepository;
import com.threefriend.lightspace.repository.ParentRepository;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;

@Service
public class MarkServiceImpl {

	@Autowired
	private MarkRepository mark_dao;
	@Autowired
	private ParentRepository parent_dao;
	@Autowired
	private IntegralRepository integral_dao;
	
	public ResultVO Signin(Map<String, String> params) throws ParseException  {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date begin = format.parse(DateFormatUtils.format(new Date(), "yyyy-MM-dd 00:00:00"));
		Date end = format.parse(DateFormatUtils.format(new Date(), "yyyy-MM-dd 23:59:59"));
		String openId = params.get("openId");
		ParentMapper parent = parent_dao.findByOpenId(params.get("openId"));
		Integer parentId = parent.getId();
		List<MarkMapper> marks = mark_dao.findByParentId(parentId);
		if(marks.size()!=0) {
			MarkMapper markMapper = marks.get(0);
			Date genTime = markMapper.getGenTime();
			if(genTime.before(end) && genTime.after(begin)) {
				//这里返回的是 今日已签到 的提示
				return ResultVOUtil.error(ResultEnum.MARK_ERROR.getStatus(),ResultEnum.MARK_ERROR.getMessage() );
			}
			markMapper.setGenTime(new Date());
			mark_dao.save(markMapper);
		}else {
			MarkMapper mark = new MarkMapper();
			mark.setGenTime(new Date());
			mark.setParentId(parentId);
			mark_dao.save(mark);
		}
		IntegralMapper integral = new IntegralMapper();
		integral.setIntegral(5l);
		integral.setDetailed("每日签到");
		integral.setState(1);
		integral.setParentId(parentId);
		integral.setGenTime(new Date());
		integral_dao.save(integral);
		//这里返回的其实是成功 就是用的err方法带回去的不一样的提示
		return ResultVOUtil.error(ResultEnum.MARK_SUCCESS.getStatus(), ResultEnum.MARK_SUCCESS.getMessage());
	}
}
