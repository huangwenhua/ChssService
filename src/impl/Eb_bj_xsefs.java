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
import util.EhcacheUtil;
import Interface.BuildingXml;
import coon.JdbcPool;
///import coon.JdbcPool;

/**
 * <p>
 * 标题: NewbornVisit.java
 * </p>
 * <p>
 * 业务描述:新生儿访视记录
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
public class Eb_bj_xsefs implements BuildingXml {

	@Override
	public Map<String, String> createxml(String startTime,String endTime) {
		Map<String, String> map = new HashMap<String, String>();

		Connection con = JdbcPool.getConnection();
		String sql = "select GRBJH,LSH,SFJXJC,ZZJCYY,FSRQ,YYRQ,CSRL,XSETW,WYFS,SY,DBCS,DBYS,PJMTSMSJ,SMCN,SMDH,BJDFSJB,JBMC,TZ,SC,TW,TZPJ,TWPJ,SFTREGL,HD,QY,HT,XZTZ,XZYC,FTZ,FYC,JX,QTYCQKDM,SFTRE,BJZDYCLDM,FSRBM,DJJGDM,DJRQ,DJRY,DJJGMC from EB_BJ_XSEFS where XGRQ>? and XGRQ<=?";
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
				transElement.setText("B04.01.01.02");
				Element resquestElement = rootElement.addElement("resquest");
				Element b04Element = resquestElement.addElement("B04.01.01.02");
				// 保健册号
				Element rptnoElement = b04Element.addElement("RPTNO");
				String rptno = CommonUtil.isNull(rs.getString("GRBJH"));
				rptnoElement.setText(rptno);
				// 访视序号
				Element snElement = b04Element.addElement("NEWBORN_SN");
				String newbornSn = CommonUtil.isNull(rs.getString("LSH"));
				if("".equals(newbornSn)) continue;
				snElement.setText(newbornSn);
				// 健康档案号
				Element idElement = b04Element.addElement("HEALTH_ID");
				idElement.setText("");
				// 是否继续监测
				Element monitorElement = b04Element.addElement("MONITOR");
				String monitor = CommonUtil.isNull(rs.getString("SFJXJC"));
				monitorElement.setText(monitor);
				// 终止监测原因
				Element reasonElement = b04Element.addElement("MONITOR_REASON");
				String reason = CommonUtil.isNull(rs.getString("ZZJCYY"));
				reasonElement.setText(reason);
				// 终止监测时间
				Element monDateElement = b04Element.addElement("MONITOR_DATE");
				monDateElement.setText("");
				// 访视日期
				Element visitDateElement = b04Element.addElement("VISITS_DATE");
				String visitDate = CommonUtil.getDate(rs.getDate("FSRQ"), "yyyyMMdd");
				visitDateElement.setText(visitDate);
				// 预约日期
				Element appoDateElement = b04Element
						.addElement("APPOINTMENT_DATE");
				String appoDate = CommonUtil.getDate(rs.getDate("YYRQ"), "yyyyMMdd");
				appoDateElement.setText(appoDate);
				// 出生日龄
				Element bornDaysElement = b04Element.addElement("BORN_DAYS");
				String bornDays = CommonUtil.isNull(rs.getString("CSRL"));
				bornDaysElement.setText(bornDays);
				// 体 温
				Element temperElement = b04Element.addElement("TEMPERATURE");
				String temperature = CommonUtil.isNull(rs.getString("XSETW"));
				temperElement.setText(temperature);
				// 喂养方式
				Element feedElement = b04Element.addElement("FEEDING");
				String feed = CommonUtil.isNull(rs.getString("WYFS"));
				feedElement.setText(feed);
				// 食 欲
				Element appetElement = b04Element.addElement("APPETITE");
				String appetite = CommonUtil.isNull(rs.getString("SY"));
				appetElement.setText(appetite);
				// 大便次数(每日)
				Element stoolFreqElement = b04Element
						.addElement("STOOL_FREQUENCY");
				String stoolFreq = CommonUtil.isNull(rs.getString("DBCS"));
				stoolFreqElement.setText(stoolFreq);
				// 大便颜色
				Element stoolColorElement = b04Element
						.addElement("STOOL_COLOR");
				String stoolColor = CommonUtil.isNull(rs.getString("DBYS"));
				stoolColorElement.setText(stoolColor);
				// 平均每天睡眠时间(小时)
				Element sleepElement = b04Element.addElement("SLEEP");
				String sleep = CommonUtil.isNull(rs.getString("PJMTSMSJ"));
				sleepElement.setText(sleep);
				// 睡眠吵闹或夜惊
				Element sleepNoisyElement = b04Element
						.addElement("SLEEP_NOISY");
				String sleepNoisy = CommonUtil.isNull(rs.getString("SMCN"));
				sleepNoisyElement.setText(sleepNoisy);
				// 睡眠多汗
				Element sleepHypElement = b04Element
						.addElement("SLEEP_HYPERHIDROSIS");
				String sleepHyp = CommonUtil.isNull(rs.getString("SMDH"));
				sleepHypElement.setText(sleepHyp);
				// 本阶段发生疾病
				Element diseaElement = b04Element.addElement("DISEASE_OCCURS");
				String disease = CommonUtil.isNull(rs.getString("BJDFSJB"));
				diseaElement.setText(disease);
				// 本阶段发生疾病其他
				Element diseaOthElement = b04Element
						.addElement("DISEASE_OCCURS_OTHER");
				String diseaOther = CommonUtil.isNull(rs.getString("JBMC"));
				diseaOthElement.setText(diseaOther);
				// 体 重(g)
				Element weightElement = b04Element.addElement("WEIGHT");
				String weight = CommonUtil.isNull(rs.getString("TZ"));
				weightElement.setText(weight);
				// 身 长(cm)
				Element bodyLenElement = b04Element.addElement("BODY_LENGTH");
				String bodyLength = CommonUtil.isNull(rs.getString("SC"));
				bodyLenElement.setText(bodyLength);
				// 头 围(cm)
				Element hcElement = b04Element.addElement("HC");
				String hc = CommonUtil.isNull(rs.getString("TW"));
				hcElement.setText(hc);
				// 体重评价
				Element weightEvalElement = b04Element
						.addElement("WEIGHT_EVAL");
				String weightEval = CommonUtil.isNull(rs.getString("TZPJ"));
				weightEvalElement.setText(weightEval);
				// 头围评价
				Element hcEvalElement = b04Element.addElement("HC_EVAL");
				String hcEval = CommonUtil.isNull(rs.getString("TWPJ"));
				hcEvalElement.setText(hcEval);
				// 是否体弱儿管理
				Element frailElement = b04Element.addElement("FRAIL");
				String frail = CommonUtil.isNull(rs.getString("SFTREGL"));
				frailElement.setText(frail);
				// 黄 疸
				Element jaunElement = b04Element.addElement("JAUNDICE");
				String jaundice = CommonUtil.isNull(rs.getString("HD"));
				jaunElement.setText(jaundice);
				// 脐 炎
				Element omphElement = b04Element.addElement("OMPHALITIS");
				String omphalitis = CommonUtil.isNull(rs.getString("QY"));
				omphElement.setText(omphalitis);
				// 红臀
				Element redSternElement = b04Element.addElement("RED_STERN");
				String redStern = CommonUtil.isNull(rs.getString("HT"));
				redSternElement.setText(redStern);
				// 心脏听诊
				Element cardiacElement = b04Element
						.addElement("CARDIAC_AUSCULTATION");
				String cardiac = CommonUtil.isNull(rs.getString("XZTZ"));
				cardiacElement.setText(cardiac);
				// 心脏听诊其他
				Element cardiacOthElement = b04Element
						.addElement("CARDIAC_AUSCULTATION_OTHER");
				String cardiacOth = CommonUtil.isNull(rs.getString("XZYC"));
				cardiacOthElement.setText(cardiacOth);
				// 肺听诊
				Element lungElement = b04Element
						.addElement("LUNG_AUSCULTATION");
				String lung = CommonUtil.isNull(rs.getString("FTZ"));
				lungElement.setText(lung);
				// 肺听诊其他
				Element lungOthElement = b04Element
						.addElement("LUNG_AUSCULTATION_OTHER");
				String lungOth = CommonUtil.isNull(rs.getString("FYC"));
				lungOthElement.setText(lungOth);
				// 是否新发现畸形
				Element defectsElement = b04Element.addElement("DEFECTS");
				String JX = CommonUtil.isNull(rs.getString("JX"));
				String defects = getDefects(JX);
				defectsElement.setText(defects);
				// 其他异常情况
				Element otherAnomElement = b04Element
						.addElement("OTHER_ANOMALIES");
				String otherAnom = CommonUtil.getComplications(rs.getString("QTYCQKDM"));
				otherAnomElement.setText(otherAnom);
				// 是否高危儿
				Element highRiskElement = b04Element.addElement("HIGH_RISK");
				String highRisk = CommonUtil.isNull(rs.getString("SFTRE"));
				highRiskElement.setText(highRisk);
				// 保健指导与处理
				Element remarkElement = b04Element.addElement("REMARKS");
				String remarks = CommonUtil.getComplications(rs.getString("BJZDYCLDM"));
				remarkElement.setText(remarks);
				// 访视人
				Element visitEmpElement = b04Element
						.addElement("VISITS_EMP_ID");
				String FSRBM = CommonUtil.isNull(rs.getString("FSRBM"));
				String visitEmp = CommonUtil.isNull(EhcacheUtil.findValue("SYS_CZRY", FSRBM));
				visitEmpElement.setText(visitEmp);
				// 填表单位
				Element createOrgElement = b04Element
						.addElement("CREATE_ORG_CODE");
				String DJJGDM = CommonUtil.isNull(rs.getString("DJJGDM"));
				String createOrg = CommonUtil.isNull(EhcacheUtil.findValue("SYS_ZCJG",DJJGDM));
				createOrgElement.setText(createOrg);
				// 建册日期
				Element createDateElement = b04Element
						.addElement("CREATE_DATE");
				String createDate = CommonUtil.getDate(rs.getDate("DJRQ"), "yyyyMMdd");
				createDateElement.setText(createDate);
				// 检查者姓名
				Element createEmpElement = b04Element
						.addElement("CREATE_EMP_NAME");
				String createEmp = CommonUtil.isNull(rs.getString("DJRY"));
				createEmpElement.setText(createEmp);
				// 机构名称
				Element orgNameElement = b04Element
						.addElement("CREATE_ORG_NAME");
				String orgName = CommonUtil.isNull(rs.getString("DJJGMC"));
				orgNameElement.setText(orgName);
				
				Element eUPLOAD_TIME = b04Element.addElement("UPLOAD_TIME");
				eUPLOAD_TIME.setText(DateUtil.nowDT15());

				// xml字符串
				xmlString = document.getRootElement().asXML();
				map.put(newbornSn, xmlString);
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

	public static void main(String[] args) throws Exception {
		Eb_bj_xsefs nb = new Eb_bj_xsefs();
		Map<String, String> map = nb.createxml("2012-03-29 00:00:00.000", "2012-04-29 00:00:00.000");
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
		    Object key = entry.getKey();
		    Object value = entry.getValue();
		    System.out.println("key=" + key + " value=" + value);
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

	// 是否新发现畸形
	// 业务：1有;2无
	// 中心：0无;1有
	public String getDefects(String code) {
		String zxbm = "";
		switch (code) {
		case "1":
			zxbm = "1";
			break;
		case "2":
			zxbm = "0";
			break;
		default:
			break;
		}
		return zxbm;
	}
 
}
