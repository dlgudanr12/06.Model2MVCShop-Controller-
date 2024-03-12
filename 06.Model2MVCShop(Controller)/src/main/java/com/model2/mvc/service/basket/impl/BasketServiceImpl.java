package com.model2.mvc.service.basket.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.service.basket.BasketDao;
import com.model2.mvc.service.basket.BasketService;
import com.model2.mvc.service.domain.Basket;

@Service("basketServiceImpl")
public class BasketServiceImpl implements BasketService {

	@Autowired
	@Qualifier("basketDaoImpl")
	BasketDao basketDao;
	
	public BasketServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addBasket(Basket basket) throws Exception {
		basketDao.insertBasket(basket);
	}

	public void setBasketDao(BasketDao basketDao) {
		this.basketDao = basketDao;
	}

}
