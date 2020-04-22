package com.threefriend.lightspace.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.threefriend.lightspace.vo.ResultVO;

/**
 * 小程序生成二维码
 * @author Administrator
 *
 */
public interface CreateQrcoreService {

	ResultVO download(HttpServletResponse response,Map<String, String> params);
}
