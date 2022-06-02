/**
 * 
 */
package com.zx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.zx.bean.Article;
import com.zx.bean.Shopcar;
import com.zx.util.ConnectionFactory;


public class ShopCarDao {

	

	/**
	 * @param uid
	 * @param aid
	 * @return
	 * 根据用户信息以及商品id查询购物车详情表 判断该商品是否存在于用户的购物车中
	 */
	public Shopcar getShopCarByUserIdAndArticeId(int uid, int aid) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句
			//2、定义sql语句
			String sql = "select * from ec_shopcar where user_id = ? and article_id = ? ";
			//3、准备PreparedStatement对象 用于发送sql语句
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, uid);
			pstm.setInt(2, aid);
			
			//4、进行查询
			rs = pstm.executeQuery();
			
			Shopcar shopcar = null;
			if(rs.next()) {
				shopcar = new Shopcar();
				shopcar.setBuynum(rs.getInt("buynum"));
			}
			return shopcar;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			ConnectionFactory.closeCon(rs, pstm, con);
		}

		return null;
	}

	/**
	 * @param uid
	 * @param valueOf
	 * @param buynum
	 * 更新购物车中商品的购买数量
	 */
	public void updateShopCar(int uid, int aid, int buynum) {

		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句
			//2、定义sql语句
			String sql = "update ec_shopcar set buynum = ? where user_id = ? and article_id = ?";
			//3、准备PreparedStatement对象 用于发送sql语句
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, buynum);
			pstm.setInt(2, uid);
			pstm.setInt(3, aid);

			//进行更新操作
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
	 * @param aid
	 * @param buynum
	 */
	public void addShopCar(int uid, int aid, int buynum) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句
			//2、定义sql语句
			String sql = "insert into ec_shopcar(buynum,user_id,article_id)  values(?,?,?) ";
			//3、准备PreparedStatement对象 用于发送sql语句
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, buynum);
			pstm.setInt(2, uid);
			pstm.setInt(3, aid);

			//进行更新操作
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
	 * 根据用户id获取该用户购物车中所有的商品信息
	 */
	public List<Shopcar> findAllShopCarByUserId(int id) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句
			//2、定义sql语句
			String sql = "SELECT * FROM ec_shopcar s,ec_article a WHERE  s.article_id = a.id  AND s.user_id = ?";
			//3、准备PreparedStatement对象 用于发送sql语句
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, id);

            //指定查询语句
			rs = pstm.executeQuery();
			//创建集合用于封装数据
			List<Shopcar> list = new ArrayList<>();
			
			while(rs.next()) {
				Shopcar shopcar = new Shopcar();
				//封装购买数量
				shopcar.setBuynum(rs.getInt("buynum"));
				Article article = new Article();
				article.setId(rs.getInt("id"));
				article.setImage(rs.getString("image"));
				article.setTitle(rs.getString("title"));
				article.setPrice(rs.getDouble("price"));
				article.setDiscount(rs.getDouble("discount"));
				article.setStorage(rs.getInt("storage"));
				shopcar.setArticle(article);
				
				list.add(shopcar);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			ConnectionFactory.closeCon(rs, pstm, con);
		}	
		return null;
	}

	/**
	 * @param userId
	 * @param articleId
	 * 进行删除操作
	 */
	public void deleteShopCar(int userId, int articleId) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句
			//2、定义sql语句
			String sql = "delete FROM ec_shopcar where user_id = ? and article_id = ?";
			//3、准备PreparedStatement对象 用于发送sql语句
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, userId);
            pstm.setInt(2, articleId);
            //指定删除语句
			pstm.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			ConnectionFactory.closeCon(rs, pstm, con);
		}	
		
	}

	
}
