package com.model2.mvc.view.purchase;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.User;

public class ListPurchaseAction extends Action {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Search search = new Search();
		HttpSession session = request.getSession();
		
		int page = 1;
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		String buyerId = ((User)session.getAttribute("user")).getUserId();
		
		
//		search.setCurrentPage(Integer.toString(currentPage));
		search.setCurrentPage(Integer.parseInt(getServletContext().getInitParameter("curruntPage")));
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		search.setPageSize(Integer.parseInt(getServletContext().getInitParameter("pageSize")));
		
		PurchaseService service = new PurchaseServiceImpl();
		HashMap<String, Object> map = service.getPurchaseList(search, buyerId);
		
		request.setAttribute("map", map);
		request.setAttribute("search", search);
		
		
		return "forward:/purchase/listPurchase.jsp";
	}

}