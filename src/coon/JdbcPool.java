package coon;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.logicalcobwebs.proxool.ProxoolDataSource;

/**
 * <p>标题: JdbcPool.java</p>
 * <p>业务描述:使用proxool数据库连接池</p>
 * <p>公司:东华软件股份公司</p>
 * <p>版权:dhcc2014</p>
 * @author <a href ='zhangfei_xa@dhcc.com.cn'>张飞</a>
 * @date 2014年4月22日
 * @version V1.0 
 */
public class JdbcPool {
	
	private static Logger logger = Logger.getLogger(JdbcPool.class);
	
	// 初始化数据源
	private static ProxoolDataSource getProxoolDataSource() {
        ProxoolDataSource pds = new ProxoolDataSource();  
        pds.setAlias("sqlserver");
        pds.setMaximumActiveTime(36000000);
        pds.setMaximumConnectionCount(200);
        pds.setMinimumConnectionCount(5);
        pds.setUser("sa");
        pds.setPassword("admin3395@fby"); 
        //pds.setDriverUrl("jdbc:sqlserver://127.0.0.1:1433;databaseName=Chss_New");  
        //pds.setDriverUrl("jdbc:sqlserver://192.168.60.3\\\feyy:1081;databaseName=Chss_New");   // sa/3395
        //pds.setDriverUrl("jdbc:sqlserver://192.168.60.3\\\feyy:1081;databaseName=History_ChssDb");  //正式上传历史数据:  sa/3395
        pds.setDriverUrl("jdbc:sqlserver://192.168.60.5\\feyy;databaseName=Chss_New");   //正式上传增量数据:   sa/admin3395@fby
        pds.setDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
        
        /*用于测试，测试完之后在注释掉*/
        pds.setUser("sa");
        pds.setPassword("lei"); 
        pds.setDriverUrl("jdbc:sqlserver://127.0.0.1:1433;databaseName=Chss_New");  
        /**/
        
        return pds;  
    }
	
	/**
	* 方法名:  getConnection<br>
	* 方法功能描述: 获取数据库链接
	* @return Connection
	* @Author:  张飞
	* @Create Date:  2014年4月23日 上午11:20:15
	 */
	public static Connection getConnection() {
		ProxoolDataSource pds = getProxoolDataSource();
		try {
			return pds.getConnection();
		} catch (SQLException e) {
			logger.error("get connection error :" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
