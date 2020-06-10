package com.xuhao.serverlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.xuhao.dao.UserLoginInfoDao;

@WebServlet("/GetHeadUrlListServlet")
public class GetHeadUrlListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String healListUrl = request.getSession().getServletContext().getRealPath("") + "img/head";
			List<String> datas = new UserLoginInfoDao().getHeadListUrl(healListUrl);
			response.setContentType("text/json;charset=utf-8");
			response.getWriter().write(new Gson().toJson(datas));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
