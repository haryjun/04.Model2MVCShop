package com.model2.mvc.service.product;

import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;


public interface ProductService {
	public void addProduct(Product product) throws Exception; 
	
	public Product getProduct(int prodNo) throws Exception; 
	
	public Map getProductList(Search search) throws Exception;

	public void updateProduct(Product product) throws Exception;
	
	public void deleteProduct(int prodNo) throws Exception;
	
	//autoComplete
	public List<String> autoCompleteProduct(Search search) throws Exception;
	
	//무한스크롤
	public List<Product> scrollProduct(int lastProdNo, Integer limit) throws Exception;
}