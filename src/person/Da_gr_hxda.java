package person;

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
 * 标题: PersonBaseInfoMap.java
 * </p>
 * <p>
 * 业务描述:妇幼专项个人基本信息
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
public class Da_gr_hxda implements BuildingXml {

	@Override
	public Map<String, String> createxml(String startTime,String endTime) {
		DictMap ma = new DictMap();
		CommonUtil commonUtil = new CommonUtil();
		Map<String, String> map = new HashMap<String, String>();

		DBConnectionManager mang = new DBConnectionManager();
		Connection con = mang.getConnection();
		String sql = "select top * from DA_GR_HXDA";
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
				transElement.setText("B04.00.00.00");
				Element resquestElement = rootElement.addElement("resquest");
				Element b04Element = resquestElement.addElement("B04.00.00.00");
				// 孕妇、儿童保健册号
				String RPTNO = commonUtil.isNull(rs.getString("GRBJH"));
				Element rptnoElement = b04Element.addElement("RPTNO");
				rptnoElement.setText(RPTNO);
				// 内部号
				Element personidElement = b04Element.addElement("PERSONID");
				personidElement.setText("");
				// 档案编号
				Element healthIdElement = b04Element.addElement("HEALTH_ID");
				healthIdElement.setText("");
				// 姓名
				Element nameElement = b04Element.addElement("NAME");
				String name = commonUtil.isNull(rs.getString("XM"));
				nameElement.setText(name);
				// 性别代码
				Element sexElement = b04Element.addElement("SEX");
				String sex = commonUtil.isNull(rs.getString("XB"));
				String sexid = ma.getXB(sex);
				sexElement.setText(sexid);
				// 出生日期
				Element birthElement = b04Element.addElement("BIRTHDAY");
				String birth = commonUtil.getDate(rs.getDate("CSRQ"),"yyyyMMdd");
				birthElement.setText(birth);
				// 户籍情况
				Element resideElement = b04Element.addElement("RESIDE");
				String reside = commonUtil.isNull(rs.getString("HJ"));
				resideElement.setText(reside);
				// 证件类型
				Element cardTypeElement = b04Element.addElement("CARD_TYPE");
				cardTypeElement.setText("");
				// 证件号码
				Element idcardElement = b04Element.addElement("IDCARD");
				String idcard = commonUtil.isNull(rs.getString("SFZH"));
				idcardElement.setText(idcard);
				// 居住所属省
				Element provincElement = b04Element.addElement("PROVINCE");
				String JZSSBM = commonUtil.isNull(rs.getString("JZSSBM"));
				String provinc = commonUtil.isNull(DictMap.getDzxx(JZSSBM));
				provincElement.setText(provinc);
				// 居住省名称
				Element provincNameElement = b04Element
						.addElement("PROVINCE_NAME");
				String provincName = commonUtil.isNull(rs.getString("JZSSMC"));
				provincNameElement.setText(provincName);
				// 居住所属市
				Element cityElement = b04Element.addElement("CITY");
				String JZDSBM = commonUtil.isNull(rs.getString("JZDSBM"));
				String city = commonUtil.isNull(DictMap.getDzxx(JZDSBM));
				cityElement.setText(city);
				// 居住市名称
				Element cityNameElement = b04Element.addElement("CITY_NAME");
				String cityName = commonUtil.isNull(rs.getString("JZDSMC"));
				cityNameElement.setText(cityName);
				// 所属区县
				Element countyElement = b04Element.addElement("COUNTY");
				String JZQXBM = commonUtil.isNull(rs.getString("JZQXBM"));
				String county = commonUtil.isNull(DictMap.getDzxx(JZQXBM));
				countyElement.setText(county);
				// 居住区县名称
				Element countyNameElement = b04Element
						.addElement("COUNTY_NAME");
				String countyName = commonUtil.isNull(rs.getString("JZQXMC"));
				countyNameElement.setText(countyName);
				// 居住所属街道（镇）
				Element townElement = b04Element.addElement("TOWN");
				String JZJDBM = commonUtil.isNull(rs.getString("JZJDBM"));
				String town = commonUtil.isNull(DictMap.getDzxx(JZJDBM));
				townElement.setText(town);
				// 居住街道名称
				Element townNameElement = b04Element.addElement("TOWN_NAME");
				String townName = commonUtil.isNull(rs.getString("JZJDMC"));
				townNameElement.setText(townName);
				// 居住社区（村）
				Element communityElement = b04Element.addElement("COMMUNITY");
				String JZJWBM = commonUtil.isNull(rs.getString("JZJWBM"));
				String community = commonUtil.isNull(DictMap.getDzxx(JZJWBM));
				communityElement.setText(community);
				// 居住社区（村）名称
				Element communityNameElement = b04Element
						.addElement("COMMUNITY_NAME");
				String communityName = commonUtil
						.isNull(rs.getString("JZJWMC"));
				communityNameElement.setText(communityName);
				// 居住地址
				Element addressElement = b04Element.addElement("ADDRESS");
				String address = commonUtil.isNull(rs.getString("JZXXDZ"));
				addressElement.setText(address);
				// 户籍所属省
				Element rpElement = b04Element.addElement("RESIDE_PROVINCE");
				String HKSSBM = commonUtil.isNull(rs.getString("HKSSBM"));
				String resideProv = commonUtil.isNull(DictMap.getDzxx(HKSSBM));
				rpElement.setText(resideProv);
				// 户籍省名称
				Element rpnElement = b04Element
						.addElement("RESIDE_PROVINCE_NAME");
				String resideProvName = commonUtil.isNull(rs
						.getString("HKSSMC"));
				rpnElement.setText(resideProvName);
				// 所属市
				Element rcElement = b04Element.addElement("RESIDE_CITY");
				String HKDSBM = commonUtil.isNull(rs.getString("HKDSBM"));
				String resideCity = commonUtil.isNull(DictMap.getDzxx(HKDSBM));
				rcElement.setText(resideCity);
				// 户籍市名称
				Element rcnElement = b04Element.addElement("RESIDE_CITY_NAME");
				String resideCityName = commonUtil.isNull(rs
						.getString("HKDSMC"));
				rcnElement.setText(resideCityName);
				// 所属区县
				Element rcountyElement = b04Element.addElement("RESIDE_COUNTY");
				String HKQXBM = commonUtil.isNull(rs.getString("HKQXBM"));
				String rcounty = commonUtil.isNull(DictMap.getDzxx(HKQXBM));
				rcountyElement.setText(rcounty);
				// 户籍区县名称
				Element rcountyNameElement = b04Element
						.addElement("RESIDE_COUNTY_NAME");
				String rcountyName = commonUtil.isNull(rs.getString("HKQXMC"));
				rcountyNameElement.setText(rcountyName);
				// 所属街道（镇）
				Element rtElement = b04Element.addElement("RESIDE_TOWN");
				String HKJDBM = commonUtil.isNull(rs.getString("HKJDBM"));
				String rt = commonUtil.isNull(DictMap.getDzxx(HKJDBM));
				rtElement.setText(rt);
				// 户籍街道名称
				Element rtnElement = b04Element.addElement("RESIDE_TOWN_NAME");
				String rtn = commonUtil.isNull(rs.getString("HKJDMC"));
				rtnElement.setText(rtn);
				// 户籍社区（村）
				Element rcommunityElement = b04Element
						.addElement("RESIDE_COMMUNITY");
				String HKJWBM = commonUtil.isNull(rs.getString("HKJWBM"));
				String rcommunity = commonUtil.isNull(DictMap.getDzxx(HKJWBM));
				rcommunityElement.setText(rcommunity);
				// 户籍社区（村名称
				Element rcommunitynameElement = b04Element
						.addElement("RESIDE_COMMUNITY_NAME");
				String rcommunityname = commonUtil.isNull(rs
						.getString("HKJWMC"));
				rcommunitynameElement.setText(rcommunityname);
				// 户籍地
				Element raElement = b04Element.addElement("RESIDE_ADDRESS");
				String ra = commonUtil.isNull(rs.getString("HKDSMC"));
				raElement.setText(ra);
				// 工作单位
				Element companyElement = b04Element.addElement("COMPANY");
				String company = commonUtil.isNull(rs.getString("GZDW"));
				companyElement.setText(company);
				// 联系电话
				Element phoneElement = b04Element.addElement("LINKPHONE");
				String phone = commonUtil.isNull(rs.getString("LXDH"));
				phoneElement.setText(phone);
				// 血型ABO代码 血型ABO名称
				Element bloodElement = b04Element.addElement("BLOOD");
				Element bloodNameElement = b04Element.addElement("BLOOD_NAME");
				String XXABO = commonUtil.isNull(rs.getString("XXABO"));
				String blood = getABO(XXABO);
				bloodElement.setText(blood);
				String bloodName = getABOName(XXABO);
				bloodNameElement.setText(bloodName);
				// Rh血型代码 RH名称
				Element rhElement = b04Element.addElement("RH_TYPE");
				Element rhNameElement = b04Element.addElement("RH_NAME");
				String XXRH = commonUtil.isNull(rs.getString("XXRH"));
				String rh = getRH(XXRH);
				rhElement.setText(rh);
				String rhName = getRhName(XXRH);
				rhNameElement.setText(rhName);
				// 文化程度 文化程度名称
				Element eduElement = b04Element.addElement("EDUCATION");
				Element eduNameElement = b04Element
						.addElement("EDUCATION_NAME");
				String WHCD = commonUtil.isNull(rs.getString("WHCD"));
				String edu = getEdu(WHCD);
				eduElement.setText(edu);
				String eduName = getEduName(WHCD);
				eduNameElement.setText(eduName);
				// 职业 职业名称
				Element occupElement = b04Element.addElement("OCCUPATION");
				Element occupNamElement = b04Element
						.addElement("OCCUPATION_NAME");
				String ZY = commonUtil.isNull(rs.getString("ZY"));
				String occup = getOccup(ZY);
				occupElement.setText(occup);
				String occupName = getOccupName(ZY);
				occupNamElement.setText(occupName);
				// 建册时间
				Element setupElement = b04Element.addElement("SETUP_TIME");
				String setup = commonUtil.getDate(rs.getDate("DJRQ"), "yyyyMMdd'T'HHmmss");
				setupElement.setText(setup);
				// 检查者姓名
				Element createEmpElement = b04Element
						.addElement("CREATE_EMP_NAME");
				String createEmp = commonUtil.isNull(rs.getString("DJRY"));
				createEmpElement.setText(createEmp);
				// 组织机构代码
				Element createOrgCodeElement = b04Element
						.addElement("CREATE_ORG_CODE");
				String DJJGDM = commonUtil.isNull(rs.getString("DJJGDM"));
				String createOrgCode = commonUtil.isNull(DictMap.getZcjg(DJJGDM));
				createOrgCodeElement.setText(createOrgCode);
				// 组织机构名称
				Element createOrgNameElement = b04Element
						.addElement("CREATE_ORG_NAME");
				String createOrgName = commonUtil
						.isNull(rs.getString("DJJGMC"));
				createOrgNameElement.setText(createOrgName);

				// xml字符串
				xmlString = document.getRootElement().asXML();
				map.put(RPTNO, xmlString);
			}
			rs.close();
			pspm.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}

	public static void main(String[] args) throws Exception {
		Da_gr_hxda p = new Da_gr_hxda();
		Map<String, String> map = p.createxml("", "");
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
		    Object key = entry.getKey();
		    Object value = entry.getValue();
		    System.out.println("key=" + key + " value=" + value);
		}
	}

	// ABO血型
	// 业务系统中：1:A型\2:B型\3:O型\4:AB型\5:不详
	// 中心系统中：0:O型\1:A型\2:B型\3:AB型\5:不详
	public String getABO(String code) {
		String zxbm = "";
		switch (code) {
		case "1":
			zxbm = "1";
			break;
		case "2":
			zxbm = "2";
			break;
		case "3":
			zxbm = "0";
			break;
		case "4":
			zxbm = "3";
			break;
		case "5":
			zxbm = "5";
			break;
		default:
			break;
		}
		return zxbm;
	}

	// ABO血型名称
	public String getABOName(String code) {
		String zxbm = "";
		switch (code) {
		case "1":
			zxbm = "A型";
			break;
		case "2":
			zxbm = "B型";
			break;
		case "3":
			zxbm = "O型";
			break;
		case "4":
			zxbm = "AB型";
			break;
		case "5":
			zxbm = "不详";
			break;
		default:
			break;
		}
		return zxbm;
	}

	// Rh血型
	// 业务系统中：1阴性 2阳性 3不详
	// 中心系统中：1Rh阴性 2Rh阳性 3不详
	public String getRH(String code) {
		String zxbm = "";
		switch (code) {
		case "1":
			zxbm = "1";
			break;
		case "2":
			zxbm = "2";
			break;
		case "3":
			zxbm = "3";
			break;
		default:
			break;
		}
		return zxbm;
	}

	// Rh血型名称
	public String getRhName(String code) {
		String zxbm = "";
		switch (code) {
		case "1":
			zxbm = "阴性";
			break;
		case "2":
			zxbm = "阳性";
			break;
		case "3":
			zxbm = "不详";
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

	// 文化程度名称
	public String getEduName(String code) {
		String zxbm = "";
		switch (code) {
		case "1":
			zxbm = "文盲半文盲";
			break;
		case "2":
			zxbm = "小学";
			break;
		case "3":
			zxbm = "初中";
			break;
		case "4":
			zxbm = "高中";
			break;
		case "5":
			zxbm = "技工";
			break;
		case "6":
			zxbm = "中专/中技";
			break;
		case "7":
			zxbm = "大专";
			break;
		case "8":
			zxbm = "大学本科";
			break;
		case "9":
			zxbm = "研究生";
			break;
		default:
			break;
		}
		return zxbm;
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
			zxbm = "2800";
			break;
		case "4":
			zxbm = "3600";
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

	// 职业名称
	public String getOccupName(String code) {
		String zxbm = "";
		switch (code) {
		case "1":
			zxbm = "农民";
			break;
		case "2":
			zxbm = "工人";
			break;
		case "3":
			zxbm = "商业";
			break;
		case "4":
			zxbm = "干部";
			break;
		case "5":
			zxbm = "军人";
			break;
		case "6":
			zxbm = "科技";
			break;
		case "7":
			zxbm = "教卫";
			break;
		case "8":
			zxbm = "其它";
			break;
		default:
			break;
		}
		return zxbm;
	}
	
}
