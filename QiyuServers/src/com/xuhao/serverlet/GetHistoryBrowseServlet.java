package com.xuhao.serverlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.xuhao.dao.CourseItemDao;
import com.xuhao.model.CourseListBean;

@WebServlet("/GetHistoryBrowseServlet")
public class GetHistoryBrowseServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String historyBrowseIdList = request.getParameter("HBCourseId");
			String[] array = historyBrowseIdList.split(" ");
			List<CourseListBean> list = new ArrayList<>();
			for(int i = 0; i<array.length; i++) {
				int courseId = Integer.valueOf(array[i]);
				CourseListBean bean = new CourseItemDao().getHBCourse(courseId);
				list.add(bean);
			}
			response.setContentType("text/json;charset=utf-8");
			response.getWriter().write(new Gson().toJson(list));	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
