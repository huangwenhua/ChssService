package newbornvisit;

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
import Interface.BuildingXml;
import coon.DBConnectionManager;
import dictall.DictMap;

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
public class NewbornVisit implements BuildingXml {

	@Override
	public Map<String, String> createxml(String startTime,String endTime) {
		CommonUtil commonUtil = new CommonUtil();
		Map<String, String> map = new HashMap<String, String>();

		DBConnectionManager mang = new DBConnectionManager();
		Connection con = mang.getConnection();
		String sql = "select top * from EB_BJ_XSEFS";
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
				transElement.setText("B04.01.01.02");
				Element resquestElement = rootElement.addElement("resquest");
				Element b04Element = resquestElement.addElement("B04.01.01.02");
				// 保健册号
				Element rptnoElement = b04Element.addElement("RPTNO");
				String rptno = commonUtil.isNull(rs.getString("GRBJH"));
				rptnoElement.setText(rptno);
				// 访视序号
				Element snElement = b04Element.addElement("NEWBORN_SN");
				String newbornSn = commonUtil.isNull(rs.getString("LSH"));
				snElement.setText(newbornSn);
				// 健康档案号
				Element idElement = b04Element.addElement("HEALTH_ID");
				idElement.setText("");
				// 是否继续监测
				Element monitorElement = b04Element.addElement("MONITOR");
				String monitor = commonUtil.isNull(rs.getString("SFJXJC"));
				monitorElement.setText(monitor);
				// 终止监测原因
				Element reasonElement = b04Element.addElement("MONITOR_REASON");
				String reason = commonUtil.isNull(rs.getString("ZZJCYY"));
				reasonElement.setText(reason);
				// 终止监测时间
				Element monDateElement = b04Element.addElement("MONITOR_DATE");
				monDateElement.setText("");
				// 访视日期
				Element visitDateElement = b04Element.addElement("VISITS_DATE");
				String visitDate = commonUtil.getDate(rs.getDate("FSRQ"), "yyyyMMdd");
				visitDateElement.setText(visitDate);
				// 预约日期
				Element appoDateElement = b04Element
						.addElement("APPOINTMENT_DATE");
				String appoDate = commonUtil.getDate(rs.getDate("YYRQ"), "yyyyMMdd");
				appoDateElement.setText(appoDate);
				// 出生日龄
				Element bornDaysElement = b04Element.addElement("BORN_DAYS");
				String bornDays = commonUtil.isNull(rs.getString("CSRL"));
				bornDaysElement.setText(bornDays);
				// 体 温
				Element temperElement = b04Element.addElement("TEMPERATURE");
				String temperature = commonUtil.isNull(rs.getString("XSETW"));
				temperElement.setText(temperature);
				// 喂养方式
				Element feedElement = b04Element.addElement("FEEDING");
				String feed = commonUtil.isNull(rs.getString("WYFS"));
				feedElement.setText(feed);
				// 食 欲
				Element appetElement = b04Element.addElement("APPETITE");
				String appetite = commonUtil.isNull(rs.getString("SY"));
				appetElement.setText(appetite);
				// 大便次数(每日)
				Element stoolFreqElement = b04Element
						.addElement("STOOL_FREQUENCY");
				String stoolFreq = commonUtil.isNull(rs.getString("DBCS"));
				stoolFreqElement.setText(stoolFreq);
				// 大便颜色
				Element stoolColorElement = b04Element
						.addElement("STOOL_COLOR");
				String stoolColor = commonUtil.isNull(rs.getString("DBYS"));
				stoolColorElement.setText(stoolColor);
				// 平均每天睡眠时间(小时)
				Element sleepElement = b04Element.addElement("SLEEP");
				String sleep = commonUtil.isNull(rs.getString("PJMTSMSJ"));
				sleepElement.setText(sleep);
				// 睡眠吵闹或夜惊
				Element sleepNoisyElement = b04Element
						.addElement("SLEEP_NOISY");
				String sleepNoisy = commonUtil.isNull(rs.getString("SMCN"));
				sleepNoisyElement.setText(sleepNoisy);
				// 睡眠多汗
				Element sleepHypElement = b04Element
						.addElement("SLEEP_HYPERHIDROSIS");
				String sleepHyp = commonUtil.isNull(rs.getString("SMDH"));
				sleepHypElement.setText(sleepHyp);
				// 本阶段发生疾病
				Element diseaElement = b04Element.addElement("DISEASE_OCCURS");
				String disease = commonUtil.isNull(rs.getString("BJDFSJB"));
				diseaElement.setText(disease);
				// 本阶段发生疾病其他
				Element diseaOthElement = b04Element
						.addElement("DISEASE_OCCURS_OTHER");
				String diseaOther = commonUtil.isNull(rs.getString("JBMC"));
				diseaOthElement.setText(diseaOther);
				// 体 重(g)
				Element weightElement = b04Element.addElement("WEIGHT");
				String weight = commonUtil.isNull(rs.getString("TZ"));
				weightElement.setText(weight);
				// 身 长(cm)
				Element bodyLenElement = b04Element.addElement("BODY_LENGTH");
				String bodyLength = commonUtil.isNull(rs.getString("SC"));
				bodyLenElement.setText(bodyLength);
				// 头 围(cm)
				Element hcElement = b04Element.addElement("HC");
				String hc = commonUtil.isNull(rs.getString("TW"));
				hcElement.setText(hc);
				// 体重评价
				Element weightEvalElement = b04Element
						.addElement("WEIGHT_EVAL");
				String weightEval = commonUtil.isNull(rs.getString("TZPJ"));
				weightEvalElement.setText(weightEval);
				// 头围评价
				Element hcEvalElement = b04Element.addElement("HC_EVAL");
				String hcEval = commonUtil.isNull(rs.getString("TWPJ"));
				hcEvalElement.setText(hcEval);
				// 是否体弱儿管理
				Element frailElement = b04Element.addElement("FRAIL");
				String frail = commonUtil.isNull(rs.getString("SFTREGL"));
				frailElement.setText(frail);
				// 黄 疸
				Element jaunElement = b04Element.addElement("JAUNDICE");
				String jaundice = commonUtil.isNull(rs.getString("HD"));
				jaunElement.setText(jaundice);
				// 脐 炎
				Element omphElement = b04Element.addElement("OMPHALITIS");
				String omphalitis = commonUtil.isNull(rs.getString("QY"));
				omphElement.setText(omphalitis);
				// 红臀
				Element redSternElement = b04Element.addElement("RED_STERN");
				String redStern = commonUtil.isNull(rs.getString("HT"));
				redSternElement.setText(redStern);
				// 心脏听诊
				Element cardiacElement = b04Element
						.addElement("CARDIAC_AUSCULTATION");
				String cardiac = commonUtil.isNull(rs.getString("XZTZ"));
				cardiacElement.setText(cardiac);
				// 心脏听诊其他
				Element cardiacOthElement = b04Element
						.addElement("CARDIAC_AUSCULTATION_OTHER");
				String cardiacOth = commonUtil.isNull(rs.getString("XZYC"));
				cardiacOthElement.setText(cardiacOth);
				// 肺听诊
				Element lungElement = b04Element
						.addElement("LUNG_AUSCULTATION");
				String lung = commonUtil.isNull(rs.getString("FTZ"));
				lungElement.setText(lung);
				// 肺听诊其他
				Element lungOthElement = b04Element
						.addElement("LUNG_AUSCULTATION_OTHER");
				String lungOth = commonUtil.isNull(rs.getString("FYC"));
				lungOthElement.setText(lungOth);
				// 是否新发现畸形
				Element defectsElement = b04Element.addElement("DEFECTS");
				String JX = commonUtil.isNull(rs.getString("JX"));
				String defects = getDefects(JX);
				defectsElement.setText(defects);
				// 其他异常情况
				Element otherAnomElement = b04Element
						.addElement("OTHER_ANOMALIES");
				String QTYCQKDM = commonUtil.isNull(rs.getString("QTYCQKDM"));
				String otherAnom = getMultiple(QTYCQKDM);
				otherAnomElement.setText(otherAnom);
				// 是否高危儿
				Element highRiskElement = b04Element.addElement("HIGH_RISK");
				String highRisk = commonUtil.isNull(rs.getString("SFTRE"));
				highRiskElement.setText(highRisk);
				// 保健指导与处理
				Element remarkElement = b04Element.addElement("REMARKS");
				String BJZDYCLDM = commonUtil.isNull(rs.getString("BJZDYCLDM"));
				String remarks = getMultiple(BJZDYCLDM);
				remarkElement.setText(remarks);
				// 访视人
				Element visitEmpElement = b04Element
						.addElement("VISITS_EMP_ID");
				String FSRBM = commonUtil.isNull(rs.getString("FSRBM"));
				String visitEmp = commonUtil.isNull(DictMap.getCzry(FSRBM));
				visitEmpElement.setText(visitEmp);
				// 填表单位
				Element createOrgElement = b04Element
						.addElement("CREATE_ORG_CODE");
				String DJJGDM = commonUtil.isNull(rs.getString("DJJGDM"));
				String createOrg = commonUtil.isNull(DictMap.getZcjg(DJJGDM));
				createOrgElement.setText(createOrg);
				// 建册日期
				Element createDateElement = b04Element
						.addElement("CREATE_DATE");
				String createDate = commonUtil.getDate(rs.getDate("DJRQ"), "yyyyMMdd");
				createDateElement.setText(createDate);
				// 检查者姓名
				Element createEmpElement = b04Element
						.addElement("CREATE_EMP_NAME");
				String createEmp = commonUtil.isNull(rs.getString("DJRY"));
				createEmpElement.setText(createEmp);
				// 机构名称
				Element orgNameElement = b04Element
						.addElement("CREATE_ORG_NAME");
				String orgName = commonUtil.isNull(rs.getString("DJJGMC"));
				orgNameElement.setText(orgName);

				// xml字符串
				xmlString = document.getRootElement().asXML();
				map.put(newbornSn, xmlString);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}

	public static void main(String[] args) throws Exception {
		NewbornVisit nb = new NewbornVisit();
		Map<String, String> map = nb.createxml("", "");
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
