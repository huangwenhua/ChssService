/**
 * 
 */
package impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import util.DateUtil;
import util.EhcacheUtil;
import Interface.BuildingXml;
import coon.JdbcPool;
import dictall.DictMap;

/**
 * <p>标题: Fb_ycf_cccj.java</p>
 * <p>业务描述:初次产检生成xml</p>
 * <p>公司:东华软件股份公司</p>
 * <p>版权:dhcc2014</p>
 * @author <a href ='zhangjun_yl@dhcc.com.cn'>张军</a>
 * @date 2014年4月30日
 * @version V1.0 
 */
public class Fb_ycf_cccj implements BuildingXml{

	private Connection con;
	private PreparedStatement pspm;
	private ResultSet rs;
	private static Logger logger = Logger.getLogger(Fb_ycf_cccj.class);
	
	public Map<String, String> createxml(String beginTime, String endTime) {
		Map<String, String> xmlresultMap = bulidXML(beginTime, endTime);
		return xmlresultMap;

	}

	// 转换时间格式
	public String dateFormat(String str) {
		String time = null;
		if (str != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyymmdd");
				Date dt = new SimpleDateFormat("yyyy-mm-dd").parse(str);
				time = sdf.format(dt);			
			} catch (ParseException e) {
				logger.error("error :" + e.getMessage());
				e.printStackTrace();
			}

		}
		return time;
	}
	
	//多选转换方法
	public String getMultiple(String str) {
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
	

	public String getCreate_emp_id(String str) {
		String result = null;
		try {
			if (str != null) {
				result = DictMap.getCzry(str);
			}

		} catch (SQLException e) {
   		        e.printStackTrace();
		}
		return result;
	}


	public String getCreate_org_code(String str) {
		String result = null;
		if (str != null) {
			try {
				result = DictMap.getZcjg(str);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	//截取字符串长度
	//限定50个字符
	public  String  limitLength50(String str){
		String result =null;
		if (str!=null) {
			if (str.getBytes().length>50) {
				result=str.substring(0, 50/3);
			}else  {
				result =str;
			}
		}
		
		return result;
	}
	
	//限定20个字符
	public  String  limitLength20(String str){
		String result =null;
		if (str!=null) {
			if (str.getBytes().length>20) {
				result=str.substring(0, 20/3);
			}else  {
				result =str;
			}
		}
		
		return result;
	}
	
	//限定100个字符
	public  String  limitLength100(String str){
		String result =null;
		if (str!=null) {
			if (str.getBytes().length>100) {
				result=str.substring(0, 100/3);
			}else  {
				result =str;
			}
		}
		
		return result;
	}
	
	//限定10个字符
	public  String  limitLength10(String str){
		String result =null;
		if (str!=null) {
			if (str.getBytes().length>10) {
				result=str.substring(0, 10/3);
			}else  {
				result =str;
			}
		}
		
		return result;
	}
	// 按照接口规范拼装element map
	public Map<String, String> createElementMap() {
		Map<String, String> elementMap = new LinkedHashMap<String, String>();
		elementMap.put("userid", "nb_fybj");
		elementMap.put("password", "123");
		elementMap.put("trans_no", "B04.02.02.00");
		elementMap.put("RPTNO","");
		elementMap.put("HEALTH_ID","");
		elementMap.put("CHECK_DATE","");
		elementMap.put("SUETSUGU_MENSTRUAL","");
		elementMap.put("PREGNANCY_DATE","");
		elementMap.put("DIAGNOSTIC_METHOD","");
		elementMap.put("PRE_BIRTH","");
		elementMap.put("GESTATION","");
		elementMap.put("PREGNANCY_TIMES","");
		elementMap.put("PARITY","");
		elementMap.put("INFERTILITY_HISTORY","");
		elementMap.put("INFERTILITY_HISTORY_OTHER","");
		elementMap.put("ABNORMAL_PREGNANCY","");
		elementMap.put("OPERATION_HIS","");
		elementMap.put("ABORTION_DATE","");
		elementMap.put("PREGNANCY_ABNORMALITIES","");
		elementMap.put("VIRUSES_INFECTION","");
		elementMap.put("PHYSICAL_EXAMINATION","");
		elementMap.put("SPB","");
		elementMap.put("DPB","");
		elementMap.put("HEIGHT","");
		elementMap.put("WEIGHT","");
		elementMap.put("BMI","");
		elementMap.put("CARDIOPULMONARY","");
		elementMap.put("CARDIOPULMONARY_OTHER","");
		elementMap.put("GY_EXAMINATION","");
		elementMap.put("GYL_EXAMINATION_OTHE","");
		elementMap.put("INTER_DIAMETER","");
		elementMap.put("ILIAC_DIAMET","");
		elementMap.put("CONJUGATE_DIAMETER","");
		elementMap.put("EXIT_DIAMETER","");
		elementMap.put("HGB","");
		elementMap.put("PLT","");
		elementMap.put("PRO","");
		elementMap.put("GLU","");
		elementMap.put("VAGINAL_DISCHARGE","");
		elementMap.put("GPT_ALT","");
		elementMap.put("GPT_VALUE","");
		elementMap.put("HBSAG","");
		elementMap.put("HBS","");
		elementMap.put("HBEAG","");
		elementMap.put("HBE","");
		elementMap.put("HBC","");
		elementMap.put("HIV","");
		elementMap.put("SYPHILIS","");
		elementMap.put("B_ULTRASONIC","");
		elementMap.put("ECG","");
		elementMap.put("INITIAL_DIAGNOSIS","");
		elementMap.put("FOLATE","");
		elementMap.put("HEALTH_GUIDANCE","");
		elementMap.put("DOCTOR_ADVICE","");
		elementMap.put("APPOINTMENT_DATE","");
		elementMap.put("REFERRAL","");
		elementMap.put("TRANSFERRED_HOSPITAL","");
		elementMap.put("COMMISSIONED","");
		elementMap.put("CREATE_ORG_NAME","");
		elementMap.put("CREATE_EMP_ID","");
		elementMap.put("CREATE_EMP_NAME","");
		elementMap.put("UPLOAD_TIME", DateUtil.nowDT15());
		return elementMap;
	}

	// 转码
	public List<Map<String, String>> convertCode(String beginTime,
			String endTime) {

		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		try {
			// 连接数据库获取记录，并进行转换
			 con = JdbcPool.getConnection();
			String sql = "select  * from FB_YCF_CCCJ  where xgrq > ? and xgrq <= ? ";
			pspm = con.prepareStatement(sql);
			pspm.setString(1, beginTime);
			pspm.setString(2, endTime);
			rs = pspm.executeQuery();

			while (rs.next()) {
			
				
				Map<String, String> elementMap = new HashMap<String, String>();
				elementMap = createElementMap();
				elementMap.put("RPTNO", rs.getString("GRBJH"));
				elementMap.put("idFlag", rs.getString("GRBJH"));				
				
				
				elementMap.put("HEALTH_ID", "");
				elementMap.put("CHECK_DATE", dateFormat(rs.getString("JCRQ")));
				elementMap.put("SUETSUGU_MENSTRUAL", dateFormat(rs.getString("MCYJ")));
				elementMap.put("PREGNANCY_DATE", dateFormat(rs.getString("SCRSQRRQ")));
				//----------------------------------------------
				elementMap.put("DIAGNOSTIC_METHOD", limitLength100(getMultiple((rs.getString("QZFFDM")))));
				elementMap.put("PRE_BIRTH", dateFormat(rs.getString("CYQ")));
				elementMap.put("GESTATION", rs.getString("YZ"));
				elementMap.put("PREGNANCY_TIMES", rs.getString("YC"));
				elementMap.put("PARITY", rs.getString("CC"));
				elementMap.put("INFERTILITY_HISTORY", getInfertility_history(rs.getString("SFYBYZS")));
				elementMap.put("INFERTILITY_HISTORY_OTHER", rs.getString("BYZSNS"));
				
				//---------------------------------------------------
				elementMap.put("ABNORMAL_PREGNANCY", getMultiple(rs.getString("YCYCSDM")));
				elementMap.put("ABNORMAL_PREGNANCY","");
				
				elementMap.put("OPERATION_HIS", getOperation_his(rs.getString("ZGSSS")));
				elementMap.put("ABORTION_DATE",  dateFormat(rs.getString("MCFMRQ")));
				
				//----------------------------------
				elementMap.put("PREGNANCY_ABNORMALITIES", getMultiple(rs.getString("BCRSZYQYCQKDM")));
				elementMap.put("PREGNANCY_ABNORMALITIES", "");
				//-----------------------
				elementMap.put("VIRUSES_INFECTION", getMultiple(rs.getString("YZQBDGYDM")));
				elementMap.put("VIRUSES_INFECTION", "");
				elementMap.put("PHYSICAL_EXAMINATION", getPhysical_examination(rs.getString("TGJC")));
				elementMap.put("SPB", rs.getString("SSY"));
				elementMap.put("DPB", rs.getString("SZY"));
				elementMap.put("HEIGHT", rs.getString("SG"));
				elementMap.put("WEIGHT", rs.getString("TZ"));
				elementMap.put("BMI", "");
				elementMap.put("CARDIOPULMONARY", getCardiopulmonary(rs.getString("XF")));
				elementMap.put("CARDIOPULMONARY_OTHER", limitLength100(rs.getString("XFYCQK")));
				
				//------------------------------------是多选条件
				elementMap.put("GY_EXAMINATION",getGy_examination(rs.getString("FKJCDM")));
				elementMap.put("GYL_EXAMINATION_OTHE", limitLength100(rs.getString("FKJCMC")));								
				elementMap.put("INTER_DIAMETER", rs.getString("GJJJ"));
				elementMap.put("ILIAC_DIAMET", rs.getString("KJJJ"));
				elementMap.put("CONJUGATE_DIAMETER", rs.getString("ZZYJ"));
				elementMap.put("EXIT_DIAMETER", rs.getString("CKHJ"));
				elementMap.put("HGB", "");
				elementMap.put("PLT", rs.getString("XXBJS"));
				elementMap.put("PRO", getPro(rs.getString("NDB")));
				elementMap.put("GLU", getGlu(rs.getString("NT")));
				elementMap.put("VAGINAL_DISCHARGE", getVaginal_discharge(rs.getString("YDFMW")));
				elementMap.put("GPT_ALT", getGpt_alt(rs.getString("GPTALT")));
				elementMap.put("GPT_VALUE", rs.getString("GPTALTSZ"));
				elementMap.put("HBSAG", getHbsag(rs.getString("HBSAG")));
				elementMap.put("HBS", getHbs(rs.getString("KHBS")));
				elementMap.put("HBEAG", getHbeag(rs.getString("HBEAG")));
				elementMap.put("HBE", getHbe(rs.getString("KHBE")));
				elementMap.put("HBC", getHbc(rs.getString("KBC")));
				elementMap.put("HIV", getHiv(rs.getString("HIV")));
				elementMap.put("SYPHILIS", getSyphilis(rs.getString("MQXQXSY")));
				elementMap.put("B_ULTRASONIC",getB_ultrasonic(rs.getString("BC")));
				elementMap.put("ECG", getEcg(rs.getString("XDTJC")));
				elementMap.put("INITIAL_DIAGNOSIS", rs.getString("CBZD"));
				elementMap.put("FOLATE", getFolate(rs.getString("SFFYYSZBJ")));
				elementMap.put("HEALTH_GUIDANCE", getHealth_guidance(rs.getString("BJZD")));
				elementMap.put("DOCTOR_ADVICE", rs.getString("QTYZ"));
				elementMap.put("APPOINTMENT_DATE", dateFormat(rs.getString("YYRQ")));
				elementMap.put("REFERRAL", getReferral(rs.getString("SFZZ")));
				elementMap.put("TRANSFERRED_HOSPITAL", limitLength10(getCreate_org_code(rs.getString("ZWYYDM"))));
				elementMap.put("COMMISSIONED", getCommissioned(rs.getString("SFWTDG")));
				elementMap.put("CREATE_ORG_NAME", limitLength100(rs.getString("DJJGMC")));
				elementMap.put("CREATE_EMP_ID", getCreate_emp_id(rs.getString("DJRYBM")));
				elementMap.put("CREATE_EMP_NAME", "");

				// 将map加入List中
				mapList.add(elementMap);

			}
		} catch (SQLException e) {
			logger.error("error :" + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

			} catch (Exception e) {
				// e.printStackTrace();
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

		return mapList;

	}


	
	private String getCommissioned(String str) {
		String result =null;
		if (str!=null) {
			if (str.equals("0")) {
				result="2";
			}else{
			result=str;
			}
		}else if(str==null){
			result="9";
		}
		return result;
	}

	//转码方法
	public String getDiagnostic_method(String str) {
		return str;
	}
	
	
	public String getInfertility_history(String str) {
		String result =null;
		if(str!=null){
			result=str;
			
		}
		return result;
	}

	public String getAbnormal_pregnancy(String str) {
		return str;
	}

	public String getOperation_his(String str) {
		String result =null;
		if(str!=null){
			result=str;
		}
		return result;
	}

	public String getPregnancy_abnormalities(String str) {
		return str;
	}

	public String getViruses_infection(String str) {
		return str;
	}

	public String getPhysical_examination(String str) {
		String result =null;
		if(str!=null){
			result=str;
		}
		return result;
	}
	
	public String getCardiopulmonary(String str) {
	    String result =null;
	    if (str!=null) {
	    	if (str.equals("0")) {
	    		result=null;
			}else {
				result=str;
			}
	    	
		}
		return result;
	}

	public String getGy_examination(String str) {
		String result = null;
		if (str != null) {		
			if ((!str.contains("12"))&&str.contains("/")) {
				result = str.replace("/", "|");
				if (result.charAt(result.length()-1)=='|') {
					result=result.substring(0, result.length()-1);
				}

			}

		}
		return result;
	}

	public String getPro(String str) {
		String result =null;
		if (str!=null) {
			result =str;
			
		}
		return result;
	}

	public String getGlu(String str) {
		String result =null;
		if (str!=null) {
			result=str;
		}
		return result;
	}

	public String getVaginal_discharge(String str) {
		String result =null;
		if (str!=null) {
			result=str;
			
		}
		return result;
	}

	public String getGpt_alt(String str) {
		String result =null;
		if (str!=null) {
			result=str;
		}else if(str==null){
			result="9";
		}
		return result;
	}

	public String getHbsag(String str) {
		String result =null;
		if (str!=null) {
			result=str;
		}
		return result;
	}
	
	public String getHbs(String str) {
		String result =null;
		if (str!=null) {
			result=str;
		}
		return result;
	}

	public String getHbeag(String str) {
		String result =null;
		if (str!=null) {
			result=str;
		}
		return result;
	}
	
	public String getHbe(String str) {
		String result =null;
		if (str!=null) {
			result=str;
		}
		return result;
	}

	public String getHbc(String str) {
		String result =null;
		if (str!=null) {
			result=str;
		}
		return result;
	}

	public String getHiv(String str) {
		String result =null;
		if (str!=null) {
			result=str;
		}
		return result;
	}
	
	public String getSyphilis(String str) {
		String result =null;
		if (str!=null) {
			result=str;
		}
		return result;
	}

	public String getB_ultrasonic(String str) {
		String result =null;
		if (str!=null) {
			result=str;
		}
		return result;
	}
	/**
	 * @return the ecg
	 */
	public String getEcg(String str) {
		String result =null;
		if (str!=null) {
			result=str;
		}else if (str==null) {
			result="9";
		}
		return result;
	}
	/**
	 * @return the folate
	 */
	public String getFolate(String str) {
		String result =null;
		if (str!=null) {
			if (str.equals("0")) {
				result="2";
			}else {
				result=str;
			}
		}else if (str==null) {
			result="9";
			
		} 
		return result;
	}

	public String getHealth_guidance(String str) {
		String result =null;
		if (str!=null) {
			if (str.equals("2")) {
				result="0";
			}if (str.equals("1")) {
				result="1";
			}
		}else if (str==null) {
			result="9";
		}
		
		
		return result;
	}

	public String getReferral(String str) {
		String result =null;
		if (str!=null) {
			if (str.equals("0")) {
				result="2";
			}else {
				result=str;
			}
		}else if (str==null) {
			result="9";
			
		} 
		return result;
	}

//--------------------------------------------

	public Map<String, String> bulidXML(String beginTime, String endTime) {
		List<Map<String, String>> mapList = convertCode(beginTime, endTime);
		Map<String, String> xmlResultMap = new LinkedHashMap<String, String>();
		for (int i = 0; i < mapList.size(); i++) {
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("body");
			Element head = root.addElement("head");
			Map<String, String> strMap = mapList.get(i);
			Element userid = head.addElement("userid");
			userid.setText(strMap.remove("userid"));
			String idFlag = strMap.remove("idFlag");
			Element password = head.addElement("password");
			password.setText(strMap.remove("password"));
			// 添加trans_no
			Element trans_no = head.addElement("trans_no");
			trans_no.setText(strMap.remove("trans_no"));
			Element resquest = root.addElement("resquest");
			Element trans_noChild = resquest.addElement("B04.02.02.00");
			for (String key : strMap.keySet()) {
				// System.out.println("key= "+ key + " and value= " +
				// strMap.get(key));
				Element element = trans_noChild.addElement(key);
				if (strMap.get(key) != null) {
					element.setText(strMap.get(key));
				} else {
					element.setText("");
				}

			}
			xmlResultMap.put(idFlag, document.getRootElement().asXML());
		}
		return xmlResultMap;
	}


	// 测试方法
	public static void main(String[] args) {
		EhcacheUtil.getInstance().initCache();
		Fb_ycf_cccj nb_fybj = new Fb_ycf_cccj();
		Map<String, String> result = nb_fybj.createxml("2012-06-06 10:45:00.000",
				"2012-06-06 10:50:00.000");
		Iterator it = result.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			System.out.println("key=" + key + " value=" + value);
		}
	}

}
