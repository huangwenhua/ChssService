package impl;

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

import sys.CommonUtil;
import util.DateUtil;
import Interface.BuildingXml;
import dictall.DictMap;
import coon.JdbcPool;

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
public class Fb_ycf_xsecsqk implements BuildingXml {

	@Override
	public Map<String, String> createxml(String startTime, String endTime) {
		Map<String, String> map = new HashMap<String, String>();

		Connection con = JdbcPool.getConnection();
		String sql = "select MQGRBJH,CSSX,XSEXB,XSEXM,CSTZ,CSSC,CSTW,TEMCRQ,TEMCSJS,TEMCSJF,CSJJ,STSCYWJX,ASPFYFZ,ASPFWFZ,XSECS,XSECSQT,XSEQTJBDM,XSEYWJX,DJRY,DJJGMC,XSELSH from FB_YCF_XSECSQK where XGRQ>? and XGRQ<=?";
		PreparedStatement pspm = null;
		ResultSet rs;
		String xmlString = "";

		try {
			pspm = con.prepareStatement(sql);
			pspm.setString(1, startTime);
			pspm.setString(2, endTime);
			rs = pspm.executeQuery();
			while (rs.next()) {
				Document document = DocumentHelper.createDocument();
				// 交易码
				Element rootElement = document.addElement("body");
				Element headElement = rootElement.addElement("head");
				Element useridElement = headElement.addElement("userid");
				useridElement.setText("nb_fybj");
				Element pwdElement = headElement.addElement("password");
				pwdElement.setText("123");
				Element transElement = headElement.addElement("trans_no");
				transElement.setText("B04.01.01.03");
				Element resquestElement = rootElement.addElement("resquest");
				Element b04Element = resquestElement.addElement("B04.01.01.03");
				// 保健册号
				Element rptnoElement = b04Element.addElement("RPTNO");
				String rptno = CommonUtil.isNull(rs.getString("XSELSH"));
				rptnoElement.setText(rptno);
				// 健康档案号
				Element idElement = b04Element.addElement("HEALTH_ID");
				idElement.setText("");
				// 母亲保健册号
				Element motherElement = b04Element.addElement("MOTHER_RPTNO");
				String motherRptno = CommonUtil.isNull(rs.getString("MQGRBJH"));
				motherElement.setText(motherRptno);
				// 出生顺序
				Element birthOrderElement = b04Element.addElement("BIRTHORDER");
				String birthOrder = CommonUtil.isNull(rs.getString("CSSX"));
				birthOrderElement.setText(birthOrder);
				// 新生儿性别
				Element sexElement = b04Element.addElement("SEX");
				String XSEXB = CommonUtil.isNull(rs.getString("XSEXB"));
				String sex = DictMap.getXB(XSEXB);
				sexElement.setText(sex);
				// 新生儿姓名
				Element nameElement = b04Element.addElement("NAME");
				String name = CommonUtil.isNull(rs.getString("XSEXM"));
				nameElement.setText(name.length() >= 20 ? name.substring(0, 20)
						: name);
				// 出生体重
				Element weightElement = b04Element.addElement("WEIGHT");
				String weight = CommonUtil.isNull(rs.getString("CSTZ"));
				weightElement.setText(weight);
				// 出生身长
				Element heightElement = b04Element.addElement("HEIGHT");
				String height = CommonUtil.isNull(rs.getString("CSSC"));
				heightElement.setText(height);
				// 出生头围
				Element headCirElement = b04Element
						.addElement("HEAD_CIRCUMFERENCE");
				String head = CommonUtil.isNull(rs.getString("CSTW"));
				headCirElement.setText(head);
				// 胎儿娩出日期
				Element fetalElement = b04Element.addElement("FETAL_DI_DATE");
				String fetalDate = CommonUtil.getDate(rs.getDate("TEMCRQ"),
						"yyyyMMdd");
				fetalElement.setText(fetalDate);
				// 新生儿娩出时间
				Element neonatalElement = b04Element
						.addElement("NEONATAL_DI_TIME");
				String neonatal = CommonUtil.isNull(rs.getString("TEMCSJS"));
				neonatalElement.setText(neonatal);
				// 新生儿娩出时间2
				Element neonatalElement2 = b04Element
						.addElement("NEONATAL_DI_TIME2");
				String neonatal2 = CommonUtil.isNull(rs.getString("TEMCSJF"));
				neonatalElement2.setText(neonatal2);
				// 出生结局
				Element birthOutElement = b04Element
						.addElement("BIRTH_OUTCOMES");
				String birthOut = CommonUtil.isNull(rs.getString("CSJJ"));
				birthOutElement.setText(birthOut);
				// 死胎死产儿有无畸形
				Element malforElement = b04Element.addElement("MALFORMATION");
				String malfor = CommonUtil.isNull(rs.getString("STSCYWJX"));
				malforElement.setText(malfor);
				// 阿氏评分(一分钟)
				Element apgar1Element = b04Element.addElement("APGAR_SCORE1");
				String apgar1 = CommonUtil.isNull(rs.getString("ASPFYFZ"));
				apgar1Element.setText(apgar1);
				// 阿氏评分(五分钟)
				Element apgar5Element = b04Element.addElement("APGAR_SCORE");
				String apgar5 = CommonUtil.isNull(rs.getString("ASPFWFZ"));
				apgar5Element.setText(apgar5);
				// 新生儿产伤
				Element traumaElement = b04Element.addElement("TRAUMA");
				String trauma = CommonUtil.isNull(rs.getString("XSECS"));
				traumaElement.setText(trauma);
				// 新生儿产伤Q
				Element traumaOthElement = b04Element
						.addElement("TRAUMA_OTHER");
				String traumaOth = CommonUtil.isNull(rs.getString("XSECSQT"));
				traumaOthElement.setText(traumaOth);
				// 新生儿其它疾病
				Element otherDisElement = b04Element
						.addElement("OTHER_DISEASES");
				String otherDis = CommonUtil.getComplications(rs
						.getString("XSEQTJBDM"));
				otherDisElement.setText(otherDis);
				// 新生儿有无畸形
				Element deformElement = b04Element.addElement("DEFORMITY");
				String deform = CommonUtil.isNull(rs.getString("XSEYWJX"));
				deformElement.setText(deform);
				// 检查者姓名
				Element createEmpElement = b04Element
						.addElement("CREATE_EMP_NAME");
				String createEmp = CommonUtil.isNull(rs.getString("DJRY"));
				createEmpElement.setText(createEmp);
				// 机构名称
				Element createOrgElement = b04Element
						.addElement("CREATE_ORG_NAME");
				String createOrg = CommonUtil.isNull(rs.getString("DJJGMC"));
				createOrgElement.setText(createOrg);
				
				Element eUPLOAD_TIME = b04Element.addElement("UPLOAD_TIME");
				eUPLOAD_TIME.setText(DateUtil.nowDT15());

				// xml字符串
				xmlString = document.getRootElement().asXML();
				map.put(rs.getString("XSELSH"), xmlString);

			}
			if (rs != null) {
				rs.close();
			}
			return map;
		} catch (SQLException e) {
			e.printStackTrace();
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

	// 多选,字典|分割
	public String getMultiple(String code) {
		String zxbm = "";
		if (!"".equals(code)) {
			zxbm = code.replace("/", "|");
		}
		return zxbm;
	}

	public static void main(String[] args) {
		Fb_ycf_xsecsqk nb = new Fb_ycf_xsecsqk();
		Map<String, String> map = nb.createxml("2012-03-29 00:00:00.000",
				"2012-04-29 00:00:00.000");
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			System.out.println("key=" + key + " value=" + value);
		}
	}
}
