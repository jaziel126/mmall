/**
 * 
 */
package com.zx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Generated;

import com.zx.bean.Article;
import com.zx.bean.Order;
import com.zx.bean.OrderItem;
import com.zx.util.ConnectionFactory;
import com.zx.util.pager.PagerModel;


public class OrderDao {

	/**
	 * @param order
	 * @return
	 * 保存订单
	 */
	public int saveOrder(Order order) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句
			//2、定义sql语句
			String sql = "insert into ec_order(order_code,create_date,amount,user_id) values(?,?,?,?)";
			//3、准备PreparedStatement对象 用于发送sql语句
			pstm = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			pstm.setString(1, order.getOrderCode());
			pstm.setDate(2, new java.sql.Date(order.getCreateDate().getTime()));
			pstm.setDouble(3, order.getAmount());
			pstm.setInt(4, order.getUserId());
			//4、进行数据插入
			int num = pstm.executeUpdate();
			
			if(num == 1) {
				rs = pstm.getGeneratedKeys();
			    rs.next();
			    
			    int orderId = rs.getInt(1);
			    return orderId;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			ConnectionFactory.closeCon(rs, pstm, con);
		}

		return 0;
	}

	/**
	 * @param item
	 * 保存订单详情
	 */
	public void saveItem(OrderItem item) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句
			//2、定义sql语句
			String sql = "insert into ec_order_item(article_id,order_id,order_num) values(?,?,?)";
			//3、准备PreparedStatement对象 用于发送sql语句
			pstm = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			pstm.setInt(1, item.getArticleId());
			pstm.setInt(2, item.getOrderId());
			pstm.setInt(3, item.getOrderNum());
			//4、进行数据插入
			 pstm.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			ConnectionFactory.closeCon(rs, pstm, con);
		}

	}

	/**
	 * @param id
	 * @return
	 * 根据用户的id获取订单信息
	 */
	public List<Order> getOrdersByUserId(int id) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句
			//2、定义sql语句
			String sql = "SELECT * FROM ec_order WHERE user_id = ?";
			//3、准备PreparedStatement对象 用于发送sql语句
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, id);
			//获取结果集
			rs = pstm.executeQuery();
			List<Order> orders = new ArrayList<>();
			
			while(rs.next()) {
				Order order = new Order();
				order.setId(rs.getInt("id"));
				order.setOrderCode(rs.getString("order_code"));
				order.setCreateDate(rs.getTimestamp("create_date"));
				order.setSendDate(rs.getTimestamp("send_date"));
				order.setAmount(rs.getDouble("amount"));
				order.setStatus(rs.getString("status"));
				orders.add(order);
			}
			return orders;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			ConnectionFactory.closeCon(rs, pstm, con);
		}
		return null;
	}

	/**
	 * @param id
	 * @return
	 * 根据订单id获取订单详情
	 */
	public List<OrderItem> getItemsByorderId(int id) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句
			//2、定义sql语句
			String sql = "SELECT * FROM ec_order_item i,ec_article e WHERE order_id = ? AND e.id = i.article_id";
			//3、准备PreparedStatement对象 用于发送sql语句
			pstm = con.prepareStatement(sql);
			
			pstm.setInt(1, id);
			//获取结果集
			rs = pstm.executeQuery();
			
			List<OrderItem> orderItems = new ArrayList<>();
			
			while(rs.next()) {
				OrderItem item = new OrderItem();
				//封装购买的数量
				item.setOrderNum(rs.getInt("order_num"));
				//封装商品相关信息
				Article article = item.getArticle();
				article.setId(rs.getInt("id"));
				article.setImage(rs.getString("image"));
				article.setTitle(rs.getString("title"));
				article.setPrice(rs.getDouble("price"));
				article.setDiscount(rs.getDouble("discount"));
				article.setStorage(rs.getInt("storage"));
				//将商品存放在item中
				item.setArticle(article);
				//item存放在集合中
				orderItems.add(item);
			}
			return orderItems;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			ConnectionFactory.closeCon(rs, pstm, con);
		}
		return null;
	}

	
	public List<Order> findAllOrder(PagerModel pageModel,String ordeCode) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句
			//2、定义sql语句
			String sql = "SELECT * FROM ec_order WHERE order_code LIKE ?  limit ?,?";
			//3、准备PreparedStatement对象 用于发送sql语句
			pstm = con.prepareStatement(sql);
	        pstm.setString(1, ordeCode==null || ordeCode.equals("")?"%%" : "%"+ordeCode+"%");
			pstm.setInt(2, pageModel.getStartSize());
			pstm.setInt(3, pageModel.getPageSize());
			//获取结果集
			rs = pstm.executeQuery();
			List<Order> orders = new ArrayList<>();
			
			while(rs.next()) {
				Order order = new Order();
				order.setId(rs.getInt("id"));
				order.setOrderCode(rs.getString("order_code"));
				order.setCreateDate(rs.getTimestamp("create_date"));
				order.setSendDate(rs.getTimestamp("send_date"));
				order.setAmount(rs.getDouble("amount"));
				order.setStatus(rs.getString("status"));
				orders.add(order);
			}
			return orders;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			ConnectionFactory.closeCon(rs, pstm, con);
		}
		return null;
	}

	//获取订单总记录数
	public int getTotalNum(String ordeCode) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句
			//2、定义sql语句  
			String sql = "select count(*) from ec_order WHERE  order_code LIKE ?";

			//3、准备PreparedStatement对象 用于发送sql语句
			pstm = con.prepareStatement(sql);
	        pstm.setString(1, ordeCode==null || ordeCode.equals("")?"%%" : "%"+ordeCode+"%");
			
			//4、进行查询
			rs = pstm.executeQuery();
	
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			ConnectionFactory.closeCon(rs, pstm, con);
		}

		return 0;
	}

	//确认发货
	public void checkOrder(Integer id,String status) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句
			//2、定义sql语句
			String sql = "update ec_order set send_date = ? , status = ? where id = ?";
		
			pstm = con.prepareStatement(sql);
			pstm.setTimestamp(1,status.equals("0") ? null : new Timestamp(new Date().getTime()));
			pstm.setString(2, status);
			pstm.setInt(3, id);
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			ConnectionFactory.closeCon(rs, pstm, con);
		}
		
	}

}
