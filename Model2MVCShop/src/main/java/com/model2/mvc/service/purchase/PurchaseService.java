package com.model2.mvc.service.purchase;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;

public interface PurchaseService {

	public int addPurchase(Purchase purchase) throws Exception;

	public Purchase getPurchase(int tranNo) throws Exception;

	public Purchase getPurchase2(int prodNo) throws Exception;

	public Map<String, Object> getPurchaseList(Search search, String userId) throws Exception;

	public void updatePurchase(Purchase purchase) throws Exception;
	
	public void updateTranCode(Purchase purchase) throws Exception;
	
	public void deletePurchase(int tranNo) throws Exception;
	
	public Purchase discountPurchase(Purchase purchase, double discountRate, String userId, String couponType) throws Exception;
	
}