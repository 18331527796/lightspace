package com.threefriend.dingding.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.threefriend.constants.DingDingAccessToken;
import com.threefriend.constants.DingDingConstants;
import com.threefriend.dingding.mapper.UserTaskRecordMapper;

public class DingDingUtils {
	
	

	public static String getToken() {
		String url = "https://oapi.dingtalk.com/gettoken?appkey="+DingDingConstants.AppKey+"&appsecret="+DingDingConstants.AppSecret;
		JSONObject response = HttpUtils.httpGet(url);
		String token = response.get("access_token").toString();
		return token;
	}
	
	public static JSONObject getUserInfo(String token,String code) {
		Map<String , String> param = new HashMap<String, String>();
		String url = "https://oapi.dingtalk.com/topapi/v2/user/getuserinfo?access_token="+token;
		param.put("code", code);
		JSONObject httpPost = HttpUtils.httpPost(url, JSONObject.toJSONString(param));
		JSONObject jsonObject = httpPost.getJSONObject("result");
		return jsonObject;
	}
	
	public static JSONArray getDeptInfo(String token) {
		Map<String , Integer> param = new HashMap<String, Integer>();
		String url = "https://oapi.dingtalk.com/topapi/v2/department/listsub?access_token="+token;
		param.put("dept_id", 1);
		JSONObject httpPost = HttpUtils.httpPost(url, JSONObject.toJSONString(param));
		JSONArray jsonArray = httpPost.getJSONArray("result");
		return jsonArray;
	}
	
	public static List<UserTaskRecordMapper> getDeptUserList(String token,String dept_id,Integer next_cursor) {
		List<UserTaskRecordMapper> userList = new ArrayList<>();
		Map<String , Object> param = new HashMap<String, Object>();
		String url = "https://oapi.dingtalk.com/topapi/user/listsimple?access_token="+token;
		param.put("dept_id", dept_id);
		param.put("cursor", next_cursor==null?0:next_cursor);
		param.put("size", 10);
		JSONObject httpPost = HttpUtils.httpPost(url, JSONObject.toJSONString(param));
		JSONObject result = httpPost.getJSONObject("result");
		boolean has = Boolean.valueOf(result.get("has_more").toString());
		JSONArray jsonArray = result.getJSONArray("list");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject user = (JSONObject) jsonArray.get(i);
			if(user.get("userid").toString().indexOf("-")!=-1)continue;
			UserTaskRecordMapper userpojo = user.toJavaObject(UserTaskRecordMapper.class);
			userList.add(userpojo);
		}
		if(has) {
			List<UserTaskRecordMapper> deptUserList = getDeptUserList(token,dept_id,Integer.valueOf(result.get("next_cursor").toString()));
			userList.addAll(deptUserList);
		}
		return userList;
	}
	
	public static String isLeader(String token,String user_id,String dept_id) {
		String isLeader = "false";
		Map<String , String> param = new HashMap<String, String>();
		String url = "https://oapi.dingtalk.com/topapi/v2/user/get?access_token="+token;
		param.put("userid", user_id);
		JSONObject httpPost = HttpUtils.httpPost(url, JSONObject.toJSONString(param));
		if(!httpPost.get("errcode").toString().equals("0")) return isLeader;
		JSONObject result = httpPost.getJSONObject("result");
		JSONArray leader_in_dept = result.getJSONArray("leader_in_dept");
		try {
			for (int i = 0; i < leader_in_dept.size(); i++) {
				JSONObject user = (JSONObject) leader_in_dept.get(i);
				if(user.get("dept_id").toString().equals(dept_id)) {
					isLeader =user.get("leader").toString();
				}
			}
		} catch (Exception e) {
			return isLeader;
		}
		return isLeader;
	}
	
	public static String isLeader(String token,String user_id) {
		Map<String , String> param = new HashMap<String, String>();
		String url = "https://oapi.dingtalk.com/topapi/v2/user/get?access_token="+token;
		param.put("userid", user_id);
		JSONObject httpPost = HttpUtils.httpPost(url, JSONObject.toJSONString(param));
		JSONObject result = httpPost.getJSONObject("result");
		JSONArray leader_in_dept = result.getJSONArray("leader_in_dept");
		String isLeader = "";
		for (int i = 0; i < leader_in_dept.size(); i++) {
			JSONObject user = (JSONObject) leader_in_dept.get(i);
			if(user.get("leader").toString().equals("true")) {
				isLeader =user.get("leader").toString();
			}
		}
		return isLeader;
	}
	
	public static boolean attendance(String userId, Date begin , Date end) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String token = DingDingAccessToken.getToken();
		List<String> userIds = Arrays.asList(userId);
		Map<String , Object> param = new HashMap<String, Object>();
		boolean timeResult = false;
		String beginStr = f.format(begin);
		String endStr = f.format(end);
		String url = "https://oapi.dingtalk.com/attendance/listRecord?access_token="+token;
		param.put("userIds", userIds);
		param.put("checkDateFrom", beginStr);
		param.put("checkDateTo", endStr);
		JSONObject httpPost = HttpUtils.httpPost(url, JSONObject.toJSONString(param));
		JSONArray recordresult = httpPost.getJSONArray("recordresult");
		for (int i = 0; i < recordresult.size(); i++) {
			JSONObject record = (JSONObject) recordresult.get(i);
			if(record.get("checkType").toString().equals("OnDuty")||!record.get("timeResult").toString().equals("NotSigned")) {
				timeResult = true;
			}
		}
		return timeResult;
	}

}
