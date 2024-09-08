package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.dao.ProductDao;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDao;

public class PurchaseServiceImpl implements PurchaseService {

	ProductDao productDao;
	PurchaseDao purchaseDao;
	
	public PurchaseServiceImpl() {
		purchaseDao = new PurchaseDao();
	}

	@Override
	public void addPurchase(Purchase purchase) throws Exception {
		purchaseDao.insertPurchase(purchase);
	}

	@Override
	public Purchase getPurchase(int tranNo) throws Exception {
		return purchaseDao.findPurchase(tranNo);
	}

	@Override
	public Purchase getPurchase2(int prodNo) throws Exception {
		return purchaseDao.findPurchase2(prodNo);
	}

	@Override
	public HashMap<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {
		return purchaseDao.getPurchaseList(search, buyerId);
	}

	@Override
	public HashMap<String, Object> getSaleList(Search search) throws Exception {
		return purchaseDao.getSaleList(search);
	}

	@Override
	public void updatePurchase(Purchase purchase) throws Exception {
		purchaseDao.updatePurchase(purchase);
	}

	@Override
	public void updateTranCode(Purchase purchase) throws Exception {
		purchaseDao.updateTranCode(purchase);
	}

}