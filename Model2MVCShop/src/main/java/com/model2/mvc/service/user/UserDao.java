package com.model2.mvc.service.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.User;


@Mapper
public interface UserDao {
	
		// INSERT
		public void addUser(User user) throws Exception ;

		// SELECT ONE
		public User getUser(String userId) throws Exception ;

		// SELECT LIST
		public List<User> getUserList(Search search) throws Exception ;

		// UPDATE
		public void updateUser(User user) throws Exception ;
		
		// 게시판 Page 처리를 위한 전체Row(totalCount)  return
		public int getTotalCount(Search search) throws Exception ;
			
		public void addCoupon(User user) throws Exception;
			
		public void useCoupon(User user) throws Exception;
			
		public List<String> getAutoComplete(Search search) throws Exception;
		
		public User getKakaoUser(String kakaoId) throws Exception;
	
}