package com.xuhao.serverlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.xuhao.dao.IndentDao;

@WebServlet("/HandleSuccessChangeTypeServlet")
public class HandleSuccessChangeTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String indentId = request.getParameter("indentId");
			Boolean isChange = new IndentDao().changeIndentType(indentId, "购买完成");
			response.setContentType("text/json;charset=utf-8");
			response.getWriter().write(new Gson().toJson(isChange));
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

}
