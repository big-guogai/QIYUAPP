package com.xuhao.serverlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.xuhao.dao.ManagerDao;

@WebServlet("/ChangeUserInfoServlet")
public class ChangeUserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String userId = request.getParameter("userId");
			String userName = request.getParameter("userName");
			String userPwd = request.getParameter("userPwd");
			String userSex = request.getParameter("userSex");
			String userAge = request.getParameter("userAge");
			Boolean isChange = new ManagerDao().changeUserInfoe(userId, userName, userPwd, userSex, userAge);
			response.setContentType("text/json;charset=utf-8");
			response.getWriter().write(new Gson().toJson(isChange));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
