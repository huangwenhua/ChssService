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
import Interface.BuildingXml;
import dictall.DictMap;

/**
 * <p>标题: FpsfBuildingXml.java</p>
 * <p>业务描述:体弱儿(肥胖随访)(EB_TRE_FPSF)</p>
 * <p>公司:东华软件股份公司</p>
 * <p>版权:dhcc2013</p>
 * @author huangwh
 * @date 2014年4月16日
 * @version V1.0 
 */
public class Eb_tre_fpsf  implements BuildingXml {

	private String  tbName;
	
	public Eb_tre_fpsf(){
		tbName = "EB_TRE_FPSF";
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
				
				//随访序号
				String LSH = rs.getString("LSH");
				String VISITS_SN = UnifieUtil.null2(LSH).substring(0, UnifieUtil.null2(LSH).length()>5?5:UnifieUtil.null2(LSH).length()); //UnifieUtil.null2(LSH);
				
				//健康档案号
				String ZALSH = rs.getString("ZALSH");
				String HEALTH_ID = "";//UnifieUtil.null2(ZALSH);
				
				//随访日期
				String JCRQ = rs.getString("JCRQ");
				String VISITS_DATE = UnifieUtil.convertDateToD8( UnifieUtil.null2(JCRQ) ); //D8
				
				//随访医生
				String JCYS = rs.getString("JCYS");
				String VISITS_EMP_ID = DictMap.getCzry( UnifieUtil.null2(JCYS) ); //中心人员库 NBD03.00.001[需要注册]
				
				//随访单位
				String VISITS_ORG_CODE =   rs.getString("DJJGDM");;//医疗机构代码基础字典库 NBD02.00.002[需要匹配]
				
				//症状体征
				String SYMPTOMS_SIGNS = "";
				
				//辅助检查
				String ASSISTANT_EXAMINATION = "";
				
				//治疗指导
				String ZLZD = rs.getString("ZLZD");
				String TREATMENT_GUIDELINES = UnifieUtil.null2(ZLZD).substring(0, UnifieUtil.null2(ZLZD).length()>30?30:UnifieUtil.null2(ZLZD).length()); //UnifieUtil.null2(ZLZD);
				
				//指导意见
				String ZDYJ = rs.getString("ZDYJ");
				String GUIDANCE = UnifieUtil.null2(ZDYJ).substring(0, UnifieUtil.null2(ZDYJ).length()>30?30:UnifieUtil.null2(ZDYJ).length());//UnifieUtil.null2(ZDYJ);
				
				
				//预约日期
				String APPOINTMENT_DATE = ""; //D8
				
				//检查者姓名
				String CREATE_EMP_NAME = "";
				
				//机构名称
				String DJJGMC = rs.getString("DJJGMC");
				String CREATE_ORG_NAME = DictMap.getZcjg( DJJGMC );
				
				
				Document document = DocumentHelper.createDocument();	
				Element root = document.addElement("body"); 
				
				Element head = root.addElement("head"); 
				Element userid = head.addElement("userid");
				userid.addText("nb_fybj");
				Element password = head.addElement("password");
				password.addText("123");
				Element trans_no = head.addElement("trans_no");
				trans_no.addText("B04.01.02.02");
				
				Element request = root.addElement("resquest");
				Element trans_noChild = request.addElement("B04.01.02.02");
				Element eRPTNO = trans_noChild.addElement("RPTNO");
				eRPTNO.setText(RPTNO);
				Element eVISITS_SN = trans_noChild.addElement("VISITS_SN");
				eVISITS_SN.setText(VISITS_SN);
				Element eHEALTH_ID = trans_noChild.addElement("HEALTH_ID");
				eHEALTH_ID.setText(HEALTH_ID);
				Element eVISITS_DATE = trans_noChild.addElement("VISITS_DATE");
				eVISITS_DATE.setText(VISITS_DATE);
				Element eVISITS_EMP_ID = trans_noChild.addElement("VISITS_EMP_ID");
				eVISITS_EMP_ID.setText(VISITS_EMP_ID);
				Element eVISITS_ORG_CODE = trans_noChild.addElement("VISITS_ORG_CODE");
				eVISITS_ORG_CODE.setText(DictMap.getZcjg(VISITS_ORG_CODE));
				Element eSYMPTOMS_SIGNS = trans_noChild.addElement("SYMPTOMS_SIGNS");
				eSYMPTOMS_SIGNS.setText(SYMPTOMS_SIGNS);
				Element eASSISTANT_EXAMINATION = trans_noChild.addElement("ASSISTANT_EXAMINATION");
				eASSISTANT_EXAMINATION.setText(ASSISTANT_EXAMINATION);
				Element eTREATMENT_GUIDELINES = trans_noChild.addElement("TREATMENT_GUIDELINES");
				eTREATMENT_GUIDELINES.setText(TREATMENT_GUIDELINES);
				Element eGUIDANCE = trans_noChild.addElement("GUIDANCE");
				eGUIDANCE.setText(GUIDANCE);
				Element eAPPOINTMENT_DATE = trans_noChild.addElement("APPOINTMENT_DATE");
				eAPPOINTMENT_DATE.setText(APPOINTMENT_DATE);
				Element eCREATE_EMP_NAME = trans_noChild.addElement("CREATE_EMP_NAME");
				eCREATE_EMP_NAME.setText(CREATE_EMP_NAME);
				Element eCREATE_ORG_NAME = trans_noChild.addElement("CREATE_ORG_NAME");
				eCREATE_ORG_NAME.setText(CREATE_ORG_NAME);
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
	
	public static void main(String[] args) {
		Eb_tre_fpsf fpsfBuildingXml = new Eb_tre_fpsf();
		fpsfBuildingXml.createxml("2009-01-01 00:00:00","2014-10-01 00:00:00");
	}
	
	private static Log logger = LogFactory.getLog(Eb_tre_fpsf.class);
	
}
