package com.threefriend.schoolclient.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.threefriend.lightspace.vo.ResultVO;

/**
 * 小程序生成二维码
 * @author Administrator
 *
 */
public interface SchoolCreateQrcoreService {

	ResultVO download(HttpServletResponse response,Map<String, String> params,HttpSession session);
	
}
