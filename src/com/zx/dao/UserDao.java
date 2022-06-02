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
import com.zx.bean.User;
import com.zx.util.ConnectionFactory;


public class UserDao {

	

	/**
	 * @param loginName
	 * @param passWord
	 * @return
	 */
	public User getUserByNameAndPass(String loginName, String passWord) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句
			//2、定义sql语句
			String sql = "select * from ec_user where login_name = ? and password =?";
			//3、准备PreparedStatement对象 用于发送sql语句
			pstm = con.prepareStatement(sql);
			pstm.setString(1, loginName);
			pstm.setString(2, passWord);
			//4、进行查询
			rs = pstm.executeQuery();
			
			//创建Article集合用于封装数据
			if(rs.next()) {
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setName(rs.getString("name"));
				u.setRole(rs.getInt("role"));
				u.setDisabled(rs.getString("disabled"));
				u.setEmail(rs.getString("email"));
				return u;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			ConnectionFactory.closeCon(rs, pstm, con);
		}

		return null;
	}

	/**
	 * @param user
	 * 保存用户
	 */
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句
			//2、定义sql语句
			String sql = "insert into ec_user(login_name,password,name,sex,email,phone,address,create_date,active,role) values(?,?,?,?,?,?,?,?,?,?)";
			//3、准备PreparedStatement对象 用于发送sql语句
			pstm = con.prepareStatement(sql);
			pstm.setString(1, user.getLoginName());
			pstm.setString(2, user.getPassword());
			pstm.setString(3, user.getName());
			pstm.setInt(4, user.getSex() == '男' ? 1 : 2);
			pstm.setString(5, user.getEmail());
			pstm.setString(6, user.getPhone());
			pstm.setString(7, user.getAddress());
			pstm.setDate(8, new java.sql.Date(user.getCreateDate().getTime()));
			pstm.setString(9, user.getActive());
			pstm.setInt(10, user.getRole());
			
			
			
			pstm.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			ConnectionFactory.closeCon(rs, pstm, con);
		}

	}

	/**
	 * @param activeCode
	 */
	public void activeUser(String activeCode) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句
			//2、定义sql语句
			String sql = "update ec_user set disabled = '1' where active = ?";
			//3、准备PreparedStatement对象 用于发送sql语句
			pstm = con.prepareStatement(sql);
			pstm.setString(1,activeCode);
			
			//执行更新操作
			pstm.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			ConnectionFactory.closeCon(rs, pstm, con);
		}
		
	}

	//根据账号获取用户信息
	public boolean getUserByName(String loginName) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句
			//2、定义sql语句
			String sql = "select * from ec_user where login_name  = ?";
			//3、准备PreparedStatement对象 用于发送sql语句
			pstm = con.prepareStatement(sql);
			pstm.setString(1, loginName);
			//4、进行查询
			rs = pstm.executeQuery();
			
			//创建Article集合用于封装数据
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

	//获取所有的管理员   管理员的角色 role 等于 2
	public List<User> getAllManageUser() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句
			//2、定义sql语句
			String sql = "select * from ec_user where role = 2 ";
			//3、准备PreparedStatement对象 用于发送sql语句
			pstm = con.prepareStatement(sql);
			
			//4、进行查询
			rs = pstm.executeQuery();
			
			List<User> users = new ArrayList<>();
			//创建Article集合用于封装数据
			while(rs.next()) {
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setName(rs.getString("name"));
				u.setSex(rs.getInt("sex"));
				u.setAddress(rs.getString("address"));
				u.setEmail(rs.getString("email"));
				u.setPhone(rs.getString("phone"));
				u.setDisabled(rs.getString("disabled"));
				users.add(u);
			}
			return users;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			ConnectionFactory.closeCon(rs, pstm, con);
		}

		return null;
	}

	
	public void activeUser(String id, String disabled) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			//1、获取连接
			con = ConnectionFactory.getCon();
			//准备需要发送的sql语句
			//2、定义sql语句
			String sql = "update ec_user set disabled = ? where id = ?";
			//3、准备PreparedStatement对象 用于发送sql语句
			pstm = con.prepareStatement(sql);
			pstm.setString(1, disabled);
			pstm.setString(2, id);
			//4、进行激活或者冻结操作
			pstm.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			ConnectionFactory.closeCon(rs, pstm, con);
		}
	}

}
