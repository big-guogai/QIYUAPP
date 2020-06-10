package com.xuhao.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.xuhao.model.CourseInfoBean;
import com.xuhao.model.CourseListBean;

public class CourseItemDao {
	
	private String sql;
	/**
	 * 根据地区获取全部课程	
	 * @param dress	当前地区
	 * @return 全部课程的展示图片与标题小列表
	 */
	public List<CourseListBean> getALLCourseListBean(String dress) {
		sql = "SELECT bosscourseinfo.courseid, bosscourseinfo.coursename, bossinfo.bossphoto_one FROM bossinfo,bosscourseinfo WHERE bossinfo.bossid = bosscourseinfo.bossid AND bossinfo.bossposition LIKE ?";
		String a = "%" + dress +"%"; 
		Object[] parms= {a};
		ResultSet resultSet = new Dao().queryResultSet(sql, parms);
		List<CourseListBean> datas = new ArrayList<>();
		
		try {
			while(resultSet.next()) {
				CourseListBean bean = new CourseListBean(resultSet.getString(3), resultSet.getString(2), resultSet.getInt(1));
				datas.add(bean);
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return datas;
	}
	/**
	 * 获取不同类别课程信息
	 * @param typeid 课程类型
	 * @param dress 地区	
	 * @return 全部课程的展示图片与标题小列表
	 */
	public List<CourseListBean> getKindCourseListBean(int typeid,String dress) {
		sql = "SELECT bosscourseinfo.courseid, bosscourseinfo.coursename, bossinfo.bossphoto_one FROM bossinfo,bosscourseinfo WHERE bossinfo.bossid = bosscourseinfo.bossid AND bosscourseinfo.coursetypeid1 = ? AND bossinfo.bossposition LIKE ? UNION SELECT bosscourseinfo.courseid, bosscourseinfo.coursename, bossinfo.bossphoto_one FROM bossinfo,bosscourseinfo WHERE bossinfo.bossid = bosscourseinfo.bossid AND bosscourseinfo.coursetypeid2 = ? AND bossinfo.bossposition LIKE ? UNION SELECT bosscourseinfo.courseid, bosscourseinfo.coursename, bossinfo.bossphoto_one FROM bossinfo,bosscourseinfo WHERE bossinfo.bossid = bosscourseinfo.bossid AND bosscourseinfo.coursetypeid3 = ? AND bossinfo.bossposition LIKE ?";
		String a = "%" + dress + "%";
		Object[] parms= {typeid,a,typeid,a,typeid,a};
		ResultSet resultSet = new Dao().queryResultSet(sql, parms);
		List<CourseListBean> datas = new ArrayList<>();
		
		try {
			while(resultSet.next()) {
				CourseListBean bean = new CourseListBean(resultSet.getString(3), resultSet.getString(2),resultSet.getInt(1));
				datas.add(bean);
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return datas;
	}
	/**
	 * 获取搜索得出的课程列表	
	 * @param searchkey 搜索关键词
	 * @param dress 地区
	 * @return 全部课程的展示图片与标题小列表
	 */
	public List<CourseListBean> getSearchCourseListBean(String searchkey,String dress) {
		sql = "SELECT bosscourseinfo.courseid, bosscourseinfo.coursename, bossinfo.bossphoto_one FROM bossinfo,bosscourseinfo WHERE bossinfo.bossid = bosscourseinfo.bossid AND bossinfo.bossposition LIKE ? AND bossinfo.bossname LIKE ? UNION SELECT bosscourseinfo.courseid, bosscourseinfo.coursename, bossinfo.bossphoto_one FROM bossinfo,bosscourseinfo WHERE bossinfo.bossid = bosscourseinfo.bossid AND bossinfo.bossposition LIKE ? AND bosscourseinfo.coursename LIKE ?";
		String a = "%" + dress + "%";
		String b = "%" + searchkey + "%";
		Object[] parms = {a,b,a,b};
		ResultSet resultSet = new Dao().queryResultSet(sql, parms);
		List<CourseListBean> datas = new ArrayList<>();
		try {
			while(resultSet.next()) {
				CourseListBean bean = new CourseListBean(resultSet.getString(3), resultSet.getString(2),resultSet.getInt(1));
				datas.add(bean);
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return datas;
	}
	/**
	 * 获取某条课程全部信息
	 * @param courseid 课程ID
	 * @return 课程相关信息
	 */
	public List<CourseInfoBean> getMainInfoCourseListBean(int courseid){
		sql = "SELECT bossinfo.bossphoto_one, bossinfo.bossphoto_two, bossinfo.bossphoto_three, bossinfo.bossphoto_four, bossinfo.bossphoto_five, bossinfo.bossname, bossinfo.bosstelephone, bossinfo.bossinformation, bossinfo.bossposition, bosscourseinfo.coursename, bosscourseinfo.courseprice, bosscourseinfo.coursepricetype, bosscourseinfo.courseinformation, bosscourseinfo.bossid FROM bosscourseinfo,bossinfo WHERE bosscourseinfo.courseid = ? AND bosscourseinfo.bossid = bossinfo.bossid";
		Object[] parms = {courseid};
		ResultSet resultSet = new Dao().queryResultSet(sql, parms);
		List<CourseInfoBean> datas = new ArrayList<>();
		
		try {
			while(resultSet.next()) {
				CourseInfoBean bean = new CourseInfoBean();
				bean.setImgUrl1(resultSet.getString(1));
				bean.setImgUrl2(resultSet.getString(2));
				bean.setImgUrl3(resultSet.getString(3));
				bean.setImgUrl4(resultSet.getString(4));
				bean.setImgUrl5(resultSet.getString(5));
				bean.setBossName(resultSet.getString(6));
				bean.setBossTelePhone(resultSet.getLong(7));
				bean.setBossInfomation(resultSet.getString(8));
				bean.setBossPosition(resultSet.getString(9));
				bean.setCourseName(resultSet.getString(10));
				bean.setCoursePrice(resultSet.getDouble(11));
				bean.setCoursePriceType(resultSet.getString(12));
				bean.setCourseInfomation(resultSet.getString(13));
				bean.setBossId(resultSet.getInt(14));
				datas.add(bean);
			}
			resultSet.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return datas;
	}
	/**
	 * 通过ID获取单条课程信息
	 * @param courseId 课程ID
	 * @return 课程基本信息
	 */
	public CourseListBean getHBCourse(int courseId) {
		sql = "SELECT bosscourseinfo.courseid, bosscourseinfo.coursename, bossinfo.bossphoto_one FROM bossinfo,bosscourseinfo WHERE bossinfo.bossid = bosscourseinfo.bossid AND bosscourseinfo.courseid LIKE ?";
		Object[] parms = {courseId};
		CourseListBean list = new CourseListBean();
		ResultSet resultSet = new Dao().queryResultSet(sql, parms);
		try {
			while(resultSet.next()) {
				list.setCourseId(resultSet.getInt(1));
				list.setTitle(resultSet.getString(2));
				list.setImgUrl(resultSet.getString(3));
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return list;
	}
}
