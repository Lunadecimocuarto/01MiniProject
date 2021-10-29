package com.model2.mvc.view.product;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;


public class UpdateProductAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		
		
		System.out.println("==UpdateProductAction Ω√¿€==");
			
		int prodNo=Integer.parseInt(request.getParameter("prodNo"));
				
		ProductVO productVO=new ProductVO();
		productVO.setProdNo(prodNo);
		productVO.setFileName(request.getParameter("fileName"));
		productVO.setManuDate((request.getParameter("manuDate")).replace("-",""));
		//System.out.println((request.getParameter("manuDate")).replace("-",""));
		productVO.setPrice(Integer.parseInt(request.getParameter("price")));
		productVO.setProdDetail(request.getParameter("prodDetail"));
		productVO.setProdName(request.getParameter("prodName"));
		System.out.println(productVO);
		
		ProductService service=new ProductServiceImpl();
		service.updateProduct(productVO);
		
		HttpSession session=request.getSession(true);
		
	//	int sessionNo=((ProductVO)session.getAttribute("vo")).getProdNo();
	//	int sessionNo=((ProductVO)request.getAttribute("vo")).getProdNo();
		
	//	if(sessionNo==prodNo) {
			session.setAttribute("vo", productVO);
	//		request.setAttribute("vo", productVO);
	//	}
		
		System.out.println("==UpdateProductAction ≥°==");
		
		return "redirect:/product/updateProduct.jsp";
		
		
	}
}