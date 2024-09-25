package com.model2.mvc.service.product;


import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;

public interface ProductDao {

	//public void insertProduct(Product product) throws Exception;
		
	/*
	 * public List<Product> getProductList(Search search) throws Exception;
	 * 
	 * 
	 * public Product findProduct(int prodNo) throws Exception;
	 * 
	 * 
	 * public void updateProduct(Product product) throws Exception;
	 * 
	 * 
	 * public int getTotalCount(Search search) throws Exception;
	 * 
	 * 
	 * public int deleteProduct(String prodName) throws Exception;
	 */

	public int addProduct(Product product) throws Exception;

	public Product getProduct(int prodNo) throws Exception;

	public int updateProduct(Product product) throws Exception;

	public List<Object> getProductList(Search search) throws Exception;
	
	public void deleteProduct(int prodNo)throws Exception;
}



