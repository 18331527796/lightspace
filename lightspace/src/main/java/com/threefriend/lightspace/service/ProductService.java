package com.threefriend.lightspace.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.threefriend.lightspace.vo.ResultVO;

/**
 *	商品业务逻辑
 */
public interface ProductService {
	
	ResultVO productList();
	
	ResultVO findByName(Map<String, String> params);
	
	ResultVO addProduct(Map<String, String> params,MultipartFile picture[],MultipartFile details);
	
	ResultVO deleteProduct(Map<String, String> params);
	
	ResultVO editProduct(Map<String, String> params);
	
	ResultVO saveProduct(Map<String, String> params,MultipartFile picture[],MultipartFile details);

}
