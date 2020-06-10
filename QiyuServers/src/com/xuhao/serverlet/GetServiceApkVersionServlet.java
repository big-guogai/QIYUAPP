package com.xuhao.serverlet;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/GetServiceApkVersionServlet")
public class GetServiceApkVersionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String serviceVersion = null;
			String apkFloderPath = request.getSession().getServletContext().getRealPath("") + "apk";
			File file = new File(apkFloderPath);
			if (file.exists()) {
				File[] listFile = file.listFiles();
				String filename = listFile[0].getName();
				serviceVersion = filename.substring(5, 10);
			}
			response.setContentType("text/json;charset=utf-8");
			response.getWriter().write(new Gson().toJson(serviceVersion));
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

}
