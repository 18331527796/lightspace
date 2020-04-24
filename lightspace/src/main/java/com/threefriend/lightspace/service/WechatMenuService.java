package com.threefriend.lightspace.service;

import java.util.Map;

import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.vo.wechatmenu.Menu;

public interface WechatMenuService {

	Menu getMenu();
	
	ResultVO sendPost();
	
	ResultVO menuList();
	
	ResultVO addMenu(Map<String, String> params);
	
	ResultVO editMenu(Map<String, String> params);
	
	ResultVO saveMenu(Map<String, String> params);
	
	ResultVO deleteMenu(Map<String, String> params);
}
