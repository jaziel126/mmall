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
 *  添加商品类型
 */
@WebServlet("/addOrUpdateArticleType.do")
public class AddorUpdateArticleTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AddorUpdateArticleTypeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		 ArticleTypeService ats = new ArticleTypeService();
		 
		 //商品类型code
		 String code = request.getParameter("code");
		 if(code!=null  && !code.equals("")) {
			//根据商品类型的code获取商品类型
			 ArticleType type = ats.getArticleType(code);
			 request.setAttribute("articleType", type);
		 }
		
		 List<ArticleType> types = ats.getAllFType();
		 request.setAttribute("types", types);

		 request.getRequestDispatcher("/WEB-INF/view/back/articleType/view.jsp").forward(request, resp);
	}




	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	         ArticleTypeService ats = new ArticleTypeService();

			  //获取父级商品类型的code
		    String parentCode = req.getParameter("parentCode");
		    //获取 商品类型名称
		    String name = req.getParameter("name");
		    //获取备注信息
		    String remark = req.getParameter("remark");
		    
		    //获取商品类型的code
		    String code = req.getParameter("code");
		    if(code != null && !code.equals("")) {
		    	/*
		    	 * 进行更新操作
		    	 * 判断用户有没有更换上级类型
		    	 * 
		    	 * */
		    	
		    	//获取 历史 上级类型  和当前选择的上级类型进行比较 
		    	String oldPcode = code.substring(0,code.length() - 4);
		    	if(oldPcode.equals(parentCode)) {
		    		//说明并未更改上级类型
		    		ats.updateType(name,remark,code);
		    	}else {
		    		//需要更换上级类型
		    		ats.updateType2(name,remark,code,parentCode);
		    	}
		    }else {
		    	//进行添加操作
			    ats.addArticleType(parentCode,name,remark);

		    }
		    //重定向至首页
		    resp.sendRedirect(req.getContextPath()+"/articleTypeList.do");
	}

}
