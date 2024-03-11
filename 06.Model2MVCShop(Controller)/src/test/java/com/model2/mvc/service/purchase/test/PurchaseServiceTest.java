package com.model2.mvc.service.purchase.test;

import java.util.ArrayList;
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
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;

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
public class PurchaseServiceTest {

	// ==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

//	@Test
	public void testAddPurchase() throws Exception {

		Purchase purchase = new Purchase();
		Product product = productService.getProduct(10101);
		User user = userService.getUser("testUserId");

		purchase.setTranNo(10200);
		purchase.setPurchaseProd(product);
		purchase.setBuyer(user);
		purchase.setPaymentOption("1");
		purchase.setReceiverName("testReceiverName");
		purchase.setReceiverPhone("010-5555-7777");
		purchase.setDivyAddr("서울시 강남구");
		purchase.setDivyRequest("testDivyRequest");
		purchase.setTranCode(null);
		purchase.setDivyDate("20240331");
		purchase.setTranQuantity(1);

		purchaseService.addPurchase(purchase);

		purchase = purchaseService.getPurchase(10200);

		// ==> console 확인
		System.out.println(purchase);

		// ==> API 확인
		Assert.assertEquals(10200, purchase.getTranNo());
		Assert.assertEquals(10101, purchase.getPurchaseProd().getProdNo());
		Assert.assertEquals("testUserId", purchase.getBuyer().getUserId());
		Assert.assertEquals("1", purchase.getPaymentOption().trim());
		Assert.assertEquals("testReceiverName", purchase.getReceiverName());
		Assert.assertEquals("010-5555-7777", purchase.getReceiverPhone());
		Assert.assertEquals("서울시 강남구", purchase.getDivyAddr());
		Assert.assertEquals("testDivyRequest", purchase.getDivyRequest());
		Assert.assertEquals(null, purchase.getTranCode());
		System.out.println(purchase.getDivyDate().substring(0, 10));
		Assert.assertEquals("2024-03-31", purchase.getDivyDate().substring(0, 10));
		Assert.assertEquals(1,purchase.getTranQuantity());
		System.out.println("==============================================");

		purchase = new Purchase();
		product = productService.getProduct(10101);
		user = userService.getUser("testUserId");

		purchase.setTranNo(10201);
		purchase.setPurchaseProd(product);
		purchase.setBuyer(user);
		purchase.setPaymentOption("1");
		purchase.setReceiverName("testReceiverName");
		purchase.setReceiverPhone("010-5555-7777");
		purchase.setDivyAddr("서울시 강남구");
		purchase.setDivyRequest("testDivyRequest");
		purchase.setTranCode(null);
		purchase.setDivyDate("20240331");
		purchase.setTranQuantity(1);

		purchaseService.addPurchase(purchase);

		purchase = purchaseService.getPurchase(10201);

		// ==> console 확인
		System.out.println(purchase);

		// ==> API 확인
		Assert.assertEquals(10201, purchase.getTranNo());
		Assert.assertEquals(10101, purchase.getPurchaseProd().getProdNo());
		Assert.assertEquals("testUserId", purchase.getBuyer().getUserId());
		Assert.assertEquals("1", purchase.getPaymentOption().trim());
		Assert.assertEquals("testReceiverName", purchase.getReceiverName());
		Assert.assertEquals("010-5555-7777", purchase.getReceiverPhone());
		Assert.assertEquals("서울시 강남구", purchase.getDivyAddr());
		Assert.assertEquals("testDivyRequest", purchase.getDivyRequest());
		Assert.assertEquals(null, purchase.getTranCode());
		System.out.println(purchase.getDivyDate().substring(0, 10));
		Assert.assertEquals("2024-03-31", purchase.getDivyDate().substring(0, 10));
		Assert.assertEquals(1,purchase.getTranQuantity());
	}

//	@Test
	public void testGetPurchase() throws Exception {

		Purchase purchase = new Purchase();
		// ==> 필요하다면...
//			user.setUserId("testUserId");
//			user.setUserName("testUserName");
//			user.setPassword("testPasswd");
//			user.setSsn("1111112222222");
//			user.setPhone("111-2222-3333");
//			user.setAddr("경기도");
//			user.setEmail("test@test.com");

		purchase = purchaseService.getPurchase(10201);

		// ==> console 확인
		System.out.println(purchase);

		// ==> API 확인

		Assert.assertEquals(10201, purchase.getTranNo());
		Assert.assertEquals(10101, purchase.getPurchaseProd().getProdNo());
		Assert.assertEquals("testUserId", purchase.getBuyer().getUserId());
		Assert.assertEquals("1", purchase.getPaymentOption().trim());
		Assert.assertEquals("testReceiverName", purchase.getReceiverName());
		Assert.assertEquals("010-5555-7777", purchase.getReceiverPhone());
		Assert.assertEquals("서울시 강남구", purchase.getDivyAddr());
		Assert.assertEquals("testDivyRequest", purchase.getDivyRequest());
		Assert.assertEquals(null, purchase.getTranCode());
		Assert.assertEquals("20240331", purchase.getDivyDate().substring(0, 10).replaceAll("-", ""));
		Assert.assertEquals(1,purchase.getTranQuantity());
	}

//	@Test
	public void testUpdatePurchase() throws Exception {
		Purchase purchase = purchaseService.getPurchase(10200);
		Assert.assertNotNull(purchase);

		Assert.assertEquals(10200, purchase.getTranNo());
		Assert.assertEquals(10101, purchase.getPurchaseProd().getProdNo());
		Assert.assertEquals("testUserId", purchase.getBuyer().getUserId());
		Assert.assertEquals("1", purchase.getPaymentOption().trim());
		Assert.assertEquals("testReceiverName", purchase.getReceiverName());
		Assert.assertEquals("010-5555-7777", purchase.getReceiverPhone());
		Assert.assertEquals("서울시 강남구", purchase.getDivyAddr());
		Assert.assertEquals("testDivyRequest", purchase.getDivyRequest());
		Assert.assertEquals("2024-03-31", purchase.getDivyDate().substring(0, 10));
		Assert.assertEquals(1,purchase.getTranQuantity());

		purchase.setPaymentOption("2");
		purchase.setReceiverName("changeReceiverName");
		purchase.setReceiverPhone("010-7777-7777");
		purchase.setDivyAddr("서울시 서초구");
		purchase.setDivyRequest("changeDivyRequest");
		purchase.setDivyDate("20240401");
		purchase.setTranQuantity(1);

		System.out.println(purchase);

		purchaseService.updatePurchase(purchase);

		purchase = purchaseService.getPurchase(10200);
		Assert.assertNotNull(purchase);

		// ==> console 확인
		// System.out.println(user);

		// ==> API 확인
		Assert.assertEquals(10200, purchase.getTranNo());
		Assert.assertEquals(10101, purchase.getPurchaseProd().getProdNo());
		Assert.assertEquals("testUserId", purchase.getBuyer().getUserId());
		Assert.assertEquals("2", purchase.getPaymentOption().trim());
		Assert.assertEquals("changeReceiverName", purchase.getReceiverName());
		Assert.assertEquals("010-7777-7777", purchase.getReceiverPhone());
		Assert.assertEquals("서울시 서초구", purchase.getDivyAddr());
		Assert.assertEquals("changeDivyRequest", purchase.getDivyRequest());
		Assert.assertEquals("20240401", purchase.getDivyDate().substring(0, 10).replaceAll("-", ""));
		Assert.assertEquals(1,purchase.getTranQuantity());
	}

