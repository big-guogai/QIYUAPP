package com.xuhao.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dao {

	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/qiyudatabase?useUnicode=true&characterEncoding=utf8";
	private static final String USER = "root";
	private static final String PWD = "123456";
	
//	private static final String DRIVER = "com.mysql.jdbc.Driver";
//	private static final String URL = "jdbc:mysql://localhost:3306/qiyudatabase?useUnicode=true&characterEncoding=utf8";
//	private static final String USER = "root";
//	private static final String PWD = "mysql";

	private Connection conn;
	private PreparedStatement pst;
	private PreparedStatement pst1;
	private ResultSet rst = null;
	private CallableStatement callableStatement = null;// 创建CallableStatement对象

	/**
	 * 获取一个数据库连接
	 * 
	 * @return
	 */
	public Connection getCon() {
		Connection conn = null;
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USER, PWD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * insert update delete SQL语句的执行的统一方法
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数数组，若没有参数则为null
	 * @return 受影响的行数
	 */
	public int executeNonQuery(String sql, Object[] params) {
		// 受影响的行数
		int affectedLine = 0;
		try {
			// 获得连接
			conn = getCon();
			// 调用SQL
			pst = conn.prepareStatement(sql);
			// 参数赋值
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					pst.setObject(i + 1, params[i]);
				}
			}
			affectedLine = pst.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			// 释放资源
			closeAll();
		}
		return affectedLine;
	}
	
	/**
	 * insert 数据返回自增ID
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数数组，若没有参数则为null
	 * @return 自增ID
	 */
	public int executeNonQueryId(String sql, Object[] params) {
		//自增Id
		int addId = 0;
		// 受影响的行数
		int affectedLine = 0;
		try {
			// 获得连接
			conn = getCon();
			// 调用SQL
			pst = conn.prepareStatement(sql);
			// 参数赋值
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					pst.setObject(i + 1, params[i]);
				}
			}
			affectedLine = pst.executeUpdate();
			if (affectedLine != 0) {
				pst1 = conn.prepareStatement("SELECT LAST_INSERT_ID()");
			}
			rst = pst1.executeQuery();
			if (rst.next()) {
				addId = rst.getInt(1);		
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			// 释放资源
			closeAll();
		}
		return addId;
	}


	/**
	 * SQL 查询将查询结果直接放入ResultSet中
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数数组，若没有参数则为null
	 * @return 结果集
	 */
	public ResultSet queryResultSet(String sql, Object[] params) {
		try {
			// 获得连接
			conn = getCon();
			// 调用SQL
			pst = conn.prepareStatement(sql);
			// 参数赋值
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					pst.setObject(i + 1, params[i]);
				}
			}
			// 执行
			rst = pst.executeQuery();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return rst;
	}

	/**
	 * SQL 查询将查询结果：首行首列。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数数组，若没有参数则为null
	 * @return 结果集
	 */
	public Object executeScalar(String sql, Object[] params) {
		Object object = null;
		try {
			// 获得连接
			conn = getCon();
			// 调用SQL
			pst = conn.prepareStatement(sql);
			// 参数赋值
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					pst.setObject(i + 1, params[i]);
				}
			}
			// 执行
			rst = pst.executeQuery();
			if (rst.next()) {
				object = rst.getObject(1);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			closeAll();
		}
		return object;
	}

	/**
	 * SQL 查询数据库是否存在该条数据。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数数组，若没有参数则为null
	 * @return {@code true} 读取到数据。 {@code false} 未发现数据。
	 */
	public boolean queryRead(String sql, Object[] params) {
		ResultSet resultSet = queryResultSet(sql, params);
		try {
			if (resultSet.next()) {
				return true;
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取结果集，并将结果放在List中
	 * 
	 * @param sql
	 *            SQL语句 params 参数，没有则为null
	 * @return List 结果集
	 */
	public List<Object> excuteQuery(String sql, Object[] params) {
		// 执行SQL获得结果集
		ResultSet rs = queryResultSet(sql, params);
		// 创建ResultSetMetaData对象
		ResultSetMetaData rsmd = null;
		// 结果集列数
		int columnCount = 0;
		try {
			rsmd = rs.getMetaData();
			// 获得结果集列数
			columnCount = rsmd.getColumnCount();
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
		}
		// 创建List
		List<Object> list = new ArrayList<>();
		try {
			// 将ResultSet的结果保存到List中
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {
					map.put(rsmd.getColumnLabel(i), rs.getObject(i));
				}
				list.add(map);// 每一个map代表一条记录，把所有记录存在list中
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			// 关闭所有资源
			closeAll();
		}
		return list;
	}

	/**
	 * 存储过程带有一个输出参数的方法
	 * 
	 * @param sql
	 *            存储过程语句
	 * @param params
	 *            参数数组
	 * @param outParamPos
	 *            输出参数位置
	 * @param SqlType
	 *            输出参数类型
	 * @return 输出参数的值
	 */
	public Object excuteQuery(String sql, Object[] params, int outParamPos, int SqlType) {
		Object object = null;
		conn = getCon();
		try {
			// 调用存储过程
			// prepareCall:创建一个 CallableStatement 对象来调用数据库存储过程。
			callableStatement = conn.prepareCall(sql);
			// 给参数赋值
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					callableStatement.setObject(i + 1, params[i]);
				}
			}
			// 注册输出参数
			callableStatement.registerOutParameter(outParamPos, SqlType);
			// 执行
			callableStatement.execute();
			// 得到输出参数
			object = callableStatement.getObject(outParamPos);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			// 释放资源
			closeAll();
		}
		return object;
	}

	/**
	 * 关闭所有资源对象
	 */
	private void closeAll() {
		// 关闭结果集对象
		if (rst != null) {
			try {
				rst.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}

		// 关闭PreparedStatement对象
		if (pst != null) {
			try {
				pst.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}

		// 关闭CallableStatement 对象
		if (callableStatement != null) {
			try {
				callableStatement.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}

		// 关闭Connection 对象
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

}
