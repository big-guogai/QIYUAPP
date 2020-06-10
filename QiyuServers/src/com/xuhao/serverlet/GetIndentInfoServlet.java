package com.xuhao.serverlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.GsonBuilder;
import com.xuhao.dao.IndentDao;
import com.xuhao.model.IndentInfoBean;
@WebServlet("/GetIndentInfoServlet")
public class GetIndentInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String indentId = request.getParameter("indentId");
			IndentInfoBean bean = new IndentDao().getIndentInfo(indentId);
			response.setContentType("text/json;charset=utf-8");
			String json = new GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJson(bean);
			response.getWriter().write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
