package com.model2.mvc.view.basket;

import java.util.Map;

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
import com.model2.mvc.service.basket.BasketService;
import com.model2.mvc.service.domain.Basket;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.user.UserService;

@Controller
public class BasketController {

	@Autowired
	@Qualifier("basketServiceImpl")
	private BasketService basketService;

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;

	@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;

	public BasketController() {
		// TODO Auto-generated constructor stub
	}

	@RequestMapping("/addBasket.do")
	public ModelAndView addBasket(@ModelAttribute("basket") Basket basket, HttpSession session,
			ModelAndView modelAndView) throws Exception {

		System.out.println("\n:: ==> addBasket() start......]");
		User user = (User) session.getAttribute("user");
		basket.setUserId(user.getUserId());
		basket.setBasketQuantity(1);

		basketService.addBasket(basket);
		modelAndView.setViewName("forward:/getProduct.do?menu=search");

		System.out.println("[addBasket() end......]\n");

		return modelAndView;
	}

	@RequestMapping("/listBasket.do")
	public ModelAndView listBasket(@ModelAttribute("search") Search search, HttpSession session,
			ModelAndView modelAndView) throws Exception {
		System.out.println("\n:: ==> listBasket() start......]");
		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);

		Map<String, Object> map = basketService.getBasketList(search,
				((User) session.getAttribute("user")).getUserId());
		Page resultPage = new Page(search.getCurrentPage(), ((Integer) map.get("totalCount")).intValue(), pageUnit,
				pageSize);
		System.out.println("listBasket.resultPage ::" + resultPage);

		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		modelAndView.setViewName("forward:/basket/listBasket.jsp");

		System.out.println("[listBasket() end......]\n");
		return modelAndView;
	}

	@RequestMapping("/removeBasket.do")
	public ModelAndView removeBasket(@ModelAttribute("basket") Basket basket, @ModelAttribute("search") Search search,
			HttpSession session, ModelAndView modelAndView) throws Exception {

		System.out.println("\n:: ==> removeBasket() start......]");
		User user = (User) session.getAttribute("user");
		basket.setUserId(user.getUserId());

		basketService.removeBasket(basket);
		modelAndView.addObject("search", search);
		modelAndView.setViewName("forward:/listBasket.do");

		System.out.println("[removeBasket() end......]\n");

		return modelAndView;
	}

}
