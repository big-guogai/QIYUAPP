package com.xuhao.serverlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.xuhao.dao.CourseItemDao;
import com.xuhao.model.CourseListBean;
public class CourseSearchItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String searchkey = request.getParameter("searchkey");
			String dress = request.getParameter("dress");
			List<CourseListBean> courseListBean = new CourseItemDao().getSearchCourseListBean(searchkey,dress);
			response.setContentType("text/json;charset=utf-8");
			response.getWriter().write(new Gson().toJson(courseListBean));	
		}catch(Exception e) {
		}
	}

}
