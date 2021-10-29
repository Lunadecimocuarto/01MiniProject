package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;

public class UpdateProductViewAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		int prodNo=Integer.parseInt(request.getParameter("prodNo"));
		System.out.println("=======UpdateProductViewAction Ω√¿€======");
		
		ProductService service=new ProductServiceImpl();
		ProductVO productVO=service.getProduct(prodNo);		
		
		System.out.println(productVO);
		
		request.setAttribute("vo",productVO);
		return "forward:/product/updateProduct.jsp";
	
	}
}