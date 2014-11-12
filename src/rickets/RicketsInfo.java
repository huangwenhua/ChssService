package rickets;

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
 * 标题: RicketsInfo.java
 * </p>
 * <p>
 * 业务描述:佝偻病专案
 * </p>
 * <p>
 * 公司:东华软件股份公司
 * </p>
 * <p>
 * 版权:dhcc2013
 * </p>
 * 
 * @author 金铭
 * @date 2014年4月18日
 * @version V1.0
 */
public class RicketsInfo implements BuildingXml {
	
	@Override
	public Map<String, String> createxml(String startTime,String endTime) {
		CommonUtil commonUtil = new CommonUtil();
		Map<String, String> map = new HashMap<String, String>();

		DBConnectionManager mang = new DBConnectionManager();
		Connection con = mang.getConnection();
		String sql = "select top * from EB_TRE_GLBZA";
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
				transElement.setText("B04.01.02.07");
				Element resquestElement = rootElement.addElement("resquest");
				Element b04Element = resquestElement.addElement("B04.01.02.07");
				// 保健册号
				Element rptnoElement = b04Element.addElement("RPTNO");
				String rptno = commonUtil.isNull(rs.getString("GRBJH"));
				rptnoElement.setText(rptno);
				// 健康档案号
				Element idElement = b04Element.addElement("HEALTH_ID");
				idElement.setText("");
				// 建专案日期
				Element creatTimeElement = b04Element.addElement("CREATE_TIME");
				String creatTime = commonUtil.getDate(rs.getDate("JZARQ"), "yyyyMMd");
				creatTimeElement.setText(creatTime);
				// 建专案医生
				Element creatEmpElement = b04Element
						.addElement("CREATE_EMP_ID");
				String JZAYS = commonUtil.isNull(rs.getString("JZAYS"));
				String creatEmp = commonUtil.isNull(DictMap.getCzry(JZAYS));
				creatEmpElement.setText(creatEmp);
				// 建专案单位
				Element creatOrgElement = b04Element
						.addElement("CREATE_ORG_CODE");
				String DJJGDM = commonUtil.isNull(rs.getString("DJJGDM"));
				String creatOrg = commonUtil.isNull(DictMap.getZcjg(DJJGDM));
				creatOrgElement.setText(creatOrg);
				// X线片号
				Element xRayElement = b04Element.addElement("X_RAY");
				String xRay = commonUtil.isNull(rs.getString("XXPH"));
				xRayElement.setText(xRay);
				// 喂养方式
				Element feedElement = b04Element.addElement("FEEDING");
				String feed = commonUtil.isNull(rs.getString("WYFS"));
				feedElement.setText(feed);
				// 断奶月份
				Element weanElement = b04Element.addElement("WEANING_MONTH");
				//String weaning = commonUtil.isNull(rs.getString("DBYF"));
				weanElement.setText("");
				// 辅食品种
				Element suppleElement = b04Element
						.addElement("SUPPLEMENTARY_FOOD");
				String supple = commonUtil.isNull(rs.getString("TJFSPZ"));
				suppleElement.setText(supple);
				// 首次添加辅食月份
				Element foodElement = b04Element.addElement("FOOD_MONTH");
				String food = commonUtil.isNull(rs.getString("SCTJFSYF"));
				foodElement.setText(food);
				// 抬头月份e
				Element riseElement = b04Element.addElement("RISE_MONTH");
				String rise = commonUtil.isNull(rs.getString("TTYF"));
				riseElement.setText(rise);
				// 坐月份
				Element takeElement = b04Element.addElement("TAKE_MONTH");
				String takeMonth = commonUtil.isNull(rs.getString("ZYF"));
				takeElement.setText(takeMonth);
				// 站立月份
				Element standElement = b04Element.addElement("STANDAND_MONTH");
				String stand = commonUtil.isNull(rs.getString("ZLYF1"));
				standElement.setText(stand);
				// 走路月份
				Element walkElement = b04Element.addElement("WALK_MONTH");
				String walk = commonUtil.isNull(rs.getString("ZLYF2"));
				walkElement.setText(walk);
				// 萌牙月份
				Element teethElement = b04Element.addElement("TEETHM_ONTH");
				String teeth = commonUtil.isNull(rs.getString("MYYF"));
				teethElement.setText(teeth);
				// 居住情况
				Element liveElement = b04Element.addElement("LIVING");
				String living = commonUtil.isNull(rs.getString("JZQK"));
				liveElement.setText(living);
				// 居住类型
				Element liveTypeElement = b04Element.addElement("LIVING_TYPE");
				String livingType = commonUtil.isNull(rs.getString("JZLX"));
				liveTypeElement.setText(livingType);
				// 住房采光
				Element homeLightElement = b04Element
						.addElement("HOME_LIGHTING");
				String homeLight = commonUtil.isNull(rs.getString("YSXG"));
				homeLightElement.setText(homeLight);
				// 住房环境
				Element houseEnvirElement = b04Element
						.addElement("HOUSING_ENVIRONMENT");
				String houseEnvir = commonUtil.isNull(rs.getString("ZFHJ"));
				houseEnvirElement.setText(houseEnvir);
				// 住楼房层数
				Element liveLowElement = b04Element.addElement("LIVE_LOW");
				String liveLow = commonUtil.isNull(rs.getString("ZLFCS"));
				liveLowElement.setText(liveLow);
				// 既往防治情况
				Element pastTreatElement = b04Element
						.addElement("PAST_TREATMENT");
				String pastTreat = commonUtil.isNull(rs.getString("JWFZQK"));
				pastTreatElement.setText(pastTreat);
				// 晒太阳时间
				Element sumTimeElement = b04Element.addElement("SUN_TIME");
				String sumTime = commonUtil.isNull(rs.getString("STYSJ"));
				sumTimeElement.setText(sumTime);
				// 经常暴露部位
				Element exposedElement = b04Element.addElement("EXPOSED_SITE");
				String exposed = commonUtil.isNull(rs.getString("JCBLBW"));
				exposedElement.setText(exposed);
				// 母孕期健康状况
				Element healthWomElement = b04Element
						.addElement("HEALTH_WOMAN");
				String healthWom = commonUtil.isNull(rs.getString("MYQJKZK"));
				healthWomElement.setText(healthWom);
				// 晚孕期季节
				Element trimesterElement = b04Element
						.addElement("TRIMESTER_SEASON");
				String trimester = commonUtil.isNull(rs.getString("WYQJJ"));
				trimesterElement.setText(trimester);
				// 开始月份
				Element startElement = b04Element.addElement("START_MONTH");
				String start = commonUtil.isNull(rs.getString("WYQKSYF"));
				startElement.setText(start);
				// 晚孕期结束月份
				Element laterElement = b04Element.addElement("LATER_MONTH");
				String later = commonUtil.isNull(rs.getString("WYQJSYF"));
				laterElement.setText(later);
				// 户外活动
				Element outdoorElement = b04Element.addElement("OUTDOOR");
				String outdoor = commonUtil.isNull(rs.getString("HWHD"));
				outdoorElement.setText(outdoor);
				// 低钙症状
				Element calciumSymElement = b04Element
						.addElement("CALCIUM_SYMPTOMS");
				String DGBXZZ = commonUtil.isNull(rs.getString("DGBXZZ"));
				String calciumSym = getMultiple(DGBXZZ);
				calciumSymElement.setText(calciumSym);
				// 晚孕期钙剂
				Element trimCalcElement = b04Element
						.addElement("TRIMESTER_CALCIUM");
				String trimCalc = commonUtil.isNull(rs.getString("WYQGJ"));
				trimCalcElement.setText(trimCalc);
				// 孕期低钙表现
				Element calciumElement = b04Element.addElement("CALCIUM");
				String calcium = commonUtil.isNull(rs.getString("YQDGBX"));
				calciumElement.setText(calcium);
				// 平均晒太阳时间
				Element sunTimeElement = b04Element
						.addElement("AVERAGE_SUN_TIME");
				String sunTime = commonUtil.isNull(rs.getString("PJSTYSJ"));
				sunTimeElement.setText(sunTime);
				// 晚孕期用药
				Element useDrugElement = b04Element.addElement("LATEUSEDRUG");
				String useDrug = commonUtil.isNull(rs.getString("WYQYY"));
				useDrugElement.setText(useDrug);
				// 晚孕期VITD
				Element latevitdElement = b04Element.addElement("LATEVITD");
				String latevitd = commonUtil.isNull(rs.getString("WYQVITD"));
				latevitdElement.setText(latevitd);
				// 结案状态
				Element closedElement = b04Element.addElement("CLOSED");
				String closed = commonUtil.isNull(rs.getString("JAZT"));
				closedElement.setText(closed);
				// 结案日期
				Element closedDateElement = b04Element
						.addElement("CLOSED_DATE");
				String closedDate = commonUtil.getDate(rs.getDate("JARQ"), "yyyyMMdd");
				closedDateElement.setText(closedDate);
				// 结案医生
				Element closedEmpElement = b04Element
						.addElement("CLOSED_EMP_ID");
				String JAYS = commonUtil.isNull(rs.getString("JAYS"));
				String closedEmp = commonUtil.isNull(DictMap.getCzry(JAYS));
				closedEmpElement.setText(closedEmp);
				// 结案结论
				Element closedConcElement = b04Element
						.addElement("CLOSED_CONCLUSIONS");
				String closedConc = commonUtil.isNull(rs.getString("JAJL"));
				closedConcElement.setText(closedConc);
				// 结案单位
				Element closedOrgElement = b04Element
						.addElement("CLOSE_ORG_CODE");
				// String DJJGDM = commonUtil.isNull(rs.getString("DJJGDM"));
				String closedOrg = commonUtil.isNull(DictMap.getZcjg(DJJGDM));
				closedOrgElement.setText(closedOrg);
				// 检查者姓名
				Element creatEmpNameElement = b04Element
						.addElement("CREATE_EMP_NAME");
				String creatEmpName = commonUtil.isNull(rs.getString("DJRY"));
				creatEmpNameElement.setText(creatEmpName);
				// 机构名称
				Element closedOrgNameElement = b04Element
						.addElement("CREATE_ORG_NAME");
				String closedOrgName = commonUtil
						.isNull(rs.getString("DJJGMC"));
				closedOrgNameElement.setText(closedOrgName);

				// xml字符串
				xmlString = document.getRootElement().asXML();
				map.put(rs.getString("LSH"), xmlString);

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
		RicketsInfo r = new RicketsInfo();
		Map<String, String> map = r.createxml("", "");
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
		    Object key = entry.getKey();
		    Object value = entry.getValue();
		    System.out.println("key=" + key + " value=" + value);
		}
	}
}
