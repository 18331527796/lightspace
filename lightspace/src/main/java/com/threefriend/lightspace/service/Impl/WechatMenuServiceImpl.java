package com.threefriend.lightspace.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.enums.AccountEnums;
import com.threefriend.lightspace.mapper.WechatMenuMapper;
import com.threefriend.lightspace.repository.WechatMenuRepository;
import com.threefriend.lightspace.service.WechatMenuService;
import com.threefriend.lightspace.util.RedisUtils;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.util.xcx.WeChatUtils;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.vo.WechatMenuVO;
import com.threefriend.lightspace.vo.wechatmenu.CommonButton;
import com.threefriend.lightspace.vo.wechatmenu.ComplexButton;
import com.threefriend.lightspace.vo.wechatmenu.Menu;
import com.threefriend.lightspace.vo.wechatmenu.MiniprogramButton;
import com.threefriend.lightspace.vo.wechatmenu.ViewButton;
@Service
public class WechatMenuServiceImpl implements WechatMenuService{
	@Resource
	private RedisUtils redisUtil;
	@Autowired
	private WechatMenuRepository dao;
	
	@Override
	public Menu getMenu() {
		Menu menu = new Menu();
		List<WechatMenuMapper> findAll = dao.findAll();
		for (WechatMenuMapper Mapper : findAll) {
			//遍历循环所有的菜单 查找父级
			if(Mapper.getpId()==0) {
				//新建父级的子级菜单
		        ComplexButton Btn1 = new ComplexButton();
		        Btn1.setName(Mapper.getName());
		        //查找属于这个父级的所有子级
				for (WechatMenuMapper cpo : findAll) {
					if("view".equals(cpo.getType())&&cpo.getpId()==Mapper.getId()) {
						ViewButton bt = new ViewButton();
						bt.setName(cpo.getName());
						bt.setType(cpo.getType());
						bt.setUrl(cpo.getUrl());
						Btn1.getSub_button().add(bt);
					} else if("click".equals(cpo.getType())&&cpo.getpId()==Mapper.getId()) {
						CommonButton bt = new CommonButton();
						bt.setName(cpo.getName());
						bt.setType(cpo.getType());
						bt.setKey(cpo.getId()+"");
						Btn1.getSub_button().add(bt);
					} else if("miniprogram".equals(cpo.getType())&&cpo.getpId()==Mapper.getId()) {
						MiniprogramButton bt = new MiniprogramButton();
						bt.setAppid(AccountEnums.APIKEY.getUrl());
						bt.setName(cpo.getName());
						bt.setPagepath(cpo.getPagepath());
						bt.setType(cpo.getType());
						bt.setUrl("http://mp.weixin.qq.com");
						Btn1.getSub_button().add(bt);
					}
				}
				menu.getButton().add(Btn1);
			}
		}
		return menu;
	}

	@Override
	public ResultVO sendPost() {
		String ACCESS_TOKEN = redisUtil.get("GZHTOKEN");
		if (ACCESS_TOKEN == null || "".equals(ACCESS_TOKEN)) {
			ACCESS_TOKEN = WeChatUtils.findAccessToken(AccountEnums.GZHAPPID.getUrl(), AccountEnums.GZHSECRET.getUrl());
			redisUtil.setValueTime("GZHTOKEN", ACCESS_TOKEN, 7000);
		}
		// 调用接口创建菜单
        int result = WeChatUtils.createMenu(getMenu(), ACCESS_TOKEN);

        // 判断菜单创建结果
        if (0 == result)
            System.out.println("菜单创建成功！");
        else
        	System.out.println("菜单创建失败，错误码：" + result);

		return ResultVOUtil.success();
	}

	@Override
	public ResultVO addMenu(Map<String, String> params) {
		WechatMenuMapper po = new WechatMenuMapper();
		po.setName(params.get("name"));
		po.setpId(Integer.valueOf(params.get("pId")));
		po.setType(params.get("type"));
		if(!StringUtils.isEmpty(params.get("pagepath")))po.setPagepath(params.get("pagepath"));
		if(!StringUtils.isEmpty(params.get("url")))po.setUrl(params.get("url"));
		return menuList();
	}

	@Override
	public ResultVO menuList() {
		List<WechatMenuVO> end = new ArrayList<WechatMenuVO>();
		List<WechatMenuMapper> findAll = dao.findAll();
		for (WechatMenuMapper Mapper : findAll) {
			if(Mapper.getpId()==0) {
				WechatMenuVO po = new WechatMenuVO();
				BeanUtils.copyProperties(Mapper, po);
				for (WechatMenuMapper children : findAll) {
					if(children.getpId()==Mapper.getId())po.getChildren().add(children);
				}
				end.add(po);
			}
		}
		return ResultVOUtil.success(end);
	}

	@Override
	public ResultVO editMenu(Map<String, String> params) {
		return ResultVOUtil.success(dao.findById(Integer.valueOf(params.get("id"))).get());
	}

	@Override
	public ResultVO saveMenu(Map<String, String> params) {
		WechatMenuMapper po = dao.findById(Integer.valueOf(params.get("id"))).get();
		if(!StringUtils.isEmpty(params.get("name")))po.setName(params.get("name"));
		if(!StringUtils.isEmpty(params.get("pId")))po.setpId(Integer.valueOf(params.get("pId")));
		if(!StringUtils.isEmpty(params.get("type")))po.setType(params.get("type"));
		if(!StringUtils.isEmpty(params.get("pagepath")))po.setPagepath(params.get("pagepath"));
		if(!StringUtils.isEmpty(params.get("url")))po.setUrl(params.get("url"));
		dao.save(po);
		return menuList();
	}

	@Override
	public ResultVO deleteMenu(Map<String, String> params) {
		dao.deleteById(Integer.valueOf(params.get("id")));
		return menuList();
	}

}
