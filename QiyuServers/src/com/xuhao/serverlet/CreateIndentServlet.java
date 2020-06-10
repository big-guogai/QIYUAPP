package com.xuhao.serverlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.xuhao.dao.IndentDao;
import com.xuhao.model.IndentInfoBean;
public class CreateIndentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			IndentInfoBean bean = new IndentInfoBean();
			bean.setBuyuserid(Long.parseLong(request.getParameter("userId")));
			bean.setBossid(Integer.parseInt(request.getParameter("bossId")));
			bean.setCourseid(Integer.parseInt(request.getParameter("courseId")));
			bean.setCourseunitprice(Double.parseDouble(request.getParameter("courseUnitPrice")));
			bean.setCoursequantity(Integer.parseInt(request.getParameter("courseQuantity")));
			bean.setIndentprice(Double.parseDouble(request.getParameter("indentPrice")));
			int indentid = new IndentDao().createIndent(bean);
			response.setContentType("text/json;charset=utf-8");
			response.getWriter().write(new Gson().toJson(indentid));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
