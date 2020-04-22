package com.threefriend.lightspace.service;

import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.vo.wechatmenu.Menu;

public interface WechatMenuService {

	Menu getMenu();
	
	ResultVO sendPost();
}
