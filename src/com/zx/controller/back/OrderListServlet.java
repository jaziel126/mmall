package com.zx.controller.back;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.zx.bean.Article;
import com.zx.bean.ArticleType;
import com.zx.bean.Order;
import com.zx.service.ArticleService;
import com.zx.service.ArticleTypeService;
import com.zx.service.OrderService;
import com.zx.service.UserService;
import com.zx.util.pager.PagerModel;

/**
 *  后台|卖家
 *  订单列表管理
 */
@WebServlet("/mOrderList.do")
public class OrderListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public OrderListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	@Override
	protected void service(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		
		//获取检索关键字
		String ordeCode = request.getParameter("ordeCode");
		
		//创建分页实体
		PagerModel pageModel = new PagerModel();
		String pageIndex = request.getParameter("pageIndex");
		if(pageIndex != null && !pageIndex.equals("")) {
			pageModel.setPageIndex(Integer.valueOf(pageIndex));
		}
		OrderService os = new OrderService();
		//订单分页查询
		List<Order>  orderList = os.findAllOrder(pageModel,ordeCode);
		request.setAttribute("orderList", orderList);
		request.setAttribute("pageModel", pageModel);
		 request.setAttribute("highLight", "orderty");

		
		//跳转至展示订单信息页面
		request.getRequestDispatcher("/WEB-INF/view/back/order/list.jsp").forward(request, resp);
	}
}
