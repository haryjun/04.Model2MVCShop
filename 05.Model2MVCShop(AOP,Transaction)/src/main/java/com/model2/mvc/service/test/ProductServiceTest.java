package com.model2.mvc.service.test;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;
import com.model2.mvc.service.product.ProductService;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
public class ProductServiceTest {

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	@Autowired
	@Qualifier("productDaoImpl")
	private ProductDao productDAO;

	//@Test
	public void testGetProduct() throws Exception {
		

		Product product = new Product();
		
		product = productService.getProduct(0);
		
		System.out.println(product);
		
		Assert.assertEquals("testProductNo", product.getProdNo());
		//Assert.assertSame(product, product);
		Assert.assertEquals("testProductName", product.getProdName());
		Assert.assertEquals("testProductDetail", product.getProdDetail());
		Assert.assertEquals("20240909", product.getManuDate());
		Assert.assertEquals("testProductFileName", product.getFileName());
		Assert.assertEquals("testProTranCode", product.getProTranCode());
		
		Assert.assertNotNull(productService.getProduct(1));		
		Assert.assertNotNull(productService.getProduct(5));



		/*
		 * Product product = new Product("5번 테스트 앱", "5번 테스트 앱 디테일", "20201114", 55555,
		 * "5번테스트앱사진", "0"); Assert.assertEquals(product.getProdName(),
		 * productService.getProduct(10032).getProdName()); // Assert.assertEquals(0,
		 * productService.getProduct(10032));
		 */
	}

	/* 에러남...,,,
	 * // @Test public void testUpdateProduct() throws Exception {
	 * 
	 * Product product = new Product("5번 테스트 앱", "5번 테스트 앱 디테일", "20201114", 55555,
	 * "5번테스트앱사진", "0"); product.setProdNo(10032); Assert.assertSame(1,
	 * productService.updateProduct(product)); // Assert.assertEquals(0,
	 * productService.updateProduct(product));
	 * 
	 * }
	 */

	@Test
	public void testGetProductList() throws Exception {
		
		Search search = new Search();
		search.setCurrentPage(1);
	 	search.setPageSize(3);
		Map<String, Object> map = productService.getProductList(search);
		
		List<Object> list = (List<Object>) map.get("list");
		Assert.assertEquals(3, list.size());

		System.out.println("총 검색 갯수 :: "+map.get("totalCount"));
		
		
	}
	
	//@Test
	public void testGetOrderedProductList() throws Exception {
		
		//판매중인 상품 테스트
		Search search = new Search();
		search.setCurrentPage(1);
	 	search.setPageSize(3);
		search.setSearchCondition("0");
		Map<String, Object> map = productService.getProductList(search);
		
		List<Object> list = (List<Object>) map.get("list");
		Assert.assertEquals(3, list.size());

		System.out.println("총 검색 갯수 :: "+map.get("totalCount"));
		
		
	}

}// end of class