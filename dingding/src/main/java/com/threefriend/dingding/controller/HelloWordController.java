package com.threefriend.dingding.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.threefriend.dingding.util.HttpUtils;
import com.threefriend.dingding.util.ResultVOUtil;
import com.threefriend.dingding.vo.ResultVO;

@RestController
public class HelloWordController {

	@SuppressWarnings("rawtypes")
	@RequestMapping("/test")
	public ResultVO test() {
		Map<String , String > end = new HashMap<String, String>();
		end.put("String", "Hello Word JavaEE");
		return ResultVOUtil.success(end);
	}
	
	
	@RequestMapping("/department")
	public void department() {
		
		@SuppressWarnings("unused")
		List<Object> depertmentIdList = new ArrayList<Object>();

		  String url = "";

		  url = "https://oapi.dingtalk.com/user/get_admin?access_token=4e9b5360680e35b8ae806fc05feddd2b";

		  JSONObject response = HttpUtils.httpGet(url);

		  System.out.println(response.toString());
		  JSONArray jsonArray = response.getJSONArray("adminList");
		  
		  for (int i=0; i<jsonArray.size(); i++) {

		  JSONObject jsonObject = (JSONObject) jsonArray.get(i);
		  
		  System.out.println(jsonObject.get("userid"));

		  //depertmentIdList.add(jsonObject.get("id").toString());

		  }
		  
	}
	  
}
