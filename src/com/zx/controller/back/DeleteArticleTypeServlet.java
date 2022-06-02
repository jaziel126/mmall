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
 *  删除商品类型
 */
@WebServlet("/deleteType.do")
public class DeleteArticleTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public DeleteArticleTypeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	@Override
	protected void service(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		 try {
			 ArticleTypeService ats = new ArticleTypeService();
				
			//获取商品类型的code
			 String code = request.getParameter("code");
			 ats.deleteArticle(code);
			 request.setAttribute("message", "删除成功！");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			 request.setAttribute("message", e.getMessage());
		}
		
          //重定向至商品类型列表页面
		 request.getRequestDispatcher("/articleTypeList.do").forward(request, resp);;
	}
}
