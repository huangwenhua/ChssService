package impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import util.DateUtil;
import util.UnifieUtil;
import Interface.BuildingXml;
import coon.JdbcPool;
import dictall.DictMap;

/**
 * <p>标题: CsqkBuildingXml.java</p>
 * <p>业务描述:产妇产时情况</p>
 * <p>公司:东华软件股份公司</p>
 * <p>版权:dhcc2013</p>
 * @author huangwh
 * @date 2014年4月16日
 * @version V1.0 
 */
public class Fb_ycf_csqk implements BuildingXml{
	private String  tbName;
	
	public Fb_ycf_csqk(){
		tbName = "FB_YCF_CSQK";//"FB_YCF_FMJL";
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

				//是否继续监测
				String SFJXJC = rs.getString("SFJXJC");
				String MONITOR = MONITOR(SFJXJC);
						
				//终止监测原因
				String ZZJCYY = rs.getString("ZZJCYY");
				String MONITOR_REASON = UnifieUtil.null2(ZZJCYY);
				
				//终止监测时间
				String ZZJCSJ = rs.getString("ZZJCSJ");
				String MONITOR_DATE = UnifieUtil.convertDateToD8(ZZJCSJ); //D8
				
				//分娩地点代码
				String FMDD = rs.getString("FMDD");
				String BIRTH_PLACE = UnifieUtil.null2(FMDD);
				
				//分娩医院代码  医疗机构代码基础字典库 NBD02.00.002[需要匹配]
				//FLEM
				String FMYYDM = rs.getString("FMYYDM");
				String BIRTH_ORG_CODE = DictMap.getZcjg( FMYYDM );
				
				//住院号
				String ZYH = rs.getString("ZYH");
				String HOSPITALIZED = UnifieUtil.null2(ZYH);
				
				//产前诊断引产代码
				String CQZDYC = rs.getString("CQZDYC");
				String PRENATAL_DIAGNOSIS = MONITOR(CQZDYC);
				
				//是否高危产妇
				String SFGWCF = rs.getString("SFGWCF");
				String HIGH_RISK = MONITOR(SFGWCF);
				
				//是否新法接生
				String SFXFJS = rs.getString("SFXFJS");
				String NEW_DELIVERY = MONITOR(SFXFJS);
				
				//产前血压
				String PRE_BLOOD_PRESSURE = "";
				
				//产前收缩压
				String PRE_SBP = "";
				
				//产前舒张压
				String PRE_DBP = "";
				
				//胎    心
				String FETAL = "";
				
				//分娩日期
				String BIRTH_DATE = ""; //D8
				
				//分娩孕周
				String GESTATION = "";
				
				//胎数代码
				String LITTER_SIZE = "";
				
				//分娩方式
				String CHILDBIRTH = "";
				
				//剖宫产原因
				String CESAREAN_SECTION = "";
				
				//第一产程
				String F_STAGE_1 = "";
				
				//第一产程F
				String F_STAGE_2 = "";
				
				//第二产程
				String S_STAGE_1 = "";
				
				//第二产程F
				String S_STAGE_2 = "";
				
				//第三产程
				String T_STAGE_1 = "";
				
				//第三产程F
				String T_STAGE_2 = "";
				
				//胎盘剥离方式
				String PLACENTAL = "";
				
				//产后血压
				String POST_BLOOD_PRESSURE = "";
				
				//产后收缩压
				String POST_SBP = "";
				
				//产后舒张压
				String POST_DBP = "";
				
				//产时疾病会阴裂伤
				String IPLD = "";
				
				//妊娠高血压疾病
				String PIH = "";
				
				//妊娠并发症
				String PREGNANCY_COMPLICATIONS = "";
				
				//产后出血量
				String CHCXL = rs.getString("CHCXL");
				String POSTPARTUM_BLEEDING = "";//UnifieUtil.null2( CHCXL );
				
				//产后出血原因代码
				String CXYYDM = rs.getString("CXYYDM");
				String POSTPARTUM_BLEEDING_R = "";//HEALTH_GUIDANCE( CXYYDM ); //产妇产时情况-产后出血原因代码表  NBB04.02.408,多选,字典|分割
				
				//其它并发症
				String QTBFZ = rs.getString("QTBFZ");
				String OTHER_COMPLICATIONS =UnifieUtil.null2( QTBFZ );
				
				//产妇死亡代码
				String CFSW = rs.getString("CFSW");
				String MATERNAL_MORTALITY = UnifieUtil.null2(CFSW);
				
				//产妇出院日期
				String CFCYRQ = rs.getString("CFCYRQ");
				String OUTTIME = UnifieUtil.convertDateToD8( CFCYRQ ); //D8
					
				//保健指导代码
				String BJZDDM = rs.getString("BJZDDM");
				String HEALTH_GUIDANCE = "";//HEALTH_GUIDANCE(BJZDDM);
				
				//档案完整
				String DAWZ = rs.getString("DAWZ");
				String FULL_ARCHIVE = FULL_ARCHIVE(DAWZ);
				
				//检查单位
				String DJJGDM = rs.getString("DJJGDM");
				String CHECK_ORG_CODE = DictMap.getZcjg( DJJGDM );  //医疗机构代码基础字典库 NBD02.00.002[需要匹配]
				
				//填表人
				String TBRDM  = rs.getString("TBRDM");
				String CREATE_EMP_ID = "";//DictMap.getCzry( TBRDM ); //中心人员库NBD03.00.001
				
				//登记机构名称
				String DJJGMC = rs.getString("DJJGMC");
				String CREATE_ORG_NAME = UnifieUtil.null2(DJJGMC);
					
				//登记人员姓名
				String DJRY = rs.getString("DJRY");
				String CREATE_EMP_NAME = UnifieUtil.null2(DJRY);
				
				//出生顺序
				String CSSX = rs.getString("CSSX");
				String BIRTH_ORDER = UnifieUtil.null2(CSSX);
				
				//报告日期
				String DJRQ = rs.getString("DJRQ");
				String CREATE_TIME = UnifieUtil.convertDateToD8( DJRQ ); //D8
				
				xml =	"<body>" +
						  "<head>" +
						    "<userid>nb_fybj</userid>" +
						    "<password>123</password>" +
						    "<trans_no>B04.02.05.00</trans_no>" +
						  "</head>" +
						  "<resquest>" + 
						    "<B04.02.05.00>" +
						      "<RPTNO>" + RPTNO + "</RPTNO>" +
						      "<HEALTH_ID>" + HEALTH_ID + "</HEALTH_ID>" +
						      "<MONITOR>" + MONITOR + "</MONITOR>" +
						      "<MONITOR_REASON>" + MONITOR_REASON + "</MONITOR_REASON>" +
						      "<MONITOR_DATE>" + MONITOR_DATE + "</MONITOR_DATE>" + 
						      "<BIRTH_PLACE>" + BIRTH_PLACE + "</BIRTH_PLACE>" + 
						      "<BIRTH_ORG_CODE>" + BIRTH_ORG_CODE + "</BIRTH_ORG_CODE>" + 
						      "<HOSPITALIZED>" + HOSPITALIZED + "</HOSPITALIZED>" +
						      "<PRENATAL_DIAGNOSIS>" + PRENATAL_DIAGNOSIS + "</PRENATAL_DIAGNOSIS>" + 
						      "<HIGH_RISK>" + HIGH_RISK + "</HIGH_RISK>" +
						      "<NEW_DELIVERY>" + NEW_DELIVERY + "</NEW_DELIVERY>" +
						      "<PRE_BLOOD_PRESSURE>" +PRE_BLOOD_PRESSURE + "</PRE_BLOOD_PRESSURE>"+
						      "<PRE_SBP>" +PRE_SBP + "</PRE_SBP>" +
						      "<PRE_DBP>" + PRE_DBP + "</PRE_DBP>" +
						      "<FETAL>" + FETAL + "</FETAL>" +
						      "<BIRTH_DATE>" + BIRTH_DATE + "</BIRTH_DATE>" +
						      "<GESTATION>" + GESTATION + "</GESTATION>" +
						      "<LITTER_SIZE>" + LITTER_SIZE + "</LITTER_SIZE>" +
						      "<CHILDBIRTH>" + CHILDBIRTH + "</CHILDBIRTH>" +
						      "<CESAREAN_SECTION>" + CESAREAN_SECTION + "</CESAREAN_SECTION>" +
						      "<F_STAGE_1>" + F_STAGE_1 + "</F_STAGE_1>" +
						      "<F_STAGE_2>" + F_STAGE_2 + "</F_STAGE_2>" +
						      "<S_STAGE_1>" + S_STAGE_1 + "</S_STAGE_1>" +
						      "<S_STAGE_2>" + S_STAGE_2 + "</S_STAGE_2>" +
						      "<T_STAGE_1>" + T_STAGE_1 + "</T_STAGE_1>" +
						      "<T_STAGE_2>" + T_STAGE_2 + "</T_STAGE_2>" +
						      "<PLACENTAL>" + PLACENTAL + "</PLACENTAL>" +
						      "<POST_BLOOD_PRESSURE>" + POST_BLOOD_PRESSURE + "</POST_BLOOD_PRESSURE>" +
						      "<POST_SBP>" + POST_SBP + "</POST_SBP>" +
						      "<POST_DBP>" + POST_DBP + "</POST_DBP>" +
						      "<IPLD>" + IPLD + "</IPLD>" +
						      "<PIH>" + PIH + "</PIH>" +
						      "<PREGNANCY_COMPLICATIONS>" + PREGNANCY_COMPLICATIONS + "</PREGNANCY_COMPLICATIONS>" +
						      "<POSTPARTUM_BLEEDING>" + POSTPARTUM_BLEEDING + "</POSTPARTUM_BLEEDING>" +
						      "<POSTPARTUM_BLEEDING_R>" + POSTPARTUM_BLEEDING_R + "</POSTPARTUM_BLEEDING_R>" +
						      "<OTHER_COMPLICATIONS>" + OTHER_COMPLICATIONS + "</OTHER_COMPLICATIONS>" +
						      "<MATERNAL_MORTALITY>" + MATERNAL_MORTALITY + "</MATERNAL_MORTALITY>" +
						      "<OUTTIME>" + OUTTIME + "</OUTTIME>" +
						      "<HEALTH_GUIDANCE>" + HEALTH_GUIDANCE + "</HEALTH_GUIDANCE>" +
						      "<FULL_ARCHIVE>" + FULL_ARCHIVE + "</FULL_ARCHIVE>" +
						      "<CHECK_ORG_CODE>" + CHECK_ORG_CODE + "</CHECK_ORG_CODE>" +
						      "<CREATE_EMP_ID>" + CREATE_EMP_ID + "</CREATE_EMP_ID>" +
						      "<CREATE_ORG_NAME>" + CREATE_ORG_NAME + "</CREATE_ORG_NAME>" +
						      "<CREATE_EMP_NAME>" + CREATE_EMP_NAME + "</CREATE_EMP_NAME>" +
						      "<BIRTH_ORDER>" + BIRTH_ORDER + "</BIRTH_ORDER>" +
						      "<CREATE_TIME>" + CREATE_TIME + "</CREATE_TIME>" +
						      "<UPLOAD_TIME>" + DateUtil.nowDT15() + "</UPLOAD_TIME>" +
						    "</B04.02.05.00>" +
						  "</resquest> " +
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
		中心系统：
		是否代码表 NBP00.00.006
		值	值含义	说    明
		1	是	
		2	否	
		9	不详	
		
		业务系统:
		是否标志	0	否
		是否标志	1	是
	**/
	private String MONITOR(String code){
		if(code==null || code.trim().equals(""))
			return "9";
		else if(code.equals("0"))
			return "2";
		else if(code.equals("1"))
			return "1";
		return "9";
	}
	
	/**
		中心系统:
		产前诊断方法代码表 NBB04.02.302,多选,字典|分割
		
		业务系统:
		用/
	 */
	private String HEALTH_GUIDANCE(String str){
//		if(f==null || f.trim().equals(""))
//			return "";
//		return f.replaceAll("/", "|");
		
		String result = null;
		if (str != null) {
			if (str.contains("/")) {
				result = str.replace("/", "|");
				if (result.charAt(result.length()-1)=='|') {
					result=result.substring(0, result.length()-1);
				}

			}

		}
		return result;
	}
	
	/**
		中心系统:
		 是否代码表 NBP00.00.006
		值	值含义	说    明
		1	是	
		2	否		
		9	不详	
		正常异常未详代码表 NBP00.00.007
		
		业务系统:
		单选：0.否 1.是
	 */
	private String FULL_ARCHIVE(String code){
		if(code==null || code.trim().equals(""))
			return "9";
		else if(code.equals("0"))
			return "2";
		else if(code.equals("1"))
			return "1";
		return "9";		
	}
    public static void main(String[] args) {
    	Fb_ycf_csqk csqkBuildingXml = new Fb_ycf_csqk();
    	csqkBuildingXml.createxml("2009-01-01 00:00:00","2014-10-01 00:00:00");
    }
	
    
    private static Log logger = LogFactory.getLog(Fb_ycf_csqk.class);
	
	
}
