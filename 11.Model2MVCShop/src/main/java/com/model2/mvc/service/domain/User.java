package com.model2.mvc.service.domain;

import java.sql.Date;

import com.model2.mvc.common.Coupon;


//==>회원정보를 모델링(추상화/캡슐화)한 Bean
public class User {
	
	///Field
	private String userId;
	private String userName;
	private String password;
	private String role;
	private String ssn;
	private String phone;
	private String addr;
	private String email;
	private Date regDate;
	/////////////// EL 적용 위해 추가된 Field ///////////
	private String phone1;
	private String phone2;
	private String phone3;
	/////////////////////////////////////////////////////
	private String discountCoupon10;
	private Coupon coupon;
	////////////////////////////////////////////////
	private String kakaoId;
	private String profile_nickname;
	private String profile_image;
	
	
	///Constructor
	public User(){
	}
	
	///Method 
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
		/////////////// EL 적용 위해 추가 ///////////
		if(phone != null && phone.length() !=0 ){
			phone1 = phone.split("-")[0];
			phone2 = phone.split("-")[1];
			phone3 = phone.split("-")[2];
		}
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getRegDate() {
		return regDate;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////
	// JSON ==> Domain Object  Binding을 위해 추가된 부분
	/*
	 * public void setRegDate(Date regDate) { this.regDate = regDate;
	 * 
	 * if(regDate !=null) { // JSON ==> Domain Object Binding을 위해 추가된 부분
	 * this.setRegDateString( regDate.toString().split("-")[0] +"-"+
	 * regDate.toString().split("-")[1] + "-" +regDate.toString().split("-")[2] ); }
	 * 
	 * }
	 */
	
	/////////////// EL 적용 위해 추가된 getter Method ///////////
	public String getPhone1() {
		return phone1;
	}
	public String getPhone2() {
		return phone2;
	}
	public String getPhone3() {
		return phone3;
	}

	@Override
	public String toString() {
		return "UserVO : [userId] "+userId+" [userName] "+userName+" [password] "+password+" [role] "+ role
			+" [ssn] "+ssn+" [phone] "+phone+" [email] "+email+" [regDate] "+regDate+ 
			", discountCoupon10=" + discountCoupon10 + ", coupon=" + coupon;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////
	// JSON ==> Domain Object  Binding을 위해 추가된 부분
	// POJO 의 중요성
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	
	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}
	/////////////////////////////////////////////////////////////////////////////////////////


	public String getDiscountCoupon10() {
		return discountCoupon10;
	}

	public void setDiscountCoupon10(String discountCoupon10) {
		this.discountCoupon10 = discountCoupon10;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	
////////////////////////////////////////////////////////////////
///////////////////////kakao Login//////////////////////////////
////////////////////////////////////////////////////////////////
	
	public String getKakaoId() {
		return kakaoId;
	}

	public void setKakaoId(String kakaoId) {
		this.kakaoId = kakaoId;
	}

	public String getProfile_nickname() {
		return profile_nickname;
	}

	public void setProfile_nickname(String profile_nickname) {
		this.profile_nickname = profile_nickname;
	}

	public String getProfile_image() {
		return profile_image;
	}

	public void setProfile_image(String profile_image) {
		this.profile_image = profile_image;
	}
}