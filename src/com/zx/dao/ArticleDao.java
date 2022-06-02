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
import com.zx.util.ConnectionFactory;
import com.zx.util.pager.PagerModel;


public class ArticleDao {

	/**
	 * 获取所有的商品信息
	 * @return
	 */
	public List<Article> getAllArticle(String typeCode,String keyword,PagerModel pagerModel,String flag) {

		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句
			//2、定义sql语句
			String frontSql = "select * from ec_article where type_code like ? and title like ? and disabled = '0' limit ?,? ";
			String backSql = "select * from ec_article where type_code like ? and title like ? limit ?,?";

			
			//3、准备PreparedStatement对象 用于发送sql语句
			pstm = con.prepareStatement(flag.equals("back") ? backSql : frontSql);
			pstm.setString(1, typeCode);
			pstm.setString(2, keyword);
			pstm.setInt(3, pagerModel.getStartSize());
			pstm.setInt(4, pagerModel.getPageSize());
			
			//4、进行查询
			rs = pstm.executeQuery();
			
			//创建Article集合用于封装数据
			List<Article> articles = new ArrayList<>();
			while(rs.next()) {
				//封装商品信息
				Article article = new Article();
				article.setId(rs.getInt("id"));
				article.setTitle(rs.getString("title"));
				article.setPrice(rs.getDouble("price"));
				article.setImage(rs.getString("image"));
				article.setDiscount(rs.getDouble("discount"));
				article.setDisabled(rs.getString("disabled"));
				//将商品的信息存放在集合中
				articles.add(article);
			}
			return articles;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			ConnectionFactory.closeCon(rs, pstm, con);
		}

		return null;
	}

	/**
	 * @param valueOf
	 * @return
	 * 根据商品id获取商品信息
	 */
	public Article getArticleById(Integer id) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句
			//2、定义sql语句
			String sql = "select * from ec_article where id = ? ";
			//3、准备PreparedStatement对象 用于发送sql语句
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, id);
			//4、进行查询
			rs = pstm.executeQuery();
			
			//创建Article集合用于封装数据
			if(rs.next()) {
				//封装商品信息
				Article article = new Article();
				article.setId(rs.getInt("id"));
				article.setTitle(rs.getString("title"));
				article.setPrice(rs.getDouble("price"));
				article.setImage(rs.getString("image"));
				article.setLocality(rs.getString("locality"));
				article.setSupplier(rs.getString("supplier"));
				article.setStorage(rs.getInt("storage"));
				article.setDiscount(rs.getDouble("discount"));
				article.setTypeCode(rs.getString("type_code"));
				article.setDescription(rs.getString("description"));
				article.setDisabled(rs.getString("disabled"));
				return article;	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			ConnectionFactory.closeCon(rs, pstm, con);
		}

		return null;
	}

	//查询总记录数
	public int getTotalNum(String typeCode, String keyword,String flag) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句
			//2、定义sql语句
			String frontSql = "select count(*) from ec_article where type_code like ? and disabled = '0' and title like ? ";
			String backSql = "select count(*) from ec_article where type_code like ? and title like ? ";

			//3、准备PreparedStatement对象 用于发送sql语句
			pstm = con.prepareStatement(flag.equals("back")?backSql : frontSql);
			pstm.setString(1, typeCode);
			pstm.setString(2, keyword);
	
			
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

	public void save(Article article) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句
			//2、定义sql语句
			String sql = "insert into ec_article(title, supplier, price, locality,storage, image, description, type_code, create_date)  values(?, ?,?, ?, ?, ?,?, ?, ?)";
			//3、准备PreparedStatement对象 用于发送sql语句
			pstm = con.prepareStatement(sql);
			pstm.setString(1, article.getTitle());
			pstm.setString(2,article.getSupplier());
			pstm.setDouble(3, article.getPrice());
			pstm.setString(4, article.getLocality());
			pstm.setInt(5, article.getStorage());
			pstm.setString(6, article.getImage());
			pstm.setString(7, article.getDescription());
			pstm.setString(8, article.getTypeCode());
			pstm.setDate(9, new java.sql.Date(article.getCreateDate().getTime()));
			
			//指定sql语句
			pstm.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			ConnectionFactory.closeCon(null, pstm, con);
		}

	}

	//更新商品信息
	public void update(Article article) {
		 
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句
			//2、定义sql语句
            StringBuffer sb = new StringBuffer();
            sb.append(" update ec_article set type_code = ?,title= ?,discount= ?, ");

            sb.append(" locality=?,price=?,storage=?, image = ? ,description=?,supplier=?");
            
            sb.append(" where id = ? ");
			
			//3、准备PreparedStatement对象 用于发送sql语句
			pstm = con.prepareStatement(sb.toString());
			pstm.setString(1, article.getTypeCode());
			pstm.setString(2, article.getTitle());
			pstm.setDouble(3, article.getDiscount());
			
			pstm.setString(4, article.getLocality());
			pstm.setDouble(5, article.getPrice());
			pstm.setInt(6, article.getStorage());
			pstm.setString(7, article.getImage());
			pstm.setString(8, article.getDescription());

			pstm.setString(9,article.getSupplier());
			pstm.setInt(10, article.getId());
			
			//指定sql语句
			pstm.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			ConnectionFactory.closeCon(null, pstm, con);
		}
	}

	//获取上架或下架标记
	public void removeOrPutArticleServlet(Integer id, String disabled) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句

             String sql = " update ec_article  set disabled = ? where id = ? ";
             pstm = con.prepareStatement(sql);
             pstm.setString(1, disabled);
             pstm.setInt(2, id);

			//指定sql语句
			pstm.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			ConnectionFactory.closeCon(null, pstm, con);
		}
		
	}

}
