package com.xuhao.serverlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/GetFeedBackTextServlet")
public class GetFeedBackTextServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String fileName = request.getSession().getServletContext().getRealPath("") + "feedText/feedText.txt";
			String feedText = "";
			File file = new File(fileName);
			if (file.exists()) {
				FileInputStream fileInputStream = new FileInputStream(file);
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
				String oldtext = null;
				try {
					while ((oldtext = bufferedReader.readLine()) != null) {
						if (!oldtext.equals("")) {
							if (!feedText.equals("")) {
								feedText += "\n\n\n" + oldtext;						
							}else {
								feedText += oldtext;
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
				fileInputStream.close();
				bufferedReader.close();
			}
			response.setContentType("text/json;charset=utf-8");
			response.getWriter().write(new Gson().toJson(feedText));
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

}
