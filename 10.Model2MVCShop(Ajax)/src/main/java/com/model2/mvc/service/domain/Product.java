package com.model2.mvc.service.domain;

import java.io.File;
import java.sql.Date;


public class Product {

	private int prodNo;
	private String prodName;
	private String prodDetail;
	private String manuDate;
	private int price;
	//private String fileName;
	private Date regDate;
	private String proTranCode;
	private int count;
	private File fileName;
	//priceAsc
	
	public Product() {
	}

	public int getProdNo() {
		return prodNo;
	}

	public void setProdNo(int prodNo) {
		this.prodNo = prodNo;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getProdDetail() {
		return prodDetail;
	}

	public void setProdDetail(String prodDetail) {
		this.prodDetail = prodDetail;
	}

	public String getManuDate() {
		return manuDate;
	}

	public void setManuDate(String manuDate) {
		this.manuDate = manuDate;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public File getFileName() {
		return fileName;
	}

	public void setFileName(File originalFileName) {
		this.fileName = originalFileName;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getProTranCode() {
		return proTranCode;
	}

	public void setProTranCode(String proTranCode) {
		this.proTranCode = proTranCode;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "ProductVO : [prodName] "+prodName+" [prodNo] "+prodNo+" [prodDetail] "+prodDetail+" [manuDate] "+ manuDate
				+" [price] "+price+" [fileName] "+fileName+" [regDate] "+regDate+" [proTranCode] "+proTranCode+" [count] "+count;

	}

}