package com.model2.mvc.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.model2.mvc.service.domain.User;

public class LogonCheckInterceptor extends HandlerInterceptorAdapter {

	public LogonCheckInterceptor() {
		System.out.println("==> LogonCheckInterceptor() default Constructor call......");
		System.out.println("\nCommon :: " + this.getClass() + "\n");
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		System.out.println("\n [LogonCheckInterceptor start......]");

		HttpSession session = request.getSession(true);
//		User user = null;
//		if ((user = (User) session.getAttribute("user")) == null) {
//			user = new User();
//		}
		User user = (User) session.getAttribute("user");

//		System.out.println("LogonCheckInterceptor.user.isActive() : " + user.isActive());
//		if (user.isActive()) {
		if (user != null) {
			String uri = request.getRequestURI();
			if (uri.indexOf("login") != -1) {
//			if(		uri.indexOf("addUserView") != -1 	|| 	uri.indexOf("addUser") != -1 || 
//					uri.indexOf("loginView") != -1 			||	uri.indexOf("login") != -1 		|| 
//					uri.indexOf("checkDuplication") != -1 ){
				request.getRequestDispatcher("/index.jsp").forward(request, response);
				System.out.println("[로그인 상태.. 로그인 후 불필요 한 요구....]");
				System.out.println("[LogonCheckInterceptor end......]\n");
				return false;
			}

			System.out.println("[로그인 상태...]");
			System.out.println("[LogonCheckInterceptor end......]\n");
			return true;
		} else {
			String uri = request.getRequestURI();
			if (/* uri.indexOf("logout") != -1 || */ uri.indexOf("login") != -1) {
				System.out.println("[로그 시도 상태....]");
				System.out.println("[LogonCheckInterceptor end......]\n");
				return true;
			}

			request.getRequestDispatcher("/index.jsp").forward(request, response);
			System.out.println("[로그인 이전......]");
			System.out.println("[LogonCheckInterceptor end......]\n");
			return false;
		}
	}

}
