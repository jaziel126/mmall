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
import com.zx.service.ArticleService;
import com.zx.service.ArticleTypeService;
import com.zx.service.OrderService;
import com.zx.service.UserService;
import com.zx.util.pager.PagerModel;

/**
 *  确认发货
 */

@WebServlet("/checkOrder.do")
public class SendOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public SendOrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	@Override
	protected void service(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		OrderService os = new OrderService();
		//获取订单id
		String id = request.getParameter("id");
		String status = request.getParameter("status");
		
		os.checkOrder(id,status);
		//重定向至订单列表
		resp.sendRedirect(request.getContextPath()+"/mOrderList.do");
	}
}
