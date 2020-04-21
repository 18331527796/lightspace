package com.threefriend.lightspace.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * 小程序生成二维码
 * @author Administrator
 *
 */
public interface CreateQrcoreService {

	void download(HttpServletResponse response,Map<String, String> params);
}
