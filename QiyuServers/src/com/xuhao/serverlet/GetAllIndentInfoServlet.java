package com.xuhao.serverlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.GsonBuilder;
import com.xuhao.dao.ManagerDao;
import com.xuhao.model.IndentInfoBean;
@WebServlet("/GetAllIndentInfoServlet")
public class GetAllIndentInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<IndentInfoBean> datas = new ManagerDao().getAllIndentInfo();
			response.setContentType("text/json;charset=utf-8");
			String json = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(datas);
			response.getWriter().write(json);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

}
