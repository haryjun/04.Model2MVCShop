package com.model2.mvc.service.domain;

import java.sql.Date;
import java.sql.Timestamp;

import com.model2.mvc.common.Coupon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
//==>ȸ�������� �𵨸�(�߻�ȭ/ĸ��ȭ)�� Bean
public class User {
	
	///Field
	private String userId;
	private String userName;
	private String password;
	private String role;
	private String ssn;
	private String phone;
	/////////////// zip code ///////////
	private String addr;
	private String postcode;
    private String address;
    private String addressDetail;
	//////////////////////////////////////////////////
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
	
	
	public void setAddr(String postcode, String address, String addressDetail) {
        this.addr = postcode + " " + address + " " + addressDetail; // 주소를 결합하여 저장
    }

}