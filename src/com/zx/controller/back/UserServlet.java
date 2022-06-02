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
import com.zx.bean.User;
import com.zx.service.ArticleService;
import com.zx.service.ArticleTypeService;
import com.zx.service.UserService;
import com.zx.util.pager.PagerModel;

//用户管理
@WebServlet("/userList.do")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	@Override
	protected void service(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		
		 UserService us = new UserService();
		 List<User> users = us.getAllManageUser();
		 request.setAttribute("users", users);
		 request.setAttribute("highLight", "userty");
		 request.getRequestDispatcher("/WEB-INF/view/back/user/list.jsp").forward(request, resp);
	}
}
