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
 * <p>
 * 标题: Eb_tre_yyblsf.java
 * </p>
 * <p>
 * 业务描述:儿童营养不良随访 生成xml
 * </p>
 * <p>
 * 公司:东华软件股份公司
 * </p>
 * <p>
 * 版权:dhcc2014
 * </p>
 * 
 * @author <a href ='zhangjun_yl@dhcc.com.cn'>张军</a>
 * @date 2014年4月17日
 * @version V1.0
 */
public class Eb_tre_yyblsf implements BuildingXml {
	private Connection con;
	private PreparedStatement pspm;
	private ResultSet rs;
	private static Logger logger = Logger.getLogger(Eb_tre_yyblsf.class);

	public Map<String, String> createxml(String beginTime, String endTime) {
		Map<String, String> xmlresultMap = bulidXML(beginTime, endTime);
		return xmlresultMap;

	}

	// 转换时间格式
	public String dateFormat(String str) {
		String time = null;
		if (str != null) {
			try {
				// System.out.println(str);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyymmdd");
				Date dt = new SimpleDateFormat("yyyy-mm-dd").parse(str);
				time = sdf.format(dt);
				// System.out.println(time);
			} catch (ParseException e) {
				logger.error("error :" + e.getMessage());
				e.printStackTrace();
			}

		}
		return time;
	}

	// 按照接口规范拼装element map
	public Map<String, String> createElementMap() {
		Map<String, String> elementMap = new LinkedHashMap<String, String>();
		elementMap.put("userid", "nb_fybj");
		elementMap.put("password", "123");
		elementMap.put("trans_no", "B04.01.02.06");
		elementMap.put("RPTNO", "");
		elementMap.put("VISITS_SN", "");
		elementMap.put("HEALTH_ID", "");
		elementMap.put("VISITS_DATE", "");
		elementMap.put("VISITS_EMP_ID", "");
		elementMap.put("VISITS_ORG_CODE", "");
		elementMap.put("ASSISTANT_EXAMINATION", "");
		elementMap.put("DIAGNOSTIC_ADVICE", "");
		elementMap.put("TREATMENT_GUIDELINES", "");
		elementMap.put("CONCLUSION", "");
		elementMap.put("REVIEW_DATE", "");
		elementMap.put("CREATE_EMP_NAME", "");
		elementMap.put("CREATE_ORG_NAME", "");
		elementMap.put("UPLOAD_TIME", DateUtil.nowDT15());
		return elementMap;
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
	
	// 转码

	public List<Map<String, String>> convertCode(String beginTime,
			String endTime) {

		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		try {
			// 连接数据库获取记录，并进行转换
			con = JdbcPool.getConnection();
			String sql = "select  GRBJH,LSH,ZALSH,JCRQ,JCYS,ZDYJ,DJJGDM,ZLZD from EB_TRE_YYBLSF  where xgrq > ? and xgrq <= ? ";
			pspm = con.prepareStatement(sql);
			pspm.setString(1, beginTime);
			pspm.setString(2, endTime);
			rs = pspm.executeQuery();

			while (rs.next()) {
				Map<String, String> elementMap = new HashMap<String, String>();
				elementMap = createElementMap();
				elementMap.put("RPTNO", limitLength50(rs.getString("GRBJH")));
				elementMap.put("idFlag", rs.getString("GRBJH"));
				elementMap.put("VISITS_SN", rs.getString("LSH"));
				elementMap.put("HEALTH_ID", "");
				elementMap.put("VISITS_DATE", dateFormat(rs.getString("JCRQ")));
				// --------------------------------
				elementMap.put("VISITS_EMP_ID",
						getVisits_emp_id(rs.getString("JCYS")));
				elementMap.put("VISITS_ORG_CODE", DictMap.getZcjg(rs.getString("DJJGDM")));
				elementMap.put("ASSISTANT_EXAMINATION", "");
				elementMap.put("DIAGNOSTIC_ADVICE", limitLength100(rs.getString("ZDYJ")));
				elementMap.put("TREATMENT_GUIDELINES", limitLength100(rs.getString("ZLZD")));
				elementMap.put("CONCLUSION", "");
				elementMap.put("REVIEW_DATE", "");
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
			Element trans_noChild = resquest.addElement("B04.01.02.06");
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

	// 生成转码方法
	public String getVisits_emp_id(String str) {
		String resultString = null;
		if (str != null) {
			try {
				resultString = DictMap.getCzry(str);
			} catch (SQLException e) {
				logger.error("error :" + e.getMessage());
				e.printStackTrace();
			}
		}
		return resultString;
	}

	public String getVisits_org_code(String str) {
		return null;
	}

	// 测试方法
	public static void main(String[] args) {
		EhcacheUtil.getInstance().initCache();
		Eb_tre_yyblsf nb_fybj = new Eb_tre_yyblsf();
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
