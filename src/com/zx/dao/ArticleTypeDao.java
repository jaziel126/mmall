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
import com.zx.bean.ArticleType;
import com.zx.util.ConnectionFactory;
import com.zx.util.pager.PagerModel;


public class ArticleTypeDao {


	/**
	 * @return
	 */
	public List<ArticleType> getAllFType() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句
			//2、定义sql语句
			String sql = "select * from ec_article_type where length(code) = 4 ";
			//3、准备PreparedStatement对象 用于发送sql语句
			pstm = con.prepareStatement(sql);
			//4、进行查询
			rs = pstm.executeQuery();
			
			//创建Article集合用于封装数据
			List<ArticleType> articleTypes = new ArrayList<>();
			while(rs.next()) {
				//封装商品类型信息
				ArticleType articleType = new ArticleType();
				articleType.setCode(rs.getString("code"));
				articleType.setName(rs.getString("name"));
				articleTypes.add(articleType);
			}
			return articleTypes;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			ConnectionFactory.closeCon(rs, pstm, con);
		}

		return null;
	}

	/**
	 * @param string
	 * @return
	 * 根据一级商品类型获取对应的二级商品类型
	 */
	public List<ArticleType> getSecondTypesByFCode(String code) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句
			//2、定义sql语句
			String sql = "select * from ec_article_type where code like ? and length(code) = 8 ";
			//3、准备PreparedStatement对象 用于发送sql语句
			pstm = con.prepareStatement(sql);
			pstm.setString(1, code);
			//4、进行查询
			rs = pstm.executeQuery();
			
			//创建Article集合用于封装数据
			List<ArticleType> articleTypes = new ArrayList<>();
			while(rs.next()) {
				//封装商品类型信息
				ArticleType articleType = new ArticleType();
				articleType.setCode(rs.getString("code"));
				articleType.setName(rs.getString("name"));
				articleTypes.add(articleType);
			}
			return articleTypes;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			ConnectionFactory.closeCon(rs, pstm, con);
		}

		return null;
	}

	//商品类型查询
	public List<ArticleType> getAllTypes(PagerModel pageModel) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句
			//2、定义sql语句     delFlag = '0'代表正常   delFlag = '1'：代表已被逻辑删除
			String sql = "select * from ec_article_type where delFlag = '0'  limit ?,? ";
			//3、准备PreparedStatement对象 用于发送sql语句
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, pageModel.getStartSize());
			pstm.setInt(2, pageModel.getPageSize());
			//4、进行查询
			rs = pstm.executeQuery();
			
			//创建Article集合用于封装数据
			List<ArticleType> articleTypes = new ArrayList<>();
			while(rs.next()) {
				//封装商品类型信息
				ArticleType articleType = new ArticleType();
				articleType.setCode(rs.getString("code"));
				articleType.setName(rs.getString("name"));
				articleType.setRemark(rs.getString("remark"));
				articleTypes.add(articleType);
			}
			return articleTypes;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			ConnectionFactory.closeCon(rs, pstm, con);
		}

		return null;
	}

	//获取商品类型的总记录
	public int getTotalNum() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句
			//2、定义sql语句             33333333333333333333333333   ec_article
			String sql = "select count(*) from ec_article_type where  delFlag = '0' ";
			//获取PreparedStatement对象
			pstm = con.prepareStatement(sql);
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

	//获取最大的一级类型code
	public String findMaxFcode() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句
			//2、定义sql语句       2222222222222222222222222   length(code) = 4
			String sql = "select max(code) from ec_article_type where length(code) = 4";
			//获取PreparedStatement对象
			pstm = con.prepareStatement(sql);
			//4、进行查询
			rs = pstm.executeQuery();
	
			if(rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			ConnectionFactory.closeCon(rs, pstm, con);
		}

		return "";
	}

	public String findMaxScode(String parentCode) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句
			//2、定义sql语句
			String sql = "select max(code) from ec_article_type where code like ? and length(code) = 8";
			//获取PreparedStatement对象
			pstm = con.prepareStatement(sql);
			pstm.setString(1, parentCode+"%");
			//4、进行查询
			rs = pstm.executeQuery();
	
			if(rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			ConnectionFactory.closeCon(rs, pstm, con);
		}

		return "";
	}

	//保存商品类型
	public void saveType(ArticleType type) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句
			//2、定义sql语句
			String sql = "insert into ec_article_type(code,name,remark) values(?,?,?)";
			//3、准备PreparedStatement对象 用于发送sql语句
			pstm = con.prepareStatement(sql);
			pstm.setString(1, type.getCode());
			pstm.setString(2, type.getName());
			pstm.setString(3, type.getRemark());

			//4、进行插入
			pstm.executeUpdate();
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			ConnectionFactory.closeCon(rs, pstm, con);
		}

	}

	public ArticleType getArticleType(String code) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句
			//2、定义sql语句
			String sql = "select *  from ec_article_type where code = ? ";
			//3、准备PreparedStatement对象 用于发送sql语句
			pstm = con.prepareStatement(sql);
			pstm.setString(1, code);

			//4、进行查询
			rs = pstm.executeQuery();
			if(rs.next()) {
                 ArticleType type = new ArticleType();
                 type.setCode(code);
                 type.setName(rs.getString("name"));
                 type.setRemark(rs.getString("remark"));
                 return type;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			ConnectionFactory.closeCon(rs, pstm, con);
		}
		return null;
	}

	//更新商品类型的  name 以及  remark
	public void updateType(String name, String remark, String code) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句
			//2、定义sql语句
			String sql = "update ec_article_type set name = ?,remark = ? where code = ? ";
			//3、准备PreparedStatement对象 用于发送sql语句
			pstm = con.prepareStatement(sql);
			pstm.setString(1, name);
			pstm.setString(2, remark);
			pstm.setString(3, code);


			//4、进行更新
			 pstm.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			ConnectionFactory.closeCon(rs, pstm, con);
		}
	}

	public void updateType2(ArticleType type, String oldCode, String name, String remark) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//设置提交方式为手动提交
			con.setAutoCommit(false);
			//准备需要发送的sql语句
			
			//2、定义sql语句
			String insertSql = "insert into ec_article_type(code,name,remark) values(?,?,?)";
			//3、准备PreparedStatement对象 用于发送sql语句
			pstm = con.prepareStatement(insertSql);
			pstm.setString(1, type.getCode());
			pstm.setString(2, name);
			pstm.setString(3, remark);
			pstm.execute();
			//pstm.addBatch();
			
			//改变商品的所属的商品类型
			String updateSql = "update ec_article set type_code = ? where type_code = ?";
			pstm = con.prepareStatement(updateSql);

			pstm.setString(1, type.getCode());
			pstm.setString(2, oldCode);		
			pstm.execute();
			
			//将之前的商品类型删除      1111111111111111111111111   ec_article
			String deleteSql = "delete from ec_article_type where code = ?";
			pstm = con.prepareStatement(deleteSql);
			System.out.println("oldCode:"+oldCode);
			pstm.setString(1, oldCode);
			//pstm.addBatch();
			pstm.execute();
			
			
			
			

			//pstm.addBatch();
   
			


			//4、执行批处理
			 //pstm.executeBatch();
			 //提交事务
			 con.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			ConnectionFactory.closeCon(rs, pstm, con);
		}
		
	}

	 //判断商品类型下是否存在上架的商品信息
	public boolean findArticleByCode(String code) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句
			//2、定义sql语句   disabled = '0'表示商品处于上架的状态
			String sql = "select * from ec_article where  type_code = ? and disabled = '0' ";
			//3、准备PreparedStatement对象 用于发送sql语句
			pstm = con.prepareStatement(sql);
			pstm.setString(1, code);
			rs = pstm.executeQuery();
            if(rs.next()) {
            	return true;
            }
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			ConnectionFactory.closeCon(rs, pstm, con);
		}
		return false;
	}

	//商品类型进行逻辑删除  发送update语句
	public void deleteType(String code) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句
			//2、定义sql语句   
			String sql = " update  ec_article_type  set delFlag = ? where code = ?";
			//3、准备PreparedStatement对象 用于发送sql语句
			pstm = con.prepareStatement(sql);
			pstm.setString(1, "1");
			pstm.setString(2, code);
			
			//指定sql
			pstm.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			ConnectionFactory.closeCon(rs, pstm, con);
		}
	}

}
