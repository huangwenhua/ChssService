package impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import util.DateUtil;
import Interface.BuildingXml;
import coon.JdbcPool;
import dictall.DictMap;

/**
 * 
* <p>标题: Eb_tre_xzbsf.java</p>
* <p>业务描述:心脏病随访  EB_TRE_XZBSF</p>
* <p>公司:东华软件股份公司</p>
* <p>版权:dhcc2014</p>
* @author 李研
* @date 2014年4月17日
* @version V1.0
 */
public class Eb_tre_xzbsf implements BuildingXml{
	
	private static Logger logger = Logger.getLogger(Eb_tre_xzbsf.class);

	@Override
	public Map<String, String> createxml(String startTime, String endTime) {

		String sql = "SELECT LSH,ZALSH,GRBJH,SFRQ,SFYS,ZG,WYKN,HDHFL,XCG,XP,"
					+ "XZBC,ZDYJ,QT,DDST,DJRY,DJJGMC FROM EB_TRE_XZBSF "
					+ "WHERE  JLZT != 9 AND XGRQ > ? AND XGRQ <= ?";
		Map<String, String> map = new HashMap<String, String>();
		
		Connection con = JdbcPool.getConnection();
		PreparedStatement pspm = null;
		ResultSet rs = null;
		
		try {
			pspm = con.prepareStatement(sql);
			pspm.setString(1, startTime);
			pspm.setString(2, endTime);
			rs = pspm.executeQuery();
			
			Map<String, String> resultMap = null;
			while (rs.next()) {

				resultMap = new HashMap<String, String>();

				String rptno = rs.getString("GRBJH");
				rptno = getValueOf(rptno);
				resultMap.put("RPTNO", rptno);
				
				resultMap.put("CHECK_SN", getValueOf(rs.getString("ZALSH")));
				resultMap.put("HEALTH_ID", "");
				
				//检查日期
				String checkDate = rs.getString("SFRQ");
				checkDate = getValueOf(checkDate);
				checkDate = convertDateToD8(checkDate);
				resultMap.put("CHECK_DATE", checkDate);
				
				//检查医生
				String checkCmpId = rs.getString("SFYS");
				checkCmpId = getValueOf(checkCmpId);
				checkCmpId = DictMap.getCzry(checkCmpId);
				resultMap.put("CHECK_EMP_ID", checkCmpId);
				
				//紫    绀
				String cyanosis = rs.getString("ZG");
				cyanosis = getValueOf(cyanosis);
				cyanosis = getFlag(cyanosis);
				resultMap.put("CYANOSIS", cyanosis);
				
				//喂养困难
				String feeding = rs.getString("WYKN");
				feeding = getValueOf(feeding);
				feeding = getFlag(feeding);
				resultMap.put("FEEDING", feeding);
				
				//活动后乏力
				String weakness = rs.getString("HDHFL");
				weakness = getValueOf(weakness);
				weakness = getFlag(weakness);
				resultMap.put("WEAKNESS", weakness);
				
				resultMap.put("ROUTINE", "");
				resultMap.put("CHEST", getValueOf(rs.getString("XP")));
				resultMap.put("CARDIAC_B", "");
				resultMap.put("GUIDANCE", "");
				resultMap.put("OTHER", getValueOf(rs.getString("QT")));
				resultMap.put("DDST", getValueOf(rs.getString("DDST")));
				resultMap.put("REVIEW_DATE", "");
				resultMap.put("CREATE_EMP_NAME", getValueOf(rs.getString("DJRY")));
				resultMap.put("CREATE_ORG_NAME", getValueOf(rs.getString("DJJGMC")));
				
				resultMap.put("UPLOAD_TIME", DateUtil.nowDT15());
				
				map.put(rptno, doXMLFromMap(resultMap));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				try {
					pspm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
			}
		}
		return map;
	}
	
	/**
	 * 
	* 方法名:          doXMLFromMap
	* 方法功能描述:    根据数据库数据转换xml
	* @param:         map   map集合
	* @return:        string
	* @Author:        李研
	* @Create Date:   2014年4月18日
	 */
	public String doXMLFromMap(Map<String, String> map){
		
		if(map == null){
			return "";
		}
		StringBuffer xmlString = new StringBuffer();
		xmlString.append("<body>")
					.append("<head>")
					.append("<userid>").append("nb_fybj").append("</userid>")
					.append("<password>").append("123").append("</password>")
					.append("<trans_no>B04.01.02.10</trans_no>")
					.append("</head>")
					.append("<resquest>")
					.append("<B04.01.02.10>");

		for(String key : map.keySet()){
			xmlString.append("<")
						.append(key)
						.append(">")
						.append(map.get(key))
						.append("</")
						.append(key)
						.append(">");
		}
		xmlString.append("</B04.01.02.10>")
					.append("</resquest>")
					.append("</body>");
		
		
		//System.out.println(xmlString.toString());
		//logger.info(xmlString.toString());
		return xmlString.toString();
	}
	
	/**
	 * 紫绀  匹配
	 * 喂养困难  匹配
	 * 活动后乏力  匹配
	 * @author ly
	 * 业务表   1 有   2 无
	 * 中心表   0  无  1  有     9不详     
	 */
	public String getFlag(String flag){
		
		if("2".equals(flag)){
			flag="0";
		}
		
		return flag;
	}
	
	/**
	 * 
	* 方法名:          convertDateToD8
	* 方法功能描述:    将字符串日期转换为D8格式的日期
	* @Create Date:   2014年4月17日 下午5:29:34
	 */
	public String convertDateToD8(String strDate){
		
		if(strDate == null || "".equals(strDate)){
			return "";
		}
		
		String newDate = "";
		try {
			
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(strDate);
			newDate = new SimpleDateFormat("yyyyMMdd").format(date);
		} catch (ParseException e) {
			logger.error("-----日期转换出错-------");
			e.printStackTrace();
		}
		return newDate;
	}
	
	/**
	 * 
	 * 方法名:          getValueOf
	 * 方法功能描述:    如果为null  返回空
	 * @Create Date:   2014年4月17日 
	 */
	public String getValueOf(String str){
		
		if(str == null){
			return "";
		}
		return str;
	}
	
	public static void main(String[] args) {

		new Eb_tre_xzbsf().createxml("2013-12-01", "2013-12-03");
	}
}
