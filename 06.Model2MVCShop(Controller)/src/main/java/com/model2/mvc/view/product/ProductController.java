package com.model2.mvc.view.product;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@Controller("productController")
public class ProductController {

	@Autowired
	@Qualifier("productServiceImpl")
	ProductService productService;

	@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;

	@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;

	public ProductController() {
		System.out.println(":: ProductController default Contrctor call : " + this.getClass());
	}

	@RequestMapping("/addProduct.do")
	public String addProduct(@ModelAttribute("product") Product product, Model model) throws Exception {

		System.out.println("\n:: ==> addProduct() start......]");

		productService.addProduct(product);

		model.addAttribute("product", productService.getProduct(product.getProdNo()));

		System.out.println("[addProduct() end......]\n");

		return "forward:/product/addProduct.jsp";
	}

	@RequestMapping("/getProduct.do")
	public String getProduct(@ModelAttribute("product") Product product, @RequestParam("menu") String menu,
			HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		System.out.println("\n:: ==> getProduct() start......]");
		System.out.println("ProductController.getProduct.manu : "+menu);

		String resultPath = "";
		String history = "";
		String cookieNewValue;
		
		product=productService.getProduct(product.getProdNo());
		model.addAttribute("product", product);
		
		history += product.getProdNo() + ":" + product.getProdName().replaceAll(" ", "_") + "/";
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals("history")) {
				cookieNewValue = cookie.getValue().replaceAll(history, "");
				history += cookieNewValue;
			}
		}
		System.out.println("history= " + history);
		Cookie cookie = new Cookie("history", history);

		response.addCookie(cookie);

		resultPath = "forward:/product/getProduct.jsp";
		if (menu != null) {
			if (menu.equals("manage")) {
//				resultPath = "forward:/product/notUpdateProduct.jsp"; //´õ ÀÌ»ó ¾È¾¸
				resultPath = "forward:/product/updateProduct.jsp";
			}
		}

		System.out.println("[getProduct() end......]\n");
		return resultPath;
	}
	
	@RequestMapping("/listProduct.do")
	public String listProduct(@ModelAttribute("search") Search search,@RequestParam("menu") String menu, Model model) throws Exception {

		System.out.println("\n:: ==> listProduct() start......]");
		
		if(search.getCurrentPage()==0) {
			search.setCurrentPage(1);
		}
		
		search.setPageSize(pageSize);
		
		Map<String, Object> map =productService.getProductList(search);
		
		Page resultPage = new Page(search.getCurrentPage(), ((Integer) map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("listProduct.resultPage ::" + resultPage);

		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);

		model.addAttribute("menu", menu);

		System.out.println("[listProduct() end......]\n");

		return "forward:/product/listProduct.jsp";
	}
	
	@RequestMapping("/updateProduct.do")
	public String updateProduct(@ModelAttribute("product") Product product, Model model) throws Exception {

		System.out.println("\n:: ==> updateProduct() start......]");

		productService.updateProduct(product);

		model.addAttribute("product", productService.getProduct(product.getProdNo()));

		System.out.println("[updateProduct() end......]\n");

		return "redirect:/getProduct.do?prodNo="+product.getProdNo()+"&menu=manage";
	}
	
	@RequestMapping("/updateProductView.do")
	public String updateProductView(@ModelAttribute("product") Product product, Model model) throws Exception {

		System.out.println("\n:: ==> updateProductView() start......]");

		model.addAttribute("product", productService.getProduct(product.getProdNo()));

		System.out.println("[updateProductView() end......]\n");

		return "forward:/product/updateProduct.jsp";
	}
	
	@RequestMapping("/updateQuantity.do")
	public String updateQuantity(@ModelAttribute("product") Product product, @ModelAttribute("search") Search search,Model model) throws Exception {

		System.out.println("\n:: ==> updateQuantity() start......]");
		
		productService.updateQuantity(product);
		
		model.addAttribute("search", search);

		System.out.println("[updateQuantity() end......]\n");

		return "forward:/listProduct.do?menu=manage";
	}

}
