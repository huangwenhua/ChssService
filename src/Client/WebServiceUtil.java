package Client;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.log4j.Logger;

import util.ErrorUtil;

import java.net.URL;
import java.net.HttpURLConnection;
import org.codehaus.xfire.client.Client;

import bussiness.bbb.ReconciliationServiceClient;
import bussiness.bbb.ReconciliationServiceSoap;

/**
 * <p>标题: WebServiceUtil.java</p>
 * <p>业务描述:发送WebService请求工具类</p>
 * <p>公司:东华软件股份公司</p>
 * <p>版权:dhcc2014</p>
 * @author <a href ='zhangfei_xa@dhcc.com.cn'>张飞</a>
 * @date 2014年4月15日
 * @version V1.0 
 */
public class WebServiceUtil {
	
	private static Logger logger = Logger.getLogger(WebServiceUtil.class);
	
	/**
	 * 上传地址
	 */
	private static final String URL = "http://172.18.0.22:7879/fybj_dpl_fyb/services/CsbBusService?wsdl";
	/**
	 * 对账地址
	 */
	//private static final String URLbill = "http://172.18.0.22:7879/fybj_dpl_countbill/services/ReconciliationService?wsdl";
	private static final String URLbill = "http://192.168.75.79:3031/ReconciliationService.asmx";
	
	/**
	 * 命名空间
	 */
	private static final String NAMESPACE = "http://tempuri.org/";
	/**
	 * Action路径
	 */
	private static final String ACTIONURL = "Process";
	/**
	 * 要调用的方法
	 */
	private static final String METHOD = "Process";
	
	private static Service service = new Service();

	/**
	* 方法名:  invork
	* 方法功能描述: 调用Webservice服务
	* @param xml
	* @return xml 返回信息
	* @Author:  张飞
	* @Create Date:  2014年4月15日 下午3:55:57
	 */
	public static String invork(String xml, String tbName, String key) {
		Call call;
		try {
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(URL));
			call.setUseSOAPAction(true);
			call.setSOAPActionURI(NAMESPACE + ACTIONURL); // action uri
			call.setOperationName(new QName(NAMESPACE, METHOD));// 设置要调用哪个方法
			call.addParameter(
					new QName(NAMESPACE, "XmlString"), // 设置要传递的参数
					org.apache.axis.encoding.XMLType.XSD_STRING,
					javax.xml.rpc.ParameterMode.IN);
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING); // 要返回的数据类型
			return (String) call.invoke(new Object[] { xml });// 调用方法并传递参数
		} catch (Exception e) {
			if ("Connection timed out: connect".equals(e.getMessage())) {
				logger.error("Server Connection error " + e.getMessage());
			} else {
				logger.error("Call WebService error " + e.getMessage());
			}
			logger.error("表名：" + tbName + ",主键：" + key+ " 错误信息:" + e.getMessage());
			ErrorUtil.saveFault(xml, key, tbName);
			ErrorUtil.saveFault(tbName);
			return null;
		}
	}
	
	public static String invorkBill(String xml, String tbName, String key) {
		try {
			ReconciliationServiceSoap jk = new ReconciliationServiceClient().getReconciliationServiceSoap(URLbill);
			return jk.process(xml);
		} catch (Exception e) {
			if ("Connection timed out: connect".equals(e.getMessage())) {
				logger.error("Server Connection error " + e.getMessage());
			} else {
				logger.error("Call WebService error " + e.getMessage());
			}
			logger.error("表名：" + tbName + ",主键：" + key+ " 错误信息:" + e.getMessage());
			ErrorUtil.saveFault(xml, key, tbName);
			ErrorUtil.saveFault(tbName);
			return null;
		}
	}
	
	 public static void main(String[] args)  {
		 String xml = "";
		 WebServiceUtil.invorkBill(xml, "test", "test");
	 }
	
}
