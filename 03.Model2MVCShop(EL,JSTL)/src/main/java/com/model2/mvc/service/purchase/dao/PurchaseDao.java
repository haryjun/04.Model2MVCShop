package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.dao.ProductDao;
import com.model2.mvc.service.user.dao.UserDao;

public class PurchaseDao {

	public PurchaseDao() {
	}

	/// 상품등록
	public void insertPurchase(Purchase purchase) throws Exception {

		Connection con = DBUtil.getConnection();
		
		
		/*
		 * ~.NEXTVAL : 해당 시퀀스의 값을 증가
		 * ~.CURRVAL : 현재 시퀀스를 알고 싶음
		 * 
		 * 사용 가능
		 * 		1) 서브쿼리가 아닌 SELECT문
		 * 		2) INSERT 문의 SELECT절
		 * 		3) INSERT문의 VALUE절
		 * 		4) UPDATE문의 SET절
		 * 
		 * 사용 불가
		 * 		1) VIEW의 SELECT절
		 * 		2) DISTINCT키워드가 있는 SELECT문
		 * 		3) GROUP BY, HAVING, ORDER BY절이 있는 SELECT문
		 * 		4) SELECT, DELETE, UPDATE의 서브쿼리
		 */
		
		/*시퀀스 사용해서 증가시키기
		 * SEQUENCE seq_transaction_tran_no;
		 * 시퀀스 값을 증가 (insert) => NEXTVAL
		 * SYSDATE 사용해서 시간지정(now도 있는데 이건 MYSQL에만 있는듯..)
		 */
		String sql = "INSERT INTO transaction VALUES "
				+ "(seq_transaction_tran_no.NEXTVAL,?,?,?,?,?,?,?,1,SYSDATE,?)";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, purchase.getPurchaseProd().getProdNo());
		stmt.setString(2, purchase.getBuyer().getUserId());
		stmt.setString(3, purchase.getPaymentOption());
		stmt.setString(4, purchase.getReceiverName());
		stmt.setString(5, purchase.getReceiverPhone());
		stmt.setString(6, purchase.getDivyAddr());
		stmt.setString(7, purchase.getDivyRequest());
		stmt.setString(8, purchase.getDivyDate());

		stmt.executeUpdate();

