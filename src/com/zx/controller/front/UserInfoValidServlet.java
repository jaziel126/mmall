package com.zx.controller.front;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.zx.service.UserService;

/**
 * 用户账号校验
 */
@WebServlet("/validName.action")
public class UserInfoValidServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public UserInfoValidServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			UserService us = new UserService();
			//获取登录名
			String loginName = req.getParameter("loginName");
            String result = us.getUserByName(loginName);
            resp.setCharacterEncoding("utf-8");
            PrintWriter pw = resp.getWriter();
            pw.write(result);
		} catch (Exception e) {
             e.printStackTrace();
		}
		
	}
}
