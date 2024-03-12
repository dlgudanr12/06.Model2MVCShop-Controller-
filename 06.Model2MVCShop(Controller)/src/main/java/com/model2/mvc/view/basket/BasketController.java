package com.model2.mvc.view.basket;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.service.basket.BasketService;
import com.model2.mvc.service.domain.Basket;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.user.UserService;

@Controller
public class BasketController {

	@Autowired
	@Qualifier("basketServiceImpl")
	BasketService basketService;

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

	public BasketController() {
		// TODO Auto-generated constructor stub
	}

	@RequestMapping("/addBasketView.do")
	public ModelAndView addBasketView(@ModelAttribute("basket") Basket basket, HttpSession session,
			ModelAndView modelAndView) throws Exception {

		System.out.println("\n:: ==> addBasketView() start......]");
		User user=(User)session.getAttribute("user");
		basket.setUserId(user.getUserId());
		basket.setBasketQuantity(1);
		
		basketService.addBasket(basket);
		modelAndView.setViewName("forward:/getProduct.do?prodNo="+basket.getProdNo());

		System.out.println("[addBasketView() end......]\n");

		return modelAndView;
	}

}
