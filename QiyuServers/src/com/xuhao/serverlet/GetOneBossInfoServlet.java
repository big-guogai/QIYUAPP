package com.xuhao.serverlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.xuhao.dao.ManagerDao;
import com.xuhao.model.BossInfoBean;
@WebServlet("/GetOneBossInfoServlet")
public class GetOneBossInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String bossId = request.getParameter("bossId");
			BossInfoBean datas = new ManagerDao().getBossInfo(bossId);
			response.setContentType("text/json;charset=utf-8");
			response.getWriter().write(new Gson().toJson(datas));
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

}
