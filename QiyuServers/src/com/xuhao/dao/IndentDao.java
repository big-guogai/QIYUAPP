package com.xuhao.dao;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.xuhao.model.IndentInfoBean;

public class IndentDao {
	private String sql;
	private String sqlBossId;
	/**
	 * 用户通过自己ID获取订单列表
	 * @param userid 用户登录ID
	 * @return 订单信息表单
	 */
	public List<IndentInfoBean> getHistoryIndent(String userid) {
		sql = "SELECT i.indentid,i.buyuserid,i.bossid,b.bossname,i.courseid,c.coursename,i.courseunitprice,c.coursepricetype,i.coursequantity,i.indentprice,i.indenttime,i.indenttype,b.bosstelephone FROM indentinfo AS i,bossinfo AS b,bosscourseinfo AS c WHERE i.buyuserid = ? AND i.bossid = b.bossid AND i.courseid = c.courseid";
		long userId = Long.parseLong(userid);
		Object[] parms = {userId};
		ResultSet resultSet = new Dao().queryResultSet(sql,parms);
		List<IndentInfoBean> datas = new ArrayList<>();
		try {
			while(resultSet.next()) {
				IndentInfoBean bean = new IndentInfoBean();
				bean.setIndentid(resultSet.getInt(1));
				bean.setBuyuserid(resultSet.getLong(2));
				bean.setBossid(resultSet.getInt(3));
				bean.setBossname(resultSet.getString(4));
				bean.setCourseid(resultSet.getInt(5));
				bean.setCoursename(resultSet.getString(6));
				bean.setCourseunitprice(resultSet.getDouble(7));
				bean.setCoursePriceType(resultSet.getString(8));
				bean.setCoursequantity(resultSet.getInt(9));
				bean.setIndentprice(resultSet.getDouble(10));
				bean.setIndenttime(resultSet.getTimestamp(11));
				bean.setIndenttype(resultSet.getString(12));
				bean.setBossTelePhone(resultSet.getLong(13));
				datas.add(bean);
			}
			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datas;
	}
	
	/**
	 * 店铺用户获取订单信息
	 * @param userid 用户ID
	 * @return 订单信息表单
	 */
	public List<IndentInfoBean> getBossIndentInfo(String userid) {
		sql = "SELECT i.indentid,i.buyuserid,i.bossid,b.bossname,i.courseid,c.coursename,i.courseunitprice,c.coursepricetype,i.coursequantity,i.indentprice,i.indenttime,i.indenttype,b.bosstelephone,u.username FROM indentinfo AS i,bossinfo AS b,bosscourseinfo AS c,userinfo AS u WHERE	i.bossid = ? AND i.bossid = b.bossid AND i.courseid = c.courseid AND i.buyuserid = u.userid";
		sqlBossId = "SELECT bossinfo.bossid FROM bossinfo WHERE userid = ?";
		long userId = Long.parseLong(userid);
		int bossId = 0;
		List<IndentInfoBean> datas = new ArrayList<>();
		Object[] parmsBossId = {userId};
		ResultSet resultSet1 = new Dao().queryResultSet(sqlBossId, parmsBossId);
		try {
			while(resultSet1.next()) {
				bossId = resultSet1.getInt(1);
			}
			Object[] params = {bossId};
			ResultSet resultSet = new Dao().queryResultSet(sql, params);
			while(resultSet.next()) {
				IndentInfoBean bean = new IndentInfoBean();
				bean.setIndentid(resultSet.getInt(1));
				bean.setBuyuserid(resultSet.getLong(2));
				bean.setBossid(resultSet.getInt(3));
				bean.setBossname(resultSet.getString(4));
				bean.setCourseid(resultSet.getInt(5));
				bean.setCoursename(resultSet.getString(6));
				bean.setCourseunitprice(resultSet.getDouble(7));
				bean.setCoursePriceType(resultSet.getString(8));
				bean.setCoursequantity(resultSet.getInt(9));
				bean.setIndentprice(resultSet.getDouble(10));
				bean.setIndenttime(resultSet.getTimestamp(11));
				bean.setIndenttype(resultSet.getString(12));
				bean.setBossTelePhone(resultSet.getLong(13));
				bean.setUsername(resultSet.getString(14));
				datas.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datas;
	}
	/**
	 * 点击购买创建新的待支付订单
	 * @param bean 订单信息
	 * @return 布尔型判断是否创建成功
	 */
	public int createIndent(IndentInfoBean bean) {
		int indentId = 0;
		sql = "INSERT INTO indentinfo SET indentinfo.buyuserid = ?,indentinfo.bossid = ?,indentinfo.courseid = ?,indentinfo.courseunitprice = ?,indentinfo.coursequantity = ?,indentinfo.indentprice = ?,indentinfo.indenttime = ?,indentinfo.indenttype = ?";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datatime = dateFormat.format(new java.util.Date());
		Object[] parms = {bean.getBuyuserid(),bean.getBossid(),bean.getCourseid(),bean.getCourseunitprice(),bean.getCoursequantity(),bean.getIndentprice(),datatime,"待支付"};
		indentId = new Dao().executeNonQueryId(sql, parms);
		return indentId;
	}
	
	/**
	 * 获取订单号对应的订单信息
	 * @param indentid 订单号
	 * @return IndentInfoBean
	 */
	public IndentInfoBean getIndentInfo(String indentid) {
		sql = "SELECT i.indentid,i.buyuserid,i.bossid,b.bossname,i.courseid,c.coursename,i.courseunitprice,c.coursepricetype,i.coursequantity,i.indentprice,i.indenttime,i.indenttype,b.bosstelephone,u.username FROM indentinfo AS i,bossinfo AS b,bosscourseinfo AS c,userinfo AS u WHERE	i.indentid = ? AND i.bossid = b.bossid AND i.courseid = c.courseid AND i.buyuserid = u.userid";
		int indentId = Integer.parseInt(indentid);
		Object[] parms = {indentId};
		IndentInfoBean bean = new IndentInfoBean();
		ResultSet resultSet = new Dao().queryResultSet(sql, parms);
		try {
			while (resultSet.next()) {
				bean.setIndentid(resultSet.getInt(1));
				bean.setBuyuserid(resultSet.getLong(2));
				bean.setBossid(resultSet.getInt(3));
				bean.setBossname(resultSet.getString(4));
				bean.setCourseid(resultSet.getInt(5));
				bean.setCoursename(resultSet.getString(6));
				bean.setCourseunitprice(resultSet.getDouble(7));
				bean.setCoursePriceType(resultSet.getString(8));
				bean.setCoursequantity(resultSet.getInt(9));
				bean.setIndentprice(resultSet.getDouble(10));
				bean.setIndenttime(resultSet.getTimestamp(11));
				bean.setIndenttype(resultSet.getString(12));
				bean.setBossTelePhone(resultSet.getLong(13));
				bean.setUsername(resultSet.getString(14));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
	/**
	 * 更改订单状态
	 * @return 更改情况
	 */
	public Boolean changeIndentType(String indentid,String indentType) {
		sql = "UPDATE indentinfo SET indenttype = ? WHERE indentid = ?";
		int indentId = Integer.parseInt(indentid);
		Object[] parms = {indentType,indentId};
		Boolean isChange = false;
		int affectLine = new Dao().executeNonQuery(sql, parms);
		try {
			if (affectLine != 0) {
				isChange = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return isChange;
	}
	/**
	 * 取消未支付订单
	 * @param indentid 订单ID
	 * @return 取消情况
	 */
	public Boolean cancleIndent(String indentid) {
		sql = "DELETE FROM indentinfo WHERE indentinfo.indentid = ? AND indentinfo.indenttype = '待支付'";
		long indentId = Long.parseLong(indentid);
		Object[] parms = {indentId};
		Boolean isCancle = false;
		int affectLine = new Dao().executeNonQuery(sql, parms);
		try {
			if (affectLine != 0) {
				isCancle = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isCancle;
		
	}
	
}
