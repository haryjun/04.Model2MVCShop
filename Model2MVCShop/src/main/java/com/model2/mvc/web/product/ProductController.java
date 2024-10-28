package com.model2.mvc.web.product;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
	
	//@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	@Value("${pageUnit}")
	int pageUnit;
	
	//@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	@Value("${pageSize}")
	int pageSize;
	
	//@Value("#{commonProperties['path']}")
//	@Value("${path}")
//	private String path;
		
	public ProductController(){
		System.out.println(this.getClass());
	}
	
	@RequestMapping(value="/addProduct", method=RequestMethod.GET)
	public String addProduct() throws Exception{
		System.out.println("/product/addProduct : GET");
		
		return "redirect:/product/addProductView.jsp";
	}
	
	@RequestMapping(value="/addProduct", method=RequestMethod.POST)
    public String addProduct(@ModelAttribute("product") Product product, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
       
		System.out.println("/product/addProduct : POST");
		
		//업로드 파일 저장소
		//String path = "C:\\Users\\USER\\git\\11.Model2MVCShop\\11.Model2MVCShop\\WebContent\\images\\uploadFiles";
		//path= C:\\git\\test로 바꿈
		
		//List<MultipartFile> fileList = multipartHttpServletRequest.getFiles("fileNameForReal");
		//String finalFileName = "";
        //for (MultipartFile multiFile : fileList) {
           // String originalFileName = multiFile.getOriginalFilename(); // 원본 파일 명
           // finalFileName += originalFileName+",";
			//File serverFile = new File(path + File.separator + originalFileName);
			//multiFile.transferTo(serverFile);
       // }
		//finalFileName = finalFileName.substring(0, finalFileName.lastIndexOf(","));
		//product.setFileName(finalFileName);

		//productService.addProduct(product);
		//return "forward:/product/addProduct.jsp";
		String path = "C:\\workspaceSpring\\workspace\\Model2MVCShop\\src\\main\\webapp\\images\\uploadFiles";
		
        List<MultipartFile> fileList = multipartHttpServletRequest.getFiles("fileNameForReal");
        StringBuilder finalFileNames = new StringBuilder(); // StringBuilder 사용

        for (MultipartFile multiFile : fileList) {
            if (!multiFile.isEmpty()) { // 비어있는 파일 체크
                String originalFileName = multiFile.getOriginalFilename(); // 원본 파일명
                finalFileNames.append(originalFileName).append(",");

                System.out.println("Saving file to: " + path);

                // 파일 저장
                File serverFile = new File(path, originalFileName);
                multiFile.transferTo(serverFile);
            }
        }

        // 마지막 쉼표 제거
        if (finalFileNames.length() > 0) {
            finalFileNames.setLength(finalFileNames.length() - 1); // 마지막 쉼표 제거
        }
        
        product.setFileName(finalFileNames.toString()); // 파일명 설정

        // 서비스 호출
        productService.addProduct(product);
        return "forward:/product/addProduct.jsp";
		
    }
	
	/*@RequestMapping("/getProduct")
	public String getProduct(@RequestParam("prodNo") int prodNo, @RequestParam(value="menu", required=false) String menu, @CookieValue(value="history", required=false) Cookie cookie, HttpServletResponse response, Model model) throws Exception{
		System.out.println("/product/getProduct");
		
		model.addAttribute("product", productService.getProduct(prodNo));
		
		//CookieValue String으로 받았을  
//		if(cookie!=null) {	
//			Cookie cookieee = new Cookie("history", cookie+","+new Integer(prodNo).toString());
//			cookieee.setPath("/");
//			response.addCookie(cookieee);
//		}else {
//			Cookie cookieee = new Cookie("history", new Integer(prodNo).toString());
//			cookieee.setPath("/");
//			response.addCookie(cookieee);
//		}
		
		//CookieValue Cookie로 받았을 때
		if(cookie!=null) {
			if( !(cookie.getValue().contains(new Integer(prodNo).toString())) ){
				cookie.setValue(cookie.getValue()+"|"+new Integer(prodNo).toString());
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
	}*/
	
    // 상품 수정 뷰로 이동
    @RequestMapping(value = "/updateProductView", method = RequestMethod.GET)
    public String updateProductView(@RequestParam("prodNo") int prodNo, Model model) throws Exception {
        System.out.println("/product/updateProductView : GET");
        Product product = productService.getProduct(prodNo);
        model.addAttribute("product", product);
        
        return "forward:/product/updateProductView.jsp";
    }

	@RequestMapping(value="/updateProduct", method=RequestMethod.POST)
	public String updateProduct(@ModelAttribute("product") Product product, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
		System.out.println("/product/updateProduct : POST");
		//String path = "C:\\Users\\USER\\git\\11.Model2MVCShop\\11.Model2MVCShop\\WebContent\\images\\uploadFiles";
		String path = "C:\\workspaceSpring\\workspace\\Model2MVCShop\\src\\main\\webapp\\images\\uploadFiles";

		List<MultipartFile> fileList = multipartHttpServletRequest.getFiles("fileNameForReal");
		String finalFileName = "";
        for (MultipartFile multiFile : fileList) {
            String originalFileName = multiFile.getOriginalFilename(); // 원본 파일 명
            if(!originalFileName.equals("")) {
	            finalFileName += originalFileName+",";
				File serverFile = new File(path + File.separator + originalFileName);
				multiFile.transferTo(serverFile);
            }
        }
        if(finalFileName.contains(",")) {
			finalFileName = finalFileName.substring(0, finalFileName.lastIndexOf(","));
			product.setFileName(finalFileName);
        }
		productService.updateProduct(product);
		
		return "redirect:/product/getProduct?prodNo="+product.getProdNo();
	}
	@RequestMapping("/getProduct")
	public String getProduct(@RequestParam(value = "prodNo", required = false, defaultValue = "0") int prodNo, 
	                         @RequestParam(value = "menu", required = false) String menu, 
	                         @CookieValue(value = "history", required = false) Cookie cookie, 
	                         HttpServletResponse response, 
	                         Model model) throws Exception {
	    System.out.println("/product/getProduct");

	    if (prodNo == 0) {
	        System.out.println("Invalid prodNo value");
	        return "redirect:/product/listProduct?menu=manage";
	    }

	    model.addAttribute("product", productService.getProduct(prodNo));

	    // Cookie 설정
	    if (cookie != null) {
	        if (!cookie.getValue().contains(String.valueOf(prodNo))) {
	            cookie.setValue(cookie.getValue() + "|" + prodNo);
	            cookie.setPath("/");
	        }
	        response.addCookie(cookie);
	    } else {
	        Cookie doNotHaveCookie = new Cookie("history", String.valueOf(prodNo));
	        doNotHaveCookie.setPath("/");
	        response.addCookie(doNotHaveCookie);
	    }

	    if ("manage".equals(menu)) {
	        return "forward:/product/updateProductView.jsp?menu=manage";
	    } else {
	        return "forward:/product/getProduct.jsp";
	    }
	}
	
	/*@RequestMapping("/getProduct")
	public String getProduct(@RequestParam(value = "prodNo", required = false, defaultValue = "0") int prodNo, 
	                         @RequestParam(value = "menu", required = false) String menu, 
	                         @CookieValue(value = "history", required = false) Cookie cookie, 
	                         HttpServletResponse response, 
	                         Model model) throws Exception {
	    System.out.println("/product/getProduct");

	    if (prodNo == 0) {
	        // 잘못된 요청에 대한 처리를 수행하거나 예외를 던질 수 있음
	        System.out.println("Invalid prodNo value");
	        return "redirect:/product/listProduct?menu=manage";
	    }

	    model.addAttribute("product", productService.getProduct(prodNo));

	    // Cookie 설정
	    if (cookie != null) {
	        if (!cookie.getValue().contains(String.valueOf(prodNo))) {
	            cookie.setValue(cookie.getValue() + "|" + prodNo);
	            cookie.setPath("/");
	        }
	        response.addCookie(cookie);
	    } else {
	        Cookie doNotHaveCookie = new Cookie("history", String.valueOf(prodNo));
	        doNotHaveCookie.setPath("/");
	        response.addCookie(doNotHaveCookie);
	    }

	    if ("manage".equals(menu)) {
	        return "forward:/product/updateProductView.jsp?menu=manage";
	    } else {
	        return "forward:/product/getProduct.jsp";
	    }
	}*/
	
	@RequestMapping("/deleteProduct")
	public String deleteProduct(@RequestParam("prodNo") int prodNo) throws Exception{
		System.out.println("/product/deleteProduct");
		
		productService.deleteProduct(prodNo);
		
		return "redirect:/product/listProduct?menu=manage";
	}
	
	@RequestMapping("/listProduct")
	public String listProduct(@ModelAttribute("search") Search search, Model model) throws Exception{
		System.out.println("/listProduct");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		if(search.getSearchKeyword1()!=0 || search.getSearchKeyword2()!=0) {
			search.setPriceSearch(true);
		}else {
			search.setPriceSearch(false);
		}
		search.setPageSize(pageSize);
		
		System.out.println("~~~~~~~~~~~~~~~ModelAttribute Search:"+search);
		
		Map<String, Object> map = productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);

		return "forward:/product/listProduct.jsp";
	}
	
	

	@ExceptionHandler(value=Exception.class)
	public String error(Exception e) throws Exception{
		e.printStackTrace();
		return e.getMessage();
	}
}