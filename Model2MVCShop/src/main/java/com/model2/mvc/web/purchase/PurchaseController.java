package com.model2.mvc.web.purchase;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;


@Controller
@RequestMapping("/purchase/*")
public class PurchaseController {
	
	///Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	//setter Method 구현 않음
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	//@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	@Value("${pageUnit}")
	int pageUnit;
	
	//@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	@Value("${pageSize}")
	int pageSize;
		
	public PurchaseController(){
		System.out.println(this.getClass());
	}
	
	@RequestMapping(value="/addPurchase", method=RequestMethod.GET)
	public ModelAndView addPurchase(@RequestParam("prodNo") int prodNo) throws Exception{
		System.out.println("/purchase/addPurchase : GET");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("product", productService.getProduct(prodNo));
		modelAndView.setViewName("forward:/purchase/addPurchaseView.jsp");
		return modelAndView;
	}
	
	@RequestMapping(value="/addPurchase", method=RequestMethod.POST)
	public ModelAndView addPurchase(HttpSession session, @ModelAttribute("purchase") Purchase purchase, @RequestParam("prodNo") int prodNo, @RequestParam("buyerId") String userId, @RequestParam(value="couponPrice", required=false) boolean checked) throws Exception{
		System.out.println("/purchase/addPurchase : POST");
		purchase.setPurchaseProd(productService.getProduct(prodNo));
		if(checked) {
			purchaseService.discountPurchase(purchase, 0.9, userId, "discountCoupon10");	
			session.setAttribute("user", userService.getUser(((User)(session.getAttribute("user"))).getUserId()));
		}
		purchase.setBuyer(userService.getUser(userId));
		purchase.setTranCode("2  ");
		int tranNo = purchaseService.addPurchase(purchase);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/addPurchase.jsp");
		modelAndView.addObject("purchase",purchaseService.getPurchase(tranNo));
		
		return modelAndView;
	}
	
	@RequestMapping("/listPurchase")
	public ModelAndView listPurchase(@ModelAttribute("search") Search search, HttpSession session) throws Exception {
	    ModelAndView modelAndView = new ModelAndView();
	    System.out.println("/purchase/listPurchase");

	    if (search.getCurrentPage() == 0) {
	        search.setCurrentPage(1);
	    }
	    search.setPageSize(pageSize);

	    // 세션에서 사용자 정보 가져오기
	    User user = (User) session.getAttribute("user");
	    if (user == null) {
	        throw new Exception("User not logged in or session expired");
	    }

	    String userId = user.getUserId().trim(); // 공백 제거
	    System.out.println("Logged in userId: " + userId); // 로그 출력으로 확인

	    // 관리자 또는 매니저 사용자 구분
	    if (userId.equals("admin") || userId.equals("manager")) {
	        userId = "";
	        modelAndView.setViewName("forward:/purchase/listPurchaseAdmin.jsp");
	    } else {
	        modelAndView.setViewName("forward:/purchase/listPurchase.jsp");
	    }

	    // Purchase 목록 조회
	    Map<String, Object> map = purchaseService.getPurchaseList(search, userId);

	    Page resultPage = new Page(search.getCurrentPage(), ((Integer) map.get("totalCount")).intValue(), pageUnit, pageSize);
	    System.out.println(resultPage);

	    modelAndView.addObject("list", map.get("list"));
	    modelAndView.addObject("resultPage", resultPage);
	    modelAndView.addObject("search", search);

	    return modelAndView;
	}
	
	@RequestMapping("/getPurchase")
	public ModelAndView getPurchase(@RequestParam("tranNo") int tranNo) throws Exception{
		System.out.println("/getPurchase");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/getPurchase.jsp");
		modelAndView.addObject("purchase", purchaseService.getPurchase(tranNo));
		
		return modelAndView;
	}
	
	@RequestMapping(value="/updatePurchase", method=RequestMethod.GET)
	public ModelAndView updatePurchase(@RequestParam("tranNo") int tranNo) throws Exception{
		System.out.println("/purchse/updatePurchase : GET");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("purchase", purchaseService.getPurchase(tranNo));
		modelAndView.setViewName("forward:/purchase/updatePurchaseView.jsp");
		return modelAndView;
	}
	
	@RequestMapping(value="/updatePurchase", method=RequestMethod.POST)
	public ModelAndView updatePurchase(@ModelAttribute("purchase") Purchase purchase, @RequestParam("tranNo") int tranNo) throws Exception{
		System.out.println("/purchase/updatePurchase : POST");
		purchase.setPurchaseProd(purchaseService.getPurchase(tranNo).getPurchaseProd());
		
		purchaseService.updatePurchase(purchase);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/getPurchase.jsp");
		modelAndView.addObject("purchase", purchaseService.getPurchase(tranNo));
		return modelAndView;
	}
	
	@RequestMapping("/deletePurchase")
	public ModelAndView deletePurchase(@RequestParam("tranNo") int tranNo, HttpSession session) throws Exception{
		System.out.println("/purchase/deletePurchase");

		purchaseService.deletePurchase(tranNo);
		
		//쿠폰 사용으로 인한 user session update
		session.setAttribute("user", userService.getUser(((User)session.getAttribute("user")).getUserId()));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/purchase/listPurchase");
		return modelAndView;
	}
	
	@RequestMapping("/updateTranCode")
	public ModelAndView updateTranCode(@RequestParam("tranNo") int tranNo, @RequestParam("tranCode") String tranCode) throws Exception{
		System.out.println("/updateTranCode");
		Purchase purchase = purchaseService.getPurchase(tranNo);
		purchase.setTranCode(tranCode);
		purchaseService.updateTranCode(purchase);
		

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/purchase/listPurchase");
		return modelAndView;
	}
	
	@RequestMapping("/updateTranCodeByProd")
	public ModelAndView updateTranCodeByProd(@RequestParam("prodNo") int prodNo, @RequestParam("tranCode") String tranCode) throws Exception{
		System.out.println("/updateTranCodeByProd");
		Purchase purchase = purchaseService.getPurchase2(prodNo);
		purchase.setTranCode(tranCode);
		purchaseService.updateTranCode(purchase);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/product/listProduct?menu=manage");
		return modelAndView;
	}
}