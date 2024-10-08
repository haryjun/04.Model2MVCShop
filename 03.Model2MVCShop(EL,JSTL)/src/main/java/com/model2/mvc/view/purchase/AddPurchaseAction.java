package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.dao.ProductDao;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;

public class AddPurchaseAction extends Action {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Product product = new ProductDao().findProduct(Integer.parseInt(request.getParameter("prodNo")));
		Purchase purchase = new Purchase();

		purchase.setBuyer(user);
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyDate(request.getParameter("receiverDate"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setPurchaseProd(product);
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));

		PurchaseService service = new PurchaseServiceImpl();
		service.addPurchase(purchase);
		request.setAttribute("purchaseVO", purchase);

		return "forward:/purchase/addPurchase.jsp";

	}

}