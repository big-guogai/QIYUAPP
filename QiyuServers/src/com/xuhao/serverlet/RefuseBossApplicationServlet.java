package com.xuhao.serverlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.xuhao.dao.ManagerDao;

@WebServlet("/RefuseBossApplicationServlet")
public class RefuseBossApplicationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String userId = request.getParameter("userId");
			String checkPhotoUrl = request.getSession().getServletContext().getRealPath("") + "img/checkbossphoto/";
			Boolean isRefuse = new ManagerDao().refuseApplicationBoss(userId,checkPhotoUrl);	
			response.setContentType("text/json;charset=utf-8");
			response.getWriter().write(new Gson().toJson(isRefuse));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
