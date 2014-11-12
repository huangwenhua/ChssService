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
public class Eb_tre_glbza implements BuildingXml {

	@Override
	public Map<String, String> createxml(String startTime, String endTime) {
		Map<String, String> map = new HashMap<String, String>();

		Connection con = JdbcPool.getConnection();
		String sql = "select GRBJH,JZARQ,JZAYS,DJJGDM,XXPH,WYFS,TJFSPZ,SCTJFSYF,TTYF,ZYF,ZLYF1,ZLYF2,MYYF,JZQK,JZLX,YSXG,ZFHJ,ZLFCS,JWFZQK,STYSJ,JCBLBW,MYQJKZK,WYQJJ,WYQKSYF,WYQJSYF,HWHD,DGBXZZ,WYQGJ,YQDGBX,PJSTYSJ,WYQYY,WYQVITD,JAZT,JARQ,JAYS,JAJL,DJRY,DJJGMC,LSH from EB_TRE_GLBZA where XGRQ>? and XGRQ<=?";
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
				transElement.setText("B04.01.02.07");
				Element resquestElement = rootElement.addElement("resquest");
				Element b04Element = resquestElement.addElement("B04.01.02.07");
				// 保健册号
				Element rptnoElement = b04Element.addElement("RPTNO");
				String rptno = CommonUtil.isNull(rs.getString("GRBJH"));
				rptnoElement.setText(rptno);
				// 健康档案号
				Element idElement = b04Element.addElement("HEALTH_ID");
				idElement.setText("");
				// 建专案日期
				Element creatTimeElement = b04Element.addElement("CREATE_TIME");
				String creatTime = CommonUtil.getDate(rs.getDate("JZARQ"),
						"yyyyMMdd");
				creatTimeElement.setText(creatTime);
				// 建专案医生
				Element creatEmpElement = b04Element
						.addElement("CREATE_EMP_ID");
				String JZAYS = CommonUtil.isNull(rs.getString("JZAYS"));
				String creatEmp = CommonUtil.isNull(EhcacheUtil.findValue(
						"SYS_CZRY", JZAYS));
				creatEmpElement.setText(creatEmp);
				// 建专案单位
				Element creatOrgElement = b04Element
						.addElement("CREATE_ORG_CODE");
				String DJJGDM = CommonUtil.isNull(rs.getString("DJJGDM"));
				String creatOrg = CommonUtil.isNull(EhcacheUtil.findValue(
						"SYS_ZCJG", DJJGDM));
				creatOrgElement.setText(creatOrg);
				// X线片号
				Element xRayElement = b04Element.addElement("X_RAY");
				String xRay = CommonUtil.isNull(rs.getString("XXPH"));
				xRayElement.setText(xRay);
				// 喂养方式
				Element feedElement = b04Element.addElement("FEEDING");
				String feed = CommonUtil.isNull(rs.getString("WYFS"));
				feedElement.setText(feed);
				// 断奶月份
				Element weanElement = b04Element.addElement("WEANING_MONTH");
				// String weaning = CommonUtil.isNull(rs.getString("DBYF"));
				weanElement.setText("");
				// 辅食品种
				Element suppleElement = b04Element
						.addElement("SUPPLEMENTARY_FOOD");
				String supple = CommonUtil.substringZh(rs.getString("TJFSPZ"),10);
				suppleElement.setText(supple);
				// 首次添加辅食月份
				Element foodElement = b04Element.addElement("FOOD_MONTH");
				String food = CommonUtil.isNull(rs.getString("SCTJFSYF"));
				foodElement.setText(food);
				// 抬头月份e
				Element riseElement = b04Element.addElement("RISE_MONTH");
				String rise = CommonUtil.isNull(rs.getString("TTYF"));
				riseElement.setText(rise);
				// 坐月份
				Element takeElement = b04Element.addElement("TAKE_MONTH");
				String takeMonth = CommonUtil.isNull(rs.getString("ZYF"));
				takeElement.setText(takeMonth);
				// 站立月份
				Element standElement = b04Element.addElement("STANDAND_MONTH");
				String stand = CommonUtil.isNull(rs.getString("ZLYF1"));
				standElement.setText(stand);
				// 走路月份
				Element walkElement = b04Element.addElement("WALK_MONTH");
				String walk = CommonUtil.isNull(rs.getString("ZLYF2"));
				walkElement.setText(walk);
				// 萌牙月份
				Element teethElement = b04Element.addElement("TEETHM_ONTH");
				String teeth = CommonUtil.isNull(rs.getString("MYYF"));
				teethElement.setText(teeth);
				// 居住情况
				Element liveElement = b04Element.addElement("LIVING");
				String living = CommonUtil.isNull(rs.getString("JZQK"));
				liveElement.setText(living);
				// 居住类型
				Element liveTypeElement = b04Element.addElement("LIVING_TYPE");
				String livingType = CommonUtil.isNull(rs.getString("JZLX"));
				liveTypeElement.setText(livingType);
				// 住房采光
				Element homeLightElement = b04Element
						.addElement("HOME_LIGHTING");
				String homeLight = CommonUtil.isNull(rs.getString("YSXG"));
				homeLightElement.setText(homeLight);
				// 住房环境
				Element houseEnvirElement = b04Element
						.addElement("HOUSING_ENVIRONMENT");
				String houseEnvir = CommonUtil.isNull(rs.getString("ZFHJ"));
				houseEnvirElement.setText(houseEnvir);
				// 住楼房层数
				Element liveLowElement = b04Element.addElement("LIVE_LOW");
				String liveLow = CommonUtil.isNull(rs.getString("ZLFCS"));
				liveLowElement.setText(liveLow);
				// 既往防治情况
				Element pastTreatElement = b04Element
						.addElement("PAST_TREATMENT");
				String pastTreat = CommonUtil.isNull(rs.getString("JWFZQK"));
				pastTreatElement.setText(pastTreat);
				// 晒太阳时间
				Element sumTimeElement = b04Element.addElement("SUN_TIME");
				String sumTime = CommonUtil.isNull(rs.getString("STYSJ"));
				sumTimeElement.setText(sumTime);
				// 经常暴露部位
				Element exposedElement = b04Element.addElement("EXPOSED_SITE");
				String exposed = CommonUtil.isNull(rs.getString("JCBLBW"));
				exposedElement.setText(exposed);
				// 母孕期健康状况
				Element healthWomElement = b04Element
						.addElement("HEALTH_WOMAN");
				String healthWom = CommonUtil.isNull(rs.getString("MYQJKZK"));
				healthWomElement.setText(healthWom);
				// 晚孕期季节
				Element trimesterElement = b04Element
						.addElement("TRIMESTER_SEASON");
				String trimester = CommonUtil.isNull(rs.getString("WYQJJ"));
				trimesterElement.setText(trimester);
				// 开始月份
				Element startElement = b04Element.addElement("START_MONTH");
				String start = CommonUtil.isNull(rs.getString("WYQKSYF"));
				startElement.setText(start);
				// 晚孕期结束月份
				Element laterElement = b04Element.addElement("LATER_MONTH");
				String later = CommonUtil.isNull(rs.getString("WYQJSYF"));
				laterElement.setText(later);
				// 户外活动
				Element outdoorElement = b04Element.addElement("OUTDOOR");
				String outdoor = CommonUtil.isNull(rs.getString("HWHD"));
				outdoorElement.setText(outdoor);
				// 低钙症状
				Element calciumSymElement = b04Element
						.addElement("CALCIUM_SYMPTOMS");
				String calciumSym = CommonUtil.getComplications(rs
						.getString("DGBXZZ"));
				calciumSymElement.setText(calciumSym);
				// 晚孕期钙剂
				Element trimCalcElement = b04Element
						.addElement("TRIMESTER_CALCIUM");
				String trimCalc = CommonUtil.isNull(rs.getString("WYQGJ"));
				trimCalcElement.setText(trimCalc);
				// 孕期低钙表现
				Element calciumElement = b04Element.addElement("CALCIUM");
				String calcium = CommonUtil.isNull(rs.getString("YQDGBX"));
				calciumElement.setText(calcium);
				// 平均晒太阳时间
				Element sunTimeElement = b04Element
						.addElement("AVERAGE_SUN_TIME");
				String sunTime = CommonUtil.toInteger(rs.getString("PJSTYSJ")).toString();
				sunTimeElement.setText(sunTime);
				// 晚孕期用药
				Element useDrugElement = b04Element.addElement("LATEUSEDRUG");
				String useDrug = CommonUtil.isNull(rs.getString("WYQYY"));
				useDrugElement.setText(useDrug);
				// 晚孕期VITD
				Element latevitdElement = b04Element.addElement("LATEVITD");
				String latevitd = CommonUtil.isNull(rs.getString("WYQVITD"));
				latevitdElement.setText(latevitd);
				// 结案状态
				Element closedElement = b04Element.addElement("CLOSED");
				String closed = CommonUtil.isNull(rs.getString("JAZT"));
				closedElement.setText(closed);
				// 结案日期
				Element closedDateElement = b04Element
						.addElement("CLOSED_DATE");
				String closedDate = CommonUtil.getDate(rs.getDate("JARQ"),
						"yyyyMMdd");
				closedDateElement.setText(closedDate);
				// 结案医生
				Element closedEmpElement = b04Element
						.addElement("CLOSED_EMP_ID");
				String JAYS = CommonUtil.isNull(rs.getString("JAYS"));
				String closedEmp = CommonUtil.isNull(EhcacheUtil.findValue(
						"SYS_CZRY", JAYS));
				closedEmpElement.setText(closedEmp);
				// 结案结论
				Element closedConcElement = b04Element
						.addElement("CLOSED_CONCLUSIONS");
				String closedConc = CommonUtil.isNull(rs.getString("JAJL"));
				closedConcElement.setText(closedConc);
				// 结案单位
				Element closedOrgElement = b04Element
						.addElement("CLOSE_ORG_CODE");
				// String DJJGDM = CommonUtil.isNull(rs.getString("DJJGDM"));
				String closedOrg = CommonUtil.isNull(EhcacheUtil.findValue(
						"SYS_ZCJG", DJJGDM));
				closedOrgElement.setText(closedOrg);
				// 检查者姓名
				Element creatEmpNameElement = b04Element
						.addElement("CREATE_EMP_NAME");
				String creatEmpName = CommonUtil.isNull(rs.getString("DJRY"));
				creatEmpNameElement.setText(creatEmpName);
				// 机构名称
				Element closedOrgNameElement = b04Element
						.addElement("CREATE_ORG_NAME");
				String closedOrgName = CommonUtil
						.isNull(rs.getString("DJJGMC"));
				closedOrgNameElement.setText(closedOrgName);
				
				Element eUPLOAD_TIME = b04Element.addElement("UPLOAD_TIME");
				eUPLOAD_TIME.setText(DateUtil.nowDT15());

				// xml字符串
				xmlString = document.getRootElement().asXML();
				map.put(rs.getString("LSH"), xmlString);

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
		Eb_tre_glbza r = new Eb_tre_glbza();
		Map<String, String> map = r.createxml("2012-03-29 00:00:00.000",
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
