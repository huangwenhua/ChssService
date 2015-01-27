package Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import util.DateUtil;
import util.ErrorUtil;
import Interface.Bill;
import Interface.BuildingXml;
import coon.JdbcPool;
/**
 * <p>标题: Entrance.java</p>
 * <p>业务描述:程序入口类</p>
 * <p>公司:东华软件股份公司</p>
 * <p>版权:dhcc2014</p>
 * @author <a href ='zhangfei_xa@dhcc.com.cn'>张飞</a>
 * @date 2014年4月15日
 * @version V1.0 
 */
public class Entrance {
	private static Logger logger = Logger.getLogger(Entrance.class);
	private Result result = new Result();
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	
	public Entrance() {

	}
	
	public Entrance(String startTime, String endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	// 取得所有的表名
	private List<String> findTable() {
		List<String> list = new ArrayList<String>();
		String sql = "select tbName from t_table";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		conn = JdbcPool.getConnection();
		try {
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("tbName"));
			}
		} catch (SQLException e) {
			logger.error("query t_table error :" + e.getMessage());
			e.printStackTrace();
		} finally {
			conn = null;
			pstm = null;
			rs = null;
		}
		return list;
	}
	
	// 获取所有的错误信息
	private List<String[]> findErrorInfo() {
		List<String[]> list = new ArrayList<String[]>();
		String[] error = null;
		String sql = "select xml,keyword,tbName from t_errordetail";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		conn = JdbcPool.getConnection();
		try {
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while (rs.next()) {
				error = new String[3];
				error[0] = rs.getString("xml");
				error[1] = rs.getString("keyword");
				error[2] = rs.getString("tbName");
				list.add(error);
			}
		} catch (SQLException e) {
			logger.error("query t_error error :" + e.getMessage());
			e.printStackTrace();
		} finally {
			conn = null;
			pstm = null;
			rs = null;
		}
		return list;
	}
	
	// 处理错误信息
	private  void doError() {
		List<String[]> list = findErrorInfo();
		logger.info("start deal with error infomation:"+ DateUtil.now());
		for (String[] error : list) {
			ErrorUtil.delete(error[1]); // 删除错误信息
			result.doResult(error[0], error[2], error[1]);
		}
		logger.info("end deal with error infomation:"+ DateUtil.now());
	}
	
	// 处理增量数据
	private void doIncrement() {
		logger.debug("start get all tablename:" + DateUtil.now());
		List<String> list = findTable();
		logger.debug("finish get all tablename:" + DateUtil.now());
		BuildingXml bx = null;
		String key = "";
		String value = "";
		Map<String, String> map = null;
		for (String tbName : list) {
			try {
				logger.debug("start assemble map tablename:" + tbName + DateUtil.now());
				bx = (BuildingXml) Class.forName("impl." + firstLetterUpper(tbName)).newInstance();
				map = bx.createxml(this.startTime, this.endTime);
				logger.debug("finish assemble map tablename:" + tbName + DateUtil.now());

				logger.debug("start deal with  map :" + DateUtil.now());

				for (Entry<String, String> entry : map.entrySet()) {
					key = entry.getKey();
					value = entry.getValue();
					result.doResult(value, tbName, key);
					logger.debug("doIncrement [ key =" + key + " ], tbName = [ " + tbName + " ]");
				}
				logger.debug("finish deal with  map :" + DateUtil.now());

			} catch (Exception e) {
				logger.error("translate class error tbname = " + tbName);
				e.printStackTrace();
			}
		}
	}
	
	// 处理对账
	public void doBill() {
		logger.info("start bill...");
		List<String> list = new ArrayList<String>();
		list.add("Etbj");
		list.add("Fyzx");
		list.add("Fyzxgrjbxx");
		list.add("Tretgl");
		
		Bill bx = null;
		String xml = null;
		for (String tbName : list) {
			logger.info("start bill [" + tbName + "]...");
			try {
				bx = (Bill) Class.forName("bill." + tbName).newInstance();
				xml = bx.createxml( new SimpleDateFormat("yyyyMMdd").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(this.startTime)) ); 
				result.doResultBill(xml, tbName, tbName);
			} catch (Exception e) {
				logger.error("translate class error tbname = " + tbName + " " + e.getMessage());
				e.printStackTrace();
			}
		}
		logger.info("end bill...");
	}
	
	public String firstLetterUpper(String str) {
		String first = str.substring(0, 1).toUpperCase();
		String remain = str.substring(1,str.length()).toLowerCase();
		return first+remain;
	}
	
	/**
	* 方法名:  doInfo
	* 方法功能描述: 处理所有的信息
	* @Author:  张飞
	* @Create Date:  2014年4月16日 下午3:27:25
	 */
	public void doInfo() {
		//doError();
		doIncrement();
	}
	
	public static void main(String args[]){
//		EhcacheUtil.getInstance().initCache();
		Entrance e = new Entrance("2015-01-25 00:00:00","2015-01-26 00:00:00");
		//e.doInfo();
		
		e.doBill();
	}
}