		//System.out.println("거래 등록 완료");
		con.close();
	}

	public Purchase findPurchase(int tranNo) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM transaction WHERE tran_no = ?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);

		ResultSet rs = stmt.executeQuery();

		rs.next();
		Purchase purchase = new Purchase();
		purchase.setTranNo(rs.getInt("tran_no"));
		purchase.setPurchaseProd(new ProductDao().findProduct(rs.getInt("prod_no")));
		purchase.setBuyer(new UserDao().findUser(rs.getString("buyer_id")));
		purchase.setPaymentOption(rs.getString("payment_option").trim());
		purchase.setReceiverName(rs.getString("receiver_name"));
		purchase.setReceiverPhone(rs.getString("receiver_phone"));
		purchase.setDivyAddr(rs.getString("demailaddr"));
		purchase.setDivyRequest(rs.getString("dlvy_request"));
		purchase.setTranCode(rs.getString("tran_status_code").trim());
		purchase.setOrderDate(rs.getDate("order_data"));
		purchase.setDivyDate(rs.getString("dlvy_date"));

		//System.out.println("db에서 가져온 데이터 : " + purchaseVO);

		return purchase;
	}

	public Purchase findPurchase2(int prodNo) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "SELECT tran_no, tran_status_code FROM transaction WHERE prod_no=?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		System.out.println("수정할 상품번호 " + prodNo);
		ResultSet rs = stmt.executeQuery();

		rs.next();
		
		Purchase purchase = new Purchase();
		purchase.setTranNo(rs.getInt("tran_No"));
		purchase.setTranCode(rs.getString("tran_status_code"));
		
		System.out.println("전달받은 "+prodNo+"에대한 tranCode 검색완료");
		
		con.close();

		return purchase;
	}

	public HashMap<String, Object> getSaleList(Search search) throws Exception {

		Connection con = DBUtil.getConnection();

		PreparedStatement stmt = con.prepareStatement("SELECT * FROM transaction", 
										ResultSet.TYPE_SCROLL_INSENSITIVE,
										ResultSet.CONCUR_UPDATABLE);

		ResultSet rs = stmt.executeQuery();

		rs.last();
		int total = rs.getRow();

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("count", new Integer(total));

		//rs.absolute
		rs.absolute(search.getCurrentPage() * search.getPageSize() - search.getPageSize() + 1);
		List<Purchase> list = new ArrayList<Purchase>();

		if (total > 0) {
			for (int i = 0; i < search.getPageSize(); i++) {
				Purchase purchase = new Purchase();

				/*
				 * 내가 알아야하는 건 구매자(buyer)의 아이디인데, buyer만하면 UserVO의 모든 정보가 해당된다
				 * (UerVO)로 casting 하면 안돌아감
				 * UserDAO에 있는 id값 가져오기 // USER_ID
				 */
				purchase.setTranNo(rs.getInt("tran_no"));
				purchase.setBuyer(new UserDao().findUser(rs.getString("user_id"))); //USER_ID, buyer_id??
				purchase.setDivyAddr(rs.getString("demailaddr"));
				purchase.setDivyDate(rs.getString("dlvy_date"));
				purchase.setDivyRequest(rs.getString("dlvy_request"));
				purchase.setOrderDate(rs.getDate("order_date"));
				purchase.setPaymentOption(rs.getString("payment_option"));
				purchase.setPurchaseProd(new ProductDao().findProduct(rs.getInt("prod_no")));
				purchase.setReceiverName(rs.getString("receiver_name"));
				purchase.setReceiverPhone(rs.getString("receiver_phone"));
				purchase.setTranCode(rs.getString("tran_status_code"));

				list.add(purchase);

				if (!rs.next()) {
					break;
				}
					
			}
		}

		map.put("list", list);
		con.close();

		return map;
	}

	/*구매목록 
	 * 
	 * append
	 * COUNT 쓸거니까 OVER() 사용해보기 => GROUP BY 사용하지않아도 됨.
	 * 
	 * 띄워줄 데이터를 서브쿼리만 만들어서 
	*/
	
	public HashMap<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {

		Connection con = DBUtil.getConnection();
		StringBuilder temp = new StringBuilder();
		temp.append("SELECT purchase.* ");
		temp.append("FROM(SELECT ROW_NUMBER() OVER(order by user_id) rn , ");
		temp.append("trans.tran_no, u.user_id, NVL(trans.tran_status_code,0) tran_code, COUNT(*)OVER() count ");
		temp.append("FROM users u, transaction trans ");
		temp.append("WHERE u.user_id = trans.buyer_id AND u.user_id = ? )purchase ");
		temp.append("WHERE rn BETWEEN ? AND ? ");

		PreparedStatement stmt = con.prepareStatement(temp.toString(), 
										ResultSet.TYPE_SCROLL_INSENSITIVE,
										ResultSet.CONCUR_UPDATABLE);

		int searchRowStart = search.getCurrentPage() * search.getPageSize() - search.getPageSize() + 1;
		System.out.println("searchVO.getPageUnit() : " + search.getPageSize());

		int searchRowEnd = searchRowStart + search.getPageSize() - 1;
		System.out.println("searchRowStart : " + searchRowStart);
		System.out.println("searchRowEnd : " + searchRowEnd);

		stmt.setString(1, buyerId);
		stmt.setInt(2, searchRowStart);
		stmt.setInt(3, searchRowEnd);

		ResultSet rs = stmt.executeQuery();

		HashMap<String, Object> map = new HashMap<String, Object>();
		if (rs.next()) {
			System.out.println("있음");
		} else {
			System.out.println("없음");
			map.put("count", new Integer(0));
			return map;
		}

		int total = rs.getInt("count");
		System.out.println("총 갯수 :" + total);

		map.put("count", new Integer(total));
		int max = search.getPageSize();
		List<Purchase> list = new ArrayList<Purchase>();
		if (total > 0) {
			if (total < max) {
				max = total;
			} else {
				total = max;
			}
			rs.first();
			System.out.println("for반복 횟수:" + total);
			for (int i = 0; i < total; i++) {
				System.out.println((i + 1) + "번째");
				Purchase purchase = new Purchase();
				purchase.setTranNo(rs.getInt("tran_no"));
				purchase.setBuyer(new UserDao().findUser(rs.getString("user_id")));
				purchase.setTranCode(rs.getString("tran_code").trim());
				System.out.println(purchase);
				list.add(purchase);
				System.out.println("리스트 추가 " + (i + 1) + "개 입니다");
				if (!rs.next()) {
					break;
				}
			}
		}

		System.out.println("list.size() : " + list.size());
		map.put("list", list);
		System.out.println("map().size() : " + map.size());

		con.close();

		return map;
	}

	public void updatePurchase(Purchase purchase) throws Exception {
		Connection con = DBUtil.getConnection();

		StringBuffer temp = new StringBuffer();
		temp.append("UPDATE transaction SET payment_option=?, ");
		temp.append("receiver_name=?,receiver_phone=?,");
		temp.append("demailAddr=?,dlvy_request=?,");
		temp.append("dlvy_date=? WHERE tran_no=?");
		PreparedStatement stmt = con.prepareStatement(temp.toString());

		System.out.println("update Query = " + temp);
		stmt.setString(1, purchase.getPaymentOption());
		stmt.setString(2, purchase.getReceiverName());
		stmt.setString(3, purchase.getReceiverPhone());
		stmt.setString(4, purchase.getDivyAddr());
		stmt.setString(5, purchase.getDivyRequest());
		stmt.setString(6, purchase.getDivyDate());
		stmt.setInt(7, purchase.getTranNo());

		stmt.executeUpdate();

		System.out.println("DB 수정 완료");

		con.close();
	}

	public void updateTranCode(Purchase purchase) throws Exception {
		Connection con = DBUtil.getConnection();

		String sql = "UPDATE transaction SET tran_status_code=? WHERE tran_no=?";

		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setString(1, purchase.getTranCode());
		stmt.setInt(2, purchase.getTranNo());

		System.out.println("변환한 배송상태 번호 : " + purchase.getTranCode());

		stmt.executeUpdate();

		con.close();

	}

}
