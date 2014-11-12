package dictall;

import java.sql.SQLException;

import util.EhcacheUtil;

//业务系统和中心字典匹配类
//author 朱文平
public class DictMap {
	//性别匹配
	//业务系统中：1男2女4不明
	//中心系统中：0未知1男2女9未说明
	public static String getXB(String xb) {
		String ZXXB = "9";
		if ("1".equals(xb)) {
			ZXXB = "1";
		} else if ("2".equals(xb)) {
			ZXXB = "2";
		} else if ("4".equals(xb)) {
			ZXXB = "0";
		}
		return ZXXB;
	}
	// 医疗机构匹配
	public static String getZcjg(String code) throws SQLException {
		return EhcacheUtil.findValue("SYS_ZCJG", code);
	}
	//行政机构匹配
	public static String getDzxx(String code) throws SQLException {
		return EhcacheUtil.findValue("WPH_DZXX", code);
	}
	
	//职工匹配
	public static String getCzry(String code) throws SQLException {
		return EhcacheUtil.findValue("SYS_CZRY", code);
	}
	
	//出生证明
	public static String getCszm(String code) throws SQLException {
		return EhcacheUtil.findValue("T_CSZM_ZDBA", code);
	}
	
}
