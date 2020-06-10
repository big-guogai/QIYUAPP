package com.xuhao.serverlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import com.google.gson.Gson;
import com.xuhao.dao.ManagerDao;
import com.xuhao.model.BossInfoBean;

@WebServlet("/ChangeBossInfoServlet")
public class ChangeBossInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 *把字符串解码为图片存入服务器
	 * @param imgStr 图像数据	
	 * @param imgFilePath 存入路径
	 * @return
	 */
	public static boolean Base64ToImage(String imgStr,String imgFilePath) { // 对字节数组字符串进行Base64解码并生成图片
		 
        if (imgStr == null || imgStr.equals("")) // 图像数据为空
            return false;
        try {
            // Base64解码
            byte[] b = DatatypeConverter.parseBase64Binary(imgStr.split(",")[0]);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String bossid = request.getParameter("bossId");
			int bossId = Integer.parseInt(bossid);
			String bossName = request.getParameter("bossName");
			String bossInfo = request.getParameter("bossInfo");
			String bossPositon = request.getParameter("bossPosition");
			long bossTelePhone = Long.parseLong(request.getParameter("bossTelePhone"));
			//字符串转图片，一定不为空，存入数据库服务器路径
			String bossPhotoOne = request.getParameter("bossPhotoOne");
			
			//生成存放图片文件夹
			String checkPhotoUrl = request.getSession().getServletContext().getRealPath("") + "img/bossphoto/";
			File file = new File(checkPhotoUrl + bossId + "/");
			file.mkdirs();
			Base64ToImage(bossPhotoOne, checkPhotoUrl + bossId + "/main1.jpg");
			String photoOne = "bossphoto/" + bossId + "/main1.jpg";
			
			//字符串转图片，非空判断，存入数据库服务器路径
			String bossPhotoTwo = request.getParameter("bossPhotoTwo");
			String photoTwo = null;
			Boolean isSuccess2 = Base64ToImage(bossPhotoTwo, checkPhotoUrl + bossId + "/main2.jpg");
			if(isSuccess2) {
				photoTwo = "bossphoto/" + bossId + "/main2.jpg";	
			}
			
			//字符串转图片，非空判断，存入数据库服务器路径
			String bossPhotoThree = request.getParameter("bossPhotoThree");
			String photoThree = null;
			Boolean isSuccess3 = Base64ToImage(bossPhotoThree, checkPhotoUrl + bossId + "/main3.jpg");
			if(isSuccess3) {
				photoThree = "bossphoto/" + bossId + "/main3.jpg";	
			}
			
			//字符串转图片，非空判断，存入数据库服务器路径
			String bossPhotoFour = request.getParameter("bossPhotoFour");
			String photoFour = null;
			Boolean isSuccess4 = Base64ToImage(bossPhotoFour, checkPhotoUrl + bossId + "/main4.jpg"); 
			if(isSuccess4) {
				photoFour = "bossphoto/" + bossId + "/main4.jpg";	
			}
			
			//字符串转图片，非空判断，存入数据库服务器路径
			String bossPhotoFive = request.getParameter("bossPhotoFive");
			String photoFive = null;
			Boolean isSuccess5 = Base64ToImage(bossPhotoFive, checkPhotoUrl + bossId + "/main5.jpg"); 
			if(isSuccess5) {
				photoFive = "bossphoto/" + bossId + "/main5.jpg";	
			}
			
			//字符串转图片，一定不为空，存入数据库服务器路径
			String bossDocument = request.getParameter("bossDocument");
			Base64ToImage(bossDocument, checkPhotoUrl + bossId + "/document.jpg");
			String document = "bossphoto/" + bossId + "/document.jpg";	
			
			BossInfoBean bean = new BossInfoBean();
			bean.setBossId(bossId);
			bean.setBossName(bossName);
			bean.setBossInformation(bossInfo);
			bean.setBossPosition(bossPositon);
			bean.setBossTelephone(bossTelePhone);
			bean.setBossPhotoOne(photoOne);
			bean.setBossPhotoTwo(photoTwo);
			bean.setBossPhotoThree(photoThree);
			bean.setBossPhotoFour(photoFour);
			bean.setBossPhotoFive(photoFive);
			bean.setBossDocument(document);
			Boolean isChange = new ManagerDao().changeBossInfo(bean);
			response.setContentType("text/json;charset=utf-8");
			response.getWriter().write(new Gson().toJson(isChange));
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

}
