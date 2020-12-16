package com.threefriend.train.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.lightspace.enums.AccountEnums;
import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.mapper.train.TrainChildrenWordMapper;
import com.threefriend.lightspace.mapper.train.TrainClertMapper;
import com.threefriend.lightspace.mapper.train.TrainChildrenCombinationMapper;
import com.threefriend.lightspace.mapper.train.TrainChildrenMapper;
import com.threefriend.lightspace.mapper.train.TrainParentChildrenMapper;
import com.threefriend.lightspace.mapper.train.TrainParentMapper;
import com.threefriend.lightspace.mapper.xcx.ParentMapper;
import com.threefriend.lightspace.repository.train.TrainChildrenWordRepository;
import com.threefriend.lightspace.repository.train.TrainChildrenCombinationRepository;
import com.threefriend.lightspace.repository.train.TrainChildrenRepository;
import com.threefriend.lightspace.repository.train.TrainParentRepository;
import com.threefriend.lightspace.repository.train.TrainPenterChildrenRepositroy;
import com.threefriend.lightspace.util.MyBeanUtils;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.util.WeChatUtils;
import com.threefriend.lightspace.xcx.util.XcxDecryptUtils;
import com.threefriend.train.service.TrainParentService;

@Service
public class TrainPartenrServiceImpl implements TrainParentService{

	@Autowired
	private TrainParentRepository parent_dao;
	@Autowired
	private TrainChildrenRepository children_dao;
	@Autowired
	private TrainChildrenWordRepository word_dao;
	@Autowired
	private TrainPenterChildrenRepositroy p_c_dao;
	@Autowired
	private TrainChildrenCombinationRepository c_c_dao;

	@Override
	public ResultVO loginXcx(Map<String, String> params) throws Exception {
		//从微信的接口获取sessionkey openId
		Map getsessionKey = WeChatUtils.getsessionKey(AccountEnums.TRAINAPIKEY.getUrl(), AccountEnums.TRAINSECRETKEY.getUrl(), params.get("code"));
		params.put("sessionKey", getsessionKey.get("sessionkey").toString());
		String openId = getsessionKey.get("openId").toString();
		//查一下有这个用户吗
		TrainParentMapper findByOpenId = parent_dao.findByOpenId(openId);
		if(findByOpenId==null) {
			Map<String, Object> userData = getUserData(params);
			TrainParentMapper parent = new TrainParentMapper();
			parent.setOpenId(openId);
			parent.setAvatarUrl(userData.get("avatarUrl").toString());
			parent.setNickName(userData.get("nickName").toString());
			parent.setGenTime(new Date());
			parent_dao.save(parent);
			return ResultVOUtil.success(parent);
		}
		return ResultVOUtil.success(findByOpenId);
	}
	
	
	/* 
	 * 绑定手机号
	 */
	@Override
	public ResultVO bindingPhone(Map<String, String> params) throws Exception {
		//从微信的接口获取sessionkey openId
		Map getsessionKey = WeChatUtils.getsessionKey(AccountEnums.TRAINAPIKEY.getUrl(), AccountEnums.TRAINSECRETKEY.getUrl(), params.get("code"));
		System.out.println(params.get("code"));
		params.put("sessionKey", getsessionKey.get("sessionkey").toString());
		String phoneDate = getPhoneDate(params);
		if(StringUtils.isEmpty(phoneDate))ResultVOUtil.error(ResultEnum.BINDINGPHONE_ERROR.getStatus(), ResultEnum.BINDINGPHONE_ERROR.getMessage());
		TrainParentMapper parent = parent_dao.findByOpenId(params.get("openId"));
		parent.setPhone(phoneDate);
		parent_dao.save(parent);
	    String phone="";
	    phone=phoneDate.substring(0, 3)+"****"+phoneDate.substring(7, phoneDate.length());
		return ResultVOUtil.success(phone);
	}
	
	/* 
	 * 获取手机号
	 */
	public String getPhoneDate(Map<String, String> params) throws Exception {
		String sessionKey=params.get("sessionKey");
		String encryptedData = params.get("encryptedData");
		String iv = params.get("iv");
		System.out.println(sessionKey+"+"+encryptedData+"+"+iv);
		Map<String, Object> userInfo = XcxDecryptUtils.getUserInfo(encryptedData, sessionKey, iv);
		String phone = (String) userInfo.get("purePhoneNumber"); //手机号
		return phone;
	}
	
