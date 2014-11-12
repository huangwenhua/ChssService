package impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import util.DateUtil;
import util.UnifieUtil;
import coon.JdbcPool;
import dictall.DictMap;
import Interface.BuildingXml;

/**
 * <p>标题: FpsfBuildingXml.java</p>
 * <p>业务描述:体弱儿(先天性心脏病)(EB_TRE_XTXXZBZA)</p>
 * <p>公司:东华软件股份公司</p>
 * <p>版权:dhcc2013</p>
 * @author huangwh
 * @date 2014年4月16日
 * @version V1.0 
 */
public class Eb_tre_xtxxzbza implements BuildingXml {
	
	private String  tbName;
	
	public Eb_tre_xtxxzbza(){
		tbName = "EB_TRE_XTXXZBZA";
	}

	@Override
	public Map<String, String> createxml(String startTime,String endTime) {
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
				//保健册号
				String GRBJH = rs.getString("GRBJH");
				String RPTNO = UnifieUtil.null2(GRBJH);
				
				//	健康档案号
				String BH = rs.getString("BH");
				String HEALTH_ID = "";//UnifieUtil.null2(BH);
				
				//	建专案日期
				String JZARQ = rs.getString("JZARQ");
				String CREATE_TIME = UnifieUtil.convertDateToD8( UnifieUtil.null2(JZARQ) );//D8
				
				//	建专案医生
				String JZAYS = rs.getString("JZAYS");
				String CREATE_EMP_ID = DictMap.getCzry( JZAYS ); //中心人员库 NBD03.00.001[需要注册]
				
				//	建专案单位
				String CREATE_ORG_CODE = DictMap.getZcjg(rs.getString("DJJGDM")); //医疗机构代码基础字典库 NBD02.00.002[需要匹配]
				
				//	确诊日期
				String QZRQ = rs.getString("QZRQ");
				String CONFIRMED_DATE = UnifieUtil.convertDateToD8( UnifieUtil.null2(QZRQ) );//D8
				
				//	确诊单位
				String QZDW = rs.getString("QZDW");
				String CONFIRMED_HOSPITAL = DictMap.getZcjg( UnifieUtil.null2(QZDW) );
				
				//	专案编号
				String PROJECT_NUMBER = UnifieUtil.null2(BH);
				
				//DIAGNOSIS	诊    断
				String ZDDM = rs.getString("ZDDM");
				String DIAGNOSIS = strchang( UnifieUtil.null2(ZDDM) );
				
				//	结案
				String CLOSED = "";
				
				//	结案日期
				String JARQ = rs.getString("JARQ");
				String CLOSED_DATE = UnifieUtil.convertDateToD8( UnifieUtil.null2(JARQ) );//D8
				
				//	结案医生
				String JAYS = rs.getString("JAYS");
				String CLOSED_EMP_ID =  DictMap.getCzry( UnifieUtil.null2(JAYS) );//中心人员库 NBD03.00.001[需要注册]
				
				//	结案结论
				String JAJL = rs.getString("JAJL");
				String CLOSED_CONCLUSIONS = UnifieUtil.null2(JAJL);
				
				//	结案单位
				String CLOSE_ORG_CODE = "";//医疗机构代码基础字典库 NBD02.00.002[需要匹配]
				
				//	检查者姓名
				String DJRY = rs.getString("DJRY");
				String CREATE_EMP_NAME = UnifieUtil.null2(DJRY);//DictMap.getCzry( UnifieUtil.null2(DJRY) );
				
				//	机构名称
				String DJJGMC = rs.getString("DJJGMC");
				String CREATE_ORG_NAME = DictMap.getZcjg( UnifieUtil.null2(DJJGMC) );
				
				
				Document document = DocumentHelper.createDocument();	
				Element root = document.addElement("body"); 
				
				Element head = root.addElement("head"); 
				Element userid = head.addElement("userid");
				userid.addText("nb_fybj");
				Element password = head.addElement("password");
				password.addText("123");
				Element trans_no = head.addElement("trans_no");
				trans_no.addText("B04.01.02.09");
				
				Element request = root.addElement("resquest");
				Element trans_noChild = request.addElement("B04.01.02.09");
				Element eRPTNO = trans_noChild.addElement("RPTNO");
				eRPTNO.setText(RPTNO);
				Element eHEALTH_ID  = trans_noChild.addElement("HEALTH_ID");
				eHEALTH_ID.setText(HEALTH_ID );
				Element eCREATE_TIME  = trans_noChild.addElement("CREATE_TIME");
				eCREATE_TIME.setText(CREATE_TIME );
				Element eCREATE_EMP_ID  = trans_noChild.addElement("CREATE_EMP_ID");
				eCREATE_EMP_ID.setText(CREATE_EMP_ID );
				Element eCREATE_ORG_CODE  = trans_noChild.addElement("CREATE_ORG_CODE");
				eCREATE_ORG_CODE.setText(CREATE_ORG_CODE );
				Element eCONFIRMED_DATE  = trans_noChild.addElement("CONFIRMED_DATE");
				eCONFIRMED_DATE.setText(CONFIRMED_DATE );
				Element eCONFIRMED_HOSPITAL  = trans_noChild.addElement("CONFIRMED_HOSPITAL");
				eCONFIRMED_HOSPITAL.setText(CONFIRMED_HOSPITAL );
				Element ePROJECT_NUMBER  = trans_noChild.addElement("PROJECT_NUMBER");
				ePROJECT_NUMBER.setText(PROJECT_NUMBER );
				Element eDIAGNOSIS  = trans_noChild.addElement("DIAGNOSIS");
				eDIAGNOSIS.setText(DIAGNOSIS );
				Element eCLOSED  = trans_noChild.addElement("CLOSED");
				eCLOSED.setText(CLOSED );
				
				Element eCLOSED_DATE   = trans_noChild.addElement("CLOSED_DATE");
				eCLOSED_DATE.setText(CLOSED_DATE  );
				Element eCLOSED_EMP_ID = trans_noChild.addElement("CLOSED_EMP_ID");
				eCLOSED_EMP_ID.setText(CLOSED_EMP_ID );
				Element eCLOSED_CONCLUSIONS   = trans_noChild.addElement("CLOSED_CONCLUSIONS");
				eCLOSED_CONCLUSIONS.setText(CLOSED_CONCLUSIONS  );
				Element eCLOSE_ORG_CODE  = trans_noChild.addElement("CLOSE_ORG_CODE");
				eCLOSE_ORG_CODE.setText(CLOSE_ORG_CODE  );
				Element eCREATE_EMP_NAME  = trans_noChild.addElement("CREATE_EMP_NAME");
				eCREATE_EMP_NAME.setText(CREATE_EMP_NAME  );
				Element eCREATE_ORG_NAME   = trans_noChild.addElement("CREATE_ORG_NAME");
				eCREATE_ORG_NAME.setText(CREATE_ORG_NAME  );
				Element eUPLOAD_TIME = trans_noChild.addElement("UPLOAD_TIME");
				eUPLOAD_TIME.setText(DateUtil.nowDT15());

				xml = document.getRootElement().asXML();
				xmlMap.put(RPTNO, xml);
			}
		}catch (SQLException e) {
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
		产前诊断方法代码表 NBB04.02.302,多选,字典|分割
		
		业务系统:
		用/
	*/
	private String strchang(String str){
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
	
	public static void main(String[] args){
		Eb_tre_xtxxzbza xtxxzbzaBuildingXMl = new Eb_tre_xtxxzbza();
		xtxxzbzaBuildingXMl.createxml("2009-01-01 00:00:00","2014-10-01 00:00:00");
	}
	
	private static Log logger = LogFactory.getLog(Eb_tre_xtxxzbza.class);
	
}
