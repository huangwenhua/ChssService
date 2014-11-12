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
 * <p>标题: FpzaBuildingXml.java</p>
 * <p>业务描述:体弱儿(肥胖专案)</p>
 * <p>公司:东华软件股份公司</p>
 * <p>版权:dhcc2013</p>
 * @author huangwh
 * @date 2014年4月16日
 * @version V1.0 
 */
public class Eb_tre_fpza implements BuildingXml {
	
	private String  tbName;
	
	public Eb_tre_fpza(){
		tbName = "EB_TRE_FPZA";
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
				
				//健康档案号
				String HEALTH_ID = "";
				
				//建专案日期
				String JZARQ = rs.getString("JZARQ");
				String CREATE_TIME = UnifieUtil.convertDateToD8( UnifieUtil.null2(JZARQ) ); //D8
				
				//建专案医生
				String JZAYS = rs.getString("JZAYS"); //select rybm, ryxm from sys_czry where jgbm ='当前登录机构代码'
				String CREATE_EMP_ID = DictMap.getCzry( UnifieUtil.null2(JZAYS) );
				
				//建专案单位
				String CREATE_ORG_CODE =  rs.getString("DJJGDM");
				
				//喂养方式  NBB04.01.102
				String WYFS = rs.getString("WYFS");
				String FEEDING = UnifieUtil.null2( FEEDING(WYFS) );
				
				//过度喂养
				String GDWY = rs.getString("GDWY");
				String FEEDING_OVER = UnifieUtil.null2 (FEEDING_OVER(GDWY) ) ;
				
				//断奶月份
				String DNYF = rs.getString("DNYF");
				String WEANING_MONTH = UnifieUtil.null2( DNYF );
				
				//添加辅食月份
				String SCTJFSYF = rs.getString("SCTJFSYF");
				String FOOD_MONTH = UnifieUtil.null2 (SCTJFSYF);
				
				//添加辅食品种
				String TJFSPZ = rs.getString("TJFSPZ");
				String FEEDING_SPECIES = UnifieUtil.null2( TJFSPZ );
				
				//食欲状况
				String SYZK = rs.getString("SYZK");
				String APPETITE_STATUS = UnifieUtil.null2 (SYZK);
				
				//活动量
				String HDL = rs.getString("HDL");
				String EXERCISE = UnifieUtil.null2(HDL);
				
				//饮食习惯
				String DIET = "";
				
				//家族史
				String JZS = rs.getString("JZS");
				String FAMILY_HISTORY = UnifieUtil.null2( JZS);
				
				//结案状态
				String JAZT = rs.getString("JAZT");
				String CLOSED = UnifieUtil.null2(JAZT); //0 未结案     1	已结案
				
				//结案日期
				String JARQ = rs.getString("JARQ");
				String CLOSED_DATE = UnifieUtil.convertDateToD8( UnifieUtil.null2( JARQ ) ); //D8
				
				//结案医生
				String JAYS = rs.getString("JAYS"); //select rybm, ryxm from sys_czry where jgbm ='当前登录机构代码'
				String CLOSED_EMP_ID = DictMap.getCzry( UnifieUtil.null2(JAYS) );
				
				//结案结论
				String JAJL = rs.getString("JAJL");
				String CLOSED_CONCLUSIONS = UnifieUtil.null2(JAJL);
				
				//结案单位
				String CLOSE_ORG_CODE = "";
				
				//检查者姓名
				String XGRY = rs.getString("XGRY");
				String CREATE_EMP_NAME =  UnifieUtil.null2(XGRY);//DictMap.getCzry( UnifieUtil.null2(XGRY) );
				
				//机构名称
				String XGJGMC = rs.getString("XGJGMC");
				String CREATE_ORG_NAME = DictMap.getZcjg( UnifieUtil.null2(XGJGMC) );
				
				Document document = DocumentHelper.createDocument();	
				Element root = document.addElement("body"); 
				
				Element head = root.addElement("head"); 
				Element userid = head.addElement("userid");
				userid.addText("nb_fybj");
				Element password = head.addElement("password");
				password.addText("123");
				Element trans_no = head.addElement("trans_no");
				trans_no.addText("B04.01.02.01");
				
				Element request = root.addElement("resquest");
				Element trans_noChild = request.addElement("B04.01.02.01");
				
				Element eRPTNO = trans_noChild.addElement("RPTNO");
				eRPTNO.setText(RPTNO);
				Element eHEALTH_ID = trans_noChild.addElement("HEALTH_ID");
				eHEALTH_ID.setText(HEALTH_ID);
				Element eCREATE_TIME = trans_noChild.addElement("CREATE_TIME");
				eCREATE_TIME.setText(CREATE_TIME);
				Element eCREATE_EMP_ID = trans_noChild.addElement("CREATE_EMP_ID");
				eCREATE_EMP_ID.setText(CREATE_EMP_ID);
				Element eCREATE_ORG_CODE = trans_noChild.addElement("CREATE_ORG_CODE");
				eCREATE_ORG_CODE.setText(DictMap.getZcjg(CREATE_ORG_CODE));
				Element eFEEDING = trans_noChild.addElement("FEEDING");
				eFEEDING.setText(FEEDING);
				Element eFEEDING_OVER = trans_noChild.addElement("FEEDING_OVER");
				eFEEDING_OVER.setText(FEEDING_OVER);
				Element eWEANING_MONTH = trans_noChild.addElement("WEANING_MONTH");
				eWEANING_MONTH.setText(WEANING_MONTH);
				Element eFOOD_MONTH = trans_noChild.addElement("FOOD_MONTH");
				eFOOD_MONTH.setText(FOOD_MONTH);
				Element eFEEDING_SPECIES = trans_noChild.addElement("FEEDING_SPECIES");
				eFEEDING_SPECIES.setText(FEEDING_SPECIES);
				Element eAPPETITE_STATUS = trans_noChild.addElement("APPETITE_STATUS");
				eAPPETITE_STATUS.setText(APPETITE_STATUS);
				Element eEXERCISE = trans_noChild.addElement("EXERCISE");
				eEXERCISE.setText(EXERCISE);
				Element eDIET = trans_noChild.addElement("DIET");
				eDIET.setText(DIET);
				Element eFAMILY_HISTORY = trans_noChild.addElement("FAMILY_HISTORY");
				eFAMILY_HISTORY.setText(FAMILY_HISTORY);
				Element eCLOSED = trans_noChild.addElement("CLOSED");
				eCLOSED.setText(CLOSED);
				Element eCLOSED_DATE = trans_noChild.addElement("CLOSED_DATE");
				eCLOSED_DATE.setText(CLOSED_DATE);
				Element eCLOSED_EMP_ID = trans_noChild.addElement("CLOSED_EMP_ID");
				eCLOSED_EMP_ID.setText(CLOSED_EMP_ID);
				Element eCLOSED_CONCLUSIONS = trans_noChild.addElement("CLOSED_CONCLUSIONS");
				eCLOSED_CONCLUSIONS.setText(CLOSED_CONCLUSIONS);
				Element eCLOSE_ORG_CODE = trans_noChild.addElement("CLOSE_ORG_CODE");
				eCLOSE_ORG_CODE.setText(CLOSE_ORG_CODE);
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
	
	/**
		中心系统：
		1	母乳喂养
		2	纯母乳喂养
		3	人工喂养
		4	混合喂养
		9	其他方式
		
		业务系统:
		1	纯母乳喂养
		2	混合喂养
		3	人工喂养
	 */
	private String FEEDING(String code){
		if(code==null || code.trim().equals(""))
			return "9";
		else if(code.equals("1"))
			return "2";
		else if(code.equals("2"))
			return "4";
		else if(code.equals("3"))
			return "3";
		return "9";	
	}
	
	/**
	中心系统：
	0	无
	1	有
	9	不详
	
	业务系统:
	0	否
	1	是
	**/
	private String FEEDING_OVER(String code){
		if(code==null || code.trim().equals(""))
			return "9";
		else if(code.equals("0"))
			return "0";
		else if(code.equals("1"))
			return "1";
		return "9";
	}
	
	
	public static void main(String[] args) {
		Eb_tre_fpza fpzaBuildingXml = new Eb_tre_fpza();
		fpzaBuildingXml.createxml("2009-01-01 00:00:00","2014-10-01 00:00:00");
	}
	
	 private static Log logger = LogFactory.getLog(Eb_tre_fpza.class);
	
}
