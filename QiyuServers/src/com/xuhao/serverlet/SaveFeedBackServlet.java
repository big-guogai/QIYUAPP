package com.xuhao.serverlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/SaveFeedBackServlet")
public class SaveFeedBackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BufferedReader bufferedReader;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String feedText = request.getParameter("feedText");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String datatime = dateFormat.format(new java.util.Date());
			feedText = "(" + datatime + ")" + feedText;
			String filefolder = request.getSession().getServletContext().getRealPath("") + "feedText";
			Boolean isSave = false;
			File fileFolder = new File(filefolder);
			// 创建存储反馈文本文件目录
			if (!fileFolder.exists()) {
				try {
					System.out.println("目录创建成功？" + fileFolder.mkdirs());
				} catch (Exception e) {
					System.out.println("错误");
					e.printStackTrace();
				}
			} else {
				System.out.println("目录已被创建");
			}
			String fileName = request.getSession().getServletContext().getRealPath("") + "feedText/feedText.txt";
			File file = new File(fileName);
			// 创建存储反馈文本文件
			if (!file.exists()) {
				try {
					System.out.println("文件创建成功？" + file.createNewFile());
				} catch (Exception e) {
					System.out.println("错误");
					e.printStackTrace();
				}
			} else {
				System.out.println("文件已被创建");
			}
			// 获取之前存储的反馈文本
			FileInputStream fileInputStream = new FileInputStream(file);
			bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
			String oldText = null;
			try {
				while ((oldText = bufferedReader.readLine()) != null) {
					if (!oldText.equals("")) {
						feedText += "\n\n" + oldText;
					}
				}
				fileInputStream.close();
				bufferedReader.close();
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			// 字符串拼接存储新的反馈文本
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			byte[] b = new byte[100000000];
			System.out.println("newtext=" + feedText);
			b = feedText.getBytes();
			try {
				fileOutputStream.write(b, 0, b.length);
				fileOutputStream.close();
				isSave = true;
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			response.setContentType("text/json;charset=utf-8");
			response.getWriter().write(new Gson().toJson(isSave));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
