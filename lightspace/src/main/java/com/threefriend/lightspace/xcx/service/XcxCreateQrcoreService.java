package com.threefriend.lightspace.xcx.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * 小程序生成二维码
 * @author Administrator
 *
 */
public interface XcxCreateQrcoreService {

	void download(HttpServletResponse response,Map<String, String> params);
}
