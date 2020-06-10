package com.xuhao.serverlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.xuhao.dao.BossInfoDao;
import com.xuhao.model.BossInfoBean;

@WebServlet("/EditBossInfoServlet")
public class EditBossInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int bossId = Integer.parseInt(request.getParameter("bossId"));
			String bossName = request.getParameter("bossName");
			String bossInformation = request.getParameter("bossInformation");
			String bossPosition = request.getParameter("bossPosition");
			long bossTelephone = Long.parseLong(request.getParameter("bossTelephone"));
			String bossReceiptType = request.getParameter("bossReceiptType");
			String bossReceiptId = request.getParameter("bossReceiptId");
			BossInfoBean bean = new BossInfoBean();
			bean.setBossId(bossId);
			bean.setBossName(bossName);
			bean.setBossInformation(bossInformation);
			bean.setBossPosition(bossPosition);
			bean.setBossTelephone(bossTelephone);
			bean.setBossReceiptType(bossReceiptType);
			bean.setBossReceiptId(bossReceiptId);
			Boolean isEdit = new BossInfoDao().editBossInfo(bean);
			response.setContentType("text/json;charset=utf-8");
			response.getWriter().write(new Gson().toJson(isEdit));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
