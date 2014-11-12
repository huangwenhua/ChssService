package coon;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

/**
 * <p>标题: JdbcUtil.java</p>
 * <p>业务描述:数据库链接工具类</p>
 * <p>公司:东华软件股份公司</p>
 * <p>版权:dhcc2014</p>
 * @author <a href ='zhangfei_xa@dhcc.com.cn'>张飞</a>
 * @date 2014年4月16日
 * @version V1.0 
 */
public final class JdbcUtil {

	private static Logger logger = Logger.getLogger(JdbcUtil.class);
	/**
	 * 驱动
	 */
	private static final String DRIVERNAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	/**
	 * 链接地址
	 */
	//private static final String URL = "jdbc:sqlserver://127.0.0.1:1433;databaseName=Chss_New";
	//"jdbc:sqlserver://192.168.60.3\\\feyy:1081;databaseName=Chss_New";
	//private static final String URL = "jdbc:sqlserver://192.168.60.3\\\feyy:1081;databaseName=History_ChssDb";
//	private static final String URL = "jdbc:sqlserver://192.168.60.5\\feyy;databaseName=Chss_New";
	/**
	/**
	 * 用户名
	 */
//	private static final String USER = "sa";
	/**
	 * 密码
	 */
//	private static final String PASSWORD = "admin3395@fby";

	// 加载驱动
	static {
		try {
			Class.forName(DRIVERNAME);
		} catch (ClassNotFoundException e) {
			logger.error("Loading Driver error:" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 方法名: getConnection 方法功能描述: 获取数据库链接
	 * 
	 * @return Connection conn
	 * @Author: 张飞
	 * @Create Date: 2014年4月16日 下午3:52:43
	 */
//	public static Connection getConnection() {
//		Connection conn = null;
//		try {
//			conn = DriverManager.getConnection(URL, USER, PASSWORD);
//		} catch (SQLException e) {
//			logger.error("Get Connection error:" + e.getMessage());
//			e.printStackTrace();
//		}
//		return conn;
//	}

	/**
	 * 方法名: close 方法功能描述: 关不数据库链接
	 * 
	 * @param conn
	 * @Author: 张飞
	 * @Create Date: 2014年4月16日 下午3:59:26
	 */
	public static void close(Connection conn) {
		if (conn == null)
			return;
		try {
			if (!conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			logger.error("Clost Connection error:" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 方法名: execute 方法功能描述: 更新一条sql语句[包括增加、删除、修改]
	 * 
	 * @param sql
	 *            sql语句
	 * @Author: 张飞
	 * @Create Date: 2014年4月16日 下午4:06:23
	 */
	public static void execute(String sql) {
		Connection conn = JdbcPool.getConnection();
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			logger.error("execute sql error:" + e.getMessage());
			logger.error("error sql is :" + sql);
			e.printStackTrace();
		} finally {
			close(conn);
		}
	}
	
}
