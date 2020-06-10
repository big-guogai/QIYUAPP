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

@WebServlet("/EditUserInfoServlet")
public class EditUserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			long userId = Long.parseLong(request.getParameter("userId"));
			String userName = request.getParameter("userName");
			String userSex = request.getParameter("userSex");
			int userAge = Integer.parseInt(request.getParameter("userAge"));
			String userHead = request.getParameter("userHead");
			UserInfoBean bean = new UserInfoBean();
			bean.setUserid(userId);
			bean.setUsername(userName);
			bean.setUsersex(userSex);
			bean.setUserage(userAge);
			bean.setUserhead(userHead);
			Boolean isEdit = new UserLoginInfoDao().updateUserInfo(bean);
			response.setContentType("text/json;charset=utf-8");
			response.getWriter().write(new Gson().toJson(isEdit));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
