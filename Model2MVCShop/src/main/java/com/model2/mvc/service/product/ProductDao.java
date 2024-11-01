package com.model2.mvc.service.product;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;

@Mapper
public interface ProductDao {
	public void addProduct(Product product) throws Exception;
	
	public List<Product> getProductList(Search search) throws Exception;
	
	public Product findProduct(int prodNo) throws Exception;
	
	public void updateProduct(Product product) throws Exception;
	
	public void deleteProduct(int prodNo) throws Exception;
	
	public int getTotalCount(Search search) throws Exception;
	
	public List<String> getAutoComplete(Search search) throws Exception;
	
	public List<Product> scrollProduct(int lastProdNo, Integer limit) throws Exception;
}