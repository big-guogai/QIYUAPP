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
@WebServlet("/AddBossCourseInfoServlet")
public class AddBossCourseInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String bossId = request.getParameter("bossId");
			BossCourseInfoBean bean = new BossCourseInfoBean();
			bean.setCourseName(request.getParameter("courseName"));
			Double coursePrice = Double.valueOf(request.getParameter("coursePrice"));
			bean.setCoursePrice(coursePrice);
			bean.setCoursePriceType(request.getParameter("coursePriceType"));
			bean.setCourseInformation(request.getParameter("courseInformation"));
			bean.setCourseTypeId1(request.getParameter("courseTypeId1"));
			bean.setCourseTypeId2(request.getParameter("courseTypeId2"));
			bean.setCourseTypeId3(request.getParameter("courseTypeId3"));
			Boolean isAdd = new BossInfoDao().addBossCourseInfoBean(bossId, bean);
			response.setContentType("text/json;charset=utf-8");
			response.getWriter().write(new Gson().toJson(isAdd));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
