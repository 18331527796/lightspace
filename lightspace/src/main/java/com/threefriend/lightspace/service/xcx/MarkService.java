package com.threefriend.lightspace.service.xcx;

import java.text.ParseException;
import java.util.Map;

import com.threefriend.lightspace.vo.ResultVO;

/**
 *   每日签到
 */
public interface MarkService {

	ResultVO Signin(Map<String, String> params)throws ParseException;
}
