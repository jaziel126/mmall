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
 *  添加商品信息
 *  当需要进行文件上传的时候，后台Servlet必须加上   @MultipartConfig
 */
@MultipartConfig
@WebServlet("/addArticle.do")
public class AddArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AddArticleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	@Override
	protected void service(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		
		//获取Part对象   文件的信息
		Part part = request.getPart("pic");
        String cd = part.getHeader("Content-Disposition");
		//获取文件的原始名称    1.jpg
//		String fileName = part.getSubmittedFileName();
        String fileName = cd.substring(cd.lastIndexOf("=")+2, cd.length()-1);
		//通过UUID生成新的文件名
		String newFileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
		System.out.println("新的文件名:"+newFileName);
		
		//指定要上传的路径
		String path = request.getServletContext().getRealPath("/resources/image/article");
		System.out.println("path:"+path);
		part.write(path+File.separator+newFileName);
		
		 //获取二级商品类型
        String typeCode = request.getParameter("code");
        //获取商品标题
        String title = request.getParameter("title");
        //获取供应商
        String supplier = request.getParameter("supplier");
        //获取出产地
        String locality = request.getParameter("locality");
        //获取价格
        String price = request.getParameter("price");
        //获取库存
        String storage = request.getParameter("storage");
        //商品描述信息
        String description = request.getParameter("description");
		
        ArticleService as = new ArticleService();
        //保存商品信息
        as.saveArticle(typeCode,title,supplier,locality,price,storage,description,newFileName);

		//跳转至首页
		resp.sendRedirect(request.getContextPath()+"/mindex.do");
	}
}
