package com.model2.mvc.service.purchase.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PurchaseServiceTest {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    //@Test
    public void testAddPurchase() throws Exception {
        // 사용자 생성 또는 조회
        User buyer = userService.getUser("testUserId");
        if (buyer == null) {
            buyer = new User();
            buyer.setUserId("testUserId");
            buyer.setUserName("테스트 사용자");
            buyer.setPassword("testPass");
            buyer.setSsn("111111-222222");
            buyer.setPhone("010-1234-5678");
            buyer.setEmail("test@example.com");
            userService.addUser(buyer);
        }

        // 제품 생성 또는 조회
        Product product = new Product();
        product.setProdName("테스트 제품");
        product.setProdDetail("테스트 제품 상세");
        product.setManuDate("20230101");
        product.setPrice(10000);
        product.setFileName("testImage.jpg");
        product.setQuantity(100);
        product.setStock(100);
        productService.addProduct(product);

        // 구매 정보 생성
        Purchase purchase = new Purchase();
        purchase.setBuyer(buyer);
        purchase.setPurchaseProd(product);
        purchase.setPaymentOption("CD");
        purchase.setReceiverName("수령인 이름");
        purchase.setReceiverPhone("010-8765-4321");
        purchase.setDivyAddr("서울시 강남구 테헤란로");
        purchase.setDivyRequest("부재 시 경비실에 맡겨주세요");
        purchase.setDivyDate("2023-01-10");
        purchase.setSoldPrice(product.getPrice());
        purchase.setAmount(1);

        // 구매 정보 추가
        purchaseService.addPurchase(purchase);

        // 구매 번호 확인
        int tranNo = purchase.getTranNo();
        Assert.assertTrue("구매 번호가 0보다 커야 합니다.", tranNo > 0);

        // 구매 정보 조회
        Purchase retrievedPurchase = purchaseService.getPurchase(tranNo);

        // 결과 확인
        Assert.assertNotNull(retrievedPurchase);
        Assert.assertEquals("testUserId", retrievedPurchase.getBuyer().getUserId());
        Assert.assertEquals("테스트 제품", retrievedPurchase.getPurchaseProd().getProdName());
        Assert.assertEquals("카드", retrievedPurchase.getPaymentOption());
        Assert.assertEquals("수령인 이름", retrievedPurchase.getReceiverName());
    }

    //@Test
    public void testGetPurchase() throws Exception {
        // 기존 구매 번호 설정 (테스트 데이터에 따라 수정 필요)
        int tranNo = 1; // 존재하는 구매 번호로 설정

        // 구매 정보 조회
        Purchase purchase = purchaseService.getPurchase(tranNo);

        // 결과 확인
        Assert.assertNotNull(purchase);
        Assert.assertEquals(tranNo, purchase.getTranNo());
        // 추가적인 필드 검증 가능
    }

   // @Test
    public void testUpdatePurchase() throws Exception {
        // 구매 정보 생성 및 추가
        // (테스트 데이터에 따라 수정 필요)

        // 구매 정보 조회
        int tranNo = 1; // 존재하는 구매 번호로 설정
        Purchase purchase = purchaseService.getPurchase(tranNo);
        Assert.assertNotNull(purchase);

        // 구매 정보 업데이트
        purchase.setPaymentOption("현금");
        purchase.setReceiverName("업데이트된 수령인");
        purchase.setDivyRequest("배송 전 연락 바랍니다");
        purchaseService.updatePurchase(purchase);

        // 업데이트된 구매 정보 조회
        Purchase updatedPurchase = purchaseService.getPurchase(tranNo);

        // 결과 확인
        Assert.assertEquals("현금", updatedPurchase.getPaymentOption());
        Assert.assertEquals("업데이트된 수령인", updatedPurchase.getReceiverName());
        Assert.assertEquals("배송 전 연락 바랍니다", updatedPurchase.getDivyRequest());
    }

   // @Test
    public void testGetPurchaseList() throws Exception {
        // 검색 조건 설정
        Search search = new Search();
        search.setCurrentPage(1);
        search.setPageSize(5);
        search.setSearchCondition("4"); // 4: 구매 상태 코드로 검색 (예시)

        // 구매 리스트 조회
        Map<String, Object> map = purchaseService.getPurchaseList(search, "testUserId");

        // 결과 확인
        List<Purchase> list = (List<Purchase>) map.get("list");
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() >= 0);

        Integer totalCount = (Integer) map.get("totalCount");
        Assert.assertNotNull(totalCount);
    }

    @Test
    public void testDeletePurchase() throws Exception {
        // 사용자 조회 또는 생성
        User buyer = userService.getUser("testUserId");
        if (buyer == null) {
            buyer = new User();
            buyer.setUserId("testUserId");
            buyer.setUserName("테스트 사용자");
            buyer.setPassword("testPass");
            buyer.setSsn("111111-222222");
            buyer.setPhone("010-1234-5678");
            buyer.setEmail("test@example.com");
            userService.addUser(buyer);
        }

        // 제품 생성 및 추가
        Product product = new Product();
        product.setProdName("테스트 제품");
        product.setProdDetail("테스트 제품 상세");
        product.setManuDate("20230101");
        product.setPrice(10000);
        product.setFileName("testImage.jpg");
        product.setQuantity(100);
        product.setStock(100);
        productService.addProduct(product);

        // 생성된 제품 번호 확인
        int prodNo = product.getProdNo();
        Assert.assertTrue("제품 번호가 0보다 커야 합니다.", prodNo > 0);

        // 구매 정보 생성
        Purchase purchase = new Purchase();
        purchase.setBuyer(buyer);
        purchase.setPurchaseProd(product);
        purchase.setPaymentOption("CD");
        purchase.setReceiverName("수령인 이름");
        purchase.setReceiverPhone("010-8765-4321");
        purchase.setDivyAddr("서울시 강남구 테헤란로");
        purchase.setDivyRequest("부재 시 경비실에 맡겨주세요");
        purchase.setDivyDate("2023-01-10");
        purchase.setSoldPrice(product.getPrice());
        purchase.setAmount(1);

        // 구매 정보 추가
        purchaseService.addPurchase(purchase);

        // 생성된 거래 번호 확인
        int tranNo = purchase.getTranNo();
        Assert.assertTrue("거래 번호가 0보다 커야 합니다.", tranNo > 0);

        // 구매 정보 삭제
        purchaseService.deletePurchase(tranNo);

        // 삭제된 구매 정보 조회
        Purchase deletedPurchase = purchaseService.getPurchase(tranNo);

        // 결과 확인
        Assert.assertNull("구매 정보가 삭제되지 않았습니다.", deletedPurchase);
    }
}

