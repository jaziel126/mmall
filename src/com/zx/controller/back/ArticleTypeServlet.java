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
 *  
 *  处理商品类型列表页面数据
 */
@WebServlet("/articleTypeList.do")
public class ArticleTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ArticleTypeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	@Override
	protected void service(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		ArticleTypeService ats = new ArticleTypeService();
		
		PagerModel pageModel = new PagerModel();
		
		//获取页码
		String pageIndex = request.getParameter("pageIndex");
		if(pageIndex != null && !pageIndex.equals("")) {
			pageModel.setPageIndex(Integer.valueOf(pageIndex));
		}
		request.setAttribute("pageModel", pageModel);
		//获取所有的商品类型
		List<ArticleType> types = ats.getAllTypes(pageModel);
		request.setAttribute("types", types);
		
		 request.setAttribute("highLight", "typeby");

		//跳转至商品类型列表页面
		request.getRequestDispatcher("/WEB-INF/view/back/articleType/list.jsp").forward(request, resp);
		
	}
}
