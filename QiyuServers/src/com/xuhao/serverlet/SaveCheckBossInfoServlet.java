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
import com.xuhao.dao.BossInfoDao;
import com.xuhao.model.CheckBossInfoBean;

@WebServlet("/SaveCheckBossInfoServlet")
public class SaveCheckBossInfoServlet extends HttpServlet {
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
			long userId = Long.parseLong(request.getParameter("userId"));
			String bossName = request.getParameter("bossName");
			String bossInfomation = request.getParameter("bossInformation");
			long bossTelePhone = Long.parseLong(request.getParameter("bossTelephone"));
			String bossPosition = request.getParameter("bossPosition");
			String bossReceiptType = request.getParameter("bossReceiptType");
			String bossReceuptId = request.getParameter("bossReceiptId");
			
			//字符串转图片，一定不为空，存入数据库服务器路径
			String bossPhotoOne = request.getParameter("bossPhotoOne");
			
			//生成存放图片文件夹
			String checkPhotoUrl = request.getSession().getServletContext().getRealPath("") + "img/checkbossphoto/";
			File file = new File(checkPhotoUrl + userId + "/");
			file.mkdirs();
			Base64ToImage(bossPhotoOne, checkPhotoUrl + userId + "/main1.jpg");
			String photoOne = "checkbossphoto/" + userId + "/main1.jpg";
			
			//字符串转图片，非空判断，存入数据库服务器路径
			String bossPhotoTwo = request.getParameter("bossPhotoTwo");
			String photoTwo = null;
			Boolean isSuccess2 = Base64ToImage(bossPhotoTwo, checkPhotoUrl + userId + "/main2.jpg");
			if(isSuccess2) {
				photoTwo = "checkbossphoto/" + userId + "/main2.jpg";	
			}
			
			//字符串转图片，非空判断，存入数据库服务器路径
			String bossPhotoThree = request.getParameter("bossPhotoThree");
			String photoThree = null;
			Boolean isSuccess3 = Base64ToImage(bossPhotoThree, checkPhotoUrl + userId + "/main3.jpg");
			if(isSuccess3) {
				photoThree = "checkbossphoto/" + userId + "/main3.jpg";	
			}
			
			//字符串转图片，非空判断，存入数据库服务器路径
			String bossPhotoFour = request.getParameter("bossPhotoFour");
			String photoFour = null;
			Boolean isSuccess4 = Base64ToImage(bossPhotoFour, checkPhotoUrl + userId + "/main4.jpg"); 
			if(isSuccess4) {
				photoFour = "checkbossphoto/" + userId + "/main4.jpg";	
			}
			
			//字符串转图片，非空判断，存入数据库服务器路径
			String bossPhotoFive = request.getParameter("bossPhotoFive");
			String photoFive = null;
			Boolean isSuccess5 = Base64ToImage(bossPhotoFive, checkPhotoUrl + userId + "/main5.jpg"); 
			if(isSuccess5) {
				photoFive = "checkbossphoto/" + userId + "/main5.jpg";	
			}
			
			//字符串转图片，一定不为空，存入数据库服务器路径
			String bossDocument = request.getParameter("bossDocument");
			Base64ToImage(bossDocument, checkPhotoUrl + userId + "/document.jpg");
			String document = "checkbossphoto/" + userId + "/document.jpg";	
			
			CheckBossInfoBean bean = new CheckBossInfoBean();
			bean.setUserId(userId);
			bean.setBossName(bossName);
			bean.setBossInformation(bossInfomation);
			bean.setBossTelePhone(bossTelePhone);
			bean.setBossPosition(bossPosition);
			bean.setBossPhotoOne(photoOne);
			bean.setBossPhotoTwo(photoTwo);
			bean.setBossPhotoThree(photoThree);
			bean.setBossPhotoFour(photoFour);
			bean.setBossPhotoFive(photoFive);
			bean.setBossDocument(document);
			bean.setBossReceiptType(bossReceiptType);
			bean.setBossReceiptId(bossReceuptId);
			Boolean isSave = new BossInfoDao().saveCheckBossInfoBean(bean);
			response.setContentType("text/json;charset=utf-8");
			response.getWriter().write(new Gson().toJson(isSave));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
