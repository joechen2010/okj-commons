package org.okj.commons.db.jdbc;
/**
 * 
 
 *数据连接帮助类
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnection {
	
	public static java.sql.Connection getConnection(String driverClassName,String DB_URL , String DB_USER, String DB_PASSWORD) {
        java.sql.Connection conn = null;
		try {
			Class.forName(driverClassName);
			try {
				DriverManager.registerDriver(new net.sourceforge.jtds.jdbc.Driver());
				conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return conn;
	}
	/**
	 * create a statement
	 * 
	 * @param conn
	 * @return
	 */
	public static Statement createStatement(Connection conn) {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stmt;
	}

	/**
	 * create a Query for Result
	 * 
	 * @param stmt
	 * @param sql
	 * @return
	 */
	public static ResultSet executeQuery(Statement stmt, String sql) {
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	/**
	 * create a Prepared Statement
	 * 
	 * @param conn
	 * @param sql
	 * @return
	 */
	public static PreparedStatement preparedStatement(Connection conn,
			String sql) {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pstmt;
	}

	public static PreparedStatement peraredSatement(Connection conn,
			String sql, int autoGeneratedKeys) {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql, autoGeneratedKeys);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pstmt;
	}

	public static int executeUpdate(Connection conn, String sql) {
		int ret = 0;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			ret = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
		}

		return ret;
	}

	/**
	 * close Statement
	 * 
	 * @param stmt
	 */
	public static void close(Statement stmt) {
		if (null != stmt) {
			try {
				stmt.close();
				stmt = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Close the ResultSet
	 * 
	 * @param rs
	 */
	public static void close(ResultSet rs) {
		if (null != rs) {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * close the Connection
	 * 
	 * @param conn
	 */
	public static void close(Connection conn) {
		if (null != conn) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}

