package com.model2.mvc.web.product;

import java.io.File;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;


//==> 상품관리 Controller
@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method 구현 않음
	
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
		
	public ProductController(){
		System.out.println(this.getClass());
	}
	
	@RequestMapping(value="addProduct", method=RequestMethod.GET)
	public String addProduct() throws Exception{
		System.out.println("/product/addProduct : GET");
		
		return "redirect:/product/addProductView.jsp";
	}
	/*
	@RequestMapping(value="addProduct", method=RequestMethod.POST)
	public String addProduct(@ModelAttribute("product") Product product) throws Exception{
		
		System.out.println("/product/addProduct : POST");
		
		//Business Logic
		productService.addProduct(product);
		return "redirect:/product/listProduct.jsp";
	}
	*/
	
	/*@RequestMapping(value="/addProduct", method=RequestMethod.POST)
	public File addProduct(@ModelAttribute("product") Product product,
			@RequestParam("fileNameForReal") MultipartFile file) throws Exception{
		System.out.println("/product/addProduct : POST" + product);
		
		//file처리
		if(!file.isEmpty()) {
			String originalFileName = file.getOriginalFilename();
			product.setFileName(originalFileName);
			
			//file저장장소
			String filePath = "/upload/"+originalFileName;
			File dest = new File(filePath);
			
			
			 // 업로드 디렉토리가 없으면 생성 
			 //if (!dest.getParentFile().exists()) {
			 //dest.getParentFile().mkdirs(); 
			 //}
			 	
			//저장
			file.transferTo(dest);
		}
		
		productService.addProduct(product);
		"redirect:/product/addProduct.jsp";
	}
	
/*	@RequestMapping(value="/addProduct", method=RequestMethod.POST)
	public String addProduct(@ModelAttribute("product") Product product) throws Exception{
		System.out.println("/product/addProduct : POST");
		productService.addProduct(product);
		return "redirect:/product/addProduct.jsp";
	}*/
	
	
	@RequestMapping("/getProduct")
	public String getProduct(@RequestParam("prodNo") int prodNo, @RequestParam(value="menu", required=false) String menu, @CookieValue(value="history", required=false) Cookie cookie, HttpServletResponse response, Model model) throws Exception{
		System.out.println("/product/getProduct");
		
		model.addAttribute("product", productService.getProduct(prodNo));
		
		//CookieValue <= String  
//		if(cookie!=null) {	
//			Cookie cookieee = new Cookie("history", cookie+","+new Integer(prodNo).toString());
//			cookieee.setPath("/");
//			response.addCookie(cookieee);
//		}else {
//			Cookie cookieee = new Cookie("history", new Integer(prodNo).toString());
//			cookieee.setPath("/");
//			response.addCookie(cookieee);
//		}
		
		//CookieValue <= Cookie
		if(cookie!=null) {
			if( !(cookie.getValue().contains(new Integer(prodNo).toString())) ){
				cookie.setValue(cookie.getValue()+","+new Integer(prodNo).toString());
				cookie.setPath("/");
			}
			response.addCookie(cookie);
		}else {
			Cookie doNotHaveCookie = new Cookie("history", new Integer(prodNo).toString());
			doNotHaveCookie.setPath("/");
			response.addCookie(doNotHaveCookie);
		}
		
		if(menu!=null && menu.equals("manage")) {
			return "forward:/product/updateProductView.jsp?menu=manage";
		}else {
			return "forward:/product/getProduct.jsp";
		}
	}
	
	@RequestMapping(value="/updateProduct", method=RequestMethod.GET)
	public String updateProduct() throws Exception{
		System.out.println("/product/updateProduct : GET");	
		
		return "forward:/product/updateProductView.jsp";
	}
	
	@RequestMapping(value="/updateProduct", method=RequestMethod.POST)
	public String updateProduct(@ModelAttribute("product") Product product) throws Exception{
		System.out.println("/product/updateProduct : POST");
		
		productService.updateProduct(product);
		
		return "forward:/product/getProduct";
	}
	
	@RequestMapping(value = "deleteProduct")
	public String deleteProduct(@RequestParam("prodNo") int prodNo) throws Exception{
		System.out.println("/product/deleteProduct");
		
		productService.deleteProduct(prodNo);
		
		return "redirect:/product/listProduct?menu=manage";
	}
	
	@RequestMapping(value = "listProduct")
	public String listProduct(@ModelAttribute("search") Search search, Model model, HttpServletRequest request) throws Exception{
		System.out.println("product/listProduct : GET / POST");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		Map<String, Object> map = productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);

		return "forward:/product/listProduct.jsp";
	}


}