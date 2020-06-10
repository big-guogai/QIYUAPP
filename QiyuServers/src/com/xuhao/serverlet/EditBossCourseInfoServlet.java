package com.xuhao.serverlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.xuhao.dao.BossInfoDao;
import com.xuhao.model.BossCourseInfoBean;
@WebServlet("/EditBossCourseInfoServlet")
public class EditBossCourseInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int courseId = Integer.parseInt(request.getParameter("courseId"));
			String courseName = request.getParameter("courseName");
			String courseInformation = request.getParameter("courseInformation");
			Double coursePrice = Double.parseDouble(request.getParameter("coursePrice"));
			String coursePriceType = request.getParameter("coursePriceType");
			String courseTypeId1 = request.getParameter("courseTypeId1");
			String courseTypeId2 = request.getParameter("courseTypeId2");
			String courseTypeId3 = request.getParameter("courseTypeId3");
			BossCourseInfoBean bean = new BossCourseInfoBean();
			bean.setCourseId(courseId);
			bean.setCourseName(courseName);
			bean.setCourseInformation(courseInformation);
			bean.setCoursePrice(coursePrice);
			bean.setCoursePriceType(coursePriceType);
			bean.setCourseTypeId1(courseTypeId1);
			bean.setCourseTypeId2(courseTypeId2);
			bean.setCourseTypeId3(courseTypeId3);
			Boolean isEdit = new BossInfoDao().editBossCourseInfo(bean);
			response.setContentType("text/json;charset=utf-8");
			response.getWriter().write(new Gson().toJson(isEdit));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
