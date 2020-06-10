package com.xuhao.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.xuhao.model.BossCourseInfoBean;
import com.xuhao.model.BossInfoBean;
import com.xuhao.model.CheckBossInfoBean;
import com.xuhao.model.IndentInfoBean;
import com.xuhao.model.UserInfoBean;

public class ManagerDao {
	private String sql;
	private String sqlChangeUserType;
	private String sqlInsertBossInfo;
	private String sqlDeleteCheckInfo;
	/**
	 * 管理员获取审核信息列表
	 * @return 返回List
	 */
	public List<CheckBossInfoBean> getCheckBossInfoBean(){
		sql="SELECT * FROM checkbossinfo";

		List<CheckBossInfoBean> datas = new ArrayList<>();
		ResultSet resultSet = new Dao().queryResultSet(sql, null);
		try {
			while (resultSet.next()) {
				CheckBossInfoBean bean = new CheckBossInfoBean();
				bean.setUserId(resultSet.getLong(1));
				bean.setBossName(resultSet.getString(2));
				bean.setBossDocument(resultSet.getString(3));
				bean.setBossTelePhone(resultSet.getLong(4));
				bean.setBossPhotoOne(resultSet.getString(5));
				bean.setBossPhotoTwo(resultSet.getString(6));
				bean.setBossPhotoThree(resultSet.getString(7));
				bean.setBossPhotoFour(resultSet.getString(8));
				bean.setBossPhotoFive(resultSet.getString(9));
				bean.setBossInformation(resultSet.getString(10));
				bean.setBossPosition(resultSet.getString(11));
				bean.setBossReceiptType(resultSet.getString(12));
				bean.setBossReceiptId(resultSet.getString(13));
				datas.add(bean);
			}
			resultSet.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return datas;
	}
	/**
	 * 驳回申请店铺方法
	 * @param userid 申请店铺用户ID
	 * @return 返回Boolean
	 */
	public Boolean refuseApplicationBoss(String userid , String checkPhotoUrl) {
		sql = "DELETE FROM checkbossinfo WHERE checkbossinfo.userid = ?";
		sqlChangeUserType = "UPDATE userinfo SET usertypeid = 1012 WHERE userid = ?";
		long userId = Long.parseLong(userid);
		Object[] params = {userId};
		Boolean isRefuse = false;
		int affectLine = new Dao().executeNonQuery(sql, params);
		try {
			if (affectLine != 0) {
				int affectLineChangeType = new Dao().executeNonQuery(sqlChangeUserType, params);
				if (affectLineChangeType != 0) {
					File file2 = new File(checkPhotoUrl + userId);
					deletePhoto(file2);
					isRefuse = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isRefuse;
	}
	
	/**
	 * 管理员审核通过店铺申请
	 * @param userid 用户ID
	 * @return 返回Boolean
	 */
	public Boolean passApplicetion(String userid,String checkPhotoUrl,String bossPhotoUrl) {
		sql = "SELECT * FROM checkbossinfo WHERE userid = ?";
		sqlInsertBossInfo = "INSERT INTO bossinfo SET bossinfo.userid = ?,bossinfo.bossname = ?,bossinfo.bossdocuments = ?, bossinfo.bosstelephone = ?,bossinfo.bossphoto_one = ?,bossinfo.bossphoto_two = ?,bossinfo.bossphoto_three = ?,bossinfo.bossphoto_four = ?,bossinfo.bossphoto_five = ?,bossinfo.bossinformation = ?,bossinfo.bossposition = ?, bossinfo.bossreceipttype = ?, bossinfo.bossreceiptid = ?";
		sqlDeleteCheckInfo = "DELETE FROM checkbossinfo WHERE checkbossinfo.userid = ?";
		sqlChangeUserType = "UPDATE userinfo SET usertypeid = 102 WHERE userid = ?";
		long userId = Long.parseLong(userid);
		Boolean isPass = false;
		Object[] parms = {userId};
		//查审核表数据
		ResultSet resultSet = new Dao().queryResultSet(sql, parms);
		File file = new File(bossPhotoUrl + userId + "/");
		file.mkdirs();
		try {
			if (resultSet.next()) {
				String photoMain1 = "bossphoto/" + userId + "/main1.jpg";
				checkImgPass(checkPhotoUrl + userId + "/main1.jpg", bossPhotoUrl + userId + "/main1.jpg");
				String photoMain2 = "";
				String photoMain3 = "";
				String photoMain4 = "";
				String photoMain5 = "";
				String photoDocument = "bossphoto/" + userId + "/document.jpg";
				checkImgPass(checkPhotoUrl + userId + "/document.jpg", bossPhotoUrl + userId + "/document.jpg");
				if (resultSet.getString(6) == null || resultSet.getString(6).equals("")) {
				}else if (resultSet.getString(7) == null || resultSet.getString(7).equals("")) {
					photoMain2 = "bossphoto/" + userId + "/main2.jpg";
					checkImgPass(checkPhotoUrl + userId + "/main2.jpg", bossPhotoUrl + userId + "/main2.jpg");
				}else if (resultSet.getString(8) == null || resultSet.getString(8).equals("")) {
					photoMain2 = "bossphoto/" + userId + "/main2.jpg";
					checkImgPass(checkPhotoUrl + userId + "/main2.jpg", bossPhotoUrl + userId + "/main2.jpg");
					photoMain3 = "bossphoto/" + userId + "/main3.jpg";
					checkImgPass(checkPhotoUrl + userId + "/main3.jpg", bossPhotoUrl + userId + "/main3.jpg");
				}else if (resultSet.getString(9) == null || resultSet.getString(9).equals("")) {
					photoMain2 = "bossphoto/" + userId + "/main2.jpg";
					checkImgPass(checkPhotoUrl + userId + "/main2.jpg", bossPhotoUrl + userId + "/main2.jpg");
					photoMain3 = "bossphoto/" + userId + "/main3.jpg";
					checkImgPass(checkPhotoUrl + userId + "/main3.jpg", bossPhotoUrl + userId + "/main3.jpg");
					photoMain4 = "bossphoto/" + userId + "/main4.jpg";
					checkImgPass(checkPhotoUrl + userId + "/main4.jpg", bossPhotoUrl + userId + "/main4.jpg");
				}else {
					photoMain2 = "bossphoto/" + userId + "/main2.jpg";
					checkImgPass(checkPhotoUrl + userId + "/main2.jpg", bossPhotoUrl + userId + "/main2.jpg");
					photoMain3 = "bossphoto/" + userId + "/main3.jpg";
					checkImgPass(checkPhotoUrl + userId + "/main3.jpg", bossPhotoUrl + userId + "/main3.jpg");
					photoMain4 = "bossphoto/" + userId + "/main4.jpg";
					checkImgPass(checkPhotoUrl + userId + "/main4.jpg", bossPhotoUrl + userId + "/main4.jpg");
					photoMain5 = "bossphoto/" + userId + "/main5.jpg";
					checkImgPass(checkPhotoUrl + userId + "/main5.jpg", bossPhotoUrl + userId + "/main5.jpg");
				}
				Object[] paramsInserBossInfo = {resultSet.getLong(1),resultSet.getString(2),photoDocument,resultSet.getLong(4),photoMain1,photoMain2,photoMain3,photoMain4,photoMain5,resultSet.getString(10),resultSet.getString(11),resultSet.getString(12),resultSet.getString(13)};
				//插入审核通过的店铺信息
				int affectLine = new Dao().executeNonQuery(sqlInsertBossInfo, paramsInserBossInfo);
				if (affectLine != 0) {
					//删除原审核表数据
					int affectLineDeleteCheckInfo = new Dao().executeNonQuery(sqlDeleteCheckInfo, parms);
					if (affectLineDeleteCheckInfo != 0) {
						//更改用户权限标识为店铺
						int affectLineChangeUserType = new Dao().executeNonQuery(sqlChangeUserType, parms);
						if (affectLineChangeUserType != 0) {
							File file2 = new File(checkPhotoUrl + userId);
							deletePhoto(file2);
							isPass = true;
						}
					}
				}
			}
			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isPass;
	}
	
	/**
	 * 图片路径改变
	 * @param getUrl 原路径
	 * @param saveUrl 新路径
	 */
	private void checkImgPass(String getUrl,String saveUrl) {
		try {
			FileInputStream inputStream = new FileInputStream(getUrl);
			OutputStream outputStream = new FileOutputStream(saveUrl);
			int c = inputStream.read();
			while(c != -1) {
				outputStream.write(c);
				c = inputStream.read();
			}
			outputStream.flush();
			inputStream.close();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 申请驳回或申请通过，删除原文件夹
	 * @param file 文件夹路径
	 */
	private void deletePhoto(File file) {
		if (file.exists()) { //判断路劲是否存在
			if (file.isFile()) { //判断路径是否为标准文件
				file.delete();
			}else { //不是文件，对文件夹操作
				File[] listFiles = file.listFiles(); //返回file下所以文件的绝对路径
				for (File file2 : listFiles) {
					deletePhoto(file2);
				}
			}
			file.delete();
		}else {
			System.out.println("文件路径不存在");
		}
	}
	/**
	 * 管理员获取全部用户表信息
	 * @return 用户表
	 */
	public List<UserInfoBean> getAllUserInfo() {
		sql = "SELECT * FROM userinfo";
		List<UserInfoBean> datas = new ArrayList<>();
		ResultSet resultSet = new Dao().queryResultSet(sql, null);
		try {
			while (resultSet.next()) {
				UserInfoBean bean = new UserInfoBean();
				bean.setUserid(resultSet.getLong(1));
				bean.setUserpassword(resultSet.getString(2));
				bean.setUsername(resultSet.getString(3));
				bean.setUsersex(resultSet.getString(4));
				bean.setUserage(resultSet.getInt(5));
				bean.setUserhead(resultSet.getString(6));
				bean.setUsertypeid(resultSet.getInt(7));
				datas.add(bean);
			}
			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return datas;
	}
	/**
	 * 管理员获取全部店铺表信息
	 * @return 店铺表
	 */
	public List<BossInfoBean> getAllBossInfo() {
		sql = "SELECT * FROM bossinfo";
		List<BossInfoBean> datas = new ArrayList<>();
		ResultSet resultSet = new Dao().queryResultSet(sql, null);
		try {
			while (resultSet.next()) {
				BossInfoBean bean = new BossInfoBean();
				bean.setBossId(resultSet.getInt(1));
				bean.setUserId(resultSet.getLong(2));
				bean.setBossName(resultSet.getString(3));
				bean.setBossDocument(resultSet.getString(4));
				bean.setBossTelephone(resultSet.getLong(5));
				bean.setBossPhotoOne(resultSet.getString(6));
				bean.setBossPhotoTwo(resultSet.getString(7));
				bean.setBossPhotoThree(resultSet.getString(8));
				bean.setBossPhotoFour(resultSet.getString(9));
				bean.setBossPhotoFive(resultSet.getString(10));
				bean.setBossInformation(resultSet.getString(11));
				bean.setBossPosition(resultSet.getString(12));
				bean.setBossReceiptType(resultSet.getString(13));
				bean.setBossReceiptId(resultSet.getString(14));
				datas.add(bean);
			}
			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return datas;
	}
	/**
	 * 管理员获取全部课程商品信息
	 * @return 课程商品表
	 */
	public List<BossCourseInfoBean> getAllCourseInfo() {
		sql = "SELECT * FROM bosscourseinfo";
		List<BossCourseInfoBean> datas = new ArrayList<>();
		ResultSet resultSet = new Dao().queryResultSet(sql, null);
		try {
			while (resultSet.next()) {
				BossCourseInfoBean bean = new BossCourseInfoBean();
				bean.setCourseId(resultSet.getInt(1));
				bean.setBossId(resultSet.getInt(2));
				bean.setCourseName(resultSet.getString(3));
				bean.setCoursePrice(resultSet.getDouble(4));
				bean.setCoursePriceType(resultSet.getString(5));
				bean.setCourseInformation(resultSet.getString(6));
				String courseTypeId1 = changeCourseType(resultSet.getInt(7));
				bean.setCourseTypeId1(courseTypeId1);
				String courseTypeId2 = changeCourseType(resultSet.getInt(8));
				bean.setCourseTypeId2(courseTypeId2);
				String courseTypeId3 = changeCourseType(resultSet.getInt(9));
				bean.setCourseTypeId3(courseTypeId3);
				datas.add(bean);
			}
			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return datas;		
	}
	/**
	 * 转换课程类型格式
	 * @param coursetypeid 课程类型ID
	 * @return 课程类型
	 */
	private String changeCourseType(int coursetypeid) {
		sql = "SELECT coursetypeinfo.coursetype FROM coursetypeinfo WHERE coursetypeinfo.coursetypeid = ?";
		String courseType = "";
		Object[] parms = {coursetypeid};
		ResultSet resultSet = new Dao().queryResultSet(sql, parms);
		try {
			while (resultSet.next()) {
				courseType = resultSet.getString(1);
			}
			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return courseType;
	}
	/**
	 * 管理员获取全部支付完成的订单信息
	 * @return 订单表
	 */
	public List<IndentInfoBean> getAllIndentInfo() {
		sql = "SELECT * FROM indentinfo WHERE indenttype = '购买完成' OR indenttype = '待处理'";
		List<IndentInfoBean> datas = new ArrayList<>();
		ResultSet resultSet = new Dao().queryResultSet(sql, null);
		try {
			while (resultSet.next()) {
				IndentInfoBean bean = new IndentInfoBean();
				bean.setIndentid(resultSet.getInt(1));
				bean.setBuyuserid(resultSet.getLong(2));
				bean.setBossid(resultSet.getInt(3));
				bean.setCourseid(resultSet.getInt(4));
				bean.setCourseunitprice(resultSet.getDouble(5));
				bean.setCoursequantity(resultSet.getInt(6));
				bean.setIndentprice(resultSet.getDouble(7));
				bean.setIndenttime(resultSet.getTimestamp(8));
				bean.setIndenttype(resultSet.getString(9));
				datas.add(bean);
			}
			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return datas;
	}
	/**
	 * 管理员通过用户ID获取用户全部信息
	 * @param userid 用户ID
	 * @return 相应账号信息
	 */
	public UserInfoBean getUserInfo(String userid) {
		sql = "SELECT * FROM userinfo WHERE userid = ?";
		long userId = Long.parseLong(userid);
		Object[] parms = {userId};
		UserInfoBean datas = new UserInfoBean();
		ResultSet resultSet = new Dao().queryResultSet(sql, parms);
		try {
			while (resultSet.next()) {
				datas.setUserid(resultSet.getLong(1));
				datas.setUserpassword(resultSet.getString(2));
				datas.setUsername(resultSet.getString(3));
				datas.setUsersex(resultSet.getString(4));
				datas.setUserage(resultSet.getInt(5));
				datas.setUserhead(resultSet.getString(6));
				datas.setUsertypeid(resultSet.getInt(7));
			}	
			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return datas;
	}
	/**
	 * 管理员删除用户信息表
	 * @param userid 用户ID
	 * @return 是否删除成功
	 */
	public Boolean deleteUserInfo(String userid) {
		sql = "DELETE FROM userinfo WHERE userinfo.userid = ?";
		long userId = Long.parseLong(userid);
		System.out.println(userId + "");
		Object[] parms = {userId};
		Boolean isDelete = false;
		int affectLine = new Dao().executeNonQuery(sql, parms);
		try {
			if (affectLine > 0) {
				isDelete = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return isDelete;
	}
	/**
	 * 管理员更改账户信息
	 * @param userid 账户ID	
	 * @param userName 账户昵称
	 * @param userPwd 账户密码
	 * @param userSex 账户性别
	 * @param userage 账户年龄
	 * @return 是否更改成功
	 */
	public Boolean changeUserInfoe(String userid,String userName,String userPwd,String userSex,String userage) {
		sql = "UPDATE userinfo SET username = ?,userpassword = ?,usersex = ?,userage = ? WHERE userid = ?";
		long userId = Long.parseLong(userid);
		int userAge = Integer.parseInt(userage);
		Object[] parms = {userName,userPwd,userSex,userAge,userId};
		Boolean isChange = false;
		int affectLine = new Dao().executeNonQuery(sql, parms);
		try {
			if (affectLine > 0) {
				isChange = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return isChange;
	}
	/**
	 * 管理员通过店铺ID获取全部信息
	 * @param bossid 店铺ID
	 * @return 相应店铺信息
	 */
	public BossInfoBean getBossInfo(String bossid) {
		sql = "SELECT * FROM bossinfo WHERE bossid = ?";
		int bossId = Integer.parseInt(bossid);
		Object[] parms = {bossId};
		BossInfoBean datas = new BossInfoBean();
		ResultSet resultSet = new Dao().queryResultSet(sql, parms);
		try {
			while(resultSet.next()) {
				datas.setBossId(resultSet.getInt(1));
				datas.setUserId(resultSet.getLong(2));
				datas.setBossName(resultSet.getString(3));
				datas.setBossDocument(resultSet.getString(4));
				datas.setBossTelephone(resultSet.getLong(5));
				datas.setBossPhotoOne(resultSet.getString(6));
				datas.setBossPhotoTwo(resultSet.getString(7));
				datas.setBossPhotoThree(resultSet.getString(8));
				datas.setBossPhotoFour(resultSet.getString(9));
				datas.setBossPhotoFive(resultSet.getString(10));
				datas.setBossInformation(resultSet.getString(11));
				datas.setBossPosition(resultSet.getString(12));
				datas.setBossReceiptType(resultSet.getString(13));
				datas.setBossReceiptId(resultSet.getString(14));
			}
			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return datas;
	}
	/**
	 * 管理员用过店铺ID删除相关信息
	 * @param bossid 店铺ID
	 * @return 是否成功删除
	 */
	public Boolean deleteBossInfo(String bossid) {
		String sqlGetUserId = "SELECT userid FROM bossinfo WHERE bossinfo.bossid = ?";
		sql = "DELETE FROM bossinfo WHERE bossinfo.bossid = ?";
		sqlChangeUserType = "UPDATE userinfo SET usertypeid = 101 WHERE userid = ?";
		int bossId = Integer.parseInt(bossid);
		Object[] parmsBossId = {bossId};
		long userid;
		Boolean isDelete = false;
		ResultSet resultSetGetUserId = new Dao().queryResultSet(sqlGetUserId, parmsBossId);
		try {
			while(resultSetGetUserId.next()) {
				userid = resultSetGetUserId.getLong(1);
				Object[] parmsUserId = {userid};
				int affectLineChangeType = new Dao().executeNonQuery(sqlChangeUserType, parmsUserId);
				if (affectLineChangeType != 0) {
					int affectLineDelete = new Dao().executeNonQuery(sql, parmsBossId);
					if (affectLineDelete !=0) {
						isDelete = true;
					}else {
						deleteBossInfo(bossid);
					}
				}else {
					deleteBossInfo(bossid);
				}
			}
			resultSetGetUserId.close();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return isDelete;
	}
	
	/**
	 * 管理员通过店铺ID更改店铺基本信息
	 * @param bean 客户端提交的用户信息表单
	 * @return 是否成功更改
	 */
	public Boolean changeBossInfo(BossInfoBean bean) {
		sql = "UPDATE bossinfo SET bossname = ?, bossinformation = ?, bosstelephone = ?, bossposition = ?, bossphoto_one = ?, bossphoto_two = ?, bossphoto_three = ?,bossphoto_four = ?,bossphoto_five = ?,bossdocuments = ? WHERE bossid = ?";
		Object[] parms = {bean.getBossName(),bean.getBossInformation(),bean.getBossTelephone(),bean.getBossPosition(),bean.getBossPhotoOne(),bean.getBossPhotoTwo(),bean.getBossPhotoThree(),bean.getBossPhotoFour(),bean.getBossPhotoFive(),bean.getBossDocument(),bean.getBossId()};
		Boolean isChange = false;
		int affectLine = new Dao().executeNonQuery(sql, parms);
		try {
			if (affectLine > 0) {
				isChange = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return isChange;
	}
	
	/**
	 * 管理员通过课程ID获取课程全部信息
	 * @param courseid 课程ID
	 * @return 相应课程信息
	 */
	public BossCourseInfoBean getCourseInfo(String courseid) {
		sql = "SELECT * FROM bosscourseinfo WHERE courseid = ?";
		int courseId = Integer.parseInt(courseid);
		Object[] parms = {courseId};
		BossCourseInfoBean datas = new BossCourseInfoBean();
		ResultSet resultSet = new Dao().queryResultSet(sql, parms);
		try {
			while(resultSet.next()) {
				datas.setCourseId(resultSet.getInt(1));
				datas.setBossId(resultSet.getInt(2));
				datas.setCourseName(resultSet.getString(3));
				datas.setCoursePrice(resultSet.getDouble(4));
				datas.setCoursePriceType(resultSet.getString(5));
				datas.setCourseInformation(resultSet.getString(6));
				datas.setCourseTypeId1(changeCourseType(resultSet.getInt(7)));
				datas.setCourseTypeId2(changeCourseType(resultSet.getInt(8)));
				datas.setCourseTypeId3(changeCourseType(resultSet.getInt(9)));
			}
			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return datas;
	}
	/**
	 * 管理员通过课程ID删除相关课程信息
	 * @param courseid 课程ID
	 * @return 是否成功删除
	 */
	public Boolean deleteCourseInfo(String courseid) {
		sql = "DELETE FROM bosscourseinfo WHERE bosscourseinfo.courseid = ?";
		int courseId = Integer.parseInt(courseid);
		Object[] prams = {courseId};
		Boolean isDelete = false;
		int affectLine = new Dao().executeNonQuery(sql, prams);
		try {
			if (affectLine > 0) {
				isDelete = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return isDelete;
	}
	/**
	 * 管理员通过店铺ID更改店铺信息
	 * @param bean 修改后的表单
	 * @return 是否成功更改
	 */
	public Boolean changeCourseInfo(BossCourseInfoBean bean) {
		int courseTypeId1 = getCourseTypeId(bean.getCourseTypeId1());
		int courseTypeId2 = getCourseTypeId(bean.getCourseTypeId2());
		int courseTypeId3 = getCourseTypeId(bean.getCourseTypeId3());
		sql = "UPDATE bosscourseinfo SET coursename = ?, courseinformation = ?, courseprice = ?, coursepricetype = ?, coursetypeid1 = ?, coursetypeid2 = ?, coursetypeid3 = ? WHERE	courseid = ?";
		System.out.println("name:" + bean.getCourseName());
		System.out.println("info:" + bean.getCourseInformation());
		System.out.println("type:" + bean.getCoursePriceType());
		Object[] parms = {bean.getCourseName(),bean.getCourseInformation(),bean.getCoursePrice(),bean.getCoursePriceType(),courseTypeId1,courseTypeId2,courseTypeId3,bean.getCourseId()};
		System.out.println(new Gson().toJson(parms));
		int affectLine = new Dao().executeNonQuery(sql, parms);
		return 0 != affectLine;
	}
	/**
	 * 通过课程类型转换为课程类型代码
	 * @param courseType 课程类型
	 * @return 课程类型代码
	 */
	private int getCourseTypeId(String courseType) {
		int courseTypeId = 0;
		sql = "SELECT coursetypeinfo.coursetypeid FROM coursetypeinfo WHERE coursetypeinfo.coursetype = ?";
		Object[] parms = {courseType};
		ResultSet resultSet = new Dao().queryResultSet(sql, parms);
		try {
			while(resultSet.next()) {
				courseTypeId = resultSet.getInt(1);
			}
			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return courseTypeId;
	}
	/**
	 * 管理员通过订单ID获取订单信息
	 * @param indentid 订单ID
	 * @return 相应订单信息
	 */
	public IndentInfoBean getIndentInfo(String indentid) {
		sql = "SELECT * FROM indentinfo WHERE indentid = ?";
		int indentId = Integer.parseInt(indentid);
		Object[] parms = {indentId};
		IndentInfoBean datas = new IndentInfoBean();
		ResultSet resultSet = new Dao().queryResultSet(sql,parms);
		try {
			while (resultSet.next()) {
				datas.setIndentid(resultSet.getInt(1));
				datas.setBuyuserid(resultSet.getLong(2));
				datas.setBossid(resultSet.getInt(3));
				datas.setCourseid(resultSet.getInt(4));
				datas.setCourseunitprice(resultSet.getDouble(5));
				datas.setCoursequantity(resultSet.getInt(6));
				datas.setIndentprice(resultSet.getDouble(7));
				datas.setIndenttime(resultSet.getTimestamp(8));
				datas.setIndenttype(resultSet.getString(9));
			}
			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return datas;
	}
}
