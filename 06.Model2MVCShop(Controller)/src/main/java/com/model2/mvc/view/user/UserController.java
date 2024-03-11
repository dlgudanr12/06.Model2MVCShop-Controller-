package com.model2.mvc.view.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserService;

@Controller("userController")
public class UserController {

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	public UserController() {
		System.out.println(":: UserController default Contrctor call : " +this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit'] !=null ? commonProperties['pageUnit'] : 3}")
//	@Value("#{commonProperties['pageUnit'] ?: 3}")
//	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize'] !=null ? commonProperties['pageSize'] : 2}")
//	@Value("#{commonProperties['pageSize'] ?: 2}")
//	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	@RequestMapping("/addUserView.do")
	public String addUserView() throws Exception {

		System.out.println("/addUserView.do");
		
		return "redirect:/user/addUserView.jsp";
	}

	@RequestMapping("/addUser.do")
	public ModelAndView addUser(@ModelAttribute("user") User user) throws Exception {
		System.out.println("\n:: ==> addUser() start......]");

		System.out.println("addUser ::" + user);

		userService.addUser(user);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/user/loginView.jsp");

		System.out.println("[addUser() end......]\n");
		return modelAndView;
	}

	@RequestMapping("/getUser.do")
	public ModelAndView getUser(@ModelAttribute("user") User user) throws Exception {
		System.out.println("\n:: ==> getUser() start......]");

		System.out.println("getUser ::" + user);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/user/getUser.jsp");
		modelAndView.addObject("user", userService.getUser(user.getUserId()));

		System.out.println("[getUser() end......]\n");
		return modelAndView;
	}

	@RequestMapping("/updateUserView.do")
	public ModelAndView updateUserView(@ModelAttribute("user") User user) throws Exception {
		System.out.println("\n:: ==> updateUserView() start......]");

		System.out.println("updateUserView ::" + user);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/user/updateUser.jsp");
		modelAndView.addObject("user", userService.getUser(user.getUserId()));

		System.out.println("[updateUserView() end......]\n");
		return modelAndView;
	}

	@RequestMapping("/updateUser.do")
	public ModelAndView updateUser(@ModelAttribute("user") User user, HttpSession session) throws Exception {
		System.out.println("\n:: ==> updateUser() start......]");

		System.out.println("updateUser ::" + user);

		userService.updateUser(user);

		String sessionId = ((User) session.getAttribute("user")).getUserId();
		if (sessionId.equals(user.getUserId())) {
			user.setActive(true);
			session.setAttribute("user", user);
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/getUser.do?userId=" + user.getUserId());

		System.out.println("[updateUser() end......]\n");
		return modelAndView;
	}
	
	@RequestMapping("/loginView.do")
	public String loginView() throws Exception{
		
		System.out.println("/loginView.do");

		return "redirect:/user/loginView.jsp";
	}

	@RequestMapping("/login.do")
	public ModelAndView login(@ModelAttribute("user") User user,HttpSession session) throws Exception {
		System.out.println("\n:: ==> login() start......]");

		System.out.println("login ::" + user);
		
		user=userService.loginUser(user);
		System.out.println("UserController.login.user.isActive() : "+ user.isActive());
		session.setAttribute("user",  user);
		
		//Service에 따로 loginUser() 안 쓰고 login 하는 법
		//하지만, presentation 단에 논리를 맡기고 싶지 않아서 사용 안 함.
//		User dbUser=userService.getUser(user.getUserId());
//		
//		if( user.getPassword().equals(dbUser.getPassword())){
//			session.setAttribute("user", dbUser);
//		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/index.jsp");

		System.out.println("[login() end......]\n");
		return modelAndView;
	}

	@RequestMapping("/logout.do")
	public ModelAndView logout(HttpSession session) throws Exception {
		System.out.println("\n:: ==> logout() start......]");

		session.invalidate();

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/index.jsp");

		System.out.println("[logout() end......]\n");
		return modelAndView;
	}

	@RequestMapping("/checkDuplication.do")
	public ModelAndView checkDuplication(@ModelAttribute("user") User user) throws Exception {
		System.out.println("\n:: ==> checkDuplication() start......]");

		System.out.println("checkDuplication.user ::" + user);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/user/checkDuplication.jsp");
		modelAndView.addObject("result", userService.checkDuplication(user.getUserId()));
		modelAndView.addObject("userId", user.getUserId());

		System.out.println("[checkDuplication() end......]\n");
		return modelAndView;
	}

	@RequestMapping("/listUser.do")
	public ModelAndView listUser(@ModelAttribute("search") Search search, HttpServletRequest request)
			throws Exception {
		System.out.println("\n:: ==> listUser() start......]");

		System.out.println("listUser.search ::" + search);

		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}

		// web.xml meta-data 로 부터 상수 추출
		search.setPageSize(pageSize);

		// Business logic 수행
		Map<String, Object> map = userService.getUserList(search);

		Page resultPage = new Page(search.getCurrentPage(), ((Integer) map.get("totalCount")).intValue(), pageUnit,
				pageSize);
		System.out.println("listUser.resultPage ::" + resultPage);

		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/user/listUser.jsp");
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);

		System.out.println("[listUser() end......]\n");
		return modelAndView;
	}
}
