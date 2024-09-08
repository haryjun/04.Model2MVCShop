package com.model2.mvc.service.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;

public interface ProductDao {

	public int addProduct(Product product) throws Exception;

	public Product getProduct(int prodNo) throws Exception;

	public int updateProduct(Product product) throws Exception;

	public List<Object> getProductList(Search search) throws Exception;
}