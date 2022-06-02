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
import com.zx.util.pager.PagerModel;

/**
 * 访问  前台|买家 商品首页
 */
@WebServlet("/index.action")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public IndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//获取商品类型
		String typeCode = req.getParameter("typeCode");
		req.setAttribute("typeCode", typeCode);
		//获取查询关键字
		String keyword = req.getParameter("keyword");
		//将用户输入的关键字存储
		req.setAttribute("keyword", keyword);
		
		//创建分页实体    用于封装分页相关的信息
		PagerModel pageModel = new PagerModel();
		
		//获取页码
		String pageIndex = req.getParameter("pageIndex");
		if(pageIndex != null && !pageIndex.equals("")) {
			pageModel.setPageIndex(Integer.valueOf(pageIndex));
		}
		
		
		//创建服务层对象
		ArticleService as = new ArticleService();
		//创建商品类型服务层对象
		ArticleTypeService articleTypeService = new ArticleTypeService();
		
		//获取商品的一级类型
		List<ArticleType> types = articleTypeService.getAllFType();
		//将一级商品类型的信息存储
		req.setAttribute("types", types);
				
		//获取所有的商品信息
		List<Article> articles = as.getAllArticle(typeCode == null ? types.get(0).getCode() : typeCode,keyword,pageModel,"front");
		
		//将商品信息存放至request对象中
		req.setAttribute("articles", articles);
		req.setAttribute("pageModel", pageModel);
		
		
		if(typeCode != null && !typeCode.equals("")) {
			//获取一级类型的code
			String firstCode = typeCode.substring(0, 4);
			req.setAttribute("firstCode", firstCode);
			//根据一级商品类型获取对应的二级商品类型   0001 == >0001  00010001 == >0001
			List<ArticleType> seTypes = articleTypeService.getSecondTypesByFCode(firstCode);
			req.setAttribute("seTypes", seTypes);
		}
		 req.setAttribute("highLight", "articleby");

		
		//跳转至首页
		req.getRequestDispatcher("/WEB-INF/view/front/artileIndex.jsp").forward(req, resp);
	}
}
