package com.xuhao.serverlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.xuhao.dao.ManagerDao;
import com.xuhao.model.BossCourseInfoBean;

@WebServlet("/ChangeCourseInfoServlet")
public class ChangeCourseInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int courseId= Integer.parseInt(request.getParameter("courseId"));
			double coursePrice = Double.parseDouble(request.getParameter("coursePrice"));
			BossCourseInfoBean bean = new BossCourseInfoBean();
			bean.setCourseId(courseId);
			bean.setCourseName(request.getParameter("courseName"));
			bean.setCoursePrice(coursePrice);
			bean.setCoursePriceType(request.getParameter("coursePriceType"));
			bean.setCourseInformation(request.getParameter("courseIntro"));
			bean.setCourseTypeId1(request.getParameter("courseTypeId1"));
			bean.setCourseTypeId2(request.getParameter("courseTypeId2"));
			bean.setCourseTypeId3(request.getParameter("courseTypeId3"));
			System.out.println(bean);
			Boolean isChange = new ManagerDao().changeCourseInfo(bean);
			response.setContentType("text/json;charset=utf-8");
			response.getWriter().write(new Gson().toJson(isChange));
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

}
