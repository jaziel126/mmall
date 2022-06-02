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
import com.zx.bean.User;
import com.zx.service.ArticleService;
import com.zx.service.ArticleTypeService;
import com.zx.service.OrderService;
import com.zx.service.UserService;
import com.zx.util.ConstantUtil;

/**
 * 保存订单信息
 */
@WebServlet("/saveOrder.do")
public class SaveOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public SaveOrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//创建service对象
		OrderService os = new OrderService();
		
		User user = (User)req.getSession().getAttribute(ConstantUtil.SESSION_USER);
		//获取订单总金额
		String totalAmount = req.getParameter("totalAmount");
		//获取商品id以及购买数量
		String articleInfo = req.getParameter("articleInfo");
		os.saveOrder(totalAmount,articleInfo,user);
		//重定向至展示订单信息页面
		resp.sendRedirect(req.getContextPath()+"/showOrder.do");
	}
}
