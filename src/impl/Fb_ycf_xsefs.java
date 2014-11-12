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
* <p>标题: Fb_ycf_xsefs.java</p>
* <p>业务描述:产后  新生儿访视    FB_YCF_XSEFS</p>
* <p>公司:东华软件股份公司</p>
* <p>版权:dhcc2014</p>
* @author 李研
* @date 2014年4月16日
* @version V1.0
 */
public class Fb_ycf_xsefs implements BuildingXml{
	private static Logger logger = Logger.getLogger(Fb_ycf_xsefs.class);

	@Override
	public Map<String, String> createxml(String startTime, String endTime) {

		String sql = "SELECT YKBH,LSH,XSELSH,GRBJH,JCRQ,SFJXJC,TZJCYY,TZJCSJ,"
						+ "TW,TZ,WYFS,DJJGDM,DJJGMC,DJRYBM,DJRY,SFFXXJX,XSEJBSC,"
						+ "XSETLSC,XSEYCQKDM FROM FB_YCF_XSEFS "
						+ " WHERE  JLZT != 9 AND XGRQ > ? AND XGRQ <= ?";
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
				
				String rptno = rs.getString("YKBH");
				rptno = getValueOf(rptno);
				resultMap.put("RPTNO", rptno);
				
				resultMap.put("CHECK_SN", getValueOf(rs.getString("LSH")));
				resultMap.put("BIRTH_ORDER", getValueOf(rs.getString("XSELSH")));
				resultMap.put("HEALTH_ID", "");
				
				//检查日期
				String checkDate = rs.getString("JCRQ");
				checkDate = getValueOf(checkDate);
				checkDate = convertDateToD8(checkDate);
				resultMap.put("CHECK_DATE", checkDate);
				
				resultMap.put("MONITOR", getValueOf(rs.getString("SFJXJC")));
				
				//终止监测原因
				String monitorReason = rs.getString("TZJCYY");
				monitorReason = getValueOf(monitorReason);
				monitorReason = getDieReason(monitorReason);
				resultMap.put("MONITOR_REASON", monitorReason);
				
				//终止监测日期
				String monitorDate = rs.getString("TZJCSJ");
				monitorDate = getValueOf(monitorDate);
				monitorDate = convertDateToD8(monitorDate);
				resultMap.put("MONITOR_DATE", monitorDate);
				
				resultMap.put("TEMPERATURE", getValueOf(rs.getString("TW")));
				resultMap.put("WEIGHT", getValueOf(rs.getString("TZ")));
				resultMap.put("FEEDING", getValueOf(rs.getString("WYFS")));
				
				//登记机构代码
				String checkOrgCode = rs.getString("DJJGDM");
				checkOrgCode = getValueOf(checkOrgCode);
				checkOrgCode = DictMap.getZcjg(checkOrgCode);
				resultMap.put("CHECK_ORG_CODE", checkOrgCode);
				
				resultMap.put("CREATE_ORG_NAME", getValueOf(rs.getString("DJJGMC")));
				
				//登记人员
				String checkEmpId = rs.getString("DJRYBM");
				checkEmpId = getValueOf(checkEmpId);
				checkEmpId = DictMap.getCzry(checkEmpId);
				resultMap.put("CHECK_EMP_ID", checkEmpId);
				
				resultMap.put("CREATE_EMP_NAME", getValueOf(rs.getString("DJRY")));
				resultMap.put("DISCOVERED_DEFECTS", getValueOf(rs.getString("SFFXXJX")));
				resultMap.put("NEONATAL_SCREENING", "");
				
				//新生儿听力筛查代码
				String hearingScreening = rs.getString("XSETLSC");
				hearingScreening = getValueOf(hearingScreening);
				hearingScreening = getHearing(hearingScreening);
				resultMap.put("HEARING_SCREENING", "");
				
				//新生儿异常情况代码
				String neonatalAnomalies = rs.getString("XSEYCQKDM");
				neonatalAnomalies = getValueOf(neonatalAnomalies);
				neonatalAnomalies = getMultiple(neonatalAnomalies);
				resultMap.put("NEONATAL_ANOMALIES", "");
				
				resultMap.put("UPLOAD_TIME", DateUtil.nowDT15());
				
				map.put(getValueOf(rs.getString("LSH")), doXMLFromMap(resultMap));
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
					.append("<trans_no>B04.02.07.00</trans_no>")
					.append("</head>")
					.append("<resquest>")
					.append("<B04.02.07.00>");

		for(String key : map.keySet()){
			xmlString.append("<")
						.append(key)
						.append(">")
						.append(map.get(key))
						.append("</")
						.append(key)
						.append(">");
		}
		xmlString.append("</B04.02.07.00>")
					.append("</resquest>")
					.append("</body>");
		//System.out.println(xmlString.toString());
		//logger.info(xmlString.toString());
		return xmlString.toString();
	}
	
	/**
	 * 产后访视  新生儿访视  终止监测原因   匹配
	 * @author ly
	 * 业务表   1   死亡   2  永久性迁出   3  失访    4  其他
	 * 中心表   1  妇女死亡   2  永久性迁出    3  失访   4 葡萄胎     5  异位妊娠
	 * 			6  妇女专项-终止监测原因代    7  <28周治疗性引产   99其他
	 */
	public String getDieReason(String reason){

		if("4".equals(reason)){
			reason="99";
		}
		
		return reason;
	}
	
	/**
	 * 新生儿听力筛查   匹配
	 * @author ly
	 * 业务表   1   有   0 无
	 * 中心表   1  正常   2  未查    3  异常   4 复查     
	 */
	public String getHearing(String hearig){
		
		if("0".equals(hearig)){
			hearig="2";
		}
		
		return hearig;
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
	 * 方法名:          getMultiple
	 * 方法功能描述:    将多选串中的分隔符转换
	 * @Create Date:   2014年4月17日 
	 */	
	public String getMultiple(String code) {

		if (code != null && !"".equals(code)) {
			code = code.replace("/", "|");
		}
		 
		if (code.endsWith("|")){
			code=code.substring(0, code.length()-1);
		}
		return code;
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

		new Fb_ycf_xsefs().createxml("2013-12-01", "2013-12-03");
	}
}
