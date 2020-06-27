package com.threefriend.lightspace.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.service.Impl.ProductServiceImpl;
import com.threefriend.lightspace.vo.ResultVO;

@RestController
public class ProduceController {

	@Autowired
	private ProductServiceImpl product_dao;
	
	@PostMapping("productList")
	public ResultVO productList(@RequestParam Map<String, String> params) {
		return product_dao.productList(params);
	}
	
	@PostMapping("addProduct")
	public ResultVO addProduct(@RequestParam Map<String, String> params,
								@RequestParam(value="picture",required=false) MultipartFile picture[],
								@RequestParam(value="details",required=false) MultipartFile details) {
		return product_dao.addProduct(params, picture, details);
	}
	
	@PostMapping("editProduct")
	public ResultVO editProduct(@RequestParam Map<String, String> params) {
		return product_dao.editProduct(params);
	}
	
	@PostMapping("saveProduct")
	public ResultVO saveProduct(@RequestParam Map<String, String> params,
								@RequestParam(value="picture",required=false) MultipartFile picture[],
								@RequestParam(value="details",required=false) MultipartFile details,
								@RequestParam(value="delPic") String []delpic) {
		return product_dao.saveProduct(params, picture, details,delpic);
	}
	
	@PostMapping("deleteProduct")
	public ResultVO deleteProduct(@RequestParam Map<String, String> params) {
		return product_dao.deleteProduct(params);
	}
	
	@PostMapping("findProductByName")
	public ResultVO findProductByName(@RequestParam Map<String, String> params) {
		return product_dao.findByName(params);
	}
	
	@PostMapping("allProduct")
	public ResultVO allProduct() {
		return product_dao.allProduct();
	}
	
	@PostMapping("disPlayBuyer")
	public ResultVO disPlay() {
		return product_dao.disPlay();
	}
}
