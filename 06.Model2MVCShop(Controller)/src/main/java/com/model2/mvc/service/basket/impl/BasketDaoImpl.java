package com.model2.mvc.service.basket.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.service.basket.BasketDao;
import com.model2.mvc.service.domain.Basket;

@Repository("basketDaoImpl")
public class BasketDaoImpl implements BasketDao {
	
	@Autowired
	@Qualifier("sqlSessionTemplate")
	SqlSession sqlSession;

	public BasketDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void insertBasket(Basket basket) throws Exception {
		sqlSession.insert("BasketMapper.addBasket", basket);
	}

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

}
