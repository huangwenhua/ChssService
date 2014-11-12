package dictall;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import util.EhcacheUtil;
import util.XMLDocDoctor;
import coon.JdbcPool;

public class DoctorMap {
	
	public static void main(String[] args) throws Exception {
		EhcacheUtil.getInstance().initCache(); // 初始化ehcache缓存
		XMLDocDoctor ds = new XMLDocDoctor();
		Service service = new Service();
		//String url = "http://192.168.75.79:9900/CsbBusService.asmx";
		String url = "http://172.18.0.22:7879/fybj_dpl_fyb/services/CsbBusService?wsdl";
		// 在浏览器中打开url，可以找到SOAPAction: "http://www.chinsoft.com.cn/SendMQ"
		String namespace = "http://tempuri.org/";
		String actionUri = "Process"; // Action路径
		String op = "Process"; // 要调用的方法名
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new java.net.URL(url));
		call.setUseSOAPAction(true);
		call.setSOAPActionURI(namespace + actionUri); // action uri
		call.setOperationName(new QName(namespace, op));// 设置要调用哪个方法

		call.addParameter(
				new QName(namespace, "XmlString"), // 设置要传递的参数
				org.apache.axis.encoding.XMLType.XSD_STRING,
				javax.xml.rpc.ParameterMode.IN);
		call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING); // 要返回的数据类型

		Connection con = JdbcPool.getConnection();
		String sql = "select * from SYS_CZRY";
		PreparedStatement pspm;
		PreparedStatement pspm1;
		pspm = con.prepareStatement(sql);
		ResultSet rs = pspm.executeQuery();
		while (rs.next()) {
			// 机构编码
			String JGBM = rs.getString("JGBM");
			String ZXBM = DictMap.getZcjg(JGBM);
			// 人员编码
			String RYBM = rs.getString("RYBM");
			// 性别
			String XB = rs.getString("XB");
			String ZXXB = DictMap.getXB(XB);
			// 中心科室编码
			String DeptCode = "9999";
			// 姓名
			String xm = rs.getString("RYXM");

			String xmlString = "<body><head><userid>test</userid><password>123</password><trans_no>D03.00.00.00</trans_no></head><resquest><D03.00.00.00><ORG_CODE>"
					+ ZXBM
					+ "</ORG_CODE><EMP_CODE>"
					+ RYBM
					+ "</EMP_CODE><EMP_NAME>"
					+ xm
					+ "</EMP_NAME><SEX>"
					+ ZXXB
					+ "</SEX><BIRTHDAY></BIRTHDAY><IDCARD></IDCARD><GRADENAME></GRADENAME><DEPT_CODE>"
					+ DeptCode
					+ "</DEPT_CODE><USED_FLAG></USED_FLAG><DOCTOR_FLAG></DOCTOR_FLAG></D03.00.00.00></resquest></body>";
			
			String v = (String) call.invoke(new Object[] { xmlString });// 调用方法并传递参数
			String ZXDM = ds.readStringXml(v);
			String sqlString = "update SYS_CZRY set ZXBM='" + ZXDM + "' WHERE RYBM='" + RYBM + "'";
			pspm1 = con.prepareStatement(sqlString);
			pspm1.executeUpdate();
		}
	}
	
}
