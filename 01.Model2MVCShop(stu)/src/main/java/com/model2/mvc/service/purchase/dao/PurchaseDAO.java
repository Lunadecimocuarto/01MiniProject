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
		
		Connection con = DBUtil.getConnection();

		String sql = "insert into product values (seq_transaction_tran_no.nextval,?,?,?,?,?,?,?,?,?,?)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setProductVO(1, purchaseVO.getPurchaseProd());
		stmt.setUserVO(2, purchaseVO.getBuyer());
		stmt.setString(3,purchaseVO.getPaymentOption());
		stmt.setString(4, purchaseVO.getReceiverName());
		stmt.setString(5, purchaseVO.getReceiverPhone());
		stmt.setString(6, purchaseVO.getDivyAddr());
		stmt.setString(7, purchaseVO.getDivyRequest());
		stmt.setString(8, purchaseVO.getTranCode());
		stmt.setDate(9, purchaseVO.getOrderDate());
		stmt.setString(10, purchaseVO.getDivyDate());
		stmt.executeUpdate();
		con.close();
		
	}
	
	public PurchaseVO findPurchase(int tranNo) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "select * from transaction where Tran_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);

		ResultSet rs = stmt.executeQuery();

		PurchaseVO purchaseVO = null;
		UserVO userVO=null;
		ProductVO productVO=null;
		while (rs.next()) {
			purchaseVO = new PurchaseVO();
			purchaseVO.setTranNo(rs.getInt("tran_no"));
			userVO.setUserId(userVO.getUserId());
			purchaseVO.setBuyer(userVO.("buyer_id"));
			purchaseVO.setPurchaseProd(rs.getString("prod_no"));
			purchaseVO.setDivyAddr(rs.getString("dlvy_addr"));
			purchaseVO.setDivyDate(rs.getString("dlvy_date"));
			purchaseVO.setDivyRequest(rs.getString("dlvy_request"));
			purchaseVO.setOrderDate(rs.getDate("order_date"));
			purchaseVO.setPaymentOption(rs.getString("payment_option"));
			purchaseVO.setReceiverName(rs.getString("receiver_name"));
			purchaseVO.setReceiverPhone(rs.getString("receiver_phone"));
			purchaseVO.setTranCode(rs.getString("tran_status_code"));
			
		}
		
		con.close();

		return purchaseVO;
	}

	public HashMap<String,Object> getPurchaseList(SearchVO searchVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql = "select * from purchase ";
		if (searchVO.getSearchCondition() != null) {
			if (searchVO.getSearchCondition().equals("0")) {
				sql += " where PROD_NO='" + searchVO.getSearchKeyword()
						+ "'";
			} else if (searchVO.getSearchCondition().equals("1")) {
				sql += " where PROD_NAME='" + searchVO.getSearchKeyword()
						+ "'";
			}else if (searchVO.getSearchCondition().equals("2")) {
				sql += " where PRICE='" + searchVO.getSearchKeyword()
				+ "'";
	}
		}
		sql += " order by PROD_NO";

		PreparedStatement stmt = 
			con.prepareStatement(	sql,
														ResultSet.TYPE_SCROLL_INSENSITIVE,
														ResultSet.CONCUR_UPDATABLE);
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
				vo.setFileName(rs.getString("IMAGE_FILE"));
				vo.setManuDate(rs.getString("MANUFACTURE_DAY"));
				vo.setPrice(rs.getInt("PRICE"));
				vo.setProdDetail(rs.getString("PROD_DETAIL"));
				vo.setProdName(rs.getString("PROD_NAME"));
				vo.setProdNo(rs.getInt("PROD_NO"));
				vo.setRegDate(rs.getDate("REG_DATE"));

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
		System.out.println("====DAO updateProduct 시작====");
			
		String sql = "update PRODUCT set PROD_NAME=?,PROD_DETAIL=?,MANUFACTURE_DAY=?,PRICE=? where PROD_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setInt(5, productVO.getProdNo());
		stmt.executeUpdate();
		
		con.close();
		
		System.out.println("====DAO updateProduct 끝====");
	}
	
	public HashMap<String,Object> getSaleList(SearchVO searchVO) throws Exception {
	}
	
	public void updateTranCode(PurchaseVO purchaseVO) throws Exception{
		
	}
	
}