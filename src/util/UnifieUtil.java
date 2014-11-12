package util;

import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;

import sys.CommonUtil;
import coon.JdbcPool;
import dictall.DictMap;

/**
 * @author huangwh
 * @date 2014年4月22日
 * @version V1.0 
 */
public class UnifieUtil {

	/**
	 * 方法名: null2
	 * 方法功能描述: 对null的统一返回
	 * @param str
	 * @return
	 */
	public static String null2(String str){
		return str = (str==null)?"":str;
	}
	
	/**
	 * 
	* 方法名:          convertDateToD8
	* 方法功能描述:    将字符串日期转换为D8格式的日期
	* @Create Date:   2014年4月17日 下午5:29:34
	 */
	public static String convertDateToD8(String strDate){
		if(strDate == null || "".equals(strDate)){
			return "";
		}
		
		String newDate = "";
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(strDate);
			newDate = new SimpleDateFormat("yyyyMMdd").format(date);
		} catch (ParseException e) {
			Date date;
			try {
				strDate = strDate.replace("/", "-");
				date = new SimpleDateFormat("yyyy-MM-dd").parse(strDate);
				newDate = new SimpleDateFormat("yyyyMMdd").format(date);
			} catch (ParseException e1) {
				logger.error("-----日期转换出错-------");
			}
			
		}
		return newDate;
	}
	

	/**
	private String xmlToStr(Document document){
		OutputFormat  format = new OutputFormat("\t",true,"utf-8"); 
		StringWriter out = new StringWriter();
		XMLWriter writer = new XMLWriter(out, format);
		try {
			writer.write(document);
			writer.flush();
		} catch (IOException e) {
			logger.error("fail xmlToStre");
		}
		return out.toString();
	}	
	**/
	
	/**
	 *通过个人保健号获取基本信息
	 */
	public static void perInfo(String grbjh,Map<String,String> localmap){
		Connection con = JdbcPool.getConnection();
		String sql = "select * from da_gr_hxda where GRBJH='" + grbjh + "'";
		PreparedStatement pspm = null;
		ResultSet rs = null;
		try {
			pspm = con.prepareStatement(sql);
			rs = pspm.executeQuery();
			while (rs.next()) {
				localmap.put("JZSSBM", rs.getString("JZSSBM"));//PROVINCE
				localmap.put("JZDSBM", rs.getString("JZDSBM"));//CITY
				localmap.put("JZQXBM", rs.getString("JZQXBM"));//COUNTYCODE
				localmap.put("JZJDBM", rs.getString("JZJDBM"));//TOWN
				localmap.put("JZJWBM", rs.getString("JZJWBM"));//COMMUNITY
				localmap.put("XM", rs.getString("XM"));//NAME
				localmap.put("XB", DictMap.getXB(CommonUtil.isNull(rs.getString("XB"))));//SEX
				localmap.put("CSRQ", CommonUtil.getDate(rs.getDate("CSRQ"),"yyyyMMdd"));//BIRTHDAY
				localmap.put("SFZH", rs.getString("SFZH"));//IDCARD
				localmap.put("XXABO", getABO(rs.getString("XXABO")));//BLOOD
				localmap.put("XXRH", getRH(CommonUtil.isNull(rs.getString("XXRH"))));//RH_TYPE
				localmap.put("JZXXDZ", rs.getString("JZXXDZ"));//HOME_ADDRESS
				localmap.put("LXDH", rs.getString("LXDH"));//HOME_PHONE
				localmap.put("GZDW",rs.getString("GZDW"));//COMPANY
			}
		} catch (SQLException e) {
			logger.error("fail to connect db：" + e.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				try {
					if (pspm != null) {
						pspm.close();
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				} finally {
					try {
						if (con != null) {
							con.close();
						}
					} catch (Exception e) {
						throw new RuntimeException(e);
					}

				}

			}

		}

	}
	
	// ABO血型
	// 业务系统中：1:A型\2:B型\3:O型\4:AB型\5:不详
	// 中心系统中：0:O型\1:A型\2:B型\3:AB型\5:不详
	private static String getABO(String code) {
		String zxbm = "";
		if(code == null)
			return zxbm;
		switch (code) {
		case "1":
			zxbm = "1";
			break;
		case "2":
			zxbm = "2";
			break;
		case "3":
			zxbm = "0";
			break;
		case "4":
			zxbm = "3";
			break;
		case "5":
			zxbm = "5";
			break;
		default:
			break;
		}
		return zxbm;
	}
	
	// Rh血型
	// 业务系统中：1阴性 2阳性 3不详
	// 中心系统中：1Rh阴性 2Rh阳性 3不详
	private static String getRH(String code) {
		String zxbm = "";
		if(code == null)
			return zxbm;
		switch (code) {
		case "1":
			zxbm = "1";
			break;
		case "2":
			zxbm = "2";
			break;
		case "3":
			zxbm = "3";
			break;
		default:
			break;
		}
		return zxbm;
	}
	
	/**操作人员**/
	public static String ry(String text){
		String ret = "";
		try {
			ret = DictMap.getCzry( UnifieUtil.null2(text) );
		} catch (SQLException e) {
			logger.error("error to get ry:" + e.getMessage());
		}
		return ret;
	}
	
	/**操作机构**/
	public static String jg(String text){
		String ret = "";
		try {
			ret = DictMap.getZcjg(  UnifieUtil.null2(text) );
		} catch (SQLException e) {
			logger.error("error to get ry:" + e.getMessage());
		}
		return ret;
	}
	
	/**区域中心
	10	未婚
	20	已婚
	21	初婚
	22	再婚
	23	复婚
	30	丧偶
	40	离婚
	90	未说明的婚姻状况
	 **/
	
	/**本地业务
	1	未婚
	2	已婚
	3	分居
	4	离异
	5	丧偶
	**/
	public static String married(String text){
		String ret = "90";
		if(text == null)
			return ret;
		if(text.equals("1"))
			ret = "10";
		else if(text.equals("2"))
			ret = "20";
		else if(text.equals("3"))
			ret = "90";
		else if(text.equals("4"))
			ret = "40";
		else if(text.equals("5"))
			ret = "30";
		return ret;
	}
	
	/**痛经**/
	/**区域中心
	0	无
	1	有
	9	不详
	 **/
	
	/**本地业务
	1	无
	2	轻
	3	中
	4	重
	**/
	public static String tj(String text){
		String ret = "9";
		if(text == null)
			return ret;
		if(text.equals("1"))
			ret = "0";
		else if(text.equals("2"))
			ret = "1";
		else if(text.equals("3"))
			ret = "1";
		else if(text.equals("4"))
			ret = "1";
		return ret;
	}
	
	private static Log logger = LogFactory.getLog(UnifieUtil.class);
}
