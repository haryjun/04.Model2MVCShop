package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;

public class AddProductAction extends Action {

	public String execute(	HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		Product product = new Product();
		
		//String productPrice = String.format("%d", "price");
		
		product.setProdName(request.getParameter("prodName"));
		product.setProdDetail(request.getParameter("prodDetail"));
//		productVO.setManuDate(request.getParameter("manuDate"));
		product.setManuDate(request.getParameter("manuDate").replaceAll("-", ""));
		product.setPrice(Integer.parseInt(request.getParameter("price")));
		product.setProdName(request.getParameter("fileName"));
		product.setProTranCode("proTranCode");
		
		System.out.println(product);
		
		ProductService service = new ProductServiceImpl();
		service.addProduct(product);

	
		return "redirect:/product/addProductView.jsp";
	}
}