	// ==> 주석을 풀고 실행하면....
	@Test
	public void testGetProductListAll() throws Exception {

		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		Map<String, Object> map = purchaseService.getPurchaseList(search, "testUserId");

		List<Object> list = (List<Object>) map.get("list");
		Assert.assertEquals(2, list.size());

		// ==> console 확인
		// System.out.println(list);

		Integer totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);
	}

	@Test
	public void testUpdateTranCode() throws Exception {
		Purchase purchase = new Purchase();

		purchase.setTranNo(10200);
		purchase.setTranCode("1");

		purchaseService.updateTranCode(purchase);
		purchase = purchaseService.getPurchase(10200);

		Assert.assertEquals(10200, purchase.getTranNo());
		Assert.assertEquals(10101, purchase.getPurchaseProd().getProdNo());
		Assert.assertEquals("testUserId", purchase.getBuyer().getUserId());
		Assert.assertEquals("2", purchase.getPaymentOption().trim());
		Assert.assertEquals("changeReceiverName", purchase.getReceiverName());
		Assert.assertEquals("010-7777-7777", purchase.getReceiverPhone());
		Assert.assertEquals("서울시 서초구", purchase.getDivyAddr());
		Assert.assertEquals("changeDivyRequest", purchase.getDivyRequest());
		Assert.assertEquals("2024-04-01", purchase.getDivyDate().substring(0, 10));
		Assert.assertEquals("2", purchase.getTranCode().trim());
		// ============================================

		purchase.setTranNo(10200);
		purchase.setTranCode("2");

		purchaseService.updateTranCode(purchase);
		purchase = purchaseService.getPurchase(10200);

		Assert.assertEquals(10200, purchase.getTranNo());
		Assert.assertEquals(10101, purchase.getPurchaseProd().getProdNo());
		Assert.assertEquals("testUserId", purchase.getBuyer().getUserId());
		Assert.assertEquals("2", purchase.getPaymentOption().trim());
		Assert.assertEquals("changeReceiverName", purchase.getReceiverName());
		Assert.assertEquals("010-7777-7777", purchase.getReceiverPhone());
		Assert.assertEquals("서울시 서초구", purchase.getDivyAddr());
		Assert.assertEquals("changeDivyRequest", purchase.getDivyRequest());
		Assert.assertEquals("2024-04-01", purchase.getDivyDate().substring(0, 10));
		Assert.assertEquals("3", purchase.getTranCode().trim());

	}
	
	@Test
	public void testGetDeliveryListAll() throws Exception {

		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		Map<String, Object> map = purchaseService.getDeliveryList(search);

		List<Object> list = (List<Object>) map.get("list");
		Assert.assertEquals(3, list.size());

		// ==> console 확인
		// System.out.println(list);

		Integer totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);
	}
	
	@Test
	public void testGetDeliveryListOfBuyer() throws Exception {

		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchCondition("0");
		search.setSearchKeyword("user01");
		Map<String, Object> map = purchaseService.getDeliveryList(search);

		List<Object> list = (List<Object>) map.get("list");
		Assert.assertEquals(3, list.size());

		// ==> console 확인
		// System.out.println(list);

		Integer totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);
	}
	
	@Test
	public void testGetDeliveryListOfTranCode() throws Exception {

		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		List <String> tranCode= new ArrayList<String>();
		tranCode.add("1");
		tranCode.add("3");
		search.setSearchTranCodeOn(tranCode);
		Map<String, Object> map = purchaseService.getDeliveryList(search);

		List<Object> list = (List<Object>) map.get("list");
		Assert.assertEquals(3, list.size());

		// ==> console 확인
		// System.out.println(list);

		Integer totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);
	}
	
	@Test
	public void testGetDeliveryListOfOrderBy() throws Exception {

		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchOrderBy("1-1");
		Map<String, Object> map = purchaseService.getDeliveryList(search);

		List<Object> list = (List<Object>) map.get("list");
		Assert.assertEquals(3, list.size());

		Integer totalCount = (Integer) map.get("totalCount");
		System.out.println(list);
		System.out.println(totalCount);
		System.out.println("==============================");
		
		search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchOrderBy("1-2");
		map = purchaseService.getDeliveryList(search);

		list = (List<Object>) map.get("list");
		Assert.assertEquals(3, list.size());

		System.out.println(list);
		totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);
	}
}