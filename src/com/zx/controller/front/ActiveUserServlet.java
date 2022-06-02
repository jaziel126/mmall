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
import com.zx.service.UserService;

/**
 * 用户信息激活
 */
@WebServlet("/userActive.action")
public class ActiveUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ActiveUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			UserService us = new UserService();
			//获取激活码
			String activeCode = req.getParameter("active");
			us.activeUser(activeCode);
			req.setAttribute("mess", "恭喜您，激活成功！");
		} catch (Exception e) {
			req.setAttribute("mess", "本次激活失败："+e.getMessage());
		}
		
		
		//跳转至登录
		req.getRequestDispatcher("/WEB-INF/view/front/login.jsp").forward(req, resp);
	}
}
