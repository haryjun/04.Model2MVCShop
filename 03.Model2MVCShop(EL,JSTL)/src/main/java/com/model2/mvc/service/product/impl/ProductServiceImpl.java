package com.model2.mvc.service.product.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDao;
import com.model2.mvc.service.domain.Product;

public class ProductServiceImpl implements ProductService {

	private ProductDao productDao;
	

	
	public ProductServiceImpl() {
		productDao = new ProductDao();
	}

	@Override
	public void addProduct(Product productVO) throws Exception {
		productDao.insertProduct(productVO);
	}

	@Override
	public Product getProduct(int prodNo) throws Exception {
		return productDao.findProduct(prodNo);
	}

	@Override
	public Map<String, Object> getProductList(Search searchVO) throws Exception {
		return productDao.getProductList(searchVO);
	}

	@Override
	public void updateProduct(Product productVO) throws Exception {
		productDao.updateProduct(productVO);
	}

}