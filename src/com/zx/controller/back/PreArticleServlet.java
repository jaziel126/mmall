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
import com.zx.util.pager.PagerModel;

/**
 *  预览商品信息
 */
@WebServlet("/preArticle.do")
public class PreArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public PreArticleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	@Override
	protected void service(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		ArticleService as = new ArticleService();
		
         //获取商品的id
		 String id = request.getParameter("id");
		 //根据商品id获取商品信息
		 Article article = as.getArticleById(id);
         request.setAttribute("article", article);
		//跳转至预览商品信息页面
		request.getRequestDispatcher("/WEB-INF/view/back/article/preArticle.jsp").forward(request, resp);
	}
}
