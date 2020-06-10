package com.xuhao.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.xuhao.model.BossCourseInfoBean;
import com.xuhao.model.BossInfoBean;
import com.xuhao.model.CheckBossInfoBean;

public class BossInfoDao {
	private String sql;
	//数据插入成功做更新标识
	private String sqlupdate;
	//课程类型获取语句
	private String sqlCourseType;
	
	/**
	 * 保存注册审核的店铺信息
	 * @param bean CheckBossInfoBean表单
	 * @return 返回Boolean
	 */
	public Boolean saveCheckBossInfoBean(CheckBossInfoBean bean){
		sql="INSERT INTO checkbossinfo SET checkbossinfo.userid = ?, checkbossinfo.bossname = ?, checkbossinfo.bossinformation = ?, checkbossinfo.bosstelephone = ?, checkbossinfo.bossposition = ?, checkbossinfo.bossphoto_one = ?, checkbossinfo.bossphoto_two = ?,checkbossinfo.bossphoto_three = ?, checkbossinfo.bossphoto_four = ?, checkbossinfo.bossphoto_five = ?,checkbossinfo.bossdocuments = ?, checkbossinfo.bossreceipttype = ?, checkbossinfo.bossreceiptid = ?";
		sqlupdate = "UPDATE userinfo SET userinfo.usertypeid = 1011 WHERE userinfo.userid = ?";
		long userid = bean.getUserId();
		String bossName = bean.getBossName();
		String bossInformation = bean.getBossInformation();
		long bossTelePhone = bean.getBossTelePhone();
		String bossPosition = bean.getBossPosition();
		String bossPhotoOne = bean.getBossPhotoOne();
		String bossPhotoTwo = bean.getBossPhotoTwo();
		String bossPhotoThree = bean.getBossPhotoThree();
		String bossPhotoFour = bean.getBossPhotoFour();
		String bossPhotoFive = bean.getBossPhotoFive();
		String bossDocument = bean.getBossDocument();
		String bossReceiptType = bean.getBossReceiptType();
		String bossReceiptId = bean.getBossReceiptId();
		Object[] parms = {userid,bossName,bossInformation,bossTelePhone,bossPosition,bossPhotoOne,bossPhotoTwo,bossPhotoThree,bossPhotoFour,bossPhotoFive,bossDocument,bossReceiptType,bossReceiptId};
		Boolean isSave = false;
		int affectedLine = new Dao().executeNonQuery(sql, parms);
		Object[] parmUpdate = {userid};
		if(affectedLine != 0) {
			int updateAffectLine = new Dao().executeNonQuery(sqlupdate, parmUpdate);
			if(updateAffectLine !=0) {
				isSave = true;
			}
		}
		return isSave;
	}
	
