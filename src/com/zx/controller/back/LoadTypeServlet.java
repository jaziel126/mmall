package com.zx.controller.back;

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
import com.zx.util.pager.PagerModel;

/**
 * 异步加载 二级商品类型信息
 */
@WebServlet("/ajaxLoadSeTypes.do")
public class LoadTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public LoadTypeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		   
		   ArticleTypeService  as = new ArticleTypeService();
		   
		  //获取一级商品类型的code
		   String code = req.getParameter("code");
		   //获取二级商品类型信息
		   String jsonStr =  as.getAllSecondTypeByCode(code);
		   System.out.println("jsonStr:"+jsonStr);
		   //将数据写出至前台页面
		   resp.getWriter().write(jsonStr);;
	}
}
