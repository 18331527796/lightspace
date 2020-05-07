package com.threefriend.lightspace.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threefriend.lightspace.service.Impl.SpecificationsServiceImpl;
import com.threefriend.lightspace.vo.ResultVO;

/**
 * 规格控制器
 *
 *	盼月疏影影阑珊，册封载情情难断。
	斤酒抒歌歌乐惨，参悟者来来复还。
	不念睹物物飞散，昔日故人人安眠。
	目望星夜夜微淡，炎焚数年年期盼。
 */
@RestController
public class SpecificationsController {

	@Autowired
	private SpecificationsServiceImpl specifications_impl;
	
	/**
	 * 规格列表
	 * @param params
	 * @return
	 */
	@PostMapping("specificationsList")
	public ResultVO specificationsList(@RequestParam Map<String, String> params) {
		return specifications_impl.specificationsList(params);
	}
	
	/**
	 * 新增规格
	 * @param params
	 * @return
	 */
	@PostMapping("addSpecifications")
	public ResultVO addSpecifications(@RequestParam Map<String, String> params) {
		return specifications_impl.addSpecifications(params);
	}
	
	/**
	 * 修改规格
	 * @param params
	 * @return
	 */
	@PostMapping("editSpecifications")
	public ResultVO editSpecifications(@RequestParam Map<String, String> params) {
		return specifications_impl.editSpecifications(params);
	}
	
	/**
	 * 修改后保存规格
	 * @param params
	 * @return
	 */
	@PostMapping("saveSpecifications")
	public ResultVO saveSpecifications(@RequestParam Map<String, String> params) {
		return specifications_impl.saveSpecifications(params);
	}
	
	/**
	 * 删除规格
	 * @param params
	 * @return
	 */
	@PostMapping("deleteSpecifications")
	public ResultVO deleteSpecifications(@RequestParam Map<String, String> params) {
		return specifications_impl.deleteSpecifications(params);
	}
	
	
}
