package com.model2.mvc.service.product;

import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.Search;
//import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.domain.Product;


public interface ProductService{
	
	//상품 정보 조회
	//public void findProduct(ProductVO productVO) throws Exception;
	
	public void addProduct(Product product) throws Exception;
	
	public Product getProduct(int product) throws Exception;
	
	//상품 목록 조회
	//ArrayList와 HashMap
	//public ArrayList<Product>getProductList(UserVO userVO) throws Exception;
	public Map<String, Object> getProductList(Search search) throws Exception;
	
	
	//상품등록
	//public  void insertProduct(ProductVO productVO)throws Exception;
	
	//상품 정보수
	public void updateProduct(Product product)throws Exception;
	
}
