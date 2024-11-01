package com.model2.mvc.service.user.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    //@Test
    public void testAddUser() throws Exception {
        // 새로운 사용자 생성
        User user = new User();
        user.setUserId("testUserId");
        user.setUserName("testUserName");
        user.setPassword("test111");
        user.setSsn("111-222");
        user.setPhone("010-1234-5678");
        user.setEmail("test@example.com");
        user.setKakaoId("testKakaoId");
        user.setProfile_nickname("testNickname");
        user.setProfile_image("testProfileImage");
        user.setPostcode("12345");
        user.setAddress("서울시 강남구");
        user.setAddressDetail("테헤란로 123");
        user.setAddr(user.getPostcode(), user.getAddress(), user.getAddressDetail());

        // 사용자 추가
        userService.addUser(user);

        // 사용자 조회
        User retrievedUser = userService.getUser("testUserId");

        // 결과 확인
        Assert.assertNotNull(retrievedUser);
        Assert.assertEquals("testUserId", retrievedUser.getUserId());
        Assert.assertEquals("testUserName", retrievedUser.getUserName());
        Assert.assertEquals("testPassword", retrievedUser.getPassword());
        Assert.assertEquals("111111-2222222", retrievedUser.getSsn());
        Assert.assertEquals("010-1234-5678", retrievedUser.getPhone());
        Assert.assertEquals("test@example.com", retrievedUser.getEmail());
        Assert.assertEquals("testKakaoId", retrievedUser.getKakaoId());
        Assert.assertEquals("testNickname", retrievedUser.getProfile_nickname());
        Assert.assertEquals("testProfileImage", retrievedUser.getProfile_image());
        Assert.assertEquals("12345 서울시 강남구 테헤란로 123", retrievedUser.getAddr());
    }

    @Test
    public void testGetUser() throws Exception {
        // 사용자 조회
        User user = userService.getUser("testUserId");

        // 결과 확인
        Assert.assertNotNull(user);
        Assert.assertEquals("testUserId", user.getUserId());
        // 추가적인 필드 확인 가능
    }

    //@Test
    public void testUpdateUser() throws Exception {
        // 사용자 조회
        User user = userService.getUser("testUserId");
        Assert.assertNotNull(user);

        // 사용자 정보 업데이트
        user.setUserName("updatedName");
        user.setPhone("010-8765-4321");
        user.setEmail("updated@example.com");
        user.setAddressDetail("변경된 주소 상세");
        user.setAddr(user.getPostcode(), user.getAddress(), user.getAddressDetail());

        userService.updateUser(user);

        // 업데이트된 사용자 조회
        User updatedUser = userService.getUser("testUserId");

        // 결과 확인
        Assert.assertEquals("updatedName", updatedUser.getUserName());
        Assert.assertEquals("010-8765-4321", updatedUser.getPhone());
        Assert.assertEquals("updated@example.com", updatedUser.getEmail());
        Assert.assertEquals("12345 서울시 강남구 변경된 주소 상세", updatedUser.getAddr());
    }

    //@Test
    public void testCheckDuplication() throws Exception {
        // 중복 체크 (이미 존재하는 ID)
        boolean isDuplicated = userService.checkDuplication("testUserId");
        Assert.assertFalse(isDuplicated);

        // 중복 체크 (새로운 ID)
        String newUserId = "newUserId" + System.currentTimeMillis();
        isDuplicated = userService.checkDuplication(newUserId);
        Assert.assertTrue(isDuplicated);
    }

   //@Test
    public void testGetUserList() throws Exception {
        // 검색 조건 설정
        Search search = new Search();
        search.setCurrentPage(1);
        search.setPageSize(3);
        search.setSearchCondition("0"); // 0: userId로 검색
        search.setSearchKeyword("testUserId");

        // 사용자 리스트 조회
        Map<String, Object> map = userService.getUserList(search);

        // 결과 확인
        List<User> list = (List<User>) map.get("list");
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);

        Integer totalCount = (Integer) map.get("totalCount");
        Assert.assertNotNull(totalCount);
    }
}
