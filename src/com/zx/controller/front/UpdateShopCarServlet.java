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
import com.zx.bean.Shopcar;
import com.zx.bean.User;
import com.zx.service.ArticleService;
import com.zx.service.ArticleTypeService;
import com.zx.service.ShopCarService;
import com.zx.util.ConstantUtil;

/**
 * 更新购物车中商品的购买数量
 */
@WebServlet("/updateShopCar.do")
public class UpdateShopCarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public UpdateShopCarServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		  
		 ShopCarService shopCarService = new ShopCarService();
		   //获取页面传递的参数
		   //商品id
		  String id = req.getParameter("articleId");
		  //购买数量
		  String number = req.getParameter("buynum");
		  //获取用户信息
		  User user = (User)req.getSession().getAttribute(ConstantUtil.SESSION_USER);
	      
		  //进行更新操作
		 shopCarService.updateShopCar(user.getId(),id,Integer.valueOf(number));
			  
		 
		  
		  //从定向至展示购物车中商品信息页面
		  resp.sendRedirect(req.getContextPath()+"/showShopCap.do");
	}
}
