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
 *  对商品进行上架与下架处理
 */
@WebServlet("/removeOrPutArticle.do")
public class RemoveOrPutArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public RemoveOrPutArticleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	@Override
	protected void service(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		
		ArticleService as = new ArticleService();
		 //获取商品id
		 String  id = request.getParameter("id");
		 
		 //获取上架或下架标记
		 String disabled = request.getParameter("disabled");
		 as.removeOrPutArticleServlet(id,disabled);
		 
		 //重定向至首页
		 resp.sendRedirect(request.getContextPath()+"/mindex.do");
	    
	}
}
