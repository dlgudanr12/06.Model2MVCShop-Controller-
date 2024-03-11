package com.model2.mvc.view.purchase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;

@Controller("purchaseController")
public class PurchaseController {

	@Autowired
	@Qualifier("purchaseServiceImpl")
	PurchaseService purchaseService;

	@Autowired
	@Qualifier("productServiceImpl")
	ProductService productService;

	@Autowired
	@Qualifier("userServiceImpl")
	UserService userService;

	@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;

	@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;

	public PurchaseController() {
		// TODO Auto-generated constructor stub
	}

	@RequestMapping("/addPurchaseView.do")
	public ModelAndView addProductView(@ModelAttribute("purchase") Purchase purchase,
			@ModelAttribute("product") Product product, @ModelAttribute("user") User user, HttpSession session,
			ModelAndView modelAndView) throws Exception {

		System.out.println("\n:: ==> addPurchaseView() start......]");
		product = productService.getProduct(product.getProdNo());

		if (session.getAttribute("user") == null) {
			modelAndView.setViewName("redirect:/purchase/notPurchase.jsp");
		}

		purchase.setPurchaseProd(product);
		purchase.setBuyer((User) session.getAttribute("user"));

		modelAndView.addObject("purchase", purchase);
		modelAndView.setViewName("forward:/purchase/addPurchaseView.jsp");

		System.out.println("[addPurchaseView() end......]\n");

		return modelAndView;
	}

	@RequestMapping("/addPurchase.do")
	public ModelAndView addPurchase(@ModelAttribute("purchase") Purchase purchase,
			@ModelAttribute("product") Product product, @RequestParam("buyerId") String buyerId,
			ModelAndView modelAndView) throws Exception {
		System.out.println("\n:: ==> addPurchase() start......]");
		if (purchase.getTranQuantity() > 0) {
			purchase.setBuyer(userService.getUser(buyerId));
			purchase.setPurchaseProd(product);
			purchase.setTranCode("1");

			modelAndView.addObject("purchase", purchaseService.addPurchase(purchase));
			modelAndView.setViewName("forward:/purchase/addPurchase.jsp");

		} else {
			modelAndView.setViewName("");// 오류페이지로 전송
		}

		System.out.println("[addPurchase() end......]\n");

		return modelAndView;
	}

	@RequestMapping("/getPurchase.do")
	public ModelAndView getPurchase(@ModelAttribute("purchase") Purchase purchase, ModelAndView modelAndView)
			throws Exception {
		System.out.println("\n:: ==> getPurchase() start......]");

		modelAndView.addObject("purchase", purchaseService.getPurchase(purchase.getTranNo()));
		modelAndView.setViewName("forward:/purchase/addPurchase.jsp");

		System.out.println("[getPurchase() end......]\n");

		return modelAndView;
	}

	@RequestMapping("/listPurchase.do")
	public ModelAndView listPurchase(@ModelAttribute("search") Search search, HttpSession session,
			ModelAndView modelAndView) throws Exception {
		System.out.println("\n:: ==> listPurchase() start......]");

		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}

		search.setPageSize(pageSize);

		Map<String, Object> map = purchaseService.getPurchaseList(search,
				((User) session.getAttribute("user")).getUserId());
		Page resultPage = new Page(search.getCurrentPage(), ((Integer) map.get("totalCount")).intValue(), pageUnit,
				pageSize);
		System.out.println("listPurchase.resultPage ::" + resultPage);

		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		modelAndView.setViewName("forward:/purchase/listPurchase.jsp");

		System.out.println("[listPurchase() end......]\n");

		return modelAndView;
	}

	@RequestMapping("/updatePurchaseView.do")
	public ModelAndView updatePurchaseView(@ModelAttribute("purchase") Purchase purchase, ModelAndView modelAndView)
			throws Exception {
		System.out.println("\n:: ==> updatePurchaseView() start......]");

		modelAndView.addObject("purchase", purchaseService.getPurchase(purchase.getTranNo()));
		modelAndView.setViewName("forward:/purchase/updatePurchase.jsp");

		System.out.println("[updatePurchaseView() end......]\n");

		return modelAndView;
	}

	@RequestMapping("/updatePurchase.do")
	public ModelAndView updatePurchase(@ModelAttribute("purchase") Purchase purchase, ModelAndView modelAndView)
			throws Exception {
		System.out.println("\n:: ==> updatePurchase() start......]");

		purchase = purchaseService.updatePurchase(purchase);
		modelAndView.setViewName("redirect:/getPurchase.do?tranNo=" + purchase.getTranNo());

		System.out.println("[updatePurchase() end......]\n");

		return modelAndView;
	}

	@RequestMapping("/updateTranCode.do")
	public ModelAndView updateTranCode(@ModelAttribute("purchase") Purchase purchase,
			@ModelAttribute("search") Search search, ModelAndView modelAndView) throws Exception {
		System.out.println("\n:: ==> updateTranCode() start......]");

		if (purchase.getTranNo() != 0 && purchase.getTranCode() != null) {
			System.out.println("UpdateTranCode.tranNo : _" + purchase.getTranNo() + "_");
			System.out.println("UpdateTranCode.currentPage : _" + purchase.getTranCode() + "_");
		}

		purchaseService.updateTranCode(purchase);

		if (purchase.getTranNo() != 0 && purchase.getTranCode() != null) {
			if (purchase.getTranCode().equals("1")) {
				modelAndView.setViewName("forward:/listDelivery.do");
			}

			if (purchase.getTranCode().equals("2")) {
				modelAndView.setViewName("redirect:/listPurchase.do?currentPage=" + search.getCurrentPage());
			}
		}

		System.out.println("[updateTranCode() end......]\n");

		return modelAndView;
	}

	@RequestMapping("/listDelivery.do")
	public ModelAndView listDelivery(@ModelAttribute("search") Search search, HttpServletRequest request,
			ModelAndView modelAndView) throws Exception {
		System.out.println("\n:: ==> listDelivery() start......]");

		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}

		List<String> searchTranCodeOn = new ArrayList<String>();
		List<Integer> listTranCode = new ArrayList<Integer>();
		for (int i = 0; i <= 3; i++) {
			System.out.println("PurchaseController.listProductAction.searchTranCodeOn" + i + ":"
					+ request.getParameter("searchTranCodeOn" + i));
			
			searchTranCodeOn.add(request.getParameter("searchTranCodeOn" + i));
			if (request.getParameter("searchTranCodeOn" + i) != null) {
				listTranCode.add(Integer.parseInt(request.getParameter("searchTranCodeOn" + i)));
			}
		}
		search.setSearchTranCodeOn(searchTranCodeOn);
		search.setListTranCode(listTranCode);

		search.setPageSize(pageSize);

		Map<String, Object> map = purchaseService.getDeliveryList(search);

		Page resultPage = new Page(search.getCurrentPage(), ((Integer) map.get("totalCount")).intValue(), pageUnit,
				pageSize);
		System.out.println("listDelivery.resultPage ::" + resultPage);

		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);

		modelAndView.setViewName("forward:/purchase/listDelivery.jsp");

		System.out.println("[listDelivery() end......]\n");

		return modelAndView;
	}

}
