package com.model2.mvc.service.product;

import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.Search;
//import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.domain.Product;


public interface ProductService{
	
	//상품 정보 조회
	//public void findProduct(ProductVO productVO) throws Exception;
	
	public int addProduct(Product product) throws Exception;
	
	public Product getProduct(int prodNo) throws Exception;
	
	public int updateProduct(Product product) throws Exception;
	
	public Map<String,Object> getProductList(Search search) throws Exception;
	
}
