package com.xuhao.serverlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.xuhao.dao.CourseItemDao;
import com.xuhao.model.CourseInfoBean;
@WebServlet("/CourseMainInfoServlet")
public class CourseMainInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			int a =Integer.parseInt(request.getParameter("courseid"));
			List<CourseInfoBean> courseInfoBeans = new CourseItemDao().getMainInfoCourseListBean(a);
			response.setContentType("text/json;charset=utf-8");
			response.getWriter().write(new Gson().toJson(courseInfoBeans));	
		}catch(Exception e) {
		}
	}
}