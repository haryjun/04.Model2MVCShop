package com.model2.mvc.service.user.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Coupon;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.UserDao;;


//==> ȸ������ ���� ����
//@Service("userService")
@Service("userServiceImpl")
//@Qualifier("userServiceImpl")
public class UserServiceImpl implements UserService{
	
	///Field
	@Autowired
	//@Qualifier("userDaoImpl")
	@Qualifier("userDao")
	private UserDao userDao;
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	///Constructor
	public UserServiceImpl() {
		System.out.println(this.getClass());
	}
	///Method
	public void addUser(User user) throws Exception {
		
	    // 주소 정보를 하나의 문자열로 결합하여 addr 필드에 설정
	    String postcode = user.getPostcode(); // 기존의 postcode
	    String address = user.getAddress(); // 기존의 address
	    String addressDetail = user.getAddressDetail(); // 기존의 addressDetail

	    user.setAddr(postcode, address, addressDetail); // addr 필드 설정

	    // DB에 사용자 추가
	    userDao.addUser(user);
	    
	    System.out.println("우편번호: " + user.getPostcode());
	    System.out.println("주소: " + user.getAddress());
	    System.out.println("상세주소: " + user.getAddressDetail());
	    System.out.println("전체 주소: " + user.getAddr());
	}

	public User getUser(String userId) throws Exception {
		return userDao.getUser(userId);
	}

	public Map<String , Object > getUserList(Search search) throws Exception {
		List<User> list= userDao.getUserList(search);
		int totalCount = userDao.getTotalCount(search);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list );
		map.put("totalCount", new Integer(totalCount));
		
		return map;
	}

	public void updateUser(User user) throws Exception {
		userDao.updateUser(user);
	}

	public boolean checkDuplication(String userId) throws Exception {
		boolean result=true;
		User user=userDao.getUser(userId);
		if(user != null) {
			result=false;
		}
		return result;
	}

	@Override
	public void addCoupon(String couponId, User user) throws Exception {
		// TODO Auto-generated method stub
		Coupon coupon = new Coupon();
		
		if(couponId!=null && couponId.equals("deleteDiscountCoupon10")) {
			System.out.println("쿠폰 삭제");
			coupon.setDiscountCoupon10("0");
			user.setCoupon(coupon);	
			userDao.useCoupon(user);
		}else if(couponId!=null && couponId.equals("abcdefg")) {
			coupon.setDiscountCoupon10("1");
			user.setCoupon(coupon);	
			userDao.addCoupon(user);
		}else if(couponId==null) {
			System.out.println("쿠폰아이디 null");
		}else {
			System.out.println("쿠폰아이디 whyrano");
		}
		
		return;
	}

	@Override
	public List<String> autoCompleteUser(Search search) throws Exception {
		// TODO Auto-generated method stub
		
		return userDao.getAutoComplete(search);
	}

	@Override
	public User getKakaoUser(String kakaoId) throws Exception {
		// TODO Auto-generated method stub
		return userDao.getKakaoUser(kakaoId);
	}
		

}