	public Map<String, Object> getUserData(Map<String, String> params) throws Exception {
		String sessionKey=params.get("sessionKey");
		String encryptedData = params.get("encryptedData");
		String iv = params.get("iv");
		Map<String, Object> userInfo = XcxDecryptUtils.getUserInfo(encryptedData, sessionKey, iv);
		return userInfo;
	}

	@Override
	public ResultVO childrenList(Map<String, String> params) {
		List<Integer> ids = new ArrayList<Integer>();
		List<TrainParentChildrenMapper> findByOpenId = p_c_dao.findByOpenId(params.get("openId"));
		for (TrainParentChildrenMapper po : findByOpenId) {
			ids.add(po.getChildrenId());
		}
		List<TrainChildrenMapper> childrenList = children_dao.findAllById(ids);
		if(childrenList.size()<1) return ResultVOUtil.error(ResultEnum.BINDINGSTUDENT_ERROR.getStatus(),ResultEnum.BINDINGSTUDENT_ERROR.getMessage());
		return ResultVOUtil.success(childrenList);
	}

	@Override
	public ResultVO addChildren(TrainChildrenMapper vo) {
		System.out.println(vo.toString());
		if(vo.getId()!=null) {
			TrainChildrenMapper children = children_dao.findById(vo.getId()).get();
			MyBeanUtils.copyProperties(vo, children);
			children_dao.save(children);
			return ResultVOUtil.success(children);
		}
		TrainChildrenMapper po = new TrainChildrenMapper();
		MyBeanUtils.copyProperties(vo, po);
		children_dao.save(po);
		TrainParentChildrenMapper p_c = new TrainParentChildrenMapper(po.getId(),po.getOpenId());
		p_c_dao.save(p_c);
		return ResultVOUtil.success(po.getId());
	}

	@Override
	public ResultVO deleteChildren(Map<String, String> params) {
		TrainChildrenMapper children = children_dao.findByIdAndOpenId(Integer.valueOf(params.get("childrenId")),params.get("openId"));
		children.setOpenId(null);
		children_dao.save(children);
		List<TrainChildrenMapper> childrenList = children_dao.findByOpenId(params.get("openId"));
		return ResultVOUtil.success(childrenList);
	}

	@Override
	public ResultVO editChildren(TrainChildrenMapper vo) {
		TrainChildrenMapper children = children_dao.findById(vo.getId()).get();
		return ResultVOUtil.success(children);
	}

	@Override
	public ResultVO childrenWordList(TrainChildrenMapper vo) {
		List<TrainChildrenWordMapper> wordList = word_dao.findByChildrenIdOrderByIdDesc(vo.getId());
		return ResultVOUtil.success(wordList);
	}

	@Override
	public ResultVO getWord(TrainChildrenWordMapper vo) {
		TrainChildrenWordMapper word = word_dao.findById(vo.getId()).get();
		return ResultVOUtil.success(word);
	}
	
	

	@Override
	public ResultVO bindingChildren(TrainParentChildrenMapper vo) {
		List<TrainParentChildrenMapper> po = p_c_dao.findByOpenIdAndChildrenId(vo.getOpenId(),vo.getChildrenId());
		if(po!=null) return ResultVOUtil.success();
		TrainParentChildrenMapper p_c = new TrainParentChildrenMapper(vo.getChildrenId(), vo.getOpenId());
		p_c_dao.save(p_c);
		return ResultVOUtil.success();
	}

	@Override
	public ResultVO combinationSuccess(TrainChildrenCombinationMapper vo) {
		TrainChildrenCombinationMapper po = c_c_dao.findById(vo.getId()).get();
		po.setIsSuccess(1);
		c_c_dao.save(po);
		return ResultVOUtil.success();
	}
	
	
	@Override
	public ResultVO childrenCombinationList(Map<String, String> params) {
		List<TrainChildrenCombinationMapper> all = c_c_dao.findByChildrenIdOrderByIsOpen(Integer.valueOf(params.get("childrenId")));
		if(all.size()<1)return ResultVOUtil.success();
		TrainChildrenCombinationMapper isOpenPo = c_c_dao.findTopByIsOpenAndChildrenId(1,Integer.valueOf(params.get("childrenId")));
		if(isOpenPo==null) {
			if(all.get(0).getIsOpen()==2) {
				all.get(0).setIsOpen(1);
				c_c_dao.save(all.get(0));
			}
		}
		return ResultVOUtil.success(all);
	}
	
	
}
