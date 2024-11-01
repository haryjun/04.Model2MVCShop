package com.model2.mvc.service.product.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void testAddProduct() throws Exception {
        // 새로운 제품 생성
        Product product = new Product();
        product.setProdName("테스트 제품");
        product.setProdDetail("이것은 테스트 제품입니다.");
        product.setManuDate("20230101");
        product.setPrice(10000);
        product.setFileName("testImage.jpg");
        product.setQuantity(100);
        product.setStock(100);

        // 제품 추가
        productService.addProduct(product);

        // 생성된 제품 번호 가져오기 (시퀀스 사용 시 마지막 번호 가져오기 등)
        int prodNo = product.getProdNo();

        // 제품 조회
        Product retrievedProduct = productService.getProduct(prodNo);

        // 결과 확인
        Assert.assertNotNull(retrievedProduct);
        Assert.assertEquals("테스트 제품", retrievedProduct.getProdName());
        Assert.assertEquals("이것은 테스트 제품입니다.", retrievedProduct.getProdDetail());
        Assert.assertEquals("20230101", retrievedProduct.getManuDate());
        Assert.assertEquals(10000, retrievedProduct.getPrice());
        Assert.assertEquals("testImage.jpg", retrievedProduct.getFileName());
        Assert.assertEquals(100, retrievedProduct.getQuantity());
        Assert.assertEquals(100, retrievedProduct.getStock());
    }

    //@Test
    public void testGetProduct() throws Exception {
        // 기존 제품 번호 설정 (테스트 데이터에 따라 수정 필요)
        int prodNo = 1; // 존재하는 제품 번호로 설정

        // 제품 조회
        Product product = productService.getProduct(prodNo);

        // 결과 확인
        Assert.assertNotNull(product);
        Assert.assertEquals(prodNo, product.getProdNo());
        // 추가적인 필드 검증 가능
    }

   // @Test
    public void testUpdateProduct() throws Exception {
        // 기존 제품 번호 설정 (테스트 데이터에 따라 수정 필요)
        int prodNo = 1; // 존재하는 제품 번호로 설정

        // 제품 조회
        Product product = productService.getProduct(prodNo);
        Assert.assertNotNull(product);

        // 제품 정보 업데이트
        product.setProdName("업데이트된 제품명");
        product.setPrice(20000);
        product.setStock(50);

        productService.updateProduct(product);

        // 업데이트된 제품 조회
        Product updatedProduct = productService.getProduct(prodNo);

        // 결과 확인
        Assert.assertEquals("업데이트된 제품명", updatedProduct.getProdName());
        Assert.assertEquals(20000, updatedProduct.getPrice());
        Assert.assertEquals(50, updatedProduct.getStock());
    }

    //@Test
    public void testGetProductList() throws Exception {
        // 검색 조건 설정
        Search search = new Search();
        search.setCurrentPage(1);
        search.setPageSize(5);
        search.setSearchCondition("1"); // 1: 제품명으로 검색
        search.setSearchKeyword("테스트");

        // 제품 리스트 조회
        Map<String, Object> map = productService.getProductList(search);

        // 결과 확인
        List<Product> list = (List<Product>) map.get("list");
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);

        Integer totalCount = (Integer) map.get("totalCount");
        Assert.assertNotNull(totalCount);
    }

   //@Test
    public void testDeleteProduct() throws Exception {
        // 새로운 제품 생성 후 삭제 테스트
        Product product = new Product();
        product.setProdName("삭제 테스트 제품");
        product.setProdDetail("삭제될 제품입니다.");
        product.setManuDate("20230101");
        product.setPrice(10000);
        product.setFileName("deleteTestImage.jpg");
        product.setQuantity(100);
        product.setStock(100);

        // 제품 추가
        productService.addProduct(product);

        // 생성된 제품 번호 가져오기
        int prodNo = product.getProdNo();

        // 제품 삭제
        productService.deleteProduct(prodNo);

        // 삭제된 제품 조회
        Product deletedProduct = productService.getProduct(prodNo);

        // 결과 확인
        Assert.assertNull(deletedProduct);
    }
}
