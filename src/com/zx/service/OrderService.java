/**
 * 
 */
package com.zx.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.zx.bean.Article;
import com.zx.bean.Order;
import com.zx.bean.OrderItem;
import com.zx.bean.User;
import com.zx.dao.ArticleDao;
import com.zx.dao.OrderDao;
import com.zx.util.pager.PagerModel;


public class OrderService {

	private OrderDao orderDao = new OrderDao();
	/**
	 * @param totalAmount
	 * @param articleInfo
	 * 保存订单
	 */
	public void saveOrder(String totalAmount, String articleInfo,User user) {
		
		 Order order = new Order();
		 
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		 
		 StringBuffer sb = new StringBuffer();
		 sb.append("PO-").append(sdf.format(new Date())).append(user.getId());
		 
		 //设置订单编号  OR-yyyymmddhhmmss-userId
		 order.setOrderCode(sb.toString());
		//设置下单时间
		 order.setCreateDate(new Date());
		 //指定用户信息
		 order.setUserId(user.getId());
		 //指定订单总金额
		 order.setAmount(Double.valueOf(totalAmount));
		 
		 //保存订单并获取订单id
		 int orderId = orderDao.saveOrder(order);
		 
		 //保存订单之后获取订单id ，插入至订单明细中 #2_1#1_1 ==> 2_1#1_1
		 articleInfo = articleInfo.substring(1);
		 //按照 # 进行切分  {2_1,1_1}
		 String[] aInfos = articleInfo.split("#");
		 
		 for(String info : aInfos) {
			 //获取商品id与购买数量
			 String[] id_num = info.split("_");
			 OrderItem item = new OrderItem();
			 item.setArticleId(Integer.valueOf(id_num[0]));
			 item.setOrderNum(Integer.valueOf(id_num[1]));
			 item.setOrderId(orderId);
			 orderDao.saveItem(item);
		 }
		 
	}
	/**
	 * @param id
	 * @return
	 * 根据用户的id获取订单信息
	 */
	public List<Order> getOrdersByUserId(int id) {
		
		List<Order> orders = orderDao.getOrdersByUserId(id);
		for(int i=0;i<orders.size();i++) {
			//根据订单id获取订单详情
			List<OrderItem> items = orderDao.getItemsByorderId(orders.get(i).getId());
		    //将订单详情存放在 订单中
			orders.get(i).setItems(items);
		}
		return orders;
	}
	
	//订单分页查询
	public List<Order> findAllOrder(PagerModel pageModel,String ordeCode) {
		// TODO Auto-generated method stub
		//指定每页显示  10 条数据
		pageModel.setPageSize(10);
		
		//查询订单总记录数
		//查询总记录数
		int totalNum = orderDao.getTotalNum(ordeCode);
		//将总记录数封装至pageModel对象中
		pageModel.setTotalNum(totalNum);
		
		List<Order> orders = orderDao.findAllOrder(pageModel,ordeCode);
		for(int i=0;i<orders.size();i++) {
			//根据订单id获取订单详情
			List<OrderItem> items = orderDao.getItemsByorderId(orders.get(i).getId());
		    //将订单详情存放在 订单中
			orders.get(i).setItems(items);
		}
		return orders;
	}
	
	//确认发货
	public void checkOrder(String id,String status) {
		// TODO Auto-generated method stub
		orderDao.checkOrder(Integer.valueOf(id),status);
	}

	

}
