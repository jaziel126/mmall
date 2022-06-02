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
import com.zx.service.UserService;
import com.zx.util.pager.PagerModel;

/**
 *  激活用户信息
 */
@MultipartConfig
@WebServlet("/activeServlet.do")
public class ActiveUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ActiveUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	@Override
	protected void service(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		//获取用户id
		String id = request.getParameter("id");
		//获取激活状态
		String disabled = request.getParameter("disabled");
		
		UserService us = new UserService();
		us.activeUser(id,disabled);
		
		
		//跳转用户列表
		resp.sendRedirect(request.getContextPath()+"/userList.do");
	}
}
