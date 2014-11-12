/**
 * 
 */
package impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import util.DateUtil;
import Interface.BuildingXml;
import coon.JdbcPool;
import dictall.DictMap;

/**
 * <p>标题: Eb_tre_glbsf.java</p>
 * <p>业务描述:佝偻病随访 生成xml</p>
 * <p>公司:东华软件股份公司</p>
 * <p>版权:dhcc2014</p>
 * @author 王军
 * @date 2014年4月17日
 * @version V1.0 
 */

public class Eb_tre_glbsf implements BuildingXml{
	private static Log logger = LogFactory.getLog(Eb_tre_glbsf.class);

	@Override
	public Map<String, String> createxml(String startTime,String endTime) {
		Connection con = JdbcPool.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		String sql = "select  LSH,GRBJH,ZALSH,JCRQ,JCYS,SZTS,YYRQ,GLBZZ,GLBTZ,GLJCJ,JXLSM,WPBGHD,ZDFQ,ZDFD,ZLZD,ZLVITD,ZLGJ,ZLQT,JLZT,DJRQ,DJRY,DJRYBM,DJJGDM,DJJGMC,XGRQ,XGRY,XGRYBM,XGJGDM,XGJGMC  from EB_TRE_GLBSF  where XGRQ>? and XGRQ<=?";
		PreparedStatement pspm = null;
		ResultSet rs = null;
		String xmlString = "";
		try {
			pspm = con.prepareStatement(sql);
			pspm.setString(1, startTime);
			pspm.setString(2, endTime);
			rs = pspm.executeQuery();
			while (rs.next()) {
				String LSH = rs.getString("LSH");
				//专案流水号
				String ZALSH = rs.getString("ZALSH");
				ZALSH = cutLength(ZALSH, 5);
				// 个人保健号
				String JCRQ = rs.getString("JCRQ");
				JCRQ = convertDateToD8(JCRQ);
				// 检查日期
				String GRBJH = rs.getString("GRBJH");
				//检查医生
				String JCYS = rs.getString("JCYS");
				
				/**
				 * 2014-4-29    未做字典处理                   李研edit
				 */
				JCYS = DictMap.getCzry(JCYS);

				//预约日期
				String YYRQ = rs.getString("YYRQ");
				YYRQ = convertDateToD8(YYRQ);
				//佝偻病症状
				String GLBZZ = rs.getString("GLBZZ");
				//佝偻病体征
				String GLBTZ = rs.getString("GLBTZ");
				//血生化(钙磷及乘积)
				String GLJCJ = rs.getString("GLJCJ");
				//血生化(碱性磷酸酶)
				String JXLSM = rs.getString("JXLSM");
				//X线(腕部片-干骺端)
				String WPBGHD = rs.getString("WPBGHD");
				//诊断分期
				String ZDFQ = rs.getString("ZDFQ");
				//诊断分度
				String ZDFD = rs.getString("ZDFD");
				//治疗(指导)
				String ZLZD = rs.getString("ZLZD");
				ZLZD = cutLength(ZLZD, 30);
				//治疗(VitD)
				String ZLVITD = rs.getString("ZLVITD");
				//治疗(钙剂)
				String ZLGJ = rs.getString("ZLGJ");
				//治疗(其他)
				String ZLQT = rs.getString("ZLQT");
				//记录状态
				String JLZT = rs.getString("JLZT");
				//修改人员编码
				String XGRYBM = rs.getString("XGRYBM");
				//修改机构代码
				String XGJGDM = rs.getString("XGJGDM");
				
				
				Document document = DocumentHelper.createDocument();	
				Element root = document.addElement("body"); 
				
				Element head = root.addElement("head"); 
				Element userid = head.addElement("userid");
				userid.addText("nb_fybj");
				Element password = head.addElement("password");
				password.addText("123");
				Element trans_no = head.addElement("trans_no");
				trans_no.addText("B04.01.02.08");
				
				Element request = root.addElement("resquest");//error   2014-4-28 李研改
				Element trans_noChild = request.addElement("B04.01.02.08");
				//保健册号
				Element eRPTNO = trans_noChild.addElement("RPTNO");
				eRPTNO.setText(getIsNull(GRBJH));
//				健康档案号
				Element eHEALTH_ID  = trans_noChild.addElement("HEALTH_ID");
				eHEALTH_ID.setText("");
//				随访序号
				Element eCREATE_TIME  = trans_noChild.addElement("VISITS_SN");
				eCREATE_TIME.setText(ZALSH);
				Element VISITS_DATE  = trans_noChild.addElement("VISITS_DATE");
				VISITS_DATE.setText( getIsNull(JCRQ));
				Element VISITS_EMP_ID  = trans_noChild.addElement("VISITS_EMP_ID");
				VISITS_EMP_ID.setText( getIsNull(JCYS));
				Element VISITS_ORG_CODE = trans_noChild.addElement("VISITS_ORG_CODE");
				VISITS_ORG_CODE.setText(DictMap.getZcjg(rs.getString("DJJGDM")));
				Element APPOINTMENT_DATE  = trans_noChild.addElement("APPOINTMENT_DATE");
				APPOINTMENT_DATE.setText( getIsNull(YYRQ));
				Element RICKETS_SYMPTOMS  = trans_noChild.addElement("RICKETS_SYMPTOMS");
				RICKETS_SYMPTOMS.setText( getIsNull(GLBZZ));
				Element SIGNS_RICKETS  = trans_noChild.addElement("SIGNS_RICKETS");
				SIGNS_RICKETS.setText( getIsNull(GLBTZ));
				Element BLOOD_BIOCHEMISTRY  = trans_noChild.addElement("BLOOD_BIOCHEMISTRY");
				BLOOD_BIOCHEMISTRY.setText( getIsNull(GLJCJ));
				Element BLOOD_BIOCHEMISTRY2   = trans_noChild.addElement("BLOOD_BIOCHEMISTRY2");
				BLOOD_BIOCHEMISTRY2.setText(  getIsNull(JXLSM));
				Element XLINE = trans_noChild.addElement("XLINE");
				XLINE.setText( getIsNull(WPBGHD));
				
				Element DIAGNOSIS_STAGING   = trans_noChild.addElement("DIAGNOSIS_STAGING");
				DIAGNOSIS_STAGING.setText( getIsNull(ZDFQ));
				Element DIAGNOSIS_DEGREE  = trans_noChild.addElement("DIAGNOSIS_DEGREE");
				DIAGNOSIS_DEGREE.setText( getIsNull(ZDFD));
				Element TREATMENT_VITD  = trans_noChild.addElement("TREATMENT_VITD");
				TREATMENT_VITD.setText( getIsNull(ZLVITD));
				Element TREATMENT_CALCIUM   = trans_noChild.addElement("TREATMENT_CALCIUM");
				TREATMENT_CALCIUM.setText( getIsNull(ZLGJ));
				
				Element TTREATMENT_OTHER   = trans_noChild.addElement("TTREATMENT_OTHER");
				TTREATMENT_OTHER.setText( getIsNull(ZLQT));
				Element TREATMENT_GUIDELINES  = trans_noChild.addElement("TREATMENT_GUIDELINES");
				TREATMENT_GUIDELINES.setText( getIsNull(ZLZD));
				Element CONCLUSION  = trans_noChild.addElement("CONCLUSION");
				CONCLUSION.setText( getIsNull(JLZT));
				Element CREATE_EMP_NAME   = trans_noChild.addElement("CREATE_EMP_NAME");
				CREATE_EMP_NAME.setText( getIsNull(XGRYBM));
				Element CREATE_ORG_NAME   = trans_noChild.addElement("CREATE_ORG_NAME");
				CREATE_ORG_NAME.setText( getIsNull(XGJGDM));
				
				Element eUPLOAD_TIME = trans_noChild.addElement("UPLOAD_TIME");
				eUPLOAD_TIME.setText(DateUtil.nowDT15());

				xmlString = document.getRootElement().asXML();
				map.put(LSH, xmlString);
			}
		}catch (SQLException e) {
			logger.error("fail to connect db");
		}finally{
			//2014-4-28   李研    添加链接关闭代码
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
	
	public static void main(String[] args) throws IOException {
		Eb_tre_glbsf childCare = new Eb_tre_glbsf();
		Map<String, String> result = childCare.createxml(
				"2012-04-02 00:00:00.000", "2012-04-29 00:00:00.000");
		@SuppressWarnings("rawtypes")
		Iterator it = result.entrySet().iterator();
		while (it.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) it.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			System.out.println("key=" + key + " value=" + value);
		}

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
	
	public String getIsNull(String str){
		if(str == null){
			return "";
		}
		return str;
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
	
}