	/**
	 * 用户获取自己的店铺信息
	 * @param userid 用户ID
	 * @return	返回BossInfoBean表单
	 */
	public BossInfoBean getBossInfoBean(String userid) {
		sql = "SELECT bossinfo.bossid, bossinfo.bossname, bossinfo.bossinformation, bossinfo.bossposition, bossinfo.bosstelephone, bossinfo.bossreceipttype, bossinfo.bossreceiptid FROM bossinfo WHERE bossinfo.userid = ?";
		long userId = Long.parseLong(userid);
		Object[] parms = {userId};
		ResultSet resultSet = new Dao().queryResultSet(sql, parms);
		BossInfoBean bean = null;
		try {
			if(resultSet.next()) {
				bean = new BossInfoBean();
				bean.setBossId(resultSet.getInt(1));
				bean.setBossName(resultSet.getString(2));
				bean.setBossInformation(resultSet.getString(3));
				bean.setBossPosition(resultSet.getString(4));
				bean.setBossTelephone(resultSet.getLong(5));
				bean.setBossReceiptType(resultSet.getString(6));
				bean.setBossReceiptId(resultSet.getString(7));
			}
			resultSet.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	/**
	 * 店铺获取自己拥有的课程信息
	 * @param bossid 店铺ID
	 * @return 返回List
	 */
	public List<BossCourseInfoBean> getBossCourseInfoBean(String bossid) {
		sql = "SELECT bosscourseinfo.courseid, bosscourseinfo.coursename, bosscourseinfo.courseinformation, bosscourseinfo.courseprice, bosscourseinfo.coursepricetype,bosscourseinfo.coursetypeid1,bosscourseinfo.coursetypeid2,bosscourseinfo.coursetypeid3 FROM bosscourseinfo WHERE bosscourseinfo.bossid = ?";
		sqlCourseType = "SELECT coursetypeinfo.coursetype FROM coursetypeinfo WHERE coursetypeinfo.coursetypeid = ?";
		int bossId = Integer.parseInt(bossid);
		Object[] parms = {bossId};
		ResultSet resultSet = new Dao().queryResultSet(sql, parms);
		List<BossCourseInfoBean> datas = new ArrayList<>();
		try {
			while(resultSet.next()) {
				BossCourseInfoBean bean = new BossCourseInfoBean();
				bean.setCourseId(resultSet.getInt(1));
				bean.setCourseName(resultSet.getString(2));
				bean.setCourseInformation(resultSet.getString(3));
				bean.setCoursePrice(resultSet.getDouble(4));
				bean.setCoursePriceType(resultSet.getString(5));
				Object[] parmsCourseType1 = {resultSet.getInt(6)};
				ResultSet resultSetCouserType1 = new Dao().queryResultSet(sqlCourseType, parmsCourseType1);
				if(resultSetCouserType1.next()) {
					bean.setCourseTypeId1(resultSetCouserType1.getString(1));	
					if(resultSet.getInt(7) != 0) {
						Object[] parmsCourseType2 = {resultSet.getInt(7)};
						ResultSet resultSetCouserType2 = new Dao().queryResultSet(sqlCourseType, parmsCourseType2);
						if(resultSetCouserType2.next()) {
							bean.setCourseTypeId2(resultSetCouserType2.getString(1));
							if(resultSet.getInt(8) != 0) {
								Object[] parmsCourseType3 = {resultSet.getInt(8)};
								ResultSet resultSetCouserType3 = new Dao().queryResultSet(sqlCourseType, parmsCourseType3);
								if(resultSetCouserType3.next()) {
									bean.setCourseTypeId3(resultSetCouserType3.getString(1));
								}
							}
						}
					}
				}
				datas.add(bean);
			}
			resultSet.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return datas;
	}
	/**
	 * 店铺添加课程信息
	 * @param bossid 店铺ID
	 * @param bean 用户上传的表单数据
	 * @return 返回Boolean
	 */
	public Boolean addBossCourseInfoBean(String bossid,BossCourseInfoBean bean) {
		sql="INSERT INTO bosscourseinfo SET bosscourseinfo.bossid = ?, bosscourseinfo.coursename = ?, bosscourseinfo.courseprice = ?, bosscourseinfo.coursepricetype = ?, bosscourseinfo.courseinformation = ?,bosscourseinfo.coursetypeid1 = ?, bosscourseinfo.coursetypeid2 = ?, bosscourseinfo.coursetypeid3 = ?";
		sqlCourseType = "SELECT coursetypeinfo.coursetypeid FROM coursetypeinfo WHERE coursetypeinfo.coursetype = ?";
		long bossId = Long.parseLong(bossid);
		int courseType1 = 0;
		int courseType2 = 0;
		int courseType3 = 0;
		Boolean isAdd = false;
		Object[] parmsCourseType1 = {bean.getCourseTypeId1()};
		ResultSet resultSetCourseType1 = new Dao().queryResultSet(sqlCourseType, parmsCourseType1);
		try {
			if(resultSetCourseType1.next()) {
				courseType1 = resultSetCourseType1.getInt(1);
				resultSetCourseType1.close();
				if(bean.getCourseTypeId2() != "") {
					Object[] parmsCourseType2 = {bean.getCourseTypeId2()};
					ResultSet resultSetCourseType2 = new Dao().queryResultSet(sqlCourseType, parmsCourseType2);
					if(resultSetCourseType2.next()) {
						courseType2 = resultSetCourseType2.getInt(1);
						resultSetCourseType2.close();
						if(bean.getCourseTypeId3() != "") {
							Object[] parmsCourseType3 = {bean.getCourseTypeId3()};
							ResultSet resultSetCourseType3 = new Dao().queryResultSet(sqlCourseType, parmsCourseType3);
							if(resultSetCourseType3.next()) {
								courseType3 = resultSetCourseType3.getInt(1);
								resultSetCourseType3.close();
							}
						}
					}
				}
			}
			Object[] parms = {bossId,bean.getCourseName(),bean.getCoursePrice(),bean.getCoursePriceType(),bean.getCourseInformation(),courseType1,courseType2,courseType3};
			int affectLine = new Dao().executeNonQuery(sql, parms);
			if(affectLine != 0) {
				isAdd = true;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return isAdd;
	}
	
	/**
	 * 编辑店铺信息
	 * @param bean 用户上传编辑后表单
	 * @return 返回是否成功编辑
	 */
	public Boolean editBossInfo(BossInfoBean bean) {
		Boolean isEdit = false;
		sql = "UPDATE bossinfo SET bossname = ?,bossinformation = ?,bossposition = ?,bosstelephone = ?,bossreceipttype = ?,bossreceiptid = ? WHERE bossid = ?";
		int bossId = bean.getBossId();
		String bossName = bean.getBossName();
		String bossInformation = bean.getBossInformation();
		String bossPosition = bean.getBossPosition();
		Long bossTelephone = bean.getBossTelephone();
		String bossReceiptType = bean.getBossReceiptType();
		String bossReceiptId = bean.getBossReceiptId();
		Object[] parms = {bossName,bossInformation,bossPosition,bossTelephone,bossReceiptType,bossReceiptId,bossId};
		int affectLine = new Dao().executeNonQuery(sql, parms);
		try {
			if (affectLine != 0) {
				isEdit = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isEdit;
	}
	
	/**
	 * 编辑店铺课程信息
	 * @param bean 用户上传编辑后表单
	 * @return 返回是否成功编辑
	 */
	public Boolean editBossCourseInfo(BossCourseInfoBean bean) {
		sql = "UPDATE bosscourseinfo SET coursename = ?, courseinformation = ?, courseprice = ?, coursepricetype = ?, coursetypeid1 = ?, coursetypeid2 = ?, coursetypeid3 = ? WHERE courseid = ?";
		sqlCourseType = "SELECT coursetypeinfo.coursetypeid FROM coursetypeinfo WHERE coursetypeinfo.coursetype = ?";
		int courseId = bean.getCourseId();
		int courseType1 = 0;
		int courseType2 = 0;
		int courseType3 = 0;
		Boolean isEdit = false;
		Object[] parmsCourseType1 = {bean.getCourseTypeId1()};
		ResultSet resultSetCourseType1 = new Dao().queryResultSet(sqlCourseType, parmsCourseType1);
		try {
			if(resultSetCourseType1.next()) {
				courseType1 = resultSetCourseType1.getInt(1);
				resultSetCourseType1.close();
				if(bean.getCourseTypeId2() != "") {
					Object[] parmsCourseType2 = {bean.getCourseTypeId2()};
					ResultSet resultSetCourseType2 = new Dao().queryResultSet(sqlCourseType, parmsCourseType2);
					if(resultSetCourseType2.next()) {
						courseType2 = resultSetCourseType2.getInt(1);
						resultSetCourseType2.close();
						if(bean.getCourseTypeId3() != "") {
							Object[] parmsCourseType3 = {bean.getCourseTypeId3()};
							ResultSet resultSetCourseType3 = new Dao().queryResultSet(sqlCourseType, parmsCourseType3);
							if(resultSetCourseType3.next()) {
								courseType3 = resultSetCourseType3.getInt(1);
								resultSetCourseType3.close();
							}
						}
					}
				}
			}
			Object[] parms = {bean.getCourseName(),bean.getCourseInformation(),bean.getCoursePrice(),bean.getCoursePriceType(),courseType1,courseType2,courseType3,courseId};
			int affectLine = new Dao().executeNonQuery(sql, parms);
			if(affectLine != 0) {
				isEdit = true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return isEdit;
	}
}
