package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.vo.UserVO;


public class PurchaseDAO {
	
	public PurchaseDAO(){
	}

	public void insertPurchase(PurchaseVO purchaseVO) throws Exception {
		
		
		System.out.println("purchaseDAO:"+purchaseVO);
		
		Connection con = DBUtil.getConnection();

		String sql = "INSERT INTO TRANSACTION VALUES(seq_transaction_tran_no.nextval,?,?,?,?,?,?,?,?,sysdate,?)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setInt(1, purchaseVO.getPurchaseProd().getProdNo());
		stmt.setString(2, purchaseVO.getBuyer().getUserId());
		stmt.setString(3,purchaseVO.getPaymentOption());
		stmt.setString(4, purchaseVO.getReceiverName());
		stmt.setString(5, purchaseVO.getReceiverPhone());
		stmt.setString(6, purchaseVO.getDivyAddr());
		stmt.setString(7, purchaseVO.getDivyRequest());
		stmt.setString(8, purchaseVO.getTranCode());
		stmt.setString(9, purchaseVO.getDivyDate());
		
		
		stmt.executeUpdate();
		con.close();
		
	}
	
	public PurchaseVO findPurchase(int tranNo) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "select * from transaction where Tran_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);

		ResultSet rs = stmt.executeQuery();

		PurchaseVO purchaseVO = new PurchaseVO();
		UserVO userVO=new UserVO();
		ProductVO productVO=new ProductVO();
		
		System.out.println(tranNo);
		
		while (rs.next()) {
			userVO.setUserId(rs.getString("buyer_id"));
			productVO.setProdNo(Integer.parseInt(rs.getString("prod_no")));
			purchaseVO.setTranNo(rs.getInt("tran_no"));
			purchaseVO.setBuyer(userVO);
			purchaseVO.setPurchaseProd(productVO);
			purchaseVO.setDivyAddr(rs.getString("demailaddr"));
			purchaseVO.setDivyDate(rs.getString("dlvy_date"));
			purchaseVO.setDivyRequest(rs.getString("dlvy_request"));
			purchaseVO.setOrderDate(rs.getDate("order_data"));
			purchaseVO.setPaymentOption(rs.getString("payment_option"));
			purchaseVO.setReceiverName(rs.getString("receiver_name"));
			purchaseVO.setReceiverPhone(rs.getString("receiver_phone"));
			purchaseVO.setTranCode(rs.getString("tran_status_code"));
			
		}
		
		con.close();

		return purchaseVO;
	}

	public HashMap<String,Object> getPurchaseList(SearchVO searchVO, String buyerId) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql = "select * from transaction WHERE buyer_id=? order by PROD_NO";

		PreparedStatement stmt = 
			con.prepareStatement(	sql,
								ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_UPDATABLE);
		stmt.setString(1, buyerId);
		
		ResultSet rs = stmt.executeQuery();

		rs.last();
		int total = rs.getRow();
		System.out.println("로우의 수:" + total);

		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("count", new Integer(total));
													 
		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
		System.out.println("searchVO.getPage():" + searchVO.getPage());
		System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());

		ArrayList<PurchaseVO> list = new ArrayList<PurchaseVO>();
		if (total > 0) {
			for (int i = 0; i < searchVO.getPageUnit(); i++) {
				PurchaseVO vo = new PurchaseVO();
				UserVO user = new UserVO();
				user.setUserId(buyerId);
				
				vo.setTranNo(rs.getInt("tran_no"));
				vo.setBuyer(user);
				vo.setReceiverName(rs.getString("receiver_name"));
				vo.setReceiverPhone(rs.getString("receiver_phone"));
				vo.setTranCode(rs.getString("tran_status_code").trim());

				list.add(vo);
				if (!rs.next())
					break;
			}
		}
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());

		con.close();
			
		return map;
	}

	public void updatePurchase(PurchaseVO purchaseVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		System.out.println("====DAO updatePurchase 시작====");
		
		String sql = "update transaction set paymentOption=?, receiverName=?,receiverPhone=?, receiverAddr=?,receiverRequest=?,receiverDate=? where TRAN_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchaseVO.getPaymentOption());
		stmt.setString(2, purchaseVO.getReceiverName());
		stmt.setString(3, purchaseVO.getReceiverPhone());
		stmt.setString(4, purchaseVO.getDivyAddr());
		stmt.setString(5, purchaseVO.getDivyRequest());
		stmt.setString(6, purchaseVO.getDivyDate());
		stmt.setInt(7, purchaseVO.getTranNo());
		stmt.executeUpdate();
		
		con.close();
		
		System.out.println("====DAO updatePurchase 끝====");
	}
	
	public HashMap<String,Object> getSaleList(SearchVO searchVO) throws Exception {
	}
	
	public void updateTranCode(PurchaseVO purchaseVO) throws Exception{
		
	}
	
}