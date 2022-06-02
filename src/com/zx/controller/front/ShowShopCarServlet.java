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
 * 展示购物车中商品信息
 */
@WebServlet("/showShopCap.do")
public class ShowShopCarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ShowShopCarServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		  
		 ShopCarService shopCarService = new ShopCarService();
		 
		 //从session中获取用户信息
		 User user = (User)req.getSession().getAttribute(ConstantUtil.SESSION_USER);
		 //根据用户id获取该用户购物车中商品信息
		 List<Shopcar> list = shopCarService.findAllShopCarByUserId(user);
		 req.setAttribute("list", list);
		 req.setAttribute("highLight", "carby");
		 req.getRequestDispatcher("/WEB-INF/view/front/shopcar.jsp").forward(req, resp);
	}
}
