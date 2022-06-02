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
import com.zx.service.ArticleService;
import com.zx.service.ArticleTypeService;

/**
 * 展示商品详情信息
 */
@WebServlet("/detail.action")
public class DetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public DetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ArticleService as = new ArticleService();
		//获取商品id
		String id = req.getParameter("id");
		
		//根据商品id获取商品信息
		Article article = as.getArticleById(id);
		//将商品信息存储
		req.setAttribute("article", article);
		//跳转至商品详情页面
		req.getRequestDispatcher("/WEB-INF/view/front/detail.jsp").forward(req, resp);
	}
}
