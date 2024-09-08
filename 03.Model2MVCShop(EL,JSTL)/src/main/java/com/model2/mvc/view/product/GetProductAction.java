/*
 * package com.model2.mvc.view.product;
 * 
 * import javax.servlet.http.Cookie; import
 * javax.servlet.http.HttpServletRequest; import
 * javax.servlet.http.HttpServletResponse;
 * 
 * 
 * import com.model2.mvc.framework.Action; import
 * com.model2.mvc.service.product.ProductService; import
 * com.model2.mvc.service.product.impl.ProductServiceImpl; import
 * com.model2.mvc.service.domain.Product;
 * 
 * public class GetProductAction extends Action{
 * 
 * @Override public String execute(HttpServletRequest request,
 * HttpServletResponse response) throws Exception {
 * 
 * int prodNo = Integer.parseInt(request.getParameter("prodNo"));
 * 
 * System.out.println("productNo : " + prodNo);
 * 
 * ProductService service = new ProductServiceImpl(); Product product =
 * service.getProduct(prodNo);
 * 
 * String history = String.valueOf(product.getProdNo())+"-";
 * 
 * Cookie[] cookies = request.getCookies(); for(Cookie c : cookies) { if
 * (c.getName().equals("history")) { String check = c.getValue(); if(check !=
 * history) { c.setValue(check.concat(history)); response.addCookie(c); } } else
 * { response.addCookie(new Cookie("history", history)); } }
 * 
 * 
 * request.setAttribute("product", product);
 * 
 * 
 * return "forward:/product/getProduct.jsp"; } }
 */


package com.model2.mvc.view.product;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;

public class GetProductAction extends Action {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // prodNo 파라미터를 가져오기
        String prodNoStr = request.getParameter("prodNo");

        // prodNo 값이 null 또는 빈 문자열인지 확인
        if (prodNoStr == null || prodNoStr.trim().isEmpty()) {
            throw new IllegalArgumentException("상품 번호가 유효하지 않습니다.");
        }

        int prodNo = 0;
        try {
            prodNo = Integer.parseInt(prodNoStr);
        } catch (NumberFormatException e) {
            // 숫자로 변환할 수 없을 때 예외 처리
            throw new IllegalArgumentException("상품 번호는 숫자여야 합니다: " + prodNoStr);
        }

        System.out.println("productNo : " + prodNo);

        // ProductService를 통해 상품 정보 가져오기
        ProductService service = new ProductServiceImpl();
        Product product = service.getProduct(prodNo);
        
        String history = String.valueOf(product.getProdNo()) + "-";

        // 쿠키 처리
        Cookie[] cookies = request.getCookies();
        boolean foundHistory = false;

        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("history")) {
                    String check = c.getValue();
                    if (!check.contains(history)) {
                        c.setValue(check.concat(history));
                        response.addCookie(c);
                    }
                    foundHistory = true;
                    break;
                }
            }
        }

        // 기존 쿠키가 없는 경우 새 쿠키 생성
        if (!foundHistory) {
            response.addCookie(new Cookie("history", history));
        }

        // 상품 객체를 request에 설정
        request.setAttribute("product", product);

        return "forward:/product/getProduct.jsp";
    }
}
