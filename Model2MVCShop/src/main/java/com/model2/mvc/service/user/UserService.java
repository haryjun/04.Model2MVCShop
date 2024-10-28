package com.model2.mvc.service.user;

import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.User;


//==> ȸ���������� ������ ���� �߻�ȭ/ĸ��ȭ�� Service  Interface Definition  
public interface UserService {
	
		// 회원가입
		public void addUser(User user) throws Exception;
		
		// 내정보확인 / 로그인
		public User getUser(String userId) throws Exception;
		
		// 회원정보리스트 
		public Map<String , Object> getUserList(Search search) throws Exception;
		
		// 회원정보수정
		public void updateUser(User user) throws Exception;
		
		// 회원 ID 중복 확인
		public boolean checkDuplication(String userId) throws Exception;
		
		//쿠폰 발급
		public void addCoupon(String couponId, User user) throws Exception;
			
		//autoComplete
		public List<String> autoCompleteUser(Search search) throws Exception;
		
		public User getKakaoUser(String kakaoId) throws Exception;
}