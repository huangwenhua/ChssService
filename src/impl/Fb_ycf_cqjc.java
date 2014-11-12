package impl;

import java.io.IOException;
import java.io.StringWriter;
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
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import util.DateUtil;
import util.EhcacheUtil;
import Interface.BuildingXml;
import coon.JdbcPool;
import dictall.DictMap;

/**
 * <p>
 * 标题: Fb_ycf_cqjc.java
 * </p>
 * <p>
 * 业务描述:产前检查生成xml
 * </p>
 * <p>
 * 公司:东华软件股份公司
 * </p>
 * <p>
 * 版权:dhcc2014
 * </p>
 * 
 * @author <a href ='zhangjun_yl@dhcc.com.cn'>张军</a>
 * @date 2014年4月30日
 * @version V1.0
 */
public class Fb_ycf_cqjc implements BuildingXml {

	private Connection con;
	private PreparedStatement pspm;
	private ResultSet rs;
	private static Logger logger = Logger.getLogger(Fb_ycf_cccj.class);

	public Map<String, String> createxml(String beginTime, String endTime) {
		Map<String, String> xmlresultMap = bulidXML(beginTime, endTime);
		return xmlresultMap;

	}


	public String getCreate_emp_id(String str) {
		String result = null;
		try {
			if (str != null) {
				result = DictMap.getCzry(str);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
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

	// 多选转换方法
	public String getMultiple(String str) {
		String result = null;
		if (str != null) {
			if (str.contains("/")) {
				result = str.replace("/", "|");
				if (result.charAt(result.length() - 1) == '|') {
					result = result.substring(0, result.length() - 1);
				}

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
	public  String  limitLength500(String str){
		String result =null;
		if (str!=null) {
			if (str.getBytes().length>500) {
				result=str.substring(0, 500/3);
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
		elementMap.put("trans_no", "B04.02.03.00");
		elementMap.put("RPTNO", "");
		elementMap.put("CHECK_SN", "");
		elementMap.put("HEALTH_ID", "");
		elementMap.put("CHECK_DATE", "");
		elementMap.put("MONITOR", "");
		elementMap.put("MONITOR_REASON", "");
		elementMap.put("MONITOR_DATE", "");
		elementMap.put("BLEEDING", "");
		elementMap.put("GESTATIONAL_AGE", "");
		elementMap.put("KUNG", "");
		elementMap.put("ABDOMEN", "");
		elementMap.put("LITTER_SIZE", "");
		elementMap.put("FETUS", "");
		elementMap.put("FETAL_HEART_1", "");
		elementMap.put("FETAL_HEART_2", "");
		elementMap.put("FETAL_HEART_3", "");
		elementMap.put("EDEMA", "");
		elementMap.put("PLACENTAL", "");
		elementMap.put("NST", "");
		elementMap.put("B_ULTRASONIC", "");
		elementMap.put("RISK_FACTORS", "");
		elementMap.put("HGB", "");
		elementMap.put("PLT", "");
		elementMap.put("PRO", "");
		elementMap.put("GLU", "");
		elementMap.put("GPT_ALT", "");
		elementMap.put("GPT_VALUE", "");
		elementMap.put("RISK_LEVELS", "");
		elementMap.put("RISK_RATINGS", "");
		elementMap.put("RISK_SIGNS", "");
		elementMap.put("REMARKS", "");
		elementMap.put("REFERRAL", "");
		elementMap.put("TRANSFERRED_ORG_CODE", "");
		elementMap.put("APPOINTMENT_DATE", "");
		elementMap.put("CHECK_ORG_CODE", "");
		elementMap.put("CHECK_EMP_ID", "");
		elementMap.put("FULL_ARCHIVE", "");
		elementMap.put("SPB", "");
		elementMap.put("DPB", "");
		elementMap.put("WEIGHT", "");
		elementMap.put("CREATE_EMP_NAME", "");
		elementMap.put("CREATE_ORG_NAME", "");
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
			String sql = "select  * from FB_YCF_CQJC  where xgrq > ? and xgrq <= ? ";
			pspm = con.prepareStatement(sql);
			pspm.setString(1, beginTime);
			pspm.setString(2, endTime);
			rs = pspm.executeQuery();

			while (rs.next()) {

				Map<String, String> elementMap = new HashMap<String, String>();
				elementMap = createElementMap();
				elementMap.put("RPTNO", rs.getString("GRBJH"));
				elementMap.put("idFlag", rs.getString("LSH"));
				elementMap.put("CHECK_SN", rs.getString("LSH"));
				elementMap.put("HEALTH_ID", "");
				elementMap.put("CHECK_DATE", dateFormat(rs.getString("JCRQ")));
				elementMap.put("MONITOR", getMonitor(rs.getString("SFJXJC")));

				// ------------------------------
				elementMap.put("MONITOR_REASON", rs.getString("ZZJCYY"));
				elementMap.put("MONITOR_DATE",
						dateFormat(rs.getString("ZZJCSJ")));
				elementMap.put("BLEEDING", getBleeding(rs.getString("YDCX")));
				elementMap.put("GESTATIONAL_AGE", limitLength50(rs.getString("HSYZ")));
				elementMap.put("KUNG", rs.getString("GG"));
				elementMap.put("ABDOMEN", rs.getString("FW"));
				elementMap.put("LITTER_SIZE",
						getLitter_size(rs.getString("TS")));
				elementMap.put("FETUS", getFetus(rs.getString("TW")));
				elementMap.put("FETAL_HEART_1", "");
				elementMap.put("FETAL_HEART_2", rs.getString("TX2"));
				elementMap.put("FETAL_HEART_3", rs.getString("TX3"));
				elementMap.put("EDEMA", getEdema(rs.getString("SZ")));
				elementMap.put("PLACENTAL", getPlacental(rs.getString("TPGN")));
				elementMap.put("NST", getNst(rs.getString("NST")));
				elementMap.put("B_ULTRASONIC",
						getB_ultrasonic(rs.getString("BC")));
				elementMap.put("RISK_FACTORS", limitLength500(rs.getString("GWYSMC")));
				elementMap.put("HGB", rs.getString("XHDB"));
				elementMap.put("PLT", rs.getString("XXBJS"));
				elementMap.put("PRO", limitLength10(getPro(rs.getString("NDB"))));
				elementMap.put("GLU", limitLength10(getGlu(rs.getString("NT"))));
				elementMap.put("GPT_ALT", getGpt_alt(rs.getString("GPTALT")));
				elementMap.put("GPT_VALUE", rs.getString("GPTALTSZ"));
				elementMap.put("RISK_LEVELS", "");
				elementMap.put("RISK_RATINGS", limitLength10(rs.getString("GWPF")));
				elementMap.put("RISK_SIGNS", "");
				elementMap.put("REMARKS", "");
				elementMap.put("REFERRAL", getReferral(rs.getString("SFZZ")));
				elementMap.put("TRANSFERRED_ORG_CODE", "");
				elementMap.put("APPOINTMENT_DATE",
						dateFormat(rs.getString("YYRQ")));
				elementMap.put("CHECK_ORG_CODE",
						getCreate_org_code(rs.getString("JCDW")));
				elementMap.put("CHECK_EMP_ID",
						getCreate_emp_id(rs.getString("JCZDM")));
				elementMap.put("FULL_ARCHIVE",
						getFull_archive(rs.getString("DAWZ")));
				elementMap.put("SPB", "");
				elementMap.put("DPB", "");
				elementMap.put("WEIGHT", rs.getString("TZ"));
				elementMap.put("CREATE_EMP_NAME", "");
				elementMap.put("CREATE_ORG_NAME", "");
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
			Element trans_noChild = resquest.addElement("B04.02.03.00");
			for (String key : strMap.keySet()) {
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

	public String getMonitor(String str) {
		String result = null;
		if (str != null) {
			result = str;
		} else if (str == null) {
			result = "9";
		}
		return result;
	}

	public String getMonitor_reason(String str) {
		return str;
	}

	public String getBleeding(String str) {
		String result = null;
		if (str != null) {
			if (str.equals("2")) {
				result="0";
			}
			else{	
				result = str;
				}
		} else if (str == null) {
			result = "9";
		}
		return result;
	}

	private String getFull_archive(String str) {
		String result = null;
		if (str != null) {
			if (str.equals("0")) {
				result="2";
			}else {
				result=str;
			}
		} else if (str==null) {
			result ="9";
		}
		return result;
	}

	public String getLitter_size(String str) {
		String result = null;
		if (str != null) {
			result = str;
		}
		return result;
	}

	public String getFetus(String str) {
		String result = null;
		if (str != null) {
			result = str;
		}

		return result;
	}

	public String getEdema(String str) {
		String result = null;
		if (str != null) {
			result = str;
		}
		return result;
	}

	public String getPlacental(String str) {
		String result = null;
		if (str != null) {
			if (str.equals("3")) {
				result = "99";
			} else {
				result = str;
			}
		}
		return result;
	}

	public String getNst(String str) {
		String result = null;
		if (str != null) {
			if (str.equals("3")) {
				result = "99";
			} else {
				result = str;
			}
		}
		return result;
	}

	public String getB_ultrasonic(String str) {
		String result = null;
		if (str != null) {
			if (str.equals("3")) {
				result = "99";
			} else {
				result = str;
			}
		}
		return result;
	}

	public String getPro(String str) {
		String result = null;
		if (str != null) {
			result = str;
		}
		return result;
	}

	public String getGlu(String str) {
		return str;
	}

	public String getGpt_alt(String str) {
		String result = null;
		if (str != null) {
			if (str.equals("3")) {
				result = "99";
			}else {
				result =str;
			}
		} 
		return result;
	}

	public String getRisk_levels(String str) {
		return null;
	}

	public String getRisk_signs(String str) {
		return str;
	}

	public String getReferral(String str) {
		String result = null;
		if (str != null) {
			if (str.equals("0")) {
				result = "2";
			} else {
				result = str;
			}

		} else if (str == null) {
			result = "9";
		}
		return result;
	}

	// 测试方法
	public static void main(String[] args) {
		EhcacheUtil.getInstance().initCache();
		Fb_ycf_cqjc nb_fybj = new Fb_ycf_cqjc();
		Map<String, String> result = nb_fybj.createxml(
				"2012-03-29 00:00:00.000", "2012-03-31 00:00:00.000");
		Iterator it = result.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			System.out.println("key=" + key + " value=" + value);
		}
	}

}
