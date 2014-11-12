package newborninfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import Interface.BuildingXml;
import sys.CommonUtil;
import coon.DBConnectionManager;
import dictall.DictMap;

/**
 * <p>
 * 标题: NewbornInfo.java
 * </p>
 * <p>
 * 业务描述:新生儿产时情况
 * </p>
 * <p>
 * 公司:东华软件股份公司
 * </p>
 * <p>
 * 版权:dhcc2013
 * </p>
 * 
 * @author 金铭
 * @date 2014年4月17日
 * @version V1.0
 */
public class NewbornInfo implements BuildingXml {
	
	@Override
	public Map<String, String> createxml(String startTime,String endTime) {
		DictMap ma = new DictMap();
		CommonUtil commonUtil = new CommonUtil();
		Map<String, String> map = new HashMap<String, String>();

		DBConnectionManager mang = new DBConnectionManager();
		Connection con = mang.getConnection();
		String sql = "select top * from FB_YCF_XSECSQK";
		PreparedStatement pspm;
		ResultSet rs;
		String xmlString = "";

		try {
			pspm = con.prepareStatement(sql);
			rs = pspm.executeQuery();
			while (rs.next()) {
				Document document = DocumentHelper.createDocument();
				// 交易码
				Element rootElement = document.addElement("body");
				Element headElement = rootElement.addElement("head");
				Element useridElement = headElement.addElement("userid");
				useridElement.setText("test");
				Element pwdElement = headElement.addElement("password");
				pwdElement.setText("123");
				Element transElement = headElement.addElement("trans_no");
				transElement.setText("B04.01.01.03");
				Element resquestElement = rootElement.addElement("resquest");
				Element b04Element = resquestElement.addElement("B04.01.01.03");
				// 保健册号
				Element rptnoElement = b04Element.addElement("RPTNO");
				rptnoElement.setText("");
				// 健康档案号
				Element idElement = b04Element.addElement("HEALTH_ID");
				idElement.setText("");
				// 出生顺序
				Element birthOrderElement = b04Element.addElement("BIRTHORDER");
				String birthOrder = commonUtil.isNull(rs.getString("CSSX"));
				birthOrderElement.setText(birthOrder);
				// 新生儿性别
				Element sexElement = b04Element.addElement("SEX");
				String XSEXB = commonUtil.isNull(rs.getString("XSEXB"));
				String sex = ma.getXB(XSEXB);
				sexElement.setText(sex);
				// 新生儿姓名
				Element nameElement = b04Element.addElement("NAME");
				String name = commonUtil.isNull(rs.getString("XSEXM"));
				nameElement.setText(name);
				// 出生体重
				Element weightElement = b04Element.addElement("WEIGHT");
				String weight = commonUtil.isNull(rs.getString("CSTZ"));
				weightElement.setText(weight);
				// 出生身长
				Element heightElement = b04Element.addElement("HEIGHT");
				String height = commonUtil.isNull(rs.getString("CSSC"));
				heightElement.setText(height);
				// 出生头围
				Element headCirElement = b04Element
						.addElement("HEAD_CIRCUMFERENCE");
				String head = commonUtil.isNull(rs.getString("CSTW"));
				headCirElement.setText(head);
				// 胎儿娩出日期
				Element fetalElement = b04Element.addElement("FETAL_DI_DATE");
				String fetalDate = commonUtil.getDate(rs.getDate("TEMCRQ"), "yyyyMMdd");
				fetalElement.setText(fetalDate);
				// 新生儿娩出时间
				Element neonatalElement = b04Element
						.addElement("NEONATAL_DI_TIME");
				String neonatal = commonUtil.isNull(rs.getString("TEMCSJS"));
				neonatalElement.setText(neonatal);
				// 新生儿娩出时间2
				Element neonatalElement2 = b04Element
						.addElement("NEONATAL_DI_TIME2");
				String neonatal2 = commonUtil.isNull(rs.getString("TEMCSJF"));
				neonatalElement2.setText(neonatal2);
				// 出生结局
				Element birthOutElement = b04Element
						.addElement("BIRTH_OUTCOMES");
				String birthOut = commonUtil.isNull(rs.getString("CSJJ"));
				birthOutElement.setText(birthOut);
				// 死胎死产儿有无畸形
				Element malforElement = b04Element.addElement("MALFORMATION");
				String malfor = commonUtil.isNull(rs.getString("STSCYWJX"));
				malforElement.setText(malfor);
				// 阿氏评分(一分钟)
				Element apgar1Element = b04Element.addElement("APGAR_SCORE1");
				String apgar1 = commonUtil.isNull(rs.getString("ASPFYFZ"));
				apgar1Element.setText(apgar1);
				// 阿氏评分(五分钟)
				Element apgar5Element = b04Element.addElement("APGAR_SCORE");
				String apgar5 = commonUtil.isNull(rs.getString("ASPFWFZ"));
				apgar5Element.setText(apgar5);
				// 新生儿产伤
				Element traumaElement = b04Element.addElement("TRAUMA");
				String trauma = commonUtil.isNull(rs.getString("XSECS"));
				traumaElement.setText(trauma);
				// 新生儿产伤Q
				Element traumaOthElement = b04Element
						.addElement("TRAUMA_OTHER");
				String traumaOth = commonUtil.isNull(rs.getString("XSECSQT"));
				traumaOthElement.setText(traumaOth);
				// 新生儿其它疾病
				Element otherDisElement = b04Element
						.addElement("OTHER_DISEASES");
				String XSEQTJBDM = commonUtil.isNull(rs.getString("XSEQTJBDM"));
				String otherDis = getMultiple(XSEQTJBDM);
				otherDisElement.setText(otherDis);
				// 新生儿有无畸形
				Element deformElement = b04Element.addElement("DEFORMITY");
				String deform = commonUtil.isNull(rs.getString("XSEYWJX"));
				deformElement.setText(deform);
				// 检查者姓名
				Element createEmpElement = b04Element
						.addElement("CREATE_EMP_NAME");
				String createEmp = commonUtil.isNull(rs.getString("DJRY"));
				createEmpElement.setText(createEmp);
				// 机构名称
				Element createOrgElement = b04Element
						.addElement("CREATE_ORG_NAME");
				String createOrg = commonUtil.isNull(rs.getString("DJJGMC"));
				createOrgElement.setText(createOrg);
				
				// xml字符串
				xmlString = document.getRootElement().asXML();
				map.put(rs.getString("XSELSH"), xmlString);

			}
			rs.close();
			pspm.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}

	// 多选,字典|分割
	public String getMultiple(String code) {
		String zxbm = "";
		if (!"".equals(code)) {
			zxbm = code.replace("/", "|");
		}
		return zxbm;
	}
	
	public static void main(String[] args) {
		NewbornInfo nb = new NewbornInfo();
		Map<String, String> map = nb.createxml("", "");
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
		    Object key = entry.getKey();
		    Object value = entry.getValue();
		    System.out.println("key=" + key + " value=" + value);
		}
	}
}
