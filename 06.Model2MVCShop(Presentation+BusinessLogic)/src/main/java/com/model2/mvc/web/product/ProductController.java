package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

//
//==> ȸ������ Controller
@Controller
public class ProductController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method ���� ����
		
	public ProductController(){
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml ���� �Ұ�
	//==> �Ʒ��� �ΰ��� �ּ��� Ǯ�� �ǹ̸� Ȯ�� �Ұ�
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	/*
	 * listProduct.jsp updateProduct.jsp
	 * updateProductView.jsp
	 */
	
	@RequestMapping("/addProductView.do")
	public String addProductView() throws Exception{
		
		System.out.println("/addProductView.do");
		
		return "redirect:/product/addProductView.jsp";
		
	}
	
	/*viewPage�ϳ� ��𰬾�
	 * @RequestMapping("/addProduct.do") public String addProduct() throws
	 * Exception{
	 * 
	 * System.out.println("/addProduct.do");
	 * 
	 * return "redirect:/product/addProduct.jsp";
	 * 
	 * }
	 */
	
	@RequestMapping("/getProduct.do")
	public String getProduct(@RequestParam("productNo")Integer productNo, Model model) throws Exception{
		
		System.out.println("/getProduct.do");
		
		//Busniess Logic
		Product product = productService.getProduct(productNo);
		
		
		return "redirect:/product/getProduct.jsp";
		
	}
	
	@RequestMapping("/updateProductView.do")
	public String updateProductView(@RequestParam("productNo")Integer productNo, Model model) throws Exception {
		
		System.out.println("/updateProductView.do");
		
		Product product = productService.getProduct(productNo);	
		
		model.addAttribute("product", product);
		
		return "forward:/product/updateProduct.jsp";
	}
	
	
	@RequestMapping("/updateProduct.do")
	public String updateProduct(@ModelAttribute("product")Product product, Model model, HttpSession session) throws Exception {
		
		System.out.println("/updateProduct.do");
		
		productService.updateProduct(product);
		
		//model.addAttribute("product", product);
		
		return "redirect:/getProduct.do?productNo" + product.getProdNo();
	}
	
	@RequestMapping("/listProduct.do")
	public String listProduct(@ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception {
		
		System.out.println("/listProduct.do");

		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);

		// Business logic ����
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model �� View ����
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
				
		
		return "forward:/product/listProduct.jsp";
	}
	
	
	}