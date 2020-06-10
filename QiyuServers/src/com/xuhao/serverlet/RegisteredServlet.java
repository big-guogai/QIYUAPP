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

@WebServlet("/RegisteredServlet")
public class RegisteredServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			long userid = Long.parseLong(request.getParameter("userId"));
			String userpwd = request.getParameter("userPwd");
			String username = request.getParameter("userName");
			int userage = Integer.parseInt(request.getParameter("userAge"));
			String usersex = request.getParameter("userSex");
			UserInfoBean bean = new UserInfoBean();
			bean.setUserid(userid);
			bean.setUserpassword(userpwd);
			bean.setUsername(username);
			bean.setUserage(userage);
			bean.setUsersex(usersex);
			Boolean isRegistered = new UserLoginInfoDao().registeredUserBean(bean);
			response.setContentType("text/json;charset=utf-8");
			response.getWriter().write(new Gson().toJson(isRegistered));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
