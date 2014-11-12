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
/**
 * <p>
 * 标题: WomenCardInfo.java
 * </p>
 * <p>
 * 业务描述:孕卡建册
 * </p>
 * <p>
 * 公司:东华软件股份公司
 * </p>
 * <p>
 * 版权:dhcc2013
 * </p>
 * 
 * @author 金铭
 * @date 2014年4月16日
 * @version V1.0
 */
public class Fb_ycf_ykjc implements BuildingXml {

	@Override
	public Map<String, String> createxml(String startTime, String endTime) {
		Map<String, String> map = new HashMap<String, String>();

		Connection con = JdbcPool.getConnection();
		String sql = "select YKBH,GRBJH,ICKH,POXM,POSFZH,POLXDH,POHJ,POWHCD,POZY,POJWBSDM,POXHJBDM,POXY,POYJ,POYCJZSDM,YFXZ,JCSJ,JCRQ,JCDWDM,DJRYBM,JWJBSDM,XHJBDM,XY,YJ,ZGSSS,FJSSS,CCNL,YJZQ,JQ,TJ,YCJZSDM,JQHP,QTYCQKDM,JCDBG,BGSJ,ZWDDM,GWBZ,JABZ,DJJGMC,DJRY from FB_YCF_YKJC where XGRQ>? and XGRQ<=?";
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
				transElement.setText("B04.02.01.00");
				Element resquestElement = rootElement.addElement("resquest");
				Element b04Element = resquestElement.addElement("B04.02.01.00");
				// 孕产妇标识号
				Element rptnoElement = b04Element.addElement("RPTNO");
				String rptno = CommonUtil.isNull(rs.getString("GRBJH"));
				rptnoElement.setText(rptno);
				// 个人基本信息标识号
				Element healthIdElement = b04Element.addElement("HEALTH_ID");
				//String healthId = CommonUtil.isNull(rs.getString("GRBJH"));
				healthIdElement.setText("");
				// IC卡号
				Element cardElement = b04Element.addElement("CARD_NO");
				String card = CommonUtil.isNull(rs.getString("YKBH"));
				cardElement.setText(card);
				// 户籍
				Element pgreElement = b04Element.addElement("PGRESIDE");
				pgreElement.setText("");
				// 配偶姓名
				Element spouseElement = b04Element.addElement("SPOUSE");
				String spouse = CommonUtil.isNull(rs.getString("POXM"));
				spouseElement.setText(spouse);
				// 配偶身份证号
				Element spIdcardElement = b04Element
						.addElement("SPOUSE_IDCARD");
				String spIdcard = CommonUtil.isNull(rs.getString("POSFZH"));
				spIdcardElement.setText(spIdcard);
				// 配偶联系电话
				Element spLinkElement = b04Element.addElement("SPOUSE_LINK");
				String spLink = CommonUtil.isNull(rs.getString("POLXDH"));
				spLinkElement.setText(spLink);
				// 配偶户籍
				Element spResideElement = b04Element
						.addElement("SPOUSE_RESIDE");
				String spReside = CommonUtil.isNull(rs.getString("POHJ"));
				spResideElement.setText(spReside);
				// 配偶文化程度代码
				Element spEduElement = b04Element
						.addElement("SPOUSE_EDUCATION");
				String POWHCD = CommonUtil.isNull(rs.getString("POWHCD"));
				String spEdu = getEdu(POWHCD);
				spEduElement.setText(spEdu);
				// 配偶职业代码
				Element spOccupElement = b04Element
						.addElement("SPOUSE_OCCUPATION");
				String POZY = CommonUtil.isNull(rs.getString("POZY"));
				String spOccup = getOccup(POZY);
				spOccupElement.setText(spOccup);
				// 配偶既往病史代码
				Element spMedElement = b04Element
						.addElement("SPOUSE_MEDICAL_HISTORY");
				String spMed = CommonUtil.getComplications(rs.getString("POJWBSDM"));
				spMedElement.setText(spMed);
				// 配偶现患疾病代码
				Element spHisElement = b04Element.addElement("SPOUSE_HISTORY");
				String spHis = CommonUtil.getComplications(rs.getString("POXHJBDM"));
				spHisElement.setText(spHis);
				// 配偶吸烟
				Element spSmokElement = b04Element
						.addElement("SPOUSE_SMOKE_STATUS");
				String spSmoke = CommonUtil.isNull(rs.getString("POXY"));
				spSmokElement.setText(spSmoke);
				// 配偶饮酒
				Element spDrinkElement = b04Element
						.addElement("SPOUSE_DRINK_STATUS");
				String spDrink = CommonUtil.isNull(rs.getString("POYJ"));
				spDrinkElement.setText(spDrink);
				// 配偶与遗传有关的家族史
				Element spFamilyElement = b04Element
						.addElement("SPOUSE_FAMILY_HISTORY");
				String spFamily = CommonUtil.getComplications(rs.getString("POYCJZSDM"));
				spFamilyElement.setText(spFamily);
				// 孕妇性质
				Element natureElement = b04Element.addElement("NATURE");
				String nature = CommonUtil.isNull(rs.getString("YFXZ"));
				natureElement.setText(nature);
				// 建册时间
				Element creatTimeElement = b04Element
						.addElement("CREATE_TIME_TYPE");
				String createTime = CommonUtil.isNull(rs.getString("JCSJ"));
				creatTimeElement.setText(createTime);
				// 建册日期
				Element creatDateElement = b04Element.addElement("CREATE_DATE");
				String createDate = CommonUtil.getDate(rs.getDate("JCRQ"), "yyyyMMdd");
				creatDateElement.setText(createDate);
				// 建册单位代码
				Element creatOrgElement = b04Element
						.addElement("CREATE_ORG_CODE");
				String JCDWDM = CommonUtil.isNull(rs.getString("JCDWDM"));
				String createOrg = CommonUtil.isNull(EhcacheUtil.findValue("SYS_ZCJG", JCDWDM));
				creatOrgElement.setText(createOrg);
				// 建册人员编码
				Element creatEmpElement = b04Element
						.addElement("CREATE_EMP_ID");
				String DJRYBM = CommonUtil.isNull(rs.getString("DJRYBM"));
				String createEmp = CommonUtil.isNull(EhcacheUtil.findValue("SYS_CZRY", DJRYBM));
				creatEmpElement.setText(createEmp);
				// 既往史代码
				Element fMedElement = b04Element
						.addElement("F_MEDICAL_HISTORYD");
				String fMedical = CommonUtil.getComplications(rs.getString("JWJBSDM"));
				fMedElement.setText(fMedical);
				// 现患疾病代码
				Element fHisElement = b04Element.addElement("F_HISTORY");
				String fHistory = CommonUtil.getComplications(rs.getString("XHJBDM"));
				fHisElement.setText(fHistory);
				// 吸烟
				Element smokeElement = b04Element.addElement("SMOKE_STATUS");
				String smoke = CommonUtil.isNull(rs.getString("XY"));
				smokeElement.setText(smoke);
				// 饮酒
				Element drinkElement = b04Element.addElement("DRINK_STATUS");
				String drink = CommonUtil.isNull(rs.getString("YJ"));
				drinkElement.setText(drink);
				// 子宫手术史
				Element uterElement = b04Element.addElement("UTERINE_SURGERY");
				String uterine = CommonUtil.isNull(rs.getString("ZGSSS"));
				uterElement.setText(uterine);
				// 附件手术史
				Element surgElement = b04Element.addElement("SURGICAL_HISTORY");
				String surgical = CommonUtil.isNull(rs.getString("FJSSS"));
				surgElement.setText(surgical);
				// 初潮年龄
				Element menaAgeElement = b04Element.addElement("MENARCHE_AGE");
				String menarcheAge = CommonUtil.isNull(rs.getString("CCNL"));
				menaAgeElement.setText(menarcheAge);
				// 月经周期
				Element mensCyclElement = b04Element
						.addElement("MENSTRUAL_CYCLE");
				String menstrual = CommonUtil.isNull(rs.getString("YJZQ"));
				mensCyclElement.setText(menstrual);
				// 经期
				Element mensElement = b04Element.addElement("MENSTRUAL");
				String menstr = CommonUtil.isNull(rs.getString("JQ"));
				mensElement.setText(menstr);
				// 痛经
				Element dysElement = b04Element.addElement("DYSMENORRHEA");
				String dysmen = CommonUtil.isNull(rs.getString("TJ"));
				dysElement.setText(dysmen);
				// 遗传家族史代码
				Element familyElement = b04Element.addElement("FAMILY_HISTORY");
				String familyHis = CommonUtil.getComplications(rs.getString("YCJZSDM"));
				familyElement.setText(familyHis);
				// 近亲婚配
				Element kinElement = b04Element.addElement("KIN_MARRIAGE");
				String kinMarr = CommonUtil.isNull(rs.getString("JQHP"));
				kinElement.setText(kinMarr);
				// 其他异常情况代码
				Element otherElement = b04Element.addElement("OTHER_ANOMALIES");
				String otherAnom = CommonUtil.isNull(rs.getString("QTYCQKDM"));
				otherElement.setText(otherAnom);
				// 监测地变更
				Element monitElement = b04Element.addElement("MONITORING");
				String monitor = CommonUtil.isNull(rs.getString("JCDBG"));
				monitElement.setText(monitor);
				// 变更时间
				Element changeElement = b04Element.addElement("CHANGETIME");
				String changeTime = CommonUtil.getDate(rs.getDate("BGSJ"),
						"yyyyMMdd'T'HHmmss");
				changeElement.setText(changeTime);
				// 转往地代码
				Element transfElement = b04Element.addElement("TRANSFERRED");
				String transfer = CommonUtil.isNull(rs.getString("ZWDDM"));
				transfElement.setText(transfer);
				// 高危标志
				Element riskElement = b04Element.addElement("RISK_SIGNS");
				String GWBZ = CommonUtil.isNull(rs.getString("GWBZ"));
				String risk = getRisk(GWBZ);
				riskElement.setText(risk);
				// 结案标志
				Element closeElement = b04Element.addElement("CLOSECASE");
				String JABZ = CommonUtil.isNull(rs.getString("JABZ"));
				String close = getClose(JABZ);
				closeElement.setText(close);
				// 孕妇类别
				Element womanElement = b04Element.addElement("WOMAN_TYPE");
				womanElement.setText("");
				// 登记机构名称
				Element orgNameElement = b04Element
						.addElement("CREATE_ORG_NAME");
				String orgName = CommonUtil.isNull(rs.getString("DJJGMC"));
				orgNameElement.setText(orgName);
				// 登记人员姓名
				Element empNameElement = b04Element
						.addElement("CREATE_EMP_NAME");
				String empName = CommonUtil.isNull(rs.getString("DJRY"));
				empNameElement.setText(empName);
				
				Element eUPLOAD_TIME = b04Element.addElement("UPLOAD_TIME");
				eUPLOAD_TIME.setText(DateUtil.nowDT15());

				// xml字符串
				xmlString = document.getRootElement().asXML();
				map.put(rptno, xmlString);
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
		Fb_ycf_ykjc w = new Fb_ycf_ykjc();
		Map<String, String> map = w.createxml("2012-03-29 00:00:00.000", "2012-04-29 00:00:00.000");
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
		    Object key = entry.getKey();
		    Object value = entry.getValue();
		    System.out.println("key=" + key + " value=" + value);
		}
	}

	// 职业
	// 业务系统中：1农民;2工人;3商业;4干部;5军人;6科技;7教卫;8其它
	// 中心系统中：1100国家机关、党群组织、企业、事业单位负责人;1101国家机关;1102党群组织;1200专业技术人员;1201技术人员;1300办事人员和有关人员;1400商业、服务业人员;
	public String getOccup(String code) {
		String zxbm = "";
		switch (code) {
		case "1":
			zxbm = "3300";
			break;
		case "2":
			zxbm = "3100";
			break;
		case "3":
			zxbm = "1400";
			break;
		case "4":
			zxbm = "1100";
			break;
		case "5":
			zxbm = "1700";
			break;
		case "6":
			zxbm = "5000";
			break;
		case "7":
			zxbm = "5001";
			break;
		case "8":
			zxbm = "3900";
			break;
		default:
			break;
		}
		return zxbm;
	}

	// 文化程度
	// 业务系统中：1文盲半文盲;2小学;3初中;4高中;5技工;6中专/中技;7大专;8大学本科;9研究生
	// 中心系统中：10研究生;20大学本科（简称“大学”）;30大学专科和专科学校;40中等专业学校或中等技术学校;50技工学校;60高中;70初中;80小学;90文盲或半文盲;97其他
	public String getEdu(String code) {
		String zxbm = "";
		switch (code) {
		case "1":
			zxbm = "90";
			break;
		case "2":
			zxbm = "80";
			break;
		case "3":
			zxbm = "70";
			break;
		case "4":
			zxbm = "60";
			break;
		case "5":
			zxbm = "50";
			break;
		case "6":
			zxbm = "40";
			break;
		case "7":
			zxbm = "30";
			break;
		case "8":
			zxbm = "20";
			break;
		case "9":
			zxbm = "10";
			break;
		default:
			break;
		}
		return zxbm;
	}

	// 多选,字典|分割
	public String getMultiple(String code) {
		String zxbm = "";
		if (!"".equals(code)) {
			zxbm = code.replace("/", "|");
		}
		return zxbm;
	}

	// 结案标志
	// 业务系统:1已结案;2未结案
	// 中心系统:1是；2否
	public String getClose(String code) {
		String zxbm = "";
		switch (code) {
		case "1":
			zxbm = "1";
			break;
		case "2":
			zxbm = "2";
			break;
		default:
			break;
		}
		return zxbm;
	}
	
	//高危标志
	//业务系统：0否 1是
	//中心系统：1是 2否
	public String getRisk(String code){
		String zxbm = "";
		switch (code) {
		case "0":
			zxbm = "2";
			break;
		case "1":
			zxbm = "1";
			break;
		default:
			break;
		}
		return zxbm;
	}
}
