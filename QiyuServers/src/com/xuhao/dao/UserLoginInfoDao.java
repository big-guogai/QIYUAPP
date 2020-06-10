package com.xuhao.dao;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.xuhao.model.UserInfoBean;

public class UserLoginInfoDao {
	private String sql;
	//检查数据是否已经存在
	private String sqlcheck;
	/**
	 * 用户登录
	 * @param userid 用户ID
	 * @param password 用户密码
	 * @return 返回用户信息
	 */
	public UserInfoBean getLoginUserInfoBean(String userid,String password){
		sql="SELECT userinfo.username, userinfo.userid, userinfo.userpassword, userinfo.userage, userinfo.userhead, userinfo.usersex, userinfo.usertypeid FROM userinfo WHERE userinfo.userid = ? and userinfo.userpassword = ?";
		long id = Long.parseLong(userid);
		Object[] parms = {id,password};
		ResultSet resultSet = new Dao().queryResultSet(sql, parms);
		UserInfoBean bean = null;
		try {
			if(resultSet.next()) {
				bean = new UserInfoBean();
				bean.setUsername(resultSet.getString(1));
				bean.setUserid(resultSet.getLong(2));
				bean.setUserpassword(resultSet.getString(3));
				bean.setUserage(resultSet.getInt(4));
				bean.setUserhead(resultSet.getString(5));
				bean.setUsersex(resultSet.getString(6));
				bean.setUsertypeid(resultSet.getInt(7));
			}
			resultSet.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
	/**
	 * 用户注册
	 * @param bean 注册申请表单
	 * @return 返回Boolean
	 */
	public Boolean registeredUserBean(UserInfoBean bean) {
		sqlcheck = "SELECT userinfo.username FROM userinfo WHERE userinfo.userid = ?";
		sql="INSERT INTO userinfo SET userinfo.userid = ?, userinfo.userpassword = ?, userinfo.username = ?, userinfo.userage = ?, userinfo.userhead = 'head2_null.png', userinfo.usersex = ?, userinfo.usertypeid = 101";
		long userid = bean.getUserid();
		String userpwd = bean.getUserpassword();
		String username = bean.getUsername();
		int userage = bean.getUserage();
		String usersex = bean.getUsersex();
		Boolean isRegistered = false;
		Object[] parmscheck = {userid};
		ResultSet resultSet = new Dao().queryResultSet(sqlcheck, parmscheck);
		try {
			if(!resultSet.next()) {
				Object[] parms = {userid,userpwd,username,userage,usersex};
				int affectedLine = new Dao().executeNonQuery(sql, parms);
				if(affectedLine != 0) {
					isRegistered = true;
				}
			}
			resultSet.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return isRegistered;
	}
	/**
	 * 获取头像列表
	 * @return 返回图片路径List
	 */
	public List<String> getHeadListUrl(String headListUrl) {
		List<String> datas = new ArrayList<>();
		File file = new File(headListUrl);
		try {
			if (file.exists()) { //判断路径是否存在
				File[] listFile = file.listFiles();
				for(File file2 : listFile) {
					//文件名路径转斜杠
					String url = file2.getPath().replaceAll("\\\\", "/");
					String string = url.substring(url.lastIndexOf("/") + 1);
					datas.add(string);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(datas);
		return datas;
	}
	/**
	 * 上传用户编辑个人信息
	 * @param bean 用户上传的表单
	 * @return 返回Boolean
	 */
	public Boolean updateUserInfo(UserInfoBean bean) {
		Boolean isEdit = false;
		sql = "UPDATE userinfo SET userinfo.username = ?, userinfo.usersex = ?, userinfo.userage = ?, userinfo.userhead = ? WHERE userinfo.userid = ?";
		Object[] parms = {bean.getUsername(),bean.getUsersex(),bean.getUserage(),bean.getUserhead(),bean.getUserid()};
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
}
