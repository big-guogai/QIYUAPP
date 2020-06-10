package com.xuhao.serverlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.xuhao.dao.UserLoginInfoDao;
import com.xuhao.model.UserInfoBean;
@WebServlet("/LoginUserInfoServlet")
public class LoginUserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String userid = request.getParameter("userid");
			System.out.println(userid);
			String password = request.getParameter("password");
			System.out.println(password);
			UserInfoBean userInfoBean = new UserLoginInfoDao().getLoginUserInfoBean(userid, password);
			response.setContentType("text/json;charset=utf-8");
			response.getWriter().write(new Gson().toJson(userInfoBean));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
