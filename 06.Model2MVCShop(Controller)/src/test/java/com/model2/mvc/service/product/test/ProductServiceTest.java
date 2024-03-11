package com.model2.mvc.service.product.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

/*
 *	FileName :  UserServiceTest.java
 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
 * ㅇ @ContextConfiguration : Meta-data location 지정
 * ㅇ @Test : 테스트 실행 소스 지정
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/context-*.xml" })
public class ProductServiceTest {

	// ==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

//	@Test
	public void testAddProduct() throws Exception {

		Product product = new Product();
		product.setProdNo(10100);
		product.setProdName("testProdName");
		product.setProdDetail("testProdDetail");
		product.setManuDate("20240101");
		product.setPrice(Integer.parseInt("10000"));
		product.setFileName("testImageFileName");
		product.setProdQuantity(100);

		productService.addProduct(product);

		product = productService.getProduct(10100);

		// ==> console 확인
		System.out.println(product);

		// ==> API 확인
		Assert.assertEquals(10100, product.getProdNo());
		Assert.assertEquals("testProdName", product.getProdName());
		Assert.assertEquals("testProdDetail", product.getProdDetail());
		Assert.assertEquals("20240101", product.getManuDate());
		Assert.assertEquals(Integer.parseInt("10000"), product.getPrice());
		Assert.assertEquals("testImageFileName", product.getFileName());
		Assert.assertEquals(100, product.getProdQuantity());

		System.out.println("==============================================");

		product = new Product();
		product.setProdNo(10101);
		product.setProdName("testProdName");
		product.setProdDetail("testProdDetail");
		product.setManuDate("20240101");
		product.setPrice(Integer.parseInt("10000"));
		product.setFileName("testImageFileName");
		product.setProdQuantity(100);

		productService.addProduct(product);

		product = productService.getProduct(10101);

		// ==> console 확인
		System.out.println(product);

		// ==> API 확인
		Assert.assertEquals(10101, product.getProdNo());
		Assert.assertEquals("testProdName", product.getProdName());
		Assert.assertEquals("testProdDetail", product.getProdDetail());
		Assert.assertEquals("20240101", product.getManuDate());
		Assert.assertEquals(Integer.parseInt("10000"), product.getPrice());
		Assert.assertEquals("testImageFileName", product.getFileName());
		Assert.assertEquals(100, product.getProdQuantity());
	}

//	@Test
	public void testGetProduct() throws Exception {

		Product product = new Product();
		// ==> 필요하다면...
//			user.setUserId("testUserId");
//			user.setUserName("testUserName");
//			user.setPassword("testPasswd");
//			user.setSsn("1111112222222");
//			user.setPhone("111-2222-3333");
//			user.setAddr("경기도");
//			user.setEmail("test@test.com");

		product = productService.getProduct(10101);

		// ==> console 확인
		System.out.println(product);

		// ==> API 확인
		Assert.assertEquals("testProdName", product.getProdName());
		Assert.assertEquals("testProdDetail", product.getProdDetail());
		Assert.assertEquals("20240101", product.getManuDate());
		Assert.assertEquals(10000, product.getPrice());
		Assert.assertEquals("testImageFileName", product.getFileName());
		Assert.assertEquals(100, product.getProdQuantity());

		Assert.assertNotNull(productService.getProduct(10002));
		Assert.assertNotNull(productService.getProduct(10003));
	}

//	@Test
	public void testUpdateProduct() throws Exception {
		Product product = productService.getProduct(10100);
		Assert.assertNotNull(product);

		Assert.assertEquals("testProdName", product.getProdName());
		Assert.assertEquals("testProdDetail", product.getProdDetail());
		Assert.assertEquals("20240101", product.getManuDate());
		Assert.assertEquals(10000, product.getPrice());
		Assert.assertEquals("testImageFileName", product.getFileName());
		Assert.assertEquals(100, product.getProdQuantity());

		product.setProdName("changeProdName");
		product.setProdDetail("changeProdDetail");
		product.setManuDate("20240201");
		product.setPrice(20000);
		product.setFileName("changeImageFileName");
		product.setProdQuantity(200);
		
		System.out.println(product);

		productService.updateProduct(product);

		product = productService.getProduct(10100);
		Assert.assertNotNull(product);

		// ==> console 확인
		// System.out.println(user);

		// ==> API 확인
		Assert.assertEquals("changeProdName", product.getProdName());
		Assert.assertEquals("changeProdDetail", product.getProdDetail());
		Assert.assertEquals("20240201", product.getManuDate());
		Assert.assertEquals(20000, product.getPrice());
		Assert.assertEquals("changeImageFileName", product.getFileName());
		Assert.assertEquals(200, product.getProdQuantity());
	}

	// ==> 주석을 풀고 실행하면....
	@Test
	public void testGetProductListAll() throws Exception {

		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		Map<String, Object> map = productService.getProductList(search);

		List<Object> list = (List<Object>) map.get("list");
		Assert.assertEquals(3, list.size());

		// ==> console 확인
		// System.out.println(list);

		Integer totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);

		System.out.println("=======================================");

		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchCondition("0");
		search.setSearchKeyword("");
		map = productService.getProductList(search);

		list = (List<Object>) map.get("list");
		Assert.assertEquals(3, list.size());

		// ==> console 확인
		// System.out.println(list);

		totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);
	}

	@Test
	public void testGetProductListByProdNo() throws Exception {

		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchCondition("0");
		search.setSearchKeyword("1010");
		Map<String, Object> map = productService.getProductList(search);

		List<Object> list = (List<Object>) map.get("list");
		Assert.assertEquals(2, list.size());

		// ==> console 확인
		// System.out.println(list);

		Integer totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);

		System.out.println("=======================================");

		search.setSearchCondition("0");
		search.setSearchKeyword("" + System.currentTimeMillis());
		map = productService.getProductList(search);

		list = (List<Object>) map.get("list");
		Assert.assertEquals(0, list.size());

		// ==> console 확인
		// System.out.println(list);

		totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);
	}

	@Test
	public void testGetProductListByProdName() throws Exception {

		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchCondition("1");
		search.setSearchKeyword("ProdName");
		Map<String, Object> map = productService.getProductList(search);

		List<Object> list = (List<Object>) map.get("list");
		System.out.println(list.size());
		Assert.assertEquals(2, list.size());

		// ==> console 확인
		System.out.println(list);

		Integer totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);

		System.out.println("=======================================");

		search.setSearchCondition("1");
		search.setSearchKeyword("" + System.currentTimeMillis());
		map = productService.getProductList(search);

		list = (List<Object>) map.get("list");
		Assert.assertEquals(0, list.size());

		// ==> console 확인
		System.out.println(list);

		totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);
	}

	@Test
	public void testGetProductListByPriceOrderBy() throws Exception {

		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchOrderBy("ASC");
		Map<String, Object> map = productService.getProductList(search);

		List<Object> list = (List<Object>) map.get("list");
		System.out.println(list.size());
		Assert.assertEquals(3, list.size());

		// ==> console 확인
		System.out.println(list);

		Integer totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);

		System.out.println("=======================================");

		search.setSearchOrderBy("DESC");
		map = productService.getProductList(search);

		list = (List<Object>) map.get("list");
		Assert.assertEquals(3, list.size());

		// ==> console 확인
		System.out.println(list);

		totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);
	}

	@Test
	public void testGetProductListByPriceLimit() throws Exception {

		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchPriceLowerLimit(10000);
		search.setSearchPriceUpperLimit(50000);
		search.setSearchOrderBy("2");
		Map<String, Object> map = productService.getProductList(search);

		List<Object> list = (List<Object>) map.get("list");
		System.out.println(list.size());
		Assert.assertEquals(3, list.size());

		// ==> console 확인
		System.out.println(list);

		Integer totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);

		System.out.println("=======================================");

		search.setSearchPriceLowerLimit(100000);
		search.setSearchPriceUpperLimit(200000);
		search.setSearchOrderBy("1");
		System.out.println(search.getSearchTranCodeOn());
		map = productService.getProductList(search);

		list = (List<Object>) map.get("list");
		Assert.assertEquals(1, list.size());

		// ==> console 확인
		System.out.println(list);

		totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);
	}
}