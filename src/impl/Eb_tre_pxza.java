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
* <p>标题: Eb_tre_pxza.java</p>
* <p>业务描述:贫血专案    EB_TRE_PXZA</p>
* <p>公司:东华软件股份公司</p>
* <p>版权:dhcc2014</p>
* @author 李研
* @date 2014年4月17日
* @version V1.0
 */
public class Eb_tre_pxza implements BuildingXml{
	
	private static Logger logger = Logger.getLogger(Eb_tre_pxza.class);

	@Override
	public Map<String, String> createxml(String startTime, String endTime) {

		String sql = "SELECT LSH,GRBJH,JZARQ,JZAYS,MYQJKZK,WYFS,TJFSPZ,DNYF,"
					+ "SCTJFSYF,JAZT,JARQ,JAYS,JAJL,DJRY,DJJGDM,DJJGMC "
					+ "FROM EB_TRE_PXZA WHERE  JLZT != 9 AND XGRQ > ? AND XGRQ <= ?";
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
				
				//String rptno = rs.getString("LSH");
				String rptno = rs.getString("GRBJH");
				rptno = getValueOf(rptno);
				resultMap.put("RPTNO", rptno);
				
				resultMap.put("HEALTH_ID", "");
				//建专案日期
				String createTime = rs.getString("JZARQ");
				createTime = getValueOf(createTime);
				createTime = convertDateToD8(createTime);
				resultMap.put("CREATE_TIME", createTime);
				
				//登记人员
				String createEmpId = rs.getString("JZAYS");
				createEmpId = getValueOf(createEmpId);
				createEmpId = DictMap.getCzry(createEmpId);
				resultMap.put("CREATE_EMP_ID", createEmpId);
				
				resultMap.put("CREATE_ORG_CODE", DictMap.getZcjg(rs.getString("DJJGDM")));
				resultMap.put("MOTHER_HEALTH", getValueOf(rs.getString("MYQJKZK")));
				
				//喂养方式
				String feeding = rs.getString("WYFS");
				feeding = getValueOf(feeding);
				feeding = getFeed(feeding);
				resultMap.put("FEEDING", feeding);
				
				
				/**
				 * [2014-04-29 17:33:25 ERROR] [DefaultQuartzScheduler_Worker-1] {Client.Result:51}-表名：Eb_tre_pxza,
				 * 主键：33020600005000120091016110937903处理失败！失败信息：业务处理失败,原因:ORA-12899: 
				 * 列 "KTMEDICALCENTER"."KTMCHS_CHILD_ANEMIA"."FEEDING_SPECIES" 的值太大 (实际值: 20, 最大值: 10) ORA-06512: 在 line 4
				 *
				 *详见 dhccChss.log.2014-04-29.log
				 *
				 *接口规范长度是50         但是日志报的是10    且汉字占2字符，，，，，，所以改为截取长度为5
				 *
				 *2014-4-30 
				 */
				String feedingSpecies = rs.getString("TJFSPZ");
				feedingSpecies = cutLength(feedingSpecies, 5);
				feedingSpecies = getValueOf(feedingSpecies);
				resultMap.put("FEEDING_SPECIES", feedingSpecies);
				
				
				
				resultMap.put("WEANING_MONTH", getValueOf(rs.getString("DNYF")));
				resultMap.put("FOOD_MONTH", getValueOf(rs.getString("SCTJFSYF")));
				resultMap.put("CLOSED", getValueOf(rs.getString("JAZT")));
				
				//结案日期
				String closedDate = rs.getString("JARQ");
				closedDate = getValueOf(closedDate);
				closedDate = convertDateToD8(closedDate);
				resultMap.put("CLOSED_DATE", closedDate);
				
				//结案医生
				String closedEmpId = rs.getString("JAYS");
				closedEmpId = getValueOf(closedEmpId);
				closedEmpId = DictMap.getCzry(closedEmpId);
				resultMap.put("CLOSED_EMP_ID", closedEmpId);
				
				resultMap.put("CLOSED_CONCLUSIONS", getValueOf(rs.getString("JAJL")));
				resultMap.put("CLOSE_ORG_CODE", "");
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
					.append("<trans_no>B04.01.02.03</trans_no>")
					.append("</head>")
					.append("<resquest>")
					.append("<B04.01.02.03>");

		for(String key : map.keySet()){
			xmlString.append("<")
						.append(key)
						.append(">")
						.append(map.get(key))
						.append("</")
						.append(key)
						.append(">");
		}
		xmlString.append("</B04.01.02.03>")
					.append("</resquest>")
					.append("</body>");
		
		//System.out.println(xmlString.toString());
		//logger.info(xmlString.toString());
		return xmlString.toString();
	}
	
	/**
	 * 喂养方式  匹配
	 * @author ly
	 * 业务表   1   纯母乳喂养   2 混合喂养   3 人工喂养
	 * 中心表   1  母乳喂养  2  纯母乳喂养    3  人工喂养   4 混合喂养   9 其他方式     
	 */
	public String getFeed(String feed){
		
		if("1".equals(feed)){
			feed="2";
		}
		if("2".equals(feed)){
			feed="4";
		}
		
		return feed;
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
	 * 方法名:          cutLength
	 * 方法功能描述:   按照参数要求截取长度
	 * @Create Date:   2014年4月17日 
	 */
	public String cutLength(String str, int length){
		
		if(str == null || "".equals(str) || length <= 0){
			return "";
		}
		
		if(str.length() > length){
			str = str.substring(0, length);
		}
		return str;
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

		new Eb_tre_pxza().createxml("2013-12-01", "2013-12-03");
	}
}
