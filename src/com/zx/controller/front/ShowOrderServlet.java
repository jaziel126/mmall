package com.zx.controller.front;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zx.bean.Article;
import com.zx.bean.ArticleType;
import com.zx.bean.Order;
import com.zx.bean.User;
import com.zx.service.ArticleService;
import com.zx.service.ArticleTypeService;
import com.zx.service.OrderService;
import com.zx.service.UserService;
import com.zx.util.ConstantUtil;

/**
 * 展示订单信息
 */
@WebServlet("/showOrder.do")
public class ShowOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ShowOrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//创建service对象
		OrderService os = new OrderService();
		
		User user = (User)req.getSession().getAttribute(ConstantUtil.SESSION_USER);
		//根据用户的id获取订单信息
		List<Order>  orders = os.getOrdersByUserId(user.getId());
		//将订单信息进行存储 
		req.setAttribute("orders", orders);
		 req.setAttribute("highLight", "ordereby");

		//跳转至展示订单信息的页面
		req.getRequestDispatcher("/WEB-INF/view/front/order.jsp").forward(req, resp);
	}
}
