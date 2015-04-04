package impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sys.CommonUtil;
import util.DateUtil;
import util.EhcacheUtil;
import util.UnifieUtil;
import Interface.BuildingXml;
import coon.JdbcPool;

/**
 * <p>标题: CqscBuildingXml.java</p>
 * <p>业务描述:产前筛查记录</p>
 * <p>公司:东华软件股份公司</p>
 * <p>版权:dhcc2013</p>
 * @author huangwh
 * @date 2014年4月16日
 * @version V1.0 
 */
public class Fb_ycf_cqsc implements BuildingXml{

	private String  tbName;
	
	public Fb_ycf_cqsc(){
		tbName = "FB_YCF_CQSC";
	}
	
	public Map<String, String> createxml( String startTime,String endTime){
		Connection con = JdbcPool.getConnection();
		String sql = "select * from " + tbName + " where jlzt != 9 AND XGRQ > ? AND XGRQ <= ?";
		PreparedStatement pspm = null;
		ResultSet rs = null;
		Map<String, String> xmlMap = new HashMap<String, String>();
		String xml = null;
		try {
			pspm = con.prepareStatement(sql);
			pspm.setString(1, startTime);
			pspm.setString(2, endTime);
			rs = pspm.executeQuery();
			while (rs.next()) {
				//个人基本信息标识号
				String GRBJH = rs.getString("GRBJH");
				String HEALTH_ID = "";//UnifieUtil.null2(GRBJH);
				
				//孕产妇标识号
				String YKBH = rs.getString("YKBH");
				String RPTNO = UnifieUtil.null2(GRBJH);
				
				//筛查日期
				String CQSCRQ = rs.getString("CQSCRQ");
				String SCREENING_DATE = UnifieUtil.convertDateToD8(CQSCRQ); //D8
				
				//NTD风险
				String NTD = rs.getString("NTD");
				String NTD_RISK = UnifieUtil.null2(NTD);
				
				//NTD风险分值
				String NTDFZ = rs.getString("NTDFZ");
				String NTD_RISK_VALUE = UnifieUtil.null2(NTDFZ);
				
				//21-三体
				String EYST = rs.getString("EYST");
				String THREE_BODY21 = UnifieUtil.null2(EYST);
				
				//18-三体
				String SBST = rs.getString("SBST");
				String THREE_BODY18 = UnifieUtil.null2(SBST);
				
				//有无产前诊断
				String CQZD = rs.getString("CQZD");
				String PRENATAL_DIAGNOSIS = PRENATAL_DIAGNOSIS(CQZD);
				
				//产前诊断方法代码
				String CQZDDM = rs.getString("CQZDDM");
				String PRENATAL_DIAGNOSIS_F = PRENATAL_DIAGNOSIS_F(CQZDDM);
				
				//诊断结果代码
				String ZSJG = rs.getString("ZSJG");
				String DIAGNOSIS = DIAGNOSIS(ZSJG);
				
				//检查人员姓名
				String DJRY = rs.getString("DJRY");
				String CREATE_EMP_NAME = UnifieUtil.null2(DJRY);
				
				//机构名称
				String DJJGMC = rs.getString("DJJGMC");
				String CREATE_ORG_NAME = UnifieUtil.null2(DJJGMC);
				
				//机构代码
				String DJJGDM = rs.getString("DJJGDM");
				String CREATE_ORG_CODE =  CommonUtil.isNull(EhcacheUtil.findValue("SYS_ZCJG", CommonUtil.isNull(DJJGDM))) ;
				
				xml =
					"<body>" +
					  "<head>" +
					    "<userid>nb_fybj</userid>" +
					    "<password>123</password>" +
					    "<trans_no>B04.02.04.00</trans_no>" +
					  "</head>" +
					  "<resquest>" +
					    "<B04.02.04.00>" +
					      "<RPTNO>" + RPTNO + "</RPTNO>" +
					      "<HEALTH_ID>" + HEALTH_ID + "</HEALTH_ID>" +
					      "<SCREENING_DATE>"+ SCREENING_DATE + "</SCREENING_DATE>" +
					      "<NTD_RISK>" + NTD_RISK + "</NTD_RISK>" + 
					      "<NTD_RISK_VALUE>" +NTD_RISK_VALUE + "</NTD_RISK_VALUE>" +
					      "<THREE_BODY21>" + THREE_BODY21 + "</THREE_BODY21>" +
					      "<THREE_BODY18>" + THREE_BODY18 + "</THREE_BODY18>" +
					      "<PRENATAL_DIAGNOSIS>" + PRENATAL_DIAGNOSIS + "</PRENATAL_DIAGNOSIS>" +
					      "<PRENATAL_DIAGNOSIS_F>" + PRENATAL_DIAGNOSIS_F + "</PRENATAL_DIAGNOSIS_F>" + 
					      "<DIAGNOSIS>" + DIAGNOSIS + "</DIAGNOSIS>" +
					      "<CREATE_EMP_NAME>" + CREATE_EMP_NAME + "</CREATE_EMP_NAME>" +
					      "<CREATE_ORG_NAME>" + CREATE_ORG_NAME + "</CREATE_ORG_NAME>" +
					      "<UPLOAD_TIME>" + DateUtil.nowDT15() + "</UPLOAD_TIME>" +
					      "<CREATE_ORG_CODE>" + CREATE_ORG_CODE + "</CREATE_ORG_CODE>" +
					    "</B04.02.04.00>" +
					  "</resquest>" +
					"</body>";
				xmlMap.put(RPTNO, xml);
			}
		} catch (SQLException e) {
			logger.error("fail to connect db：" + e.getMessage());
		}finally{
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

		return xmlMap;
	}
	
	/**
		中心系统:
		产前筛查记录:有无代码表 NBP00.00.002
		值	值含义	说    明
		0	无	
		1	有	
		9	不详	
		
		业务系统:
		产前诊断	1	无
		产前诊断	2	有
	 */
	private String PRENATAL_DIAGNOSIS(String code){
		if(code==null || code.trim().equals(""))
			return "9";
		else if(code.equals("1"))
			return "0";
		else if(code.equals("2"))
			return "1";
		return "9";
	}
	
	/**
		中心系统:
		产前诊断方法代码表 NBB04.02.302,多选,字典|分割
		
		业务系统:
		用/
	 */
	private String PRENATAL_DIAGNOSIS_F(String f){
		if(f==null || f.trim().equals(""))
			return "";
		return f.replaceAll("/", "|");
	}
	
	/**
		中心系统:
		初次产检记录-未见异常未查代码表 NBB04.02.109
		值	值含义	说    明
		1	未见异常	
		2	异常	
		99	未查	
		
		业务系统:
		检查结果	1	未见异常
		检查结果	2	异常情况
	**/
	private String DIAGNOSIS(String code){
		if(code==null || code.trim().equals(""))
			return "99";	
		else
			return code;
	}
	
    public static void main(String[] args) {
    	Fb_ycf_cqsc cqscBuildingXml = new Fb_ycf_cqsc();
    	cqscBuildingXml.createxml("2009-01-01 00:00:00","2014-10-01 00:00:00");
    }
	
    
    private static Log logger = LogFactory.getLog(Fb_ycf_cqsc.class);
}
