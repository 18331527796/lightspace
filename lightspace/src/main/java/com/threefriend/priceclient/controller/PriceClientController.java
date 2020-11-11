package com.threefriend.priceclient.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.mapper.GlassesMapper;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.priceclient.service.impl.PriceClientServiceImpl;

@RestController
public class PriceClientController {

	@Autowired
	private PriceClientServiceImpl impl;
	
	@PostMapping("/seriesList")
	public ResultVO seriesList(@RequestParam Map<String, String> params) {
		return impl.seriesList(params);
	}
	
	@PostMapping("/addSeries")
	public ResultVO addSeries(@RequestParam Map<String, String> params,
							  @RequestParam(value="file",required=false) MultipartFile file) {
		return impl.addSeries(params, file);
	}
	
	@PostMapping("/editSeries")
	public ResultVO editSeries(@RequestParam Map<String, String> params) {
		return impl.editSeries(params);
	}
	
	@PostMapping("/saveSeries")
	public ResultVO saveSeries(@RequestParam Map<String, String> params,
			  				  @RequestParam(value="file",required=false) MultipartFile file) {
		return impl.saveSeries(params,file);
	}
	
	@PostMapping("/deleteSeries")
	public ResultVO deleteSeries(@RequestParam Map<String, String> params) {
		return impl.deleteSeries(params);
	}
	
	@PostMapping("/labelList")
	public ResultVO labelList(@RequestParam Map<String, String> params) {
		return impl.labelList(params);
	}
	
	@PostMapping("/addLabel")
	public ResultVO addLabel(@RequestParam Map<String, String> params) {
		return impl.addLabel(params);
	}
	
	@PostMapping("/editLabel")
	public ResultVO editLabel(@RequestParam Map<String, String> params) {
		return impl.editLabel(params);
	}
	
	@PostMapping("/saveLabel")
	public ResultVO saveLabel(@RequestParam Map<String, String> params) {
		return impl.saveLabel(params);
	}
	
	@PostMapping("/deleteLabel")
	public ResultVO deleteLabel(@RequestParam Map<String, String> params) {
		return impl.deleteLabel(params);
	}
	
	@PostMapping("/seriesProductList")
	public ResultVO seriesProductList(@RequestParam Map<String, String> params) {
		return impl.seriesProductList(params);
	}
	
	@PostMapping("/addSeriesProduct")
	public ResultVO addSeriesProduct(@RequestParam Map<String, String> params,
			  				  @RequestParam(value="file",required=false) MultipartFile file) {
		return impl.addSeriesProduct(params,file);
	}
	
	@PostMapping("/deleteSeriesProduct")
	public ResultVO deleteSeriesProduct(@RequestParam Map<String, String> params) {
		return impl.deleteSeriesProduct(params);
	}
	
	@PostMapping("/findSeriesBylabel")
	public ResultVO findSeriesBylabel(@RequestParam Map<String, String> params) {
		return impl.findSeriesBylabel(params);
	}
	
	@PostMapping("/addGlasses")
	public ResultVO addGlasses(@Valid GlassesMapper vo) {
		return impl.addGlasses(vo);
	}
	
	@PostMapping("/findGlassesBySeries")
	public ResultVO findGlassesBySeries(@RequestParam Map<String, String> params) {
		return impl.findGlassesBySeries(params);
	}
	
	@PostMapping("/editGlasses")
	public ResultVO editGlasses(@RequestParam Map<String, String> params) {
		return impl.editGlasses(params);
	}
	
	@PostMapping("/saveGlasses")
	public ResultVO saveGlasses(@Valid GlassesMapper vo) {
		return impl.saveGlasses(vo);
	}
	
	@PostMapping("/deleteGlasses")
	public ResultVO deleteGlasses(@RequestParam Map<String, String> params) {
		return impl.deleteGlasses(params);
	}
	
	
	
}
